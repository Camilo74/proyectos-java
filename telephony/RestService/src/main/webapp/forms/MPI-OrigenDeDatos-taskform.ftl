<h1>Configurar origen de Datos</h1>
<h3>Descripcion: ${reglaid_task!}</h3>
<form name="OrigenDeDatosForm" enctype="multipart/form-data">
<table>
<tr>
<td>Tipo conexion</td>
<td>
<select name="tipo_conexion_task">
 <option value="bbdd">Base de Datos</option>
 <option value="ws">WebServices</option>
</select>
</td>
</tr>
<tr>
<td>URL</td>
<td><input type="text" name="url_task"/></td>
</tr>
<tr>
<td>Usuario</td>
<td><input type="text" name="usuario_task"/></td>
</tr>
<tr>
<td>Clave</td>
<td><input type="text" name="clave_task"/></td>
</tr>
<tr>
<td>Query</td>
<td><input type="text" name="query_task"/></td>
</tr>
  <input id="datos_conexion_task" type="text" name="datos_conexion_task" />
</table>
</form>	
<button onclick="getTableDataExample()">AjaxExampleCall</button>
<script>
function getTableDataExample(){
		alert('hago la llamada a ajax');
		$.ajax(
				{
					url : 'http://10.200.106.152:8080/mp-backend/rest/mp/querydata',
					type: 'post',
					data : 'url=jdbc%3Amysql%3A%2F%2F10.100.7.177%3A3306%2Fmpi&query=select%20*%20from%20Peliculas&username=root&password=kGW8Nhq2'
,
					success:function(data)
					{
						alert(data);
					},
					error: function(e) 
					{
						alert("error");
					}
				});
		
		}
</script>
