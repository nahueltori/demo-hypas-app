package jsp.urlmap;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.http.HttpContext;
import org.osgi.service.http.HttpService;

public class MapService {
    public void addUrl(BundleContext context) {
        try {
            HttpService http;
            ServiceReference ref = context.getServiceReference("org.osgi.service.http.HttpService");
            http = (HttpService)context.getService(ref);
            HttpContext ctx = http.createDefaultHttpContext();
            http.registerResources("/Aplicacion","WebContents", ctx);
            http.registerServlet("/Aplicacion/servlet/stateservlet.do",new aplicacion.StateServlet(), null, ctx);
            http.registerServlet("/Aplicacion/servlet/appservlet.do",new aplicacion.AppServlet(), null, ctx);
            http.registerServlet("/Aplicacion/config.jsp",new jsp.config_jsp(), null, ctx);
            http.registerServlet("/Aplicacion/endPage.jsp",new jsp.endPage_jsp(), null, ctx);
            http.registerServlet("/Aplicacion/page1st.jsp",new jsp.page1st_jsp(), null, ctx);
            http.registerServlet("/Aplicacion/page2nd.jsp",new jsp.page2nd_jsp(), null, ctx);
        } catch (Exception e) {
        }
    }
    public void removeUrl(BundleContext context){
        try {
            HttpService http;
            ServiceReference ref = context.getServiceReference("org.osgi.service.http.HttpService");
            http = (HttpService)context.getService(ref);
            http.unregister("/Aplicacion");
            http.unregister("/Aplicacion/servlet/stateservlet.do");
            http.unregister("/Aplicacion/servlet/appservlet.do");
            http.unregister("/Aplicacion/config.jsp");
            http.unregister("/Aplicacion/endPage.jsp");
            http.unregister("/Aplicacion/page1st.jsp");
            http.unregister("/Aplicacion/page2nd.jsp");
        } catch (Exception e) {
        }
    }
}
