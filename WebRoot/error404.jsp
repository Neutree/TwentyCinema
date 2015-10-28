<%@ page language="java" import="java.util.*" contentType="text/html;charset=utf-8"  pageEncoding="utf-8"%>
<!DOCTYPE HTML >
<html>
  <head>
    <title>log success</title>

	<meta name="pragma" content="no-cache">
	<meta name="cache-control" content="no-cache">
	<meta name="expires" content="0">    
	<meta name="keywords" content="keyword1,keyword2,keyword3">
	<meta name="description" content="log success page">
	<meta http-equiv="Refresh" content="3;url=index.jsp" />
	
	<link href="assets/css/style.css" rel="stylesheet" type="text/css" media="all"/>
	<script type="text/javascript">
		var interval=setInterval(countTime,1000);
		var time_count=3;
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
  
  <body class="login page_bg">
	<!-- start here-->	
	<!-- header -->
	<jsp:include page="header.html"/>
	<div style="margin-top:20%;" >
		<h2>。。。。。404页面错误，页面没找到哇/(ㄒoㄒ)/~~。。。。。</h2>
		<br/><br/><br/>
		<h3 id="hint"></h3>
	</div>	
	<!-- footer -->
	<jsp:include page="footer.html"/>	
	<!--end here-->
 </body>
</html>
