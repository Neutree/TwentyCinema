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
	
	int SQLtype;//0：SQL  1：MySQL
	
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
			}  //使用 sqljdbc4.jar 类库时，应用程序无需调用 Class.forName 方法来注册或加载驱动程序*/
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
			}  //使用 sqljdbc4.jar 类库时，应用程序无需调用 Class.forName 方法来注册或加载驱动程序*/
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
 * 使用方法
 * 
 * //定义对象
	GetConnectionToSQL getConnection;
	
	//初始化
	getConnection=new GetConnectionToSQL();//使用默认的地址和用户
	getConnection.registerDrver();
	
	//连接数据库
	if(!getConnection.getConnection()){//连接数据库失败
	
		return;
	}
	
	//JDBC存储过程 传入执行的语句
	getConnection.prepareStatement(Condition);
	
	//执行语句
	rs=getConnection.getSql().executeQuery(); 
 * 
 * */
