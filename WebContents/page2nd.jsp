<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Digitalización</title>
<link rel="stylesheet" type="text/css" href="/Aplicacion/css/style.css">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script language="Javascript" src="/Aplicacion/js/jquery-1.1.js"></script>
<script language="Javascript" src="/Aplicacion/js/state.js"></script>
<script language="Javascript" src="/Aplicacion/js/nav.js"></script>
<script src="/ksf/js/hypas-api.js" language="JavaScript"></script>
</head>
<body onload="HyPAS.App.fullScreen(false); checkStatuses();" style="margin-left: 0px; margin-top: 0px;">
<div class="container" style="height: 417px; width: 790px; margin-left: 0px; margin-top: 0px;">
	<div style="display:inline; width: 528px; text-align:left; font-size:24px; font-family:Albany;">
	Ingresando en Digital Docs
	</div>
	<br>
	<div id="estadoLoginBandeja" style="display:inline; text-align:left; font-size:24px; font-family:Albany;">
	<!-- Estado Login en Digital Docs -->
	</div>
	<br>
	<div id="estadoEscaneo" style="display:inline; width: 528px; text-align:left; font-size:24px; font-family:Albany;">
	<!-- Estado del Escaneo -->
	</div>
	<br>
	<div id="estadoUploadBandeja" style="display:inline; width: 528px; text-align:left; font-size:24px; font-family:Albany;">
	<!-- Estado Upload en Digital Docs -->
	</div>
	<br>
	<input class="button" type="button" value="Volver" onMouseDown="" onMouseUp="inicio();" style="height:35px; width:128px; font-size:16px; font-family:Albany; background-color:#D4D6D9" onfocus="this.blur()">
</div>
</body>
</html>
