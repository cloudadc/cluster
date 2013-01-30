package com.kylin.tankwar.jgroups.factory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;

public interface TransportConfiguration extends ProtocolConfiguration{

	boolean isShared();
	
	ExecutorService getDefaultExecutor();

    ExecutorService getOOBExecutor();

    ScheduledExecutorService getTimerExecutor();

    ThreadFactory getThreadFactory();

    Topology getTopology();

    interface Topology {
        String getMachine();
        String getRack();
        String getSite();
    }
}
