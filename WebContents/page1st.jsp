<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Aplicacion</title>
<link rel="stylesheet" type="text/css" href="/Aplicacion/css/style.css">
<script src="/ksf/js/hypas-api.js" language="JavaScript"></script>
<script language="Javascript" src="/Aplicacion/js/nav.js"></script>
</head>
<body onload="HyPAS.App.fullScreen(false);" style="margin-left: 0px; margin-top: 0px;">
<div class="container" style="height: 417px; width: 790px; margin-left: 0px; margin-top: 0px;">
	<img alt="Digital Docs" src="/Aplicacion/img/aplicacion.png"/>
	<br>
	<input class="button" type="button" value="Iniciar digitalización" onMouseDown="" onMouseUp="iniciarDigitalizacion();" style="height:35px; width: 276px; font-size:16px; font-family:Albany; background-color:#D4D6D9" onfocus="this.blur()">
	<br>
	<input class="button" type="button" value="Configurar Aplicacion" onMouseDown="" onMouseUp="configurar();" style="height:35px; width: 276px; font-size:16px; font-family:Albany; background-color:#D4D6D9" onfocus="this.blur()">
	<br>
	<input class="button" type="button" value="Salir" onMouseDown="" onMouseUp="HyPAS.App.end();" style="height:35px; width: 276px; font-size:16px; font-family:Albany; background-color:#D4D6D9" onfocus="this.blur()">
</div>

</body>
</html>
