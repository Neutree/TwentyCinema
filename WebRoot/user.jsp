<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<jsp:useBean id="userBean" class="com.model.entity.User" scope="session"/>
<jsp:useBean id="ticketsBean" class="java.util.ArrayList" scope="session"/>
<jsp:useBean id="ticketsBean2" class="java.util.ArrayList" scope="session"/>
<jsp:useBean id="MessageBean" class="com.model.entity.Message" scope="request"/>
<%@ page import="com.model.entity.Ticket" %>
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
		  color: white;
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
		  color: white;
		  padding: 10px 10px;
		  width: 100%;
		  outline:none;
		  border: 0;
		  cursor:pointer;
		  border-radius:5px;
		  background-color:#2098d1;
		  margin: 0.5em auto 0.5em auto;
		}
		.tickets_dis{
			background-color:white;
			width:900px;
			margin:30px auto;
			border-radius:20px;
			padding:50px;
		}
		#customers
		{
			font-family:"Trebuchet MS", Arial, Helvetica, sans-serif;
			width:100%;
			border-collapse:collapse;
			color:gray;
		}
		#customers td, #customers th 
		{
			font-size:1em;
			border:1px solid #98bf21;
			padding:3px 7px 2px 7px;
		}
		
		#customers th 
		{
			font-size:1.1em;
			text-align:left;
			padding-top:5px;
			padding-bottom:4px;
			background-color:#A7C942;
			color:#ffffff;
		}
		
		#customers tr.alt td 
		{
			color:#000000;
			background-color:#EAF2D3;
		}
	</style>
  </head>
  
  <body id="wrapper" class="login page_bg">
	<!-- start here-->	
	<!-- header -->
	<jsp:include page="header.html"/>
	<div >
		<h3 id="hint"></h3>
		<%
			if(userBean.getName()==null){//用户未登录
				out.print("<h2><strong>用户名:</strong>"+"游客"+"</h2>");
				out.print("<h2><strong>用户类型:</strong>"+"游客"+"</h2>");
				%><form action="login.jsp" method="post">
					<input type="submit" value="登录" class="transition8" />
				</form><%
			}
			else{//已经登录了
				out.print("<h2><strong>用户名:</strong>"+userBean.getName()+"</h2>");
				out.print("<h2><strong>用户类型:</strong>"+userBean.getRole()+"</h2>");
				
				%><form action="helpLogout" method="post">
					<input type="submit" value="退出" class="transition8" />
				</form><%
			}
			%>
			<div class="tickets_dis">
				<div><h2 style="color:red;"><jsp:getProperty property="backNews" name="MessageBean"/></h2></div>
				<div >
					<h1 style="color:gray;">我的购物车</h1>
				</div>
				<table id="customers">
					
					<%
						if(ticketsBean.size()>0){//有预订信息
							%><tr>
								<th>电影票信息</th><th style="width:20%;">移除</th><th>结算</th>
							</tr>
							<%
							for(int i=0;i<ticketsBean.size();++i){
								out.print("<tr>");
									out.print("<td>");
										out.print("<strong>电影编号：</strong>"+((Ticket)ticketsBean.get(i)).getSession_number());
										out.print("<br/><strong>用户名：</strong>"+((Ticket)ticketsBean.get(i)).getUser_name());
										out.print("<br/><strong>价格：</strong>"+((Ticket)ticketsBean.get(i)).getPrice());
										out.print("<br/><strong>座位号：</strong>"+((Ticket)ticketsBean.get(i)).getSit_number());
										out.print("<br/><strong>状态：</strong>"+((Ticket)ticketsBean.get(i)).getStatus());
									out.print("</td>");	
									out.print("<td>");
										out.print("<form action=\"RemoveOrBill\" method=\"post\" >");
											out.print("<input type=\"hidden\" name=\"removeORbill\" value=\""+i+"\">");
											out.print("<input  class=\"transition4\" type=\"submit\" value=\"移除订单>>\" name=\"submit\">");
										out.print("<form >");
									out.print("</td>");	
									out.print("<td>");
									if(userBean.getName()!=null){//已经登录了的用户才能结算
										out.print("<form action=\"RemoveOrBill\" method=\"post\" >");
											out.print("<input type=\"hidden\" name=\"removeORbill\" value=\""+i+"\">");
											out.print("<input class=\"transition4\" type=\"submit\" value=\"结算>>\" name=\"submit\">");
										out.print("</form >");
									}
									else{
											out.print("<a href=\"login.jsp\"><input class=\"transition4\" type=\"submit\" name=\"submit\" value=\"结算请登录>>\"></a>");
									}
									out.print("</td>");	
								out.print("</tr>");							
							}
						}							
						else{
							out.print("<h2 style=\"color:gray;\">没有订单哦！赶快去下单吧！<a href=\"BrowseFilms\">点我浏览电影</a></h2>");
						}							
					%>
				</table>
			</div>
			<div class="tickets_dis">
				<div >
					<h1 style="color:gray;">我的电影票</h1>
					<table id="customers">
					
					<%
						if(ticketsBean2.size()>0){//有预订信息
							%><tr>
								<th>电影票信息</th><th style="width:20%;">退订</th>
							</tr>
							<%
							for(int i=0;i<ticketsBean2.size();++i){
								out.print("<tr>");
									out.print("<td>");
										out.print("<strong>电影编号：</strong>"+((Ticket)ticketsBean2.get(i)).getSession_number());
										out.print("<br/><strong>用户名：</strong>"+((Ticket)ticketsBean2.get(i)).getUser_name());
										out.print("<br/><strong>价格：</strong>"+((Ticket)ticketsBean2.get(i)).getPrice());
										out.print("<br/><strong>座位号：</strong>"+((Ticket)ticketsBean2.get(i)).getSit_number());
										out.print("<br/><strong>状态：</strong>"+((Ticket)ticketsBean2.get(i)).getStatus());
									out.print("</td>");	
									out.print("<td>");
										out.print("<form action=\"Unsubscribe\" method=\"post\" >");
											out.print("<input type=\"hidden\" name=\"removeIndex\" value=\""+((Ticket)ticketsBean2.get(i)).getNumber()+"\">");
											out.print("<input type=\"hidden\" name=\"session_number\" value=\""+((Ticket)ticketsBean2.get(i)).getSession_number()+"\">");
											out.print("<input type=\"hidden\" name=\"session_sit\" value=\""+((Ticket)ticketsBean2.get(i)).getSit_number()+"\">");
											out.print("<input  class=\"transition4\" type=\"submit\" value=\"退订>>\" name=\"submit\">");
										out.print("<form >");
									out.print("</td>");	
								out.print("</tr>");							
							}
						}							
						else{
							//out.print("<h2 style=\"color:gray;\">没有订单哦！赶快去下单吧！<a href=\"BrowseFilms\">点我浏览电影</a></h2>");
							//可能是由于第一次登录，未刷新，调用UserInfo servlet从数据库中查询记录
							//response.sendRedirect("UserInfo");
						}							
					%>
				</table>
				</div>
			</div>
			
		<%
		%>
		
	</div>	
	<!-- footer -->
	<jsp:include page="footer.html"/>
	<!--end here-->
 </body>
</html>