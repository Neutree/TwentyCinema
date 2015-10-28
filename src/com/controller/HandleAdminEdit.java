package com.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

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
import com.model.entity.User;

/**
 * Servlet implementation class HandleAdminEdit
 */
@WebServlet("/HandleAdminEdit")
public class HandleAdminEdit extends HttpServlet {
	private static final long serialVersionUID = 1L;
	GetConnectionToSQL getConnection;   
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HandleAdminEdit() {
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
		HttpSession session=request.getSession(true);
		User userBean=(User)session.getAttribute("userBean");
		if(userBean==null){//�û�δ��¼
			forwardTo(request, response, "��δ��¼����Ȩ��Ȩ��", "hint.jsp");
			return;
		}
		else{//��¼�ˣ�Ȩ�޼��
			if(!userBean.getRole().equals("admin")){
				forwardTo(request, response, "�����ǹ���Ա����Ȩ��Ȩ�޽��в���", "hint.jsp");
				return;
			}
		}
		String newone=handleString(request.getParameter("newone")).trim();
		String func=handleString(request.getParameter("func")).trim();
		String ID=handleString(request.getParameter("ID")).trim();
		String editone=handleString(request.getParameter("editone")).trim();
		String Condition="select * from session;";
		if(!getConnection.getConnection()){//�������ݿ�ʧ��
			forwardTo(request, response, "�������ݿ�ʧ��", "HelpAdmin");
			return;
		}
		try {
			if(newone.equals("nocontent")){//����Ĳ�����������ɾ�����߱༭
				switch(func){
					case "editRoom":
						if(editone.equals("nocontent")){
							Condition="select * from room where name='"+ID+"';";
							getConnection.prepareStatement(Condition);
							ResultSet rs=getConnection.getSql().executeQuery();
							if(rs.next()){
								//���ҵ�����
								String name=rs.getString("name");
								if(name!=null)
									name=name.trim();
								int sit_count=rs.getInt("sit_count");
								request.setAttribute("newone", newone);
								request.setAttribute("roomName",name);
								request.setAttribute("sitsCount",""+sit_count);
								request.setAttribute("func",func);
								forwardTo(request, response, "�༭Ӱ��", "editRoom.jsp");
							}
							else{//δ�ҵ�����
								forwardTo(request, response, "δ�ҵ�Ӱ����Ϣ", "HelpAdmin");
							}
							
							//System.out.println("����Ӱ��");
						}
						else if(editone.equals("Room")){
							//System.out.println("������������");
							String sit_count=handleString(request.getParameter("sit_count")).trim();
							String name=handleString(request.getParameter("name")).trim();
							String name_old=handleString(request.getParameter("nameold")).trim();
							if(sit_count.equals("nocontent")||name.equals("nocontent")){//δ��ȡ������
								forwardTo(request, response, "���Ӱ��ʧ��,δ��д��Ϣ", "HelpAdmin");
							}
							else{
								Condition="update room SET name='"+name+"',sit_count="+Integer.parseInt(sit_count)+" WHERE name='"+name_old+"';";
//								System.out.println(Condition);
								getConnection.prepareStatement(Condition);
								if(getConnection.getSql().executeUpdate()!=0){
									forwardTo(request, response, "�༭Ӱ���ɹ�", "HelpAdmin");
									//���³ɹ�
//									System.out.println("����������������������������");
								}
								else{
//									System.out.println("��������������������������������");
									forwardTo(request, response, "�༭Ӱ��ʧ��", "HelpAdmin");
								}
							}
							//System.out.println("������Ӱ��Ϣ����");
						}
						
//						System.out.println("�༭Ӱ��");
						break;
					case "deleteRoom":
						Condition="delete from room where name='"+ID+"'";
						getConnection.prepareStatement(Condition);
						getConnection.getSql().executeUpdate();
						forwardTo(request, response, "ɾ���ɹ�", "HelpAdmin");
//						System.out.println("ɾ��Ӱ��");
						break;
					case "editFilm":
						if(editone.equals("nocontent")){
							Condition="select * from session where number='"+ID+"'";
							getConnection.prepareStatement(Condition);
							ResultSet rs=getConnection.getSql().executeQuery();
							if(rs.next()){
								String name=rs.getString("name");
								String company=rs.getString("company");
								String director=rs.getString("director");
								double price=rs.getDouble("price");
								String room_name=rs.getString("room_name");
								Date startTime0=rs.getDate("start_time");
								Date overTime0=rs.getDate("over_time");
								String introduction=rs.getString("introduction");
								String picUrl=rs.getString("picture");
								
//html5 datetime(datetime-local)Ԫ�صĸ�ʽ��eg��2015-01-02T22:45
								//��ʽת��
								String startTime=(new SimpleDateFormat("yyyy-MM-dd hh:mm")).format(startTime0);
								startTime=replaceIndex(10, startTime,"T");
								String overTime=(new SimpleDateFormat("yyyy-MM-dd hh:mm")).format(overTime0);
//								System.out.println("..."+overTime+"...");
								overTime=replaceIndex(10, overTime,"T");
//								System.out.println("..."+overTime+"...");
								
								request.setAttribute("name",name);
								request.setAttribute("company", company);
								request.setAttribute("director", director);
								request.setAttribute("price",price+"" );
								request.setAttribute("room_name",room_name );
								request.setAttribute("startTime",startTime);
								request.setAttribute("overTime",overTime );
								request.setAttribute("introduction", introduction);
								request.setAttribute("picUrl",picUrl );
								request.setAttribute("number", ID);
								
								request.setAttribute("newone", newone);
								request.setAttribute("func",func);
								forwardTo(request, response, "�༭��Ӱ��Ϣ", "editFilm.jsp");//ת��
							}
							else{//
								forwardTo(request, response, "δ�ҵ���Ӱ��Ϣ", "HelpAdmin");
							}
						}
						else if(editone.equals("Film")){
							String numberOld=handleString(request.getParameter("numberOld")).trim();
							
							String number=handleString(request.getParameter("number")).trim();
							String name=handleString(request.getParameter("name")).trim();
							String company=handleString(request.getParameter("company")).trim();
							String director=handleString(request.getParameter("director")).trim();
							String price=handleString(request.getParameter("price")).trim();
							String room_name=handleString(request.getParameter("room_name")).trim();
							String startTime0=handleString(request.getParameter("startTime")).trim();
							String overTime0=handleString(request.getParameter("overTime")).trim();
							String introduction=handleString(request.getParameter("introduction")).trim();
							String picUrl=handleString(request.getParameter("picUrl")).trim();
							try{
								startTime0=replaceIndex(10,startTime0," ")+":00";
								overTime0=replaceIndex(10,overTime0," ")+":00";
							}catch(Exception e){
								forwardTo(request, response, "�޸�ʧ�ܣ���ȷ����Ϣ��д��������ʽ����", "HelpAdmin");
								getConnection.close();
							}
							
							Condition="update session set name='"+name+"',company='"+company+"',director='"+director+"',price="+Double.parseDouble(price)+",room_name='"+room_name+"',"
						+"start_time='"+startTime0+"',over_time='"+overTime0+"',number='"+number+"',introduction='"+introduction+"',picture='"+picUrl+"' where number='"+numberOld+"';";
							getConnection.prepareStatement(Condition);
							if(getConnection.getSql().executeUpdate()!=0){
								forwardTo(request, response, "���ĳɹ�", "HelpAdmin");
								//���³ɹ�
							}
							else{
								forwardTo(request, response, "����ʧ��,��ע���ʽ", "HelpAdmin");
							}
							//System.out.println("������Ӱ��Ϣ����");
						}
//						System.out.println("�༭��Ӱ");
						break;
					case "deleteFilm":
						Condition="delete from session where number='"+ID+"'";
						getConnection.prepareStatement(Condition);
						if(getConnection.getSql().executeUpdate()!=0)
							forwardTo(request, response, "ɾ���ɹ�", "HelpAdmin");
						else
							forwardTo(request, response, "ɾ��ʧ�ܣ������ڸ���Ϣ", "HelpAdmin");
//						System.out.println("ɾ����Ӱ");
						break;
				}
			}
			else {//���������
				if(newone.equals("film")){//������Ӱ
					if(editone.equals("nocontent")){
						request.setAttribute("newone", newone);
						forwardTo(request, response, "������Ӱ", "editFilm.jsp");
						//System.out.println("������Ӱ");
					}
					else if(editone.equals("Film")){
						String number=handleString(request.getParameter("number")).trim();
						String name=handleString(request.getParameter("name")).trim();
						String company=handleString(request.getParameter("company")).trim();
						String director=handleString(request.getParameter("director")).trim();
						String price=handleString(request.getParameter("price")).trim();
						String room_name=handleString(request.getParameter("room_name")).trim();
						String startTime0=handleString(request.getParameter("startTime")).trim();
						String overTime0=handleString(request.getParameter("overTime")).trim();
						String introduction=handleString(request.getParameter("introduction")).trim();
						String picUrl=handleString(request.getParameter("picUrl")).trim();
						startTime0=replaceIndex(10,startTime0," ")+":00";
						overTime0=replaceIndex(10,overTime0," ")+":00";
						
						Condition="insert into session(name,company,director,price,room_name,start_time,over_time,number,introduction,picture) values('"
								+name+"','"+company+"','"+director+"',"+Double.parseDouble(price)+",'"+room_name+"','"+startTime0+"','"+overTime0+"','"+number+"','"+introduction+"','"+picUrl+"')";
						getConnection.prepareStatement(Condition);
						if(getConnection.getSql().executeUpdate()!=0){
							forwardTo(request, response, "���³ɹ�", "HelpAdmin");
							//���³ɹ�
						}
						//System.out.println("������Ӱ��Ϣ����");
					}
				}
				else if(newone.equals("room")){//����Ӱ����Ϣ
					if(editone.equals("nocontent")){
						request.setAttribute("newone", newone);
						forwardTo(request, response, "����Ӱ��", "editRoom.jsp");
						//System.out.println("����Ӱ��");
					}
					else if(editone.equals("Room")){
						//System.out.println("������������");
						String sit_count=handleString(request.getParameter("sit_count")).trim();
						String name=handleString(request.getParameter("name")).trim();
						if(sit_count.equals("nocontent")||name.equals("nocontent")){//δ��ȡ������
							forwardTo(request, response, "���Ӱ��ʧ��,δ��д��Ϣ", "HelpAdmin");
						}
						else{
							Condition="insert into room(name,sit_count) values('"+name+"',"+Integer.parseInt(sit_count)+");";
							getConnection.prepareStatement(Condition);
							if(getConnection.getSql().executeUpdate()!=0){
								forwardTo(request, response, "���Ӱ���ɹ�", "HelpAdmin");
								//���³ɹ�
							}
							else{
								forwardTo(request, response, "���Ӱ��ʧ��", "HelpAdmin");
							}
						}
						//System.out.println("������Ӱ��Ϣ����");
					}
				}
			}
			getConnection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			forwardTo(request, response, "�޸�ʧ��","HelpAdmin");
		}
		
	}
	public String handleString(String s){
		if (s==null)
			return new String("nocontent");
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
	public static String replaceIndex(int index,String res,String str){
		return res.substring(0, index)+str+res.substring(index+1);
	}
}
