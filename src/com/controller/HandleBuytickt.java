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

import com.function.GetConnectionToSQL;
import com.model.entity.Film;
import com.model.entity.Message;
import com.model.entity.Room;

/**
 * Servlet implementation class HandleBuytickt
 */
@WebServlet("/HandleBuytickt")
public class HandleBuytickt extends HttpServlet {
	private static final long serialVersionUID = 1L;
	GetConnectionToSQL getConnection;   
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HandleBuytickt() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		getConnection=new GetConnectionToSQL();//使用默认的地址和用户
		getConnection.registerDrver(); //使用 sqljdbc4.jar 类库时，应用程序无需调用 Class.forName 方法来注册或加载驱动程序*/
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
		//System.out.print(filmID);
		String selectCondition="select * from session where number='"+filmID+"';";
		if(!getConnection.getConnection()){//连接数据库失败
			Message MessageBean=new Message();
			request.setAttribute("MessageBean",MessageBean);//将会更新id是MessageBean的bean
			MessageBean.setBackNews("数据库连接失败，闲杂数据库无法提供服务哦！！");
			RequestDispatcher dispatcher=request.getRequestDispatcher("hint.jsp");
			dispatcher.forward(request, response);//转发
			return;
		}
			try {
				getConnection.prepareStatement(selectCondition);
				ResultSet rs=getConnection.getSql().executeQuery();
				if(rs.next()){//查询到信息
					Film film=new Film();//只能凡在循环内，否则在向ArraList添加时只能添加最后一个，原因不明
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
					request.setAttribute("filmDetails", film);//更新ID为filmsBean的bean
					
					selectCondition="select * from room where name='"+film.getRoom_name()+"';";
					getConnection.prepareStatement(selectCondition);
					rs=getConnection.getSql().executeQuery();
					if(rs.next()){//查询到数据
						Room room=new Room();
						room.setName(rs.getString("name"));
						room.setSit_count(rs.getInt("sit_count"));
						request.setAttribute("roomBean",room);//更新ID为roomBean的bean
						getConnection.close();//关闭数据库连接
						RequestDispatcher dispatcher=request.getRequestDispatcher("Buytickt1.jsp");
						dispatcher.forward(request, response);//转发
					}
					else{
						Message MessageBean=new Message();
						request.setAttribute("MessageBean",MessageBean);//将会更新id是MessageBean的bean
						MessageBean.setBackNews("影厅数据查询失败！可能已经被删除");
						RequestDispatcher dispatcher=request.getRequestDispatcher("hint.jsp");
						dispatcher.forward(request, response);//转发
					}
					
				}
				else{
					Message MessageBean=new Message();
					request.setAttribute("MessageBean",MessageBean);//将会更新id是MessageBean的bean
					MessageBean.setBackNews("该电影数据查询失败！可能已经被删除");
					RequestDispatcher dispatcher=request.getRequestDispatcher("hint.jsp");
					dispatcher.forward(request, response);//转发
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
//				e.printStackTrace();
				Message MessageBean=new Message();
				request.setAttribute("MessageBean",MessageBean);//将会更新id是MessageBean的bean
				MessageBean.setBackNews("数据查询失败！可能已经被删除");
				RequestDispatcher dispatcher=request.getRequestDispatcher("hint.jsp");
				dispatcher.forward(request, response);//转发
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

}
