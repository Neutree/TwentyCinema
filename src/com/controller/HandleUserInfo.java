package com.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.function.GetConnectionToSQL;
import com.model.entity.Message;
import com.model.entity.Ticket;
import com.model.entity.User;

/**
 * Servlet implementation class HandleUserInfo
 */
@WebServlet("/HandleUserInfo")
public class HandleUserInfo extends HttpServlet {
	private static final long serialVersionUID = 1L;
	GetConnectionToSQL getConnection;   
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HandleUserInfo() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		getConnection=new GetConnectionToSQL();//使用默认的地址和用户
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
		HttpSession session=request.getSession(true);
		User userBean=(User)session.getAttribute("userBean");
		if(userBean==null){//用户未登录
			forwardToUser(request, response, "");//转发
			return;
		}
		ArrayList<Ticket> tickets=new ArrayList<Ticket>();
//		ArrayList<Ticket> tickets2=(ArrayList<Ticket>) session.getAttribute("ticketsBean2");
//		if(tickets2==null){//从未创建过这个bean
//			
//		}
//		else{
//			tickets=(ArrayList<Ticket>) tickets2.clone();//复制到tickets
//		}
		//从数据库获取电影信息
		String selectCondition="select * from ticket_order where user_name='"+userBean.getName()+"';";
		if(!getConnection.getConnection()){//连接数据库失败
			forwardToUser(request, response, "数据库连接失败，数据库现在无法服务！！");
			return;
		}
		try {
			getConnection.prepareStatement(selectCondition);
			ResultSet rs=getConnection.getSql().executeQuery();
			
			while(rs.next()){
				Ticket ticket=new Ticket();
				ResultSet rs2=null;
				ticket.setNumber(rs.getInt("number"));
				ticket.setSession_number(rs.getString("session_number"));
				ticket.setStatus(rs.getString("status"));
				ticket.setUser_name(userBean.getName());
				selectCondition="select * from order_details where number='"+ticket.getNumber()+"';";
				getConnection.prepareStatement(selectCondition);
				rs2=getConnection.getSql().executeQuery();
				if(rs2.next()){
					ticket.setPrice(rs2.getDouble("price"));
					ticket.setSit_number(rs2.getString("sit_number"));
					tickets.add(ticket);
				}
			}
			getConnection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			forwardToUser(request, response, "更新数据失败，请检查网络或者数据库出错！");
		}
		session.setAttribute("ticketsBean2",tickets);
		Message a=new Message();
		a=(Message) request.getAttribute("MessageBean");
//		System.out.print(((Message)request.getAttribute("MessageBean")).getBackNews());
//		request.setAttribute("MessageBean",);
		forwardToUser(request, response, "");
	}
	public String handleString(String s){
		if(s==null)
			return new String();
		try {
			s= new String(s.getBytes("iso-8859-1") , "utf-8" );
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return s;
	}
	public void forwardToHint(HttpServletRequest request, HttpServletResponse response,String news) throws ServletException, IOException{
		Message MessageBean=new Message();
		request.setAttribute("MessageBean",MessageBean);//将会更新id是MessageBean的bean
		MessageBean.setBackNews(news);
		RequestDispatcher dispatcher=request.getRequestDispatcher("hint.jsp");
		dispatcher.forward(request, response);//转发
	}
	public void forwardToUser(HttpServletRequest request, HttpServletResponse response,String news) throws ServletException, IOException{
		Message MessageBean=new Message();
		request.setAttribute("MessageBean",MessageBean);//将会更新id是MessageBean的bean
		MessageBean.setBackNews(news);
		RequestDispatcher dispatcher=request.getRequestDispatcher("user.jsp");
		dispatcher.forward(request, response);//转发
	}
}
