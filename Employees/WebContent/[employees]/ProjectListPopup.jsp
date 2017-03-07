<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet"
   href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script src="//ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<link rel="stylesheet" type="text/css" href="/Employees/css/popuplist.css"/>
<title>Insert title here</title>

<script type="text/javascript">

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
		
		var start = "${param.p_start }";
		var end = "${param.p_end }";
		
		if(start!=""){
			var s_date = new Date(start.substring(0,4),start.substring(4,6)-1,start.substring(6,8), 0, 0, 0, 0);
	        $("#f_start").datepicker( "setDate", s_date );
		}
		if(end!=""){
			var e_date = new Date(end.substring(0,4),end.substring(4,6)-1,end.substring(6,8), 0, 0, 0, 0);
	        $("#f_end").datepicker( "setDate", e_date );
		}
		
		var prow = "${requestScope.prow}";
		
		$(".profindSel").click(function() {
			var pro_table = document.getElementById('project_t');
			var row = $(this).parents("tr").index();
			
			var p_name = pro_table.rows[row+1].cells[0].innerHTML;
			var p_content = pro_table.rows[row+1].cells[1].innerHTML;
			var period = pro_table.rows[row+1].cells[2].innerHTML;
			var p_arr = period.split("~");
			var p_order = pro_table.rows[row+1].cells[3].innerHTML;
			var p_code = pro_table.rows[row+1].cells[5].innerHTML;
			
			try {
				window.opener.list_go(p_name,p_content,p_order,p_code,prow,p_arr[0],p_arr[1]);
				self.close();
			} catch (e) {
				alert("e:"+e);
			}
		});

		$("#pro_search").click(function() {
			if(($("input[name=p_start]").val()!="" ) || ($("input[name=p_end]").val()!="") || ($("input[name=p_name]").val()!="" ) || ($("input[name=p_order]").val()!="" )){
				$("#searchForm").attr("action","ProController?type=proFindSearch&prow="+prow).attr("method", "post").submit();
				
			}else{
				alert("하나의 값이라도 입력해주세요");
			}
		});

	});
</script>
</head>
<body>
	<div class="sectionWrap">
		<h1>프로젝트 목록</h1>
		<hr/>
		<div id="searchFormWrap">
			<form id="searchForm" class="searchForm">
				<label class="f_lable">프로젝트 명</label><input type="text" name="p_name" value="${param.p_name }" class="inputText" placeholder="프로젝트 명 입력"/> &nbsp;&nbsp;&nbsp;
				<label class="f_lable">발주처</label><input type="text" name="p_order" class="inputText" value="${param.p_order }" placeholder="발주처 입력"/> &nbsp;&nbsp;&nbsp; <br/>
				<label class="f_lable">기간</label><input type="text" name="p_start" id="f_start" class="p_date" readonly placeholder="기간 입력"/>&nbsp;~&nbsp;<input type="text" name="p_end" id="f_end" class="p_date" readonly placeholder="기간 입력"/>
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
									<!-- <input type="button" value="선택" id="proSel"/> -->
									<button class="profindSel">선택</button>
								</td>
							</tr>
						</c:forEach>
					</c:when>
					<c:when test="${!empty proSearchList }">
						<c:forEach items="${proSearchList}" var="k">
							<tr onmouseover="this.style.background='#E1E1E1'" onmouseout="this.style.background='white'">
								<td>${k.p_name}</td>
								<td>${k.p_content}</td>
								<td>${k.p_start.substring(0,10)}~${k.p_end.substring(0,10)}</td>
								<td>${k.p_order}</td>
								<td class="p_num">${k.p_num}</td>
								<td style="display: none;">${k.p_code}</td>
								<td>
									<!-- <input type="button" value="선택" id="proSel"/> -->
									<button class="profindSel">선택</button>
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
									<li><a href="/Employees/ProController?type=proFind&cPage=${pvo.beginPage-pvo.pagePerBlock}&prow=${requestScope.prow}">이전으로</a></li>
								</c:otherwise>
							</c:choose>
							
							<c:forEach var="k" begin="${pvo.beginPage}" end="${pvo.endPage}" step="1">
								<c:choose>
									<c:when test="${k==pvo.nowPage}">
										<li class="now">${k}</li>
									</c:when>
									<c:otherwise>
										<li><a href="/Employees/ProController?type=proFind&cPage=${k}&prow=${requestScope.prow}">${k}</a></li>
									</c:otherwise>
								</c:choose>
							</c:forEach>
							
							<c:choose>
								<c:when test="${pvo.endPage>=pvo.totalPage}">
									<li class="disable">다음으로</li>
								</c:when>
								<c:otherwise>
									<li><a href="/Employees/ProController?type=proFind&cPage=${pvo.beginPage+pvo.pagePerBlock}&prow=${requestScope.prow}">다음으로</a></li>
								</c:otherwise>
							</c:choose>
						</ul>
					</td>
				</tr>
			</tfoot>
			</c:when>
			</c:choose>
			<c:choose>
			<c:when test="${!empty proSearchList }">
			<tfoot class="t_foot">
				<tr style="text-align: center; font-size: 14px;">
					<td colspan="6">
						<ul class="paging" style="margin-left: 420px;">
							<c:choose>
								<c:when test="${pvo.beginPage<pvo.pagePerBlock}">
									<li class="disable">이전으로</li>
								</c:when>
								<c:otherwise>
									<li><a href="/Employees/ProController?type=proFindSearch&cPage=${pvo.beginPage-pvo.pagePerBlock}&p_name=${param.p_name}&p_order=${param.p_order}&p_start=${param.p_start}&p_end=${param.p_end}&prow=${requestScope.prow}">이전으로</a></li>
								</c:otherwise>
							</c:choose>
							
							<c:forEach var="k" begin="${pvo.beginPage}" end="${pvo.endPage}" step="1">
								<c:choose>
									<c:when test="${k==pvo.nowPage}">
										<li class="now">${k}</li>
									</c:when>
									<c:otherwise>
										<li><a href="/Employees/ProController?type=proFindSearch&cPage=${k}&p_name=${param.p_name}&p_order=${param.p_order}&p_start=${param.p_start}&p_end=${param.p_end}&prow=${requestScope.prow}">${k}</a></li>
									</c:otherwise>
								</c:choose>
							</c:forEach>
							
							<c:choose>
								<c:when test="${pvo.endPage>=pvo.totalPage}">
									<li class="disable">다음으로</li>
								</c:when>
								<c:otherwise>
									<li><a href="/Employees/ProController?type=proFindSearch&cPage=${pvo.beginPage+pvo.pagePerBlock}&p_name=${param.p_name}&p_order=${param.p_order}&p_start=${param.p_start}&p_end=${param.p_end}&prow=${requestScope.prow}">다음으로</a></li>
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