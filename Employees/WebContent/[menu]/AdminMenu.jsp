<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="/Employees/css/menu.css"/>
<link rel="stylesheet"
   href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script src="//ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script type="text/javascript">

	function myinfo() {
		location.href="/Employees/EmpController?type=empOnelist&id="+${empInfo.e_id};
	}
</script>
</head>
<body>
	<div class="wrap">
		<div id="top">
			<img src="/Employees/img/ican.PNG"/>
		</div>
		<hr/>
		<div class="menubar">
			<div id="emp">
				<a class="topList">사원 정보</a>
				<div class="bottomList">
					<a class="list" href="/Employees/EmpController?type=empList">사원 목록</a><br/>
					<a class="list" href="/Employees/EmpController?type=empAdd_go">사원 등록</a><br/>
					<!-- <a class="list" href="/Employees/[employees]/EmployeesAdd2.jsp">사원 등록</a><br/> -->
				</div>
			</div>
			<div id="pro">
				<a class="topList">프로젝트 정보</a>
				<div class="bottomList">
					<a class="list" href="/Employees/ProController?type=proList">프로젝트 목록</a><br/>
					<a class="list" href="/Employees/[project]/ProjectAdd.jsp">프로젝트 등록</a><br/>
				</div>
			</div>
			
		</div>
	</div>
	<div class="header">
		${empInfo.e_name}님&nbsp;
		<a id="info" onclick="myinfo()">내정보</a>&nbsp;&nbsp;&nbsp;&nbsp;
		<a id="logout" href="/Employees/LoginController?type=logout">로그아웃</a>
	</div>
</body>
</html>