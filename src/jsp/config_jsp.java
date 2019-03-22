package jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import org.apache.jasper.runtime.*;
import aplicacion.ConfigBean;

public class config_jsp extends HttpJspBase {


  private static java.util.Vector _jspx_includes;

  public java.util.List getIncludes() {
    return _jspx_includes;
  }

  public void _jspService(HttpServletRequest request, HttpServletResponse response)
        throws java.io.IOException, ServletException {

    JspFactory _jspxFactory = null;
    javax.servlet.jsp.PageContext pageContext = null;
    HttpSession session = null;
    ServletContext application = null;
    ServletConfig config = null;
    JspWriter out = null;
    Object page = this;
    JspWriter _jspx_out = null;


    try {
      _jspxFactory = JspFactory.getDefaultFactory();
      response.setContentType("text/html; charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\n\n<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">\n<html>\n<head>\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n<title>Aplicacion Configuración</title>\n<link rel=\"stylesheet\" type=\"text/css\" href=\"/Aplicacion/css/style.css\">\n<script src=\"/ksf/js/hypas-api.js\" language=\"JavaScript\"></script>\n<script language=\"Javascript\" src=\"/Aplicacion/js/nav.js\"></script>\n</head>\n<body onload=\"HyPAS.App.fullScreen(false);\" bgcolor=\"#eeeeee\" style=\"margin-left: 0px; margin-top: 0px;\">\n<form action=\"appservlet.do\" method=\"post\">\n\t<div class=\"container\" style=\"height: 417px; width: 790px; background-color: #ffffff; margin-left: 0px; margin-top: 0px;\">\n\t\t<div style=\"display:inline; width:100px; text-align:left; font-size:12px; font-family:Albany;\">Usuario:</div>\n\t\t<input class=\"text\" type=\"text\" name=\"usuario\" value=\"");
                                                                                                                        out.print(ConfigBean.getInstance().getUserName());
      out.write("\" style=\"text-align:left; font-size:24px; font-family:Albany; height:38px; width:138px\"><br>\n\t\t<div style=\"display:inline; width:100px; text-align:left; font-size:12px; font-family:Albany;\">Contraseña:</div>\n\t\t<input class=\"text\" type=\"password\" name=\"password\" value=\"");
                              out.print(ConfigBean.getInstance().getPassword());
      out.write("\" style=\"text-align:left; font-size:24px; font-family:Albany; height:38px; width:138px\"><br>\n\t\t<div style=\"display:inline; width:100px; text-align:left; font-size:12px; font-family:Albany;\">Usar HTTPS:</div>\n\t\t<input type=\"checkbox\" name=\"https\" value=\"on\" ");
                              out.print(ConfigBean.getInstance().getUseHttps() ? "checked" : "" );
      out.write("><br>\n\t\t<input class=\"button\" type=\"button\" value=\"Volver\" onMouseDown=\"\" onMouseUp=\"inicio()\" style=\"height:35px; width:128px; font-size:16px; font-family:Albany; background-color:#D4D6D9\" onfocus=\"this.blur()\">\n\t\t<input class=\"button\" type=\"submit\" value=\"Guardar\" onMouseDown=\"\" onMouseUp=\"\" style=\"height:35px; width:128px; font-size:16px; font-family:Albany; background-color:#D4D6D9\" onfocus=\"this.blur()\">\n\t</div>\n</form>\n</body>\n</html>\n");
                                              } catch (Throwable t) {
      out = _jspx_out;
      if (out != null && out.getBufferSize() != 0)
        out.clearBuffer();
      if (pageContext != null) pageContext.handlePageException(t);
    } finally {
      if (_jspxFactory != null) _jspxFactory.releasePageContext(pageContext);
    }
  }
}
