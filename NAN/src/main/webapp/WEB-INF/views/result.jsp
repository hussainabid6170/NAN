<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page session="false" %>
<html>
<head>
	<title>Login Page</title>
	<style type="text/css">
		
		.mainDiv{
			display:table;
			margin:auto;
		}
	</style>
</head>
<body>
	
	<div class="mainDiv"><h1>Online Exam Portal</h1></div>
	<h3>You have taken up the test successfully</h3>
    		<div style="float:right"><h3>&nbsp; <c:out value="${user.firstName}"></c:out> <c:out value="${user.lastName}"></c:out></h3></div>
    		<br><br><br>
    		<div class="mainDiv">
    			<table>
    				<tr>
    				<td>Total Questions:</td><td> <c:out value="${totalQuestions}"></c:out></td>
    				</tr><tr>
    				<tr>
    				<td>Right Answers:</td><td> <c:out value="${result.score}"></c:out></td>
    				</tr><tr>
    				<td>Percentage: </td><td><c:out value="${percentage}"></c:out></td>
    				</tr>
    			</table><br>
    				<h1>Thank You....</h1>
    		</div>
  	<br>
</body>
</html>
