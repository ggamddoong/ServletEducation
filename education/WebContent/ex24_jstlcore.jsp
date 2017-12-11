<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>     
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<c:out value="�ȳ��ϼ���!"/> <br />
<c:out value="${null}">�ݰ����ϴ�. </c:out>  <br />
<c:out value="�ȳ��ϼ���!">�ݰ����ϴ�. </c:out> <br />
<c:out value="${null}"/><br />

	<h3>c:set</h3>
	<c:set var="vatName" value="varValue"/>
	vatName : <c:out value="${vatName}"/>
	
	<h3>�⺻ ������</h3>
	<c:set var="vatName" value="varValue"/>
	pageScope : ${pageScope.vatName}<br />
	requestScope : ${requestScope.vatName}
	<br />
	
	<h3>������ ����</h3>
	<c:set var="vatName" value="varValue2" scope="request"/>
	pageScope : ${pageScope.vatName}<br />
	requestScope : ${requestScope.vatName}
	<br />
	
	<%!
	public static class MyMember {
		int no;
		String name;
		
		public int getNo(){
			return no;
		}
		public void setNo(int no){
			this.no = no;
		}
		public String getName(){
			return name;
		}
		public void setName(String name){
			this.name = name;
		}
	}
	
	%>
	<%
	MyMember member = new MyMember();
	member.setNo(100);
	member.setName("ȫ�浿");
	pageContext.setAttribute("member", member);
	%>
	
	<h3>��ü�� ������Ƽ �� ���� ��</h3>
	${member.name} <br>
	<c:set target="${member}" property="name"  value="�Ӳ���" />	
	<h3>��ü�� ������Ƽ �� ���� ��</h3>
	${member.name} <br>
	
	<h3>c:remove</h3>
	<c:remove var="member"/>
	member.name : <c:out value="${member.name}"/></h3>	
	<hr />
	
		
	<c:catch var="error">
		<%=2/0%>
	</c:catch>
	<br />
	<c:out value="${error}"/>
	
	<hr />

	<c:if test="${1+2==3}">
		1 + 2 = 3
	</c:if>
	
	<c:if test="${1+2!=3}">
		1 + 2 != 3
	</c:if>
	
	<hr />

	<c:forEach var="fEach" begin="0" end="30" step="3">
		${fEach}
	</c:forEach>

<br />
<h3>�迭</h3>
<% pageContext.setAttribute("nameList", new String[] {"ȫ�浿","�Ӳ���","������"}); %>
<c:forEach var="name" items="${nameList}">
	<li>${name}</li>
</c:forEach>
<br />

<h3>�迭�� ���� �ε����� ���� �ε��� ����</h3>
<% pageContext.setAttribute("nameList2", new String[] {"ȫ�浿","�Ӳ���","������", "�ָԴ���", "�����屺"}); %>
<c:forEach var="name" items="${nameList2}" begin="2" end="3" >
	<li>${name}</li>
</c:forEach>
<br />

<h3>�޸��� ���е� ���ڿ�</h3>
<% pageContext.setAttribute("nameList3","ȫ�浿,�Ӳ���,������,�ָԴ���,�����屺"); %>
<c:forEach var="name" items="${nameList3}"  >
	<li>${name}</li>
</c:forEach>
<br />

<h3>�迭�̳� List���� Ư�� Ƚ�� ��ŭ �ݺ�</h3>
<c:forEach var="no" begin="1" end="6" >
	<li>${no}</li>
</c:forEach>

<h3>forTokens</h3>
<% pageContext.setAttribute("tokens","v1=20&v2=30&op=+"); %>
<c:forTokens var="item" items="${tokens}" delims="&" >
	<li>${item}</li>
</c:forTokens>
<br />

<h3>c:url</h3>
<c:url var="ex" value ="/ex7">
	<c:param name="name" value="hahaha" />
</c:url>
<a href="${ex}">url tag</a>


<h3>c:import</h3>
<c:import var="zdnetRSS" url="http://www.zdnet.co.kr/Include/ZDNetKorea_Nes.xml" />
<textarea rows="10" cols="80">${zdnetRSS}</textarea>

<br />
<fmt:parseDate var="dd" value="2017-11-06" pattern ="yyyy-MM-dd" />
${dd}
<br />
<fmt:formatDate value="${dd}" pattern="MM/dd/yy" />
</body>
</html>