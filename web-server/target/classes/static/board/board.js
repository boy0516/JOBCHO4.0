console.log("board.js 호출");

//const boardUrl = "http://127.0.0.1:8000/board-service";

var listBoard = (function() {


    //게시판 전체 목록 가져오기
    function getListBoard(param,callback, error) {
        console.log("게시판 목록 가져오기: "+ param.teamNum); //21

        $.ajax({
            type : 'get',
            url : "/team/" +param.teamNum+ "/board/", //Controller 호출
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
        var teamNum = param.teamNum;
        console.log("게시판 생성: " + board.boardName );

        if(!board.boardName){ //게시판 이름 작성 안했을 시
            alert("게시판 이름을 작성해주세요.");
            return false;
        }
        if(!board.boardInfo){//게시판 정보 작성 안했을 시
            alert("게시판 정보를 작성해주세요.");
            return false;
        }

        $.ajax({
            type : 'post',
            url : "/team/"+ teamNum +"/board/new", //team번호 전달
            data : JSON.stringify(board), //board객체 전달
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



