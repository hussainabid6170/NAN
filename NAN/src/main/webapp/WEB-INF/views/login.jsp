<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page session="false" %>
<html>
<head>
	<title>Login Page</title>
	<style type="text/css">
		.tg  {border-collapse:collapse;border-spacing:0;border-color:#ccc;}
		.tg td{font-family:Arial, sans-serif;font-size:14px;padding:10px 5px;border-style:solid;border-width:1px;overflow:hidden;word-break:normal;border-color:#ccc;color:#333;background-color:#fff;}
		.tg th{font-family:Arial, sans-serif;font-size:14px;font-weight:normal;padding:10px 5px;border-style:solid;border-width:1px;overflow:hidden;word-break:normal;border-color:#ccc;color:#333;background-color:#f0f0f0;}
		.tg .tg-4eph{background-color:#f9f9f9}
		.mainDiv{
			display:table;
			margin:auto;
		}
	</style>
</head>
<body>
	
	<div class="mainDiv"><h1>Online Exam Portal</h1></div>
	<c:url var="loginAction" value="/login/validate" ></c:url>
	<c:choose>
	<c:when test="${status != 'valid'}">
		<div class="mainDiv">
		<c:if test="${!empty msg}">
			<div style="color:#7e0000;"><c:out value="${msg}"></c:out></div>
		</c:if>
		<div class="mainDiv">
		<h3>
				<div class="mainDiv">Please login Here</div>
		</h3>
		<form action="${loginAction}" method="post">
			<table>
				<tr>
					<td>
						<label path="User name">
							<spring:message text="Name"/>
						</label>
					</td>
					<td>
						<input id="username" name="username" />
					</td> 
				</tr>
				<tr>
					<td>
						<label path="password">
							<spring:message text="Password"/>
						</label>
					</td>
					<td>
						<input type="password" id="password" name="password" />
					</td>
				</tr>
				<tr>
					<td colspan="2">
							<div class="mainDiv"><input type="submit" value="<spring:message text="Login"/>" /></div>
					</td>
				</tr>
			</table>	
		</form>
		</div>
		</div>
	 </c:when>
  	<c:otherwise>
    		<div style="float:right"><h3>Welcome &nbsp; <c:out value="${user.firstName}"></c:out> <c:out value="${user.lastName}"></c:out></h3></div>
    		<br><br><br>
    		<div class="mainDiv">
    			<table><tr>
    				<td>Name of the Exam:</td><td> <c:out value="${testDetails.testName}"></c:out></td>
    				</tr><tr>
    				<td>Exam Description: </td><td><c:out value="${testDetails.testDescription}"></c:out></td>
    				</tr><tr>
    				<td>Duration of test will be: </td><td><c:out value="${testDetails.testDuration}"></c:out> Minutes</td>
    				</tr>
    			</table><br>
    				Please click start button below to begin exam, Good Luck!....
    				<c:url var="beginExam" value="/beginExam" ></c:url>
    				<form action="${beginExam}" method="post">
    					<div class="mainDiv"><input type="submit" value="<spring:message text="Start Exam"/>" /></div>
    				</form>
    		</div>
  	</c:otherwise>
  	</c:choose>
  	
	<br>
</body>
</html>
