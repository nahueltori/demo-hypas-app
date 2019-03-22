<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Fin</title>
<link rel="stylesheet" type="text/css" href="/Aplicacion/css/style.css">
<script src="/ksf/js/hypas-api.js" language="JavaScript"></script>
<script language="Javascript" src="/Aplicacion/js/nav.js"></script>
</head>
<body onload="HyPAS.App.fullScreen(false); HyPAS.CloseButton.enable(true);" bgcolor="#eeeeee" style="margin-left: 0px; margin-top: 0px;">
	<div class="container" style="height: 417px; width: 790px; background-color: #ffffff; margin-left: 0px; margin-top: 0px;">
		<div style="display:inline; width: 463px; text-align:left; font-size:32px; font-family:Albany;">¿Desea escanear un nuevo archivo?</div>
	<br>
	<input class="button" type="button" value="Nueva digitalización" onMouseDown="" onMouseUp="iniciarDigitalizacion();" style="height:35px; width: 276px; font-size:16px; font-family:Albany; background-color:#D4D6D9" onfocus="this.blur()">
	<input class="button" type="button" value="Salir" onMouseDown="" onMouseUp="logout();" style="height:35px; width: 276px; font-size:16px; font-family:Albany; background-color:#D4D6D9" onfocus="this.blur()">
	</div>
</body>
</html>
