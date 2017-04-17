<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>


<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.0/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<title>Process Definition</title>
</head>

<body>
	<section>
		<div class="jumbotron jumbotron-fluid" style="height: 200px;">
			<div class="container">
				<h2>JBPM REPOSITORY</h2>
				<p>Processes</p>
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
				<li class="active"><a href="/workflow-ui/processdef/">Processes</a></li>
				<li><a href="/workflow-ui/task/">Tasks</a></li>
				<li><a href="/workflow-ui/processinstance/">Process
						Instances</a></li>
				<!-- <li><a href="/workflow-ui/taskinstance/">Task List</a></li> -->
			</ul>
		</nav>
	</header>



	<div class="container">

		<div class="panel panel-primary">
			<div class="panel-heading">Processes List</div>
			<div class="panel-body">

				<form method="get">
					<div class="form-group col-xs-12 row">
						<p>&nbsp; &nbsp; &nbsp;Select a process to start</p>
						<div class="col-xs-10">
							<select path="processName" name="processName"
								class="form-control btn-group inline pull-left ">
								<!-- <option value="" selected>Please select process definition</option> -->
								<c:forEach items="${processDefinitionList}" var="item">
									<option value="${item.name}">${item.name}</option>
								</c:forEach>
							</select>
						</div>
						<div class="col-xs-1 row">
							<input type="submit" class="btn btn-primary" value="Start"
								onclick="form.action='/workflow-ui/process/start'" />
						</div>

						<!-- 						<div class="col-xs-2 row">
						<div class="dropdown">
						    <button class="btn btn-primary glyphicon glyphicon-filter dropdown-toggle" type="button" data-toggle="dropdown"> Filter
							    <span class="caret"></span></button>
							    <ul class="dropdown-menu">
							      <li><a href="#">Monitored</a></li>
							      <li><a href="#">Troubleshoot</a></li>
							      <li><a href="#">Active</a></li>
							    </ul>
						  </div>
						</div> -->
				</form>
			</div>

			<div class="container">
				<form method="post" action="/workflow-ui/processdef/filter/">
					<div class="col-xs-10 row">
						<div class="filter">
							<button type="submit" name="fltr" id="Monitored"
								class="btn btn-primary" value="">
								Monitored <span>${stateMap['monitoredCount']}</span>
							</button>
							<button type="submit" name="fltr" id="Troubleshoot"
								class="btn btn-primary" value="">
								Troubleshoot <span>${stateMap['troubleshootCount']}</span>
							</button>
							<button type="submit" name="fltr" id="Active"
								class="btn btn-primary" value="1">
								Active <span style="padding-left:20px; border-left: 1px solid; " >${stateMap['activeCount']}</span>
							</button>
							<button type="submit" name="fltr" id="Completed"
								class="btn btn-primary" value="2">
								Complete <span  style="padding-left:20px; border-left: 1px solid; ">${stateMap['completedCount']}</span>
							</button>
							<button type="submit" name="fltr" id="Aborted"
								class="btn btn-primary" value="3">
								Aborted <span style="padding-left:20px; border-left: 1px solid; ">${stateMap['abortedCount']}</span>
							</button>
						</div>
				</form>
				</div>
			<%-- 				<div class="tbl">
					<table class="table">
						<thead class="thead-inverse">
							<tr>
								<th>Process Id</th>
								<th>Process Name</th>
								<th>Process Version</th>
								<th>Package</th>
							</tr>
						</thead>
						<tbody class="thead-default">
							<c:forEach items="${processDefinitionList}" var="item">
								<tr class="table-active">
									<td>${item.id }</td>
									<td>${item.name }</td>
									<td>${item.deploymentId }</td>
									<td>${item.packageName }</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div> --%>
				
				<div class="container  col-xs-11 row">
				<table class="table table-hover">
					<thead>
						<tr  class="clickable-row' data-href='url://">
							<th>Nb</th>
							<th>Bag</th>
							<th>Name</th>
							<th>State</th>
							<th>Version</th>
							<th>Date</th>
							<th>Deployment</th>
							<th>Initiator</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${processInstances}" var="item">
							<tr class="table-active">
								<td>${item.processInstanceId }</td>
								<td><a href="https://transferqa.loctest.gov/transter/inventory/bag/369073.html">afc2001019a</a></td>
								<td>${item.processInstanceName }</td>
								<c:choose>
									<c:when test="${empty item.status }">
										<script>
										$('table ').find('th:eq(2)').hide();
										</script>
									</c:when>
									<c:otherwise>
										<td>${item.status }</td>
									</c:otherwise>
								</c:choose>
								<td>${item.version }</td>
								<td>${item.startDate }</td>
								<td>${item.deploymentId }</td>
								<td>${item.initiator }</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				</div>
</div>
</div>
<!-- <script type="text/javascript">
$(document).ready(function(){
	  $('table tr').click(function(){
	     alert($(this).html());
	  });
	  
	  });
</script> -->
</body>
</html>