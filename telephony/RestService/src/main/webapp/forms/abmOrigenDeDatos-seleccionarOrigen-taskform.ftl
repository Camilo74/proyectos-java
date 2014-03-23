<link rel="stylesheet" type="text/css" href="http://ajax.aspnetcdn.com/ajax/jquery.dataTables/1.9.4/css/jquery.dataTables.css"/></head>

<script>
var opcionesParaCombo = [];
var oTable = null;
var id;

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
			obj = {'sources' : [{'sourceName': nombre,'type':tipo,'url': url,'query': query,'username':username,'password': password,'typeDb' : tipoBd}]};
			ajaxRestJsonCall('http://localhost:8080/mp-backend-poc3/rest/mp/newSourcesConf',JSON.stringify(obj),mostrarMensajeIdSource,'post');
			oTable.fnAddData( [nombre,tipo,url,query,username,password,tipoBd,"<input class='approveButton' type='button' value='Delete' id='"+id+"'></input>"] );
			$('#sourceNameId').removeAttr('value');
			$('#urlDSId').removeAttr('value');
			$('#queryId').removeAttr('value');
			$('#userNameId').removeAttr('value');
			$('#passwordId').removeAttr('value'); 
		}
	}
	
	function mostrarMensajeIdSource(resultado){
		alert("Se guardo el source con el id: " + resultado);
		id = resultado;
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
		ajaxRestJsonCall('http://localhost:8080/mp-backend-poc3/rest/mp/allSourcesConf',null,agregarSourcesExistentes,'get');
		$("#jqueryDataTable").on("click", ".approveButton", function(event) { 
			var row = $(this).closest("tr").get(0);
			var input = row.getElementsByTagName("input");
			deleteSource(input[0].getAttribute("id"));			
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
	//
	// 		$('td', oTable.fnGetNodes()).editable( 
	// 				function( sValue, y ) {
	// 					var aPos = oTable.fnGetPosition( this );
	// 					oTable.fnUpdate( sValue, aPos[0], aPos[1] );
	// 				}, 
	// 				{
	// 		"callback": function( sValue, y ) {
	// 			var aPos = oTable.fnGetPosition( this );
	// 			oTable.fnUpdate( sValue, aPos[0], aPos[1] );
	// 		},
	// 		"submitdata": function ( value, settings ) {
	// 			return {
	// 				"row_id": this.parentNode.getAttribute('id'),
	// 				"column": oTable.fnGetPosition( this )[2]
	// 			};
	// 		},
	// 		"height": "14px"
	// 	} );
	
	//	
	});	
	
	
	
	
	function deleteSource(id){
		alert("Se eliminara el source con id: " + id);
		ajaxRestJsonCall('http://localhost:8080/mp-backend-poc3/rest/mp/deleteSourceConf?sourceDeleteId=' + id,null,null,'post');
	}
	
	
	
	
	function agregarSourcesExistentes(json){
		if(json != null){
			listaConValores(json.lista);
		}
	}
	
	function listaConValores(valores){
		var datos = [];		
		var listaRegistro = [];
		if(valores.length === undefined){
			listaRegistro.push(valores.sources.sourceName);
			listaRegistro.push(valores.sources.type);
			listaRegistro.push(valores.sources.url);
			listaRegistro.push(valores.sources.query);
			listaRegistro.push(valores.sources.username);
			listaRegistro.push(valores.sources.password);
			listaRegistro.push(valores.sources.typeDb);
			listaRegistro.push("<input class='approveButton' type='button' value='Delete' id='"+valores.sources.id+"'></input>");
			datos.push(listaRegistro);
		} else {
		for(var i in valores){
			listaRegistro = [];
			listaRegistro.push(valores[i].sources.sourceName);
			listaRegistro.push(valores[i].sources.type);
			listaRegistro.push(valores[i].sources.url);
			listaRegistro.push(valores[i].sources.query);
			listaRegistro.push(valores[i].sources.username);
			listaRegistro.push(valores[i].sources.password);
			listaRegistro.push(valores[i].sources.typeDb);
			listaRegistro.push("<input class='approveButton' type='button' value='Delete' id='"+valores[i].sources.id+"'></input>");
			datos.push(listaRegistro);
		}
		}
		oTable.fnClearTable();
		oTable.fnAddData(datos);
	}
	

	function parsearNombreEntidades(json){
		for(var i in json.lista){
			var obj = {"name":json.lista[i].entityName,"id": i};
			opcionesParaCombo.push(obj);
		}
		return opcionesParaCombo;
	}

	function parsearNombreEntidades(json){
		if(json.lista.length === undefined){
			var objetoEnLista = json.lista;
			json.lista = [];
			json.lista.push(objetoEnLista);
		}
	
		for(var i in json.lista){
			var obj = {"name":json.lista[i].entityName,"id": json.lista[i].idEntity};
			opcionesParaCombo.push(obj);
		}
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
		}
		return (cadenaInicial + cadenaOpciones + cadenaFinal);
	}


/********************************************************************/

	function ajaxRestJsonCall(url,datos,callBack,method){
		$.ajax({
		   	type: method,
			url: url,
			async: false,
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