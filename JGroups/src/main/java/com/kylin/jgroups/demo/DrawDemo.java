package com.kylin.jgroups.demo;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.management.MBeanServer;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.apache.log4j.Logger;
import org.jgroups.Address;
import org.jgroups.Channel;
import org.jgroups.ChannelListener;
import org.jgroups.JChannel;
import org.jgroups.MergeView;
import org.jgroups.Message;
import org.jgroups.View;
import org.jgroups.jmx.JmxConfigurator;
import org.jgroups.util.Util;

import com.kylin.jgroups.DemoBase;

public class DrawDemo extends DemoBase implements ActionListener, ChannelListener{

	/**
	 * DrawDemo cluster name
	 */
	private String clusterName = "DrawCluster";
	
	private boolean no_channel = false;
	
	private boolean jmx = true;
	
	private boolean use_state = false;
	
	private long state_timeout = 5000;
	
	/**
	 * Whether use unicast, default false emans use multicast
	 */
	private boolean use_unicasts = false;
	
	/**
	 * DrawDemo main Channel
	 */
	private Channel channel = null;
	
	/**
	 * DrawDemo main Frame
	 */
	private JFrame mainFrame = null;
	
	/**
	 * DrawDemo main panel
	 */
	private DrawPanel panel = null;
	
	/**
	 * DrawDemo button panel
	 */
	private JPanel sub_panel = null;
	
	/**
	 * DrawDemo clear and leave button
	 */
	private JButton clear_button, leave_button;
	
	private final Font default_font = new Font("Helvetica", Font.PLAIN, 12);
	
	private static final Color background_color = Color.white;
	
	private int member_size = 1;
	
	private final Random random = new Random(System.currentTimeMillis());
	
	private final Color draw_color = selectColor();
	
	private final List<Address> members = new ArrayList<Address>();
	
	private static final Logger logger = Logger.getLogger(DrawDemo.class);
	
	public DrawDemo() {
		
	}
	
	public DrawDemo(Channel channel) throws Exception{
		this.channel = channel;
		channel.setReceiver(this);
		channel.addChannelListener(this);
	}
	
	
	public void init(String[] args) throws Exception {
		
		String props = "udp.xml";
		String name = null;
		
		 for(int i=0; i < args.length; i++) {
			 
			if ("-help".equals(args[i]) || "-h".equals(args[i])) {
				help();
				return;
			}
			
			if ("-c".equals(args[i])) {
				clusterName = args[++i];
				continue;
			}
	            
			if ("-p".equals(args[i])) {
				props = args[++i];
				continue;
			}
			
			if ("-n".equals(args[i])) {
				name = args[++i];
				continue;
			}
			
			if ("-t".equals(args[i])) {
				state_timeout = Long.parseLong(args[++i]);
				continue;
			}
			
			if("-b".equals(args[i])) {
                System.setProperty("jgroups.bind_addr", args[++i]);
                continue;
            }
			
			if ("-no_channel".equals(args[i])) {
				no_channel = true;
				continue;
			}
			
			if ("-jmx".equals(args[i])) {
				jmx = true;
				continue;
			}

			if ("-state".equals(args[i])) {
				use_state = true;
				continue;
			}

			if ("-use_unicasts".equals(args[i])) {
				use_unicasts = true;
				continue;
			}
	           
			help();
		}


		logger.info("Create Channel with name " + name);
		channel=new JChannel(props);
		
		if(name != null){
			channel.setName(name);
		}
		
		channel.setReceiver(this);
        channel.addChannelListener(this);
        
        if (!no_channel && !use_state) {
			logger.info("connect to " + clusterName);
			channel.connect(clusterName);
		}
		
		if(!no_channel && use_state) {
			logger.info("connect to " + clusterName + ", use_state = " + use_state);
            channel.connect(clusterName, null, state_timeout);
        }
		
	}

	private void help() {
		
		System.out.println("Run Application with [-p <props>] [-n <name>] [-c <clusterName>] [-b <bind_address>] [-t <state_timeout>]");
		System.out.println("                     [-no_channel] [-jmx] [-state] [-use_unicasts]");
		Runtime.getRuntime().exit(1);
	}

	public static void main(String[] args) throws Exception {

		DrawDemo demo = new DrawDemo();
		demo.start(args);
		
//		demo.help();
	}
	
	public void start(String[] args) throws Exception {
		
		init(args);
		
		launchFrame();
	}
	
