<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>    
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>My App</title>
</head>
<body>
	<%@include file="_header.jsp" %>
	<h1>Welcome Home!!</h1>

	<h1>${user.name } <a href="mailto:${user.email}">Email</a></h1>
</body>
</html>