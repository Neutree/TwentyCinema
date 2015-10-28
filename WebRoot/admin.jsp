<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<jsp:useBean id="filmsBean" class="java.util.ArrayList" scope="request"/>
<jsp:useBean id="userBean" class="com.model.entity.User" scope="session"/>
<jsp:useBean id="roomsBean" class="java.util.ArrayList" scope="request"/>
<jsp:useBean id="MessageBean" class="com.model.entity.Message" scope="request"/>
<%@ page import="com.model.entity.Room" import="com.model.entity.Film" import="com.function.GetTokens" import="com.model.entity.Message" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>User's page</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	
	<link href="assets/css/style.css" rel="stylesheet" type="text/css" media="all"/>
	<style type="text/css">
		div{
			
		}
		 input[type="submit"] {
		  font-size: 1em;
		  font-weight: 800;
		  color:white;
		  padding: 10px 10px;
		  width: 100%;
		  outline:none;
		  border: 0;
		  border-radius:5px;
		  background-color:#3B5998;
		  margin: 0.5em auto 0.5em auto;
		}
		input[type="submit"]:hover {
		  font-size: 1em;
		  font-weight: 800;
		  color:white;
		  padding: 10px 10px;
		  width: 100%;
		  outline:none;
		  border: 0;
		  cursor:pointer;
		  border-radius:5px;
		  background-color:#2098d1;
		  margin: 0.5em auto 0.5em auto;
		}
		
	</style>
  </head>
  
  <body id="wrapper" class="login page_bg">
	<!-- start here-->	
	<!-- header -->
	<jsp:include page="header.html"/>
	<div class="dis" >
		<div><h2 style="color:red;">*******<jsp:getProperty property="backNews" name="MessageBean"/>*******</h2></div>
		<div><h1 style="color:green;">影厅信息</h1></div>
		<table id="customers" >
			<tr><th>影厅名</th><th>容量</th><th>删除</th><th>修改</th></tr>
			<%
					
				if(userBean.getName()==null){//用户未登录
					request.setAttribute("MessageBean",MessageBean);//将会更新id是MessageBean的bean
					MessageBean.setBackNews("抱歉，您无权访问本页面！请登录");
					RequestDispatcher dispatcher=request.getRequestDispatcher("login.jsp");
					dispatcher.forward(request, response);//转发
					return;
				}
				else{//登录了，权限检查
					if(!userBean.getRole().equals("admin")){
						request.setAttribute("MessageBean",MessageBean);//将会更新id是MessageBean的bean
					MessageBean.setBackNews("抱歉，您无权访问该页面!请确认账户信息");
					RequestDispatcher dispatcher=request.getRequestDispatcher("user.jsp");
					dispatcher.forward(request, response);//转发
						return;
					}
				}
				for(int i=0;i<roomsBean.size();++i){
					out.print("<tr>");
						out.print("<td>");
							out.print(((Room)roomsBean.get(i)).getName());
						out.print("</td>");
						out.print("<td>");
							out.print(((Room)roomsBean.get(i)).getSit_count());
						out.print("</td>");
						out.print("<td>");
							%>
								<form  action="AdminEdit" method="post">
									<input type="hidden" name="func" value="deleteRoom">
									<% out.print("<input type=\"hidden\" name=\"ID\" value=\""+((Room)roomsBean.get(i)).getName()+"\">"); %>
									<input class="transition4" type="submit" name="submit" value="删除"/>
								</form>
							<%
						out.print("</td>");
						out.print("<td>");
							%>
								<form action="AdminEdit" method="post">
									<input type="hidden" name="func" value="editRoom">
									<% out.print("<input type=\"hidden\" name=\"ID\" value=\""+((Room)roomsBean.get(i)).getName()+"\">"); %>
									<input class="transition4" type="submit" name="submit" value="修改"/>
								</form>
							<%
						out.print("</td>");
					out.print("</tr>");
				}
				
			 %>
			 
		</table>
			<form action="AdminEdit" method="post">
				<h2 style=" padding:35px 0 0px 0;"><input type="hidden" name="newone" value="room"><input class="transition4" type="submit" value="新增"/></h2>
			</form>
	</div>
	<div class="dis" >
		<div><h1 style="color:green;">影片场次信息</h1></div>
		<table id="customers">
			<tr><th>电影票编号</th><th>电影信息</th><th>售票数量统计</th><th>新增</th><th>删除</th></tr><%
			for(int i=0;i<filmsBean.size();++i){
					out.print("<tr>");
						out.print("<td>");
							out.print(((Film)filmsBean.get(i)).getNumber());
						out.print("</td>");
						out.print("<td>");
							out.print("<strong>影片名</strong>&nbsp; &nbsp;  "+((Film)filmsBean.get(i)).getName());
							out.print("<br/><strong>影厅：</strong>&nbsp; &nbsp;  "+((Film)filmsBean.get(i)).getRoom_name());
							out.print("<br/><strong>票价：</strong>&nbsp; &nbsp;  "+((Film)filmsBean.get(i)).getPrice()+"元");
						out.print("</td>");
						out.print("<td>");
							String booked=((Film)filmsBean.get(i)).getBooked();
							//System.out.println(booked);
							int length=0;
							if(booked!=null){
								String[] booked_array=GetTokens.getTokens(booked);
								
								if(booked_array!=null)
									length=booked_array.length;
							}
							out.print(length+"张");
						out.print("</td>");
						out.print("<td>");
							%>
								<form  action="AdminEdit" method="post">
									<input type="hidden" name="func" value="deleteFilm">
									<% out.print("<input type=\"hidden\" name=\"ID\" value=\""+((Film)filmsBean.get(i)).getNumber()+"\">"); %>
									<input class="transition4" type="submit" name="submit" value="删除"/>
								</form>
							<%
						out.print("</td>");
						out.print("<td>");
							%>
								<form action="AdminEdit" method="post">
									<input type="hidden" name="func" value="editFilm">
									<% out.print("<input type=\"hidden\" name=\"ID\" value=\""+((Film)filmsBean.get(i)).getNumber()+"\">"); %>
									<input class="transition4" type="submit" name="submit" value="修改"/>
								</form>
							<%
						out.print("</td>");
					out.print("</tr>");
				}
				%>
		</table>
			<form action="AdminEdit" method="post">
				<h2 style=" padding:35px 0 0px 0;"><input type="hidden" name="newone" value="film"><input class="transition4" type="submit" value="新增"/></h2>
			</form>
	</div>		
	<!-- footer -->
	<jsp:include page="footer.html"/>
	<!--end here-->
 </body>
</html>