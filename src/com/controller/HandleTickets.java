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
		getConnection=new GetConnectionToSQL();//ʹ��Ĭ�ϵĵ�ַ���û�
		getConnection.registerDrver();
  //ʹ�� sqljdbc4.jar ���ʱ��Ӧ�ó���������� Class.forName ������ע��������������*/
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
		if(tickets!=null){//session�д��ڶ�����Ϣ�����Ѿ�����
			if(request_submit.equals("�Ƴ�����>>")){//������Ƴ���ť
				tickets.remove(index_selected);//��session���Ƴ�����
				session.setAttribute("ticketsBean", tickets);//����session�е�ticketsBean
				forwardToUser(request, response, "�Ƴ��ɹ���");
				return ;
			}
			else if(request_submit.equals("����>>")){//����˽��㰴ť
				ticket.setSession_number(((Ticket)tickets.get(index_selected)).getSession_number());
				ticket.setPrice(((Ticket)tickets.get(index_selected)).getPrice());
				ticket.setSit_number(((Ticket)tickets.get(index_selected)).getSit_number());
				ticket.setStatus("����");
				ticket.setUser_name(((Ticket)tickets.get(index_selected)).getUser_name());
				tickets.remove(index_selected);//��session���Ƴ�����
				session.setAttribute("ticketsBean", tickets);//����session�е�ticketsBean
			}
		}
		else{
			forwardToHint(request, response, "û�ж�Ʊ��Ϣ��");
			return ;
		}
		//�����ݿ��ȡ��Ӱ��Ϣ
		String film_booked="";
		String selectCondition="select booked from session where number='"+ticket.getSession_number()+"';";
		if(!getConnection.getConnection()){//�������ݿ�ʧ��
			forwardToUser(request, response, "���ݿ�����ʧ�ܣ����ݿ������޷����񣡣�");
			return;
		}
		try {
			getConnection.prepareStatement(selectCondition);
			ResultSet rs=getConnection.getSql().executeQuery();
			if(rs.next()){//��ѯ����Ϣ
				film_booked=rs.getString("booked");
			}
			//���µ���λ��Ϣ���뵽ӰƬ��Ϣ��
			film_booked=film_booked+" "+ticket.getSit_number();
			selectCondition="UPDATE session SET booked='"+film_booked+"' WHERE number="+ticket.getSession_number()+";";
			getConnection.prepareStatement(selectCondition);
			if(getConnection.getSql().executeUpdate()!=0){//ִ�и��³ɹ�
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
					if(getConnection.getSql().executeUpdate()!=0){//�����¼�ɹ�
						//System.out.println("����������������������");
					}
					//con3.close();
					//con4.close();
					//con2.close();
					forwardToUser(request, response, "����ɹ�");
				}
				else{
//					con2.rollback();//����ع�
//					con.rollback();//����ع�
				//	con2.close();
					forwardToUser(request, response, "��������ʧ�ܣ���������������ݿ����");
				}
			}
			else{
				forwardToUser(request, response, "����λ������ʧ�ܣ���������������ݿ����");
//				con.rollback();//����ع�
			}
			getConnection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			forwardToUser(request, response, "��������ʧ�ܣ���������������ݿ����");
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
		request.setAttribute("MessageBean",MessageBean);//�������id��MessageBean��bean
		MessageBean.setBackNews(news);
		RequestDispatcher dispatcher=request.getRequestDispatcher("hint.jsp");
		dispatcher.forward(request, response);//ת��
	}
	public void forwardToUser(HttpServletRequest request, HttpServletResponse response,String news) throws ServletException, IOException{
		Message MessageBean=new Message();
		MessageBean.setBackNews(news);
		request.setAttribute("MessageBean",MessageBean);//�������id��MessageBean��bean
		RequestDispatcher dispatcher=request.getRequestDispatcher("UserInfo");
		dispatcher.forward(request, response);//ת��
	}
}
