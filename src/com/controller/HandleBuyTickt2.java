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
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String filmID=handleString(request.getParameter("film_ID")).trim();
		String sits=handleString(request.getParameter("sits"));
		//�����ݿ��ȡ��Ӱ��Ϣ
		Film film = null;
		String[] Booked_sits=null;//�����д���Ѿ�Ԥ���˵�λ��
		int sits_booked_num=0;//�Ѿ�Ԥ���˵���λ����
		Room room=new Room();//Ӱ����Ϣ
		//System.out.println(filmID);
		String selectCondition="select * from session where number='"+filmID+"';";
		if(!getConnection.getConnection()){//�������ݿ�ʧ��
			forwardToHint(request, response, "�������ݿ�ʧ�ܣ����ݿ������޷�����Ŷ��");//ת��
			return;
		}
		try {
			
			getConnection.prepareStatement(selectCondition);
			ResultSet rs=getConnection.getSql().executeQuery();
			if(rs.next()){//��ѯ����Ϣ
				film=new Film();//ֻ�ܷ���ѭ���ڣ���������ArraList���ʱֻ��������һ����ԭ����
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
				//System.out.println("��������ѯʧ����");
			}
			selectCondition="select * from room where name='"+film.getRoom_name()+"';";
			getConnection.prepareStatement(selectCondition);
			rs=getConnection.getSql().executeQuery();
			if(rs.next()){//��ѯ������
				room.setName(rs.getString("name"));
				room.setSit_count(rs.getInt("sit_count"));
			}
			getConnection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Message MessageBean=new Message();
			request.setAttribute("MessageBean",MessageBean);//�������id��MessageBean��bean
			MessageBean.setBackNews("��Ʊʧ�ܣ����ݿ�����������⣬sorry��");
			RequestDispatcher dispatcher=request.getRequestDispatcher("hint.jsp");
			dispatcher.forward(request, response);//ת��
		}
		if(film!=null){//��ѯ���ݿ�ɹ�
			//��������Ϣ��ӵ�session�У�IDΪticketBean��bean
			HttpSession session=request.getSession(true);
			User userBean=(User)session.getAttribute("userBean");
			
			if(sits==null){
				forwardToHint(request, response, "���빺�ﳵʧ�ܣ�û��ѡ����λ��<br/><a href=\"user.jsp\">����˴������������</a>");//ת��
				return;
			}
			String[] sits_booked=null;
			int sits_booked_NUM=0;
			sits_booked=GetTokens.getTokens(sits);
			if(sits_booked!=null)
				sits_booked_NUM=sits_booked.length;
			if(sits_booked_NUM==0){
				forwardToHint(request, response, "���빺�ﳵʧ�ܣ�û��ѡ����λ��<br/><a href=\"user.jsp\">����˴������������</a>");//ת��
				return;
			}
			ArrayList<Ticket> tickets=new ArrayList<Ticket>();
			ArrayList<Ticket> tickets2=new ArrayList<Ticket>();
			tickets2=(ArrayList<Ticket>) session.getAttribute("ticketsBean");
			if(tickets2!=null){//���ǵ�һ�δ�����bean
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
				ticket.setStatus("Ԥ��");
				if(userBean!=null){//�Ѿ���¼
					String name=userBean.getName();
//					System.out.println(userBean.getName()+"000000000000");
					ticket.setUser_name(name);
				}
				else{
					ticket.setUser_name("�ο�");
				}
				ticket.setSit_number(sits_booked[i]);
				int j=0;
				for(;j<sits_booked_num;++j){//�������жϸ�λ���Ƿ��Ѿ����˶�Ʊ��
					if(Booked_sits[j].equals(sits_booked[i]))
						break;
				}
				try{
					if(j==sits_booked_num && Integer.parseInt(sits_booked[i])<=room.getSit_count()){//û����Ԥ���������û�г���Ӱ�����������
						tickets.add(ticket);
					}
				}catch(Exception e){
					forwardToHint(request,response,"�����ʽ�������������֣����ÿո����");
					return;
				}
					
			}
			session.setAttribute("ticketsBean", tickets);
			
			forwardToHint(request, response, "���빺�ﳵ�ɹ��������û�������Ϣ���鿴����ҳ->�û���<br/><a href=\"user.jsp\">����˴������������</a>");//ת��
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
		request.setAttribute("MessageBean",MessageBean);//�������id��MessageBean��bean
		MessageBean.setBackNews(news);
		RequestDispatcher dispatcher=request.getRequestDispatcher("hint.jsp");
		dispatcher.forward(request, response);//ת��
	}
}
