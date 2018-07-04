<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
   
<!DOCTYPE html>
<!--[if lt IE 7]>      <html class="no-js lt-ie9 lt-ie8 lt-ie7"> <![endif]-->
<!--[if IE 7]>         <html class="no-js lt-ie9 lt-ie8"> <![endif]-->
<!--[if IE 8]>         <html class="no-js lt-ie9"> <![endif]-->
<!--[if gt IE 8]><!--> <html class="no-js"> <!--<![endif]-->
	<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<title>Minimal and Clean Sign up / Login and Forgot Form by FreeHTML5.co</title>
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<meta name="description" content="Free HTML5 Template by FreeHTML5.co" />
	<meta name="keywords" content="free html5, free template, free bootstrap, html5, css3, mobile first, responsive" />
	

  

  	<!-- Facebook and Twitter integration -->
	<meta property="og:title" content=""/>
	<meta property="og:image" content=""/>
	<meta property="og:url" content=""/>
	<meta property="og:site_name" content=""/>
	<meta property="og:description" content=""/>
	<meta name="twitter:title" content="" />
	<meta name="twitter:image" content="" />
	<meta name="twitter:url" content="" />
	<meta name="twitter:card" content="" />

	<!-- Place favicon.ico and apple-touch-icon.png in the root directory -->
	<link rel="shortcut icon" href="favicon.ico">

	<link href='https://fonts.googleapis.com/css?family=Open+Sans:400,700,300' rel='stylesheet' type='text/css'>
	
	<link rel="stylesheet" href="${pageContext.request.contextPath }/login/css/bootstrap.min.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath }/login/css/animate.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath }/login/css/style.css">


	<!-- Modernizr JS -->
	<script src="${pageContext.request.contextPath }/login/js/modernizr-2.6.2.min.js"></script>
	<!-- FOR IE9 below -->
	<!--[if lt IE 9]>
	<script src="${pageContext.request.contextPath }/login/js/respond.min.js"></script>
	<![endif]-->

	</head>
	<body class="style-3">

		<div class="container">
			<div class="row">
				<div class="col-md-12 text-center">
					<ul class="menu">
						<li><a href="${pageContext.request.contextPath }/login.jsp">Style 1</a></li>
						<li><a href="${pageContext.request.contextPath }/index2.jsp">Style 2</a></li>
						<li class="active"><a href="${pageContext.request.contextPath }/index3.jsp">Style 3</a></li>
					</ul>
				</div>
			</div>
			<div class="row">
				<div class="col-md-4 col-md-push-8">
					

					<!-- Start Sign In Form -->
					<form action="login.action" method="post" class="fh5co-form animate-box" data-animate-effect="fadeInLeft">
						<h2>Sign In</h2>
						<div class="form-group">
							<label for="username" class="sr-only">Username</label>
							<input type="text" class="form-control" id="username" name="username" placeholder="Username" autocomplete="off">
						</div>
						<div class="form-group">
							<label for="password" class="sr-only">Password</label>
							<input type="password" class="form-control" id="password" name="password" placeholder="Password" autocomplete="off">
						</div>
						<div class="form-group">
							<label for="remember"><input type="checkbox" id="remember"> Remember Me</label>
						</div>
						
						<input id="loginform:codeInput" class="loginFormTdIpt" type="text"
								name="checkcode" title="请输入验证码" />
							<img id="loginform:vCode" src="${pageContext.request.contextPath }/validatecode.jsp"
								onclick="javascript:document.getElementById('loginform:vCode').src='${pageContext.request.contextPath }/validatecode.jsp?'+Math.random();" />
								
						<span><s:actionerror/></span>
						
						
						<div class="form-group">
							<p>Not registered? <a href="${pageContext.request.contextPath }/login/sign-up2.jsp">Sign Up</a> | <a href="${pageContext.request.contextPath }/login/forgot2.jsp">Forgot Password?</a></p>
						</div>
						<div class="form-group">
							<input type="submit" value="Sign In" class="btn btn-primary">
						</div>
					</form>
					<!-- END Sign In Form -->


				</div>
			</div>
			<div class="row" style="padding-top: 60px; clear: both;">
				<div class="col-md-12 text-center"><p><small>&copy; All Rights Reserved. More Templates <a href="http://www.cssmoban.com/" target="_blank" title="模板之家">模板之家</a> - Collect from <a href="http://www.cssmoban.com/" title="网页模板" target="_blank">网页模板</a></small></p></div>
			</div>
		</div>
	
	<!-- jQuery -->
	<script src="${pageContext.request.contextPath }/login/js/jquery.min.js"></script>
	<!-- Bootstrap -->
	<script src="${pageContext.request.contextPath }/login/js/bootstrap.min.js"></script>
	<!-- Placeholder -->
	<script src="${pageContext.request.contextPath }/login/js/jquery.placeholder.min.js"></script>
	<!-- Waypoints -->
	<script src="${pageContext.request.contextPath }/login/js/jquery.waypoints.min.js"></script>
	<!-- Main JS -->
	<script src="${pageContext.request.contextPath }/login/js/main.js"></script>

	</body>
</html>

