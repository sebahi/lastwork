package gov.loc.workflow.authentication;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.client.RestTemplate;

import gov.loc.workflow.domain.Env;
import gov.loc.workflow.domain.User;
import gov.loc.workflow.util.ConnectionEstablishement;

public class CustomAuthenticationProvider implements AuthenticationProvider {

	private Logger logger = Logger.getLogger(CustomAuthenticationProvider.class);
	
	@Autowired
	Env environment;
	
	@Autowired
	private User user;

	@Autowired
	RestTemplate restTemplate;
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String name = authentication.getName();
		String password = (String)authentication.getCredentials();
		if (authorizedUser(name, password)) {
			List<GrantedAuthority> grantedAuths = new ArrayList<>();
			grantedAuths.add(() -> {
				return "AUTH_USER";
			});
			Authentication auth = new UsernamePasswordAuthenticationToken(name, password, grantedAuths);
			return auth;
		} else {
			throw new AuthenticationCredentialsNotFoundException("Invalid Credentials!");
		}
	}

	private boolean authorizedUser(String name, String password) {

		if (name != null && !name.isEmpty() && password != null && !password.isEmpty()) {
			String status = getResponseStatus(name, password);
			if (status == null) {
				return false;
			} else if (status.equalsIgnoreCase("200")) {
				user.setUserName(name);
				user.setPassword(password);
				return true;
			} else {
				return false;
			}
		}
		return false;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
	}

	public String getResponseStatus(String userName, String password) {
		try {
			ConnectionEstablishement connectionEstablishement = new ConnectionEstablishement();
			String url = "http://"+environment.getEnvironment()+"/jbpm-console/rest/history/instances";
			logger.debug("Url: "+ url);	
			ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, connectionEstablishement.getConnectionRequest(userName,password), String.class);
			return response.getStatusCode().toString();
		} catch (Exception hce) {
			logger.error(hce);
		}
		return null;
	}
	
}