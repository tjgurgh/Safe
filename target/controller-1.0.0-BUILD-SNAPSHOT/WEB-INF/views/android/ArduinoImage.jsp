<%@page import="java.util.ArrayList"%>
<%@page import="org.board.Android.*"%>
<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<%

    AuduinoImage.getTypedFileDown();
	Thread.sleep(10000);
	response.sendRedirect("/android/PhotoAlert");

%>
<script>
$(document).ready(function () 
{
    setTimeout(function()
    {
       location.href("/android/ArduinoImage");
    },1000);
});
</script>