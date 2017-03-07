<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script src="http://dmaps.daum.net/map_js_init/postcode.v2.js"></script>
<script type="text/javascript" src="/Employees/js/jquery-3.1.1.min.js"></script>
<link rel="stylesheet" type="text/css" href="/Employees/css/add.css"/>
<script type="text/javascript">
	var e_idx = "${ requestScope.e_idx }";
	$(function() {
		$(".date").datepicker({
	         dateFormat : 'yy-mm-dd',
	         monthNamesShort: [ "1월", "2월", "3월", "4월", "5월", "6월", "7월", "8월", "9월", "10월", "11월", "12월" ],
	         changeMonth : true,
	         changeYear : true,
	         yearRange : 'c-100:c+2',
	         onSelect: function (dateText, inst) {
			    	//학력
	        	 	if($("#s_start1").val()!="" && $("#s_end1").val() != "" && $("#s_start1").val()>$("#s_end1").val()){
						alert("학력사항 시작날짜가 더 큽니다.");
						$(this).parent().find('input:text').val("");
					}else if($("#s_start2").val()!="" && $("#s_end2").val() != "" && $("#s_start2").val()>$("#s_end2").val()){
						alert("학력사항 시작날짜가 더 큽니다.");
						$(this).parent().find('input:text').val("");
					}
					if($("#s_start1").val()!="" && $("#s_end1").val()!=""&&$("#s_start2").val()!="" && $("#s_end2").val()!="" &&(
							 ($("#s_start1").val()<=$("#s_start2").val() && $("#s_end1").val()>$("#s_start2").val()) ||
							 ($("#s_start1").val()<$("#s_end2").val() && $("#s_end1").val()>=$("#s_end2").val()) ||
							 ($("#s_start2").val()<=$("#s_start1").val() && $("#s_end2").val()>$("#s_start1").val())||
							 ($("#s_start2").val()<$("#s_end1").val() && $("#s_end2").val()>=$("#s_end1").val()))){
						 alert("기간이 올바르지 않습니다.");
						 $(this).parent().find('input:text').val("");
					 }
			   }
		});
		$(".hire_date").datepicker({
	         dateFormat : 'yymmdd',
	         monthNamesShort: [ "1월", "2월", "3월", "4월", "5월", "6월", "7월", "8월", "9월", "10월", "11월", "12월" ],
	         changeMonth : true,
	         changeYear : true,
	         yearRange : 'c-100:c+2'
		});
		$("input[name=e_hire_date]").change(function() {
			if($("input[name=e_hire_date]").val()!=""){
				$("#e_id").html($("input[name=e_hire_date]").val().substring(2)+e_idx);
				$("input[name=e_id]").val($("input[name=e_hire_date]").val().substring(2)+e_idx);
				
				var hire_date = $("input[name=e_hire_date]").val().substring(0,4)+"-"+$("input[name=e_hire_date]").val().substring(4,6)+"-"+$("input[name=e_hire_date]").val().substring(6,8);
				
				var c_length = $("#c_table tbody tr").length;
				for (var i = 2; i <= c_length; i++) {
					var c_start = $('#c_table tr:eq('+i+') td:eq(0)').find(".cdate").eq(0).val();
					var c_end = $('#c_table tr:eq('+i+') td:eq(0)').find(".cdate").eq(1).val();
					
					if((c_start!="")&&(c_end!="")&&(((hire_date<c_start)||(hire_date<c_end))||((hire_date<c_start)&&(hire_date<c_end)))){
						alert((i-2)+"번째 경력사항 근무기간을 수정해주세요.");
						$('#c_table tr:eq('+i+') td:eq(0)').find(".cdate").val("");
					}
				}
				
				var p_length = $("#p_table tbody tr").length;
				for (var i = 3; i <= p_length; i++) {
					var p_chk = $('#p_table tr:eq('+i+') td:eq(8)').find("input:hidden").val();
					if(p_chk!=""){
						// 찾기
						var p_start = $('#p_table tr:eq('+i+') td:eq(4)').find(".pdate").eq(0).val();
						var p_end = $('#p_table tr:eq('+i+') td:eq(4)').find(".pdate").eq(1).val();
						
						if(( p_start!="" )&&( p_end!="" )&& ( ( (hire_date>p_start) || (hire_date>p_end) )||( (hire_date>p_start) && (hire_date>p_end) ) ) ){
							alert((i-2)+"번째 프로젝트 수행 내역을 수정해주세요.");
							$('#p_table tr:eq('+i+') td:eq(4)').find(".pdate").val("");
						}
					}
				}
			}

		});
		
		
		// 유효성 검사
		// 1. 이름 한글 검사
		$("input[name=e_name]").keyup(function() {
			var str =  $(this).val();
			var check = /([^가-힣ㄱ-ㅎㅏ-ㅣ\x20])/i;
			
			if(check.test(str)){
				alert("한글만 입력할수있습니다.");
				$(this).val("");
				return;
			}
		});
		// 주민번호
		$("input[name=e_jumin]").keyup(function() {
			var chk;
			var id_length = $(this).val().length;
			
			// 아스키 코드값으로 숫자 확인
			for (var i = 0; i < id_length; i++) {
				chk = $(this).val().charCodeAt(i);
				if(chk>=48 && chk<=57){
					
				}else{
					alert("주민번호는 숫자만 입력해주세요.");
					$(this).val("");
					return;
				}
			}
			
			$.ajax({
				type : "post",
				url : "/Employees/EmpController?type=jumin_chk",
				dataType : "text",
				data : "jumin="+$("input[name=e_jumin]").val(),
				success : function(data) {
					if(data.substring(0,4)=="등록가능"){
						// 등록가능
						$("#juminOverlap").html(data.substring(0,4)).css("color","blue").css("font-size","13px");
					}else{
						// 중복된 사원
						$("#juminOverlap").html(data).css("color","red").css("font-size","13px");
					}
				},
				error : function() {
					alert("오류발생");
				}
			});
		});
		// 전화번호
		$("input[name=e_tel]").keyup(function() {
			var chk;
			var id_length = $(this).val().length;
			
			// 아스키 코드값으로 숫자 확인
			for (var i = 0; i < id_length; i++) {
				chk = $(this).val().charCodeAt(i);
				if(chk>=48 && chk<=57){
					
				}else{
					alert("전화번호는 숫자만 입력해주세요.");
					$(this).val("");
					return;
				}
				if(id_length>11){
					alert("11자리이상 입력할수 없습니다.");
					$(this).val("");
					return;
				}
			}
		});
		// 이메일 골뱅이
		$("input[name=e_email]").keyup(function() {
			var chk;
			var id_length = $(this).val().length;
			
			// 아스키 코드값으로 숫자 확인
			for (var i = 0; i < id_length; i++) {
				chk = $(this).val().charCodeAt(i);
				
				if((chk>=65 && chk<=90) || (chk>=97 && chk<=122) || (chk>=48 && chk<=57) || (chk==95)){
					
				}else{
					alert("특수문자나 한글은 입력할 수 없습니다.");
					$(this).val("");
					return;
				}
			}
		});

		// 이메일 콤보박스 제어
		$("#email_select").change(function() {
			if($("#email_select").val()=="direct"){
				$("input[name=e_email2]").val(""); // 값 초기화 하기
				$("input[name=e_email2]").prop("readonly",false); // textbox 활성화
			}else if($("#email_select").val()==""){
				$("input[name=e_email2]").val("");
				$("input[name=e_email2]").prop("readonly",true); // textbox 비활성화
			}else{
				$("input[name=e_email2]").val($("#email_select").val()); // 선택한 값 입력
				$("input[name=e_email2]").prop("readonly",true); // textbox 비활성화
			}
		});
		
		// 입사 날짜
		$("input[name=e_hire_date]").keyup(function() {
			var chk;
			var id_length = $(this).val().length;
			
			// 아스키 코드값으로 숫자 확인
			for (var i = 0; i < id_length; i++) {
				chk = $(this).val().charCodeAt(i);
				if(chk>=48 && chk<=57){
					
				}else{
					alert("날짜는 숫자만 입력해주세요.");
					$(this).val("");
					return;
				}
				if(id_length>8){
					alert("더이상 입력할 수 없습니다.");
					$(this).val("");
					return;
				}
			}
		});
		
		// 우편번호 찾기
		$("#addrFind").click(function() {
			new daum.Postcode({
		         oncomplete : function(data) {
		            var fullRoadAddr = data.roadAddress;
		            var extraRoadAddr = '';

		            if (data.bname !== '' && /[동|로|가]$/g.test(data.bname)) {
		               extraRoadAddr += data.bname;
		            }
		            if (data.buildingName !== '' && data.apartment === 'Y') {
		               extraRoadAddr += (extraRoadAddr !== '' ? ', '
		                     + data.buildingName : data.buildingName);
		            }
		            if (extraRoadAddr !== '') {
		               extraRoadAddr = ' (' + extraRoadAddr + ')';
		            }
		            if (fullRoadAddr !== '') {
		               fullRoadAddr += extraRoadAddr;
		            }
		            $("input[name=e_post]").val(data.zonecode);
		            $("input[name=e_addr]").val(fullRoadAddr);
		         }
		      }).open();
		});
		
		// 주민번호 확인
		$("#juminConfirm").click(function() {
			var jumin = $("input[name=e_jumin]").val();
			var key = "234567892345"; // 주민번호 생성 key 값
			var sum = 0; // 곱해서 더한 총합 

			for(i=0;i<12;i++){ //주민등록 생성 알고리즘에 의한 마지막 번호 유효성 검사
			   sum += jumin.charAt(i)*key.charAt(i);
			} 
			sum=11-(sum % 11)
			var endNo=sum%10; 

			if (jumin.charAt(12) != endNo){
				$("#juminchk").html("유효하지 않음").css("color","red").css("font-size","13px");    			$("input[name=e_jumin]").val("");
				$("input[name=e_jumin]").focus(); 
				return;
			}
			   
			$("#juminchk").html("확인").css("color","blue").css("font-size","13px");
		});
		
		// 주민번호 확인
		$("input[name=e_jumin]").keyup(function() {
			$("#juminchk").html("");
		});
		
		// 등록
		$(".empAddBtn").click(function() {
			var chk = true;
			
			if($("input[name=e_name]").val()==""){
				alert("이름을 입력해주세요.");
				chk = false;
			}else if($("#e_rankCategory option:selected").val()==""){
				alert("직급을 선택해주세요.");
				chk = false;
			}else if($("#e_deptCategory option:selected").val()==""){
				alert("부서를 선택해주세요.");
				chk = false;
			}else if($("input[name=e_jumin]").val()==""){
				alert("주민번호를 입력해주세요.");
				chk = false;
			}else if($("#juminchk").html()==""){
				alert("주민번호를 확인 버튼을 클릭해주세요.");
				chk = false;
			}else if($("#juminchk").html().substring(0,2)!="확인"){
				alert("유효하지않은 주민번호입니다.");
				chk = false;
			}else if($("#juminOverlap").html().substring(0,4)!="등록가능"){
				alert("중복된 사원입니다.");
				chk = false;
			}else if($("input[name=e_hire_date]").val()==""){
				alert("입사날짜를 입력해주세요.");
				chk = false;
			}else if($("input[name=e_tel]").val()==""){
				alert("전화번호를 입력해주세요.");
				chk = false;
			}else if($("input[name=e_email]").val()==""){
				alert("이메일을 입력해주세요.");
				chk = false;
			}else if($("input[name=e_email2]").val()==""){
				alert("이메일을 입력해주세요.");
				chk = false;
			}else if($("input[name=e_post]").val()==""){
				alert("우편번호 찾기를 통해 주소를 입력해주세요.");
				chk = false;
			}else if($("input[name=e_addr2]").val()==""){
				alert("상세주소를 입력해주세요.");
				chk = false;
			}else if( ($("#s_start1").val()!="" ) || ($("#s_end1").val()!="") || ($("#s_name1").val()!="") || ($("#s_major1").val()!="")){
				if($("#s_start1").val()==""){
					alert("학력사항 기간을 입력해주세요."); 
					chk = false;
				}else if($("#s_end1").val()==""){
					alert("학력사항 기간을 입력해주세요."); 
					chk = false;
				}else if($("#s_name1").val()==""){
					alert("학교명을 입력해주세요."); 
					chk = false;
				}else if($("#s_major1").val()==""){
					alert("전공을 입력해주세요.");
					chk = false;
				}
			}else if( ($("#s_start2").val()!="" ) || ($("#s_end2").val()!="") || ($("#s_name2").val()!="") || ($("#s_major2").val()!="")){
				if($("#s_start2").val()==""){
					alert("학력사항 기간을 입력해주세요.");
					chk = false;
				}else if($("#s_end2").val()==""){
					alert("학력사항 기간을 입력해주세요.");
					chk = false;
				}else if($("#s_name2").val()==""){
					alert("학교명을 입력해주세요.");
					chk = false;
				}else if($("#s_major2").val()==""){
					alert("전공을 입력해주세요.");
					chk = false;
				}
			}
			
			// 경력사항 유효성 검사
			var c_length = $("#c_table tr").length;
			for (var i = 3; i < c_length; i++) {
				var c_start = $('#c_table tr:eq('+i+') td:eq(0)').find(".cdate").eq(0).val();
				var c_end = $('#c_table tr:eq('+i+') td:eq(0)').find(".cdate").eq(1).val();
				var c_name = $('#c_table tr:eq('+i+') td:eq(1)').find("input:text").val();
				var c_rank = $('#c_table tr:eq('+i+') td:eq(2)').find("#c_rankCategory").val();
				var c_order = $('#c_table tr:eq('+i+') td:eq(3)').find("input:text").val();
				if((c_start!="")||(c_end!="")||(c_name!="")||(c_rank!="")||(c_order!="")){
					if(c_start==""){
						alert("경력사항 시작날짜를 입력해주세요.");
						chk = false;
						return;
					}else if(c_end==""){
						alert("경력사항 종료날짜를 입력해주세요.");
						chk = false;
						return;
					}else if(c_name==""){
						alert("회사명을 입력해주세요.");
						chk = false;
						return;
					}else if(c_rank==""){
						alert("직급을 선택해주세요.");
						chk = false;
						return;
					}else if(c_order==""){
						alert("담당업무를 입력해주세요.");
						chk = false;
						return;
					}
				}
			}
			
			// 자격증 유효성 검사
			var l_length = $("#l_table tr").length;
			for (var i = 3; i < l_length; i++) {
				var l_name = $('#l_table tr:eq('+i+') td:eq(0)').find("input:text").val();
				var l_date = $('#l_table tr:eq('+i+') td:eq(1)').find("input:text").val();
				
				if((l_name!="")||(l_date!="")){
					if(l_name==""){
						alert("자격증명을 입력해주세요.");
						chk = false;
						return;
					}else if(l_date==""){
						alert("자격증 날짜를 입력해주세요.");
						chk = false;
						return;
					}
				}
			}
			
			// 프로젝트 수행 내역 유효성 검사
			var p_length = $("#p_table tr").length;
			for (var i = 3; i < p_length; i++) {
				var p_chk = $('#p_table tr:eq('+i+') td:eq(8)').text();
				
				if(p_chk!=""){
					// 찾기
					var p_start = $('#p_table tr:eq('+i+') td:eq(3)').find(".pdate").eq(0).val();
					var p_end = $('#p_table tr:eq('+i+') td:eq(3)').find(".pdate").eq(1).val();
					var p_task = $('#p_table tr:eq('+i+') td:eq(4)').find("input:text").val();
					
					if((p_start!="")||(p_end!="")||(p_task!="")){
						if(p_start==""){
							alert("프로젝트 시작 날짜를 입력해주세요.");
							chk = false;
							return;
						}else if(p_end==""){
							alert("프로젝트 종료 날짜를 입력해주세요.");
							chk = false;
							return;
						}else if(p_task==""){
							alert("프로젝트 담당업무를 입력해주세요.");
							chk = false;
							return;
						}
					}
				}else{
					// 직접입력
					var dp_name = $('#p_table tr:eq('+i+') td:eq(1)').find("input:text").val();
					var dp_content = $('#p_table tr:eq('+i+') td:eq(2)').find("input:text").val();
					var dp_start = $('#p_table tr:eq('+i+') td:eq(3)').find(".pdate").eq(0).val();
					var dp_end = $('#p_table tr:eq('+i+') td:eq(3)').find(".pdate").eq(1).val();
					var dm_start = $('#p_table tr:eq('+i+') td:eq(4)').find(".pdate").eq(0).val();
					var dm_end = $('#p_table tr:eq('+i+') td:eq(4)').find(".pdate").eq(1).val();
					var dp_task = $('#p_table tr:eq('+i+') td:eq(5)').find("input:text").val();
					var dp_order = $('#p_table tr:eq('+i+') td:eq(6)').find("input:text").val();
					
					if((dp_name!="")||(dp_content!="")||(dp_start!="")||(dp_end!="")||(dp_task!="")||(dp_order!="")||(dm_start!="")||(dm_end!="")){
						if(dp_name==""){
							alert("프로젝트명을 입력해주세요.");
							chk = false;
							return;
						}else if(dp_content==""){
							alert("프로젝트 내용을 입력해주세요.");
							chk = false;
							return;
						}else if(dp_start==""){
							alert("프로젝트 시작일을 입력해주세요.");
							chk = false;
							return;
						}else if(dp_end==""){
							alert("프로젝트 종료일을 입력해주세요.");
							chk = false;
							return;
						}else if(dp_task==""){
							alert("프로젝트 담당업무를 입력해주세요.");
							chk = false;
							return;
						}else if(dp_order==""){
							alert("프로젝트 발주처를 입력해주세요.");
							chk = false;
							return;
						}else if(dm_start==""){
							alert("프로젝트 참여기간을 입력해주세요.");
							chk = false;
							return;
						}else if(dm_end==""){
							alert("프로젝트 참여기간을 입력해주세요.");
							chk = false;
							return;
						}
					}
				}

			}
		
			if(chk){
				if(confirm("등록하시겠습니까?")==true){
					$("#frm").attr("enctype","multipart/form-data").attr("action","/Employees/EmpController?type=empAdd&cidx="+cidx+"&lidx="+lidx+"&pidx="+pidx).submit();
				}
			}
			
		});
	});
	var count=0;
	
	// 2. 경력 테이블 추가
	var cidx = 0;
	function c_addItem() {
		var newDate = $("input[name=e_hire_date]").val();
		if(newDate==""){
			alert("입사일자를 먼저 선택해주세요.");
		}else{
			$('#c_table tr:eq(1)').hide();
			
			cidx++;
			$.tRow = $('#c_table tr:last').clone();
			$.tRow.find(".cdate").eq(0).attr("id","c_start"+(count));
			$.tRow.find(".cdate").eq(1).attr("id","c_end"+(count));
			
	        var str= $.tRow.html();
	        $.newTr = $("<tr>"+str+"</tr>");
			
			$('#c_table').append($.newTr);
			
			cdateSetting("c_start"+count,"c_end"+count);
			count++;
			
			 // delete Button 추가
	        $.btnDelete = $(document.createElement("input"));
	        $.btnDelete.attr({
	            name : "cbtnRemove",
	            type : "button" ,
	            value : "삭제"
	        });
	        $("#c_table tr:last td:last").html("");
	        $("#c_table tr:last td:last").append($.btnDelete);
	        
	    	 // 버튼에 클릭 이벤트 추가
	    	 
	        $("#c_table tr>td:last>input[type='button']").on('click', function(){
	            $(this).parent().parent().remove();
	            cidx--;
	            if($('#c_table tr').length == 3){
	    			$('#c_table tr:eq(1)').show();
	    		}
	        });
		}
		
		
	}
	
	// 3. 자격증 테이블 추가
	var lidx = 0;
	function l_addItem() {
		$('#l_table tr:eq(1)').hide();
		lidx++;
		$.tRow = $('#l_table tr:last').clone();
		$.tRow.find(".date").eq(0).attr("id","l_start"+(count));
		
        var str= $.tRow.html();
        $.newTr = $("<tr>"+str+"</tr>");
		
		$('#l_table').append($.newTr);
		
		l_dateSetting();
		count++;
		
		 // delete Button 추가
        $.btnDelete = $(document.createElement("input"));
        $.btnDelete.attr({
            name : "lbtnRemove",
            type : "button" ,
            value : "삭제"
        });
        $("#l_table tr:last td:last").html("");
        $("#l_table tr:last td:last").append($.btnDelete);
        
    	 // 버튼에 클릭 이벤트 추가
    	 
        $("#l_table tr>td:last>input[type='button']").on('click', function(){
            $(this).parent().parent().remove();
            lidx--;
            if($('#l_table tr').length == 3){
    			$('#l_table tr:eq(1)').show();
    		}
        });
	}
	
	// 4. 프로젝트 테이블 추가
	var pidx = 0;
	function p_addItem() {
		$('#p_table tr:eq(1)').hide();
		pidx++;
		$.tRow = $('#p_table tr:last').clone();
		$.tRow.children('td').eq(1).html("");
		$.tRow.children('td').eq(2).html("");
		$.tRow.children('td').eq(3).html("");
		$.tRow.children('td').eq(4).html("");
		$.tRow.children('td').eq(5).html("");
		$.tRow.children('td').eq(6).html("");
		
        var str= $.tRow.html();
        $.newTr = $("<tr>"+str+"</tr>");
		
		$('#p_table').append($.newTr);
		
		// delete Button 추가
        $.btnDelete = $(document.createElement("input"));
        $.btnDelete.attr({
            name : "pbtnRemove",
            type : "button" ,
            value : "삭제"
        });
        $("#p_table tr:last td:last").html("");
        $("#p_table tr:last td:last").append($.btnDelete);
        
    	// 버튼에 클릭 이벤트 추가
        $("#p_table tr>td:last>input[type='button']").on('click', function(){
        	
            $(this).parent().parent().remove();
            pidx--;
            if($('#p_table tr').length == 3){
    			$('#p_table tr:eq(1)').show();
    		}
        });
        
    	// 찾기 버튼
       $("input[name=pro_find]").click(function() {
            var p_table = document.getElementById('p_table');
		    var prow = $(this).parents("tr").index();
		    newWindow = window.open("/Employees/ProController?type=proFind&prow="+prow, "newWindow", "height=600, width=1140, resizable=yes");
		});
    	
    	// 직접입력
       $("input[name=pro_direct]").click(function() {
    	    
    	    var p_table = document.getElementById('p_table');
  			var prow = $(this).parents("tr").index();
  			prow *= 1;
  			var row = prow+1;
  			
  			p_table.rows[row].cells[1].innerHTML="<input type='text' name='dp_name' />";
  			p_table.rows[row].cells[2].innerHTML="<input type='text' name='dp_content' />";
  			p_table.rows[row].cells[3].innerHTML="<input type='text' name='dp_start' class='pdate ddate' readonly id='dp_start'/>~<input type='text' readonly name='dp_end' class='pdate ddate' id='dp_end'/>";
  			p_table.rows[row].cells[4].innerHTML="<input type='text' name='dm_start' class='pdate ddate' readonly id='dm_start'/>~<input type='text' readonly name='dm_end' class='pdate ddate' id='dm_end'/>";
  			p_table.rows[row].cells[5].innerHTML="<input type='text' name='dm_task' />";
  			p_table.rows[row].cells[6].innerHTML="<input type='text' name='dp_order' />";
  			p_table.rows[row].cells[7].innerHTML="<input type='text' name='dm_etc' />";
  			
  			$(this).parents("tr").find(".pdate").eq(0).attr("id","dp_start"+(row));
  			$(this).parents("tr").find(".pdate").eq(1).attr("id","dp_end"+(row));
  			$(this).parents("tr").find(".pdate").eq(2).attr("id","dm_start"+(row));
  			$(this).parents("tr").find(".pdate").eq(3).attr("id","dm_end"+(row));
  			
			pdateSetting("dm_start"+row,"dm_end"+row);
			count++;
		});
	}
	function sbtnRemove(btn) {
		$(btn).parent().parent().find("input:text").val("");
	}
        
	
	// 파일 이미지 띄우기
	function imageCheck(input) {
      	if(input.files&&input.files[0]){
        	var reader = new FileReader();
         	reader.onload = function(e){
            $('#preview').attr('src',e.target.result);
        }
        reader.readAsDataURL(input.files[0]);
        }   
	}
	
	// 찾기버튼을 통해 팝업창에서 받아온 값 뿌리기
	function list_go(p_name,p_content,p_order,p_code,prow,p_start,p_end) {
		var p_table = document.getElementById('p_table');
		prow *= 1;
		var row = prow+1;
		
		var p_length = $("#p_table tr").length;
		for (var i = 3; i < p_length; i++) {
			var str = $('#p_table tr:eq('+(i-1)+') td:eq(8)').find("input:hidden").val();
			if(str!=null && str==p_code){
				alert("동일한 프로젝트가 존재합니다.");
				return;
			}
		}
		p_table.rows[row].cells[1].innerHTML=p_name;
		p_table.rows[row].cells[2].innerHTML =p_content;
		
		p_table.rows[row].cells[3].innerHTML="<input type='hidden' class='fdate pdate' value='"+p_start+"'/>"+"<input type='hidden' class='fdate pdate' value='"+p_end+"'/>"+p_start+"~"+p_end;
		p_table.rows[row].cells[4].innerHTML="<input type='text' name='m_start' class='fdate pdate' readonly id='m_start"+row+"'/>~<input type='text' name='m_end' readonly class='fdate pdate' id='m_end"+row+"'/>";
		p_table.rows[row].cells[5].innerHTML="<input type='text' name='m_task' />";
		p_table.rows[row].cells[6].innerHTML=p_order;
		p_table.rows[row].cells[7].innerHTML="<input type='text' name='m_etc' />";
		p_table.rows[row].cells[8].innerHTML="<input type='hidden' name='p_code' value='"+p_code+"' />";
		
		fdateSetting("m_start"+row,"m_end"+row);
	}
	
	// 프로젝트 직접입력 
	function pdateSetting(start,end) {
		$(".ddate").removeClass('hasDatepicker').datepicker({
	         dateFormat : 'yy-mm-dd',
	         monthNamesShort: [ "1월", "2월", "3월", "4월", "5월", "6월", "7월", "8월", "9월", "10월", "11월", "12월" ],
	         changeMonth : true,
	         changeYear : true,
	         yearRange : 'c-100:c+2',
	         onSelect: function (dateText, inst) {
	        	 common(this);
			 },
			 beforeShowDay : noCareerBefore
		});
	}
	function noCareerBefore(date) {
		var c_length = $("#c_table tr").length;
    	for (var i = 2; i < c_length; i++) {
    		var start = $("#c_table tr:eq("+i+")").find(".cdate").eq(0).val();
			var end = $("#c_table tr:eq("+i+")").find(".cdate").eq(1).val();
			if(start!=null&&end!=null){
				var sdate = new Date(start.substring(0,4),start.substring(5,7)-1,start.substring(8,10));
				var edate = new Date(end.substring(0,4),end.substring(5,7)-1,end.substring(8,10));
				
				if(date>=sdate&&date<=edate){
					return[true];
				}
			}
		}
    	return [false];
	}
	
	// 프로젝트 찾기 datepicker
	function fdateSetting(start,end) {
		$(".fdate").removeClass('hasDatepicker').datepicker({
	         dateFormat : 'yy-mm-dd',
	         monthNamesShort: [ "1월", "2월", "3월", "4월", "5월", "6월", "7월", "8월", "9월", "10월", "11월", "12월" ],
	         changeMonth : true,
	         changeYear : true,
	         yearRange : 'c-100:c+2',
	         onSelect: function (dateText, inst) {
	        	 common(this);
			 },
			 beforeShowDay : nohireBefore
		});
	}
	function nohireBefore(date) {
		var newDate = $("input[name=e_hire_date]").val();
		var beforeDate = new Date(newDate.substring(0,4),newDate.substring(4,6)-1,newDate.substring(6,8));
		
		if(newDate!=""&&date>beforeDate){
			return[true];
		}
		return [false];
	}
	
	function common(sel) {
		var p_length = $("#p_table tr").length;
    	for (var i = 3; i < p_length; i++) {
    		// 프로젝트 기간
    		var dstartA = $("#p_table tr:eq("+i+") td:eq(3)").find(".pdate").eq(0).val();
			var dendA = $("#p_table tr:eq("+i+") td:eq(3)").find(".pdate").eq(1).val();
			
			if(dstartA!=""&&dendA!="" && dstartA>dendA){
				alert("직접입력 프로젝트 시작날짜가 더 큽니다.");
	    		$(sel).parent().find('input:text').val("");
	    		return;
			}
    		
    		// 프로젝트 참여기간
    		var startA = $("#p_table tr:eq("+i+") td:eq(4)").find(".pdate").eq(0).val();
			var endA = $("#p_table tr:eq("+i+") td:eq(4)").find(".pdate").eq(1).val();
			
			if(startA!="" && endA != "" && startA>endA){
	    		alert("프로젝트 시작날짜가 더 큽니다.");
	    		$(sel).parent().find('input:text').val("");
	    		return; 
			}
			
			if((startA!="" && endA != "" && dstartA!="" && dendA!=""  ) && ( (dstartA>startA) || (dendA<endA)) ){
				alert("프로젝트 기간안에 참여기간이 포함되어야 합니다.");
				$(sel).parent().find('input:text').val("");
				return;
			}
			
			for (var j = i+1; j < p_length; j++) {
				var startB = $("#p_table tr:eq("+j+") td:eq(4)").find(".pdate").eq(0).val();
				var endB = $("#p_table tr:eq("+j+") td:eq(4)").find(".pdate").eq(1).val();
				
				if(startA!="" && endA!=""&&startB!="" && endB!="" &&(
						 (startA<=startB && endA>startB) ||
						 (startA<endB && endA>=endB) ||
						 (startB<=startA && endB>startA)||
						 (startB<endA && endB>=endA))){
					alert("프로젝트의 참여 기간이 겹칩니다.");
					$(sel).parent().find('input:text').val("");
					return;
				}
			}
		}
	}
	
	
	// 경력사항 datepicker
	function cdateSetting(start,end) {
		$(".cdate").removeClass('hasDatepicker').datepicker({
	         dateFormat : 'yy-mm-dd',
	         monthNamesShort: [ "1월", "2월", "3월", "4월", "5월", "6월", "7월", "8월", "9월", "10월", "11월", "12월" ],
	         changeMonth : true,
	         changeYear : true,
	         yearRange : 'c-100:c+2',
	         onSelect: function (dateText, inst) {
	        	 	var c_length = $("#c_table tr").length;
			    	for (var i = 0; i < c_length; i++) {
			    		var startA = $("#c_table tr:eq("+i+")").find(".cdate").eq(0).val();
						var endA = $("#c_table tr:eq("+i+")").find(".cdate").eq(1).val();
						
						if(startA!="" && endA != "" && startA>endA){
				    		alert("경력사항 시작날짜가 더 큽니다.");
				    		$(this).parent().find('input:text').val("");
						}
						
						for (var j = i+1; j < c_length; j++) {
							var startB = $("#c_table tr:eq("+j+")").find(".cdate").eq(0).val();
							var endB = $("#c_table tr:eq("+j+")").find(".cdate").eq(1).val();
							
							if(startA!="" && endA!=""&&startB!="" && endB!="" &&(
									 (startA<=startB && endA>startB) ||
									 (startA<endB && endA>=endB) ||
									 (startB<=startA && endB>startA)||
									 (startB<endA && endB>=endA))){
								alert("경력사항의 기간이 겹칩니다.");
								$(this).parent().find('input:text').val("");
							}
						}
					}
			 },
			 beforeShowDay : noBefore
		});
	}
	function noBefore(date) {
		var newDate = $("input[name=e_hire_date]").val();
		var beforeDate = new Date(newDate.substring(0,4),newDate.substring(4,6)-1,newDate.substring(6,8));
		if(date<beforeDate){
			return[true];
		}else{
			return [false];
		}
	}
	
	// 자격증 datepicker
	function l_dateSetting() {
		$(".date").removeClass('hasDatepicker').datepicker({
	         dateFormat : 'yy-mm-dd',
	         monthNamesShort: [ "1월", "2월", "3월", "4월", "5월", "6월", "7월", "8월", "9월", "10월", "11월", "12월" ],
	         changeMonth : true,
	         changeYear : true,
	         yearRange : 'c-100:c+2',
		});
	}


