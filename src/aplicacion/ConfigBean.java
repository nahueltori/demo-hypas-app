package aplicacion;

import java.io.Serializable;

public class ConfigBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private String userName = "demouser";
	private String password = "demo1234";
	private String domain = "ws1.aplicacion.com.ar/DDRest.svc";
	private String userId = "";
	private boolean useHttps = true;
	private static ConfigBean instance = new ConfigBean();

	/**
	* Constructor
	*/
	private ConfigBean() {
	}
	
	/**
	* Get ConfigBean instance.
	* @return
	*/
	public static ConfigBean getInstance() {
		return instance;
	}
	/**
	* set name of user.
	* @param name
	* @return
	*/
	public void setUserName(String name) {
		this.userName = name;
	}
	/**
	* get name of user.
	* @return
	*/
	public String getUserName() {
		return userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public boolean getUseHttps() {
		return useHttps;
	}

	public void setUseHttps(boolean useHttps) {
		this.useHttps = useHttps;
	}

	public String getEndpoint() {
		return useHttps ? "https://" + domain : "http://" + domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}
}
