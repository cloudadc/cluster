package org.infinispan.loaders.test;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCTest {

	public static void main(String[] args) throws SQLException {

		Connection conn = null;
		Statement stmt = null;
		ResultSet r = null;
		final String driver = "org.h2.Driver";
		final String url = "jdbc:h2:mem:jdbctest;DB_CLOSE_DELAY=-1";
		final String user = "sa";
		final String passwd = "";

		try {
			Class c = Class.forName(driver);
			Driver d = (Driver) c.newInstance();
			DriverManager.registerDriver(d);
			conn = DriverManager.getConnection(url, user, passwd);
			System.out.println(conn);
			// stmt = conn.createStatement();
			// r =
			// stmt.executeQuery("SELECT USER_TABLES.TABLE_NAME FROM USER_TABLES");
			// while(r.next()) {
			// System.out.println(r.getString(1));
			// }
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// r.close();
			// stmt.close();
			conn.close();
		}
	}

}
