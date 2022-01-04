/*===== EXPANDER MENU  =====*/ 
$(document).ready(function(){
	function setHome(){
			var str = `<div id="calendar"></div>`;
			$(".job-team-body3").html("");
			$(".job-team-body").html(str);
			var calendarEl = document.getElementById("calendar");
			console.log(calendarEl);
			
			var calendar = new FullCalendar.Calendar(calendarEl, {
				headerToolbar : {
					left : "",
					center : "title",
					right : "dayGridMonth,timeGridWeek,timeGridDay",
				},
				locale : "ko",
				timeZone : "local",
				navLinks : true, 
				selectable : true,
				selectMirror : true,
				select : function() {
					// Display the modal.
					// You could fill in the start and end fields based on the parameters
					$('#CalModal').modal('show');
					
					
						var title = prompt('Event Title:');
						var start = prompt('Event startDate:');
						var end = prompt('Event endDate:');
						var allDay = prompt('Event allDay:');
						if (title) {
							calendar.addEvent({
								title: title,
								start: start,
								end: end,
								allDay: allDay
							})
						}
							calendar.unselect()
							console.log("화면 이벤트 출력 성공");
						

				},//select end
				events:[ 
					{
						title : "채팅기능 개발일정",
						start : "2021-12-03",
						end 	: "2021-12-06"
					},
					{
						title : "이벤트 추가",
						start : "2021-12-03",
						end 	: "2021-12-08"
					},
					{
						title : "ToDo 리스트 개발",
						start : "2021-12-17",
						end 	: "2021-12-25"
					},
					{
						title : "댓글 추가기능 개발",
						start : "2021-12-04",
						end 	: "2021-12-11"
					},
					{
						title : "JSP 비동기화 작업",
						start : "2021-12-03",
						end 	: "2021-12-09				"
					},
					{
						title : "MVC2 작업중",
						start : "2021-12-03",
						end 	: "2021-12-10"
					}
					
				],
				eventClick : function(arg) {
					console.log("일정등록 이벤트 삭제");
					console.log(arg.events);
					if (confirm("일정을 삭제하시겠습니까?")) {
						arg.event.remove();
					}
				},
				editable : true,
				dayMaxEvents : true,
			});
			calendar.render();
			
    }
    
    //홈버튼 클릭했을때 홈으로 돌아옴
    $("#toggle-key-home").on("click", function(){setHome()})
    
    //초기 홈화면 세팅
    //setHome()
	
  $("#toggle-key-home").on("click",function(){
    console.log("집으로")
    $("#navbar-search").removeClass("expander-search")
    $("#navbar-search2").removeClass("expander-search")
    $("#navbar-search3").removeClass("expander-search")
  })


  $("#toggle-key").on("click",function(){
    console.log("눌러줘")
    $("#navbar-search2").removeClass("expander-search")
    $("#navbar-search3").removeClass("expander-search")
    $("#navbar-search").toggleClass("expander-search")
  })

  $("#toggle-key2").on("click",function(){
    console.log("눌러줘2")
    $("#navbar-search").removeClass("expander-search")
    $("#navbar-search3").removeClass("expander-search")
    $("#navbar-search2").toggleClass("expander-search")
  })

  $("#toggle-key3").on("click",function(){
    console.log("눌러줘3")
    $("#navbar-search").removeClass("expander-search")
    $("#navbar-search2").removeClass("expander-search")
    $("#navbar-search3").toggleClass("expander-search")
  })

  $("#toggle-key-member").on("click",function(){
    console.log("집으로")
    $("#navbar-search").removeClass("expander-search")
    $("#navbar-search2").removeClass("expander-search")
    $("#navbar-search3").removeClass("expander-search")
  })
})

