package com.chinacloud.yangfeiran;

import org.hibernate.Hibernate;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class hiveJDBC {
	private static final String JDBC_DRIVER_NAME = "org.apache.hive.jdbc.HiveDriver";

	static {
		try {
			Class.forName(JDBC_DRIVER_NAME);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws Exception {

		Connection con = null;
		con = DriverManager.getConnection("jdbc:hive2://172.16.50.81:10000/chinacloud", "hdfs", "");
		Statement stmt = con.createStatement();

		ResultSet res = stmt.executeQuery("select * from chinacloud.parquet_all2");
//		ResultSet res = stmt.executeQuery("select * from default.external_table_1 limit 1");
		int i = 3;
		while (res.next()) {
			InputStream s = res.getBinaryStream("col2");
			Blob b = Hibernate.createBlob(s);
			String filepath = "C:\\Users\\Administrator\\Desktop\\" + i++ +".png";
			System.out.println("输出文件路径为:" + filepath);
			FileOutputStream out = new FileOutputStream(filepath);
			int len = (int) b.length();
			byte buffer[] = new byte[len];
			while ((len = s.read(buffer)) != -1) {
				out.write(buffer, 0, len);
			}
			out.close();
			s.close();
			
//			byte[] b  = res.getByte(1);
//			Blob blob = Hibernate.createBlob(b);
//			InputStream input = blob.getBinaryStream();
		}
		System.out.println("shit");

	}
}
