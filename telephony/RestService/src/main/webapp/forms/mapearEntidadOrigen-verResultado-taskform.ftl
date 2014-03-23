<!DOCTYPE html>

<link rel="stylesheet" type="text/css" href="http://ajax.aspnetcdn.com/ajax/jquery.dataTables/1.9.4/css/jquery.dataTables.css"/></head>
<script>

	function AjaxCallGetTable() {
	var url = 'http://localhost:8080/mp-backend-poc3/rest/mp/table?queryDataId=' + ${idMapeo_task};
//	alert('mando alerta a ' + url);
	
	$.ajax({
	   type: 'GET',
		url: url,
//		async: false,
		contentType: "application/json",
		dataType: 'json',
		success: function(json) {
			console.dir(json);
//			alert(JSON.stringify(json));
			initializeDataTable(json);
		},
		error: function(e) {
		   console.log(e.message);
		}
	});

	};

	function initializeDataTable(json) {
	
	var jsonDataSC;

	jsonDataSC = json;
	alert('La cantidad total de registros encontrados para la consulta es: ' + jsonDataSC.cantRegisters);
	
	var columnas = jsonDataSC['columns'];
	var data = jsonDataSC['records'];

	var dataTableColumns = [];
	var dataTableData = [];
	
	for(var i in columnas){
		dataTableColumns[i] = { "sTitle": columnas[i].name };
	}

	for(var i in data){
		var register = [];
		for(var j in data[i].cells){
			register.push(data[i].cells[j].$);
		}
	//	dataTableData[i] = data[i].cells[1];
		dataTableData[i] = register;
	}
		$('#jqueryDataTable').dataTable( {
        "aaData": dataTableData,
        "aoColumns": dataTableColumns
    } );   

	$('#cantResultId').val(jsonDataSC.cantRegisters);
	}

	$( document ).ready(function() {
		AjaxCallGetTable();
//		initializeDataTable();
	});
</script>

	<h2>Mostrar Resultados.</h2>
	<br/>

	Cantidad Resultados Obtenidos: <input type="text" name="cantResult" id="cantResultId" disabled="true">

	<table id="jqueryDataTable">
		<tbody>
		</tbody>
	</table>

