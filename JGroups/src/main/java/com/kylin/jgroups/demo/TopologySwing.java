package com.kylin.jgroups.demo;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Vector;

import org.jgroups.Address;
import org.jgroups.JChannel;
import org.jgroups.ReceiverAdapter;
import org.jgroups.View;


public class TopologySwing extends Frame implements WindowListener{

	private static final long serialVersionUID = 8015481790873125650L;
	
	private final Vector members = new Vector();
	private final Font myFont;
	private final FontMetrics fm;
	private final Color node_color = new Color(250, 220, 100);
	private boolean coordinator = false;
	private static final int NormalStyle = 0;
	private static final int CheckStyle = 1;
    private JChannel channel;
	private Object my_addr = null;
	private static final String channel_name = "FD-Heartbeat";
	
	public TopologySwing() {
		addWindowListener(this);
		fm = getFontMetrics(new Font("Helvetica", Font.PLAIN, 12));
		myFont = new Font("Helvetica", Font.PLAIN, 12);
	}
	
	public void addNode(Object member) {
		Object tmp;
		for (int i = 0; i < members.size(); i++) {
			tmp = members.elementAt(i);
			if (member.equals(tmp))
				return;
		}
		members.addElement(member);
		repaint();
	}

	public void removeNode(Object member) {
		Object tmp;
		for (int i = 0; i < members.size(); i++) {
			tmp = members.elementAt(i);
			if (member.equals(tmp)) {
				members.removeElement(members.elementAt(i));
				break;
			}
		}
		repaint();
	}


    public void drawNode(Graphics g, int x, int y, String label, int style) {
		Color old = g.getColor();
		int width, height;
		width = fm.stringWidth(label) + 10;
		height = fm.getHeight() + 5;

        g.setColor(node_color);

		g.fillRect(x, y, width, height);
		g.setColor(old);
        g.drawString(label, x + 5, y + 15);
        g.drawRoundRect(x - 1, y - 1, width + 1, height + 1, 10, 10);
        if(style == CheckStyle) {
            g.drawRoundRect(x - 2, y - 2, width + 2, height + 2, 10, 10);
            g.drawRoundRect(x - 3, y - 3, width + 3, height + 3, 10, 10);
        }
    }


    public void drawTopology(Graphics g) {
        int x=20, y=50;
        String label;
        Dimension box=getSize();
        Color old=g.getColor();

        if(coordinator) {
            g.setColor(Color.cyan);
            g.fillRect(11, 31, box.width - 21, box.height - 61);
            g.setColor(old);
        }

        g.drawRect(10, 30, box.width - 20, box.height - 60);
        g.setFont(myFont);

        for(int i=0; i < members.size(); i++) {
            label=members.elementAt(i).toString();
            drawNode(g, x, y, label, NormalStyle);
            y+=50;
        }


    }


    public void paint(Graphics g) {
        drawTopology(g);
    }

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void windowClosing(WindowEvent e) {
		setVisible(false);
        channel.close();
	}

	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public void start() throws Exception {
		String props = "udp.xml";

		channel = new JChannel(props);

        channel.setReceiver(new ReceiverAdapter() {
            public void viewAccepted(View view) {
                setInternalState(view.getMembers());
            }

            public void setInternalState(java.util.List<Address> mbrs) {
                members.removeAllElements();
                for(Address mbr: mbrs)
                    addNode(mbr);
                coordinator=mbrs.size() <= 1 || (mbrs.size() > 1 && mbrs.iterator().next().equals(my_addr));
                repaint();
			}
		});

        channel.connect(channel_name);
		my_addr = channel.getAddress();
		if (my_addr != null)
			setTitle(my_addr.toString());
		pack();
		setVisible(true);
    }


    public static void main(String[] args) {
        try {
			TopologySwing top = new TopologySwing();
			top.setLayout(null);
			top.setSize(240, 507);
			top.start();
        }
        catch(Exception e) {
            System.err.println(e);
            e.printStackTrace();
            System.exit(0);
        }
    }

}
