package com.kylin.jgroups.test;

import com.kylin.jgroups.User;

public interface IRPCInvokeMethod {

	public User getUser(int id, String name, User user);
}
