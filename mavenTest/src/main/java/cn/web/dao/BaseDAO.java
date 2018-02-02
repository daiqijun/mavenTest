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
	
	//��ѯͨ�÷���
	public List<T> Query(String sql,Object[] params,Class clazz){
		//����list(��������ֵ)
		List<T> list = new ArrayList<T>();
		//�������
		Connection con = null;
		//PreparedStatement ����
		PreparedStatement pstm = null;
		//�����
		ResultSet rs = null;
		try {
			con = DBHelper.getConn();
			pstm= con.prepareStatement(sql);
			//���ò���
			for (int i = 0; i < params.length; i++) {
				pstm.setObject(i+1, params[i]);
			}
			//���ò�ѯ�ķ��������ؽ����
			rs = pstm.executeQuery();
			//��ȡrs���������
			ResultSetMetaData data = rs.getMetaData();
			//��ȡ����
			int count = data.getColumnCount();
			while (rs.next()) {
				T t = (T)clazz.newInstance();
				for (int i = 0; i < count; i++) {
					//��ȡ����(ʵ�����������)(�±��1��ʼ) 
					String column = data.getColumnName(i+1);
					//ƴ��set����id - > setId name-->setName
					String columnStr = "set"+column.substring(0,1).toUpperCase()+column.substring(1);
					//��ȡʵ����������Ϣ
					Field field = clazz.getDeclaredField(column);
					//���õ��õķ���
					Method method = clazz.getMethod(columnStr, field.getType());
					//ִ�е��÷���
					method.invoke(t, rs.getObject(column));
				}
				list.add(t);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			//�ر�
			DBHelper.closeAll(rs, pstm,con);
		}
		return list;
	}
	/**��ѯ����*/
	public T getOnly(String sql,Object[] params,Class clazz){
		T t = null;
		//�������
		Connection con = null;
		//PreparedStatement ����
		PreparedStatement pstm = null;
		//�����
		ResultSet rs = null;
		try {
			con = DBHelper.getConn();
			pstm= con.prepareStatement(sql);
			//���ò���
			for (int i = 0; i < params.length; i++) {
				pstm.setObject(i+1, params[i]);
			}
			//���ò�ѯ�ķ��������ؽ����
			rs = pstm.executeQuery();
			//��ȡrs���������
			ResultSetMetaData data = rs.getMetaData();
			//��ȡ����
			int count = data.getColumnCount();
			if (rs.next()) {
				t = (T)clazz.newInstance();
				for (int i = 0; i < count; i++) {
					//��ȡ����(ʵ�����������)(�±��1��ʼ) 
					String column = data.getColumnName(i+1);
					//ƴ��set����id - > setId name-->setName
					String columnStr = "set"+column.substring(0,1).toUpperCase()+column.substring(1);
					//��ȡʵ����������Ϣ
					Field field = clazz.getDeclaredField(column);
					//���õ��õķ���
					Method method = clazz.getMethod(columnStr, field.getType());
					//ִ�е��÷���
					method.invoke(t, rs.getObject(column));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			//�ر�
			DBHelper.closeAll(rs, pstm,con);
		}
		return t;
	}
	
	
	//��ɾ��
	public void update(String sql,Object[] params,Connection conn){
		PreparedStatement pstm = null;
		try {
			pstm = conn.prepareStatement(sql);
			for (int i = 0; i < params.length; i++) {
				pstm.setObject(i+1, params[i]);
			}
			//executeUpdate ִ����ɾ�Ĳ���
			pstm.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			DBHelper.closeAll(null, pstm, null);
		}
	}
}














