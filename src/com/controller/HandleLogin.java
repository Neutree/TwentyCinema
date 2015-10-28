package com.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.*;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.function.GetConnectionToSQL;
import com.model.entity.Message;
import com.model.entity.User;

/**
 * Servlet implementation class HandleLogin
 */
@WebServlet("/HandleLogin")
public class HandleLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
	boolean isAdmin=false;
	GetConnectionToSQL getConnection;
    public HandleLogin() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		getConnection=new GetConnectionToSQL();//ʹ��Ĭ�ϵĵ�ַ���û�
		getConnection.registerDrver();
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
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		boolean login_status=false,login_isEnable=false;
//		String uri="jdbc:sqlserver://127.0.0.1:1433;databaseName=Twenty_Cinema;user=neutree;password=1208077207;";
		
		Message MessageBean=new Message();
		request.setAttribute("MessageBean",MessageBean);//�������id��MessageBean��bean
		String logname=handleString(request.getParameter("logname").trim());
		String logpassword=handleString(request.getParameter("logpassword").trim());
		if(logname==null)
			logname="";
		if(logpassword==null)
			logpassword="";
		if(logname.length()<=0||logpassword.length()<=5){
			MessageBean.setBackNews("����ĸ�ʽ����Ŷ��Ҫ������Ŷ��");
			RequestDispatcher dispatcher=request.getRequestDispatcher("login.jsp");
			dispatcher.forward(request, response);//ת��
			return ;
		}
		try {
//			con=DriverManager.getConnection(uri);
			if(!getConnection.getConnection()){//�������ݿ�ʧ��
				MessageBean.setBackNews("���ݿ�����ʧ�ܣ����ݿ������޷����񣡣�");
				RequestDispatcher dispatcher=request.getRequestDispatcher("login.jsp");
				dispatcher.forward(request, response);//ת��
				return;
			}
			String selectCondition="select * from usr where name='"+logname+"' and password='"+logpassword+"';";
			getConnection.prepareStatement(selectCondition);
			ResultSet rs=null;
			rs=getConnection.getSql().executeQuery();
//			System.out.println("  "+logname+"      "+logpassword);
			if(rs.next()==true){//��ѯ�ɹ�
				login_isEnable=success(request,response,rs);
				if(login_isEnable){
					login_status=true;
					MessageBean.setBackNews("��¼�ɹ�");
				}
				else
					MessageBean.setBackNews("�û�����ֹ��¼");
			}
			else{//��ѯʧ��
				MessageBean.setBackNews("��¼ʧ�ܣ��û������������");
				login_status=false;
			}
			
		} catch (SQLException e) {
		//	e.printStackTrace();
			MessageBean.setBackNews("��¼ʧ����Ŷ�����ݿ�����ʧ�ܣ�");
		}
		if(login_status&&login_isEnable){//�ɹ��ҵ��û�
//			RequestDispatcher dispatcher=request.getRequestDispatcher("hint.jsp");
//			dispatcher.forward(request, response);//ת��
			if(!isAdmin)
				forwardTo(request, response, "��¼�ɹ�","UserInfo");
			else
				forwardTo(request, response, "��¼�ɹ�","HelpAdmin");
	//		response.sendRedirect("hint.jsp");
		}
		else if(!login_isEnable){//�û�����ֹ��¼
			RequestDispatcher dispatcher=request.getRequestDispatcher("login.jsp");
			dispatcher.forward(request, response);//ת��
		}
		else{//δ�ҵ��û�
			RequestDispatcher dispatcher=request.getRequestDispatcher("login.jsp");
			dispatcher.forward(request, response);//ת��
		}
	}
	public boolean success(HttpServletRequest request,HttpServletResponse response,ResultSet rs)
			throws ServletException,IOException, SQLException{
			User userBean=null; 
			String logname = new String(rs.getString("name"));
			String logpassword=new String(rs.getString("password"));
			String logrole=new String(rs.getString("role")).trim();
			String logenable=new String(rs.getString("enable"));
//			System.out.println(logname+"sssssssssssss");
			if(logrole.equals("admin"))
				isAdmin=true;
			else
				isAdmin=false;
			if(logenable.equals("false"))
				return false;
			else if(logenable.equals("true_"))
			{
				HttpSession session=request.getSession(true);
				try{
					userBean=(User)session.getAttribute("userBean");
					if(userBean==null){//��һ�ν�������
						userBean=new User();
						session.setAttribute("userBean", userBean);
						userBean=(User)session.getAttribute("userBean");
					}
					String name=userBean.getName();
					if(name.equals(logname)){
						userBean.setBackNews(logname+"�Ѿ���¼��");
						userBean.setName(logname);
						userBean.setRole(logrole);
						userBean.setEnable(logenable);
					}
					else{
						userBean.setBackNews(logname+"��¼�ɹ�");
						userBean.setName(logname);
						userBean.setRole(logrole);
						userBean.setEnable(logenable);
					}
				}catch(Exception e){
					userBean=new User();
					session.setAttribute("userBean", userBean);
					userBean.setBackNews(logname+"��¼�ɹ�");
					userBean.setName(logname);
					userBean.setRole(logrole);
					userBean.setEnable(logenable);
				}
				return true;
			}
			return false;
	}
	public String handleString(String s){
		if(s==null)
			return new String("");
		try {
			s= new String(s.getBytes("iso-8859-1") , "utf-8" );
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return s;
	}
	public void forwardTo(HttpServletRequest request, HttpServletResponse response,String news,String To) throws ServletException, IOException{
		Message MessageBean=new Message();
		request.setAttribute("MessageBean",MessageBean);//�������id��MessageBean��bean
		MessageBean.setBackNews(news);
		RequestDispatcher dispatcher=request.getRequestDispatcher(To);
		dispatcher.forward(request, response);//ת��
	}
}
