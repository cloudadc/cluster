package com.kylin.infinispan.demo;

import com.customized.tools.cli.TreeInputConsole;
import com.customized.tools.cli.TreeNode;

public class InfinispanConsole extends TreeInputConsole {

	public InfinispanConsole() {
		super("Infinispan");
	}

	protected void handleLS(String pointer) {
		for(TreeNode node : getCurrentNode().getSons()){
			println(node.getContent());
		}
	}
	
	protected void handleRM(String pointer) {

	}

	protected void handleADD(String pointer) {

		String key = readString("Enter Key:", true);
		String value = readString("Enter Value:", true);
	}
	
	protected void handleHELP(String pointer) {

	}

	protected void handlePWD(String pointer) {
		handleHELP(pointer);
	}

	protected void handleCD(String pointer) {
		handleHELP(pointer);
	}

	protected void handleOther(String pointer) {
		handleHELP(pointer);
	}
	
	class CachedEntry {
		String key, value, alias;
		long lifespan = -1, maxIdle = -1;

		public CachedEntry(String key, String value) {
			super();
			this.key = key;
			this.value = value;
		}

		public CachedEntry(String key, String value, long lifespan, long maxIdle, String alias) {
			super();
			this.key = key;
			this.value = value;
			this.lifespan = lifespan;
			this.maxIdle = maxIdle;
			this.alias = alias ;
		}

		public boolean equals(Object o) {
			if (this == o)
				return true;
			if (o == null || getClass() != o.getClass())
				return false;

			CachedEntry that = (CachedEntry) o;

			if (lifespan != that.lifespan)
				return false;
			if (maxIdle != that.maxIdle)
				return false;
			if (key != null ? !key.equals(that.key) : that.key != null)
				return false;
			if (value != null ? !value.equals(that.value) : that.value != null)
				return false;

			return true;
		}

		public int hashCode() {
			int result = key != null ? key.hashCode() : 0;
			result = 31 * result + (value != null ? value.hashCode() : 0);
			result = 31 * result + (int) (lifespan ^ (lifespan >>> 32));
			result = 31 * result + (int) (maxIdle ^ (maxIdle >>> 32));
			return result;
		}

		public String toString() {
			return "[key=" + key + ", value=" + value + ", lifespan=" + lifespan + ", maxIdle=" + maxIdle + ", alias=" + alias + "]";
		}
	}

}
