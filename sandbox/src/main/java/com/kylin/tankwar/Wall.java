package com.kylin.tankwar;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.io.Serializable;

import org.apache.log4j.Logger;

public class Wall implements Serializable{
	int x, y, w, h;
	TankFrame tc ;
	
	private static final Logger logger = Logger.getLogger(Wall.class);
	
	public Wall(int x, int y, int w, int h, TankFrame tc) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.tc = tc;
		
		logger.info("initialize a Wall instance, [x= " + x + ", y= " + y + ", w= " + w + ", h= " + h + "]");
	}
	
	public void draw(Graphics g) {
		Color c = g.getColor();
		g.setColor(Color.DARK_GRAY);
		g.fillRect(x, y, w, h);
		g.setColor(c);
	}
	
	public Rectangle getRect() {
		return new Rectangle(x, y, w, h);
	}
}
