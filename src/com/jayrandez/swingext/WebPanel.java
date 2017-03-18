package com.jayrandez.swingext;

import java.awt.BorderLayout;
import java.net.MalformedURLException;
import java.net.URL;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.JFXPanel;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebEvent;
import javafx.scene.web.WebView;
import javafx.concurrent.*;
import javafx.concurrent.Worker.State;

public class WebPanel extends JPanel
{
	private JFXPanel jfxPanel = new JFXPanel();
    private WebEngine engine;
    private boolean loaded = false;
    
	public WebPanel() {
		Platform.runLater(new Runnable() { public void run() {
			WebView view = new WebView();
			engine = view.getEngine();

			engine.titleProperty().addListener(titleChangeListener);
			engine.setOnStatusChanged(statusChangedHandler);
			engine.locationProperty().addListener(locationChangeListener);
			engine.getLoadWorker().workDoneProperty().addListener(progressListener);
			engine.getLoadWorker().exceptionProperty().addListener(exceptionListener);
			engine.getLoadWorker().stateProperty().addListener(stateListener);

			jfxPanel.setScene(new Scene(view));
		}});
		
		this.setLayout(new BorderLayout());
		this.add(jfxPanel, BorderLayout.CENTER);
	}
	
	public boolean isLoaded() {
		return loaded;
	}
	
	public void refresh() {
		Platform.runLater(new Runnable() { public void run() {
			loaded = false;
			engine.reload();
		}});
	}
	
	public void loadURL(final String url) {
		Platform.runLater(new Runnable() { public void run() {
			String tmp = toURL(url);
			if(tmp == null)
				tmp = toURL("http://" + url);
			loaded = false;
			engine.load(tmp);
		}});
	}

	private static String toURL(String str) {
		try { return new URL(str).toExternalForm(); }
		catch(MalformedURLException exception) { return null; }
	}
	
    private ChangeListener<String> titleChangeListener = new ChangeListener<String>() {
		public void changed(ObservableValue<? extends String> observable, String oldValue, final String newValue) {
			SwingUtilities.invokeLater(new Runnable() { public void run() {
				// Window Title Set to newValue
			}});
		}
	};
	
   private EventHandler<WebEvent<String>> statusChangedHandler = new EventHandler<WebEvent<String>>() {
		public void handle(final WebEvent<String> event) {
			SwingUtilities.invokeLater(new Runnable() { public void run() {
				// Set label to event.getData()
			}});
		}
	};
	
    private ChangeListener<String> locationChangeListener = new ChangeListener<String>() {
		public void changed(ObservableValue<? extends String> ov, String oldValue, final String newValue) {
			SwingUtilities.invokeLater(new Runnable() { public void run() {
				// Set URL to newValue
			}});
		}
	};
	
    private ChangeListener<Number> progressListener = new ChangeListener<Number>() {
		public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, final Number newValue) {
			SwingUtilities.invokeLater(new Runnable() { public void run() {
				// Set Progress to newValue.intValue()
			}});
		}
	};
	
	private ChangeListener<Worker.State> stateListener = new ChangeListener<Worker.State>() {
		public void changed(ObservableValue<? extends State> observable, State oldValue, State newValue) {
			if(newValue == Worker.State.SUCCEEDED) {
				SwingUtilities.invokeLater(new Runnable() { public void run() {
					loaded = true;
				}});
			}
		}
	};
    
    private ChangeListener<Throwable> exceptionListener = new ChangeListener<Throwable>() {
		public void changed(ObservableValue<? extends Throwable> o, Throwable old, final Throwable value) {
			if(engine.getLoadWorker().getState() != javafx.concurrent.Worker.State.FAILED)
				return;
			
			SwingUtilities.invokeLater(new Runnable() { public void run() {
				String message;
				
				if(value == null)
					message = engine.getLocation() + "\nUnexpected error.";
				else
					message = engine.getLocation() + "\n" + value.getMessage();
				
				JOptionPane.showMessageDialog(WebPanel.this, message, "Loading error...", JOptionPane.ERROR_MESSAGE);
			}});
		}
	};
}
