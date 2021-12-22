$(document).ready(function(){
        	function showMemberList(result){
        		str=""
                result.forEach(function(item){
                	console.log(item)
                    console.log(item.user)
                    console.log(item.user.user_name)
                    
                    str +=`<div class="job-container">
                                    <div class="team-profile-image" style="background-image: url('http://localhost:8081/jobcho/resources/css/members/99D279435B3D788602.jfif');"></div>
                                    
                                    <div>
                                        <p class="team-profile-name">`+item.user.user_name+`</p>
                                        <p class="team-profile-email">`+item.user.user_email+`</p>
                                        <p class="team-profile-email">`+item.user.user_phoneNum+`</p>
                                        <p class="team-profile-email">`+item.member_position+`</p>
                                    </div>
                                    <div class="team-btn">
                                        <button class="updataModal" value="`+item.member_num+`">수정</button>
                                    </div>
                                </div>`

                    
                })
                $(".job-team-body").html(str);
            };
            
            function reloadMemberList(){
            	$.ajax({
                    url:'/jobcho/team/17/members',
                    type:'Get',
                    processData:false,
                    contentType:'application/json',
                    dataType:'json',
                    success:function(result){
                        
                        console.log(result);
                        
                        showMemberList(result);

                    }
                });
            }

            $.ajax({
                url:'/jobcho/team/17/members',
                type:'Get',
                processData:false,
                contentType:'application/json',
                
                dataType:'json',
                success:function(result){
                    
                    console.log(result);
                    
                    showMemberList(result);

                }
            });//$.ajax
            
            var updataMemberNum=0;
            
            $(document).on("click",".updataModal" ,function(e){
            	$("#updataModal").modal("show");
            	updataMemberNum = this.value
            });
            
            $("#updateMemberAction").on("click", function(e){
            	console.log({"member_position":$("#updataPosition").value})
            	$.ajax({
                    url:'/jobcho/team/17/members/'+updataMemberNum,
                    type:'put',
                    
                    contentType:'application/json',
                    data: JSON.stringify({"member_position":$("#updataPosition").val()}),
                    dataType: 'json',
                    success:function(result){
                        
                        console.log(result);
                        alert("수정완료")
                        reloadMemberList();
                    }
                });
            	
            	$("#updataModal").modal("hide");     	
            });
            
            
            
        })//끝