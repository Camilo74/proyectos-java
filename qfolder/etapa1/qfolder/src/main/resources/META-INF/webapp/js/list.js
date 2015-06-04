$(document).ready(function() {
	
	$.get("http://localhost:8280/status", function(data) {
		$("#message").text("Agente conectado! [os: "+data+"]");
		$("#message").css("color","green");
		localStorage.setItem("agente", "true");
	})
    .fail(function() {
		$("#message").text("Agente DESCONECTADO!");
		$("#message").css("color","red");
		localStorage.setItem("agente", "false");
    });
});


function download(item,hosts){
	if(localStorage.getItem("agente") == "true"){
		$.get("http://localhost:8280/open",{hosts: hosts, filename: item}, function() {})
	    .fail(function(data) {
			alert(data.responseText);
	    });
	}else{
		window.location.href="/rest/download/"+item;
	}
	
}