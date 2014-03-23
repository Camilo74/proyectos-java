<!DOCTYPE html>

<link rel="stylesheet" type="text/css" href="http://ajax.aspnetcdn.com/ajax/jquery.dataTables/1.9.4/css/jquery.dataTables.css"/></head>

<script>
var opcionesParaCombo = [];
var oTable = null;

	function addNewSource(){

		var nombre = $('#sourceNameId').val();
		var tipo =$('#comboTipoOrigenId').val();
		var url = $('#urlDSId').val();
		var query = $('#queryId').val();
		var username = $('#userNameId').val();
		var password = $('#passwordId').val();
		var tipoBd = $('#tipoBdId').val();

		if(nombre == ""){
			alert('Complete todos los valores del formulario para agregar un nuevo atributo');
		}else{
			oTable.fnAddData( [nombre,tipo,url,query,username,password,tipoBd,"<input class='approveButton' type='button' value='Delete'></input>"] );

			$('#sourceNameId').removeAttr('value');
			$('#urlDSId').removeAttr('value');
			$('#queryId').removeAttr('value');
			$('#userNameId').removeAttr('value');
			$('#passwordId').removeAttr('value'); 
		}	

	}	
	
	function getDataResult(){

		var data = oTable.fnGetData();
		
		if(data == ""){
			alert('No ha sido agregado ningun origen de datos aún.');
			return;
		}		

		jsonTable = data;//JSON.stringify(data);
		var atributes = {'sources' : []};

		for(var i in jsonTable){
			obj = {'sourceName': jsonTable[i][0],'type':jsonTable[i][1],'url': jsonTable[i][2],'query': jsonTable[i][3],'username':jsonTable[i][4],'password': jsonTable[i][5],'typeDb' : jsonTable[i][6], 'id': "1"}
//			alert(JSON.stringify(obj));
			atributes.sources.push(obj);
		}
		
		ajaxRestJsonCall('http://localhost:8080/mp-backend-poc3/rest/mp/selectedSources',JSON.stringify(atributes),asignarVariable,'post');
	}
	
	function asignarVariable(resultado){
		alert('El resultado ' + resultado + 'fue guardado correctamente');
		$('#idOrigen_task').val(resultado);		
	} 

	function crearTabla(json) {
	
	var jsonData = {"columns":[{"name":"name"},{"name":"type"},{"name":"url"},
	{ "name" : "query"},{ "name" : "username"},{ "name" : "password"},{ "name" : "tipoBd"},{ "name" : "delete"}],
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
		ajaxRestJsonCall('http://localhost:8080/mp-backend-poc3/rest/mp/dataSourceTypes',null,comboTipoDataSourceCallBack,'get');
		crearTabla();

	$("#jqueryDataTable").on("click", ".approveButton", function(event) { 
		var row = $(this).closest("tr").get(0);
		oTable.fnDeleteRow(oTable.fnGetPosition(row));
	 });

	$("#divComboTiposDataSourceId").on("change", ".tipoDataSource", function(event) { 
		if(this.value == 1){
			$('#divDataBaseSourceFormId').css('display', 'inline');
			$('#divWebServiceSourceFormId').css('display', 'none');
    			$('#addAtrrId').attr('disabled', false);
		}

		if(this.value == 2){
			$('#divWebServiceSourceFormId').css('display', 'inline');
			$('#divDataBaseSourceFormId').css('display', 'none');
    			$('#addAtrrId').attr('disabled', true);
			
		}
	});

	});


	function llenarListaEE(index){
		var valores = entitiesExistentes.lista[index];
		listaConValores(valores);
	}

	function listaConValores(valores){
		var datos = [];
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
//			alert(JSON.stringify(json.lista));
		for(var i in json.lista){
			var obj = {"name":json.lista[i].entityName,"id": i};
//			alert(JSON.stringify(obj));
			opcionesParaCombo.push(obj);
		}
//		alert(JSON.stringify(opcionesParaCombo));
		return opcionesParaCombo;
	}

	function createCombo(classForFunction, opciones, idCombo){
		var cadenaInicial;		
		if(idCombo == null){
			cadenaInicial = '<select class="' + classForFunction + '">';
		}else{
			cadenaInicial = '<select class="' + classForFunction + '" id="' + idCombo + '">';
		}
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


	function comboTipoDataSourceCallBack(json){
		if(json != null){
			$("#divComboTiposDataSourceId").append(createCombo("tipoDataSource", json.lista, "comboTipoOrigenId"));
		}
	}

function getDsType(){
	alert($('#comboTipoOrigenId').val());
}

/********************************************************************/
</script>
<h2>Configurar Origen de Datos.</h2>
	
<!--	 <div id="comboEntidadExistenteId">Entidades:</div><br/>  -->

	<h3>Datos Configuracion DS.</h3>

	<table border=0>

		<tr>
			<td align="left">DS Name: </td>
			<td><input type="text" name="sourceName" id="sourceNameId"></td>
		</tr>
		<tr>
			<td align="left">Type: </td>
			<td>
			<div id="divComboTiposDataSourceId"></div>
			</td>
		</tr>	
</table>	
<div id="divDataBaseSourceFormId">
<table border=0>
		<tr>
			<td align="left">Motor Db Name: </td>
			<td align="left" >
				<select name="tipoBd" id="tipoBdId">
					<option value="1">MySql</option>
				</select>
			</td>
		</tr>
		<tr>
			<td align="left">URL: </td>
			<td><input type="text" name="urlDS" id="urlDSId" value="jdbc:mysql://localhost:3306/mpi"></td>
		</tr>		
		<tr>
			<td align="left">Query: </td>
			<td><input type="text" name="query" id="queryId" value ="select * from Socios"></td>
		</tr>		
		<tr>
			<td align="left">UserName: </td>
			<td><input type="text" name="userName" id="userNameId" value="root"></td>
		</tr>		
		<tr>
			<td align="left">Password: </td>
			<td><input type="text" name="password" id="passwordId" value="password"></td>
		</tr>
</table>
</div>


<div id="divWebServiceSourceFormId"  style="display:none;"><br/>
<tr>
	<td align="left">IN CONSTRUCTION....</td>
</tr>
<br/></div>
<br/>
	<input type="button" value="Add new source" id="addAtrrId" onclick="addNewSource()"></input><br/><br><br>

	<table id="jqueryDataTable">
		<tbody>
		</tbody>
	</table>
	<br><br>
	<br><input value="Guardar DS Seleccionados" type="button" onclick="getDataResult()"></input>

	<input type="hidden" id="idOrigen_task" name="idOrigen_task"/>
