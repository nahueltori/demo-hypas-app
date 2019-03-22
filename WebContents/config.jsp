<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="aplicacion.ConfigBean"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Aplicacion Configuración</title>
<link rel="stylesheet" type="text/css" href="/Aplicacion/css/style.css">
<script src="/ksf/js/hypas-api.js" language="JavaScript"></script>
<script language="Javascript" src="/Aplicacion/js/nav.js"></script>
</head>
<body onload="HyPAS.App.fullScreen(false);" bgcolor="#eeeeee" style="margin-left: 0px; margin-top: 0px;">
<form action="appservlet.do" method="post">
	<div class="container" style="height: 417px; width: 790px; background-color: #ffffff; margin-left: 0px; margin-top: 0px;">
		<div style="display:inline; width:100px; text-align:left; font-size:12px; font-family:Albany;">Usuario:</div>
		<input class="text" type="text" name="usuario" value="<%=ConfigBean.getInstance().getUserName()%>" style="text-align:left; font-size:24px; font-family:Albany; height:38px; width:138px"><br>
		<div style="display:inline; width:100px; text-align:left; font-size:12px; font-family:Albany;">Contraseña:</div>
		<input class="text" type="password" name="password" value="<%=ConfigBean.getInstance().getPassword()%>" style="text-align:left; font-size:24px; font-family:Albany; height:38px; width:138px"><br>
		<div style="display:inline; width:100px; text-align:left; font-size:12px; font-family:Albany;">Usar HTTPS:</div>
		<input type="checkbox" name="https" value="on" <%=ConfigBean.getInstance().getUseHttps() ? "checked" : "" %>><br>
		<input class="button" type="button" value="Volver" onMouseDown="" onMouseUp="inicio()" style="height:35px; width:128px; font-size:16px; font-family:Albany; background-color:#D4D6D9" onfocus="this.blur()">
		<input class="button" type="submit" value="Guardar" onMouseDown="" onMouseUp="" style="height:35px; width:128px; font-size:16px; font-family:Albany; background-color:#D4D6D9" onfocus="this.blur()">
	</div>
</form>
</body>
</html>
