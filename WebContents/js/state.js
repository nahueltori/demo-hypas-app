baseurl = "/Aplicacion/servlet/appservlet.do";
LOGIN_OK = "Login OK en Digital Docs";
ESCANEO_OK = "Escaneo finalizado";
UPLOAD_OK = "Upload OK en Digital Docs";
REFRESH = 3000;

function checkStatuses(){
	if (window.XMLHttpRequest) {
		setTimeout(checkLogin,REFRESH);
	}
	else{
		document.getElementById("estadoLoginBandeja").innerHTML = "AJAX not supported";
	}
}

function checkLogin() {
	$.ajax({
		url : "stateservlet.do",
		data : {
			elemento : "login_bandeja"
		},
		cache: false,
		success : function(responseText) {
			if(responseText){
				$("#estadoLoginBandeja").text(responseText);
			}
			else{
				setTimeout(checkLogin,REFRESH);
			}
			if(responseText == LOGIN_OK){
				digitalizar();
			}
		}
	});
	
	/*
	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
	    if (this.readyState == 4 && this.status == 200) {
	       // Typical action to be performed when the document is ready:
	       document.getElementById("estadoLoginBandeja").innerHTML = xhttp.responseText;
	    }
		if(xhttp.responseText == LOGIN_OK){
			digitalizar();
		}
		else{
			setTimeout(checkLogin,REFRESH);
		}

	};
	xhttp.open("GET", "stateservlet.do?elemento=login_bandeja&t=" + Math.random(), true);
	xhttp.send();
	*/
}

function digitalizar(){
	$.ajax({
		url : "appservlet.do",
		data : {
			flag : "digitalizar"
		},
		cache: false,
		success : function(responseText) {
			setTimeout(checkEscaneo,REFRESH);
		}
	});
}

function checkEscaneo() {
	$.ajax({
		url : "stateservlet.do",
		data : {
			elemento : "escaneo"
		},
		cache: false,
		success : function(responseText) {
			if(responseText){
				$("#estadoEscaneo").text(responseText);
			}
			else{
				setTimeout(checkEscaneo,REFRESH);
			}
			if(responseText == ESCANEO_OK){
				subirBandeja();
			}
		}
	});
}

function subirBandeja(){
	$.ajax({
		url : "appservlet.do",
		data : {
			flag : "subirarchivo"
		},
		cache: false,
		success : function(responseText) {
			setTimeout(checkUpload,REFRESH);
		}
	});
}

function checkUpload() {
	$.ajax({
		url : "stateservlet.do",
		data : {
			elemento : "upload_bandeja"
		},
		cache: false,
		success : function(responseText) {
			if(responseText){
				$("#estadoUploadBandeja").text(responseText);
			}
			else{
				setTimeout(checkUpload,REFRESH);
			}
			if(responseText == UPLOAD_OK){
				terminar();
			}
		}
	});
}

function terminar(){
	location.href = baseurl + "?flag=terminar";
}

