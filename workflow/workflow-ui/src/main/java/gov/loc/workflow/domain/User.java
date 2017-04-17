package gov.loc.workflow.domain;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

//TODO: Remove the @Component annotation. 
@Component
@Scope(value="session", proxyMode=ScopedProxyMode.TARGET_CLASS)
public class User {
	
	private String userName;
	private String password;

	public User() {
	}

	public User(String userName, String password) {
		this.userName = userName;
		this.password = password;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
