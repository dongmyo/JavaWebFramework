<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<jsp:useBean id="member" scope="session" class="vo.Member"/>

<div style="background-color:#00008b;color:#ffffff;height:20px;padding: 5px;">
Java Web Framework

<% if (member.getEmail() != null) { %>
	<span style="float:right;">
		<%= member.getName() %>
		<a style="color:white;" href="<%= request.getContextPath() %>/auth/logout.do">로그아웃</a>
	</span>
<% } %>
</div>
