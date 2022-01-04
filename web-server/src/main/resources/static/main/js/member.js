//멤버리스트와 수정모달
$(document).ready(function(){
    function getParameter(name) {
        name = name.replace(/[\[]/, "\\[").replace(/[\]]/, "\\]");
        var regex = new RegExp("[\\?&]" + name + "=([^&#]*)"),
            results = regex.exec(location.search);
        return results === null ? "" : decodeURIComponent(results[1].replace(/\+/g, " "));
    }
    //초대할 유저 검색 리스트
    var searchUserList = null;

    //현재 팀 넘버
    var team_num= getParameter("team_num");

    var member_num = getParameter("member_num")
    //로그인한 유저 넘버
    var user_num=1;



    //컨텐츠바디에 현재팀의 멤버리스트 출력
    function showMemberList(result){
        str=""
        console.log("멤버리스트 출력")
        var teamMaster = result[0].userNum;
        console.log(user_num+"and"+teamMaster)
        //팀장일때
        if(user_num==teamMaster){
            result.forEach(function(item){
                if(item.userNum==user_num){
                    str =`<div class="job-container">
                                <div class="team-profile-image" style="background-image: url('/jobcho/display?filename=`+item.profileName+`');"></div>
                                <div class="team-profile-info">
                                    <p class="team-profile-name">`+item.memberName+`</p>
                                    
                                    <p class="team-profile-email">`+item.memberPosition+`</p>
                                    
                                </div>
                                <div class="team-btn">
                                    <button class="updataAdminModal" value="`+item.memberNum+`">수정</button>
                                    <button class="updataTeamModal" value="`+item.memberNum+`">팀설정</button>
                                </div>
                            </div>`+str;
                }else{
                    str +=`<div class="job-container">
                                <div class="team-profile-image" style="background-image: url('/jobcho/display?filename=`+item.profileName+`');"></div>
                                <div class="team-profile-info">
                                    <p class="team-profile-name">`+item.memberName+`</p>
                                    
                                    <p class="team-profile-email">`+item.memberPosition+`</p>
                                </div>
                                <div class="team-btn">
                                    <button class="updataAdminModal" value="`+item.memberNum+`">수정</button>
                                </div>
                            </div>`;
                }


            });
            $(".job-team-body3").html("");
            $(".job-team-body").html(str);
            //팀원일때
        }else{
            result.forEach(function(item){
                if(item.userNum==user_num){
                    str =`<div class="job-container">
                                <div class="team-profile-image" style="background-image: url('/jobcho/display?filename=`+item.profileName+`');"></div>
                                <div class="team-profile-info">
                                    <p class="team-profile-name">`+item.memberName+`</p>
                                    
                                    <p class="team-profile-email">`+item.memberPosition+`</p>
                                </div>
                                <div class="team-btn">
                                    <button class="updataModal" value="`+item.memberNum+`">수정</button>
                                </div>
                            </div>`+str;
                }else{
                    str +=`<div class="job-container">
                                <div class="team-profile-image" style="background-image: url('/jobcho/display?filename=`+item.profileName+`');"></div>
                                <div class="team-profile-info">
                                    <p class="team-profile-name">`+item.memberName+`</p>
                                    
                                    <p class="team-profile-email">`+item.memberPosition+`</p>
                                </div>
                            </div>`;
                }

            })
            $(".job-team-body3").html("");
            $(".job-team-body").html(str);
        };
    };
    //현재 팀의 멤버 리스트 재출력
    function reloadMemberList(){
        console.log(team_num)
        $.ajax({
            url:'/member-service/team/'+team_num+'/member',
            type:'Get',
            contentType:'application/json',
            dataType:'json',
            success:function(result){

                console.log(result);

                showMemberList(result);

            }
        });
    };

    //초대할 수 있는 유저 리스트 보여줌
    function showUserSearchList(result){
        str=""
        result.forEach(function(item){
            console.log(item)
            str +=`<li class="list-group-item">
                                        <div class="thumnail-profile"></div>
                                        `+item.userName+"__"+item.userEmail+
                `<button id="inviteMember" class="badge "
                                        data-value="`+item.userName+`"
                                        value="`+item.userNum+`">초대</button>
                                    </li>`

        })
        $("#invite-list-group").html(str);
    }

    //초대할 수 있는 유저 리스트를 불러옴
    function showUserSearchListAction(){
        $.ajax({
            url:'/member-service/team/'+team_num+'/member/withoutmember',
            type:'Get',
            processData:false,
            contentType:'application/json',

            dataType:'json',
            success:function(result){
                searchUserList= result
                console.log(result);
                showUserSearchList(result);
            }
        });//$.ajax
    }

    $("#toggle-key-member").on("click",function(){
        //처음 페이지 로드 시 멤버 리스트 호출
        reloadMemberList();
    })


    //수정하고싶은 멤버의 번호 초기화
    var updataMemberNum=0;

    //팀장의 멤버 정보 수정 모달 호출
    $(document).on("click",".updataAdminModal" ,function(e){
        $("#updataAdminModal").modal("show");
        updataMemberNum = this.value
    });

    //팀원의 멤버 정보 수정 모달 호출
    $(document).on("click",".updataModal" ,function(e){
        $("#updataModal").modal("show");
        updataMemberNum = this.value
    });

    //팀장의 팀 정보 수정 모달 호출
    $(document).on("click",".updataTeamModal" ,function(e){
        $("#updataTeamModal").modal("show");
        updataMemberNum = this.value
    });

    //멤버 정보 수정 모달에서 수정엑션
    $("#updateMemberAction").on("click", function(e){
        console.log({"member_position":$(".updatePosition").val()})
        var position = $(".updatePosition").val()

        $.ajax({
            url:'/member-service/team/'+team_num+'/member/'+updataMemberNum,
            type:'put',

            contentType:'application/json',
            data: JSON.stringify({"memberPosition":position}),
            dataType: 'json',
            success:function(result){
                alert("수정완료")
                $("#updataAdminModal").modal("hide");
                console.log(result);
                reloadMemberList();
            }
        });

    });

    $("#updateMemberAction").on("click", function(e){
        var file = $(this).parent().parent().find("input[name='uploadFile']")[0].files;
        console.log($(this).parent().parent().find("input[name='uploadFile']")[0].files)
        $(this).parent().find("input[name='uploadFile']").clone();

        console.log( file)
        var formData = new FormData();
        for(var i = 0; i<file.length; i++){
            formData.append("uploadFile", file[i])
        }
        $.ajax({
            url:'/member-service/team/'+team_num+'/member/'+updataMemberNum+'/uploadprofile',
            type:'Post',
            processData:false,
            contentType:false,
            data:formData,
            success:function(result){
                console.log(result)
                console.log("upload succss");

            }
        });// $.ajax
    });

    //초대할 유저검색 모달 호출
    $("#inviteSearch").on("click", function(){
        showUserSearchListAction();
    });

    //멤버 초대 모달에서 유저를 멤버로 초대
    $(document).on("click","#inviteMember",function(){
        console.log("초대 실행")
        $.ajax({
            url:'/member-service/team/'+team_num+'/member/invite',
            type:'Post',
            processData:false,
            contentType:'application/json',
            data:JSON.stringify({"memberPosition": "팀원",
                "memberName":this.dataset.value,
                "userNum": this.value}),
            dataType:'json',
            success:function(result){

                console.log(result);
                alert("초대완료")
                searchUserList = result;
                showUserSearchListAction();

            }
        });//$.ajax

    });

    //멤버초대 모달 닫기 + 현재 팀의 멤버 리스트 재출력
    $("#inviteModalClose").on("click",function(){
        console.log("모달 닫음")
        reloadMemberList();
    });

    //멤버 초대 모달의 검색바에서 검색어와 일치하는 유저 검색
    $('#inviteUserSearchbar').keyup(function(){
        var keyword = $(this).val()
        var str=""
        searchUserList.forEach(function(user,index){
            console.log(index);
            console.log(user);
            console.log();
            if(((keyword==user.userEmail.substr(0,keyword.length))||(keyword==user.userName.substr(0,keyword.length)))&& keyword.length!=0){
                str +=`<li class="list-group-item">
                            <div class="thumnail-profile"></div>
                            `+user.userName+"__"+user.userEmail+`<button id="inviteMember" class="badge " 
                            data-value="`+item.userName+`"
                            value="`+user.userNum+`">초대</button>
                        </li>`

            };
        });

        $("#invite-list-group").html(str);
    });

    //멤버탈퇴
    $('#deleteMemberAction').on('click', function(){

        var msg = "팀을 탈퇴하시겠습니까?"

        var flag = confirm(msg);

        if(flag){
            $.ajax({
                url:'/member-service/team/'+team_num+'/member/'+updataMemberNum,
                type:'delete',
                dataType:'json',
                success:function(result){
                    console.log(result);
                    alert("탈퇴되었습니다")
                }
            });//$.ajax
        }
    });
});//끝
