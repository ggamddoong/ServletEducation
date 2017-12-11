<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<jsp:useBean id="b" scope="request" class="com.javalec.ex.ex16_class"/>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>게시판</title>
</head>
<body>
	아이디 : <%= b.getId() %><br>
	비밀번호 : <%= b.getPassword() %><br>
	제목 : <%= b.getTitle() %><br>
	내용 : <%= b.getContent() %><br>
</body>
</html>
