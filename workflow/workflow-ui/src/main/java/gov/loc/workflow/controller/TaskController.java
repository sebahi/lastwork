package gov.loc.workflow.controller;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import gov.loc.workflow.domain.Env;
import gov.loc.workflow.domain.Task;
import gov.loc.workflow.domain.User;
import gov.loc.workflow.util.ConnectionEstablishement;
import gov.loc.workflow.util.JSONObjectReader;

@Controller
public class TaskController {

	private Logger logger = Logger.getLogger(TaskController.class);

	@Autowired
	Env environment;

	@Autowired
	User user;

	@Autowired
	ConnectionEstablishement connectionEstablishement;

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	JSONObjectReader jsonObjectReader;

	private Task task;
	private Task completedTask;
	private ArrayList<String> list;
	private JSONObject jsonObject;
	private int myTaskCount;
	private int inProgressCount;
	private int reservedCount;
	private int readyCount;
	private int completedCount;
	private int projectTaskCount;
	private ArrayList<Task> taskList;
	private boolean filter;
	private ResponseEntity<String> response;
	private boolean error;

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/task", method = RequestMethod.GET)
	public ModelAndView getAllTasks(Model model) {

		SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy:HH:mm:ss ");

		ModelAndView mav = new ModelAndView("task");
		String url = "http://" + environment.getEnvironment() + "/jbpm-console/rest/task/query";
		task = new Task();
		taskList = new ArrayList<>();
		JSONArray array = jsonObjectReader.jsonReader(url, "taskSummaryList");
		for (int i = 0; i < array.length(); i++) {
			JSONObject jsn = array.getJSONObject(i);
			Iterator<String> keys = jsn.keys();
			while (keys.hasNext()) {
				keys.next();
				task.setTaskId(jsn.get("id").toString().trim());
				task.setTaskName(jsn.get("name").toString().trim());
				if(jsn.has("created-by"))
				task.setCreatedBy(jsn.get("created-by").toString().trim());
				task.setTaskDescription(jsn.get("description").toString().trim());
				task.setProcessInstanceId(jsn.get("process-instance-id").toString().trim());
				task.setProcessId(jsn.get("process-id").toString().trim());
				task.setTaskStatus(jsn.get("status").toString().trim());
				if(jsn.has("actual-owner")){
					task.setActualOwner(jsn.getString("actual-owner"));
					task.setPotentialOwner("");
				}
				else{
					task.setActualOwner("");
					task.setPotentialOwner(jsn.optString("potential-owners", user.getUserName()));
					/*ResponseEntity<String> response = getConnection(task.getTaskId(), HttpMethod.GET, "");
					JSONObject jsonObject = new JSONObject(response.getBody());
					
					JSONObject jsnPeopleAssignments = jsonObject.getJSONObject("people-assignments");
					JSONArray potentialOwners = jsnPeopleAssignments.getJSONArray("potential-owners");
					System.out.println("*****"+potentialOwners);
					String processInstanceId = jsn.get("id").toString().trim();
					for (int j = 0; j < potentialOwners.length(); j++) {
						JSONObject jsnPotentialOwner = potentialOwners.getJSONObject(j);
						String potentialOwnerId = jsnPotentialOwner.getString("id");
						String potentialOwnerType = jsnPotentialOwner.getString("type");
						if(processInstanceId.equalsIgnoreCase(jsonObject.optString("id"))){
							if (potentialOwnerType.equalsIgnoreCase("user")) {
								task.setPotentialOwner(potentialOwnerId);
							}
						}break;
					}*/
					
					
				}
				
				String createdOn = jsn.get("created-on").toString().trim();
				Timestamp createdOnStamp = new Timestamp(Long.valueOf(createdOn));
				Date cDate = new Date(createdOnStamp.getTime());
				Date createdDate;

				try {
					createdDate = format.parse(format.format(cDate));
					task.setCreatedOn(createdDate);
				} catch (ParseException e) {
					e.printStackTrace();
					logger.error(e);
				}
				

			}
		
/*			ResponseEntity<String> response = getConnection(task.getTaskId(), HttpMethod.GET, "");
			JSONObject jsonObject = new JSONObject(response.getBody());
			JSONObject jsnPeopleAssignments = jsonObject.getJSONObject("people-assignments");
			JSONArray potentialOwners = jsnPeopleAssignments.getJSONArray("potential-owners");
			for (int j = 0; j < potentialOwners.length(); j++) {
				JSONObject jsnPotentialOwner = potentialOwners.getJSONObject(j);
				String potentialOwnerId = jsnPotentialOwner.getString("id");
				String potentialOwnerType = jsnPotentialOwner.getString("type");
				if (potentialOwnerType.equalsIgnoreCase("user")) {
					task.setPotentialOwner(potentialOwnerId);
				}
			}*/

			taskList.add(new Task(task.getTaskId(), task.getTaskName(), task.getTaskDescription(), task.getTaskStatus(),
					task.getCreatedBy(), task.getCreatedOn(), task.getActualOwner(), task.getProcessId(),
					task.getProcessInstanceId(), task.getPotentialOwner(), null));
		}

		for (Task task : taskList) {
			if (task.getActualOwner()!=null && task.getActualOwner().contentEquals(user.getUserName())) {
				myTaskCount++;
			}

			String status = task.getTaskStatus();
			switch (status) {
			case "Reserved":
				reservedCount++;
				break;
			case "InProgress":
				inProgressCount++;
				break;
			case "Ready":
				readyCount++;
				break;
			}
		}

		ArrayList<Task> readyTaskList = new ArrayList<>();
		for (Task task : taskList) {
			readyTaskList.add(task);
		}
		model.addAttribute("myTasksCount", myTaskCount);
		model.addAttribute("readyCount", readyCount);
		model.addAttribute("inProgressCount", inProgressCount);
		model.addAttribute("reservedCount", reservedCount);
		model.addAttribute("completedCount", completedCount);
		model.addAttribute("projectTaskCount", taskList.size());

		mav.addObject("task", readyTaskList);
		mav.addObject("tasks", taskList);
		myTaskCount = 0;
		readyCount = 0;
		inProgressCount = 0;
		reservedCount = 0;
		completedCount = 0;
		projectTaskCount = 0;

		return mav;
	}

