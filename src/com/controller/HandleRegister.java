package com.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.*;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.function.GetConnectionToSQL;
import com.model.entity.LogClass;

/**
 * Servlet implementation class HandleRegister
 */
@WebServlet("/HandleRegister")
public class HandleRegister extends HttpServlet {
	private static final long serialVersionUID = 1L;
	GetConnectionToSQL getConnection;   
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HandleRegister() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		getConnection=new GetConnectionToSQL();//使用默认的地址和用户
		getConnection.registerDrver();  //使用 sqljdbc4.jar 类库时，应用程序无需调用 Class.forName 方法来注册或加载驱动程序*/
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		boolean isSussed=false;
		LogClass RegisterBean=new LogClass();//创建javabean模型
		request.setAttribute("RegisterBean",RegisterBean);//将会更新id是userBean的bean
		String logname=request.getParameter("sign_name").trim();
		String logpassword=request.getParameter("sign_password").trim();
		String logpassword2=request.getParameter("sign_confirm_password").trim();
		if(logname==null)
			logname="";
		if(logpassword==null)
			logpassword="";
		if(!logpassword.equals(logpassword2)){
			RegisterBean.setBackNews("两次密码不同，注册失败啦！要认真填哦！");
			RegisterBean.setSucced(false);
			isSussed=false;
			RequestDispatcher dispatcher=request.getRequestDispatcher("join.jsp");
			dispatcher.forward(request, response);//转发
			return ;
		}
		//注册信息合法检查
		if(logname.length()>0&&logpassword.length()>5){//信息合法
			if(!getConnection.getConnection()){//连接数据库失败
				RegisterBean.setBackNews("数据库连接失败，数据库现在无法服务哦！！");
				RequestDispatcher dispatcher=request.getRequestDispatcher("join.jsp");
				dispatcher.forward(request, response);//转发
				return;
			}
			try {
				String insertCondition="INSERT INTO usr VALUES(?,?,?,?);";
				getConnection.prepareStatement(insertCondition);
				getConnection.getSql().setString(1, handleString(logname));
				getConnection.getSql().setString(2, handleString(logpassword));
				getConnection.getSql().setString(3, "user_");
				getConnection.getSql().setString(4, "true_");
				if(getConnection.getSql().executeUpdate()!=0){
					RegisterBean.setBackNews("注册成功,用户名为："+handleString(logname)+"密码："+logpassword);
					RegisterBean.setSucced(true);
					isSussed=true;
					RegisterBean.setName(logname);
					RegisterBean.setPassword(logpassword);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
			//	e.printStackTrace();
				RegisterBean.setBackNews("用户名已经存在或格式有问题哟，请重新填写信息！");
				RegisterBean.setSucced(false);
				isSussed=false;
			}
		}
		else if(logname.length()<=0){
			RegisterBean.setBackNews("用户名长度不够，请重新填写哦！");
			RegisterBean.setSucced(false);
			isSussed=false;
		}
		else if(logpassword.length()<6){
			RegisterBean.setBackNews("密码长度不够，请重新填写哦！");
			RegisterBean.setSucced(false);
			isSussed=false;
		}
		else{//信息不合法
			RegisterBean.setBackNews("信息填写不完整或名字中有非法字，请重新填写哦！");
			RegisterBean.setSucced(false);
			isSussed=false;
		}
		if(!isSussed){
			RequestDispatcher dispatcher=request.getRequestDispatcher("join.jsp");
			dispatcher.forward(request, response);//转发
		}
		else{
			RequestDispatcher dispatcher=request.getRequestDispatcher("hint.jsp");
			dispatcher.forward(request, response);//转发
		}
	}
	public String handleString(String s){
		try {
			s= new String(s.getBytes("iso-8859-1") , "utf-8" );
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return s;
	}
}
