package com.kylin.tankwar.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import javax.swing.JFrame;

import org.apache.log4j.Logger;

import com.kylin.tankwar.Direction;
import com.kylin.tankwar.jgroups.AsychCommunication;
import com.kylin.tankwar.jgroups.Communication;

public class MainFrame extends JFrame {

	private static final long serialVersionUID = 8165439060971008330L;
	
	public static final int GAME_WIDTH = 800;
	public static final int GAME_HEIGHT = 600;
	
	private static final Logger logger = Logger.getLogger(MainFrame.class);
	
	private Communication comm;
	
	private Tank myTank ;
	
	private boolean isGood ;
	
	List<Tank> tanks = new ArrayList<Tank>();
	
	public MainFrame(String props, String name) {
		
		logger.info("initialize  MainFrame");
		
		initComm(props, name);
		
		initTank();
	}

	private void initTank() {
		
		String id = UUID.randomUUID().toString();
		
		if(comm.getMemberSize() % 2 == 0) {
			isGood = false ;
		} else {
			isGood = true ;
		}
		
		int x = getRandom(GAME_WIDTH - 100);
		int y = getRandom(GAME_HEIGHT - 100);
		
		myTank = new Tank(id, isGood, true, 100, x, y, Direction.STOP, Direction.D);
		tanks.add(myTank);
		logger.info("initialized a Tank, " + myTank.getView());
	}
	
	

	private int getRandom(int max) {
		
		int r = new Random().nextInt(max);
		
		if(r > 50) {
			return r;
		} else {
			return getRandom(max);
		}
	}

	private void initComm(String props, String name) {
		
		logger.info("initialize communication");
		
		comm = new AsychCommunication();
		
		comm.connect(props, name);
	}

}
