$(document).ready(function(){

    $("#loginaction").on("click",function(){
        console.log("ttltl")
        let email = $("#inputId").val()
        let password = $("#password").val()

        $.ajax({
            type : 'post',
            url : "/user-service/login", //Controller 호출
            contentType : "application/json; charset=utf-8",
            data:JSON.stringify({"email": email,
                "password":password
                }),
            success : function(result, status, xhr) { // Ajax 실행결과에 따라 Callback 함수 실행
                console.log(result)
                // if (status == 200) {
                location.href="/jobcho/team/"
                // }
            },
            error : function(xhr, status, er) {
                console.log(status)
                // location.href="/jobcho/users/login"
            }
        });
    })




});//끝