</script>
</head>
<body>
<form id="frm" method="post">
	<div>
		<c:choose>
			<c:when test="${empInfo.e_dept eq '관리팀'}">
				<div>	
					<jsp:include page="../[menu]/AdminMenu.jsp" />
				</div>
			</c:when>
			
			<c:otherwise>
				<div>
					<jsp:include page="../[menu]/Menu.jsp" />
				</div>
			</c:otherwise>
		</c:choose>
		
	</div>
	<div class="sectionWrap">
	
		<h1>회원 등록</h1>
		<hr/>
		<div class="f_wrap">
			<h2>1. 기본사항</h2>
			
				<table>
					<tbody>
						<tr><td rowspan="10"><img id="preview" width="248px" height="319px" src="/Employees/img/confirmIcon.PNG"/><br/><input type="file" class="photoSelFile" name="photoSel" onchange="imageCheck(this);"/></td></tr>
						<tr>
							<th>성함</th><td><input type="text" name="e_name" /></td>
							<th>경력기간</th><td id="c_career_date"></td>
						</tr>
						<tr class="tr_id">
							<th>사번</th><td class="e_id"><span id="e_id"></span></td>
							<th>직급</th>
							<td>
								<select id="e_rankCategory" name="e_rankCategory" class="select">
									<option value="" selected >::선택하세요::</option>
									<option value="사장">사장</option>
									<option value="전무">전무</option>
									<option value="상무">상무</option>
									<option value="부장">부장</option>
									<option value="차장">차장</option>
									<option value="과장">과장</option>
									<option value="대리">대리</option>
									<option value="사원">사원</option>
								</select>
							</td>
						</tr>
						<tr>
							<th>소속회사</th><td>아이캔매니지먼트(주)</td>
							<th>부서</th>
							<td>
								<select id="e_deptCategory" name="e_deptCategory" class="select">
									<option value="" selected >::선택하세요::</option>
									<option value="개발 1팀">개발 1팀</option>
									<option value="개발 2팀">개발 2팀</option>
									<option value="빅데이타팀">빅데이타팀</option>
									<option value="관리팀">관리팀</option>
									<option value="해당없음">해당없음</option>
								</select>
							</td>
						</tr>
						<tr>
							<th>주민번호</th><td><input type="text" id="jumin" name="e_jumin" />&nbsp;&nbsp;<input type="button" id="juminConfirm" value="확인" />&nbsp;&nbsp;<span id="juminchk"></span>&nbsp;&nbsp;<span id="juminOverlap"></span></td>
							<th>입사날짜</th><td id="hire_date"><input type="text" name="e_hire_date" class="hire_date" /></td>
						</tr>
						<tr>
							<th>전화번호</th><td><input type="text" name="e_tel" /></td>
							<th>이메일</th>
							<td>
								<input type="text" name="e_email" />@<input type="text" name="e_email2" id="email" readonly />
								<select id="email_select">
									<option value="" selected >::선택하세요::</option>
									<option value="naver.com" >naver.com</option>
									<option value="daum.net" >daum.net</option>
									<option value="nate.com" >nate.com</option>
									<option value="gmail.net" >gmail.net</option>
									<option value="direct" >::직접 입력::</option>
								</select>
							</td>
						</tr>
						<tr><th rowspan="3">학력</th><th>기간</th><th>학교명</th><th>전공</th></tr>
							<tr><td><input type="text" name="s_start" id="s_start1" readonly class="date" />~<input type="text" name="s_end" readonly id="s_end1" class="date" /></td><td><input type="text" id="s_name1" name="s_name" /></td><td><input type="text" id="s_major1" name="s_major" /></td><td><input type="button" value="삭제" onclick="sbtnRemove(this)"/></td></tr>
							<tr><td><input type="text" name="s_start" id="s_start2" readonly class="date" />~<input type="text" name="s_end" readonly id="s_end2" class="date" /></td><td><input type="text" id="s_name1" name="s_name" /></td><td><input type="text" id="s_major1" name="s_major" /></td><td><input type="button" value="삭제" onclick="sbtnRemove(this)"/></td></tr>
						<tr><th>주소</th><td colspan="5"><input type="button" id="addrFind" value="우편번호 찾기" /><input type="text" id="post" placeholder="우편번호" name="e_post" readonly /><input type="text" name="e_addr" id="addr1" placeholder="주소" readonly /><input type="text" id="addr2" placeholder="상세주소 입력" name="e_addr2"/></td></tr>	
					</tbody>
				</table>
			
			<h2>2. 경력사항</h2>
			<table id="c_table">
				<thead>
					<tr>
						<th>근무기간</th>
						<th>회사명</th>
						<th>직위</th>
						<th>담당업무</th>
					</tr>
				</thead>
				<tbody>
					<tr><td colspan="4">추가 버튼을 클릭하세요.</td></tr>
					<tr style="display: none;">
						<td id="c_date"><input type="text" name="c_start" readonly class="cdate"/>~<input type="text" name="c_end" readonly class="cdate"/></td>
						<td><input type="text" name="c_name" /></td>
						<td>
							<select id="c_rankCategory" name="c_rankCategory" class="select">
								<option value="" selected >::선택하세요::</option>
								<option value="사장">사장</option>
								<option value="전무">전무</option>
								<option value="상무">상무</option>
								<option value="부장">부장</option>
								<option value="차장">차장</option>
								<option value="과장">과장</option>
								<option value="대리">대리</option>
								<option value="사원">사원</option>
							</select>
						</td>
						<td><input type="text" name="c_task" /></td>
						<td></td>
					</tr>
							
				</tbody>
			</table>
			<input type="button" value="추가" onclick="c_addItem()"/>
			
			<h2>3. 자격증</h2>
			<table id="l_table">
				<thead>
					<tr>
						<th>자격증</th>
						<th>취득일</th>
					</tr>
				</thead>
				<tbody>
					<tr><td colspan="2">추가 버튼을 클릭하세요.</td></tr>
					<tr style="display: none;">
						<td><input type="text" name="l_name" /></td>
						<td><input type="text" name="l_date" readonly class="date"/></td>
						<td></td>
					</tr>
				</tbody>
			</table>
			<input type="button" value="추가" onclick="l_addItem()">
			
			<h2>4. 프로젝트 수행 내역</h2><span id="chk"></span>
			<table id="p_table">
				<thead>
					<tr>
						<th></th>
						<th>프로젝트명</th>
						<th>프로젝트 내용</th>
						<th>프로젝트 기간</th>
						<th>참여기간</th>
						<th>담당업무/역할</th>
						<th>발주처</th>
						<th>비고</th>	
						<th></th>				
					</tr>
				</thead>
				<tbody>
					<tr><td colspan="8">추가 버튼을 클릭하세요.</td></tr>
					 <tr style="display: none;">
						<td><input type="button" value="직접입력" name="pro_direct" id="pro_direct" /><input type="button" value="찾기" name="pro_find" id="pro_find" /></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td style="display: none;"></td>
						<td></td>
					 </tr>
				</tbody>
			</table>
			<input type="button" value="추가" onclick="p_addItem()"/>
			<br/><br/>
			
			<input type="hidden" name="e_id" value="" />
			<input type="button" class="empAddBtn" value="등록"/>
		
		</div>
	
		
	</div>
	</form>	
</body>
</html>