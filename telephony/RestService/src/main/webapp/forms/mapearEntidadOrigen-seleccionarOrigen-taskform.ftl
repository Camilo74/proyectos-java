<!DOCTYPE html>

<link rel="stylesheet" type="text/css" href="http://ajax.aspnetcdn.com/ajax/jquery.dataTables/1.9.4/css/jquery.dataTables.css"/></head>

<script>

var origenesExistentes = null;
var opcionesParaCombo = [{"name":'Select',"id": -1}];
var oTable = null;
var idOrigenes = [];


/*******************PARSEO PARA RETORNAR********************************/

	function asignarOrigen(resultado){
		idOrigenes.push(resultado);	
	}

/***************************ONREADY*************************************/

	function crearTabla() {
	
	var jsonData = {"columns":[{"name":"name"},{"name":"type"},{"name":"url"},
		                       	{ "name" : "query"},{ "name" : "username"},{ "name" : "password"},{ "name" : "tipoBd"}],
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
		ajaxRestJsonCall('http://localhost:8080/mp-backend-poc3/rest/mp/allSourcesConf',null,comboEntidadesExistentes,'get');
		crearTabla();

	$("#comboOrigenesExistenteId").on("change", ".llenarTabla", function(event) { 
		asignarOrigen(this.value);
		if(this.value == -1){
			oTable.fnClearTable();
		}else{
			llenarListaEE(this.value);
		}
	});

	});

	function llenarListaEE(id){	
		var valores = null;
		if(origenesExistentes.lista.length === undefined){
			valores = origenesExistentes.lista;
		}else {
			for(var i in origenesExistentes.lista){
				if(origenesExistentes.lista[i].sources.id==id){
					valores = origenesExistentes.lista[i];
				}
			}
		}
		listaConValores(valores);
	}

	function listaConValores(valores){
		var listaRegistro = [];
		listaRegistro.push(valores.sources.sourceName);
		listaRegistro.push(valores.sources.type);
		listaRegistro.push(valores.sources.url);
		listaRegistro.push(valores.sources.query);
		listaRegistro.push(valores.sources.username);
		listaRegistro.push(valores.sources.password);
		listaRegistro.push(valores.sources.typeDb);
		oTable.fnAddData(listaRegistro);

	}

	function parsearNombreOrigen(valores){
		if(valores.length === undefined){

			var obj = {"name":valores.sources.sourceName,"id": valores.sources.id};
			opcionesParaCombo.push(obj);
		} else {
		for(var i in valores){
			var obj = {"name":valores[i].sources.sourceName,"id": valores[i].sources.id};
			opcionesParaCombo.push(obj);
		}
		}

		return opcionesParaCombo;
	}

	function createCombo(classForFunction, opciones, idCombo){

		var cadenaInicial = '<select class="' + classForFunction + '" id="' + idCombo + '">';
		var cadenaFinal = '</select>';
		var cadenaOpciones = '';		
		for(var i in opciones){
			cadenaOpciones = cadenaOpciones + '<option value="'+ opciones[i].id + '">' + opciones[i].name + '</option>';
		}
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

	function comboEntidadesExistentes(json){
		origenesExistentes = json;
		if(json != null){
			$("#comboOrigenesExistenteId").append(createCombo("llenarTabla", parsearNombreOrigen(json.lista), "entidadesExistentesId"));
		}
	}

/*****************************END***************************************/


	function getDataResult(){


		var data = oTable.fnGetData();
		
		if(data == ""){
			alert('No ha sido agregado ningun origen de datos aún.');
			return;
		}		

		jsonTable = data;//JSON.stringify(data);
		var atributes = {'sources' : []};

		for(var i in jsonTable){
			obj = {'sourceName': jsonTable[i][0],'type':jsonTable[i][1],'url': jsonTable[i][2],'query': jsonTable[i][3],'username':jsonTable[i][4],'password': jsonTable[i][5],'typeDb' : jsonTable[i][6], 'id': idOrigenes[i]}
//			alert(JSON.stringify(obj));
			atributes.sources.push(obj);
		}



			ajaxRestJsonCall('http://localhost:8080/mp-backend-poc3/rest/mp/selectedSources',JSON.stringify(atributes),asignarVariable,'post');
	}

	function asignarVariable(result){
		alert('El resultado ' + result + 'fue guardado correctamente');
		$('#idOrigen_task').val(result);		
	} 






</script>

	<h2>Configurar Origen de Datos Motor Predictivo.</h2>
	
	 <div id="comboOrigenesExistenteId">Sources:</div><br/>	

	<table id="jqueryDataTable">
		<tbody>
		</tbody>
	</table>
	<br><br>
	<br><input value="Guardar DS Seleccionados" type="button" onclick="getDataResult()"></input>
	<input type="hidden" id="idOrigen_task" name="idOrigen_task" />
