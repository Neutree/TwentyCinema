<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<jsp:useBean id="filmsBean" class="java.util.ArrayList" scope="request"/>
<jsp:useBean id="roomsBean" class="java.util.ArrayList" scope="request"/>
<jsp:useBean id="MessageBean" class="com.model.entity.Message" scope="request"/>
<%@ page import="com.model.entity.Room" import="com.model.entity.Film" %>
<!DOCTYPE HTML ">
<html>
  <head>
    <title>User's page</title>

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
			String func=(String)request.getAttribute("func");
			String name=(String)request.getAttribute("name");
			String company=(String)request.getAttribute("company");
			String director=(String)request.getAttribute("director");
			String price=(String)request.getAttribute("price" );
			String room_name=(String)request.getAttribute("room_name" );
			String startTime=(String)request.getAttribute("startTime");
			String overTime=(String)request.getAttribute("overTime" );
			String introduction=(String)request.getAttribute("introduction");
			String picUrl=(String)request.getAttribute("picUrl" );
			String number=(String)request.getAttribute("number");
			
//			System.out.println("jsp页面："+startTime+",,,,,,,,"+overTime);
			String numberOld=number;
			
			//System.out.println(newone);
		 %>
		<form action="AdminEdit" method="post">
			<input type="hidden" name="editone" value="Film">
		<%	out.print("<input type=\"hidden\" name=\"newone\" value=\""+newone+"\">"); %>
		<%	out.print("<input type=\"hidden\" name=\"func\" value=\""+func+"\">"); %>
		<%	out.print("<input type=\"hidden\" name=\"numberOld\" value=\""+numberOld+"\">"); %>
			编号:<input type="text" name="number" value="<%if(number!=null){out.print(number);}%>"><br>
			影片名：<input type="text" name="name" value="<%if(name!=null){out.print(name);}%>"><br>
			出品公司:<input type="text" name="company" value="<%if(company!=null){out.print(company);}%>"><br>
			导演:<input type="text" name="director" value="<%if(director!=null){out.print(director);}%>"><br>
			票价:<input type="text" name="price" value="<%if(price!=null){out.print(price);}%>"><br>
			影厅名:<input type="text" name="room_name" value="<%if(room_name!=null){out.print(room_name);}%>"><br>
			开始放映时间:<input type="datetime-local" name="startTime" value="<%if(startTime!=null){out.print(startTime);}%>"><br>
			结束放映时间:<input type="datetime-local" name="overTime" value="<%if(overTime!=null){out.print(overTime);}%>"><br>
			简介:<input type="text" name="introduction" value="<%if(introduction!=null){out.print(introduction);}%>"><br>
			图片地址:<input type="text" name="picUrl" value="<%if(picUrl!=null){out.print(picUrl);}%>"><br>
			<input class="transition4" type="submit" value="<%if( newone.equals("nocontent")){out.print("修改");} else{out.print("新增");}%>"/><br>
		</form>

		</div>
	</div>	
		
	<!-- footer -->
	<jsp:include page="footer.html"/>
	<!--end here-->
 </body>
</html>