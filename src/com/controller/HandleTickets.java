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
import com.model.entity.Ticket;

/**
 * Servlet implementation class HandleTickets
 */
@WebServlet("/HandleTickets")
public class HandleTickets extends HttpServlet {
	private static final long serialVersionUID = 1L;
	GetConnectionToSQL getConnection;   
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HandleTickets() {
        super();
        // TODO Auto-generated constructor stub
    }

	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();
		getConnection=new GetConnectionToSQL();//使用默认的地址和用户
		getConnection.registerDrver();
  //使用 sqljdbc4.jar 类库时，应用程序无需调用 Class.forName 方法来注册或加载驱动程序*/
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
		int index_selected=Integer.parseInt(handleString(request.getParameter("removeORbill")).trim());
		String request_submit=handleString(request.getParameter("submit").trim());
		Ticket ticket=new Ticket();
		HttpSession session=request.getSession(true);
		ArrayList<Ticket> tickets=new ArrayList<Ticket>();
		tickets=(ArrayList<Ticket>) session.getAttribute("ticketsBean");
		if(tickets!=null){//session中存在订单信息链表已经建立
			if(request_submit.equals("移除订单>>")){//点击了移除按钮
				tickets.remove(index_selected);//从session中移除订单
				session.setAttribute("ticketsBean", tickets);//更新session中的ticketsBean
				forwardToUser(request, response, "移除成功！");
				return ;
			}
			else if(request_submit.equals("结算>>")){//点击了结算按钮
				ticket.setSession_number(((Ticket)tickets.get(index_selected)).getSession_number());
				ticket.setPrice(((Ticket)tickets.get(index_selected)).getPrice());
				ticket.setSit_number(((Ticket)tickets.get(index_selected)).getSit_number());
				ticket.setStatus("付款");
				ticket.setUser_name(((Ticket)tickets.get(index_selected)).getUser_name());
				tickets.remove(index_selected);//从session中移除订单
				session.setAttribute("ticketsBean", tickets);//更新session中的ticketsBean
			}
		}
		else{
			forwardToHint(request, response, "没有订票信息，");
			return ;
		}
		//从数据库获取电影信息
		String film_booked="";
		String selectCondition="select booked from session where number='"+ticket.getSession_number()+"';";
		if(!getConnection.getConnection()){//连接数据库失败
			forwardToUser(request, response, "数据库连接失败，数据库现在无法服务！！");
			return;
		}
		try {
			getConnection.prepareStatement(selectCondition);
			ResultSet rs=getConnection.getSql().executeQuery();
			if(rs.next()){//查询到信息
				film_booked=rs.getString("booked");
			}
			//将新的座位信息插入到影片信息中
			film_booked=film_booked+" "+ticket.getSit_number();
			selectCondition="UPDATE session SET booked='"+film_booked+"' WHERE number="+ticket.getSession_number()+";";
			getConnection.prepareStatement(selectCondition);
			if(getConnection.getSql().executeUpdate()!=0){//执行更新成功
				selectCondition="insert into ticket_order(user_name,session_number,status) values('"+ticket.getUser_name()+"','"+ticket.getSession_number()+"','"+ticket.getStatus()+"');";
				//con2=DriverManager.getConnection(uri);
				getConnection.prepareStatement(selectCondition);
				if(getConnection.getSql().executeUpdate()!=0){
					selectCondition="select number from ticket_order where user_name='"+ticket.getUser_name()+"' and session_number='"+ticket.getSession_number()+"'";
					//con3=DriverManager.getConnection(uri);
					getConnection.prepareStatement(selectCondition);
					rs=getConnection.getSql().executeQuery();
					int number=0;
					while(rs.next()){
						number=rs.getInt("number");
					}
					selectCondition="insert into order_details values("+number+",'"+ticket.getSit_number()+"','"+ticket.getPrice()+"');";
					//con4=DriverManager.getConnection(uri);
					getConnection.prepareStatement(selectCondition);
					if(getConnection.getSql().executeUpdate()!=0){//插入记录成功
						//System.out.println("啦啦啦啦啦啦啦啦啦啦啦");
					}
					//con3.close();
					//con4.close();
					//con2.close();
					forwardToUser(request, response, "结算成功");
				}
				else{
//					con2.rollback();//事务回滚
//					con.rollback();//事务回滚
				//	con2.close();
					forwardToUser(request, response, "新增数据失败，请检查网络或者数据库出错！");
				}
			}
			else{
				forwardToUser(request, response, "更新位置数据失败，请检查网络或者数据库出错！");
//				con.rollback();//事务回滚
			}
			getConnection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			forwardToUser(request, response, "更新数据失败，请检查网络或者数据库出错！");
		}
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
		MessageBean.setBackNews(news);
		request.setAttribute("MessageBean",MessageBean);//将会更新id是MessageBean的bean
		RequestDispatcher dispatcher=request.getRequestDispatcher("UserInfo");
		dispatcher.forward(request, response);//转发
	}
}
