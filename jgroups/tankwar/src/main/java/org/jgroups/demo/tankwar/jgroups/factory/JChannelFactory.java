package org.jgroups.demo.tankwar.jgroups.factory;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;

import javax.management.MBeanServer;

import org.jgroups.Channel;
import org.jgroups.ChannelListener;
import org.jgroups.JChannel;
import org.jgroups.ReceiverAdapter;
import org.jgroups.conf.ProtocolConfiguration;
import org.jgroups.conf.ProtocolStackConfigurator;
import org.jgroups.jmx.JmxConfigurator;
import org.jgroups.protocols.TP;

public class JChannelFactory implements ChannelFactory, ChannelListener, ProtocolStackConfigurator {
		
	private final ProtocolStackConfiguration configuration ;
	private final Map<Channel, String> channels = Collections.synchronizedMap(new WeakHashMap<Channel, String>());
	
	public JChannelFactory(ProtocolStackConfiguration configuration) {
		this.configuration = configuration ;
	}

	public Channel createChannel(String id) throws Exception {
		
		JChannel channel = new MuxChannel(this);
		
		// We need to synchronize on shared transport,
        // so we don't attempt to init a shared transport multiple times
		TP transport = channel.getProtocolStack().getTransport();
		if(transport.isSingleton()) {
			synchronized(transport) {
				init(transport);
			}
		} else {
			init(transport);
		}
		
		channel.setName(configuration.getNodeName() + "/" + id);
		
		TransportConfiguration.Topology topology = configuration.getTransport().getTopology();
		if (topology != null) {
			channel.setAddressGenerator(new TopologyAddressGenerator(channel, topology.getSite(), topology.getRack(), topology.getMachine()));
		}
		
		MBeanServer server = configuration.getMBeanServer();
		if (server != null) {
            try {
                this.channels.put(channel, id);
                JmxConfigurator.registerChannel(channel, server, id);
            } catch (Exception e) {
            	System.out.println(e.getMessage());
            }
            channel.addChannelListener(this);
        }
		
		return channel;
	}
	
	private void init(TP transport) {
		
		TransportConfiguration transportConfig = configuration.getTransport();
		
		ThreadFactory threadFactory = transportConfig.getThreadFactory();
		if (threadFactory != null) {
			if (!(transport.getThreadFactory() instanceof ThreadFactoryAdapter)) {
                transport.setThreadFactory(new ThreadFactoryAdapter(threadFactory));
            }
		}
		
		ExecutorService defaultExecutor = transportConfig.getDefaultExecutor();
		if (defaultExecutor != null) {
            if (!(transport.getDefaultThreadPool() instanceof ManagedExecutorService)) {
                transport.setDefaultThreadPool(new ManagedExecutorService(defaultExecutor));
            }
        }
		
		ExecutorService oobExecutor = transportConfig.getOOBExecutor();
        if (oobExecutor != null) {
            if (!(transport.getOOBThreadPool() instanceof ManagedExecutorService)) {
                transport.setOOBThreadPool(new ManagedExecutorService(oobExecutor));
            }
        }
        
        ScheduledExecutorService timerExecutor = transportConfig.getTimerExecutor();
        if (timerExecutor != null) {
        	
        }
	}

	public ProtocolStackConfiguration getProtocolStackConfiguration() {
		return configuration;
	}

	@Override
	public String getProtocolStackString() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ProtocolConfiguration> getProtocolStack() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void channelConnected(Channel channel) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void channelDisconnected(Channel channel) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void channelClosed(Channel channel) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public JChannel createChannel(String name, String cluster,
			ReceiverAdapter reciever) {
		// TODO Auto-generated method stub
		return null;
	}

}
