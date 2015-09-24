function onLoad(){
}

function cambiarCategoria(vista){
	cambiarEntradaDeCategoria(vista);
	cambiarBotonCategoria(vista);
}

function cambiarEntradaDeCategoria(vista){
	var id = "selectorCategoria";
	var element = document.getElementById(id);
	//--------------------------------------------------
	if(vista){
		element.outerHTML = "<input id='selectorCategoria' type='text' value='sss'/>"		
	}else{
		
	}
}

function cambiarBotonCategoria(vista){
	var id = "botonAccionCategoria";
	var element = document.getElementById(id);
	//--------------------------------------------------
	if(vista){
		element.outerHTML = "<a id='botonAccionCategoria' href='#' onclick='cambiarCategoria(false)'><img src='/img/market/confirmar.png' width='32' height='32'></a>"
	}else{
		element.outerHTML = "<a id='botonAccionCategoria' href='#' onclick='cambiarCategoria(true)'><img src='/img/market/add.png' width='32' height='32'></a>"
	}
}