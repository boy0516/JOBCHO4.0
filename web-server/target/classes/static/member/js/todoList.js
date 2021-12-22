/**
 * 
 */
$(document).ready(function(){
	var team_num =$("#teamNum").val();
	var userName = $("#userName").val();
	console.log("사용자이름  test : " + userName);
	var user_num= $("#userNum").val();
	var member_num = $("#memberNum").val();
	
	//오늘의 할일 리스트 뽑아오는 ajax
	function getTodoList(){
		console.log("getTodoList 함수 실행");
		$.ajax({
	        url:'/team/'+team_num+'/todo/list/'+member_num,
	        type:'Get',
	        dataType:'json',
	        success:function(result){
	        	console.log(result);
	        	showTodoList(result);
	        }
	    });//$.ajax
	}//end getTodoList
	
	//초기화면 출력
	getTodoList();
	
	function showTodoList(result){
		console.log("showTodoList 함수실행");
		str="";
		result.forEach(function(item){
			console.log(item)
			str+=`<div class="nav-search-result active-right todoUpdateModalShow" data-air="`+item.todo_num+`">
                    <div class="result-container">
                        <div class="result-image" style="background-image: url('99D279435B3D788602.jfif');"></div>
                        <div>
                             <p  class="team-profile-email">시작날짜 : `+item.todo_startDate+`</p>
                             <p class="team-profile-email">마감날짜 : `+item.todo_endDate+`</p>
                            <p class="team-profile-email">제목 : `+item.todo_title+`</p>
                            <p class="team-profile-email">설명 : `+item.todo_description+`</p>
                            
                        </div>
                    </div>
                </div>
                <hr>`
		})
		$(".job-todolist-wrap").html(str);
	}//showTodoList
	
	//삭제된 오늘의 할일 리스트 뽑아오는 ajax
	function getDeleteTodoList(){
		console.log("getDeleteTodoList 함수 실행");
		$.ajax({
	        url:'/team/'+team_num+'/todo/deletelist/'+member_num,
	        type:'Get',
	        dataType:'json',
	        success:function(result){
	        	console.log(result);
	        	showDeleteTodoList(result);
	        }
	    });//$.ajax
	}//end getTodoList
	
	getDeleteTodoList();
	
	function showDeleteTodoList(result){
		console.log("showDeleteTodoList 함수실행");
		str="";
		result.forEach(function(item){
			console.log(item)
			str+=`<div class="nav-search-result active-right" data-air="`+item.todo_num+`">
                    <div class="result-container">
                        <div class="result-image" style="background-image: url('99D279435B3D788602.jfif');"></div>
                        <div>
                             <p  class="team-profile-email">시작날짜 : `+item.todo_startDate+`</p>
                             <p class="team-profile-email">마감날짜 : `+item.todo_endDate+`</p>
                            <p class="team-profile-email">제목 : `+item.todo_title+`</p>
                            <p class="team-profile-email">설명 : `+item.todo_description+`</p>
                        </div>
                    </div>
                </div>
                <hr>`
		})
		$(".job-todoDeletelist-wrap").html(str);
	}//showTodoList
	
	
	
	
	//여러개 불러오는 ajax
	function showUpdateTodoInfo(todo_num){
		console.log()
		
		$.ajax({
	        url:'/team/' +team_num+'/todo',
	        type:'Get',
	        dataType:'json',
	        success:function(result){
	        	console.log(result);
	        
	        	result.forEach(function(item){
	        		if(item.todo_num== todo_num){
	        			$("#updateTodoNum").val(item.todo_num)
	        			$("#updateTodoTitle").val(item.todo_title)
	        			$("#updateTodoDescription").val(item.todo_description)
	        			$("#updateTodoEnd").val(item.todo_endDate)
	        		}
	        	})
	        }
	    });//$.ajax
	}
	
	//수정
	function updateTodoAction(){
		$.ajax({
            url:'/team/'+team_num+'/todo/'+$("#updateTodoNum").val(),
            type:'put',
            contentType:'application/json',
            data: JSON.stringify(
            		{
            			"todo_num":$("#updateTodoNum").val(),
            			"todo_title":$("#updateTodoTitle").val(),
            			"todo_description":$("#updateTodoDescription").val(),
            			"todo_endDate":$("#updateTodoEnd").val()
            		}),
            dataType: 'json',
            success:function(result){
            	console.log(result);
            }
        });
		$("#updateTodoListInfoModal").modal("hide");
		alert("수정이 완료되었습니다.");
		getTodoList();
	}
	
	
	
	
	//오늘의 할일 삭제하기 
	function deleteTodoAction(){
		console.log("오늘의 할일 삭제버튼 눌림");
		 
		if(!confirm("정말로 삭제하시겠습니까?")){
			alert("취소되었습니다.")
			$('#updateTodoListInfoModal').modal("hide");
		}else{
			$.ajax({
				url : '/team/'+team_num+'/todo/'+$("#updateTodoNum").val(),
				type : "delete",
				contentType : "application/json",
				success : function(data){
						console.log(data);
						alert("삭제가 완료되었습니다.");
						$('#updateTodoListInfoModal').modal("hide");
						getTodoList();
				},
				error : function(error){
					alert("실패");
					return false;
				}
			});
			
			
		}
		
	}//end deleteTeamAction
	
	//할일 추가 ajax
	function insertTodoListAction(){
		console.log("insertTodoListAction 버튼 눌림");
		 	
		var todo_title = document.getElementById('todo_title').value;
		var todo_description = document.getElementById('todo_description').value;
		var todo_endDate = document.getElementById('todo_endDate').value;
		var member_num = document.getElementById('member_num').value;
		var team_num = document.getElementById('team_num').value;
			
		if(!todo_title){
			alert('오늘의 할일을 입력해주세요');
			return false;
		}
		if(!todo_description){
			alert('내용을 입력해주세요');
			return false;
		}
		if(!todo_endDate){
			alert('마감날짜를 선택해주세요');
			return false;
		}
			
		$.ajax({
			url : '/team/'+team_num+'/todo/new',
			type : "post",
			contentType : "application/json",
			data : JSON.stringify({"todo_title" : $("#todo_title").val(),
						"todo_description" : $("#todo_description").val(),			
						"todo_endDate" : $("#todo_endDate").val(),
						"member_num" : $("#member_num").val(),
						"team_num" : $("#team_num").val(),
			}),
			success : function(data){
					console.log(data);
					alert("오늘의 할일이 추가되었습니다.");
					$('#insertTodoListInfoModal').modal("hide");
					getTodoList();
					//window.location.href = "/team/main?team_num="${param.team_num}";
					//window.location.replace("/users/main");
			},
			error : function(error){
				alert("실패");
				return false;
			}
		});
		
	}//end insertTodoListAction
	
	//삭제된 오늘의 할일 모달 띄우기 
	$(document).on("click","#selectTodoDeleteList", function(){
		$("#selectTodoDeleteListModal").modal("show");
		getDeleteTodoList();
	})
	
	
	//수정모달 띄우기 
	$(document).on("click",".todoUpdateModalShow", function(){
		$("#updateTodoListInfoModal").modal("show");
		console.log("this : " + $(this).data('air'));
		showUpdateTodoInfo($(this).data('air'));
	})
	
	
	
	//오늘의 할일 수정버튼 눌렀을 때 수정시키는 함수 실행
	$("#updateTodoAction").on("click", function(){
		updateTodoAction();
	})
	
	//오늘의 할일 삭제 버튼을 눌렀을 때 삭제시키는 함수 실행 
	$("#deleteTodoAction").on("click", function(){
		deleteTodoAction();
		getTodoList();
	})
	
	//오늘의 할일 생성버튼 눌렀을 때 수정시키는 함수 실행
	$("#insertTeamAction").on("click", function(){
		insertTodoListAction();
		getTodoList();
	})
	
	
	
});//end document.ready



