/**
 * 
 */
$(document).ready(function(){
	const apiurl="http://127.0.0.1:8000/"
	var user_num=1;
	
	function showTeamList(result){
		str="";
		result.forEach(function(item){
			console.log(item)
			str+=`<div class="job-container">
            <!--프로필-->
            
            <div class="team-profile-image" style="background-image: url('/jobcho/team/default.png');"></div>
            
            <div class="team-profile-info">
                <p class="team-profile-name">`+item.teamName+`</p>
                <p class="team-profile-email">`+item.teamInfo+`</p>
            </div>
            <!--프로필 끝-->
            <div class="team-btn">
                <button class="teamAdminModal" value="`+item.teamNum+`">팀관리</button>
                <button class="enterTeamMain"  value='`+item.teamNum+`'>팀으로 가기</button>
            </div>
        </div>`
		})
		$(".job-teamlist-wrap").html(str);
	}
	
	$(document).on("click",".enterTeamMain",function(){
		team_num = $(this).val();
		getMemberNum(team_num);
	})
	
	//사용자 이미지 관련
	// $.ajax({
	// 		url:apiurl+"/team/1/member/"+user_num,
	//         type:'Get',
	//         dataType:'json',
	//         success:function(result){
	//         	console.log(result);
	//         	showProfile(result);
	//         }
	// 	})
	
		// //사용자 이미지 관련
		// function showProfile(result){
		// 	console.log("실행");
		// 		if(result.profile_name!=null){
		// 			console.log("실행2");
		// 			$('.nav-profile-image-left').css('background-image', "url('/display?filename="+result.profile_name+"')");
		//
		// 		}
		// 	console.log("실행3");
		// }//end showProfile
	
	function getMemberNum(team_num){
		console.log(team_num);
		console.log("aaaa");
	
		$.ajax({
			url:"/member-service/team/"+team_num+"/member/"+user_num,
	        type:'Get',
	        dataType:'json',
	        success:function(result){
	        	console.log(result);
	        	location.href="/jobcho/main?team_num="+team_num+"&member_num="+result.memberNum;
	        }
		})
	}
	
	function showUpdateTeamInfo(team_num){
		$.ajax({
	        url:'/team-service/team/'+user_num,
	        type:'Get',
	        dataType:'json',
	        success:function(result){
	        	console.log(result)
	        	result.forEach(function(item){
	        		if(item.teamNum==team_num){
	        			$("#updateTeamNum").val(item.teamNum)
	        			$("#updateTeamName").val(item.teamName)
	        			$("#updateTeamInfo").val(item.teamInfo)
	        		}
	        	})
	        }
	    });//$.ajax
	}
	
	function updateTeamAction(){
		$.ajax({
            url:'/team-service/team/'+$("#updateTeamNum").val(),
            type:'put',
            contentType:'application/json',
            data: JSON.stringify(
            		{
            			"teamNum":$("#updateTeamNum").val(),
            			"teamName":$("#updateTeamName").val(),
            			"teamInfo":$("#updateTeamInfo").val()
            		}),
            dataType: 'json',
            success:function(result){
            	console.log(result);
            }
        });
		$("#updataTeamInfoModal").modal("hide");
		alert("수정완료");
		getTeamList();
	}
	
	function getTeamList(){
		$.ajax({
	        url:'/team-service/team/'+user_num,
	        type:'Get',
	        dataType:'json',
	        success:function(result){
	        	console.log(result);
	        	showTeamList(result);
	        }
	    });//$.ajax
	}
	
	//초기화면 출력
	getTeamList();
	
	//팀 삭제하기 
	function deleteTeamAction(){
		console.log("deleteTeamAction 버튼 눌림");
		 
		if(!confirm("정말로 삭제하시겠습니까?")){
			alert("취소되었습니다.")
			$('#updataTeamInfoModal').modal("hide");
		}else{
			$.ajax({
				url : '/team-service/team/'+$('#updateTeamNum').val(),
				type : "delete",
				contentType : "application/json",
				success : function(data){
						console.log(data);
						$('#updataTeamInfoModal').modal("hide");
						alert("팀 삭제가 완료되었습니다.");
						getTeamList();
				},
				error : function(error){
					alert("실패");
					return false;
				}
			});
			
		}
		
	}//end deleteTeamAction
	
	 //팀생성
	function insertTeamAction(){
		console.log("insertTeamAction 버튼 눌림");
		 var insertTeamName = document.getElementById('insertTeamName').value;
		 var insertTeamInfo = document.getElementById('insertTeamName').value;
		 var insertUser_num = document.getElementById('insertTeamName').value;
		
		 if(!insertTeamName){
				alert('팀명을 입력해주세요');
				return false;
			}
		 
		 if(!insertTeamInfo){
				alert('팀 정보를 입력해주세요');
				return false;
			}
		 
		 $.ajax({
				url : '/team-service/team/'+user_num,
				type : "post",
				contentType : "application/json",
				data : JSON.stringify({
							"teamName" : $("#insertTeamName").val(),
							"teamInfo" : $("#insertTeamInfo").val(),
							"userNum" : $("#insertUser_num").val()
				}),
				success : function(data){
						console.log(data);
						alert("팀생성이 완료되었습니다");
						$('#insertTeamInfoModal').modal("hide");
						//window.location.href = "/team/choose";
						getTeamList();

				},
				error : function(error){
					alert("실패");
					return false;
				}
			})
		
		
	}//end insertTeamAction 
	
	
	
	
	//팀수정 모달창 띠우기
	$(document).on("click",".teamAdminModal", function(){
		$("#updataTeamInfoModal").modal("show");
		console.log("this : " + $(this).val());
		showUpdateTeamInfo($(this).val());
	})
	//팀수정 실행
	$("#updateTeamAction").on("click", function(){
		updateTeamAction();
	})
	//팀삭제 실행
	$("#deleteTeamAction").on("click", function(){
		deleteTeamAction();
		getTeamList();
	})
	//팀추가 실행 
	$("#insertTeamAction").on("click", function(){
		insertTeamAction();
		getTeamList();
	})
	
});



