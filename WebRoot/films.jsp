<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<jsp:useBean id="filmsBean" class="java.util.ArrayList" scope="request"/>
<%@ page import="com.model.entity.Film" %>
<%@ page import="com.model.entity.Message" %>
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
		input[type="submit"] {
		  font-size: 1em;
		  font-weight: 800;
		  color: white;
		  padding: 10px 10px;
		  width: 100%;
		  height:100%;
		  outline:none;
		  border: 0;
		  border-radius:5px;
		  background-color:#3B5998;
		  margin: 2em auto 1em auto;
		}
		input[type="submit"]:hover {
		  font-size: 1em;
		  font-weight: 800;
		  color: white;
		  padding: 10px 10px;
		  width: 100%;
		  height:100%;
		  outline:none;
		  border: 0;
		  cursor:pointer;
		  border-radius:5px;
		  background-color:#2098d1;
		  margin: 2em auto 1em auto;
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
				<th>电影名称</th>
				<th>简介</th>
				<th>  <div class="fa fa-video-camera"> 详情</div></th>
				<th>订票</th>
			</tr>
			<%
				if(filmsBean.size()!=0){
					for(int i=0;i<filmsBean.size();++i){
						Film temp=(Film)filmsBean.get(i);
						out.print("<tr>");
						out.print("<td  style=\"width:200px;height:300px;\">"+"<img class=\"img_shadow\" src="+temp.getPicture()+"></td>"
			+"<td><strong>影片名：</strong>"+temp.getName()+" <br/><strong>导演：</strong>"+temp.getDirector()+"<br/><strong>介绍：</strong>"+temp.getIntroduction()+"</td>"
			+"<td><form action=\"BrowseFilmDetails\" method=\"post\"><input type=\"hidden\" name=\"film_Name\" value="+temp.getName()+"><input type=\"submit\" value=\"详情>>\" class=\"transition8\" /></form></td>"+"</td>"
			+"<td><form action=\"Buytickt\" method=\"post\"><input type=\"hidden\" name=\"film_ID\" value="+temp.getNumber()+"><input type=\"submit\" value=\"订票\" class=\"transition4\" /></form></td>");
						out.print("</tr>");
					}
				}
				else{//没有电影或者是直接输入网址进来的
					Message MessageBean=new Message();
					request.setAttribute("MessageBean",MessageBean);//将会更新id是MessageBean的bean
					MessageBean.setBackNews("库中没有电影哦，请勿直接输入本页网址进入哦，请从主页链接或其它页面链接进入<br/><a  href=\"BrowseFilms\">点我查看电影</a>");
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