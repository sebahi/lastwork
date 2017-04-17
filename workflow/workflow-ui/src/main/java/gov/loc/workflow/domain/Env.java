package gov.loc.workflow.domain;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Env {

	@Value("${ip.address}")
	private String ipAddress;
	@SuppressWarnings("unused")
	private String environment;
	private String webServer;
	
	public Env() {
	}

	public Env(String environment, String webServer) {
		this.environment = environment;
		this.webServer = webServer;
	}


	public String getEnvironment() {
		return ipAddress;
	}

	public void setEnvironment(String environment) {
		this.environment = environment;
	}

	public String getWebServer() {
		return webServer;
	}

	public void setWebServer(String webServer) {
		this.webServer = webServer;
	}

}
