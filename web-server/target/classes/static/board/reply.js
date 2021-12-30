

var replyService = (function() {

    //게시글 댓글 등록
    function insertReply(reply, callback, error){
        console.log("댓글 등록~!");

        $.ajax({
            type : 'post',
            url : '/reply/new', //Restcontroller 호출
            data : JSON.stringify(reply),
            contentType : "application/json; charset=utf-8",
            success : function(result, status, xhr){
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


    //특정 게시글 댓글 리스트 불러오기
    function getListReply(param, callback, error) {

        $.getJSON("/reply/post/" + param.pno +".json", //Restcontroller 호출
            function(data) {

                if (callback) {
                    callback(data); // 댓글 목록만 가져오는 경우
                }
            }).fail(function(xhr, status, err) {
            if (error) {
                error();
            }
        });
    }

    //특정 댓글 조회
    function getReply(reply_num, callback, error) {

        $.get("/reply/" + reply_num + ".json", function(result) {

            if (callback) {
                callback(result);
            }
        }).fail(function(xhr, status, err) {
            if (error) {
                error();
            }
        });
    }



    //댓글 수정
    function updateReply(reply, callback, error){

        console.log("댓글 수정 번호: " + reply.reply_num);

        $.ajax({
            type : 'put',
            url : '/reply/' + reply.reply_num, //controller 호출
            data : JSON.stringify(reply),
            contentType : "application/json; charset=utf-8",
            success : function(result, status, xhr){
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


    //댓글 삭제
    function deleteReply(reply_num, callback, error) {

        $.ajax({
            type : 'delete',
            url : '/reply/' + reply_num,
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



    //댓글 등록 시 등록한 시간 표시
    function replyTime(timeValue){

        var today = new Date(); //Date 객체생성
        var gap = today.getTime() - timeValue;
        var dateObj = new Date(timeValue);
        var str = "";

        if(gap < (1000 * 60 * 60 * 24)){

            var hh = dateObj.getHours();
            var mi = dateObj.getMinutes();
            var ss = dateObj.getSeconds();

            return [ (hh > 9 ? '' : '0') + hh, ':', (mi > 9 ? '' : '0') + mi, ':', (ss > 9 ? '' : '0') + ss ].join('');
        }
        else {
            var yy = dateObj.getFullYear();
            var mm = dateObj.getMonth() + 1;
            var dd = dateObj.getDate();

            return [ yy, '/', (mm > 9 ? '' : '0') + mm, '/', (dd > 9 ? '' : '0') + dd ].join('');
        }
    };







    return {  //객체로 리턴 (변수에 함수를 넣음)
        insertReply : insertReply,
        getListReply : getListReply,
        replyTime : replyTime,
        getReply : getReply,
        updateReply : updateReply,
        deleteReply : deleteReply
    };

})(); //end ReplyService
