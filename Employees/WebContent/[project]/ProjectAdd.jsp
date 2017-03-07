<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet"
   href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script src="//ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<link rel="stylesheet" type="text/css" href="/Employees/css/proAdd.css"/>
<script type="text/javascript">
 	$(function() {
		
		$(".f_date").datepicker({
	         dateFormat : 'yy-mm-dd',
	         monthNamesShort: [ "1월", "2월", "3월", "4월", "5월", "6월", "7월", "8월", "9월", "10월", "11월", "12월" ],
	         changeMonth : true,
	         changeYear : true,
	         yearRange : 'c-100:c+2',
	         onSelect: function (dateText, inst) {
			    	if($("input[name=p_start]").val()!="" && $("input[name=p_end]").val() != "" && $("input[name=p_start]").val()>$("input[name=p_end]").val()){
						alert("프로젝트 시작날짜가 더 큽니다.");
						$("input[name=p_start]").val("");
						$("input[name=p_end]").val("");
						
					}
			    }
		});
		
		
		 $("#proAddBtn").click(function() {
			if($("input[name=p_name]").val()==""){
				alert("프로젝트 명을 입력해주세요.");
			}else if($("input[name=p_content]").val()==""){
				alert("프로젝트 내용을 입력해주세요.");
			}else if($("input[name=p_start]").val()==""){
				alert("프로젝트 시작 날짜를 입력해주세요.");
			}else if($("input[name=p_end]").val()==""){
				alert("프로젝트 종료 날짜를 입력해주세요.");
			}else if($("input[name=p_start]").val()>$("input[name=p_end]").val()){
		 		alert("프로젝트 시작날짜가 더 큽니다.");
		 	}else if($("input[name=p_order]").val()==""){
				alert("발주처를 입력해주세요.");
			}else{
				$(function() {
					$.ajax({
						type : "get",
						url : "/Employees/ProController",
						data : "type=proAdd&p_name="+$("input[name=p_name]").val()+"&p_content="+$("input[name=p_content]").val()
								+"&p_start="+$("input[name=p_start]").val()+"&p_end="+$("input[name=p_end]").val()
								+"&p_order="+$("input[name=p_order]").val(),
						dataType : "text",
						success: function(data) {
							if(data.substring(0,4) != "fail"){
								alert("등록 성공");
								//location.href=data;
								
								$(".f_wrap").children().find('input:text').val("");
							}else{
								alert("등록 실패");
							}
						},
						error: function(request, status, error) {
							alert("request: " + request + " status: " + status
									+ " error: " + error);
						}
					});
				});
			}
		});
		
			
		
	});
	
</script>
</head>
<body>
	 <div>
		<c:choose>
			<c:when test="${empInfo.e_dept eq '관리팀'}">
				<div>	
					<%-- <%@ include file="/Employees/WebContent/[employees]/AdminMenu.jsp" %> --%>
					<jsp:include page="../[menu]/AdminMenu.jsp" />
				</div>
			</c:when>
			
			<c:otherwise>
				<div>
					<%-- <%@ include file="Menu.jsp" %> --%>
					<jsp:include page="../[menu]/Menu.jsp" />
				</div>
			</c:otherwise>
		</c:choose>
		
	</div> 
	<div class="sectionWrap">
		<h1>프로젝트 등록</h1>
		<hr/>
		<div class="f_wrap">
			
			<form>
				<label class="f_label">프로젝트 명</label> <br/><input type="text" name="p_name" class="inputText"/><br/>
				<label class="f_label">프로젝트 내용</label> <br/><input type="text" name="p_content" class="inputText"/><br/>
				<label class="f_label">기간</label> <br/><input type="text" name="p_start" id="p_start" readonly class="f_date datePicker"/> ~ <input type="text" name="p_end" id="p_end" readonly class="f_date datePicker"/><br/>
				<label class="f_label">발주처</label> <br/><input type="text" name="p_order" class="inputText"/><br/>
				<input type="button" value="등록" id="proAddBtn"/>
			</form>
		</div>
	</div>
</body>
</html>