package com.kylin.tankwar;
import java.awt.*;

import org.apache.log4j.Logger;

public class Blood {
	int x, y, w, h;
	TankFrame tc; 
	
	private static final Logger logger = Logger.getLogger(Blood.class);
	
	int step = 0;
	private boolean live = true;
	
	private int[][] pos = {
			          {350, 300}, {355, 295}, {360, 290}, {365, 285}, {370, 280}, {375, 275}, {380, 270}, {385, 265}, {390, 260}, {395, 255}, {400, 250}, {405, 245}, {410, 240}, {415, 235},
			          {415, 235}, {410, 240}, {405, 245}, {400, 250}, {395, 255}, {390, 260}, {385, 265}, {380, 270}, {375, 275}, {370, 280}, {365, 285}, {360, 290}, {355, 295}, {350, 300}
					  };
	
	public Blood() {
		x = pos[0][0];
		y = pos[0][1];
		w = h = 15;
		
		logger.info("initialize a Blood instance");
	}
	
	public void draw(Graphics g) {
		if(!live) return;
		
		Color c = g.getColor();
		g.setColor(Color.MAGENTA);
		g.fillRect(x, y, w, h);
		g.setColor(c);
		
		move();
	}

	private void move() {
		step ++;
		if(step == pos.length){
			step = 0;
		}
		x = pos[step][0];
		y = pos[step][1];
	}
	
	public Rectangle getRect() {
		return new Rectangle(x, y, w , h);
	}

	public boolean isLive() {
		return live;
	}

	public void setLive(boolean live) {
		this.live = live;
	}
	
}
