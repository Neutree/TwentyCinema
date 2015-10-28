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

/**
 * Servlet implementation class HandleBrowseFilmDetails
 */
@WebServlet("/HandleBrowseFilmDetails")
public class HandleBrowseFilmDetails extends HttpServlet {
	private static final long serialVersionUID = 1L;
	GetConnectionToSQL getConnection;   
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HandleBrowseFilmDetails() {
        super();
        // TODO Auto-generated constructor stub
        getConnection=new GetConnectionToSQL();//使用默认的地址和用户
        getConnection.registerDrver();  //使用 sqljdbc4.jar 类库时，应用程序无需调用 Class.forName 方法来注册或加载驱动程序*/
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
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
		String filmName=handleString(request.getParameter("film_Name")).trim();
//		System.out.print(name_film+"..."+filmName);
		String selectCondition="select * from session where name='"+filmName+"';";
		if(!getConnection.getConnection()){//连接数据库失败
			Message MessageBean=new Message();
			request.setAttribute("MessageBean",MessageBean);//将会更新id是MessageBean的bean
			MessageBean.setBackNews("数据库连接失败，数据库先在无法服务");
			RequestDispatcher dispatcher=request.getRequestDispatcher("hint.jsp");
			dispatcher.forward(request, response);//转发
			return;
		}
			try {
				getConnection.prepareStatement(selectCondition);
				ResultSet rs=getConnection.getSql().executeQuery();
				ArrayList<Film> films=new ArrayList<Film>();
				if(rs.next()){//查询到信息
					Film temp=new Film();//只能凡在循环内，否则在向ArraList添加时只能添加最后一个，原因不明
					temp.setName(rs.getString("name").trim());
					temp.setCompany(rs.getString("company"));
					temp.setDirector(rs.getString("director"));
					temp.setPrice(rs.getDouble("price"));
					temp.setRoom_name(rs.getString("room_name"));
					temp.setNumber(rs.getString("number"));
					temp.setStart_time(new Date(rs.getTimestamp("start_time").getTime()));
					temp.setOver_time(new Date(rs.getTimestamp("over_time").getTime()));
					temp.setIntroduction(rs.getString("introduction"));
					temp.setPicture(rs.getString("picture"));
					films.add(temp);
					while(rs.next()){
						Film temp1=new Film();//只能凡在循环内，否则在向ArraList添加时只能添加最后一个，原因不明
						temp1.setName(rs.getString("name").trim());
						temp1.setCompany(rs.getString("company"));
						temp1.setDirector(rs.getString("director"));
						temp1.setPrice(rs.getDouble("price"));
						temp1.setRoom_name(rs.getString("room_name"));
						temp1.setNumber(rs.getString("number"));
						temp1.setStart_time(new Date(rs.getTimestamp("start_time").getTime()));
						temp1.setOver_time(new Date(rs.getTimestamp("over_time").getTime()));
						temp1.setIntroduction(rs.getString("introduction"));
						temp1.setPicture(rs.getString("picture"));
						films.add(temp1);
					}
					request.setAttribute("filmDetails", films);//更新ID为filmsBean的bean
					RequestDispatcher dispatcher=request.getRequestDispatcher("filmDetails.jsp");
					dispatcher.forward(request, response);//转发
				}
				else{
					Message MessageBean=new Message();
					request.setAttribute("MessageBean",MessageBean);//将会更新id是MessageBean的bean
					MessageBean.setBackNews("数据查询失败！可能已经被删除");
					RequestDispatcher dispatcher=request.getRequestDispatcher("hint.jsp");
					dispatcher.forward(request, response);//转发
				}
				getConnection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				Message MessageBean=new Message();
				request.setAttribute("MessageBean",MessageBean);//将会更新id是MessageBean的bean
				MessageBean.setBackNews("数据查询失败！可能已经被删除");
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
