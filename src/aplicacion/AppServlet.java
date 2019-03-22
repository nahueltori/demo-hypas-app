package aplicacion;

import hypas.Escaneo;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AppServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	boolean statusLogin = true;
	boolean escaneoTerminado = false;
	Escaneo escaneo = Escaneo.getInstance();
	String statusEscaneo;
	Bandeja bandejaDDocs = Bandeja.getInstance();
	ConfigBean configuracion = ConfigBean.getInstance();
	String root = "/" + HyPASActivator.PLUGIN_ID; // Get root path from HyPASActivator.java

	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		ServletContext sc = getServletContext();
		String attrArrayflag = req.getParameter("flag"); // Get flag from request.
		
		//Cuando se carga por primera vez, accede a page1st.jsp
		if (attrArrayflag == null) {
			// default
			sc.getRequestDispatcher(root+"/page1st.jsp").forward(req, resp);
			System.out.println("Loading");
		}
		else {
			
			//Cuando se pide la configuración, carga config.jsp
			if(attrArrayflag.equals("loadconfig")) {
				System.out.println("* Cargando página de configuración");
				sc.getRequestDispatcher(root+"/config.jsp").forward(req, resp);
			}
			
			if(attrArrayflag.equals("iniciarbandeja")) {
				System.out.println("* Login en Aplicacion");
				statusLogin = bandejaDDocs.login();
				req.getSession().setAttribute("bandeja", bandejaDDocs);
				sc.getRequestDispatcher(root+"/page2nd.jsp").forward(req, resp);
			}
			
			if(attrArrayflag.equals("digitalizar")) {
				System.out.println("* Escaneo del documento");
				statusEscaneo = escaneo.comenzarEscaneo();
				escaneoTerminado = true;
				req.getSession().setAttribute("escaneo", escaneo);
			}

			if(attrArrayflag.equals("subirarchivo")) {
				System.out.println("* Upload del archivo");
				bandejaDDocs.upload(escaneo.getArchivoEscaneado(), escaneo.getArchivoEscaneadoFileName());
				req.getSession().setAttribute("bandeja", bandejaDDocs);
			}

			if(attrArrayflag.equals("terminar")) {
				sc.getRequestDispatcher(root+"/endPage.jsp").forward(req, resp);
			}

			if(attrArrayflag.equals("desconectar")) {
				System.out.println("* Desconectar Bandeja");
				bandejaDDocs.logout();
				sc.getRequestDispatcher(root+"/page1st.jsp").forward(req, resp);
			}
		}
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		System.out.println("Guardando configuración");
		ServletContext sc = getServletContext();
		
		configuracion.setUserName(req.getParameter("usuario"));
		configuracion.setPassword(req.getParameter("password"));
		String https = req.getParameter("https");
		if((https != null) && (https.equals("on"))){
			configuracion.setUseHttps(true);
		}
		else{
			configuracion.setUseHttps(false);
		}
		sc.getRequestDispatcher(root+"/page1st.jsp").forward(req, resp);
	}

}