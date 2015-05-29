function fileOnDragOver(holder){
	holder.className = "element-drop";
	return false;
}

function fileOnDragEnd(holder) {
	holder.className = "element";
	return false;
}

function fileOnDrop(e) {

	this.className = '';
	e.preventDefault();

	var file = e.dataTransfer.files[0]; 
	var reader = new FileReader();
	
	reader.onload = function(event) {
		holder.style.background = 'url(' + event.target.result + ') no-repeat center';
	};
	reader.readAsDataURL(file);

	return false;
}

function expand(id,hostip,hostname,osname,acount){
	$.get("/views/components/line-ok-expand1.ht", function(contents) {
		var files = "";
		$.get(hostip+"/ws/all", function(data) {
			var json = jQuery.parseJSON( data );
			files = "<ul type=disk>";
		    for (var key in json.list) {
		    	var filename = json.list[key];
		    	files=files.concat("<li>").concat("<a href=\"javascript:open('" + hostip + "','" + filename + "')\">").concat(filename).concat("</a>").concat("</li>");
		    }
		    files=files.concat("</ul>");
		}).fail(function(error) {
			var errMsg = "";
			if(error.status == 0){
				files = "No se puede conectar con el host remoto";
			}else{
				files = error.responseText;
			}
		}).always(function() {
			$("#element-"+id)[0].outerHTML=contents
				.replace(/\${id}/g,id)
				.replace(/\${hostip}/g,hostip)
				.replace(/\${hostname}/g,hostname)
				.replace(/\${osname}/g,osname)
				.replace(/\${acount}/g,acount)
				.replace(/\${files}/g,files)

			//registerDragAndDrop($("#element-"+id+"-files"));
		});	
	})
}

function open(host,filename){
	$.post("/client/open", {host: host, filename: filename}, function(data) {})
	.fail(function(data) {
	    alert( "error:" + data );
	});	
}

function contract(id,hostip,hostname,osname,acount){
	$.get("/views/components/line-ok.ht", function(contents) {
		$("#element-"+id)[0].outerHTML=contents
			.replace(/\${id}/g,id)
			.replace(/\${hostip}/g,hostip)
			.replace(/\${hostname}/g,hostname)
			.replace(/\${osname}/g,osname)
			.replace(/\${acount}/g,acount)

	})
}

function reloadPage(){
	$.get("/host/all", function(data) {
	    for (var key in data) {
	        addElement(key,data[key]);
	    }
	}).fail(function(data) {
	    alert( "error:" + data );
	});	
}

function refresh(){
	var form = $( "#formulario" );
	$.post( form.attr( "action" ), form.serialize(), function(data) {
		addElement(new Date().getMilliseconds(),data);
	}).fail(function(data) {
	    alert( "error:" + data );
	});
	return false;
}

function addElement(id,host){
	$.get("/views/components/line-process.ht", function(contents) {
		$("#elements").append(contents
			.replace(/\${id}/g,id)
			.replace(/\${hostip}/g,host)
		);
	})
    .always(function() {
    	$.get(host+"/ws/status", function(data) {
    		var json = jQuery.parseJSON( data );
    		$.get("/views/components/line-ok.ht", function(contents) {
    			$("#element-"+id)[0].outerHTML=contents
    				.replace(/\${id}/g,id)
    				.replace(/\${hostip}/g,host)
    				.replace(/\${hostname}/g,json.name)
    				.replace(/\${osname}/g,json.os)
    				.replace(/\${acount}/g,json.size)
    		})
    		$("#element-"+id).css("color","green");
    	})
        .fail(function(error) {
    		$.get("/views/components/line-error.ht", function(contents) {
    			var errMsg = "";
    			if(error.status == 0){
    				errMsg = "No se puede conectar con el host remoto";
    			}else{
    				errMsg = error.responseText;
    			}
    			$("#element-"+id)[0].outerHTML=contents
    				.replace(/\${id}/g,id)
    				.replace(/\${hostip}/g,host)
    				.replace(/\${title}/g,errMsg)
    		})
    		$("#element-"+id).css("color","red");
        });
    });
}