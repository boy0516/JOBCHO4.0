/**
 *
 */
$(document).ready(function(){
	var team_num =$("#teamNum").val();
	var userName = $("#userName").val();
	console.log("사용자이름  test : " + userName);
	var user_num= $("#userNum").val();
	var member_num = $("#memberNum").val();
	console.log("사용자 멤버 번호 : " + member_num);
	
	//초기 투표 리스트 불러오기
	function getVoteList(){
		console.log("getVoteList 실행");
		$.ajax({
			url:'/team/'+team_num+'/vote/list',
			type:'Get',
			dataType:'json',
			success:function(result){
				showVoteList(result);
			}
		});//end ajax
	}//end getVoteList
	
	//완료된 투표 ajax 호출
	function getEndVoteList(){
		$.ajax({
			url:'/team/'+team_num+'/vote/endlist',
			type:'Get',
			dataType:'json',
			success:function(result){
				showEndVoteList(result);
			}
		});//end ajax
	}//end getEndVoteList
	
	
	//초기화면 출력
	getVoteList();
	
	
	//완료된 투표 목록 출력
	getEndVoteList();
	
	
	//ajax 호출한 진행중 투표 리스트 화면 출력
	function showVoteList(result){
		str = "";
		result.forEach(function(item){
			str += `<div class="nav-search-result active-right voteModalShow" data-air="`+item.vote_num+`">
                    <div class="result-container">
                        <div class="result-image" style="background-image: url('99D279435B3D788602.jfif');"></div>
                        <div>
                             <p  class="team-profile-email">투표번호 : `+item.vote_num+`</p>
                             <p class="team-profile-email">투표제목 : `+item.vote_name+`</p>
                        </div>
                    </div>
                </div>
                <hr>`
		})
		$(".job-vote-wrap").html(str);
	}//end showVoteList
	
	//ajax 호출한 완료된 투표 리스트 화면 출력
	function showEndVoteList(result){
		str = "";
		result.forEach(function(item){
			str += `<div class="nav-search-result active-right endVoteModalShow" data-air="`+item.vote_num+`">
            <div class="result-container">
                <div class="result-image" style="background-image: url('99D279435B3D788602.jfif');"></div>
                <div>
                     <p  class="team-profile-email">투표번호 : `+item.vote_num+`</p>
                     <p class="team-profile-email">투표제목 : `+item.vote_name+`</p>
                </div>
            </div>
        </div>
        <hr>`
		})//end result.forEach
		$(".job-vote-end-view-wrap").html(str);
	}//end showEndVoteList
	
	//투표 내용 보여주기
	function voting(result){
		console.log(result);
		str = "";
			str+= '<div class ="vote-result-container">'
			str+= '<input id="insertVoteResult" type="hidden">'
			str+= '<input type ="hidden" id = "vote_member_num" value="'+result.member_num+'">'	
			str+= '<input type ="hidden" id = "vote_vote_num" value="'+result.vote_num+'">'	
			str+= '<input type="radio" name="vote_result" value="1">'+result.vote_content1+'<br>'
			str+= '<input type="radio" name="vote_result" value="2">'+result.vote_content2+'<br>'
		
			if(result.vote_content3 != null){
				str+= '<input type="radio" name="vote_result" value="3">'+result.vote_content3+'<br>'
				if(result.vote_content4 != null){
					str+= '<input type="radio" name="vote_result" value="4">'+result.vote_content4+'<br>'
					if(result.vote_content5 != null){
						str+= '<input type="radio" name="vote_result" value="5">'+result.vote_content5+'<br>'	
					}
				}
			}
						
			str+= '<input id="submitVoteResult" type="button" class="btn btn-success" value="투표 하기">'
			str+= '<input id="viewVoteResult" type="button" class="btn btn-success" value="결과보기">'
			str+= '<input id="endVote" type="button" class="btn btn-success" value="투표 마감">'
			str+= '</div>'
		
		$(".job-vote-result-wrap").html(str)
	}//end voting
	
	//투표 내용 불러오기
	function showVoting(vote_num){
		console.log(vote_num);
		$.ajax({
			url:'/team/'+team_num+'/vote/'+vote_num,
			type:'Get',
			dataType:'json',
			success:function(result){
				console.log(result);
				voting(result);
			}
		});
	};//end showVoting
	
	function showEndVoteResult(item, vote_num){
		str = "";
		
		str+= '<div class ="end-vote-result-view-container">'
			str+= '<input type ="hidden" id="vote_result_vote_num" value="'+ vote_num+'">'
			str+= '<input type = "button" id="content1_member" value="'+item.vote_content1+'">'+item.vote_result1+'<br>'
			str+= '<input type = "button" id="content2_member" value="'+item.vote_content2+'">'+item.vote_result2+'<br>'
			if(item.vote_content3 != null){
				str+= '<input type = "button" id="content3_member" value="'+item.vote_content3+'">'+item.vote_result3+'<br>'
				if(item.vote_content4 != null){
					str+= '<input type = "button" id="content4_member" value="'+item.vote_content4+'">'+item.vote_result4+'<br>'
					if(item.vote_content5 != null){
						str+= '<input type = "button" id="content5_member" value="'+item.vote_content5+'">'+item.vote_result5+'<br>'
					}
				}
			}
			str+= '</div>'
				
				$(".job-vote-end-result-view-wrap").html(str)
		
	}
	
	//완료된 투표 내용 불러오기
	function showEndVoting(vote_num){
		$.ajax({
			url:'/team/'+team_num+'/vote/'+vote_num+'/result',
			type:'Get',
			dataType:'json',
			success:function(result){
				showEndVoteResult(result, vote_num);
			}
		})
	}//end showEndVoting(vote_num)
	
	
	
	

		
		
	//투표추가 ajax
	function insertVoteAction(){
		console.log("insertVoteAction() 버튼 눌림")
				
		$.ajax({
			url:'/team/'+team_num+'/vote/new',
			type:"post",
			contentType : "application/json",
			data:JSON.stringify({
				"vote_name":$("#vote_name").val(),
				"vote_content1":$("#vote_content1").val(),
				"vote_content2":$("#vote_content2").val(),
				"vote_content3":$("#vote_content3").val(),
				"vote_content4":$("#vote_content4").val(),
				"vote_content5":$("#vote_content5").val(),
				"member_num":member_num,
				"team_num":$("#team_num").val()
			}),
			success : function(data){
				alert("투표생성 완료");
				$('#insertVoteModal').modal("hide");
				getVoteList();
			}
		})
	}//end insertVoteAction
	
	//투표한 결과 전달
	function insertVoteResultAction(item){
		var vote_num = $("#vote_vote_num").val();
		console.log("insertVoteResultAction : " +vote_num);
		console.log("insertVoteResultAction : " +vote_num);
		
		var vote_result1 = 0;
		var vote_result2 = 0;
		var vote_result3 = 0;
		var vote_result4 = 0;
		var vote_result5 = 0;
		
		var elementExistCheck = true;
				
		var vote_result = $('input[name="vote_result"]:checked').val();
		console.log("insertVoteResultAction : " +vote_result);
		
		
		
		//DB에서 이미 투표한 멤버번호 불러오기
		
		function getVoteResultMemberAjax(){
			$.ajax({
				url:'/team/'+team_num+'/vote/'+vote_num+'/result/member',
				type:'Get',
				dataType:'json',
				success : function(result){
					console.log("result/member : " + result)
					doubleVoteCheck(result);
				}
			})// end ajax
		}//end getVoteResultMemberAajx
		
		getVoteResultMemberAjax();
		
		
		//중복 투표 체크
		function doubleVoteCheck(result){
			console.log("중복투표체크");
			
			console.log("result:"+result);
			console.log("member_num:" +member_num);
			
			forcheck();
			
			function forcheck(){
				for(var i = 0; i<result.length; i++){
					if(result[i] == member_num){
						alert("중복투표는 불가능합니다.");
						elementExistCheck = false;
						console.log("elementExistCheck:"+elementExistCheck)
						break;
						
					}
					
				}
				ifcheck(elementExistCheck);
			}
			
			
			
			
		function ifcheck(item){
			console.log("ifcheck:"+item);
			if(item == true){
				console.log("if else false")
				doubleCheckVote();
				console.log("insertVoteResultAction true: " +elementExistCheck)
			}else if(item == false){
				console.log("insertVoteResultAction false: " +elementExistCheck)
				console.log("if else false")
				//alert("중복투표는 불가능합니다.")
			}
		}	
			
			
			
		}//end 중복 투표 체크
		
		function doubleCheckVote(){
			if(vote_result == 1){
				vote_result1 += 1;
			}else if(vote_result == 2){
				vote_result2 += 2;
			}else if(vote_result == 3){
				vote_result3 += 3;
			}else if(vote_result == 4){
				vote_result4 += 4;
			}else if(vote_result == 5){
				vote_result5 += 5;
			}
			$.ajax({
				url:'/team/'+team_num+'/vote/'+vote_num+'/insert',
				type:'post',
				contentType:'application/json',
				data:JSON.stringify({
					"vote_num":vote_num,
					"member_num":member_num,
					"vote_result1":vote_result1,
					"vote_result2":vote_result2,
					"vote_result3":vote_result3,
					"vote_result4":vote_result4,
					"vote_result5":vote_result5
				}),
				dataType:'json',
				success:function(result){
					
				}
			});
			alert("투표를 완료하셨습니다.");
			$("#voting").modal("hide");
			getVoteList();
			
			
			
		}//end doubleCheckVote
		
		
		
		
	}//투표한 결과 전달 끝
	
	//투표 결과 표시하기
	function showVoteResult(item, vote_num){
		console.log("voteResult:" + item);
		console.log("vote_num:" + vote_num);
		str = ""
			str+= '<div class ="vote-result-view-container">'
			str+= '<input type ="hidden" id="vote_result_vote_num" value="'+ vote_num+'">'
			str+= '<input type = "button" id="content1_member" value="'+item.vote_content1+'">'+item.vote_result1+'<br>'
			str+= '<input type = "button" id="content2_member" value="'+item.vote_content2+'">'+item.vote_result2+'<br>'
			if(item.vote_content3 != null){
				str+= '<input type = "button" id="content3_member" value="'+item.vote_content3+'">'+item.vote_result3+'<br>'
				if(item.vote_content4 != null){
					str+= '<input type = "button" id="content4_member" value="'+item.vote_content4+'">'+item.vote_result4+'<br>'
					if(item.vote_content5 != null){
						str+= '<input type = "button" id="content5_member" value="'+item.vote_content5+'">'+item.vote_result5+'<br>'
					}
				}
			}
			str+= '</div>'
				
				$(".job-vote-result-view-wrap").html(str)
	}
	
	//투표 결과 불러오기
	function getVoteResult(vote_num){
		console.log(vote_num);
		$.ajax({
			url:'/team/'+team_num+'/vote/'+vote_num+'/result',
			type:'Get',
			dataType:'json',
			success:function(result){
				console.log(result);
				showVoteResult(result, vote_num);
			}
		})
	}
	
	//투표 결과 멤버 표시하기
	function showVoteResultMember(result){
		console.log(result);
		str = "";
		result.forEach(function(item){
			str+= '<div class ="vote-result-member-view-container">'
			str+='<div class ="voteContentMember">'+item.user_name+'</div>'
			str+='</div><hr>'
		})
		$(".job-vote-result-member-view-wrap").html(str);
	}
	
	
	
	//투표 멤버 불러오기
	function getVoteResultMember(vote_num, item){
		console.log(vote_num);
		console.log(item);
		$.ajax({
			url:'/team/'+team_num+'/vote/'+vote_num+'/result/'+item,
			type:'Get',
			dataType:'json',
			success:function(result){
				console.log(result);
				showVoteResultMember(result);
			}
			
		})
	}
	
	function endVoteSubmit(vote_num){
		console.log(vote_num)
		$.ajax({
			url:'/team/'+team_num+'/vote/'+vote_num+'/end',
			type:'delete',
			dataType:'json',
			success:function(result){
				console.log("endVoteSubmit:"+result);
				
				
			}
		});//end ajax
		alert("투표가 마감되었습니다.")
		$("#voting").modal("hide");
		getVoteList();
	}
	
	//투표 추가 모달 이벤트
	$(document).on("click","#createVote", function(e){
		e.preventDefault();
		console.log("투표 클릭");
		$("#insertVoteModal").modal("show");
		updataMemberNum = this.value
	});
	
	//투표완료리스트 출력 모달 이벤트
	$(document).on("click", "#end-vote-list", function(){
		getEndVoteList();
		$("#end-list-vote-modal").modal("show");
	});
	
	//투표생성 실행시키는 함수 
	$("#insertVoteAction").on("click", function(){
		console.log("투표생성 버튼눌리는중");
		insertVoteAction();
	});
	
	//투표 모달 띄우기
	$(document).on("click", ".voteModalShow", function(){
		$("#voting").modal("show");
		showVoting($(this).data('air'));
	})
	
	//완료된 투표 모달 띄우기
	$(document).on("click", ".endVoteModalShow", function(){
		$("#end-list-vote-result-modal").modal("show");
		showEndVoting($(this).data('air'));
	})
	
	//투표 결과 제출
	$(document).on("click", "#submitVoteResult", function(){
		console.log("투표 결과 제출");
		insertVoteResultAction();		
	});
	
	//투표 결과 모달 띄우기
	$(document).on("click", "#viewVoteResult", function(e){
		//$("#voting").modal("hide");
		e.preventDefault();
		console.log("투표 결과 보기");
		$("#voteResult").modal("show");
		getVoteResult($("#vote_vote_num").val());
	});
	
	//투표 마감
	$(document).on("click", "#endVote", function(){
		console.log("투표 마감 출력")
		endVoteSubmit($("#vote_vote_num").val());
	});
	
	//1번 투표 멤버 보기
	$(document).on("click", "#content1_member", function(){
		$("#voteResultMember").modal("show");
		console.log("vote_num: " + $("#vote_result_vote_num").val());
		console.log("content1 value :" + $("#content1_member").val());
		getVoteResultMember($("#vote_result_vote_num").val(), '1')
	});

	//2번 투표 멤버 보기
	$(document).on("click", "#content2_member", function(){
		$("#voteResultMember").modal("show");
		console.log("vote_num: " + $("#vote_result_vote_num").val());
		console.log("content2 value :" + $("#content2_member").val());
		getVoteResultMember($("#vote_result_vote_num").val(), '2')
	})
	
	//3번 투표 멤버 보기
	$(document).on("click", "#content3_member", function(){
		$("#voteResultMember").modal("show");
		console.log("vote_num: " + $("#vote_result_vote_num").val());
		console.log("content3 value :" + $("#content3_member").val());
		getVoteResultMember($("#vote_result_vote_num").val(), '3')
	})
	
	//4번 투표 멤버 보기
	$(document).on("click", "#content4_member", function(){
		$("#voteResultMember").modal("show");
		console.log("vote_num: " + $("#vote_result_vote_num").val());
		console.log("content4 value :" + $("#content4_member").val());
		getVoteResultMember($("#vote_result_vote_num").val(), '4')
	})
	
	//5번 투표 멤버 보기
	$(document).on("click", "#content5_member", function(){
		$("#voteResultMember").modal("show");
		console.log("vote_num: " + $("#vote_result_vote_num").val());
		console.log("content5 value :" + $("#content5_member").val());
		getVoteResultMember($("#vote_result_vote_num").val(), '5')
	})
	
})//end document.ready




	/*function getVoteLIst(){
		$.ajax({
			url:'/team/'+team_num+'/vote/list',
			type:'Get',
			dataType:'json',
			success:function(result){
				console.log(result);
				showVoteList(result);
			}
		});//end ajax
	}//end getVoteList
*/	
	/*function showVoteList(result){
		str = "";
		result.forEach(function(item){
			console.log(item)
			str+=`<div class="job-container">
            <!--프로필-->
            
            <div class="team-profile-image" style="background-image: url('/resources/team/99D279435B3D788602.jfif');"></div>
            
            <div>
                <p class="vote-num">`+item.vote_num+`</p>
                <p class="vote-name">`+item.vote_name+`</p>
            </div>
            <!--프로필 끝-->
            <div class="vote-btn">
                <button id="voteModal" value="`+item.vote_num+`">투표확인</button>
            </div>
        </div>`
                
            vote_num += item.vote_num;
		})
		$(".job-votelist-wrap").html(str);
	}*/
	
	//getVoteList();
	
	/*$(document).on("click", "#voteModal", function(){
		$("#voteResultModal").modal("show");
		
		getVoteResult();
		
		function getVoteResult(){
			$.ajax({
				url:'/team/'+team_num+'/vote/'+vote_num,
				type:'Get',
				dataType:'json',
				success:function(result){
					console.log(result);
					showVoteResult(result);
				}
			})
		}//end function
*/		
		/*function showVoteResult(result){
			str = "";
			result.forEach(function(item){
				str += '<div class = "modal" id = "voteResultModal" tabindex = "-1">'
				'<div class ="modal-dialog">'
				'<div class = "modal-header">투표 내용<button class="close" data-dismiss="modal">닫기</button>'
				'</div>'
				'<div class="modal-body">'
					'<div id ="voteRadio">'	
						'<input id = "vote_result1" type = "radio" class="form-control" value="1">'+item.vote_result1+'</input>'
						'<input id = "vote_result2" type = "radio" class="form-control" value="2">'+item.vote_result2+'</input>'
						'<input id = "vote_result3" type = "radio" class="form-control" value="3">'+item.vote_result3+'</input>'
						'<input id = "vote_result4" type = "radio" class="form-control" value="4">'+item.vote_result4+'</input>'
						'<input id = "vote_result5" type = "radio" class="form-control" value="5">'+item.vote_result5+'</input>'
						'<button type ="submit" id = "vote-complete-button" onclick="postVote()">완료</button>'
					'</div>'
				'</div>'
			'</div>'
		'</div>'
			})
		};//end showVoteResult
*/		
		/*function postVote(){
			$.ajax({
				url:'/team/'+team_num+'/vote/'+vote_num+'/insert',
				type:'Post',
				contentType:'application/json',
				data:JSON.stringify({
					"vote_num":vote_num,
					"vote_result1":$("#vote_result1").val(),
					"vote_result2":$("#vote_result2").val(),
					"vote_result3":$("#vote_result3").val(),
					"vote_result4":$("#vote_result4").val(),
					"vote_result5":$("#vote_result5").val(),
					"member_num":param.member_num
				}),
				dataType:'json',
				success:function(result){
					console.log(result);
				}
			})
		}
		*/












