<%@ page import="com.javalec.ex.ex20_MemberDTO"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="com.javalec.ex.ex20_MemberDAO"%>
<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>Insert title here</title>
</head>
<body>

	<%
		ex20_MemberDAO memberDAO = new ex20_MemberDAO();
		ArrayList<ex20_MemberDTO> dtos = memberDAO.memberSelect();
		
		for(int i=0; i<dtos.size(); i++) {
			ex20_MemberDTO dto = dtos.get(i);
			String seq = dto.getSeq();
			String title = dto.getTitle();
			String content_text = dto.getContent_text();
			String ins_dt = dto.getIns_dt();
			String upd_dt = dto.getUpd_dt();
			
			out.println("seq : " + seq + "<br /> title : " + title + "<br /> content_text : " + content_text + "<br /> ins_dt : " + ins_dt + "<br />w  Upd_dt : " + upd_dt + "<br />" );
		}
		
	%>

</body>
</html>