package com.jayrandez.swingext;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

public class MarkdownView extends JPanel
{
	protected JTextArea textArea;
	protected WebPanel webAdapter;
	
	private JSplitPane splitPane;
	
	public MarkdownView() {
		this.setLayout(new BorderLayout());
		
		this.textArea = new JTextArea();
		textArea.setFont(new Font("monospaced", Font.PLAIN, 12));
		textArea.setTabSize(4);
		textArea.setBorder(new EmptyBorder(5, 5, 5, 5));
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		
		this.webAdapter = new WebPanel();
		
		this.splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, new JScrollPane(textArea), webAdapter);
		splitPane.setResizeWeight(0.5);
		this.add(splitPane, BorderLayout.CENTER);
		
		webAdapter.loadURL("https://google.com");
	}

	private boolean painted;
	
	public void paint(Graphics g) {
		super.paint(g);
		if(!painted) {
			painted = true;
			splitPane.setDividerLocation(0.5);
		}
	}
	

}
