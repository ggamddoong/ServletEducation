<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<jsp:useBean id="b" scope="request" class="com.javalec.ex.ex16_class"/>
	<%
		b.setId("gz");
		b.setPassword("1234");
		b.setTitle("제목");
		b.setContent("내용");
	%>
	<jsp:forward page="ex16_3_userbean2.jsp"/>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

</body>
</html>