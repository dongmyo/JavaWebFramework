<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!doctype html>
<html>
<head>
	<meta charset="UTF-8" />
	<title>회원 등록</title>
</head>
<body>
	<jsp:include page="/Header.jsp"/>
	
	<h1>회원 정보</h1>
	
	<form action='update' method='post'>
	번호: <input type='text' name='no' value='${member.no}' readonly /><br />
	이름: <input type='text' name='name' value='${member.name}' /><br />
	이메일: <input type='text' name='email' value='${member.email}' /><br />
	가입일: ${member.createdDate} <br />
	<input type='submit' value='저장' />
	<input type='button' value='삭제' onclick='location.href="delete?no=${member.no}";' />
	<input type='button' value='취소' onclick='location.href="list"' />
	</form>

	<jsp:include page="/Tail.jsp"/>
</body>
</html>
