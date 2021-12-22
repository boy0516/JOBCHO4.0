console.log("board.js 호출");


var listBoard = (function() {
	

	//게시판 전체 목록 가져오기
	function getListBoard(param,callback, error) {
		console.log("게시판 목록 가져오기: "+ param.team_num); //21
	
		$.ajax({
			type : 'get', 
			url : "/team/" +param.team_num+ "/board/", //Controller 호출
			contentType : "application/json; charset=utf-8",
			success : function(result, status, xhr) { // Ajax 실행결과에 따라 Callback 함수 실행
				if (callback) {
					callback(result);
				}
			},
			error : function(xhr, status, er) {
				if (error) {
					error(er);
				}
			}
		});
	}
	
	//게시판 생성
	function insertBoard(board, param, callback, error) {
		var team_num = param.team_num;
		console.log("게시판 생성zzzzz: "+team_num );
		
		if(!board.board_name){
			alert("게시판 이름을 작성해주세요.");
			return false;
		}
		if(!board.board_info){
			alert("게시판 정보를 작성해주세요.");
			return false;
		}
		
		
		$.ajax({
			type : 'post',
			url : "/team/"+ team_num +"/board/new",
			data : JSON.stringify(board),
			contentType : "application/json; charset=utf-8",
			success : function(result, status, xhr) {
				if(callback){
					callback(result);
				}
			},
			error : function(xhr, status, er){
				if(error){
					error(er);
				}
			}
		});
	}
	
	
	
	
	//게시판 삭제
	function deleteBoard(param, callback, error) {
		
		$.ajax({
			type : 'delete',
			url : '/team/' + param.team_num+'/board/' +param.board_num,
			success : function(deleteResult, status, xhr) {
				if (callback) {
					callback(deleteResult);
				}
			},
			error : function(xhr, status, er) {
				if (error) {
					error(er);
				}
			}
		});
	}
	
	
	
	
	
	

	return {  //객체로 리턴 (변수에 함수를 넣음)
		
		getListBoard : getListBoard,
		insertBoard : insertBoard,
		deleteBoard : deleteBoard
	};

})(); //end listBoard



