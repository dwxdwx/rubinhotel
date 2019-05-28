package rubin.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * 负责连接数据库，产生连接对象
 * @author OO
 *
 */
public class DBConnection {
	private static DBConnection instance = new DBConnection() ;
	private static Connection conn ;
	private DBConnection() {
		// 载入属性文件
		Properties pro = null;
		pro = new Properties() ;
		try {
			pro.load(DBConnection.class.getResourceAsStream("db.proterties"));
			// 连接数据库
			Class.forName(pro.getProperty("driver")) ;
			conn = DriverManager.getConnection(pro.getProperty("connurl"), pro.getProperty("username"), pro.getProperty("userpass")) ;
			System.out.println("connection ok!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static DBConnection getInstance() {
		return instance ;
	}
	public Connection getConn() {
		return conn;
	}
	
	public void closeConn() {
		if(null != conn) {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			conn = null ;
		}
	}
}
