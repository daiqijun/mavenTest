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

	
	//��̬�����
	static{
		try {
			//����properties�����ļ�����
			Properties prop = new Properties();
			//��������ļ�������
			InputStream in = DBHelper.class
				.getClassLoader()
					.getResourceAsStream("jdbc.properties");
			//���������ļ�
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

	//��ȡ����
	public static Connection getConn(){
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url, username, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}

	
	//�ر�����
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
			System.out.println("����ʧ��");
		}else{
			System.out.println("���ӳɹ�");
		}
	}

}
