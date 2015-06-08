function fileOnDragOver(holder){
	holder.className = "element-drag";
	event.preventDefault();
	return false;
}

function fileOnDragEnd(holder) {
	holder.className = "element";
	return false;
}

function fileOnDrop(event,id,hostip,username,hostname,osname,acount) {

	event.preventDefault();
	var holder = $("#content-"+id)[0];
	holder.className = "element-drop";

	var files = event.dataTransfer.files;     
	var fd = new FormData();
    fd.append('uploadedFile', files[0]);
    
    var progress = jQuery('<progress>', {
        id: "progress-"+id,
        value: "0",
        max: "100"
    });
    
	$("#footer-"+id)[0].outerHTML = progress[0].outerHTML;
	
    $.ajax({
        url: hostip+"/ws/put",
        type: "POST",
        data: fd,
        processData: false, //Work around #1
        contentType: false, //Work around #2
        success: function(){
            holder.className = "element";
            expand(id,hostip,username,hostname,osname);
        },
        error: function(){
        	holder.className = "element";
        	alert("Failed");
        },
        //Work around #3
        xhr: function() {
            myXhr = $.ajaxSettings.xhr();
            if(myXhr.upload){
                myXhr.upload.addEventListener('progress',function(evt) {
			    if (evt.lengthComputable) {
			        var percentComplete = (evt.loaded / evt.total) * 100;
			        $('#progress-'+id)[0].value = percentComplete;
			    }  
			}, false);
            } else {
                console.log("Uploadress is not supported.");
            }
            return myXhr;
        }
    });
	
	return false;
}

function expand(id,hostip,username,hostname,osname){
	$.get("/views/components/line-ok-expand12.ht", function(contents) {
		var acount = 0;
		var files = "";
		$.get(hostip+"/ws/all", function(data) {
			var json = jQuery.parseJSON( data );
			acount = json.list.length;
			files = "<ul type=disk>";
		    for (var key in json.list) {
		    	var filename = json.list[key];
		    	var filekey = "file-"+id+key;
		    	files=files.concat("<li id=\""+filekey+"\">").concat("<a href=\"javascript:open('"+filekey+"','" + hostip + "','" + filename + "')\">").concat(filename).concat("</a>").concat("<label class='namespace'>").concat(" [12kb]").concat("</label>").concat("</li>");
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
				.replace(/\${username}/g,username)
				.replace(/\${hostname}/g,hostname)
				.replace(/\${osname}/g,osname)
				.replace(/\${acount}/g,acount)
				.replace(/\${files}/g,files)
		});	
	})
}

function open(filekey,host,filename){
	
	var label=$("<li>",{
		id: filekey
	}).append($("<label>",{
		class:'namespace'
	}).text(filename + " [Damian]"));
	
	$("#"+filekey)[0].outerHTML = label[0].outerHTML;
	$.post("/client/open", {host: host, filename: filename}, function(data) {
		var a = $("<li>",{
			id: filekey
		}).append($("<a>",{
			href: "javascript:open('"+filekey+"','"+host+"','"+filename+"')"
		}).text(filename));
		$("#"+filekey)[0].outerHTML=a[0].outerHTML;
	})
	.fail(function(data) {
		//alert( "error:" + data );
		window.location.href=host+"/ws/get/"+filename;
	});	
}

function contract(id,hostip,username,hostname,osname,acount){
	$.get("/views/components/line-ok12.ht", function(contents) {
		$("#element-"+id)[0].outerHTML=contents
			.replace(/\${id}/g,id)
			.replace(/\${hostip}/g,hostip)
			.replace(/\${username}/g,username)
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

function refreshElement(id,host){
	$("#element-"+id).remove();
	addElement(id,host);
}

function addElement(id,host){
	$.get("/views/components/line-process12.ht", function(contents) {
		$("#elements").append(contents
			.replace(/\${id}/g,id)
			.replace(/\${hostip}/g,host)
		);
	})
    .always(function() {
    	$.get(host+"/ws/status", function(data) {
    		var json = jQuery.parseJSON( data );
    		$.get("/views/components/line-ok12.ht", function(contents) {
    			$("#element-"+id)[0].outerHTML=contents
    				.replace(/\${id}/g,id)
    				.replace(/\${hostip}/g,host)
    				.replace(/\${username}/g,json.username)
    				.replace(/\${hostname}/g,json.hostname)
    				.replace(/\${osname}/g,json.os)
    				.replace(/\${acount}/g,json.size)
    		})
    		$("#element-"+id).css("color","green");
    	})
        .fail(function(error) {
    		$.get("/views/components/line-error12.ht", function(contents) {
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