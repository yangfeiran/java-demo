package com.chinacloud.yangfeiran;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class OracleTest {
	public static void main(String[] args) throws Exception {

		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@172.16.50.24:1521:dev", "stargate", "123456");
			Statement st = conn.createStatement();
			rs = st.executeQuery("select COL2 from STARGATE.TEST4");
			while (rs.next()) {
				Blob blob = rs.getBlob("COL2");
				String filepath = "C:\\Users\\Administrator\\Desktop\\3.png";
				System.out.println("输出文件路径为:" + filepath);

				InputStream input = blob.getBinaryStream();
				FileOutputStream out = new FileOutputStream(filepath);
				int len = (int) blob.length();
				byte buffer[] = new byte[len];
				while ((len = input.read(buffer)) != -1) {
					out.write(buffer, 0, len);
				}
				out.close();
				input.close();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null)
				rs.close();
			if (stmt != null)
				stmt.close();
			if (conn != null)
				conn.close();
		}
	}
}