<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>Insert title here</title>
</head>
<body>

	<a href="insert.ex25_2">insert</a>
	<hr />
	<a href="http://localhost:8181<%=request.getContextPath()%>/update.ex25_2">update</a>
	<hr />
	<a href="http://localhost:8181/education/select.ex25_2">select</a>
	<hr />
	<a href="<%=request.getContextPath()%>/delete.ex25_2">delete</a>

</body>
</html>