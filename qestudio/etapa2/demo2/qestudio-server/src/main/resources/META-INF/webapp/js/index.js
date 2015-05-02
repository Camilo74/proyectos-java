$(document).ready(function() {
	$.ajax({
		url: "http://localhost:8280/status",
		type: "GET",
		dataType: "jsonp",
		crossDomain: true,
		jsonpCallback: "jsonCallback",
		success: function(res){
			$("#message").text("Agente conectado! [os: "+res.os+"]");
			$("#message").css("color","green");
			localStorage.setItem("agente", "true");
		},
		error: function(err){
			$("#message").text("Agente DESCONECTADO!");
			$("#message").css("color","red");
			localStorage.setItem("agente", "false");
		}
	});		
});

function jsonCallback(json) {
	//alert(json);
}	

function download(item,hosts){
	if(localStorage.getItem("agente") == "true"){
		$.ajax({
			url: "http://localhost:8280/open",
			data:{hosts: hosts, filename: item},
			type: "GET",
			dataType: "jsonp",
			crossDomain: true,
			jsonpCallback: "jsonCallback",
			success: function(res){},
			error: function(err){}
		});
	}else{
		window.location.href="/rest/download/"+item;
	}

}