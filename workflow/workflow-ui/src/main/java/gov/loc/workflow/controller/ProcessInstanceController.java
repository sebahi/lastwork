package gov.loc.workflow.controller;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import gov.loc.workflow.domain.Env;
import gov.loc.workflow.domain.ProcessInstance;
import gov.loc.workflow.util.JSONObjectReader;

@Controller
public class ProcessInstanceController {
	
	private Logger logger = Logger.getLogger(ProcessInstanceController.class);

	@Autowired
	Env environment;

	@Autowired
	JSONObjectReader jsonObjectReader;
	
	private ProcessInstance processInstance;
	private ArrayList<String> list;
	private ArrayList<ProcessInstance> processInstancesList;
    
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/processinstance", method = RequestMethod.GET)
	public ModelAndView getAllProcessesDefinitions(Model model) {

		
		ModelAndView mav = new ModelAndView("processInstanceList");
		try {
			String url = "http://" + environment.getEnvironment() + "/jbpm-console/rest/history/instances";

			JSONArray array = jsonObjectReader.jsonReader(url, "historyLogList");
			processInstancesList = new ArrayList<>();
			list = new ArrayList<>();
			processInstance = new ProcessInstance();
			for (int i = 0; i < array.length(); i++) {
				JSONObject jsn = array.getJSONObject(i);
				@SuppressWarnings("unchecked")
				Iterator<String> keys = jsn.keys();
				while (keys.hasNext()) {
					String key = keys.next();
					list.add(jsn.get(key).toString());
				}
			}
			
			for (String string : list) {
				JSONObject jsonObject = new JSONObject(string);
				processInstance.setProcessInstanceName(jsonObject.get("process-name").toString().trim());
				processInstance.setInitiator(jsonObject.get("identity").toString().trim());
				String state = null;
				switch (jsonObject.get("status").toString().trim()) {
				case "1":
					state = "Active";
					break;
				case "2":
					state = "Completed";
					break;
				case "3":
					state = "Aborted";
					break;
				}
				processInstance.setStatus(state);
				processInstance.setVersion(jsonObject.get("process-version").toString().trim());
				processInstance.setProcessInstanceId(jsonObject.get("process-instance-id").toString().trim());
				processInstance.setDeploymentId(jsonObject.get("external-id").toString().trim());
				SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy:HH:mm:ss ");

				String start = jsonObject.get("start").toString().trim();
				Timestamp startStamp = new Timestamp(Long.valueOf(start));
				Date sDate = new Date(startStamp.getTime());
				Date startDate = format.parse(format.format(sDate));
				processInstance.setStartDate(startDate);
				
				processInstancesList.add(new ProcessInstance(processInstance.getProcessInstanceId(), null, processInstance.getStatus(), processInstance.getProcessInstanceName(),
						processInstance.getInitiator(), processInstance.getVersion(), processInstance.getDeploymentId(), processInstance.getStartDate()));
			
			}
		} catch (Exception ex) {
			System.out.println(ex);
			logger.debug(ex);
		}
		
		mav.addObject("processInstances", processInstancesList);
		return mav;
	}
}
