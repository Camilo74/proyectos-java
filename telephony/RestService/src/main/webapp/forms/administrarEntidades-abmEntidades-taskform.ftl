<!DOCTYPE html>
<link rel="stylesheet" type="text/css" href="http://ajax.aspnetcdn.com/ajax/jquery.dataTables/1.9.4/css/jquery.dataTables.css"/></head>
<script type="text/javascript">

var entitiesExistentes = null;
var opcionesParaCombo = [{"name":'Select',"id": -1}];
var oTable = null;
var tableAllEntities = null;

/**************************FEATURES**********************************/

	function addNewAttribute(){
		var nombre = $('#atributeNameId').val();
		var tipo =$('#comboTipoAtributoId').val();
		var addinfo = $('#addInfoId').val();
		var droolsMapping = $('#droolsMappingId').val();

		if(nombre == "" || (tipo == "nominal" && addinfo == "")){
			alert('Complete todos los valores del formulario para agregar un nuevo atributo');
		}else{
			oTable.fnAddData( [nombre,tipo,addinfo,droolsMapping,"<input class='approveButton' type='button' value='Delete'></input>"] );
			$('#atributeNameId').removeAttr('value');
			$('#addInfoId').removeAttr('value');
			$('#droolsMappingId').removeAttr('value');
		}	

	}	


/*******************PARSEO PARA RETORNAR********************************/

	
	function getDataResult(){
//		oTable = $('#jqueryDataTable').dataTable();
		var data = oTable.fnGetData();
		
		if(data == ""){
			alert('No ha sido agregado ningun origen de datos aún.');
			return;
		}

		var entId = $("#entityId").val();
		if(entId == ""){entId = -1;}
		jsonTable = data;//JSON.stringify(data);
		var atributes = {'atributes' : [], 'idEntity' : entId,'entityName': $("#entityNameId").val()};
//		alert(JSON.stringify(atributes));
		for(var i in jsonTable){
			obj = {'name': jsonTable[i][0],'type':jsonTable[i][1],'aditionalInfo': jsonTable[i][2], 'droolsMapping': jsonTable[i][3]}
//			alert(JSON.stringify(obj));
			atributes.atributes.push(obj);
		}
//					alert(JSON.stringify(atributes));
		ajaxRestJsonCall("http://localhost:8080/mp-backend-poc3/rest/mp/newEntity",JSON.stringify(atributes),refreshEntitiesTable,'post')

	}
	
	function asignarVariable(resultado){
//		alert('El resultado ' + resultado + 'fue guardado correctamente');
		$('#idEntidad_task').val(resultado);		
	}

/***************************ONREADY*************************************/

	function llenarTablaConEntidades(json){
		var dataTableEntitiesData = [];
		for(var i in json.lista){
			var fila = [];
			var atributos = '';
			fila.push(json.lista[i].idEntity);
			fila.push(json.lista[i].entityName);
			
			for(var j in json.lista[i].atributes){
				var atributos = atributos + ' - ' + json.lista[i].atributes[j].name;
			}
			fila.push(atributos);
			fila.push("<input class='deleteEntity' type='button' value='Delete Entity'></input>");
			dataTableEntitiesData.push(fila);
		}

	}	

	function crearTablaEntidades(json){
		var jsonDataEntities = {"columns":[{"name":"Id"},{"name":"name"},{"name":"Atributes"},{ "name" : "delete"}],
		"records":[]
		};

		var columnasEntities = jsonDataEntities['columns'];
		var dataEntities = jsonDataEntities['records'];

		var dataTableEntitiesColumns = [];
		var dataTableEntitiesData = [];

		for(var i in columnasEntities){
			dataTableEntitiesColumns[i] = { "sTitle": columnasEntities[i].name };
		}

		for(var i in json.lista){
			var fila = [];
			var atributos = '';
			fila.push(json.lista[i].idEntity);
			fila.push(json.lista[i].entityName);
			
			for(var j in json.lista[i].atributes){
				var atributos = atributos + ' - ' + json.lista[i].atributes[j].name;
			}
			fila.push(atributos);
			fila.push("<input class='deleteEntity' type='button' value='Delete Entity'></input>");
			dataTableEntitiesData.push(fila);
		}

		$('#entitiesTableId').dataTable( {
			"aaData": dataTableEntitiesData,
			"aoColumns": dataTableEntitiesColumns
	    	} );   

		tableAllEntities = $('#entitiesTableId').dataTable();
//		alert(JSON.stringify(dataTableEntitiesData));
	}


	function crearTabla(json) {
	
	var jsonData = {"columns":[{"name":"name"},{"name":"type"},{"name":"additional info"},{"name":"drools mapping"},
	{ "name" : "delete"}],
	"records":[]
	};

	var columnas = jsonData['columns'];

	var data = jsonData['records'];

	var dataTableColumns = [];
	var dataTableData = [];
	
	for(var i in columnas){
		dataTableColumns[i] = { "sTitle": columnas[i].name };
	}

	$('#jqueryDataTable').dataTable( {
		"aaData": dataTableData,
		"aoColumns": dataTableColumns
    	} );   

	oTable = $('#jqueryDataTable').dataTable();

	}

	$( document ).ready(function() {
		ajaxRestJsonCall('http://localhost:8080/mp-backend-poc3/rest/mp/entityDataTypes',null,comboTipoDatoCallBack,'get');
		//jQuery.getJSON("http://172.27.11.13:8080/mp-backend-poc3/rest/mp/entityDataTypes", comboTipoDatoCallBack);
		
		ajaxRestJsonCall('http://localhost:8080/mp-backend-poc3/rest/mp/allEntities',null,comboEntidadesExistentes,'get');
		crearTabla();

	$("#jqueryDataTable").on("click", ".approveButton", function(event) { 
		var row = $(this).closest("tr").get(0);
		oTable.fnDeleteRow(oTable.fnGetPosition(row));
	 });

	$("#entitiesTableId").on("click", ".deleteEntity", function(event) { 
		var row = $(this).closest("tr").get(0);
		var aPos = tableAllEntities.fnGetPosition(row);
		var aData = tableAllEntities.fnGetData( aPos );
//		alert('Eliminar el id: ' + aData[0] + ' y luego hacer un refresh de la tabla');
		var datos='entityId=' + aData[0];

		ajaxRestJsonCall('http://localhost:8080/mp-backend-poc3/rest/mp/deleteEntity',datos,refreshEntitiesTable,'get');
	 });


	});

	function createCombo(classForFunction, opciones, idCombo){

		var cadenaInicial = '<select class="' + classForFunction + '" id="' + idCombo + '">';
		var cadenaFinal = '</select>';
		var cadenaOpciones = '';		
		for(var i in opciones){
			cadenaOpciones = cadenaOpciones + '<option value="'+ opciones[i].id + '">' + opciones[i].name + '</option>';
//			alert(JSON.stringify(cadenaOpciones));
		}
//			alert(JSON.stringify(cadenaInicial + cadenaOpciones + cadenaFinal));
		return (cadenaInicial + cadenaOpciones + cadenaFinal);
	}

