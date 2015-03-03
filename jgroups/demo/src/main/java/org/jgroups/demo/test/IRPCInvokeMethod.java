package org.jgroups.demo.test;

import org.jgroups.demo.User;

public interface IRPCInvokeMethod {

	public User getUser(int id, String name, User user);
}
