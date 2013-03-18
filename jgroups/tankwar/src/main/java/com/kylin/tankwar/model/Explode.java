package com.kylin.tankwar.model;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

public class Explode {
	
	private static Toolkit tk = Toolkit.getDefaultToolkit();
	
	private static Image[] imgs = {
		tk.getImage(Explode.class.getClassLoader().getResource("images/0.gif")),
		tk.getImage(Explode.class.getClassLoader().getResource("images/1.gif")),
		tk.getImage(Explode.class.getClassLoader().getResource("images/2.gif")),
		tk.getImage(Explode.class.getClassLoader().getResource("images/3.gif")),
		tk.getImage(Explode.class.getClassLoader().getResource("images/4.gif")),
		tk.getImage(Explode.class.getClassLoader().getResource("images/5.gif")),
		tk.getImage(Explode.class.getClassLoader().getResource("images/6.gif")),
		tk.getImage(Explode.class.getClassLoader().getResource("images/7.gif")),
		tk.getImage(Explode.class.getClassLoader().getResource("images/8.gif")),
		tk.getImage(Explode.class.getClassLoader().getResource("images/9.gif")),
		tk.getImage(Explode.class.getClassLoader().getResource("images/10.gif"))
	};
	
	private int x, y;
	
	private String id;
	
	private boolean isLive = true ;
	
	public boolean isLive() {
		return isLive;
	}

	private int step = 0 ;

	public Explode(int x, int y, String id) {
		super();
		this.x = x;
		this.y = y;
		this.id = id;
	}
	
	public Explode(ExplodeView view) {
		this.x = view.getX();
		this.y = view.getY();
		this.id = view.getId();
	}

	private static boolean init = false;
	
	public void draw(Graphics g) {
		
		if(!init) {
			for (int i = 0; i < imgs.length; i++) {
				g.drawImage(imgs[i], -100, -100, null);
			}			
			init = true;
		}
		
		if(step == imgs.length) {
			isLive = false;
			return;
		}
		
		g.drawImage(imgs[step], x, y, null);
		
		step ++;
	}
	
	public ExplodeView getView() {
		return new ExplodeView(x, y, id);
	}
}
