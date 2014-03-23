<!DOCTYPE html>

<link rel="stylesheet" type="text/css" href="http://ajax.aspnetcdn.com/ajax/jquery.dataTables/1.9.4/css/jquery.dataTables.css"/></head>
<script>
var sources = [{"columns":[],"name":"Select a DS"}];
var mapping = [];
var origenes = [];
var oTable = null;

	function AjaxCallGetTable() {
	var url = 'http://localhost:8080/mp-backend-poc3/rest/mp/entity?entityId=' + ${idEntidad_task};
	
	$.ajax({
	   type: 'get',
		url: url,
//		async: false,
		contentType: "application/json",
		dataType: 'json',
		success: function(json) {
		   	console.dir(json);
			iniciarSource(json);
		},
		error: function(e) {
		   console.log(e.message);
		}
	});

	}

	function iniciarSource(json){
	var url = 'http://localhost:8080/mp-backend-poc3/rest/mp/sourcesMetada?sourcesId=' + ${idOrigen_task};
	
	$.ajax({
	   type: 'get',
		url: url,
	//	async: false,
		contentType: "application/json",
		dataType: 'json',
		success: function(origenes) {
			sources.push(origenes);
//			alert(JSON.stringify(origenes));
			initializeDataTable(json);
		},
		error: function(e) {
		   console.log(e.message);
		}
	});

	}

	function crearTablaMapeo(json){

		for(var i in json.atributes){
		$("#mapSourceEntityTableId").append('<tr><td>' + json.atributes[i].name + '</td><td>' +
		'<select onchange="jsFunction(this)"><option value="a">A</option><option value="b">B</option></select></td></tr>');

		}

	}



function initializeDataTable(json) {
	
//	alert(JSON.stringify(json));
	var jsonDataSC = {"columns":[{"name":"ATRIBUTO ENTIDAD MP"},{"name":"ORIGEN"}, {"name":"CAMPO"}],
	"data":[{"cells":["NOMBRE",'<select class="approveButton"><option value="a">A</option><option value="b">B</option></select>','<select class="approveButton"><option value="a">1</option><option value="b">2</option></select>']},
	{"cells":["EDAD",'<select class="approveButton"><option value="a">A</option><option value="b">B</option></select>','<select class="approveButton"><option value="a">1</option><option value="b">2</option></select>']},
	{"cells":["SEXO",'<select class="approveButton"><option value="a">A</option><option value="b">B</option></select>','<select class="approveButton"><option value="a">1</option><option value="b">2</option></select>']}]
	};

//	var columnas = jsonDataSC['columns'];
//	var data = jsonDataSC['data'];

	var dataTableColumns = [{ "sTitle": "ATRIBUTO ENTIDAD MP" },{ "sTitle": "ORIGEN" },{ "sTitle": "CAMPO" }];
	var dataTableData = [];
	

	var dataSourcesName = [];
	for(var i in sources){
		var obj = {"name":sources[i].name,"id": i};
		dataSourcesName.push(obj);	
	}
//	alert(JSON.stringify(dataSourcesName));
	for(var i in json.atributes){
//		dataTableData[i] = [json.atributes[i].name,'<select class="approveButton"><option value="a">A</option><option value="b">B</option></select>',];
		dataTableData[i] = [json.atributes[i].name,createCombo("approveButton",dataSourcesName),];
	}


	$('#jqueryDataTable').dataTable( {
		"aaData": dataTableData,
		"aoColumns": dataTableColumns
	} );   
	
	oTable = $('#jqueryDataTable').dataTable();

	}

	function createCombo(classForFunction, opciones){
		var cadenaInicial = '<select class="' + classForFunction + '">';
		var cadenaFinal = '</select>';
		var cadenaOpciones = '';		
		for(var i in opciones){
//		alert(JSON.stringify(opciones[i]));
			cadenaOpciones = cadenaOpciones + '<option value="'+ opciones[i].id + '">' + opciones[i].name + '</option>';
		}
		return (cadenaInicial + cadenaOpciones + cadenaFinal);
	}

/***********************************************************************/
/***************************ONREADY*************************************/
/***********************************************************************/

	$( document ).ready(function() {
		AjaxCallGetTable();
//		initializeDataTable();

		$("#jqueryDataTable").on("change", ".approveButton", function(event) { 
//			var oTable = $('#jqueryDataTable').dataTable();
			var row = $(this).closest("tr").get(0);		
			var position = oTable.fnGetPosition(row);
			if(this.value != 0){
//				agregarOrigen(oTable.fnGetPosition(row), this.options[this.selectedIndex].innerHTML);
				agregarOrigen(oTable.fnGetPosition(row), this.value);
				oTable.fnUpdate(createCombo("mapearAtributo",sources[this.value].columns), oTable.fnGetPosition(row), 2 ); // Single cell
			}else{
//				alert('Posicion: ' + oTable.fnGetPosition(row));
				borrarMapeo(oTable.fnGetPosition(row));
//				alert('Borre, queda: ' + JSON.stringify(mapping));
				oTable.fnUpdate(null, oTable.fnGetPosition(row), 2 );
			}
		});


		$("#jqueryDataTable").on("change", ".mapearAtributo", function(event) { 
//			alert('mapearAtributo' + this.options[this.selectedIndex].innerHTML);
//			var oTable = $('#jqueryDataTable').dataTable();
			var row = $(this).closest("tr").get(0);
			agregarField(oTable.fnGetPosition(row), this.options[this.selectedIndex].innerHTML, this.value);
		});
	});