	@RequestMapping(value = "/task/start", method = RequestMethod.GET)
	public String claim(@RequestParam("taskId") String taskId, Model model) {
		error = false;
		task.setTaskId(taskId);
		JSONObject jsnStatus = getStatusById(taskId);
		String status = jsnStatus.getString("status");
		if (status.equalsIgnoreCase("Ready")) {
			claimStart(taskId, "claim");
			if(error){
				model.addAttribute("error", error);
			}
		} else if (status.equalsIgnoreCase("Reserved")) {
			claimStart(taskId, "start");
		}
		return "taskForm";
	}

	@SuppressWarnings("unused")
	@RequestMapping(value = "/task/claim", method = RequestMethod.GET)
	public String claimProcess(@ModelAttribute("resubmit") String resubmit, Model model) {

		ModelAndView mav = new ModelAndView("task");

		JSONObject jsn = getStatusById(task.getTaskId());
		String status = jsn.getString("status");
		switch (status) {
		case "Reserved":
			claimStart(task.getTaskId(), "resume");
			claimStart(task.getTaskId(), "start");
			break;
		case "InProgress":
			claimStart(task.getTaskId(), "resume");
			break;
		case "Ready":
			status = "Reserved";
			claimStart(task.getTaskId(), "claim");
			claimStart(task.getTaskId(), "start");
			break;
		}
		if (resubmit.equalsIgnoreCase("on") && resubmit != null) {
			String responseCompleteBody = claimComplete(task.getTaskId(), "true");
		} else {
			String responseCompleteBody = claimComplete(task.getTaskId(), "false");
		}

		mav.addObject("tasks", completedTask);
		return "redirect:/task";
	}

	public ResponseEntity<String> claimStart(String taskId, String status) {
		ResponseEntity<String> body = getConnection(taskId, HttpMethod.POST, status);
		return body;
	}

	public String claimComplete(String taskId, String confirm) {

		completedTask = setCompletedTasks(taskId);
		MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
		map.add("map_resubmit_out", confirm);
		ResponseEntity<String> response = getConnection(taskId, HttpMethod.POST, "complete", map);
		String body = response.getBody();
		if (response.getStatusCode().toString().contentEquals("200")) {
			completedCount++;
		}

		return body;
	}

