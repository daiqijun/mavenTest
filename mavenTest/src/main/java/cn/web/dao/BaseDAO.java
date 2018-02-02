package cn.web.dao;


import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cn.web.util.DBHelper;



public class BaseDAO<T> {
	
	//查询通用方法
	public List<T> Query(String sql,Object[] params,Class clazz){
		//创建list(用作返回值)
		List<T> list = new ArrayList<T>();
		//获得链接
		Connection con = null;
		//PreparedStatement 对象
		PreparedStatement pstm = null;
		//结果集
		ResultSet rs = null;
		try {
			con = DBHelper.getConn();
			pstm= con.prepareStatement(sql);
			//设置参数
			for (int i = 0; i < params.length; i++) {
				pstm.setObject(i+1, params[i]);
			}
			//调用查询的方法，返回结果集
			rs = pstm.executeQuery();
			//获取rs里面的数据
			ResultSetMetaData data = rs.getMetaData();
			//获取列数
			int count = data.getColumnCount();
			while (rs.next()) {
				T t = (T)clazz.newInstance();
				for (int i = 0; i < count; i++) {
					//获取列名(实体类的属性名)(下标从1开始) 
					String column = data.getColumnName(i+1);
					//拼接set方法id - > setId name-->setName
					String columnStr = "set"+column.substring(0,1).toUpperCase()+column.substring(1);
					//获取实体类属性信息
					Field field = clazz.getDeclaredField(column);
					//设置调用的方法
					Method method = clazz.getMethod(columnStr, field.getType());
					//执行调用方法
					method.invoke(t, rs.getObject(column));
				}
				list.add(t);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			//关闭
			DBHelper.closeAll(rs, pstm,con);
		}
		return list;
	}
	/**查询单个*/
	public T getOnly(String sql,Object[] params,Class clazz){
		T t = null;
		//获得链接
		Connection con = null;
		//PreparedStatement 对象
		PreparedStatement pstm = null;
		//结果集
		ResultSet rs = null;
		try {
			con = DBHelper.getConn();
			pstm= con.prepareStatement(sql);
			//设置参数
			for (int i = 0; i < params.length; i++) {
				pstm.setObject(i+1, params[i]);
			}
			//调用查询的方法，返回结果集
			rs = pstm.executeQuery();
			//获取rs里面的数据
			ResultSetMetaData data = rs.getMetaData();
			//获取列数
			int count = data.getColumnCount();
			if (rs.next()) {
				t = (T)clazz.newInstance();
				for (int i = 0; i < count; i++) {
					//获取列名(实体类的属性名)(下标从1开始) 
					String column = data.getColumnName(i+1);
					//拼接set方法id - > setId name-->setName
					String columnStr = "set"+column.substring(0,1).toUpperCase()+column.substring(1);
					//获取实体类属性信息
					Field field = clazz.getDeclaredField(column);
					//设置调用的方法
					Method method = clazz.getMethod(columnStr, field.getType());
					//执行调用方法
					method.invoke(t, rs.getObject(column));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			//关闭
			DBHelper.closeAll(rs, pstm,con);
		}
		return t;
	}
	
	
	//增删改
	public void update(String sql,Object[] params,Connection conn){
		PreparedStatement pstm = null;
		try {
			pstm = conn.prepareStatement(sql);
			for (int i = 0; i < params.length; i++) {
				pstm.setObject(i+1, params[i]);
			}
			//executeUpdate 执行增删改操作
			pstm.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			DBHelper.closeAll(null, pstm, null);
		}
	}
}














