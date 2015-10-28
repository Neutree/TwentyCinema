<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<jsp:useBean id="filmDetails" class="com.model.entity.Film" scope="request"/>
<jsp:useBean id="roomBean" class="com.model.entity.Room" scope="request"/>
<jsp:useBean id="userBean" class="com.model.entity.User" scope="session"/>
<%@ page import="com.model.entity.Film"%>
<%@ page import="java.text.SimpleDateFormat"  %>
<%@ page import="com.model.entity.Message" %>
<%@ page import="com.function.GetTokens" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>My JSP 'user.jsp' starting page</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	
	<link href="assets/css/style.css" rel="stylesheet" type="text/css" media="all"/>
	<link href="assets/css/font-awesome.min.css" rel='stylesheet' type='text/css' />
	<style>
		#films_dis{
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
		input[type="text"] {
		  font-size: 1em;
		  font-weight: 800;
		  color: gray;
		  padding: 10px 10px;
		  width: 80%;
		  outline:none;
		  border: 2px solid #999;
		  border-radius:5px;
		  background-color:white;
		  margin: 0.5em auto 0.5em auto;
		}
		input[type="submit"] {
		  font-size: 1em;
		  font-weight: 800;
		  color: white;
		  padding: 10px 10px;
		  width: 20%;
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
		  width: 20%;
		  outline:none;
		  border: 0;
		  cursor:pointer;
		  border-radius:5px;
		  background-color:#2098d1;
		  margin: 0.5em auto 0.5em auto;
		}
		img{
			width:200px;
			height:300px;
		}
	</style>
  </head>
  
  <body class="page_bg">
	<!-- start here-->	
	<div id="wrapper" >
	<!-- header -->
	<jsp:include page="header.html"/>
	<div  id="films_dis">
		<table id="customers">
			<tr>
				<th><div class="fa fa-map-marker">  影厅座位信息（无人型图标处可订）</div></th>
				<th ><div class="fa fa-file-text-o">  订单信息</div></th>
			</tr>
			<%
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				
				if(filmDetails.getName()!=null){
					String[] Booked_sits=null;//数组中存放已经预定了的位置
					int sits_booked_num=0;
					//将String类型的booked属性中用空格隔开的值放到数组中
					if(filmDetails.getBooked()!=null){
						Booked_sits=GetTokens.getTokens(filmDetails.getBooked());
						sits_booked_num=Booked_sits.length;
					}
					out.print("<tr>");
						out.print("<td>");
							out.print("<table style=\"color:green;\">");
							int i,j;
							out.print("<tr>");
							for(j=0;j<roomBean.getSit_count();++j){
								for(i=0;i<sits_booked_num;++i){
									if(Booked_sits[i].equals(String.valueOf(j+1))){
										break;
									}
								}
								if(i<sits_booked_num)//找到该位置已经被预定
									out.print("<td class=\"fa fa-user\"><br/>"+(j+1)+" </td>");
								else
									out.print("<td ><br/>"+(j+1)+" </td>");
								if((j+1)%6==0)
									out.print("</tr><tr>");
							}
							out.print("</tr>");
							out.print("</table>");
						out.print("</td>");
						
						out.print("<td>");
							out.print("<div class=\"fa fa-film\"><strong>  影片名称：</strong></div>"+filmDetails.getName());
							out.print("<br/><div class=\"fa fa-map-marker\"><strong>    影厅：</strong></div>"+filmDetails.getRoom_name());
							out.print("<br/><div class=\"fa fa-play\"><strong>    开始放映时间：</strong></div>"+sdf.format(filmDetails.getStart_time()));
							out.print("<br/><div class=\"fa fa-stop\"><strong>    停止放映时间：</strong></div>"+sdf.format(filmDetails.getOver_time()));
							out.print("<br/><div class=\"fa fa-user\"><strong>    用户：</strong></div>");
								if(userBean.getName()==null){//未登录
									out.print("<a href=\"login.jsp\">点我登录</a>&nbsp&nbsp<a href=\"join.jsp\">点我注册</a>");
								}
								else{//已经登录
									out.print("<a href=\"user.jsp\">"+userBean.getName()+"</a>");
								}
							if(userBean.getName()==null){//未登录
									out.print("");
							}
							out.print("<form action=\"Buytickt2\" method=\"post\" mame=\"form\">");
							/*	if(userBean.getName()==null){//未登录
									out.print("<br/><strong>请输入手机号：</strong>");
									out.print("<input type=\"text\" name=\"phone_number\" placeholder=\"请输入手机号码\" autofocus=\"autofocus\" value=\"\"/>");								
								}*/
								out.print("<br/><strong>请选择座位号（多个请用空格隔开）：</strong>");
								out.print("<input type=\"hidden\" name=\"film_ID\" value="+filmDetails.getNumber()+"></input><input type=\"text\" placeholder=\"选择座位（多个之间用空格隔开）\" autofocus=\"autofocus\" name=\"sits\"></input><input type=\"submit\" value=\"添加到购物车\" name=\"submit\"></input>");
							out.print("</form>");
						out.print("</td>");
					out.print("</tr>");
				}
				else{//没有电影或者是直接输入网址进来的
					Message MessageBean=new Message();
					request.setAttribute("MessageBean",MessageBean);//将会更新id是MessageBean的bean
					MessageBean.setBackNews("库中没有该电影哦，可能已经被删除<br/>请勿直接输入本页网址进入哦，请从主页链接或其它页面链接进入");
					RequestDispatcher dispatcher=request.getRequestDispatcher("hint.jsp");
					dispatcher.forward(request, response);//转发
				}
			 %>
		</table>
	</div>	
	<!-- footer -->
	<jsp:include page="footer.html"/>	
	
	</div>
	<!--end here-->
 </body>
</html>