package aplicacion;

import hypas.Escaneo;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.net.ssl.SSLContext;

import java.security.cert.X509Certificate;

import jp.co.kyoceramita.log.Logger;

import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.entity.ContentType;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.Header;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;


public class Bandeja implements Serializable {

	private final String LOGIN_RESULT = "LoginResult";
	public final String MENSAJE_LOGIN_OK = "Login OK en Digital Docs";
	public final String MENSAJE_REINTENTE = "Por favor vuelva a intentar la operación.";
	public final String MENSAJE_USUARIO = "Chequee su usuario y password de Digital Docs.";
	public final String MENSAJE_TECNICO = "Error accediendo a Digital Docs.";
	public final String MENSAJE_UPLOAD_OK = "Upload OK en Digital Docs";
	public final String MENSAJE_UPLOAD_ERROR = "Upload con error en Digital Docs";
	public final String MENSAJE_CONEXION = "Revise la conexión con el servicio";

	private static final long serialVersionUID = 1L;
	private static Bandeja instance;
	private final String LOGIN_SERVICE = "/Login";
	private final String GET_CURRENT_USER_SERVICE = "/GetCurrentUser";
	private final String UPLOAD_SERVICE = "/UploadFileWithNameToBin";
	private final String LOGOUT_SERVICE = "/Disconnect";
	private final String CLIENT_REV = "clientRev";
	private final String CLIENT_REV_VALUE = "3000";
	private final String LANG = "lang";
	private final String LANG_VALUE = "es";
	private final String STATION = "station";
	private final String STATION_VALUE = "testclient";
	private final String LOGIN_NAME = "loginName";
	private final String PASSWORD = "password";
	private final String SESSION_ID = "sessionID";
	private final String GETCURRENTUSERRESULT = "GetCurrentUserResult";
	private final String ID = "ID";
	
	private String loginResult;
	private String loginMessage;
	private String uploadMessage;
	private boolean statusBandejaOk;
	private Logger logger;
	
	ConfigBean configuracion;
	Escaneo escaneo;
	
	private Bandeja(){
		configuracion = ConfigBean.getInstance();
		escaneo = Escaneo.getInstance();
		logger = HyPASActivator.getDefault().getLogger();
		loginResult = "";
		loginMessage = "";
		statusBandejaOk = false;
	}
	
	public static Bandeja getInstance(){
		if(instance == null){
			return new Bandeja();
		}
		else{
			return instance;
		}
	}
	
	public boolean login(){
		if(!statusBandejaOk){
			LoginThread login = new LoginThread(logger);
			login.run();
		}
		return statusBandejaOk;
	}
	
	public String getLoginMessage(){
		return loginMessage;
	}
	
	public String getUploadMessage(){
		return uploadMessage;
	}
	
	public boolean upload(ByteArrayOutputStream baos, String filename){
		uploadMessage = "";
		if(uploadMessage.equals("")){
			UploadThread upload = new UploadThread(baos, filename);
			upload.run();
		}
		return statusBandejaOk;
	}
	
