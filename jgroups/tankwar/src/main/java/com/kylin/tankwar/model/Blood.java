package com.kylin.tankwar.model;
import java.awt.*;

import org.apache.log4j.Logger;

import com.kylin.tankwar.core.MainFrame;

public class Blood {
	
	private int x, y, w, h;
	
//	private static final Logger logger = Logger.getLogger(Blood.class);
	
	int step = 0;
	private boolean live = true;
	
	Point[] array = null;
	
	MainFrame mainFrame;
	
	public Blood() {
		
	}
	
	public Blood(MainFrame mainFrame) {
		initArray() ;
		x = array[0].x;
		y = array[0].y;
		w = h = 15;
		this.mainFrame = mainFrame;
		
//		logger.info("initialize a Blood instance");
	}
	
	private void initArray() {

		Point[] tmp = new Point[75];
		array = new Point[150];
		
		for(int i = 0 ; i < 75 ; i ++){
			Point p = new Point(350 + i, 300 - i);
			tmp[i] = p;
			array[i] = p;
		}
		
		for(int i = 75 ; i > 0 ; i --) {
			array[150 - i] = tmp[i - 1];
		}
	}

	private int count = 0 ;
	
	public void draw(Graphics g) {
	
		if(!live) {
			
			count ++ ;
			
			if(count == 1000) {
				count = 0;
				live = true;
				mainFrame.getComm().replicateBlood(getBooldView());
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
		if(step == array.length){
			step = 0;
		}
		x = array[step].x ;
		y = array[step].y ;
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
	
	public BloodView getBooldView() {
		return new BloodView(step, live);
	}
	
	public void updateBlood(BloodView view) {
		this.step = view.getStep();
		this.live = view.isLive();		
	}
	
}
