// Copyright 2017, Closed Source
// Author: Jay Randez

package com.jayrandez.swingext;

import javax.swing.JPanel;

public abstract class Subject<AppClass>
{
	protected AppClass app;
	
	public Subject(AppClass app) {
		this.app = app;
	}
	
	public abstract String getTitle(); // Return a string for default tab name
	public abstract JPanel getView(); // Return a single unique view throughout lifetime
	public abstract void finish();
}
