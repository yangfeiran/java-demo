package com.chinacloud.yangfeiran;
import java.sql.Connection;  
import java.sql.DriverManager;  
import java.sql.ResultSet;  
import java.sql.SQLException;  
import java.sql.Statement;  
  
public class FetchSize {  
    static final String driver_class  = "oracle.jdbc.driver.OracleDriver";  
    static final String connectionURL = "jdbc:oracle:thin:@172.16.50.24:1521:dev";  
    static final String userID        = "stargate";  
    static final String userPassword  = "123456";  
    public void runTest(int fetchSize) {  
        Connection  con = null;  
        Statement   stmt = null;  
        ResultSet   rset = null;  
        long startTime =System.currentTimeMillis();  
        String  query_string = "SELECT * FROM STARGATE.T_PEOPLE_1000";//test有5万条记录  
        try {  
            Class.forName (driver_class).newInstance();  
            con = DriverManager.getConnection(connectionURL, userID, userPassword);  
            stmt = con.createStatement();  
            stmt.setFetchSize(fetchSize);  
            rset = stmt.executeQuery (query_string);  
            while (rset.next ()) {  
                 String s = rset.getString(1);  
                 rset.getString(2);  
                 rset.getString(3);  
            }  
            rset.close();  
            stmt.close();  
            long endTime =System.currentTimeMillis();  
            System.out.println("fetchsize为"+fetchSize+"---消耗的时间:"+(endTime-startTime));  
        }  catch (SQLException e) {  
            e.printStackTrace();  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
  
    public static void main(String[] args) {  
        FetchSize fetchSize = new FetchSize();  
        fetchSize.runTest(10);  
        fetchSize.runTest(50);  
        fetchSize.runTest(100);  
        fetchSize.runTest(200);  
        fetchSize.runTest(500);  
        fetchSize.runTest(1000);  
    }  
}  