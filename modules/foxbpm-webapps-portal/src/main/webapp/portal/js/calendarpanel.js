var taskPriority="";
$("#eventInfoTip").hide(); 
function showFormByUrl(formUrl){
	$("#contentFrame").attr("src",formUrl);
	$('#remoteModal').modal({backdrop:"static"});
}
$('#remoteModal').on('hide.bs.modal', function () {
	$('#calendar').fullCalendar('refetchEvents');
});
  
var showWeekend = true;
var pagefunction = function() {
	
	// full calendar
	 
    var hdr = {
        left:  'prev,next today',
        center: 'title',
        right:'month,agendaWeek,agendaDay'
    }; 
    /* initialize the calendar
	 -----------------------------------------------------------------*/
    $('#calendar').fullCalendar({
    	lazyFetching:false,
    	aspectRatio:2,
    	allDaySlot:false,
    	weekends:showWeekend,
    	slotEventOverlap:false,
    	lang:'zh-cn',
    	defaultView: 'agendaWeek',
        header: hdr,
        editable: false,
        droppable: false, // this allows things to be dropped onto the calendar !!!
        viewRender:function(a,b){
        	$(".fc-toolbar").css("margin-bottom","0px");
        	 
		},
		eventMouseover:function(event, jsEvent, view ) {
			$("#eventInfoTipCreateTime").html("创        &nbsp;&nbsp; 建:  "+event.start.format("YYYY-MM-DD HH:mm:ss"));
			$("#eventInfoTipTitle").html("主      &nbsp;&nbsp; 题:  "+event.title);
			
			var priority = "";
			if(event.className == "event,bg-color-red"){
				priority = "高";
			}else if(event.className == "event,bg-color-greenLight"){
				priority = "中";
			}else if(event.className == "event,bg-color-darken"){
				priority = "低";
			}
			
			$("#eventInfoTipPriority").html("优先级:  "+priority);
			
			$("#eventInfoTip").css("display", "block"); 
			$("#eventInfoTip").css("z-index", "1000000"); 
			$("#eventInfoTip").css("position", ""); 
			if(document.getElementById("jarviswidget-fullscreen-mode")){
				$("#eventInfoTip").css("position", "fixed"); 
			}
			
			var tipLeft = jsEvent.clientX-200;
			if(jsEvent.clientX+500>document.body.clientWidth){
				tipLeft = jsEvent.clientX-(700-(document.body.clientWidth-jsEvent.clientX))
			}
	      	$("#eventInfoTip2").css("top",jsEvent.clientY-70+document.body.scrollTop);  
	       	$("#eventInfoTip2").css("left",tipLeft);  
	       	$("#eventInfoTip").show();
	    	$("#eventInfoTip2").show();
			
		},
		eventMouseout:function( calEvent, jsEvent, view ) {
			$("#eventInfoTip").hide(); 
		},
		eventClick:function( event, jsEvent, view ) {
			if(document.getElementById("jarviswidget-fullscreen-mode")){
				$("#remoteModal").css("z-index", "1000000"); 
				$("#remoteModal").css("position", "fixed"); 
			}
			showFormByUrl(event.id);
		},

        events: function(start, end, timezone, callback) {
	    	$.ajax({
	            url: _serviceUrl + "runtime/tasks?assignee="+_userId+"&candidateUser="+_userId+"&ended=false",
	            dataType: 'json',
	            data: {
	            	createTimeB: start.format(),
	            	createTimeE: end.format()
	            },
	            success: function(doc) {
	                var events = []; 
	                var taskColor;
	                for(var i=0;i<doc.data.length;i++){ 
	                	
	                	//判断是否选择了优先级
	                	if(taskPriority == ""){
	                		if(doc.data[i].priority == "50"){
		                		taskColor = ["event", "bg-color-greenLight"];
		                	}else if(doc.data[i].priority == "0"){
		                		taskColor = ["event", "bg-color-darken"];
		                	}else if(doc.data[i].priority == "100"){
		                		taskColor = ["event", "bg-color-red"];
		                	}
		                	var create_time = new Date(doc.data[i].createTime);
		                	var due_time;
		                	if(doc.data[i].dueDate == null){
		                		due_time = null;
		                	}else{
		                		due_time = new Date(doc.data[i].dueDate);
		                	}
		                	events.push({
		                        title: doc.data[i].subject,
		                        start: create_time, 
								end:due_time,
		                        className: taskColor,
		                        id:"portal/expense/editExpense.jsp"+"?dataId="+doc.data[i].bizKey+"&taskId="+doc.data[i].id+"&processInstanceId="+doc.data[i].processInstanceId,
		                    });
	                	}else if(taskPriority == doc.data[i].priority){
	                		if(doc.data[i].priority == "50"){
		                		taskColor = ["event", "bg-color-greenLight"];
		                	}else if(doc.data[i].priority == "0"){
		                		taskColor = ["event", "bg-color-darken"];
		                	}else if(doc.data[i].priority == "100"){
		                		taskColor = ["event", "bg-color-red"];
		                	}
		                	var create_time = new Date(doc.data[i].createTime);
		                	var due_time;
		                	if(doc.data[i].dueDate == null){
		                		due_time = null;
		                	}else{
		                		due_time = new Date(doc.data[i].dueDate);
		                	}
		                	events.push({
		                        title: doc.data[i].subject,
		                        start: create_time, 
								end:due_time,
		                        className: taskColor,
		                        id:"portal/expense/editExpense.jsp"+"?dataId="+doc.data[i].bizKey+"&taskId="+doc.data[i].id+"&processInstanceId="+doc.data[i].processInstanceId,
		                    });
	                	}
	                	
	                }
	                callback(events);
	            }
	        });
	         
	    },

        eventRender: function (event, element, icon) {
        },

        windowResize: function (event, ui) {
            $('#calendar').fullCalendar('render');
        }
    });
 
    $('.fc-right, .fc-left').hide();

	$('#btn-prev').click(function () {
	    $('#calendar').fullCalendar('prev');
	    var moment = $('#calendar').fullCalendar('getDate');
	    var view = $('#calendar').fullCalendar('getView');
	    return false;
	});
	
	$('#btn-next').click(function () {
	    //$('.fc-next-button').click();
	    $('#calendar').fullCalendar('next');
	    var moment = $('#calendar').fullCalendar('getDate'); 
	    return false;
	});
	
	$('#calendar-buttons #btn-today').click(function () {
	    $('.fc-button-today').click();
	    return false;
	});
	
	$('#mt').click(function () {
		$('#select_view').html($('#mt').html()+"<i class='fa fa-caret-down'></i>");
	    $('#calendar').fullCalendar('changeView', 'month');
	});
	
	$('#ag').click(function () {
		$('#select_view').html($('#ag').html()+"<i class='fa fa-caret-down'></i>");
	    $('#calendar').fullCalendar('changeView', 'agendaWeek');
	});
	
	$('#td').click(function () {
		$('#select_view').html($('#td').html()+"<i class='fa fa-caret-down'></i>");
	    $('#calendar').fullCalendar('changeView', 'agendaDay');
	});
	
	$('#basicWeek').click(function () {
		$('#select_view').html($('#basicWeek').html()+"<i class='fa fa-caret-down'></i>");
	    $('#calendar').fullCalendar('changeView', 'basicWeek');
	});
	
	$('#basicDay').click(function () {
		$('#select_view').html($('#basicDay').html()+"<i class='fa fa-caret-down'></i>");
	    $('#calendar').fullCalendar('changeView', 'basicDay');
	});
	
	$('#task_createtime').click(function () {
		$('#select_time').html($('#task_createtime').html()+"<i class='fa fa-caret-down'></i>"); 
	});
	$('#task_duration').click(function () {
		$('#select_time').html($('#task_duration').html()+"<i class='fa fa-caret-down'></i>"); 
	});
	
	$('#priority_high').click(function () {
		taskPriority = "100";
		$('#select_priority').html($('#priority_high').html()+"<i class='fa fa-caret-down'></i>"); 
		$('#calendar').fullCalendar('refetchEvents');
	});
	
	$('#priority_mid').click(function () {
		taskPriority = "50";
		$('#select_priority').html($('#priority_mid').html()+"<i class='fa fa-caret-down'></i>"); 
		$('#calendar').fullCalendar('refetchEvents');
	});
	
	$('#priority_low').click(function () {
		taskPriority = "0";
		$('#select_priority').html($('#priority_low').html()+"<i class='fa fa-caret-down'></i>"); 
		$('#calendar').fullCalendar('refetchEvents');
	});
	$('#priority_all').click(function () {
		taskPriority = "";
		$('#select_priority').html($('#priority_all').html()+"<i class='fa fa-caret-down'></i>"); 
		$('#calendar').fullCalendar('refetchEvents');
	});
	
	
	
};