	private void launchFrame() throws Exception {

		logger.info("launch Frame Start");
		
		
		
		mainFrame = new JFrame();
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		panel = new DrawPanel(use_state);
		panel.setBackground(background_color);
		
		mainFrame.getContentPane().add("Center", panel);
		
		clear_button = new JButton("Clear");
		clear_button.setFont(default_font);
		clear_button.addActionListener(this);
		clear_button.setForeground(Color.blue);
		
		leave_button = new JButton("Leave");
		leave_button.setFont(default_font);
		leave_button.addActionListener(this);
		leave_button.setForeground(Color.blue);

		sub_panel = new JPanel();
		sub_panel.add("South", clear_button);
        sub_panel.add("South", leave_button);
        
        mainFrame.getContentPane().add("South", sub_panel);
        mainFrame.setBackground(background_color);
        
        mainFrame.pack();
        mainFrame.setLocation(100, 100);
        mainFrame.setBounds(new Rectangle(500, 350));
        mainFrame.setVisible(true);
        setTitle(null);
	}

	private void setTitle(String title) {

		String tmp = " Draw Demo ";
		
		if (no_channel) {
			mainFrame.setTitle(tmp);
			return;
		}
		
		if (title != null) {
			mainFrame.setTitle(title);
		} else {
			mainFrame.setTitle(tmp + channel.getName());
		}
	}

	public void channelConnected(Channel channel) {
		
		logger.info("Channel Connected");
		
		logChannel(channel);
		
		if(jmx) {
			logger.info("Register " + channel.getName() + " to JGroups JMX MBean Server");
            Util.registerChannel((JChannel)channel, "jgroups");
        }
	}

	public void channelDisconnected(Channel channel) {
		
		logger.info("Channel Disconnected");
		
		logChannel(channel);
		
		if(jmx) {
            MBeanServer server=Util.getMBeanServer();
            if(server != null) {
                try {
                    JmxConfigurator.unregisterChannel((JChannel)channel,server, clusterName);
                }
                catch(Exception e) {
                    logger.error(e);
                }
            }
        }
	}

	public void channelClosed(Channel channel) {
		
		logger.info("Channel Closed");
		
		logChannel(channel);
	}

	private void logChannel(Channel channel) {
		
		try {
			logger.debug("----------");
			logger.debug("Channel Name: " + channel.getName());
			logger.debug("Channel Address: " + channel.getAddress());
			logger.debug("Channel ClusterName: " + channel.getClusterName());
			logger.debug("Channel View: " + channel.getView());
			logger.debug("  -> View Id: " + channel.getView().getViewId());
			logger.debug("  -> View Creater: " + channel.getView().getCreator());
			logger.debug("  -> View Coordinator: " + channel.getView().getMembers().get(0));
			logger.debug("  -> View Memembers: " + channel.getView().getMembers());
			logger.debug("-----------");
		} catch (Exception e) {
			
		}
	}

	public void actionPerformed(ActionEvent e) {

		String command = e.getActionCommand();
        if("Clear".equals(command)) {
            if(no_channel) {
                clearPanel();
                return;
            }
            sendClearPanelMsg();
		} else if ("Leave".equals(command)) {
            stop();
		} else {
			System.out.println("Unknown action");
		}
	}
	
	
	private void clearPanel() {
		
		if(panel != null){
			panel.clear();
		}
	}

	private void sendClearPanelMsg() {

		DrawDemoCommand comm = new DrawDemoCommand(DrawDemoCommand.CLEAR);

        try {
            byte[] buf=Util.streamableToByteBuffer(comm);
            if(use_unicasts)
                sendToAll(buf);
            else
                channel.send(new Message(null, null, buf));
        }
        catch(Exception ex) {
            System.err.println(ex);
        }
	}
	
	private void sendToAll(byte[] buf) throws Exception {
		
		for (Address mbr : members) {
			Message msg = new Message(mbr, null, buf);
			channel.send(msg);
		}
	}

	private void stop() {

		if (!no_channel) {
			try {
				channel.close();
			} catch (Exception ex) {
				logger.error(ex);
			}
		}
		mainFrame.setVisible(false);
		mainFrame.dispose();
	}

	private Color selectColor() {
		
		int red=Math.abs(random.nextInt()) % 255;
        int green=Math.abs(random.nextInt()) % 255;
        int blue=Math.abs(random.nextInt()) % 255;
        return new Color(red, green, blue);
	}

	public void viewAccepted(View view) {
		
		logger.info("View Acceepted");
		
		member_size = view.size();
		
        if(mainFrame != null){
        	setTitle(null);
        }
        
        members.clear();
        members.addAll(view.getMembers());

        if(view instanceof MergeView) {
            System.out.println("** MergeView = " + view);

			if (use_state && !members.isEmpty()) {
				Address coord = members.get(0);
				Address local_addr = channel.getAddress();
				if (local_addr != null && !local_addr.equals(coord)) {
					try {
						System.out.println("fetching state from " + coord);
						channel.getState(coord, 5000);
					} catch (Exception e) {
						logger.error(e);
					}
				}
			}
		} else {
			System.out.println("** View = " + view);
		}
	}

