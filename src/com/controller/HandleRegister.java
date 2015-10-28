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
		getConnection=new GetConnectionToSQL();//ʹ��Ĭ�ϵĵ�ַ���û�
		getConnection.registerDrver();  //ʹ�� sqljdbc4.jar ���ʱ��Ӧ�ó���������� Class.forName ������ע��������������*/
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
		LogClass RegisterBean=new LogClass();//����javabeanģ��
		request.setAttribute("RegisterBean",RegisterBean);//�������id��userBean��bean
		String logname=request.getParameter("sign_name").trim();
		String logpassword=request.getParameter("sign_password").trim();
		String logpassword2=request.getParameter("sign_confirm_password").trim();
		if(logname==null)
			logname="";
		if(logpassword==null)
			logpassword="";
		if(!logpassword.equals(logpassword2)){
			RegisterBean.setBackNews("�������벻ͬ��ע��ʧ������Ҫ������Ŷ��");
			RegisterBean.setSucced(false);
			isSussed=false;
			RequestDispatcher dispatcher=request.getRequestDispatcher("join.jsp");
			dispatcher.forward(request, response);//ת��
			return ;
		}
		//ע����Ϣ�Ϸ����
		if(logname.length()>0&&logpassword.length()>5){//��Ϣ�Ϸ�
			if(!getConnection.getConnection()){//�������ݿ�ʧ��
				RegisterBean.setBackNews("���ݿ�����ʧ�ܣ����ݿ������޷�����Ŷ����");
				RequestDispatcher dispatcher=request.getRequestDispatcher("join.jsp");
				dispatcher.forward(request, response);//ת��
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
					RegisterBean.setBackNews("ע��ɹ�,�û���Ϊ��"+handleString(logname)+"���룺"+logpassword);
					RegisterBean.setSucced(true);
					isSussed=true;
					RegisterBean.setName(logname);
					RegisterBean.setPassword(logpassword);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
			//	e.printStackTrace();
				RegisterBean.setBackNews("�û����Ѿ����ڻ��ʽ������Ӵ����������д��Ϣ��");
				RegisterBean.setSucced(false);
				isSussed=false;
			}
		}
		else if(logname.length()<=0){
			RegisterBean.setBackNews("�û������Ȳ�������������дŶ��");
			RegisterBean.setSucced(false);
			isSussed=false;
		}
		else if(logpassword.length()<6){
			RegisterBean.setBackNews("���볤�Ȳ�������������дŶ��");
			RegisterBean.setSucced(false);
			isSussed=false;
		}
		else{//��Ϣ���Ϸ�
			RegisterBean.setBackNews("��Ϣ��д���������������зǷ��֣���������дŶ��");
			RegisterBean.setSucced(false);
			isSussed=false;
		}
		if(!isSussed){
			RequestDispatcher dispatcher=request.getRequestDispatcher("join.jsp");
			dispatcher.forward(request, response);//ת��
		}
		else{
			RequestDispatcher dispatcher=request.getRequestDispatcher("hint.jsp");
			dispatcher.forward(request, response);//ת��
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
