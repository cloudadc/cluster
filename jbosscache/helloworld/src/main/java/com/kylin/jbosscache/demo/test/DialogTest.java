package com.kylin.jbosscache.demo.test;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class DialogTest {

	public static void main(String[] args) {

		JFrame frame = new JFrame("Input Dialog Box Frame");
		JButton button = new JButton("Show Input Dialog Box");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
//				String str = JOptionPane.showInputDialog(null, "Enter some text : ", "Roseindia.net", 1);
//				if (str != null){
//					JOptionPane.showMessageDialog(null, "You entered the text : " + str, "Roseindia.net", 1);
//				} else {
//					JOptionPane.showMessageDialog(null, "You pressed cancel button.", "Roseindia.net", 1);
//				}
				
				JTextField keyField = new JTextField("key");
				JTextField valueField = new JTextField("value");
				Object[] information = { "Enter Key", keyField, "Enter Value", valueField };
				final String btnOk = "OK";
				final String btnCancel = "Cancel";
				Object[] options = { btnOk, btnCancel };
				int userChoice = JOptionPane.showOptionDialog(null, information,
						"Add DataNode", JOptionPane.YES_NO_OPTION,
						JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
				
				if (userChoice == 0) {
					String key = keyField.getText();
					String value = valueField.getText();
					System.out.println(key + " = " + value);
				}
			}
		});
		JPanel panel = new JPanel();
		panel.add(button);
		frame.add(panel);
		frame.setSize(400, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

}
