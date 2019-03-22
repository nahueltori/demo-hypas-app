package jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import org.apache.jasper.runtime.*;

public class page2nd_jsp extends HttpJspBase {


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

      out.write("\n<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">\n<html>\n<head>\n<title>Digitalización</title>\n<link rel=\"stylesheet\" type=\"text/css\" href=\"/Aplicacion/css/style.css\">\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n<script language=\"Javascript\" src=\"/Aplicacion/js/jquery-1.1.js\"></script>\n<script language=\"Javascript\" src=\"/Aplicacion/js/state.js\"></script>\n<script language=\"Javascript\" src=\"/Aplicacion/js/nav.js\"></script>\n<script src=\"/ksf/js/hypas-api.js\" language=\"JavaScript\"></script>\n</head>\n<body onload=\"HyPAS.App.fullScreen(false); checkStatuses();\" style=\"margin-left: 0px; margin-top: 0px;\">\n<div class=\"container\" style=\"height: 417px; width: 790px; margin-left: 0px; margin-top: 0px;\">\n\t<div style=\"display:inline; width: 528px; text-align:left; font-size:24px; font-family:Albany;\">\n\tIngresando en Digital Docs\n\t</div>\n\t<br>\n\t<div id=\"estadoLoginBandeja\" style=\"display:inline; text-align:left; font-size:24px; font-family:Albany;\">\n");
      out.write("\t<!-- Estado Login en Digital Docs -->\n\t</div>\n\t<br>\n\t<div id=\"estadoEscaneo\" style=\"display:inline; width: 528px; text-align:left; font-size:24px; font-family:Albany;\">\n\t<!-- Estado del Escaneo -->\n\t</div>\n\t<br>\n\t<div id=\"estadoUploadBandeja\" style=\"display:inline; width: 528px; text-align:left; font-size:24px; font-family:Albany;\">\n\t<!-- Estado Upload en Digital Docs -->\n\t</div>\n\t<br>\n\t<input class=\"button\" type=\"button\" value=\"Volver\" onMouseDown=\"\" onMouseUp=\"inicio();\" style=\"height:35px; width:128px; font-size:16px; font-family:Albany; background-color:#D4D6D9\" onfocus=\"this.blur()\">\n</div>\n</body>\n</html>\n");
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
