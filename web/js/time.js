window.onload = function(){
	ShowTime();
}
function ShowTime(){
	var now = new Date();
	var year = now.getFullYear();
	var month = now.getMonth()+1;
	var day = now.getDate();
	var hour = now.getHours();
	var minute = now.getMinutes();
	var second = now.getSeconds();
	
	if(hour<10){
		hour = "0"+hour;
	}
	if(minute<10){
		minute = "0"+minute;
	}
	if(second<10){
		second = "0"+second;
	}
	
	var msg = year+"-"+month+"-"+day+"&nbsp;&nbsp;&nbsp;"+hour+":"+minute+":"+second;
	document.getElementById('time').innerHTML = msg;
	setTimeout("ShowTime()",1000);
}