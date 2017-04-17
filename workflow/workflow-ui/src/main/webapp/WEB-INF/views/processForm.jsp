<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet"
	href="//netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap.min.css">

<script
	src="https://ajax.googleapis.com/ajax/libs/angularjs/1.0.1/angular.min.js"></script>
<script src="/webapp/resources/js/controller.js"></script>
<style>
input[type="checkbox"] {
	width: 18px;
	height: 18px;
}
</style>
<title>Process Form</title>
</head>
<body>
	<section>
	<div class="jumbotron jumbotron-fluid" style="height: 200px;">
		<div class="container">
			<h2>JBPM REPOSITORY</h2>
			<p>Process Management</p>
			<button type="button" class="btn btn-default btn-sm pull-right"
				onclick="location.href='/workflow-ui'">
				<span class="glyphicon glyphicon-log-out"></span> Log out
			</button>
			<button type="button" class="btn btn-primary btn-sm pull-right"
				onclick="location.href='/workflow-ui/processdef'">
				<span class="glyphicon glyphicon-home"></span> Home
			</button>
		</div>
	</div>
	</section>
	<div class="container">
		<div class="panel panel-primary">
			<div class="panel-heading">Process Form</div>
			<div class="panel-body">
				<div class="form-group">
					<form:form class="form-vertical col-xs-8" method="GET"
						action="/workflow-ui/process/submit" modelAttribute="bag">

						<input type="hidden" name="pdId" value="${processDefinition.id}">                         
						<input type="hidden" name="pdDeploymentId" value="${processDefinition.deploymentId}">   
	                      
						<div>
							<label class="col-sm-4 control-label">Bag Id</label>
							<div class="form-group col-sm-6">
								<form:input class="form-control" type="text" path="bagId" />
							</div>
						</div>
						<div>
							<label class="col-sm-4 control-label">Do Inventory?</label>
							<div class="form-group col-sm-6">
								<form:checkbox path="doInventory" />
							</div>
						</div>
						<div>
							<label class="col-sm-4 control-label">Do Malware Scan?</label>
							<div class="form-group col-sm-6">
								<form:checkbox path="doMalwareScan" />
							</div>
						</div>

						<label class="col-sm-4 control-label">Do Bag-In Place?</label>
						<div class="form-group col-sm-6">
							<form:checkbox path="doBagInPlace" />
						</div>

						<label class="col-sm-4 control-label">Do Verify?</label>
						<div class="form-group col-sm-6">
							<form:checkbox path="doVerify" />
						</div>


						<label class="col-sm-4 control-label">Do Write Bag Info?</label>
						<div class="form-group col-sm-6">
							<form:checkbox path="doWriteBagInfo" />
						</div>

						<label class="col-sm-4 control-label">Do Copy?</label>
						<div class="form-group col-sm-6">
							<form:checkbox path="doCopy" />
							<form:input  class="form-control" type="text"
								path="numberOfCopies" placeholder="Enter number of copies " />
						</div>
						
						<label class="col-sm-4 control-label">Do Export?</label>
						<div class="form-group col-sm-6">
							<form:checkbox path="doExport" />
						</div>

						<label class="col-sm-4 control-label">Do Delete From
							Staging?</label>
						<div class="form-group col-sm-6">
							<form:checkbox path="doDeleteFromStaging" />
						</div>
				</div>

				<div class="col-sm-10">
					<br> <br> <input type="submit"
						class="btn btn-primary pull-right" value="Submit"
						onclick="location.href='http://'+${server}+'/workflow-ui/processdef'" />
				</div>


			</div>
		</div>
	</div>

	</form:form>
</body>
</html>