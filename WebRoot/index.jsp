<!--
Author: Neutree
Author URL: http://192.243.118.141
-->

<%@ page language="java" import="java.util.*" contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<jsp:useBean id="userBean" class="com.model.entity.User" scope="session"/>
<!DOCTYPE HTML>
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>TWENTY CINEMA 二十, HOME PAGE</title>
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="keywords" content="film,Twenty,二十,电影,Neutree" />
	<meta name="description" content="description。。。"/>

	<!-- Custom Theme files -->
	<link type="image/x-icon" rel="shortcut icon" href="assets/icons/favicon.ico" />
	<link href="assets/css/fonts.css" rel='stylesheet' type='text/css' /> <!--fonts-->
	<link href="assets/css/style.css" rel='stylesheet' type='text/css' />	
	<link href="assets/css/font-awesome.min.css" rel='stylesheet' type='text/css' />
	<link href="assets/css/bootstrap.css" rel='stylesheet' type='text/css' />
		
	<script type="text/javascript" src="assets/js/jquery-2.1.4.js"></script>
	<script src="assets/js/jquery-migrate-1.2.1.min.js"></script>
	<script src="assets/js/jquery.actual.min.js"></script>
	<script src="assets/js/jquery.scrollTo.min.js"></script>
	<script src="assets/js/script.js"></script>
	<!-- inside style -->
	<!--inside script -->

  </head>
  
  <body id="page_body" class="page_bg">
  
		<div id="drawer-left"><!-- #drawer-LEFT -->
			<div class="cross text-right">
				<a class="toggleDrawer" href="#" ><i class="fa fa-times-circle fa-2x"></i></a>
			</div>
			<div class="user fa fa-user fa-2x">
				<%
					if(userBean.getName()!=null){					
					%>
					<a href="UserInfo"><%
						out.print(""+userBean.getName());
					%></a><%
					}
					else{%><a href="login.jsp"><%
						out.print("请登录");
					}%></a>
			</div><br/><br/>
			<%
				if(userBean.getName()==null|| !userBean.getRole().equals("admin")){//用户未登录
					%><div class="user fa fa-shopping-cart fa-2x">
						<a href="UserInfo">我的购物信息</a>
					  </div>
					<%
				}
				else{//登录了，是管理员
					%>	<div class="user">
						<h3><i class="fa fa-cog fa-spin fa-1x"></i>
							<a href="HelpAdmin">管理影院</a></h3>
						</div>
					<%
				}
			%>
			<br/>
			<nav>
				<ul class="nav nav-pills nav-stacked">
					<li>
						<a href="#wrapper"><i class="fa fa-home"></i> 首页</a>
					</li>
					<li>
						<a href="#hot"><i class="fa fa-heart"></i> 热映推荐</a>
					</li>
					<li>
						<a href="#coming"><i class="fa fa-spinner fa-spin"></i> 即将上映</a>
					</li>
					<li>
						<a href="#nearby"><i class="fa fa-map-marker"></i> 附近影院</a>
					</li>
					<li>
						<a href="#contact"><i class="fa fa-phone-square"></i> 联系我们</a>
					</li>
				</ul>
			</nav>
			<br/><br/><br/>
			<div class="social">
				<h2 class="fa fa-share-alt fa-2x">SHARE</h2>
				<ul>
					<li><a href="">
							<span class="fa-stack fa-lg fa-1x">
										<i class="fa fa-square fa-stack-2x"></i>
							  <i class="fa fa-qq fa-stack-1x  fa-inverse"></i>
							</span>
						</a>
					</li><li><a target="_blank" href="http://service.weibo.com/share/share.php?url=http://192.243.118.141&appkey=&title=&pic=&ralateUid=&language=" id="weibo_share">
							<span class="fa-stack fa-lg fa-1x">
										<i class="fa fa-square fa-stack-2x"></i>
							  <i class="fa fa-weibo fa-stack-1x  fa-inverse"></i>
							</span>
						</a>
					</li><li><a target="_blank" href="http://share.v.t.qq.com/index.php?c=share&a=index&title=Neutree's home page &url=http://192.243.118.141 &site=http://192.243.118.141 pic=_pic">
							<span class="fa-stack fa-lg fa-1x">
										<i class="fa fa-square fa-stack-2x"></i>
							  <i class="fa fa-tencent-weibo fa-stack-1x  fa-inverse"></i>
							</span>
						</a>
					</li>
					<li><a href="">
							<span class="fa-stack fa-lg fa-1x">
										<i class="fa fa-square fa-stack-2x"></i>
							  <i class="fa fa-wechat fa-stack-1x  fa-inverse"></i>
							</span>
						</a>
					</li>
				</ul>
			</div>
		</div><!-- #drawer-LEFT -->

  		<div id="wrapper"><!-- wrapper-->
			<div id="header" class="content-block"><!-- header -->
				<section class="top clearfix">
					
					
					<div class="pull-left">
						<a class="toggleDrawer" href="#"><i class="fa fa-bars fa-2x"></i></a>
					</div>
					<div class="pull-right">
						<h1 class="logo"><a  href="index.jsp">TWENTY</a></h1>
					</div>
					<div class="pull-center">
						<div class="searchshadow">
							<div class="selectshow">
								<form action="BrowseFilmDetails" method="post">
									<input type="text" class="text search_input" name="film_Name"  autofocus="autofocus"   placeholder="搜索电影" autocomplete="off">
									<input type="submit" value="" class="search_button">
								</form>
							</div>
						</div>
					</div>
				</section>
				
				<section class="center">
					<div class="click_left inlineblock">
						<a id="click_button_left" href="#"><img src="assets/images/arrow_down.png"></a>
					</div>
					<div class="hot_film_images inlineblock" style="display:inline-block;">
						<div class="center-title">
							<dl class="title_introduction">
								<dt id="hot_film_title" class="transition2" ></dt>
								<dd id="hot_film_introduction" class="transition2"></dd>
							</dl>
						</div>
						<ul id="headImgSlidesRegion" class="_headImgSlidesRegion">
			                <li id="headImgSlidesRegion0"  >
			                  <!--  <h2>分歧者2：绝地反击</h2> -->
			                    <a href="http://movie.mtime.com/208253/" title="分歧者2：绝地反击" target="_blank"><img src="assets/images/films/pic0.jpg"  alt="分歧者2：绝地反击"></a>
			                	
			                </li>
			                <li  id="headImgSlidesRegion1"  >
			                     <!-- <h2>侏罗纪世界</h2> -->
			                    <a href="http://movie.mtime.com/191813/" title="侏罗纪世界" target="_blank" ><img src="assets/images/films/pic1.jpg"  alt="侏罗纪世界"></a>
			                    
			                </li>
			                <li  id="headImgSlidesRegion2"  >
			                     <!-- <h2>末日崩塌</h2> -->
			                    <a href="http://movie.mtime.com/209238/" title="末日崩塌" target="_blank" ><img src="assets/images/films/pic2.jpg"  alt="末日崩塌"></a>
			                    
			                </li>
			            </ul>
					</div>
					<div class="click_right inlineblock">
						<a id="click_button_right" href="#"><img src="assets/images/arrow_down.png"></a>
					</div>
				</section>

				<section class="bottom">
					<a id="scrollToContent" href="#">
						<img src="assets/images/arrow_down.png">
					</a>
				</section>
			</div><!-- header -->
			

			
			<div id="hot" class=""><!-- #hot -->
 				<div class="more transition8">
 					<a href="BrowseFilms" title="浏览更多影片信息" class="fa fa-film" style="font-size:2em"> MORE>> </a>
 				</div>
 				<div id="films_home"><!-- 首页展示哦电影 -->
 					<table >
 						<tr>
 							<td><a href="BrowseFilms"><img src="assets/images/films/films_small/pic0.jpg"/></a></td><td><a href="BrowseFilms"><img src="assets/images/films/films_small/pic1.jpg"/></a></td><td><a href="BrowseFilms"><img src="assets/images/films/films_small/pic2.jpg"/></a></td>
 						</tr>
 						<tr>
 							<td><a href="BrowseFilms"><img src="assets/images/films/films_small/pic3.jpg"/></a></td><td><a href="BrowseFilms"><img src="assets/images/films/films_small/pic4.jpg"/></a></td><td><a href="BrowseFilms"><img src="assets/images/films/films_small/pic5.jpg"/></a></td>
 						</tr>
 						<tr>
 							<td><a href="BrowseFilms"><img src="assets/images/films/films_small/pic6.jpg"/></a></td><td><a href="BrowseFilms"><img src="assets/images/films/films_small/pic7.jpg"/></a></td><td><a href="BrowseFilms"><img src="assets/images/films/films_small/pic8.jpg"/></a></td>
 						</tr>
 					</table>
 				</div>
	 		</div><!-- #hot -->
	 		<div id="coming"><!-- #coming -->
	 			<div class="more transition8">
 					<a href="BrowseFilms" title="浏览更多影片信息" class="fa fa-film" style="font-size:2em"> MORE>> </a>
 				</div>
				<div id="films_home"><!-- 首页展示哦电影 -->
 					<table >
 						<tr>
 							<td><a href="BrowseFilms"><img src="assets/images/films/films_small/pic0.jpg"/></a></td><td><a href="BrowseFilms"><img src="assets/images/films/films_small/pic1.jpg"/></a></td><td><a href="BrowseFilms"><img src="assets/images/films/films_small/pic2.jpg"/></a></td>
 						</tr>
 						<tr>
 							<td><a href="BrowseFilms"><img src="assets/images/films/films_small/pic3.jpg"/></a></td><td><a href="BrowseFilms"><img src="assets/images/films/films_small/pic4.jpg"/></a></td><td><a href="BrowseFilms"><img src="assets/images/films/films_small/pic5.jpg"/></a></td>
 						</tr>
 						<tr>
 							<td><a href="BrowseFilms"><img src="assets/images/films/films_small/pic6.jpg"/></a></td><td><a href="BrowseFilms"><img src="assets/images/films/films_small/pic7.jpg"/></a></td><td><a href="BrowseFilms"><img src="assets/images/films/films_small/pic8.jpg"/></a></td>
 						</tr>
 					</table>
 				</div>
	 		</div><!-- #coming -->
			
			
	 		<div id="nearby"><!-- #nearby -->	
	 			<div>
	 			
	 			</div>
	 		</div><!-- #nearby -->	
			
			
			<div id="contact"><!-- #contact -->
				
			</div><!-- #contact -->
			
			<!-- footer -->
			<jsp:include page="footer.html"/>
			
			
	</div><!--/#wrapper-->
 		
  </body>
</html>