	@RequestMapping(value = "/task/filter", method = RequestMethod.POST)
	public ModelAndView getFilterdTasks(@RequestParam("fltr") String filterType, Model model) {
		ModelAndView mav = new ModelAndView("task");
		ArrayList<Task> filteredTasks = new ArrayList<>();
		filter = true;
		for (Task task : taskList) {
			if (filterType.equalsIgnoreCase(task.getTaskStatus())) {
				filteredTasks.add(task);

			} else if (filterType.equalsIgnoreCase("myTasks")) {
				filter = false;
				if (task.getActualOwner().contentEquals(user.getUserName())) {
					filteredTasks.add(task);
				}
			} else if (filterType.equalsIgnoreCase("All")) {
				filter = false;
				filteredTasks.add(task);
			}

			if (task.getActualOwner().contentEquals(user.getUserName()))
				myTaskCount++;
			String status = task.getTaskStatus();
			switch (status) {
			case "Reserved":
				reservedCount++;
				break;
			case "InProgress":
				inProgressCount++;
				break;
			case "Ready":
				readyCount++;
				break;
			}
		}

		if (filterType.equalsIgnoreCase("Completed")) {
			filteredTasks.add(completedTask);
			completedCount = filteredTasks.size();
		}

		model.addAttribute("myTasksCount", myTaskCount);
		model.addAttribute("readyCount", readyCount);
		model.addAttribute("inProgressCount", inProgressCount);
		model.addAttribute("reservedCount", reservedCount);
		model.addAttribute("completedCount", completedCount);
		model.addAttribute("projectTaskCount", taskList.size());
		model.addAttribute("filter", filter);
		mav.addObject("task", filteredTasks);
		mav.addObject("tasks", filteredTasks);
		myTaskCount = 0;
		readyCount = 0;
		inProgressCount = 0;
		reservedCount = 0;
		completedCount = 0;
		filter = false;
		return mav;
	}

	@RequestMapping(value = "/task/options", method = RequestMethod.POST)
	public ModelAndView taskManagement(@RequestParam("optn") String option, Model model) {

		ModelAndView mav = new ModelAndView("task");

		if (option.equalsIgnoreCase("Unassign")) {
			getConnection(task.getTaskId(), HttpMethod.POST, "release");
			mav = getAllTasks(model);
		} else if (option.equalsIgnoreCase("continue")) {
			getConnection(task.getTaskId(), HttpMethod.POST, "claimnextavailable");
			mav = getAllTasks(model);
		} else if (option.equalsIgnoreCase("repeat")) {

		} else {
			JSONObject jsn = getStatusById(task.getTaskId());
			JSONArray comments = jsn.getJSONArray("comments");
			for (int i = 0; i < comments.length(); i++) {
				JSONObject jsnComments = comments.getJSONObject(i);
				String textJson = (String) jsnComments.get("text");
				mav = new ModelAndView("taskForm");
			}
		}

		return mav;
	}

	public JSONObject getStatusById(String taskId) {

		String url = "http://" + environment.getEnvironment() + "/jbpm-console/rest/task/" + taskId;
		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET,
				connectionEstablishement.getConnectionRequest(user.getUserName(), user.getPassword()), String.class);
		JSONObject jsonObject = new JSONObject(response.getBody());
		JSONObject jsn = jsonObject.getJSONObject("taskData");
		return jsn;
	}

	public ResponseEntity<String> getConnection(String taskId, HttpMethod method, String action) {
		String url = "http://" + environment.getEnvironment() + "/jbpm-console/rest/task/" + taskId + "/" + action;
		try{
		response = restTemplate.exchange(url, method,
				connectionEstablishement.getConnectionRequest(user.getUserName(), user.getPassword()), String.class);
		}catch(Exception pde){
		System.out.println("*******result"+pde);
		error = true;
		}
		return response;
	}

	public ResponseEntity<String> getConnection(String taskId, HttpMethod method, String action,
			MultiValueMap<String, String> map) {
		String url = "http://" + environment.getEnvironment() + "/jbpm-console/rest/task/" + taskId + "/" + action;
		response = restTemplate.exchange(url, HttpMethod.POST,
				connectionEstablishement.getConnectionRequest(user.getUserName(), user.getPassword(), map),
				String.class);
		return response;
	}

	public Task setCompletedTasks(String taskId) {

		SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy:HH:mm:ss ");
		JSONObject jsn = getStatusById(taskId);

		completedTask = new Task();
		Iterator<String> keys = jsn.keys();
		while (keys.hasNext()) {
			keys.next();
			completedTask.setTaskId(taskId);
			// completedTask.setTaskName(jsn.get("name").toString().trim());
			completedTask.setCreatedBy(jsn.optString("created-by", user.getUserName()));
			//completedTask.setCreatedBy(jsn.get("created-by").toString().trim());
			// completedTask.setTaskDescription(jsn.get("description").toString().trim());
			completedTask.setActualOwner(jsn.get("actual-owner").toString().trim());
			completedTask.setProcessInstanceId(jsn.get("process-instance-id").toString().trim());
			completedTask.setProcessId(jsn.get("process-id").toString().trim());

			String createdOn = jsn.get("created-on").toString().trim();
			Timestamp createdOnStamp = new Timestamp(Long.valueOf(createdOn));
			Date cDate = new Date(createdOnStamp.getTime());
			Date createdDate;

			try {
				createdDate = format.parse(format.format(cDate));
				completedTask.setCreatedOn(createdDate);
			} catch (ParseException e) {
				e.printStackTrace();
				logger.error(e);
			}
		}
		return completedTask;
	}
}
