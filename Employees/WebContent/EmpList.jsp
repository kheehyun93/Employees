<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
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
			<c:when test="${empInfo.e_dept} eq '관리팀'">
				<div>	
					<%@ include file="Menu.jsp" %>
				</div>
			</c:when>
			
			<c:otherwise>
				<div>
					<%@ include file="AdminMenu.jsp" %>
				</div>
			</c:otherwise>
		</c:choose>
		
	</div>
	<div class="sectionWrap">
		
	</div>
</body>
</html>