package cn.web.controller;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BaseController extends HttpServlet{
        @Override
        protected void service(HttpServletRequest request, HttpServletResponse response)
        		throws ServletException, IOException {
        	//解决乱码问题
        	request.setCharacterEncoding("UTF-8");
        	response.setCharacterEncoding("UTF-8");
        	String url = request.getRequestURI();
        	String action = url.substring(url.lastIndexOf("/")+1, url.lastIndexOf("."));
        	//反射
        	try {
        		Class clazz = BaseController.class;
				Object obj = clazz.newInstance();
				Method method = clazz.getMethod(action, HttpServletRequest.class,HttpServletResponse.class);
				method.invoke(obj, request,response);
			} catch (Exception e) {
				e.printStackTrace();
			} 
        	
        }
}
