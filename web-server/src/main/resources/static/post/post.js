console.log("post.js 호출");

const PostUrl = "http://127.0.0.1:8000/post-service";

var listPost = (function() {


    //게시글 전체 목록 가져오기
    function getListPost(cri,param,callback, error) {
        console.log("게시글 목록 가져오기: "+ param.boardNum);

        $.ajax({
            type : 'post',
            url : PostUrl+"/team/" +param.teamNum+ "/board/" +param.boardNum+ "/post", //Controller 호출
            data : JSON.stringify(cri), //post객체 전달
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

    //게시글 상세조회
    function getPost(param,callback, error) {
        console.log("게시글 상세조회: "+ param.postNum);

        $.ajax({
            type : 'get',
            url : PostUrl+"/team/" +param.teamNum+ "/board/" +param.boardNum+ "/post/" +param.postNum, //Controller 호출
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


    //게시글 생성
    function insertPost(post, param, callback, error) {

        //게시글 이름 작성 안했을 시
        if(!post.postTitle){
            alert("게시글 제목을 작성해주세요.");
            return false;
        }

        //게시글 정보 작성 안했을 시
        if(!post.postContents){
            alert("게시글 내용을 작성해주세요.");
            return false;
        }

        $.ajax({
            type : 'post',
            url : PostUrl+"/team/"+ param.teamNum +"/board/"+param.boardNum+"/post/new", //team,board번호 전달
            data : JSON.stringify(post), //post객체 전달
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


    //게시글 수정
    function updatePost(post, param, callback, error) {

        $.ajax({
            type : 'put',
            url : PostUrl+'/team/' + param.teamNum+'/board/' +param.boardNum+ '/post/' +param.postNum,
            data : JSON.stringify(post), //post객체 전달
            contentType : "application/json; charset=utf-8",
            success : function(updateResult, status, xhr) {
                if (callback) {
                    callback(updateResult);
                }
            },
            error : function(xhr, status, er) {
                if (error) {
                    error(er);
                }
            }
        });
    }



    //게시글 삭제
    function deletePost(param, callback, error) {

        $.ajax({
            type : 'delete',
            url : PostUrl+'/team/' + param.teamNum+ '/board/' +param.boardNum+ '/post/' +param.postNum,
            contentType : "application/json; charset=utf-8",
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


    //특정 게시글 댓글 수 증가
    function updateReplyCnt(param,callback, error) {
        console.log("댓글 수 증가 요청: "+ param.postNum);

        $.ajax({
            type : 'post',
            url : PostUrl+"/team/" +param.teamNum+ "/board/" +param.boardNum+ "/post/" +param.postNum, //Controller 호출
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

    //특정 게시글 댓글 수 증가
    function deleteReplyCnt(param,callback, error) {
        console.log("댓글 수 -1 요청: "+ param.postNum);

        $.ajax({
            type : 'post',
            url : PostUrl+"/team/" +param.teamNum+ "/board/" +param.boardNum+ "/post/reply/" +param.postNum, //Controller 호출
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





    return {  //객체로 리턴 (변수에 함수를 넣음)

        getListPost : getListPost,
        getPost : getPost,
        insertPost : insertPost,
        updatePost : updatePost,
        deletePost : deletePost,
        updateReplyCnt : updateReplyCnt,
        deleteReplyCnt : deleteReplyCnt
    };

})(); //end listPost



