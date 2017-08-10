package com.chinacloud.yangfeiran;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ImpalaTest {
	static String JDBC_DRIVER = "org.apache.hive.jdbc.HiveDriver";
    static String CONNECTION_URL = "jdbc:hive2://172.16.50.81:21050/default;auth=noSasl";

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Connection con = null;
		 
		try {
 
			Class.forName(JDBC_DRIVER);
			con = DriverManager.getConnection(CONNECTION_URL,"hdfs","");
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("show tables");
			System.out.println("\n== Begin Query Results ======================");
			// print the results to the console
			while (rs.next()) {
				System.out.println(rs.getString(1));
			}
 
			System.out.println("== End Query Results =======================\n\n");
 
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (Exception e) {
			}
		}
	}

}
