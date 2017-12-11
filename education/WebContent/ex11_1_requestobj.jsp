<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>Insert title here</title>
</head>
<body>
<!-- 그냥 바로 url로 실행해야 될 page -->
	<%
		out.println("서버 : " + request.getServerName() + "<br />");
		out.println("포트 번호 : " + request.getServerPort() + "<br />");
		out.println("요청 방식 : " + request.getMethod() + "<br />");
		out.println("프로토콜 : " + request.getProtocol() + "<br />");
		out.println("URL : " + request.getRequestURL() + "<br />");
		out.println("URI : " + request.getRequestURI() + "<br />");
	%>

</body>
</html>