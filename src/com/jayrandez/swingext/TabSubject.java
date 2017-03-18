package com.jayrandez.swingext;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;

public class TabSubject<AppClass> extends Subject<AppClass>
{
	private TabView view;
	
	public TabSubject(AppClass app) {
		super(app);
		this.view = new TabView();
		
		this.view.closeButton.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent ev) {
			view.tabPane.removeTabAt(view.tabPane.getSelectedIndex());
		}});
		
		this.view.leftButton.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent ev) {
			int index = view.tabPane.getSelectedIndex();
			if(index < 1)
				return;
			
			moveTab(index, index - 1);
		}});
		
		this.view.rightButton.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent ev) {
			int index = view.tabPane.getSelectedIndex();
			if(index >= view.tabPane.getTabCount() - 1)
				return;
			
			moveTab(index, index + 1);
		}});
	}

	public JPanel getView() {
		return view;
	}
	
	private void moveTab(int fromIndex, int toIndex) {
		String title = view.tabPane.getTitleAt(fromIndex);
		Component component = view.tabPane.getComponentAt(fromIndex);
		view.tabPane.remove(fromIndex);
		
		view.tabPane.insertTab(title, null, component, null, toIndex);
		view.tabPane.setSelectedIndex(toIndex);
	}
	
	public void addTab(JPanel panel, String label) {
		view.tabPane.addTab((label == null) ? "Unnamed Tab" : label, panel);
		view.tabPane.setSelectedComponent(panel);
	}

	public void removeTab(JPanel panel) {
		int index = view.tabPane.indexOfComponent(panel);
		view.tabPane.removeTabAt(index);
	}
	
	public void renameTab(JPanel panel, String label) {
		int index = view.tabPane.indexOfComponent(panel);
		view.tabPane.setTitleAt(index, label);
	}
	
	public void finish() {}

	public String getTitle() {
		return null;
	}
}
