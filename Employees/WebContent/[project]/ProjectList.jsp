<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<!-- <link rel="stylesheet" type="text/css" href="/Employees/css/login.css"/> -->
<link rel="stylesheet"
   href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script src="//ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<link rel="stylesheet" type="text/css" href="/Employees/css/proAdd.css"/>
<link rel="stylesheet" type="text/css" href="/Employees/css/list.css"/>
<title>Insert title here</title>

<script type="text/javascript">

	function list_go(emp_id) {
		location.href="/Employees/EmpController?type=empOnelist&id="+emp_id;
	}

	function table_change(row){
 		
		var pro_table = document.getElementById('project_t');
		
		for (var i = 0; i < 4; i++) {
			
			if(i==2){
				var period = pro_table.rows[row].cells[i].innerHTML;
				var strArray = period.split('~');

				pro_table.rows[row].cells[i].innerHTML="<input type = 'text' id='start' class='inputPeriod' value='"+strArray[0]+"'/><input type = 'text' id='end' class='inputPeriod' value='"+strArray[1]+"'/>";
				
				$(".inputPeriod").datepicker({
			         dateFormat : 'yymmdd',
			         monthNamesShort: [ "1월", "2월", "3월", "4월", "5월", "6월", "7월", "8월", "9월", "10월", "11월", "12월" ],
			         changeMonth : true,
			         changeYear : true,
			         yearRange : 'c-100:c+2',
			         onSelect: function (dateText, inst) {
					    	if($("#start").val()!="" && $("#end").val() != "" && $("#start").val()>$("#end").val()){
								alert("프로젝트 시작날짜가 더 큽니다.");
								$("#start").val("");
								$("#end").val("");
								
							}
					  }
				});
				
		        var x = new Date(strArray[0].substring(0,4),strArray[0].substring(5,7)-1,strArray[0].substring(8,10), 0, 0, 0, 0);
		        $("#start").datepicker( "setDate", x );
		        var y = new Date(strArray[1].substring(0,4),strArray[1].substring(5,7)-1,strArray[1].substring(8,10), 0, 0, 0, 0);
		        $("#end").datepicker( "setDate", y );
 
			}else{
				pro_table.rows[row].cells[i].innerHTML="<input type = 'text' value='"+pro_table.rows[row].cells[i].innerHTML+"'/>";
			}
		}
		
	}
	
	/* function table_cancel(row){
		var pro_table = document.getElementById('project_t');
		var inputt = $("#project_t input:text");
		var inputVal = new Array();
		var num = 0;
		for (var i = 0; i < 5; i++) {
			var inputtt = inputt.eq(i);
			
			inputVal[i] = inputtt.val();
			
			if(i==2){
				var p_result= inputtt.val()+"~"+inputt.eq(i+1).val();
				i++;
				pro_table.rows[row].cells[num].innerHTML=p_result;
			}else{
				pro_table.rows[row].cells[num].innerHTML=inputVal[i];
			}

			num++;
			if(num>=4){
				num--;
			} 
			
		}
		
	} */
	
	function getData(row){
		var pro_table = document.getElementById('project_t');
		var inputt = $("#project_t input:text");
		var inputText = new Array();
		for (var i = 0; i < 5; i++) {
			var inputtt = inputt.eq(i);
			inputText[i] = inputtt.val();
		}
		return inputText;
	}

	$(function() {
		$(".p_date").datepicker({
	         dateFormat : 'yymmdd',
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

		$("input[name=modify]").click(function() {
			var btnId = $(this).attr("id");
			$("."+btnId+"view").show();
			$("."+btnId+"first").hide();

			var row = $(this).parents("tr").index();
			table_change(row+1);
			
			 
		});
		
		$("input[name=cancel]").click(function() {
			var btnId = $(this).attr("class");
			$("."+btnId+"view").hide();
			$("."+btnId+"first").show();
			
			parent.document.location.reload();
		});
		
		// 수정
		$("input[name=finish]").click(function() {
			var row = $(this).parents("tr").index();
			var inputText = getData(row+1);

			if(inputText[0]==""){
				alert("프로젝트 명을 입력해주세요.");
			}else if(inputText[1]==""){
				alert("프로젝트 내용을 입력해주세요.");
			}else if(inputText[2]==""){
				alert("프로젝트 시작 기간을 입력해주세요.");
			}else if(inputText[3]==""){
				alert("프로젝트 종료 기간을 입력해주세요.");
			}else if(inputText[4]==""){
				alert("발주처를 입력해주세요.");
			}else{
				$(function() {
					$.ajax({
						type : "get",
						url : "/Employees/ProController",
						data : "type=proModify&p_name="+inputText[0]+"&p_content="+inputText[1]+"&p_start="+inputText[2]
								+"&p_end="+inputText[3]+"&p_order="+inputText[4]+"&p_code="+$("input[name=p_code]").val(),
						dataType : "text",
						success : function(data) {
							if(data.substring(0,4) != "fail"){
								alert("성공");
								parent.document.location.reload();
							}else{
								alert("수정 실패");
							}
						},
						error : function(request, status, error) {
							alert("request: " + request + " status: " + status
									+ " error: " + error);
						}
					});
				});
			}
		});
		
		//삭제
		$("input[name=delete]").click(function() {
			if(confirm("해당 프로젝트에 참여하고 있는 사원의 프로젝트 내역까지 삭제됩니다. 그래도 삭제하시겠습니까?")==true){
				$(function() {
					$.ajax({
						type : "get",
						url : "/Employees/ProController",
						data : "type=proDel&p_code="+$("input[name=p_code]").val(),
						dataType : "text",
						success : function(data) {
							if(data.substring(0,4) != "fail"){
								alert("삭제 성공");
								/* parent.document.location.reload(); */
								location.href="/Employees/ProController?type=proList&cPage="+${pvo.nowPage};
							}else{
								alert("삭제 실패");
							}
						},
						error : function(request, status, error) {
							alert("request: " + request + " status: " + status
									+ " error: " + error);
						}
					});
				});
			}else{
				return;
			}
			
		});
		
		/* var p_num = "${k.p_num}";
		alert(p_num); */
		
		
		// 프로젝트 리스트 팝업창 띄우기
		$(".p_num").click(function() {
			var pro_table = document.getElementById('project_t');
			var row = $(this).parents("tr").index();
			var p_code = pro_table.rows[row+1].cells[5].innerHTML;
			var p_num = pro_table.rows[row+1].cells[4].innerHTML;
			if(p_num!=0){
				$(function() {
					$.ajax({
						type : "get",
						url : "/Employees/ProController",
						data : "type=proEmpList&p_code="+p_code,
						dataType : "json",
						success : function(data) {
							
							newWindow = window.open("[project]/ProEmpListPopup.jsp", "newWindow",
				            "height=200, width=400, resizable=yes");
							
							newWindow.data=data;
						},
						error : function(request, status, error) {
							alert("request: " + request + " status: " + status
									+ " error: " + error);
						}
					});
				});
			}
			
			
		});
		
		$("#pro_search").click(function() {
			if(($("input[name=p_start]").val()!="" ) || ($("input[name=p_end]").val()!="") || ($("input[name=p_name]").val()!="" ) || ($("input[name=p_order]").val()!="" )){
				$("#searchForm").attr("action","/Employees/ProController?type=proSearch").attr("method", "post").submit();
				
			}else{
				alert("하나의 값이라도 입력해주세요");
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
		<h1>프로젝트 목록</h1>
		<hr/>
		<div id="searchFormWrap">
			<form id="searchForm" class="searchForm">
				<label class="f_lable">프로젝트 명</label><input type="text" name="p_name" class="inputText" placeholder="프로젝트 명 입력"/> &nbsp;&nbsp;&nbsp;
				<label class="f_lable">발주처</label><input type="text" name="p_order" class="inputText" placeholder="발주처 입력"/> &nbsp;&nbsp;&nbsp; <br/>
				<label class="f_lable">기간</label><input type="text" name="p_start" class="p_date" readonly placeholder="기간 입력"/>&nbsp;~&nbsp;<input type="text" name="p_end" class="p_date" readonly placeholder="기간 입력"/>
				<input type="button" value="검색" id="pro_search"/>
			</form>
		</div>
		<div class="t_wrap">
		
			<table id="project_t" style="width: 1100px;">
			<thead>
				<tr>
					<th>프로젝트명</th>
					<th>프로젝트 내용</th>
					<th>기간</th>
					<th>발주처</th>
					<th>참여인원</th>
					<th></th>					
				</tr>
			</thead>
			<tbody>
				
				<c:choose>
					<c:when test="${!empty proList }">
						<c:forEach items="${proList}" var="k">
							<tr onmouseover="this.style.background='#E1E1E1'" onmouseout="this.style.background='white'">
								<td>${k.p_name}</td>
								<td>${k.p_content}</td>
								<td>${k.p_start.substring(0,10)}~${k.p_end.substring(0,10)}</td>
								<td>${k.p_order}</td>
								<td class="p_num">${k.p_num}</td>
								<td style="display: none;">${k.p_code}</td>
								<td>
									<div>
										<form>
											<p class="${k.p_code}first">
												<input type="button" value="수정" name="modify" id="${k.p_code}"/>
												<input type="button" value="삭제" name="delete"/>
											</p> 
											<p class="${k.p_code}view" style="display: none;">
												<input type="button" value="확인" name="finish"/>
												<input type="button" value="취소" class="${k.p_code}" name="cancel" />
												<input type="hidden" name="p_code" value="${k.p_code}" />
											</p>
										</form>
									</div>
								</td>
							</tr>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<tr>
							<td colspan="6">프로젝트 내역이 없습니다.</td>
						</tr>
					</c:otherwise>
				</c:choose>

			</tbody>
			<c:choose>
			<c:when test="${!empty proList }">
			<tfoot class="t_foot">
				<tr style="text-align: center; font-size: 14px;">
					<td colspan="6">
						<ul class="paging" style="margin-left: 420px;">
							<c:choose>
								<c:when test="${pvo.beginPage<pvo.pagePerBlock}">
									<li class="disable">이전으로</li>
								</c:when>
								<c:otherwise>
									<li><a href="/Employees/ProController?type=proList&cPage=${pvo.beginPage-pvo.pagePerBlock}">이전으로</a></li>
								</c:otherwise>
							</c:choose>
							
							<c:forEach var="k" begin="${pvo.beginPage}" end="${pvo.endPage}" step="1">
								<c:choose>
									<c:when test="${k==pvo.nowPage}">
										<li class="now">${k}</li>
									</c:when>
									<c:otherwise>
										<li><a href="/Employees/ProController?type=proList&cPage=${k}">${k}</a></li>
									</c:otherwise>
								</c:choose>
							</c:forEach>
							
							<c:choose>
								<c:when test="${pvo.endPage>=pvo.totalPage}">
									<li class="disable">다음으로</li>
								</c:when>
								<c:otherwise>
									<li><a href="/Employees/ProController?type=proList&cPage=${pvo.beginPage+pvo.pagePerBlock}">다음으로</a></li>
								</c:otherwise>
							</c:choose>
						</ul>
					</td>
				</tr>
			</tfoot>
			</c:when>
			</c:choose>
		</table>
		
		</div>
	</div>
</body>
</html>