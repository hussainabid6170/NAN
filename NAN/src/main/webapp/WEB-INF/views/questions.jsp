<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page session="false" %>
<html>
<head>
	<title>Login Page</title>
	<script src="resources/js/jQuery-1.11.js" type="text/javascript"></script>
	<script src="resources/js/exam.js" type="text/javascript"></script>
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
	<script>
		var seconds = ${duration};
	</script>
</head>
<body>
	<div class="mainDiv"><h1>Exam in progress</h1></div>
	<div style="color:red;">
   		Time left is <span id="timer"></span> Seconds!
   </div>
	<div class="mainDiv">
		<div class="mainDiv">
			Select question <Select id="questionNo" onchange="renderQuestion();" name="questionNo">
	<c:forEach items="${questionList}" var="question">
		<option value="${question.id}"> <c:out value="${question.id}"></c:out></option>
	</c:forEach>
	</select></div>
	<div id="questionDiv"></div>
	</div>
	<form action="/CrossOverProject/finishExam" id="finish_exam" name="finish_exam" method="post">
    	
    </form>
</body>
</html>
