package aplicacion;

import hypas.Escaneo;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class StateServlet extends HttpServlet {

	private final String LOGIN_BANDEJA = "login_bandeja";
	private final String UPLOAD_BANDEJA = "upload_bandeja";
	private final String ESCANEO = "escaneo";
	private static final long serialVersionUID = 1L;

	private Escaneo escaneo;
	private Bandeja bandeja;

	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		bandeja = (Bandeja) req.getSession().getAttribute("bandeja");
		escaneo = (Escaneo) req.getSession().getAttribute("escaneo");
		String elemento = req.getParameter("elemento").trim();
		String mensaje = "";
		if(elemento.equals(LOGIN_BANDEJA)){
			mensaje = bandeja.getLoginMessage();
		}
		else if (elemento.equals(UPLOAD_BANDEJA)){
			mensaje = bandeja.getUploadMessage();
		}
		else if (elemento.equals(ESCANEO)){
			mensaje = escaneo.getStatus();
		}
		
		resp.setContentType("text/plain");
		resp.getWriter().write(mensaje);
	}

	
}
