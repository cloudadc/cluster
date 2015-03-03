package org.jgroups.demo.test.blocks;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.jgroups.util.FutureListener;
import org.jgroups.util.RspList;

/**
 * 
 * @author kylin
 *
 */
public class MyFutureListener implements FutureListener<RspList<Content>> {

	public void futureDone(Future<RspList<Content>> future) {
		
		try {
			RspList<Content> list = future.get();
			for(Content c : list.getResults()) {
				System.out.println("   " + c);
			}
			System.out.println();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
	}
}
