<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<jsp:useBean id="b" class="com.javalec.ex.ex16_class" scope="page" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>Insert title here</title>
</head>
<body>
	
	<jsp:setProperty name="b" property="id" value="shkim3"/>
	<jsp:setProperty name="b" property="password" value="1234"/>
	<jsp:setProperty name="b" property="title" value="�����Դϴ�."/>
	<jsp:setProperty name="b" property="content" value="�����Դϴ�."/>
	
	id : <jsp:getProperty name="b" property="id" /><br />
	pw : <jsp:getProperty name="b" property="password" /><br />
	���� : <jsp:getProperty name="b" property="title" /><br />
	���� : <jsp:getProperty name="b" property="content" /><br />


</body>
</html>