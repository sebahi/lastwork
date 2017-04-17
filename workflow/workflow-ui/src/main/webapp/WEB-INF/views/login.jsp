<%@ page language="java" contentType="text/html; charset=windows-1256" pageEncoding="windows-1256"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=windows-8859-1">
<link rel="stylesheet"
	href="//netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap.min.css">
<title>JBPM</title>
</head>
<body>
	<section>
	<div class="jumbotron jumbotron-fluid" style="height: 200px;">
		<div class="container">
			<h2>JBPM REPOSITORY</h2>
			<p>Process Management</p>
		</div>
	</div>
	</section>
	
	<div class="container">
		<div class="row">
			<div class="col-md-4 col-md-offset-4">
				<div class="panel panel-default">
					<div class="panel-heading">
						<h3 class="panel-title">Please sign in</h3>
					</div>
					<div class="panel-body">
						<c:if test="${not empty error}">
						<div class="alert alert-danger">
							<spring:message code="AbstractUserDetailsAuthenticationProvider.badCredentials"/><br />
						</div>
						</c:if>
						<form action="<c:url value="/j_spring_security_check"></c:url>" method="post">
						<fieldset>
							<div class="form-group">
								<input class="form-control" placeholder="User Name" name='j_username' type="text">
							</div>
							<div class="form-group">
								<input class="form-control" placeholder="Password" name='j_password' type="password" value="">
							</div>
							<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
							<input class="btn btn-primary btn-block" type="submit" value="Login">
						</fieldset>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>