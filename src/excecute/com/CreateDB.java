package excecute.com;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.lang.*;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import skeleton.ocm.BasicMethods;
import skeleton.ocm.Youtube;

public class CreateDB {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
	
		//extractData();		System.out.println("lol");

	}
public static Connection CreateDBMethod(String fileName) {
	
		
		Connection conn=null;
		try {	Youtube youtube= new Youtube();
			String url= youtube.getSQLiteDBTagVal()+fileName+".db";
			conn= DriverManager.getConnection(url);
			if(conn!=null) {
				DatabaseMetaData meta = conn.getMetaData();
				System.out.println("the driver name is "+meta.getDriverName());
				System.out.println("a new database has been created");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
				
		return conn;		
	}
public static Connection createTable(String tableName) {
	Connection conn=null;
	  String sql = "CREATE TABLE IF NOT EXISTS songs (\n"
              + "    id integer PRIMARY KEY,\n"
              + "    singer text NOT NULL,\n"
              + "    song text NOT NULL\n"
              + ");";
	  try {
		 conn= CreateDBMethod("shaiDB");
		Statement stm= conn.createStatement();
	
		stm.execute(sql);
	} catch (Exception e) {
		// TODO: handle exception
		System.out.println(e.getMessage());
	}
	  return conn;

}
public static void insertData() {
	String sql="INSERT INTO 'songs' ('id','singer','song')\r\n" + 
			"VALUES('1','singer','song');";
	 try {
			Connection conn= createTable("songs");
			Statement stm= conn.createStatement();
		
			stm.execute(sql);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
}

}
