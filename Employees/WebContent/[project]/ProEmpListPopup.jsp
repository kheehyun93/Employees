<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script type="text/javascript" src="/Employees/js/jquery-3.1.1.min.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript">

	var data;
	var result = "<h2>프로젝트 참여 사원 리스트</h2>";
	result += "<table>";
	result += "<tr><th>사원번호</th><th>이름</th></tr>";
	
	$.each(data,function(key,value){	
		result += "<tr>";
		result += "<td class='td_id'>"+value.e_id+"</td>";
		result += "<td>"+value.e_name+"</td>";
		result += "</tr>";
	});
	result += "</table>";
	
	$(function() {
		$(".resultt").html(result);
		
		$('td').click(function() {
			var id = $(this).closest("tr").find(".td_id").text();
			try {
				window.opener.list_go(id);
				self.close();
			} catch (e) {
				alert("e:"+e);
			}
		});
	});

</script>
</head>
<body>

	<div class="resultt">
		
	</div>
</body>
</html>