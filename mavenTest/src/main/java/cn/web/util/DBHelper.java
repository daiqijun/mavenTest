package cn.web.util;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class DBHelper {
	private static String driver;
	private static String url;
	private static String username;
	private static String password;

	
	//静态代码块
	static{
		try {
			//创建properties配置文件对象
			Properties prop = new Properties();
			//获得配置文件输入流
			InputStream in = DBHelper.class
				.getClassLoader()
					.getResourceAsStream("jdbc.properties");
			//加载配置文件
			prop.load(in);
			driver = prop.getProperty("driver");
			url = prop.getProperty("url");
			username = prop.getProperty("username");
			password = prop.getProperty("password");
			Class.forName(driver);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//获取连接
	public static Connection getConn(){
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url, username, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}

	
	//关闭连接
	public static void closeAll(ResultSet rs,PreparedStatement pstm,Connection conn){
		try {
			if(rs!=null){
				rs.close();
			}
			if(pstm!=null){
				pstm.close();
			}
			if(conn!=null){
				conn.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		Connection conn = DBHelper.getConn();
		if(conn==null){
			System.out.println("连接失败");
		}else{
			System.out.println("连接成功");
		}
	}

}
