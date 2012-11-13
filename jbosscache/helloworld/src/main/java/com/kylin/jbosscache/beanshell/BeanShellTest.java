package com.kylin.jbosscache.beanshell;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import bsh.Interpreter;
import bsh.util.JConsole;

public class BeanShellTest extends JFrame {

	private static final long serialVersionUID = -8210724657009671472L;
	
	private JPanel mainPanel;
	
	private void test() {
		
		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());

		JConsole bshConsole = new JConsole();
		Interpreter interpreter = new Interpreter(bshConsole);

		interpreter.println("Hello World");
		Thread t = new Thread(interpreter);
		t.start();
		
		getContentPane().add(new JSplitPane(JSplitPane.VERTICAL_SPLIT, mainPanel, bshConsole));
	}

	public static void main(String[] args) {
		
		new BeanShellTest().test();
	}

}
