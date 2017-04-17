<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>


<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<title>Task Instance</title>
</head>
<body>
	<section>
		<div class="jumbotron jumbotron-fluid" style="height: 200px;">
			<div class="container">
				<h2>JBPM REPOSITORY</h2>
				<p>Tasks Management</p>
				<button type="button" class="btn btn-default btn-sm pull-right"
					onclick="location.href='/workflow-ui'">
					<span class="glyphicon glyphicon-log-out"></span> Log out
				</button>
			</div>
		</div>
	</section>

	<header class="navbar navbar-light navbar-toggleable-md bd-navbar">
		<nav class="container">
			<ul class="nav nav-tabs">
				<li><a href="/workflow-ui/processdef/">Processes</a></li>
				<li><a href="/workflow-ui/task/">Tasks</a></li>
				<li><a href="/workflow-ui/processinstance/">Process Instances</a></li>
				<!-- <li class="active"><a href="/workflow-ui/taskinstance/">Task
						List</a></li> -->
			</ul>
		</nav>
	</header>

	<div class="container">
		<div class="panel panel-primary">
			<div class="panel-heading">Processes List</div>
			<div class="panel-body">

				<form method="get">
					<div class="tbl">
						<table class="table">
							<thead>
								<tr>
									<th>#</th>
									<th>Task</th>
									<th>Status</th>
									<th>Priority</th>
									<th>Created On</th>
								</tr>
							</thead>
							<tbody>

								<c:forEach items="${task}" var="item">
									<c:set var="index" value="${index + 1}" />
									<tr class="table-active">
										<td data-name="#">${index}</td>
										<td data-name="Task">${item.task }</td>
										<td data-name="Status">${item.status }</td>
										<td>${item.priority }</td>
										<td>${item.createdOn }</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>

				</form>
			</div>
		</div>
	</div>
</body>
</html>