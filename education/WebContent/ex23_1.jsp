<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	{10}=${10}<br />
	{99.99}=${99.99}<br />
	"ABC"=${"ABC"}<br />
	'ABC'=${'ABC'}<br />
	{true}=${true}<br />
	{null}=${null}<br />
	
	{ 1+2 }=${ 1+2 } <br />
	{ 1-2 }=${ 1-2 } <br />
	{ 1*2 }=${ 1*2 } <br />
	{ 1/2 }=${ 1/2 } <br />

	{ 1 div 2 } = ${ 1 div 2 }<br />
	{ 1 % 2 }=${ 1 % 2 } <br />
	{ 1 mod 2 }=${ 1 mod 2 } 
	{ 1>2 }=${ 1>2 } <br><br />
	{ 1 gt 2 }=${ 1 gt 2 } <br />
	{ 1<2 }=${ 1<2 } <br />
	{ 1 lt 2 }=${ 1 lt 2 } <br />
	{ (1>2) ? 1 : 2 }=${ (1>2) ? 1 : 2 } <br />
	(1>2) || (1<2)=${ (1>2) || (1<2) } <br />
	{true && false } = ${true && false } <br />
	{true and false }=${true and false } <br />
	{false || true } = ${false || true } <br />
	{false or true } = ${false or true } <br />
	{not true} = ${not true} <br />
	{!true} = ${!true} <br />
	
	<% pageContext.setAttribute("title","EL 연산자!"); %>
	{empty title} = ${empty title}  <br />
	{empty title2} = ${empty title2}  <br />
</body>
</html>