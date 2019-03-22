baseurl = "/Aplicacion/servlet/appservlet.do";

function configurar(){
	location.href = baseurl + "?flag=loadconfig";
}

function inicio(){
	location.href = baseurl;
}

function iniciarDigitalizacion(){
	location.href = baseurl + "?flag=iniciarbandeja";
}

function logout(){
	location.href = baseurl + "?flag=desconectar";
}
