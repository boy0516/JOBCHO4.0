/**
 * 
 */

function onChatting(e){
		e.preventDefault();
}
$(document).ready(function(){
	var search = location.search 
	var params = new URLSearchParams(search); 
	var member_num= $("#memberNum").val();
	console.log("member_num:"+member_num)
	var team_num = $("#teamNum").val();
	var user_name = $("#userName").val();
	var user_num = $("#userNum"). val();
	var chatRoom_num=0;
	var chatRoom_name=null;
	var chatMember_num = 0;
	var searchTeamMemberList = null;
	var searchTeamMemberWithOutList = null;
	var inviteChatMemberList = [];
	var inviteChatMemberList2 = [];
	var webSocket = null;
	var webSocketMap={};
	var chatMember_numMap={};
	var chatRoomList = null;
	var thisChatRoom_num2=0;
	var ChatMemberMap={};
	var member=null;

	
	// 채팅방 초대를 위한 팀 멤버리스트 호출
	function getInviteChatMemberList(){
		// 멤버리스트 호출
		$.ajax({
            url:'/team/'+team_num+'/member/',
            type:'Get',
            dataType:'json',
            success:function(result){
            	console.log(result);
            	searchTeamMemberList=result;
            }
        });// $.ajax
	}
	
	// 채팅 보여주기
	function showChatting(result){
		var str=`<div class="dragablediv" id="room`+chatRoom_num+`">
			<div class="dragabledivheader" id="room`+chatRoom_num+`header"><h3>`+chatRoom_name+`
			<ion-icon name="person-add-outline" class="addChatMember" ></ion-icon>
			<ion-icon name="close-outline" class="chat-off"></ion-icon></h3>
			</div>
		<div class="job-chat-body">
			
	        <div class="job-chat"> 
	            <hr>`
		result.forEach(function(item){
			var regex = new RegExp("(.*?)\.(PNG|png|JPG|JPEG|jpg|jpeg|jpe|JPE|BMP|bmp|GIF|gif|)$");

			if(item.chatMember.member_num == member_num){
				var chatTime = new Date(+item.chat_date + 3240 * 10000).toISOString().split("T")[0];
				str += `<div class='send-thumnail-profile' style="background-image: url('/display?filename=`+item.chatMember.member.profile_name+`');"></div>`
				str +="<div class='send-nameDate'>"+item.chatMember.member.user.user_name+chatTime+"</div>";
				if(item.upload_name!=null){
					
					if(regex.test(item.upload_name)){
						
						str +="<p id="+item.chat_num+" class='send'><img class='chat-thumnail' src='/display?filename="+item.upload_name+"'>" +
						"<a href='/team/0/chatroom/download?fileName="+item.upload_name+"'><ion-icon name='download'></ion-icon></a><ion-icon class='chatdelete' name='close-outline' class='chat-off'></ion-icon></p>"
					}else{
					str +="<p id="+item.chat_num+" class='send'><img class='chat-thumnail' src='/display?filename="+"downloadingfile_87287.png"+"'>" +
							"<a href='/team/0/chatroom/download?fileName="+item.upload_name+"'><ion-icon name='download'></ion-icon></a><ion-icon class='chatdelete' name='close-outline' class='chat-off'></ion-icon></p>"}
				}else{
					str +=`<p id=`+item.chat_num+` class="send">`+item.chat_contents+`<ion-icon class="chatdelete" name="close-outline" class="chat-off"></ion-icon></h3></p>`
				}
			}else{
				var chatTime = new Date(+item.chat_date + 3240 * 10000).toISOString().split("T")[0];
				str += `<div class='receive-thumnail-profile' style="background-image: url('/display?filename=`+item.chatMember.member.profile_name+`');"></div>`
				str +="<div class='receive-nameDate'>"+item.chatMember.member.user.user_name+chatTime+"</div>";
				if(item.upload_name){
					if(regex.test(item.upload_name)){
						str +="<p id="+item.chat_num+" class='receive'><img class='chat-thumnail' src='/display?filename="+item.upload_name+"'>" +
						"<a href='/team/0/chatroom/download?fileName="+item.upload_name+"'><ion-icon name='download'></ion-icon></a></p>"
					}else{
					str +="<p id="+item.chat_num+" class='receive'><img class='chat-thumnail' src='/display?filename="+"downloadingfile_87287.png"+"'>" +
							"<a href='/team/0/chatroom/download?fileName="+item.upload_name+"'><ion-icon name='download'></ion-icon></a></p>"}
				}else{
					str +=`<p id=`+item.chat_num+` class="receive">`+item.chat_contents+`</h3></p>`
				}
			}
		});
	        str +=`</div>
	        
	        <input type='file' name="uploadFile" multiple>
	        
	        <textarea id="commentParentText" 
	        class="commentParentText form-control col-lg-12"style="width: 100%" rows="5" 
	        name="comment_contents"></textarea>
	        <input id="sendMessage" type="submit" value="전송" class="btn btn-default btn-lg sendMessage">
		</div></div>`
			
			
		$(".job-team-body2").append(str);
	    $(document).find(".job-chat").scrollTop($(document).find(".job-chat")[0].scrollHeight);
// console.log(document.getElementById("mydiv"));
	    dragElement(document.getElementById("room"+chatRoom_num));
	    console.log($(document).find("#chatRoom_num"))
	}
	
	// 채팅방 목록 왼쪽 사이드바에 출력
	function showChatRoomList(){
		$.ajax({
            url:'/team/'+team_num+'/chatroom/1/chatmember/'+member_num,
            type:'Get',
            dataType:'json',
            success:function(result){
            	var str = "";
            	chatRoomList.forEach(function(room){
            		result.forEach(function(item){
            			if(room.chatRoom_num == item.chatRoom_num){
            				str +=`<a href="#" class="nav__link-left onChatting" onclick="onChatting(event)" data-name="`+room.chatRoom_name+`" data-value="`+item.chatRoom_num+`"> <ion-icon
    						name="chatbubbles-outline" class="nav__icon-left"></ion-icon> <span
    					class="nav__name-left">`+room.chatRoom_name+`</span>
    				</a>`
            			}
            	})
            	

        			$("#chatRoomList").html(str);
        		});		
            }
        });// $.ajax	
	}
	
	// 채팅방 초대 모달에서 멤버 리스트 출력
	function showInviteChatRoomMemberList(result){
		var str="";
		result.forEach(function(item){
			str +=`<ul id="invite-list-group" class="list-group">
								<li class="list-group-item">
									<div class="thumnail-profile"></div> Lorem
									<button class="badge">초대</button>
								</li>
							</ul>`
			
			$("#chatRoomList").html(str);
		});	
	}
	
	// Socket onmessage
	function socketOnmessage(message){
		var chatTime = new Date().toISOString().split("T")[0];
		var messagearr = message.data.split("+")
		console.log(messagearr)
		
		var submessage=""
		for(var i=3; i<messagearr.length;i++){
			
			submessage += messagearr[i]
		}
		var str=""
		str += `<div class='receive-thumnail-profile' style="background-image: url('/display?filename=`+messagearr[1]+`');"></div>`
		str +="<div class='receive-nameDate'>"+messagearr[2]+chatTime+"</div>";
		
		str +=`<p class="receive">`+submessage+`</p>`
		$(document).find("#room"+message.data.split("+",1)[0]).find(".job-chat").append(str);
//		downscroll.scrollTop(downscroll[0].scrollHeight);
		$(document).find("#room"+message.data.split("+",1)[0]).find(".job-chat").scrollTop($(document).find("#room"+message.data.split("+",1)[0]).find(".job-chat")[0].scrollHeight);
	}
	
	// 채팅멤버번호 가져오는함수
	function getChatMemberNum(findChatRoom_num){

		if(!chatMember_numMap[findChatRoom_num]){
			$.ajax({
		        url:'/team/'+team_num+'/chatroom/'+chatRoom_num+'/chatmember',
		        type:'Get',
		        dataType:'json',
		        success:function(result){
		        	findChatMemberNum(result,findChatRoom_num)
		        }
		    });// $.ajax
		}
	}
	
	function findChatMemberNum(result,findChatRoom_num){
		result.forEach(function(item){
			if(item.chatRoom_num==chatRoom_num && item.member_num==member_num){

				chatMember_numMap[findChatRoom_num] = item.chatMember_num
			}
		})
	}
	
	
	// 채팅방 선택하면 실행
	$(document).on("click",".onChatting" ,function(e){
		setMember();
		chatRoom_num = $(this).data("value");
		chatRoom_name = $(this).data("name");
		if($(document).find("#room"+chatRoom_num).length){
			console.log("already")
			return
		}
		getChatMemberNum(chatRoom_num)
		$.ajax({
            url:'/team/'+team_num+'/chatroom/'+chatRoom_num+'/chat',
            type:'Get',
            dataType:'json',
            success:function(result){

            	showChatting(result);
            }
        });// $.ajax
		
		if(!webSocketMap[chatRoom_num]){
			webSocketMap[chatRoom_num] = new WebSocket("ws://localhost:8082/chatsocket/"+chatRoom_num)
		}
		
		webSocketMap[chatRoom_num].onopen = function(message){
			console.log("연결"+message)
		}
		
		webSocketMap[chatRoom_num].onclose = function(message){
			console.log("연결해제"+message)
		}
		
		webSocketMap[chatRoom_num].onerror = function(message){
			console.log("socketError"+message)
		}
		
		webSocketMap[chatRoom_num].onmessage = socketOnmessage
		
//		webSocketMap[chatRoom_num].binaryType="arraybuffer";
	});
	
	// 채팅방 생성되고 채팅방에 초대
	function inviteChatMemberAction(){
		inviteChatMemberList.forEach(function(member_num,index){
			$.ajax({
		        url:'/team/'+team_num+'/chatroom/'+chatRoom_num+'/chatmember/new',
		        type:'Post',
		        contentType:'application/json',
		        data:JSON.stringify({
		        	"chatRoom_num": chatRoom_num,
		            "member_num": member_num
		        }),
		        dataType:'json',
		        success:function(result){
		        	console.log("member invite succss");
		        	$("#insertChatRoomModal").modal("hide");
		        	callChatRoom();
		        	alert("채팅방 생성");
		        	
		        }
	    	});
		})
	}
	function callChatRoom(){
		$.ajax({
			
	        url:'/team/'+team_num+'/chatroom/',
	        type:'Get',
	        dataType:'json',
	        success:function(result){
	        	showChatRoomList(result);
	        	chatRoomList = result;
	        }
	    });// $.ajax
	}
	
	
	// 채팅방 리스트 ajax요청
	$.ajax({
		
        url:'/team/'+team_num+'/chatroom/',
        type:'Get',
        dataType:'json',
        success:function(result){
        	showChatRoomList(result);
        	chatRoomList = result;
        }
    });// $.ajax
	
	// 채팅방 생성 모달
	$("#createChatRoom").on("click", function(){
		$("#invite-chat-list").html("");
		console.log("fuuuck")
		$("#invite-wait-list").html("");
		$("#insertChatRoomModal").modal("show");
		inviteChatMemberList=[]
		inviteChatMemberList.push(member_num);
		getInviteChatMemberList();
		
	})

	// 채팅방 생성 모달의 검색바에서 검색어와 일치하는 팀멤버 검색
    $('#inviteChatMemberSearchbar').keyup(function(){
    	console.log(searchTeamMemberList);
		var keyword = $(this).val()
		var str=""
		searchTeamMemberList.forEach(function(member,index){
			if(((keyword==member.user.user_email.substr(0,keyword.length))
					||(keyword==member.user.user_name.substr(0,keyword.length)))
					&& keyword.length!=0
					&& member.isLive==1
					&& member_num!=member.member_num){
				str +=`<li class="list-group-item">
	                <div class="thumnail-profile"></div>
	                `+member.user.user_name+"__"+member.user.user_email+`<button id="inviteChatMember" class="badge " value="`+member.member_num+`">초대</button>
	            </li>`
	            
			};
		});
		
		$("#invite-chat-list").html(str);
	});
	
	$(document).on('click','#inviteChatMember',function(e){
		var invitemember_num = this.value;
		$(this).parent().remove();
		var str=""
		searchTeamMemberList.forEach(function(member,index){
			if(member.member_num==invitemember_num){
				str +=`<li class="list-group-item">
		            <div class="thumnail-profile"></div>
		            `+member.user.user_name+"__"+member.user.user_email+`<button id="cancelInviteChatMember" class="badge " value="`+member.member_num+`">취소</button>
		        </li>`
		        member.isLive=0;
				inviteChatMemberList.push(member.member_num);
			}
		});
		$("#invite-wait-list").append(str);
		
	})
	
	$(document).on('click','#cancelInviteChatMember',function(e){
		member_num = this.value;
		var str=""
		$(this).parent().remove();
		searchTeamMemberList.forEach(function(member,index){
			if(member.member_num==member_num){
				str +=`<li class="list-group-item">
	                <div class="thumnail-profile"></div>
	                `+member.user.user_name+"__"+member.user.user_email+`<button id="inviteChatMember" class="badge " value="`+member.member_num+`">초대</button>
	            </li>`
		        member.isLive=1;
				for(var i=0; i<inviteChatMemberList.length;i++){
					if(inviteChatMemberList[i]==member.member_num)
						inviteChatMemberList.pop(i)
						break
				}
				
			}
		});
		$("#invite-chat-list").html(str);
	})
	
	// 채팅방 생성
	$("#createRoomAction").on('click',function(){
		$.ajax({
	        url:'/team/'+team_num+'/chatroom/new',
	        type:'Post',
	        contentType:'application/json',
	        data:JSON.stringify({
	            "chatRoom_name": $("#insertChatRoomName").val(),
	            "team_num": team_num,
	        }),
	        dataType:'json',
	        success:function(result){
	        	console.log("chatroom create succss");
	        	chatRoom_num = result.chatRoom_num;
	        	inviteChatMemberAction()
	        }
    	});// $.ajax
		
	})
	
	// 채팅 전송
	$(document).on("click", ".sendMessage",function(e){
		if(!$(this).parent().find("input[name='uploadFile']")[0].files.length){
			console.log($(this).parent().children('.job-chat'))
			var message = $(this).parent().parent().find(".commentParentText").val()
			var thisChatRoom_num = $(this).parent().parent().attr('id').substr(4)
			var chatTime = new Date().toISOString().split("T")[0];
			str=""
			str += `<div class='send-thumnail-profile' style="background-image: url('/display?filename=`+member.profile_name+`');"></div>`
			str +="<div class='send-nameDate'>"+user_name+chatTime+"</div>";
//			str =`<p class="send">`+message+`</p>`
			str +=`<p id="+item.chat_num+" class='send'>` +message+`
			<ion-icon class='chatdelete' name='close-outline' class='chat-off'></ion-icon></p>`
			$(this).parent().children('.job-chat').append(str);
			$(this).parent().children('.job-chat').scrollTop($(this).parent().children('.job-chat')[0].scrollHeight);
			
			
			$.ajax({
		        url:'/team/'+team_num+'/chatroom/'+chatRoom_num+'/chat/new',
		        type:'Post',
		        contentType:'application/json',
		        data:JSON.stringify({
		            "chatMember_num": chatMember_numMap[thisChatRoom_num],
		            "chat_contents": message,
		        }),
		        dataType:'json',
		        success:function(result){
		        	console.log("creat chat succss");
		        }
	    	});// $.ajax
			webSocketMap[thisChatRoom_num].send(thisChatRoom_num+"+"+member.profile_name+"+"+user_name+"+"+message)
		}else{
			var file = $(this).parent().find("input[name='uploadFile']")[0].files;
			var chatTime = new Date().toISOString().split("T")[0];
			var str=""
				str += `<div class='send-thumnail-profile' style="background-image: url('/display?filename=`+member.profile_name+`');"></div>`
				str +="<div class='send-nameDate'>"+user_name+chatTime+"</div>";

			
//			$(this).parent().find("input[name='uploadFile']").clone();
			//$(this).parent().children('.job-chat').scrollTop($(this).parent().children('.job-chat')[0].scrollHeight);
			var downscroll = $(this).parent().children('.job-chat')
			downscroll.scrollTop(downscroll[0].scrollHeight);
			  console.log( file)
			  var formData = new FormData();
			  for(var i = 0; i<file.length; i++){
				  formData.append("uploadFile", file[i])
			  }
			  console.log('/team/'+team_num+'/chatroom/uploadfile')
			  var thisChatRoom_num = $(this).parent().parent().attr('id').substr(4)
			  $.ajax({
			        url:'/team/'+team_num+'/chatroom/uploadfile?chatMember_num='+chatMember_numMap[thisChatRoom_num],
			        type:'Post',
			        processData:false,
			        contentType:false,
			        data:formData,
			        success:function(result){
			        	console.log(result)
			        	console.log("upload succss");
			        	showUploadFile(result,str);
			        	showUploadFileSendSocket(result,thisChatRoom_num);
			        	downscroll.scrollTop(downscroll[0].scrollHeight);
			        }
		  		});// $.ajax
			  var reader = new FileReader();
			  var rawData = new ArrayBuffer();
			  $(this).parent().find("input[name='uploadFile']").val("");
			  
// reader.loadend = function(){
//				  
// }
//			  
// reader.onload = function (e) {
// rawData = e.target.result;
// webSocketMap[thisChatRoom_num].send(rawData);
// console.log(rawData)
// alert("file send")
//	                
// }
// reader.readAsArrayBuffer(file[0]);
			  
		};
		
	})
	
	$(document).on("keydown", ".commentParentText",function(e){
		
		if(e.keyCode !=13){
			return
		}
		if($(this).parent().find("input[name='uploadFile']")[0].files.length){
			return
		};
		var message = $(this).val()
		$(this).val("")
		
		var chatTime = new Date().toISOString().split("T")[0];
		str=""
		str += `<div class='send-thumnail-profile' style="background-image: url('/display?filename=`+member.profile_name+`');"></div>`
		str +="<div class='send-nameDate'>"+user_name+chatTime+"</div>";
		
//		str =`<p class="send">`+message+`</p>`
		str +=`<p class='send'>` +message+`
		<ion-icon class='chatdelete' name='close-outline' class='chat-off'></ion-icon></p>`
		$(this).parent().children('.job-chat').append(str);
		$(this).parent().children('.job-chat').scrollTop($(this).parent().children('.job-chat')[0].scrollHeight);
		$(this).parent().children('.job-chat').scrollTop($(this).parent().children('.job-chat')[0].scrollHeight);
		var thisChatRoom_num = $(this).parent().parent().attr('id').substr(4)
		console.log("fuck")
		console.log(chatMember_numMap[thisChatRoom_num])
		$.ajax({
	        url:'/team/'+team_num+'/chatroom/'+thisChatRoom_num+'/chat/new',
	        type:'Post',
	        contentType:'application/json',
	        data:JSON.stringify({
	            "chatMember_num": chatMember_numMap[thisChatRoom_num],
	            "chat_contents": message,
	        }),
	        dataType:'json',
	        success:function(result){
	        	console.log("creat chat succss"+thisChatRoom_num);
	        }
    	});// $.ajax
		webSocketMap[thisChatRoom_num].send(thisChatRoom_num+"+"+member.profile_name+"+"+user_name+"+"+message)
	})
	
	function dragElement(elmnt) {
		
	  var pos1 = 0, pos2 = 0, pos3 = 0, pos4 = 0;
	  if (document.getElementById(elmnt.id + "header")) {
	    // if present, the header is where you move the DIV from:
	    document.getElementById(elmnt.id + "header").onmousedown = dragMouseDown;
	// elmnt.onmousedown = dragMouseDown;
	  } else {
	    // otherwise, move the DIV from anywhere inside the DIV:
	    elmnt.onmousedown = dragMouseDown;
	  }
	
	  function dragMouseDown(e) {
		console.log("dragMouseDown")
	    e = e || window.event;
	    e.preventDefault();
	    // get the mouse cursor position at startup:
	    pos3 = e.clientX;
	    pos4 = e.clientY;
	    document.onmouseup = closeDragElement;
	    // call a function whenever the cursor moves:
	    document.onmousemove = elementDrag;
	  }
	
	  function elementDrag(e) {
		  console.log("elementDrag")
	    e = e || window.event;
	    e.preventDefault();
	    // calculate the new cursor position:
	    pos1 = pos3 - e.clientX;
	    pos2 = pos4 - e.clientY;
	    pos3 = e.clientX;
	    pos4 = e.clientY;
	    // set the element's new position:
	    elmnt.style.top = (elmnt.offsetTop - pos2) + "px";
	    elmnt.style.left = (elmnt.offsetLeft - pos1) + "px";
	  }
	
	  function closeDragElement() {
		  console.log("closeDragElement")
	    // stop moving when mouse button is released:
	    document.onmouseup = null;
	    document.onmousemove = null;
	  }
	  
	  $(document).on("click",".chat-off", function(){
		  $(this).parent().parent().parent().remove();
		  
	  })
	  
	}
	function showUploadFile(result,str){
			console.log(str);
		  str+= "<p class='send'><img class='chat-thumnail' src='/display?filename="+result+"'><a href='/team/1/chatroom/download?fileName="+result+"'><ion-icon name='download'></ion-icon></a><ion-icon class='chatdelete' name='close-outline' class='chat-off'></ion-icon></p>"
		  $(document).find(".job-chat").append(str)
		  
		  $(this).parent().children('.job-chat').scrollTop($(this).parent().children('.job-chat').scrollHeight);
	}
	
	function showUploadFileSendSocket(result,thisChatRoom_num){
		  var str= "<img class='chat-thumnail' src='/display?filename="+result+"'><a href='/team/1/chatroom/download?fileName="+result+"'><ion-icon name='download'></ion-icon></a></p>"
		  webSocketMap[thisChatRoom_num].send(thisChatRoom_num+"+"+member.profile_name+"+"+user_name+"+"+str)
	}
	
//	$(document).on("click",".chat-thumnail",function(){
//		console.log($(this).parent().html());
//		var str=`<div class="big-img" id="room`+chatRoom_num+`">
//			`+$(this).parent().html()+`
//		</div>`
//        	$(".job-team-body").append(str);
//	})
	
	$(document).on("mousedown",".chatdelete",function(){
		console.log($(this).parent().parent().parent().parent().attr('id').substr(4))
		
		var thisChatRoom_num = $(this).parent().parent().parent().parent().attr('id').substr(4)
		var chat_num = $(this).parent().attr("id");
		var ptext= $(this).parent();
		
		if(confirm("채팅을 삭제하시겠습니까?")){
			$.ajax({
		        url:'/team/'+team_num+'/chatroom/'+thisChatRoom_num+'/chat/'+chat_num,
		        type:'delete',
		        
		        success:function(result){
		        	console.log(result);
		        	ptext.text("삭제된 메시지")
		        }
	    	});// $.ajax
		}
		
	})
	
	//채팅방 초대 모달
	function memberInvite(result){
		console.log("aa")
		$.ajax({
		        url:'/team/'+team_num+'/member/withoutchatmember?chatroom_num='+chatroom_num,
		        type:'delete',
		        dataType:'json',
		        success:function(result){
		        	console.log(result);
		        }
	    	});// $.ajax
	}
	
	$(document).on("click",".addChatMember",function(){
		thisChatRoom_num2 = $(this).parent().parent().parent().attr('id').substr(4)
		$("#inviteMemberModal").modal("show")
		
		$.ajax({
		        url:'/team/'+team_num+'/member/withoutchatmember?chatroom_num='+thisChatRoom_num2,
		        type:'get',
		        dataType:'json',
		        success:function(result){
		        	console.log(result);
		        	searchTeamMemberWithOutList=result;
		        }
	    	});// $.ajax
	})
	
	
	 $('#inviteChatMemberSearchbar2').keyup(function(){
			var keyword = $(this).val()

			var str=""
				console.log(searchTeamMemberWithOutList)
				searchTeamMemberWithOutList.forEach(function(member,index){
					console.log(member)
				if(((keyword==member.user.user_email.substr(0,keyword.length))
						||(keyword==member.user.user_name.substr(0,keyword.length)))
						&& keyword.length!=0
						&& member.isLive==1
						&& member_num!=member.member_num){
					str +=`<li class="list-group-item">
		                <div class="thumnail-profile"></div>
		                `+member.user.user_name+"__"+member.user.user_email+`<button id="inviteChatMember2" class="badge inviteChatMember2" value="`+member.member_num+`">초대</button>
		            </li>`
		            
				};
			});
			
			$("#invite-chat-list2").html(str);
		});
	
	$(document).on('click','.inviteChatMember2',function(e){
		var invitemember_num = this.value;
		$(this).parent().remove();
		var str=""
			searchTeamMemberWithOutList.forEach(function(member,index){
			if(member.member_num==invitemember_num){
				str +=`<li class="list-group-item">
		            <div class="thumnail-profile"></div>
		            `+member.user.user_name+"__"+member.user.user_email+`<button id="cancelInviteChatMember" class="badge " value="`+member.member_num+`">취소</button>
		        </li>`
		        member.isLive=0;
				inviteChatMemberList2.push(member.member_num);
			}
		});
		$("#invite-wait-list2").append(str);
		
	})
	
	//채팅방에 초대
	$("#createRoomAction2").on('click',function(){
		inviteChatMemberList2.forEach(function(member_num,index){
			$.ajax({
		        url:'/team/'+team_num+'/chatroom/'+thisChatRoom_num2+'/chatmember/new',
		        type:'Post',
		        contentType:'application/json',
		        data:JSON.stringify({
		        	"chatRoom_num": thisChatRoom_num2,
		            "member_num": member_num
		        }),
		        dataType:'json',
		        success:function(result){
		        	console.log("member invite succss");
		        	alert("멤버 초대 완료")
		        	$("#inviteMemberModal").modal("hide")
		        }
	    	});
		})
	})

	function setMember(){
		$.ajax({
            url:'/team/'+team_num+'/member/'+user_num,
            type:'Get',
            processData:false,
            contentType:'application/json',
            
            dataType:'json',
            success:function(result){
                console.log(result);
                
                member=result;

            }
        });//$.ajax
	}
	
});