	public void logout(){
		HttpClient coneccion = null;
		//Armo datos a enviar en el body
		JSONObject logoutdata = new JSONObject();
		try {
			logoutdata.put(SESSION_ID, loginResult);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		String endpoint = configuracion.getEndpoint() + LOGOUT_SERVICE;
		HttpPost httpPost = null;
		//Ejecuto el request
		try {
			coneccion = getClient();
			httpPost = new HttpPost(endpoint);
			StringEntity entity = new StringEntity(logoutdata.toString());
			httpPost.setEntity(entity);
			httpPost.setHeader("Accept", "application/json");
			httpPost.setHeader("Content-Type", "application/json");
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Request a enviar: " + coneccion.toString());
		System.out.println("Datos: " + logoutdata.toString());
		
		//Recibo el response
		int status = 0;
		//Recibo el response
        String content = "";
        String message = "";
		try {
			HttpResponse response = coneccion.execute(httpPost);				
			status = response.getStatusLine().getStatusCode();
			if (message.equals("")){
				message = response.getStatusLine().getReasonPhrase();
				HttpEntity entity = response.getEntity();
				if(entity != null) {
					content = EntityUtils.toString(entity);
				}
			}
			System.out.println("Status: " + status);
			System.out.println("Mensaje: " + message);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			httpPost.releaseConnection();
		}
		System.out.println("Respuesta: " + content);

		//Evalúo el status de la respuesta
		switch(status){
		
		case HttpURLConnection.HTTP_OK:
		case HttpURLConnection.HTTP_ACCEPTED:
			statusBandejaOk = true;
			break;
			
		case HttpURLConnection.HTTP_INTERNAL_ERROR:
		case HttpURLConnection.HTTP_BAD_GATEWAY:
		case HttpURLConnection.HTTP_GATEWAY_TIMEOUT:
		case HttpURLConnection.HTTP_CLIENT_TIMEOUT:
		case HttpURLConnection.HTTP_UNAVAILABLE:
			break;
			
		case HttpURLConnection.HTTP_UNAUTHORIZED:
			break;

		default:
			break;
		}
	}
	
	private class LoginThread extends Thread {
    	Logger logger;
    	
    	LoginThread(Logger logger){
    		this.logger = logger;
    	}
    	
		public void run(){
			int status = 0;
			String message = "";
			HttpClient coneccion = null;
			//Armo datos a enviar en el body
			JSONObject logindata = new JSONObject();
			try {
				logindata.put(CLIENT_REV,CLIENT_REV_VALUE);
				logindata.put(LANG,LANG_VALUE);
				logindata.put(STATION, STATION_VALUE);
				logindata.put(LOGIN_NAME, configuracion.getUserName());
				logindata.put(PASSWORD, configuracion.getPassword());
			} catch (Exception e1) {
				message = "E1 " + e1.toString();
				logger.error("Error generando Json de Login. " + message);
			}
			
			String endpoint = configuracion.getEndpoint() + LOGIN_SERVICE;
			HttpPost httpPost = null;
			//Ejecuto el request
			try {
				coneccion = getClient();
				httpPost = new HttpPost(endpoint);
				StringEntity entity = new StringEntity(logindata.toString());
				httpPost.setEntity(entity);
				httpPost.setHeader("Accept", "application/json");
				httpPost.setHeader("Content-Type", "application/json");
			} catch (Exception e) {
				message = "E2 " + e.toString();
				logger.error("Error abriendo conexión de Login. " + message);
				e.printStackTrace();
			}
			System.out.println("Request a enviar: " + coneccion.toString());
			System.out.println("Datos: " + logindata.toString());
			
			//Recibo el response
	        String content = "";
			try {
				HttpResponse response = coneccion.execute(httpPost);				
				status = response.getStatusLine().getStatusCode();
				if (message.equals("")){
					message = response.getStatusLine().getReasonPhrase();
					HttpEntity entity = response.getEntity();
					if(entity != null) {
						content = EntityUtils.toString(entity);
					}
				}
				System.out.println("Status: " + status);
				System.out.println("Mensaje: " + message);
			} catch (IOException e) {
				message = "E3 " + e.toString();
				logger.error("Error leyendo status de Login. " + message);
				e.printStackTrace();
			} finally {
				httpPost.releaseConnection();
			}
			System.out.println("Respuesta: " + content);
 
			//Evalúo el status de la respuesta
			switch(status){
			
			case HttpURLConnection.HTTP_OK:
			case HttpURLConnection.HTTP_ACCEPTED:
				JSONObject respuesta;
				try {
					respuesta = new JSONObject(content);
					loginResult = respuesta.getString(LOGIN_RESULT);
					obtenerUsuario();
				} catch (Exception e) {
					message = "E4 " + e.toString();
					logger.error("Error leyendo respuesta Json de Login. " + message);
					e.printStackTrace();
				}
				break;
				
			case HttpURLConnection.HTTP_INTERNAL_ERROR:
			case HttpURLConnection.HTTP_BAD_GATEWAY:
			case HttpURLConnection.HTTP_GATEWAY_TIMEOUT:
			case HttpURLConnection.HTTP_CLIENT_TIMEOUT:
			case HttpURLConnection.HTTP_UNAVAILABLE:
				loginMessage = MENSAJE_REINTENTE;
				break;
				
			case HttpURLConnection.HTTP_UNAUTHORIZED:
				loginMessage = MENSAJE_USUARIO;
				break;

			default:
				loginMessage = MENSAJE_TECNICO + "Status: " + status + ". Mensaje: " + message;
			}
		}
		
		private void obtenerUsuario(){
			int status = 0;
			String message = "";
			HttpClient coneccion = null;
			//Armo datos a enviar en el body
			JSONObject userData = new JSONObject();
			try {
				userData.put(SESSION_ID, loginResult);
			} catch (Exception e1) {
				message = "G1 " + e1.toString();
				logger.error("Error generando Json de User Data. " + message);
			}
			
			String endpoint = configuracion.getEndpoint() + GET_CURRENT_USER_SERVICE;
			HttpPost httpPost = null;
			//Ejecuto el request
			try {
				coneccion = getClient();
				httpPost = new HttpPost(endpoint);
				StringEntity entity = new StringEntity(userData.toString());
				httpPost.setEntity(entity);
				httpPost.setHeader("Accept", "application/json");
				httpPost.setHeader("Content-Type", "application/json");
			} catch (Exception e) {
				message = "G2 " + e.toString();
				logger.error("Error abriendo conexión de User Data. " + message);
				e.printStackTrace();
			}
			System.out.println("Request a enviar: " + coneccion.toString());
			System.out.println("Datos: " + userData.toString());
			
			//Recibo el response
	        String content = "";
			try {
				HttpResponse response = coneccion.execute(httpPost);				
				status = response.getStatusLine().getStatusCode();
				if (message.equals("")){
					message = response.getStatusLine().getReasonPhrase();
					HttpEntity entity = response.getEntity();
					if(entity != null) {
						content = EntityUtils.toString(entity);
					}
				}
			} catch (IOException e) {
				message = "G3 " + e.toString();
				logger.error("Error leyendo status de User Data. " + message);
				e.printStackTrace();
			} finally {
				httpPost.releaseConnection();
			}
			System.out.println("Respuesta: " + content);

			//Evalúo el status de la respuesta
			switch(status){
			
			case HttpURLConnection.HTTP_OK:
			case HttpURLConnection.HTTP_ACCEPTED:
				JSONObject respuesta;
				try {
					respuesta = new JSONObject(content);
					JSONObject resultObj = respuesta.getJSONObject(GETCURRENTUSERRESULT);
					String userId = resultObj.getString(ID);
					configuracion.setUserId(userId);
				} catch (Exception e) {
					message = "G4 " + e.toString();
					logger.error("Error leyendo respuesta Json de User Data. " + message);
					e.printStackTrace();
				}
				loginMessage = MENSAJE_LOGIN_OK;
				statusBandejaOk = true;
				break;
				
			case HttpURLConnection.HTTP_INTERNAL_ERROR:
			case HttpURLConnection.HTTP_BAD_GATEWAY:
			case HttpURLConnection.HTTP_GATEWAY_TIMEOUT:
			case HttpURLConnection.HTTP_CLIENT_TIMEOUT:
			case HttpURLConnection.HTTP_UNAVAILABLE:
				loginMessage = MENSAJE_REINTENTE;
				break;
				
			case HttpURLConnection.HTTP_UNAUTHORIZED:
				loginMessage = MENSAJE_USUARIO;
				break;

			default:
				loginMessage = MENSAJE_TECNICO + "Status: " + status + ". Mensaje: " + message;
			}
			
		}
	}
	
	private class UploadThread extends Thread {
		ByteArrayOutputStream baos;
		String filename;
		
		UploadThread(ByteArrayOutputStream baos, String filename){
			this.baos = baos;
			this.filename = filename;
		}

		public void run(){
			HttpClient coneccion = null;
			String message = "";
			byte[] baosData = baos.toByteArray();
/*			Path path = Paths.get("C:\\Users\\Avature\\workspace\\Aplicacion\\testpdf.pdf");
			byte[] baosData = null;
			try {
				baosData = Files.readAllBytes(path);
			} catch (IOException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
*/
			//Armo el request a enviar
			//Endpoint
			String endpoint = configuracion.getEndpoint() + UPLOAD_SERVICE + "/U" + configuracion.getUserId();
			HttpPost httpPost = null;
			//Querystring parameters
			try {
				endpoint = addQuerystringParam(endpoint, "originalfilename", filename);
				httpPost = new HttpPost(endpoint);
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
			}
			
			//Termino de armar el request
			try {
				coneccion = getClient();
				MultipartEntityBuilder builder = MultipartEntityBuilder.create();
				builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
				builder.addBinaryBody("file", baosData, ContentType.create("application/pdf"), filename);
				HttpEntity entity = builder.build();
				httpPost.setEntity(entity);
			} catch (Exception e) {
				e.printStackTrace();
				message = "U1 " + e.toString();
			}
			System.out.println("Request a enviar:\n");
			System.out.println("Headers:\n");
			for(Header h : httpPost.getAllHeaders()){
				System.out.println(h.getName() + ":" + h.getValue() + "\n");
			}
			try {
				System.out.println("Body:\n" + EntityUtils.toString(httpPost.getEntity()));
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			//Recibo el response
			int status = 0;
	        String content = "";
			try {
				HttpResponse response = coneccion.execute(httpPost);				
				status = response.getStatusLine().getStatusCode();
				if (message.equals("")){
					message = response.getStatusLine().getReasonPhrase();
					HttpEntity entity = response.getEntity();
					if(entity != null) {
						content = EntityUtils.toString(entity);
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
				message = "U2 " + e.toString();
			} finally {
				httpPost.releaseConnection();
			}
			System.out.println("Respuesta: " + content);

			//Evalúo el status de la respuesta
			switch(status){
			
			case HttpURLConnection.HTTP_OK:
			case HttpURLConnection.HTTP_ACCEPTED:
				uploadMessage = MENSAJE_UPLOAD_OK;
				statusBandejaOk = true;
				break;
				
			case HttpURLConnection.HTTP_INTERNAL_ERROR:
			case HttpURLConnection.HTTP_BAD_GATEWAY:
			case HttpURLConnection.HTTP_GATEWAY_TIMEOUT:
			case HttpURLConnection.HTTP_CLIENT_TIMEOUT:
			case HttpURLConnection.HTTP_UNAVAILABLE:
				statusBandejaOk = false;
				uploadMessage = MENSAJE_REINTENTE;
				break;
				
			case HttpURLConnection.HTTP_UNAUTHORIZED:
				statusBandejaOk = false;
				uploadMessage = MENSAJE_USUARIO;
				break;

			default:
				statusBandejaOk = false;
				uploadMessage = MENSAJE_TECNICO + "Status: " + status + ". Mensaje: " + message;
			}
		}
	}
	
	public String addQuerystringParam (String endpoint, String key, String param) throws UnsupportedEncodingException{
		String printKey = URLEncoder.encode(key, "UTF-8");
		String printParam = URLEncoder.encode(param, "UTF-8");
		String newParam = printKey.concat("=").concat(printParam);
		return endpoint.indexOf('?') == -1 ?
				endpoint + "?" + newParam
				: endpoint + "&" + newParam;
	}
	
/*	
	private HttpURLConnection nuevaConeccion(String endpoint) throws Exception{
		URL url;
		url = new URL(endpoint);
		if (configuracion.getUseHttps()){
			HttpsURLConnection sslConex = (HttpsURLConnection) url.openConnection();
//			sslConex.setSSLSocketFactory(buildSslSocketFactory());
			return sslConex;
		}
		else{
			HttpURLConnection conex = (HttpURLConnection) url.openConnection();
			return conex;
		}
	}
	*/
	
	private HttpClient getClient() throws Exception {
		HttpClient client;
		int connectionTimeOutInSeconds = 5;

		client = new DefaultHttpClient();
		HttpParams params = client.getParams();
		HttpConnectionParams.setConnectionTimeout(params, connectionTimeOutInSeconds *1000);
		HttpConnectionParams.setSoTimeout(params, connectionTimeOutInSeconds *1000);
		TrustStrategy acceptingTrustStrategy = new TrustStrategy() {
			public boolean isTrusted(X509Certificate[] certificate, String authType) {
				return true;
			}
		};
		SSLSocketFactory sf;
		try {
         SSLContext sslContext = SSLContexts.custom().loadTrustMaterial(null, acceptingTrustStrategy).useTLS().build();
         sf = new SSLSocketFactory(
             sslContext,
             new String[]{"TLSv1.2", "TLSv1.1"},
             null,
             org.apache.http.conn.ssl.SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER
        );
		} catch (Exception e1) {
			throw new RuntimeException(e1);
		}
		SchemeRegistry registry = new SchemeRegistry();
		registry.register(new Scheme("http", 80, PlainSocketFactory.getSocketFactory()));
		registry.register(new Scheme("https", 443, sf));
		ThreadSafeClientConnManager mgr = new ThreadSafeClientConnManager(registry);
		mgr.setDefaultMaxPerRoute(100);
		mgr.setMaxTotal(100);
		client = new DefaultHttpClient(mgr, client.getParams());
//		HostnameVerifier hostnameVerifier = org.apache.http.conn.ssl.SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER;
//		HttpsURLConnection.setDefaultHostnameVerifier(hostnameVerifier);
		return client;
	}
	
}
