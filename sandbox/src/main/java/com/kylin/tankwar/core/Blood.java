package com.kylin.tankwar.core;
import java.awt.*;

import org.apache.log4j.Logger;

public class Blood {
	
	private int x, y, w, h;
	
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
	
	private int count = 0 ;
	
	public void draw(Graphics g) {
	
		if(!live) {
			
			count ++ ;
			
			if(count == 100) {
				count = 0;
				live = true;
			}
			
			return;
		}
		
		Color c = g.getColor();
		g.setColor(Color.WHITE);
		g.fillOval(x, y, 25, 25);
		g.setColor(Color.RED);
		g.fillRect(x + 5, y + 10, 15, 5);
		g.fillRect(x + 10, y + 5, 5, 15);
		g.setColor(c);
		
		move();
	}

	public int getStep() {
		return step;
	}

	public void setStep(int step) {
		this.step = step;
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
