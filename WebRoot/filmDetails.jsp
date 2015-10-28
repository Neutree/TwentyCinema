<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<jsp:useBean id="filmDetails" class="java.util.ArrayList" scope="request"/>
<%@ page import="com.model.entity.Film"%>
<%@ page import="java.text.SimpleDateFormat"  %>
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
				<th>  <div class="fa fa-video-camera"> 详情</div></th>
				<th>订票</th>
			</tr>
			<%
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				if(filmDetails!=null){
					for(int i=0;i<filmDetails.size();++i){
						Film temp=(Film)filmDetails.get(i);
						out.print("<tr>");
						out.print("<td  style=\"width:200px;height:300px;\">"+"<img class=\"img_shadow\" src="+temp.getPicture()+"></td>"
			+"<td><strong>影片名：</strong>"+temp.getName()+" <br/><strong>导演：</strong>"+temp.getDirector()+"<br/><strong>介绍：</strong>"+temp.getIntroduction()
			+"<br/><strong>制片公司：</strong>"+temp.getCompany()
			+"<br/><strong>价格：</strong>"+temp.getPrice()+"元"
			+"<br/><strong>放映厅：</strong>"+temp.getRoom_name()
			+"<br/><strong>开始时间：</strong>"+sdf.format(temp.getStart_time())
			+"<br/><strong>结束时间：</strong>"+sdf.format(temp.getOver_time())
			+"</td>"
			+"<td><form action=\"Buytickt\" method=\"post\"><input type=\"hidden\" name=\"film_ID\" value="+temp.getNumber()+"><input type=\"submit\" value=\"订票\" class=\"transition4\" /></form></td>");
						out.print("</tr>");
					}
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