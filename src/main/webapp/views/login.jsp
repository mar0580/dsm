<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html ng-app="login">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>DSM - Login</title>
<link rel="icon" href="static/img/logo.png" type="image/x-icon">
<link href="<c:url value='/static/css/bootstrap.css' />" rel="stylesheet"></link>
<link href="<c:url value='/static/css/app.css' />" rel="stylesheet"></link>
<link href="<c:url value='/static/css/toggle.css' />" rel="stylesheet"></link>
<script src="<c:url value='/static/js/jquery-3.2.0.min.js'/>"></script>
<script src="<c:url value='/static/js/angular.min.js'/>"></script>
<script src="<c:url value='/static/js/angular-utf8-base64.js'/>"></script>
<script src="<c:url value='/static/js/login.js'/>"></script>

<style>
@import url(https://fonts.googleapis.com/css?family=Roboto:300);

body {
	background: #d2a41c /* fallback for old browsers */ 
	 background:  -webkit-linear-gradient(right, #94a4bb, #c2d2e8);
	background: -moz-linear-gradient(right, #94a4bb, #c2d2e8);
	background: -o-linear-gradient(right, #94a4bb, #c2d2e8);
	background: linear-gradient(to left, #94a4bb, #c2d2e8);
	font-family: "Roboto", sans-serif;
	-webkit-font-smoothing: antialiased;
	-moz-osx-font-smoothing: grayscale;
}

.logo {
	background-color: #f9f9f9;
	position: relative;
	z-index: 1;
	padding: 10px;
	max-width: 360px;
}

.img-logo {
	display: block;
	margin: auto;
}

.login-page {
	width: 360px;
	padding: 8% 0 0;
	margin: auto;
}

.form {
	position: relative;
	z-index: 1;
	background: #FFFFFF;
	max-width: 360px;
	margin: 0 auto 100px;
	padding: 45px;
	text-align: center;
	box-shadow: 0 0 20px 0 rgba(0, 0, 0, 0.2), 0 5px 5px 0
		rgba(0, 0, 0, 0.24);
}

.form input {
	font-family: "Roboto", sans-serif;
	outline: 0;
	background: #d4e6ff;
	width: 100%;
	border: 0;
	margin: 0 0 15px;
	padding: 15px;
	box-sizing: border-box;
	font-size: 14px;
}

.form button {
	font-family: "Roboto", sans-serif;
	text-transform: uppercase;
	outline: 0;
	background: #94a4bb;
	width: 100%;
	border: 0;
	padding: 15px;
	color: #FFFFFF;
	font-size: 14px;
	-webkit-transition: all 0.3 ease;
	transition: all 0.3 ease;
	cursor: pointer;
}

.form button:hover,.form button:active,.form button:focus {
	background: #19365f;
}

.form .message {
	margin: 15px 0 0;
	color: #b3b3b3;
	font-size: 12px;
}

.form .message a {
	color: #4CAF50;
	text-decoration: none;
}

.form .register-form {
	display: none;
}

.footer {
    bottom: 0;
    width: 100%;
    height: 25px;
    line-height: 25px;
    background-color: #dce4ef;
    position: absolute;
}
</style>
</head>
<script>
	$('.message a').click(function() {
		$('form').animate({
			height : "toggle",
			opacity : "toggle"
		}, "slow");
	});
</script>
<body ng-controller="controller">
	<div class="login-page">
		<div class="logo">
			<img class="img-logo" alt="Logo Diginet" src="/dsm/static/img/logo_diginet.jpg">
		</div>
		<div class="form">
			<!-- <form action="/dsm/" method="get" class="register-form">
				<input type="text" placeholder="name" /> <input type="password" placeholder="password" /> 
				<input type="text" placeholder="email address" />
				<button>create</button>
				<p class="message">
					Already registered? <a href="#">Sign In</a>
				</p> 
			</form> -->
			<form class="login-form">
				<div id="msgReturn" ng-bind-html="msgReturn"></div>
				<input type="text" ng-model="user" placeholder="usuário" autofocus required="required" /> 
				<input type="password" ng-model="pass" placeholder="senha" required="required" />
				<button ng-click="login()">login</button>    
				<!--<p class="message">
					Not registered? <a href="#">Create an account</a>
				</p> -->
			</form>                  
		</div>
	</div>
	<footer class="footer">
		<div class="container text-center">
			<span class="text-muted">Copyright © 2010-2022 - Digicon S.A. - Todos os direitos reservados</span>
		</div>
	</footer>
</body>
</html>