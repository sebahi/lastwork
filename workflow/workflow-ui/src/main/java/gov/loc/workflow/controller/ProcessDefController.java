package gov.loc.workflow.controller;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import gov.loc.workflow.domain.Bag;
import gov.loc.workflow.domain.Env;
import gov.loc.workflow.domain.ProcessDefinition;
import gov.loc.workflow.domain.ProcessInstance;
import gov.loc.workflow.util.JSONObjectReader;

@Controller
public class ProcessDefController {

	private Logger logger = Logger.getLogger(ProcessDefController.class);

	@Autowired
	Env environment;

	@Autowired
	JSONObjectReader jsonObjectReader;

	private ProcessInstance processInstance;
	private ArrayList<String> list;
	private ArrayList<ProcessInstance> processInstancesList;
	private List<ProcessDefinition>  processDefinitionList;
	private int activeCount;
	private int completedCount;
	private int abortedCount;

	@RequestMapping(value = "/processdef", method = RequestMethod.GET)
	public String getAllProcessesDefinitions(ModelMap model) throws ParseException {

		String url = "http://" + environment.getEnvironment() + "/jbpm-console/rest/deployment/processes";
		processDefinitionList = this.getProcessDefinitionList(url);
		List<ProcessInstance> processInstancesList = this.getProcessInstancesList();
		model.addAttribute(processDefinitionList);
		model.addAttribute("processInstances", processInstancesList);
		
		for (String string : getProcessStatus()) {
			JSONObject jsonObject = new JSONObject(string);
			switch (jsonObject.get("status").toString().trim()) {
			case "1":
				activeCount = activeCount + 1;
				break;
			case "2":
				completedCount = completedCount + 1;
				break;
			case "3":
				abortedCount = abortedCount + 1;
				break;
			}
		}

		HashMap<String, Integer> stateMap = new HashMap<>();
		stateMap.put("activeCount", activeCount);
		stateMap.put("completedCount", completedCount);
		stateMap.put("abortedCount", abortedCount);
		activeCount = 0;
		completedCount = 0;
		abortedCount = 0;

		model.addAttribute("stateMap", stateMap);

		return "processDefinitions";
	}

	@RequestMapping(value = "/process/start", method = RequestMethod.GET)
	public String startProcess(@RequestParam String processName, ModelMap model) {
		logger.debug("Selected process definition name: " + processName);
		String url = "http://" + environment.getEnvironment() + "/jbpm-console/rest/deployment/processes";
		List<ProcessDefinition> processDefinitionList = this.getProcessDefinitionList(url);

		ProcessDefinition selectedProcessDefinition = new ProcessDefinition();
		for (ProcessDefinition processDefinition : processDefinitionList) {
			if (processDefinition.getName().equalsIgnoreCase(processName)) {
				selectedProcessDefinition.setName(processName);
				selectedProcessDefinition.setId(processDefinition.getId());
				selectedProcessDefinition.setDeploymentId(processDefinition.getDeploymentId());
			}
		}

		model.addAttribute("bag", new Bag());
		model.addAttribute("processDefinition", selectedProcessDefinition);
		model.addAttribute("server", environment.getEnvironment());

		return "processForm";
	}

	@RequestMapping(value = "/processdef/filter", method = RequestMethod.POST)
	public String processesDefFilter(@RequestParam("fltr") String filterType, ModelMap model) {
		String url = "http://" + environment.getEnvironment() + "/jbpm-console/rest/history/instances";

		try {
			for (String string : getProcessStatus()) {
				JSONObject jsonObject = new JSONObject(string);
				if (jsonObject.get("status").toString().contentEquals(filterType)) {
					processInstance.setProcessInstanceName(jsonObject.get("process-name").toString().trim());
					processInstance.setInitiator(jsonObject.get("identity").toString().trim());

					processInstance.setVersion(jsonObject.get("process-version").toString().trim());
					processInstance.setProcessInstanceId(jsonObject.get("process-instance-id").toString().trim());
					processInstance.setDeploymentId(jsonObject.get("external-id").toString().trim());
					SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy:HH:mm:ss ");

					String start = jsonObject.get("start").toString().trim();
					Timestamp startStamp = new Timestamp(Long.valueOf(start));
					Date sDate = new Date(startStamp.getTime());
					Date startDate = format.parse(format.format(sDate));
					processInstance.setStartDate(startDate);

					processInstancesList.add(new ProcessInstance(processInstance.getProcessInstanceId(), null,
							processInstance.getStatus(), processInstance.getProcessInstanceName(),
							processInstance.getInitiator(), processInstance.getVersion(),
							processInstance.getDeploymentId(), processInstance.getStartDate()));

				}
			}
		} catch (Exception ex) {
			System.out.println(ex);
			logger.debug(ex);
		}
		
		model.addAttribute(processDefinitionList);
		model.addAttribute("processInstances", processInstancesList);
		

		for (String string : getProcessStatus()) {
			JSONObject jsonObject = new JSONObject(string);
			switch (jsonObject.get("status").toString().trim()) {
			case "1":
				activeCount = activeCount + 1;
				break;
			case "2":
				completedCount = completedCount + 1;
				break;
			case "3":
				abortedCount = abortedCount + 1;
				break;
			}
		}

		HashMap<String, Integer> stateMap = new HashMap<>();
		stateMap.put("activeCount", activeCount);
		stateMap.put("completedCount", completedCount);
		stateMap.put("abortedCount", abortedCount);
		activeCount = 0;
		completedCount = 0;
		abortedCount = 0;

		model.addAttribute("stateMap", stateMap);
		
		return "processDefinitions";
	}

	@SuppressWarnings("unchecked")
	private List<ProcessDefinition> getProcessDefinitionList(String url) {

		List<ProcessDefinition> processDefinitionList = new ArrayList<ProcessDefinition>();
		JSONArray array = jsonObjectReader.jsonReader(url, "processDefinitionList");
		for (int i = 0; i < array.length(); i++) {
			JSONObject jsn = array.getJSONObject(i);
			JSONObject pdJson = jsn.getJSONObject("process-definition");
			ProcessDefinition processDefinition = new ProcessDefinition();
			processDefinition.setId((String) pdJson.get("id"));
			processDefinition.setName((String) pdJson.get("name"));
			processDefinition.setDeploymentId((String) pdJson.get("deployment-id"));
			processDefinition.setPackageName((String) pdJson.get("package-name"));
			processDefinitionList.add(processDefinition);
		}
		return processDefinitionList;
	}

	private List<String> getProcessStatus() {
		String url = "http://" + environment.getEnvironment() + "/jbpm-console/rest/history/instances";

		JSONArray jsonArray = jsonObjectReader.jsonReader(url, "historyLogList");
		processInstancesList = new ArrayList<>();
		list = new ArrayList<>();
		processInstance = new ProcessInstance();
		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsn = jsonArray.getJSONObject(i);
			@SuppressWarnings("unchecked")
			Iterator<String> keys = jsn.keys();
			while (keys.hasNext()) {
				String key = keys.next();
				list.add(jsn.get(key).toString());
			}
		}

		return list;
	}
	
	private ArrayList<ProcessInstance> getProcessInstancesList() throws ParseException{
		
		for (String string : getProcessStatus()) {
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
		return processInstancesList;
	}

}
