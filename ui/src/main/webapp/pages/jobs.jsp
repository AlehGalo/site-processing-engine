<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<!-- saved from url=(0050)http://getbootstrap.com/examples/navbar-fixed-top/ -->
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">
<!-- <link rel="shortcut icon"
    href="http://getbootstrap.com/assets/ico/favicon.ico">
 -->
<title>Informer main page</title>

<!-- Bootstrap core CSS -->
<link href="../css/bootstrap.min.css" rel="stylesheet">


<link href="../css/navbar-fixed-top.css" rel="stylesheet">

<!-- Just for debugging purposes. Don't actually copy this line! -->
<!--[if lt IE 9]><script src="../../assets/js/ie8-responsive-file-warning.js"></script><![endif]-->

<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
      <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>

<body>

	<!-- Fixed navbar -->
	<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">

		<div class="container">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle" data-toggle="collapse"
					data-target=".navbar-collapse">
					<span class="sr-only">Toggle navigation</span> <span
						class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="#">Informer</a>
			</div>
			<div class="navbar-collapse collapse">
				<ul class="nav navbar-nav">
					<li class="active"><a href="#">Home</a></li>
					<li><a href="#">About</a></li>
					<li><a href="#">Contact</a></li>
					<li class="dropdown"><a href="#" class="dropdown-toggle"
						data-toggle="dropdown">Dropdown <b class="caret"></b></a>
						<ul class="dropdown-menu">
							<li><a href="#">Action</a></li>
							<li><a href="#">Another action</a></li>
							<li><a href="#">Something else here</a></li>
							<li class="divider"></li>
							<li class="dropdown-header">Nav header</li>
							<li><a href="#">Separated link</a></li>
							<li><a href="#">One more separated link</a></li>
						</ul></li>
				</ul>
				<ul class="nav navbar-nav navbar-right">
					<li><a href="#">Default</a></li>
					<li><a href="#">Static top</a></li>
					<li class="active"><a href="#">Fixed top</a></li>
				</ul>
			</div>
			<!--/.nav-collapse -->
		</div>
	</nav>
	<div class="container">


		<div class="panel panel-default">
			<!-- Default panel contents -->
			<div class="panel-heading">
				<h5>Number of records ${count}</h5>
			</div>
			<table id="mainTableId"
				class="table table-striped table-bordered table-hover">
				<thead>
					<tr>
						<th>#</th>
						<th>Site</th>
						<th>Start - End Time</th>
						<th>Status</th>
					</tr>
				</thead>
				<tbody>
					<c:choose>
						<c:when test="${not empty lists}">
							<c:forEach var="job" items="${lists}" varStatus="status">
								<tr>
									<td><c:out value="${status.count}" /></td>
									<td><c:out value="${job.siteUrl}" /></td>
									<td><c:out value="${job.startTime}" />&nbsp;&nbsp;<c:out
											value="${job.endTime}" /></td>
									<td><c:out value="${job.status}" /></td>
								</tr>
							</c:forEach>
						</c:when>
						<c:otherwise>
							<h5>No Data</h5>
						</c:otherwise>
					</c:choose>
				<tfoot>
					<tr>
						<th>#</th>
						<th>Title</th>
						<th>in progress</th>
						<th>in progress</th>
					</tr>
				</tfoot>
				</tbody>
			</table>
		</div>
	</div>
	<script src="../js/jquery.min.js"></script>
	<script src="../js/bootstrap.min.js"></script>
</body>
</html>