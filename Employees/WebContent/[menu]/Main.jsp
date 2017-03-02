<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="/Employees/js/jquery-3.1.1.min.js"></script>
<style type="text/css">
	.sectionWrap{
		margin-left: 244px;
	}
	.sectionWrap img{
		width: 1250px;
	}
</style>
</head>
<body>
	<div>
		<c:choose>
			<c:when test="${empInfo.e_dept eq '관리팀'}">
				<div>	
					<jsp:include page="AdminMenu.jsp" />
				</div>
			</c:when>
			
			<c:otherwise>
				<div>
					<jsp:include page="Menu.jsp" />
				</div>
			</c:otherwise>
		</c:choose>
		
	</div>
	<div class="sectionWrap">
		<img src="/Employees/img/icanMain.PNG"/>
	</div>
</body>
</html>