	public void receive(Message msg) {
		
		logger.info("Recieve Message");
		
		byte[] buf = msg.getRawBuffer();
        if(buf == null) {
            logger.warn("[" + channel.getAddress() + "] received null buffer from " + msg.getSrc() + ", headers: " + msg.printHeaders());
            return;
        }

        try {
			DrawDemoCommand comm = (DrawDemoCommand)Util.streamableFromByteBuffer(DrawDemoCommand.class, buf, msg.getOffset(), msg.getLength());
            switch(comm.mode) {
                case DrawDemoCommand.DRAW:
                    if(panel != null)
                        panel.drawPoint(comm);
                    break;
                case DrawDemoCommand.CLEAR:
                    clearPanel();
                    break;
                default:
                	logger.warn("***** received invalid draw command " + comm.mode);
                    break;
            }
        }
        catch(Exception e) {
           logger.error(e);
        }
	}

	public void getState(OutputStream output) throws Exception {
		panel.writeState(output);
	}

	public void setState(InputStream input) throws Exception {
		panel.readState(input);
	}


	private class DrawPanel extends JPanel implements MouseMotionListener {
		
		private Dimension d; 
		private Dimension imgsize = null;
		private Image img = null;
		private Graphics gr = null;
		
		final Map<Point,Color>  state;
		
		public DrawPanel(boolean use_state) {
			
            if(use_state){
            	state=new LinkedHashMap<Point,Color>();
			} else {
				state=null;
			}
                
            createOffscreenImage(false);
            addMouseMotionListener(this);
            addComponentListener(new ComponentAdapter() {
                public void componentResized(ComponentEvent e) {
                    if(getWidth() <= 0 || getHeight() <= 0) return;
                    createOffscreenImage(false);
                }
            });
        }

		public void readState(InputStream input) throws IOException {

			DataInputStream in = new DataInputStream(input);
			Map<Point, Color> new_state = new HashMap<Point, Color>();
			int num = in.readInt();
			for (int i = 0; i < num; i++) {
				Point point = new Point(in.readInt(), in.readInt());
				Color col = new Color(in.readInt());
				new_state.put(point, col);
			}

			synchronized (state) {
				state.clear();
				state.putAll(new_state);
				logger.info("read state: " + state.size() + " entries");
				createOffscreenImage(true);
			}
		}

		public void writeState(OutputStream output) throws IOException {

			 if(state == null){
				 return;
			 }
			 
			synchronized (state) {
				DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(output, 4096));
				dos.writeInt(state.size());
				for (Map.Entry<Point, Color> entry : state.entrySet()) {
					Point point = entry.getKey();
					Color col = entry.getValue();
					dos.writeInt(point.x);
					dos.writeInt(point.y);
					dos.writeInt(col.getRGB());
				}
				dos.flush();
			}
		}

		public void drawPoint(DrawDemoCommand c) {

			if (c == null || gr == null) {
				return;
			}
			
			Color col = new Color(c.r, c.g, c.b);
			gr.setColor(col);
			gr.fillOval(c.x, c.y, 10, 10);
			repaint();
			
			if (state != null) {
				synchronized (state) {
					state.put(new Point(c.x, c.y), col);
				}
			}
		}

		public void clear() {

			if (gr == null) {
				return;
			}
			
			gr.clearRect(0, 0, getSize().width, getSize().height);
			repaint();
			
			if (state != null) {
				synchronized (state) {
					state.clear();
				}
			}
		}

		private void createOffscreenImage(boolean discard_image) {

			d = getSize();
			
			if (discard_image) {
				img = null;
				imgsize = null;
			}
			
            if(img == null || imgsize == null || imgsize.width != d.width || imgsize.height != d.height) {
				img = createImage(d.width, d.height);
				if (img != null) {
					gr = img.getGraphics();
					if (gr != null && state != null) {
						drawState();
					}
				}
				imgsize = d;
			}
			repaint();
		}

		private void drawState() {

			Map.Entry entry;
			Point pt;
			Color col;
			synchronized (state) {
				for (Iterator it = state.entrySet().iterator(); it.hasNext();) {
					entry = (Map.Entry) it.next();
					pt = (Point) entry.getKey();
					col = (Color) entry.getValue();
					gr.setColor(col);
					gr.fillOval(pt.x, pt.y, 10, 10);

				}
			}
			repaint();
		}

		public void mouseDragged(MouseEvent e) {
			
			logger.debug("Mouse Dragged");
			
			int x = e.getX();
			int y = e.getY();
			
			DrawDemoCommand comm = new DrawDemoCommand(DrawDemoCommand.DRAW, x, y, draw_color.getRed(), draw_color.getGreen(), draw_color.getBlue());

			if (no_channel) {
				drawPoint(comm);
				return;
			}

	        try {
				byte[] buf = Util.streamableToByteBuffer(comm);
	            
	            if(use_unicasts){
	            	sendToAll(buf);
				} else {
					channel.send(new Message(null, null, buf));
				}
	                
			} catch (Exception ex) {
				logger.error(ex);
			}
		}

		public void mouseMoved(MouseEvent e) {
			
		}
		
	}
	
}
