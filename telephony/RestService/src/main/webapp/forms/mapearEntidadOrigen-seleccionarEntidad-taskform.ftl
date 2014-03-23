<!DOCTYPE html>
<link rel="stylesheet" type="text/css" href="http://ajax.aspnetcdn.com/ajax/jquery.dataTables/1.9.4/css/jquery.dataTables.css"/></head>
<script type="text/javascript">

var entitiesExistentes = null;
var opcionesParaCombo = [{"name":'Select',"id": -1}];
var oTable = null;


/*******************PARSEO PARA RETORNAR********************************/

	function asignarVariable(resultado){
		alert('El resultado ' + resultado + 'fue guardado correctamente');
		$('#idEntidad_task').val(resultado);		
	}

/***************************ONREADY*************************************/

	function crearTabla(json) {
	
	var jsonData = {"columns":[{"name":"name"},{"name":"type"},{"name":"additional info"},{"name":"drools mapping"}],
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
		
		ajaxRestJsonCall('http://localhost:8080/mp-backend-poc3/rest/mp/allEntities',null,comboEntidadesExistentes,'get');
		crearTabla();

	$("#comboEntidadExistenteId").on("change", ".llenarTabla", function(event) { 
	asignarVariable(this.value);
		
		if(this.value == -1){
			oTable.fnClearTable();
			$('#entityId').removeAttr('value');
			$('#entityNameId').removeAttr('value');
    			$('#newAttributeButtonId').attr('disabled', false);
    			$('#entityNameId').attr('disabled', false);


		}else{
    			$('#newAttributeButtonId').attr('disabled', true);
    			$('#entityNameId').attr('disabled', true);
			llenarListaEE(this.value);
		}
	});

	});

	function llenarListaEE(index){
		var valores = entitiesExistentes.lista[index];
		listaConValores(valores);
	}

	function listaConValores(valores){
		var datos = [];

		$("#entityNameId").val(valores.entityName);
		$("#entityId").val(valores.idEntity);
		for(var i in valores.atributes){
			var listaRegistro = [];
			listaRegistro.push(valores.atributes[i].name);
			listaRegistro.push(valores.atributes[i].type);
			listaRegistro.push(valores.atributes[i].aditionalInfo);
			datos.push(listaRegistro);
		}
		oTable.fnClearTable();
		oTable.fnAddData(datos);

	}

	function parsearNombreEntidades(json){
		if(json.lista.length === undefined){
			var objetoEnLista = json.lista;
			json.lista = [];
			json.lista.push(objetoEnLista);
		}
	
		for(var i in json.lista){
			var obj = {"name":json.lista[i].entityName,"id": json.lista[i].idEntity};
//			alert(JSON.stringify(obj));
			opcionesParaCombo.push(obj);
		}
//		alert(JSON.stringify(opcionesParaCombo));
		return opcionesParaCombo;
	}

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

	function comboEntidadesExistentes(json){
		entitiesExistentes = json;
		if(json != null){
			$("#comboEntidadExistenteId").append(createCombo("llenarTabla", parsearNombreEntidades(json), "entidadesExistentesId"));
		}
	}

/*****************************END***************************************/


</script>

	<h2>Configurar Entidad Motor Predictivo.</h2>
	
	 <div id="comboEntidadExistenteId">Entidades:</div><br/>	

	<table id="jqueryDataTable">
		<tbody>
		</tbody>
	</table>
	<br><br>
<!--	<br><input type="button" onclick="getDataResult()" value="Guardar Entidad"></input> -->
	
	
	<input type="hidden" id="idEntidad_task" name="idEntidad_task"/>