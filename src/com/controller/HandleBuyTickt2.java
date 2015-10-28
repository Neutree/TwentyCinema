package com.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

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
import com.model.entity.Film;
import com.model.entity.Message;
import com.model.entity.Room;
import com.model.entity.Ticket;
import com.model.entity.User;

/**
 * Servlet implementation class HandleBuyTickt2
 */
@WebServlet("/HandleBuyTickt2")
public class HandleBuyTickt2 extends HttpServlet {
	private static final long serialVersionUID = 1L;
	GetConnectionToSQL getConnection;   
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HandleBuyTickt2() {
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
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String filmID=handleString(request.getParameter("film_ID")).trim();
		String sits=handleString(request.getParameter("sits"));
		//从数据库获取电影信息
		Film film = null;
		String[] Booked_sits=null;//数组中存放已经预定了的位置
		int sits_booked_num=0;//已经预订了的座位数量
		Room room=new Room();//影厅信息
		//System.out.println(filmID);
		String selectCondition="select * from session where number='"+filmID+"';";
		if(!getConnection.getConnection()){//连接数据库失败
			forwardToHint(request, response, "连接数据库失败，数据库现在无法服务哦！");//转发
			return;
		}
		try {
			
			getConnection.prepareStatement(selectCondition);
			ResultSet rs=getConnection.getSql().executeQuery();
			if(rs.next()){//查询到信息
				film=new Film();//只能凡在循环内，否则在向ArraList添加时只能添加最后一个，原因不明
				film.setName(rs.getString("name").trim());
				film.setCompany(rs.getString("company"));
				film.setDirector(rs.getString("director"));
				film.setPrice(rs.getDouble("price"));
				film.setRoom_name(rs.getString("room_name"));
				film.setNumber(rs.getString("number"));
				film.setStart_time(new Date(rs.getTimestamp("start_time").getTime()));
				film.setOver_time(new Date(rs.getTimestamp("over_time").getTime()));
				film.setIntroduction(rs.getString("introduction"));
				film.setPicture(rs.getString("picture"));
				film.setBooked(rs.getString("booked"));
			}
			else{
				//System.out.println("哈哈，查询失败啦");
			}
			selectCondition="select * from room where name='"+film.getRoom_name()+"';";
			getConnection.prepareStatement(selectCondition);
			rs=getConnection.getSql().executeQuery();
			if(rs.next()){//查询到数据
				room.setName(rs.getString("name"));
				room.setSit_count(rs.getInt("sit_count"));
			}
			getConnection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Message MessageBean=new Message();
			request.setAttribute("MessageBean",MessageBean);//将会更新id是MessageBean的bean
			MessageBean.setBackNews("买票失败！数据库操作出现问题，sorry！");
			RequestDispatcher dispatcher=request.getRequestDispatcher("hint.jsp");
			dispatcher.forward(request, response);//转发
		}
		if(film!=null){//查询数据库成功
			//将订单信息添加到session中，ID为ticketBean的bean
			HttpSession session=request.getSession(true);
			User userBean=(User)session.getAttribute("userBean");
			
			if(sits==null){
				forwardToHint(request, response, "加入购物车失败，没有选择座位）<br/><a href=\"user.jsp\">点击此处进入个人中心</a>");//转发
				return;
			}
			String[] sits_booked=null;
			int sits_booked_NUM=0;
			sits_booked=GetTokens.getTokens(sits);
			if(sits_booked!=null)
				sits_booked_NUM=sits_booked.length;
			if(sits_booked_NUM==0){
				forwardToHint(request, response, "加入购物车失败，没有选择座位）<br/><a href=\"user.jsp\">点击此处进入个人中心</a>");//转发
				return;
			}
			ArrayList<Ticket> tickets=new ArrayList<Ticket>();
			ArrayList<Ticket> tickets2=new ArrayList<Ticket>();
			tickets2=(ArrayList<Ticket>) session.getAttribute("ticketsBean");
			if(tickets2!=null){//不是第一次创建该bean
				tickets=(ArrayList<Ticket>) tickets2.clone();
			}
			if(film.getBooked()!=null){
				Booked_sits=GetTokens.getTokens(film.getBooked());
				sits_booked_num=Booked_sits.length;
			}
			for(int i=0;i<sits_booked_NUM;++i){
				Ticket ticket=new Ticket();
				ticket.setPrice(film.getPrice());
				ticket.setSession_number(film.getNumber());
				ticket.setStatus("预订");
				if(userBean!=null){//已经登录
					String name=userBean.getName();
//					System.out.println(userBean.getName()+"000000000000");
					ticket.setUser_name(name);
				}
				else{
					ticket.setUser_name("游客");
				}
				ticket.setSit_number(sits_booked[i]);
				int j=0;
				for(;j<sits_booked_num;++j){//遍历，判断该位置是否已经有人订票了
					if(Booked_sits[j].equals(sits_booked[i]))
						break;
				}
				try{
					if(j==sits_booked_num && Integer.parseInt(sits_booked[i])<=room.getSit_count()){//没有人预订并且最大没有超出影厅的最大容量
						tickets.add(ticket);
					}
				}catch(Exception e){
					forwardToHint(request,response,"输入格式错误，请输入数字，并用空格隔开");
					return;
				}
					
			}
			session.setAttribute("ticketsBean", tickets);
			
			forwardToHint(request, response, "加入购物车成功，请在用户个人信息出查看（主页->用户）<br/><a href=\"user.jsp\">点击此处进入个人中心</a>");//转发
		}
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
	public void forwardToHint(HttpServletRequest request, HttpServletResponse response,String news) throws ServletException, IOException{
		Message MessageBean=new Message();
		request.setAttribute("MessageBean",MessageBean);//将会更新id是MessageBean的bean
		MessageBean.setBackNews(news);
		RequestDispatcher dispatcher=request.getRequestDispatcher("hint.jsp");
		dispatcher.forward(request, response);//转发
	}
}
