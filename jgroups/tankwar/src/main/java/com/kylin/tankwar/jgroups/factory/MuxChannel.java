package com.kylin.tankwar.jgroups.factory;

import org.jgroups.JChannel;
import org.jgroups.Receiver;
import org.jgroups.UpHandler;
import org.jgroups.blocks.mux.MuxUpHandler;
import org.jgroups.blocks.mux.Muxer;
import org.jgroups.conf.ProtocolStackConfigurator;

/**
 * A JGroups channel that uses a MuxUpHandler by default.
 */
public class MuxChannel extends JChannel {
    public MuxChannel(ProtocolStackConfigurator configurator) throws Exception {
        super(configurator);
        this.setUpHandler(new MuxUpHandler());
    }

    @Override
    public void setReceiver(Receiver receiver) {
        super.setReceiver(receiver);
        // If we're using a receiver, we're not interested in using an up handler
        if (receiver != null) {
            super.setUpHandler(null);
        }
    }

    @Override
    public void setUpHandler(UpHandler handler) {
        UpHandler existingHandler = this.getUpHandler();
        if ((existingHandler != null) && (existingHandler instanceof Muxer)) {
            @SuppressWarnings("unchecked")
            Muxer<UpHandler> muxer = (Muxer<UpHandler>) existingHandler;
            muxer.setDefaultHandler(handler);
        } else {
            super.setUpHandler(handler);
        }
    }
}