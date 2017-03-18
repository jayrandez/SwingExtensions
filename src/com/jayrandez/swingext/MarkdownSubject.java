package com.jayrandez.swingext;

import java.io.*;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JPanel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class MarkdownSubject<AppClass> extends Subject<AppClass> implements DocumentListener
{
	private MarkdownView view;
	private boolean dirty;
	private File tempFile;
	
	public MarkdownSubject(AppClass app) {
		super(app);
		setupView();
	}
	
	public MarkdownSubject(AppClass app, String value) {
		super(app);
		setupView();
		view.textArea.setText(value);
	}
	
	private void setupView() {
		try {
			this.tempFile = File.createTempFile("mkd_render", ".html");
			String tempFileURL = tempFile.toURI().toURL().toString();
			
			System.out.println(tempFileURL);
			
			this.view = new MarkdownView();
			view.webAdapter.loadURL(tempFileURL);
			view.textArea.getDocument().addDocumentListener(this);
			
			new Timer().scheduleAtFixedRate(new TimerTask() { public void run() {
				if(dirty)
					render();
			}}, 0, 100);
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public void render() {
		try {
			Process process = Runtime.getRuntime ().exec("pandoc -f markdown -t html");
			OutputStream stdin = process.getOutputStream();
			InputStream stdout = process.getInputStream();

			stdin.write(view.textArea.getText().getBytes());
			stdin.flush();
			stdin.close();
			
			String line;
			BufferedReader reader = new BufferedReader(new InputStreamReader(stdout));
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(tempFile)));
			writer.write("<html><body>\n");
			while((line = reader.readLine()) != null)
				writer.write(line + "\n");
			writer.write("</body></html>");
			writer.close();
			
			reader.close();
			stdout.close();
		}
		catch(IOException ex) {
			ex.printStackTrace();
		}
		
		if(view.webAdapter.isLoaded()) {
			view.webAdapter.refresh();
			
			synchronized(this) {
				dirty = false;
			}
		}
	}

	public void changedUpdate(DocumentEvent ev) { synchronized(MarkdownSubject.this) { dirty = true; } }
	public void insertUpdate(DocumentEvent ev) { synchronized(MarkdownSubject.this) { dirty = true; } }
	public void removeUpdate(DocumentEvent ev) { synchronized(MarkdownSubject.this) { dirty = true; } }

	public String getTitle() {
		return "Markdown Viewer";
	}

	public JPanel getView() {
		return view;
	}

	public void finish() {}



}
