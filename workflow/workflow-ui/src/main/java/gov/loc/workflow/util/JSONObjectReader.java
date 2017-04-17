package gov.loc.workflow.util;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import gov.loc.workflow.domain.User;

@Component
public class JSONObjectReader {

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	User user;

	@Autowired
	ConnectionEstablishement connectionEstablishement;

	public JSONArray jsonReader(String url, String jsonField) {

		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET,
				connectionEstablishement.getConnectionRequest(user.getUserName(), user.getPassword()), String.class);

		String body = response.getBody();
		JSONObject json = new JSONObject(body);
		JSONArray arrayTask = json.getJSONArray(jsonField);
		return arrayTask;
	}
}
