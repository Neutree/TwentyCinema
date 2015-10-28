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
import com.function.GetTokens;
import com.model.entity.Message;
import com.model.entity.Ticket;
import com.model.entity.User;

/**
 * Servlet implementation class HandleUnsubscribe
 */
@WebServlet("/HandleUnsubscribe")
public class HandleUnsubscribe extends HttpServlet {
	private static final long serialVersionUID = 1L;
	GetConnectionToSQL getConnection;   
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HandleUnsubscribe() {
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
		int order_number=Integer.parseInt(handleString(request.getParameter("removeIndex")).trim());
		String session_number=handleString(request.getParameter("session_number").trim());
		String sit_number=handleString(request.getParameter("session_sit").trim());
		HttpSession session=request.getSession(true);
		User userBean=(User)session.getAttribute("userBean");
		if(userBean==null){//用户未登录
			forwardToUser(request, response, "");//转发
			return;
		}
		ArrayList<Ticket> tickets=new ArrayList<Ticket>();
		//从数据库获取电影信息
		String selectCondition="delete from ticket_order where number='"+order_number+"';";
		if(!getConnection.getConnection()){//连接数据库失败
			forwardToUser(request, response, "数据库连接失败，数据库现在无法服务！！");
			return;
		}
		try {
			getConnection.prepareStatement(selectCondition);
			if(getConnection.getSql().executeUpdate()!=0){//删除成功
				String film_booked="";
				selectCondition="select booked from session where number='"+session_number+"';";
				getConnection.prepareStatement(selectCondition);
				ResultSet rs=getConnection.getSql().executeQuery();
				if(rs.next()){//查询到信息
					film_booked=rs.getString("booked");
				}
				//将新的座位信息插入到影片信息中
				String[] temp=GetTokens.getTokens(film_booked);
				int temp_length=0;
				if(temp!=null)
					temp_length=temp.length;
				for(int i=0;i<temp_length;++i){
					if(temp[i].equals(sit_number))
						temp[i]="";
				}
				film_booked="";
				for(int i=0;i<temp_length;++i)
					film_booked=film_booked+" "+temp[i];
				selectCondition="UPDATE session SET booked='"+film_booked+"' WHERE number="+session_number+";";
				getConnection.prepareStatement(selectCondition);
				if(getConnection.getSql().executeUpdate()!=0){//执行更新成功
				
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
		RequestDispatcher dispatcher=request.getRequestDispatcher("UserInfo");
		dispatcher.forward(request, response);//转发
	}
}
