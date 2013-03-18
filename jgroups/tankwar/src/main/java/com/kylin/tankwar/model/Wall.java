package com.kylin.tankwar.model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Wall {
	
	private int x, y, w, h;

	public Wall(int x, int y, int w, int h) {
		super();
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
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

	public String toString() {
		return "Wall [x=" + x + ", y=" + y + ", w=" + w + ", h=" + h + "]";
	}

}
