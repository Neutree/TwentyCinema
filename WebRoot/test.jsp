<%@ page language="java" import="java.util.*"  contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<jsp:useBean id="MessageBean" class="com.model.entity.Message" scope="request"/>
<!DOCTYPE HTML>
<html>
  <head>
    <title>Twenty sign in</title>

	<meta name="pragma" content="no-cache">
	<meta name="cache-control" content="no-cache">
	<meta name="expires" content="0">    
	<meta name="keywords" content="keyword1,keyword2,keyword3">
	<meta name="description" content="This is my page">
	
	<link href="assets/css/style.css" rel="stylesheet" type="text/css" media="all"/>
	<link href="assets/css/font-awesome.min.css" rel='stylesheet' type='text/css' />
	<style>
		body{
			position:relative;
			padding-bottom:30px;
		}
	</style>
  </head>
<body class="login page_bg">
	<!--login form start here-->	
	<!-- header -->
	<jsp:include page="header.html"/>
	<h1>TWENTY LOG IN</h1>
	<div class="login_">
		<div style="color:red;" >
			<jsp:getProperty name="MessageBean" property="backNews"/>
		</div>
		<form action="helpLogin" method="post" name="form">
			<input type="text" value="" name="logname" class="transition8" placeholder="用户名" autofocus="autofocus" />
			<br/><input type="password" name="logpassword" class="transition8" value="" placeholder="密码  长度大于5（不包含5）" />
			<br/><input type="submit" class="transition8" value="登录"/>
		</form>
		<div class="forgot">
			<div class="checkbox_div">
					<input type="checkbox" value="1" id="checkboxFourInput"  name="checkbox" />
					<label for="checkboxFourInput"></label>
			</div>
			<div class="checkbox_hint">
				<i class="">记住密码？ </i>
			</div>
		</div>
		<div class="forgot-para">
				忘记密码？<a href="#">赶快戳我找回呀！</a>
		</div>
		<div class="signup">
			<h3>还没注册？<a href="join.jsp">戳我马上注册!come on</a>  </h3>
		</div>
		<div class="buttons">
			<ul>
				<li><a class="face" href="#"><span class="fa fa-facebook"></span>Login to Facebook</a></li>
				<li><a class="twit" href="#"><span class="fa fa-twitter"></span>Login to Twitter</a></li>
			</ul>
		</div>
	</div>
	<!-- footer -->
	<jsp:include page="footer.html"/>
	<!--login form end here-->
</body>
</html>