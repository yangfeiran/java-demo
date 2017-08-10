package com.chinacloud.yangfeiran;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class Phoenix {
	public static void createTable(Statement stmt) throws SQLException{
		int n = 0;
			n = stmt.executeUpdate(
					"create table shit (stuid integer not null primary key,age integer,score integer,classid integer)");
	}
	
	public static void selectFromTable(Statement stmt) throws SQLException{
		ResultSet n = null;
			n = stmt.executeQuery(
					"select * from YANGFEIRAN_2 where O_ORDERDATE > '1996-01-02 00:00:00.0' ORDER BY O_ORDERDATE");
			while (n.next()) {
				System.out.println(n.getString(1));
			}
	}
	
	public static void showTables(Connection con ,Statement stmt) throws SQLException{
		DatabaseMetaData databaseMetaData = con.getMetaData();
		ResultSet res = databaseMetaData.getTables(con.getCatalog(), null, null, new String[]{"TABLE"});
		while (res.next()) {
			System.out.println(res.getString("TABLE_NAME"));
		}
	}
	
	public static void main(String[] args) {
		shit.bullshit bull = new shit.bullshit();
		final String PHOENIX_JDBC_DRIVER_NAME = "org.apache.phoenix.jdbc.PhoenixDriver";
		try {
			Class.forName(PHOENIX_JDBC_DRIVER_NAME);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Properties p = new Properties();
		p.setProperty("phoenix.query.timeoutMs", "360");
		Connection con = null;
		try {
			con = DriverManager.getConnection("jdbc:phoenix:172.16.80.37:2181",p);

			con.setAutoCommit(true);
			Statement stmt = con.createStatement();
			stmt.execute("UPSERT INTO SHIT (ID) VALUES ('varchar primary key111')");
//			selectFromTable(stmt);
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
