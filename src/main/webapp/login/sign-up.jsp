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
	<body>

		<div class="container">
			<div class="row">
				<div class="col-md-12 text-center">
					<ul class="menu">
						<li class="active"><a href="${pageContext.request.contextPath }/login/index.jsp">Style 1</a></li>
						<li><a href="${pageContext.request.contextPath }/login/index2.jsp">Style 2</a></li>
						<li><a href="${pageContext.request.contextPath }/login/index3.jsp">Style 3</a></li>
					</ul>
				</div>
			</div>
			<div class="row">
				<div class="col-md-4 col-md-offset-4">
					<!-- Start Sign In Form -->
					<form action="${pageContext.request.contextPath}/admin?method=register" method="post"  class="fh5co-form animate-box" data-animate-effect="fadeIn">
						<h2>Sign Up</h2>
						<div class="form-group">
							<div class="alert alert-success" role="alert">Your info has been saved.</div>
						</div>
						<div class="form-group">
							<label for="name" class="sr-only">Name</label>
							<input type="text" class="form-control" id="name" name="username" placeholder="Name" autocomplete="off">
						</div>
						<div class="form-group">
							<label for="password" class="sr-only">Password</label>
							<input type="password" class="form-control" id="pwd1" name="pwd" placeholder="Password" autocomplete="off">
						</div>
						<div class="form-group">
							<label for="re-password" class="sr-only">Re-type Password</label>
							<input type="password" class="form-control" id="pwd2" name="pwd1" onkeyup="just()" placeholder="Re-type Password" autocomplete="off">
						</div>
						<span id="tishi"></span>
						<div class="form-group">
							<label for="email" class="sr-only">Email</label>
							<input type="email" class="form-control" id="email" name="email" placeholder="Email" autocomplete="off">
						</div>
						<div class="form-group">
							<label for="phone" class="sr-only">Phone</label>
							<input type="text" class="form-control" id="phone"  name="phone" placeholder="Phone" autocomplete="off" pattern="^1[3-9]\d{9}$">
						</div>
						<div class="form-group">
							<label for="remember"><input type="checkbox" id="remember"> Remember Me</label>
						</div>
						<div class="form-group">
							<p>Already registered? <a href="${pageContext.request.contextPath }/login/index.jsp">Sign In</a></p>
						</div>
						<div class="form-group">
							<input type="submit" value="Sign Up" class="btn btn-primary">
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
	<script type="text/javascript">
			var phone=document.getElementById("phone");
				phone.oninput=function(){
				phone.setCustomValidity("");
			}
			phone.oninvalid=function(){
				phone.setCustomValidity("输入正确的手机号");
			}
			   function just() {
			   
              var pwd1 = document.getElementById("pwd1").value;
              var pwd2 = document.getElementById("pwd2").value;
		
              if(pwd1 == pwd2) {
                  document.getElementById("tishi").innerHTML="<font color='green'>两次密码相同</font>";
                  document.getElementById("submit").disabled = false;
              }
              else {
                  document.getElementById("tishi").innerHTML="<font color='red'>两次密码不相同</font>";
                document.getElementById("submit").disabled = true;
              }
          }
 		</script>
	</body>
</html>

