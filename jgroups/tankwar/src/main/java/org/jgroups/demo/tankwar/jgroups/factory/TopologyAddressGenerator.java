package org.jgroups.demo.tankwar.jgroups.factory;

import org.jgroups.Address;
import org.jgroups.Channel;
import org.jgroups.stack.AddressGenerator;
import org.jgroups.util.TopologyUUID;

/**
 * An AddressGenerator which generates TopologyUUID addresses with specified site, rack and machine ids.
 * 
 * @author kylin
 *
 */
public class TopologyAddressGenerator implements AddressGenerator{
	
	private final Channel channel;
    private final String site_id;
    private final String rack_id;
    private final String machine_id;

	public TopologyAddressGenerator(Channel channel, String site_id, String rack_id, String machine_id) {
		super();
		this.channel = channel;
		this.site_id = site_id;
		this.rack_id = rack_id;
		this.machine_id = machine_id;
	}

	public Address generateAddress() {
		return TopologyUUID.randomUUID(channel.getName(), site_id, rack_id, machine_id);
	}

}
