<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<jsp:useBean id="filmsBean" class="java.util.ArrayList" scope="request"/>
<jsp:useBean id="roomsBean" class="java.util.ArrayList" scope="request"/>
<jsp:useBean id="MessageBean" class="com.model.entity.Message" scope="request"/>
<%@ page import="com.model.entity.Room" import="com.model.entity.Film" %>
<!DOCTYPE HTML ">
<html>
  <head>
    <title>editRoom page</title>

	<meta name="pragma" content="no-cache">
	<meta name="cache-control" content="no-cache">
	<meta name="expires" content="0">    
	<meta name="keywords" content="keyword1,keyword2,keyword3">
	<meta name="description" content="This is my page">
	
	<link href="assets/css/style.css" rel="stylesheet" type="text/css" media="all"/>
	<style type="text/css">
		div{
			
		}
		 input[type="submit"] {
		  font-size: 1em;
		  font-weight: 800;
		  color:white;
		  padding: 10px 10px;
		  width: 60%;
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
		  width: 60%;
		  outline:none;
		  border: 0;
		  cursor:pointer;
		  border-radius:5px;
		  background-color:#2098d1;
		  margin: 0.5em auto 0.5em auto;
		}
		.dis{
			background-color:white;
			width:900px;
			margin:30px auto;
			border-radius:20px;
			padding:50px;
		}
		input[type="text"],input[type="datetime-local"] {
			  font-size: 0.8em;
			  font-weight: 500;
			  color: #008000;
			  padding: 10px 10px;
			  width: 65%;
			  outline:none;
			 
			  border: 2px solid #999;
			  margin: 3em 0em 0em 0em;
		}
		form{
			color:gray;
			text-align:right;
		}
		.dis2{
			background-color:white;
			width:600px;
			margin-right:120px;
		}
	</style>
  </head>
  
  <body id="wrapper" class="login page_bg">
	<!-- start here-->	
	<!-- header -->
	<jsp:include page="header.html"/>
	<div class="dis" >
		<div><h1 style="color:green;">影片信息</h1></div>
		<div class="dis2">
		<%
			String newone=(String)request.getAttribute("newone");
			String roomName=(String)request.getAttribute("roomName");
			String nameOld=roomName;
			if(roomName!=null)
				roomName=roomName.trim();
			String func=(String)request.getAttribute("func");
			String sit_count=(String)request.getAttribute("sitsCount");
			//System.out.println(newone+"..//... ");
			
		 %>
		<form action="AdminEdit" method="post">
			<input type="hidden" name="editone" value="Room">
		<%	out.print("<input type=\"hidden\" name=\"newone\" value=\""+newone+"\">"); %>
		<%	out.print("<input type=\"hidden\" name=\"func\" value=\""+func+"\">"); %>
		<%	out.print("<input type=\"hidden\" name=\"nameold\" value=\""+nameOld+"\">"); %>
			影厅名：<input type="text" name="name" value="<%if( roomName!=null){out.print(roomName);} %>"><br>
			影厅容量:<input type="text" name="sit_count" value="<%if( sit_count!=null){out.print(sit_count);} %>"><br>
			<input class="transition4" type="submit" value="<%if( newone.equals("nocontent")){out.print("修改");} else{out.print("新增");}%>"/><br>
		</form>

		</div>
	</div>	
		
	<!-- footer -->
	<jsp:include page="footer.html"/>
	<!--end here-->
 </body>
</html>