package com.chinacloud.yangfeiran;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class Kylin {
	public void createTable(Statement stmt) throws SQLException{
		int n = 0;
			n = stmt.executeUpdate(
					"create table student (stuid integer,age integer,score integer,classid integer)");
	}
	
	public static void showTables(Connection con ,Statement stmt) throws SQLException{
		DatabaseMetaData databaseMetaData = con.getMetaData();
		ResultSet res = databaseMetaData.getTables(con.getCatalog(), null, null, new String[]{"TABLE"});
		while (res.next()) {
			System.out.println(res.getString("TABLE_NAME"));
		}
		ResultSet tables = con.getMetaData().getTables(null, null, "dummy", null);
		while (tables.next()) {
		    for (int i = 0; i < 10; i++) {
		    	System.out.println( tables.getString(i + 1));
		    }
		}
	}
	
	public static void main(String[] args) throws Exception {
		final String KYLIN_JDBC_DRIVER_NAME = "org.apache.kylin.jdbc.Driver";

		Class.forName(KYLIN_JDBC_DRIVER_NAME).newInstance();
	
		Connection con = null;
		Properties info = new Properties();
		try {
			info.put("user", "ADMIN");
			info.put("password", "KYLIN");
			con = DriverManager.getConnection("jdbc:kylin://172.16.80.70:7070/learn_kylin",info);

			con.setAutoCommit(true);
			Statement stmt = con.createStatement();
			showTables(con,stmt);
			con.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// while (res.next()) {
		// System.out.println(res.getString(1));
		// }
		
		System.out.println("shit");
	}
}
