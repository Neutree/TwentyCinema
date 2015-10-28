<%@ page language="java" import="java.util.*" contentType="text/html;charset=utf-8"  pageEncoding="utf-8"%>
<jsp:useBean id="RegisterBean" class="com.model.entity.LogClass" scope="request"/>
<jsp:useBean id="MessageBean" class="com.model.entity.Message" scope="request"/>
<!DOCTYPE HTML >
<html>
  <head>
    <title>log success</title>

	<meta name="pragma" content="no-cache">
	<meta name="cache-control" content="no-cache">
	<meta name="expires" content="0">    
	<meta name="keywords" content="keyword1,keyword2,keyword3">
	<meta name="description" content="log success page">
	<meta http-equiv="Refresh" content="5;url=index.jsp" />
	
	<link href="assets/css/style.css" rel="stylesheet" type="text/css" media="all"/>
	<script type="text/javascript">
		var interval=setInterval(countTime,1000);
		var time_count=5;
		function countTime(){
			document.getElementById("hint").innerHTML="页面将在"+time_count+"秒后自动跳转";
			--time_count;
		}
	</script>
	<style type="text/css">
		h2,h3{
			text-align:center;
		}
	</style>
  </head>
  
  <body  class="login page_bg">
	<!-- start here-->	
	<div id="wraper" >
		<!-- header -->
	<jsp:include page="header.html"/>
		<div style="margin-top:15%;" >
			<h3 id="hint"></h3>
			<h2><jsp:getProperty property="backNews" name="RegisterBean"/></h2><br/>
			<h2><jsp:getProperty property="backNews" name="MessageBean"/></h2>
		</div>	
	</div>
	<!-- footer -->
	<jsp:include page="footer.html"/>
	<!--end here-->
 </body>
</html>
