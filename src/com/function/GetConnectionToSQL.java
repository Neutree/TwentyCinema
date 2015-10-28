package com.function;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class GetConnectionToSQL {
	String user;
	String url;
	String port;
	String password;
	String dataBase;
	Connection con;
	PreparedStatement sql;
	
	int SQLtype;//0��SQL  1��MySQL
	
	public GetConnectionToSQL( String url, String port, String dataBase,String usr,
			String password) {
		this.user = usr;
		this.url = url;
		this.port = port;
		this.password = password;
		this.dataBase = dataBase;
		this.con = null;
		this.sql = null;
		SQLtype=0;//
	}

	public GetConnectionToSQL() {
		// TODO Auto-generated constructor stub
		this.user = "neutree";
		this.url = "127.0.0.1";
		this.port = "1433";
		this.password = "1208077207";
		this.dataBase = "Twenty_Cinema";
		this.con = null;
		this.sql = null;
		SQLtype=0;
//		
//		this.user = "mine";
//		this.url = "192.243.118.141";
//		this.port = "3306";
//		this.password = "c1208077207";
//		this.dataBase = "Twenty_Cinema";
//		this.con = null;
//		this.sql = null;
//		SQLtype=1;
	}

	public int getSQLtype() {
		return SQLtype;
	}

	public void setSQLtype(int sQLtype) {
		SQLtype = sQLtype;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getUrl() {
		return url;
	}
	
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getDataBase() {
		return dataBase;
	}
	public void setDataBase(String dataBase) {
		this.dataBase = dataBase;
	}
	public Connection getCon() {
		return con;
	}
	public void setCon(Connection con) {
		this.con = con;
	}
	public PreparedStatement getSql() {
		return sql;
	}
	public void setSql(PreparedStatement sql) {
		this.sql = sql;
	}
	public boolean registerDrver(){
		if(SQLtype==0){
			try{
		         Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			}catch(java.lang.ClassNotFoundException e){
			           System.err.println(e.getMessage());
			           return false;
			}  //ʹ�� sqljdbc4.jar ���ʱ��Ӧ�ó���������� Class.forName ������ע��������������*/
			return true;
		}
		else if(SQLtype==1){
			try {
				Class.forName("com.mysql.jdbc.Driver");
				try {
					Class.forName("com.mysql.jdbc.Driver").newInstance();
				} catch (InstantiationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return false;
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return false;
				}
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
			return true;
		}
		else{
			try{
		         Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			}catch(java.lang.ClassNotFoundException e){
			           System.err.println(e.getMessage());
			           return false;
			}  //ʹ�� sqljdbc4.jar ���ʱ��Ӧ�ó���������� Class.forName ������ע��������������*/
			return true;
		}
	}
	
	public boolean getConnection(){
		if(SQLtype==0)
			return getConnectionSQL();
		else if(SQLtype==1)
			return getConnectionMySQL();
		else
			return getConnectionSQL();
	}
	public boolean getConnectionSQL(){
		try {
			con=DriverManager.getConnection("jdbc:sqlserver://"+url+":"+port+";databaseName="+dataBase+";user="+user+";password="+password+";");
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
		//	e.printStackTrace();
			return false;
		}
	}
	public boolean getConnectionMySQL() {
		try {
			con=DriverManager.getConnection("jdbc:mysql://"+url+":"+port+"/"+dataBase+"?user="+user+"&password="+password+"&useUnicode=true&characterEncoding=utf-8");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			return false;
		}
		return true;
	}
	public  PreparedStatement prepareStatement(String s) throws SQLException{
		sql=con.prepareStatement(s);
		return sql;
	}
	public void close() throws SQLException{
		con.close();
	}
}

/*
 * ʹ�÷���
 * 
 * //�������
	GetConnectionToSQL getConnection;
	
	//��ʼ��
	getConnection=new GetConnectionToSQL();//ʹ��Ĭ�ϵĵ�ַ���û�
	getConnection.registerDrver();
	
	//�������ݿ�
	if(!getConnection.getConnection()){//�������ݿ�ʧ��
	
		return;
	}
	
	//JDBC�洢���� ����ִ�е����
	getConnection.prepareStatement(Condition);
	
	//ִ�����
	rs=getConnection.getSql().executeQuery(); 
 * 
 * */