/***********************************************************************/
/*********************RESPUESTA*****************************************/
/***********************************************************************/

var mappingResult = [];

	function getDataResult(){
		var mt = $('#maxTransactionsId').val();
		var lista = {'mapping' : mappingResult, 'maxTransactions': mt, 'sourceId': 0, 'entityId': 0}
		$.ajax({
		   type: 'post',
			url: 'http://localhost:8080/mp-backend-poc3/rest/mp/mapping',
	//		async: false,
			contentType: "application/json",
			dataType: 'json',
			data : JSON.stringify(lista),
			success: function(json) {
				alert('El resultado ' + json + ' fue guardado correctamente');
			   console.dir(json);
			   $('#idMapeo_task').val(json);	
			},
			error: function(e) {
			   console.log(e.message);
			}
		});
	}
	
		function asignarVariable(resultado){
		alert('El resultado ' + resultado + 'fue guardado correctamente');
		$('#idOrigen_task').val(resultado);		
	} 

/***********************************************************************/
/*******************COMBOS**********************************************/
/***********************************************************************/

	function agregarOrigen(id, origen){
		borrarMapeo(id);
		var obj = {"idRow" : id, "idAtributo" : "", "idOrigen" : origen, "field": ""};
//		alert('OBJECT ES: ' + JSON.stringify(obj));
		mapping.push(obj);
	}

	function borrarMapeo(id){
		for(var i in mapping){
			if(mapping[i].idRow == id){
				mapping.splice(i, 1);
			}		
		}
	}

	function agregarField(id, field, idAtributo){
		for(var i in mapping){
			if(mapping[i].idRow == id){
				mapping[i].field = field;
//ACA DEBERIA SETTEARLE EL ID DEL ATRIBUTO CORRESPONDIENTET A LA ENTIDAD, NO LO ESTA HACIEDNO, DEBERIA AGREGAR METADATA O
//UNA COLUMNA NUEVA EN LA TABLA PARA PODER OBTENER ESTE CAMPO, POR AHORA LO MAPEO CON EL ROW YA QUE AL SER SOLO UNA FUENTE
//DE DATOS SE CORRESPONDE PERO AL AUMENTAR EL DS ORIGEN ESTO YA NO FUNCIONA
				mapping[i].idRow = idAtributo;
			}		
		}
	}

/***********************************************************************/
/*******************PARSEO PARA RETORNAR********************************/
/***********************************************************************/


	function viewMapping(){
	mappingResult = [];
//	alert('MAPPING: ' + JSON.stringify(mapping));
		for(var i in mapping){
			evaluarObjetoMapeo(mapping[i]);
		}

//	alert('MappingResult: ' + JSON.stringify(mappingResult));
	getDataResult();
 	}

	function evaluarObjetoMapeo(campos){
		var attrObj = {"idAtributo" : campos.idAtributo , "field" : campos.field};
		for (var i in mappingResult){
			if(mappingResult[i].idOrigen == campos.idOrigen){
				mappingResult[i].atributeMap.push(attrObj);
				return true;
			}
		}
		agregarOrigenAResult(campos);
		return false;
	}

	function agregarOrigenAResult(campos){
		var attrObj = {"idAtributo" : campos.idAtributo , "field" : campos.field};

		var atributos = [];
		atributos.push(attrObj);
		var obj = {"idOrigen" : campos.idOrigen, "atributeMap" : atributos};
		mappingResult.push(obj);
	}

/***********************************************************************/
/*****************************END***************************************/
/***********************************************************************/

</script>

	<h2>Mapeo Entidad MP - Origenes De Datos.</h2>
	<br/>
<!--	Filtros: <input type="text" name="filtro">	
	<br/>
	<input type="button" value="Add new row" onclick="agregarFila()"></input><br/><br><br>

	<table id="mapSourceEntityTableId">
		 <tbody>

		  </tbody>
	</table>	
-->
	<table id="jqueryDataTable">
		<tbody>
		</tbody>
	</table><br><br>	

	Max. Transactions: <input type="number" name="maxTransactions" id="maxTransactionsId" value=100><br>

	<br><input type="button" onclick="viewMapping()" value="Mapear"></input>
	
	<input type="hidden" id="idMapeo_task" name="idMapeo_task"/>
	

