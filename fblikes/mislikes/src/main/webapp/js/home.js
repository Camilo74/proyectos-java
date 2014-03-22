FB.init({
	appId : '576063092479459',
	status : true, // comprobar estado de login
	cookie : true, // habilitar cookies para permitir al servidor acceder a la sesión
	xfbml : true, // renderizar código XFBML
	channelURL : 'http://mislikes-q3s.rhcloud.com/channel.html', // fichero channel.html
	oauth : true // habilita OAuth 2.0
});

FB.login(function(response) {
	if (response.authResponse) {
		console.log('Welcome!  Fetching your information.... ');
		FB.api('/me', function(response) {
			alert("bien " + response.name);
			console.log('Good to see you, ' + response.name + '.');
		});
	} else {
		alert("mal " + response.name);
		console.log('User cancelled login or did not fully authorize.');
	}
});

/*
FB.Event.subscribe('edge.create', function(response) {
	alert('You liked the URL: ' + response);
});
 */
