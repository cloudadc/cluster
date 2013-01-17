package com.kylin.jbosscache.demo.test;

import java.net.URL;

import javax.swing.Icon;
import javax.swing.ImageIcon;

public class IconTest {
	
	URL closeLocation = getClass().getResource("close.gif");
	URL leafLocation = getClass().getResource("leaf.gif");
	
	Icon closedIcon = new ImageIcon(closeLocation);
	Icon openedIcon = new ImageIcon(closeLocation);
	Icon leafIcon = new ImageIcon(leafLocation);

	public static void main(String[] args) {
		
		new IconTest().test();
	}

	private void test() {

		URL closeLocation = getClass().getResource("close.gif");
		URL leafLocation = getClass().getResource("leaf.gif");
		
		Icon closedIcon = new ImageIcon(closeLocation);
		Icon openedIcon = new ImageIcon(closeLocation);
		Icon leafIcon = new ImageIcon(leafLocation);
	}

}
