//ready function is called when the page is ready
$(document).ready(function(){
	renderQuestion();
	startTimer(seconds);
	checkForValidity();
});
/** this function will display time left in seconds like a ticking timer on the page ***/
function startTimer(seconds){
     var sec = seconds;
     setInterval(function(){
     	sec--;
       document.getElementById("timer").innerHTML = sec ;
       if(sec == 00){
            alert("Time Elapsed, We will show you result now");
            finishExam();
         }
      },1000);
 }
 /** this function will call server validation functin every 2 seconds to check is time has expired or not **/ 
 function checkForValidity(){
   setInterval(function(){
 	$.ajax({
		type: 'GET',
		url: "checkTestValidity",
		dataType: 'json',
		cache: false,
		success: function(data){
			if(data.status == 'expired'){
				document.getElementById("timer").innerHTML = "0" ;
				alert("Time Elapsed, We will show you result now");
            	finishExam();
			}
		}
	});
	 },2000);
 }
 // calls a submitAnswer service that will store the answer of question given by user
function submitAnswer(){
	var questionId = $('#questionNo').val();
	var answer = $('input[name=answer]:checked').val();
	$.ajax({
		type: 'GET',
		url: "submitAnswer?questionId="+questionId+"&answer="+answer,
		dataType: 'json',
		cache: false,
		success: function(data){
			alert(data.msg);
		}
	});
}
/** this will finish the exam, can be from finish button or time elapsed functions **/ 
function finishExam(){
	document.getElementById('finish_exam').submit();
}
/** validation function to check if user has given answers to all the question **/
function checkExam(){
	$.ajax({
		type: 'GET',
		url: "checkAllAnswered",
		dataType: 'json',
		cache: false,
		success: function(data){
			if(data.status == 'success'){
				finishExam();
			}else{
				if (confirm(data.msg)) {
    				finishExam();
				} else {
   					alert('Questions that are unanswered are '+data.unansweredquestions);
				}
			}
		}
	});
}
/** when question no choosen by user from select combo this will get a question from server and wil render it on web page **/
function renderQuestion(){
	var questionId = $('#questionNo').val(); //accessing what user has entered in text box
	$.ajax({
		type: 'GET',
		url: "getQuestion?questionId="+questionId,
		dataType: 'json',
		cache: false,
		success: function(data){
			var html = "<br><h3>Question : "+data.question+"</h3>";
			html += "<input type='radio' value='option1' name='answer'/>"+data.option1+"<br>";
			html += "<input type='radio' value='option2' name='answer' />"+data.option2+"<br>";
			html += "<input type='radio' value='option3' name='answer' />"+data.option3+"<br>";
			html += "<input type='radio' value='option4' name='answer' />"+data.option4+"<br><br>";
			html += "<div class='mainDiv'><input type='button' value='Submit' name='answer' onclick='submitAnswer()'/>";
			html += "<input type='button' value='Finish Exam' onclick='checkExam();'/></div>";
			$('#questionDiv').html(html);
			if(data.hasOwnProperty('useranswer')){
				var answers = document.getElementsByName("answer");
				for(var i = 0; i < answers.length; i++) {
  					 if(answers[i].value == data.useranswer) {
      				 	answers[i].checked=true;
   					}
 				}
			}
		}
	});
}