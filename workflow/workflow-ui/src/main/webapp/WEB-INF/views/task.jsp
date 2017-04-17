<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>


<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.0/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<title>Task Instance</title>
</head>
<body>
	<section>
		<c:if test="${error == true}">
		<div class="alert alert-error alert-dismissable">
		  <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
		  <strong>Error</strong>  You don't have the permission to claim this task.
		</div>
		</c:if>
		<div class="jumbotron jumbotron-fluid" style="height: 200px;">
			<div class="container">
				<h2>JBPM REPOSITORY</h2>
				<p>Tasks</p>
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
				<li class="active"><a href="/workflow-ui/task/">Tasks</a></li>
				<li><a href="/workflow-ui/processinstance/">Process
						Instances</a></li>
				<!-- <li><a href="/workflow-ui/taskinstance/">Task List</a></li> -->
			</ul>
		</nav>
	</header>

	<div class="container">
		<div class="panel panel-primary">
			<div class="panel-heading">Tasks List</div>
			<div class="panel-body">

				<form method="get" commandName="task"
					modelAttribute="selectedTasksName">

					<div class="form-group col-xs-12 row">
						<p>&nbsp; &nbsp; &nbsp;Select a task to claim</p>
						<div class="col-xs-10">
							<select path="taskIdList" name="taskId"
								class="form-control btn-group inline pull-left">
								<!-- <option value="" selected>Please select process definition</option> -->
								<c:forEach items="${task }" var="item">
									<option value="${item.taskId }">${item.taskId }</option>
								</c:forEach>
							</select>
						</div>
						<div class="col-xs-2">
							<input type="submit" class="btn btn-primary" value="Claim"
								onclick="form.action='/workflow-ui/task/start'" />
						</div>
					</div>
				</form>
				
				<div class="container">
				<form method="post" action="/workflow-ui/task/filter/">
					<div class="col-xs-12 row">
						<div class="filter">
							<button type="submit" name="fltr" id="myTasks"
								class="btn btn-primary" value="myTasks">
								My Tasks <span style="padding-left:20px; border-left: 1px solid; ">${myTasksCount }</span>
							</button>
							<button type="submit" name="fltr" id="unassignedTasks"
								class="btn btn-primary" value="Ready">
								Unassigned Tasks <span style="padding-left:20px; border-left: 1px solid; ">${readyCount }</span>
							</button>
							<button type="submit" name="fltr" id="projectTasks"
								class="btn btn-primary" value="All">
								Project Tasks <span style="padding-left:20px; border-left: 1px solid; ">${projectTaskCount }</span>
							</button>
							<button type="submit" name="fltr" id="inProgressTasks"
								class="btn btn-primary" value="InProgress">
								In Progress Tasks <span style="padding-left:20px; border-left: 1px solid; ">${inProgressCount }</span>
							</button>
							<button type="submit" name="fltr" id="reservedTasks"
								class="btn btn-primary" value="Reserved">
								Reserved Tasks <span style="padding-left:20px; border-left: 1px solid; ">${reservedCount }</span>
							</button>
							<button type="submit" name="fltr" id="completedTasks"
								class="btn btn-primary" value="Completed">
								Completed Tasks <span style="padding-left:20px; border-left: 1px solid; ">${completedCount }</span>
							</button>
						</div>
				</form>
				</div>
				
				<br />
				<div class="tbl  col-xs-12 row">
 					<table class="table ">
						<thead class="thead-inverse ">
							<tr>
								<th>Task Id</th>
								<th>Task Name</th>
								<th>Task Description</th>
								<th>Task Status</th>
								<th>Created By</th>
								<th>Created On</th>
								<th>Actual Owner</th>
								<th>Potential Owner</th>
								<th>Process Id</th>
								<th>Process Instance Id</th>
							</tr>
						</thead>
						<tbody class="thead-default">
							<c:forEach items="${tasks }" var="item">
								<tr class="table-active">
									<td>${item.taskId }</td>
									<td>${item.taskName }</td>
									<td>${item.taskDescription }</td>
									<c:choose>
										<c:when test="${filter==true }">
											<script>
											$('table ').find('th:eq(3)').hide();
											</script>
										</c:when>
										<c:otherwise>
											<td>${item.taskStatus }</td>
										</c:otherwise>
									</c:choose>
									<%-- <td>${item.taskStatus }</td> --%>
									<td>${item.createdBy }</td>
									<td>${item.createdOn }</td>
									<td>${item.actualOwner }</td>
									<td>${item.potentialOwner }</td>
									<td>${item.processId }</td>
									<td>${item.processInstanceId }</td>								
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
</body>
</html>