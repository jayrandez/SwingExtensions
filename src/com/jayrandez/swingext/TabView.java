package com.jayrandez.swingext;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.EmptyBorder;

public class TabView extends JPanel
{
	protected JTabbedPane tabPane;
	protected JButton leftButton;
	protected JButton rightButton;
	protected JButton closeButton;
	
	public TabView() {
		this.tabPane = new JTabbedPane();
		tabPane.setBorder(new EmptyBorder(0, 10, 10, 10));
		
		this.setLayout(new BorderLayout());
		
		JPanel header = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		this.leftButton = new JButton("<");
		this.rightButton = new JButton(">");
		this.closeButton = new JButton("X");
		
		leftButton.setPreferredSize(new Dimension(41, 20));
		rightButton.setPreferredSize(new Dimension(41, 20));
		closeButton.setPreferredSize(new Dimension(45, 20));
		
		header.add(leftButton);
		header.add(rightButton);
		header.add(Box.createHorizontalStrut(20));
		header.add(closeButton);
		header.add(Box.createHorizontalStrut(1));
		
		this.add(header, BorderLayout.NORTH);
		this.add(tabPane, BorderLayout.CENTER);
	}
}