/********************************************************************/

	function ajaxRestJsonCall(url,datos,callBack,method){
		$.ajax({
		   	type: method,
			url: url,
//			async: false,
			contentType: "application/json",
			dataType: 'json',
			data: datos,
			success: function(json) {
				if(callBack != null){
					callBack(json);
				}
			},
			error: function(e) {
				console.log(e.message);
			}
		});	
	}

	function comboTipoDatoCallBack(json){
//		alert(json.lista);
		if(json != null){
			$("#comboTiposDatoEntidadId").append(createCombo("tipoEntidades", json.lista, "comboTipoAtributoId"));
		}
	}

	function comboEntidadesExistentes(json){
		entitiesExistentes = json;
		crearTablaEntidades(json);
	}

	function refreshEntitiesTable(value){
//		alert(value);
		oTable.fnClearTable();
		ajaxRestJsonCall('http://localhost:8080/mp-backend-poc3/rest/mp/allEntities',null,refreshET,'get');
	}

	function refreshET(json){
		entitiesExistentes = json;
		var dataTableEntitiesData = [];
		for(var i in json.lista){
			var fila = [];
			var atributos = '';
			fila.push(json.lista[i].idEntity);
			fila.push(json.lista[i].entityName);
			
			for(var j in json.lista[i].atributes){
				var atributos = atributos + ' - ' + json.lista[i].atributes[j].name;
			}
			fila.push(atributos);
			fila.push("<input class='deleteEntity' type='button' value='Delete Entity'></input>");
			dataTableEntitiesData.push(fila);
		}
		tableAllEntities.fnClearTable();
		tableAllEntities.fnAddData(dataTableEntitiesData);

	}

/*****************************END***************************************/


</script>

	<h2>Configurar Entidad Motor Predictivo.</h2>
	


	<table border=0>
		<tr>
			<td align="left">Entity Name: </td>
			<td><input type="text" name="entityName" id="entityNameId"></td>
		</tr>
			<td><input type="hidden" name="entityId" id="entityId"></td>
		<tr>
			<td align="left"><h3>Atributes.</h3></td>
		</tr>
		
		<tr>
			<td align="left">Name: </td>
			<td><input type="text" name="atributeName" id="atributeNameId"></td>
		</tr>
		<tr>
			<td align="left">Type: </td>
			<td>
			<div id="comboTiposDatoEntidadId"></div>
			</td>
		</tr>
		<tr>
			<td align="left">Additional Info: </td>
			<td><input type="text" name="addInfo" id="addInfoId"></td>
		</tr>
		<tr>
			<td align="left">Drools Mapping: </td>
			<td><input type="text" name="droolsMapping" id="droolsMappingId"></td>
			<td align="left">(Ex: Socio.direccion.cp) </td>
		</tr>
	</table> 

	<input type="button" onclick="addNewAttribute()" id="newAttributeButtonId" value="Add new attribute"></input><br/><br><br>

	<table id="jqueryDataTable">
		<tbody>
		</tbody>
	</table>
	<br><br>
	<br><input type="button" onclick="getDataResult()" value="Guardar Entidad"></input>
	
	<h2>Administración Entidades.</h2>	
	<br>
	<table id="entitiesTableId">
		<tbody>
		</tbody>
	</table>
	<br>
	
	<input type="hidden" id="idEntidad_task" name="idEntidad_task"/>
