package com.kylin.jbosscache.beanshell;

import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;

import bsh.EvalError;
import bsh.Interpreter;

public class BeanShellHelloWorld {

	public static void main(String[] args) throws FileNotFoundException, IOException, EvalError {
		
		JButton button = new JButton("HelloWorld");
        button.addActionListener((ActionListener)new Interpreter().source("actionHandler.bsh") );

        JFrame f = new JFrame();
        f.setTitle("BeanShell Test");
        f.setSize(200, 100);
        f.getContentPane().add( button );
        f.setVisible(true);
	}

}
