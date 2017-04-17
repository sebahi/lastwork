package gov.loc.workflow.controller;

import java.sql.Timestamp;
import java.text.ParseException;
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
import gov.loc.workflow.domain.TaskStatus;
import gov.loc.workflow.util.JSONObjectReader;

@Controller
public class TaskStatusController {
	private Logger logger = Logger.getLogger(TaskStatusController.class);

	@Autowired
	Env environment;

	@Autowired
	JSONObjectReader jsonObjectReader;

	TaskStatus taskStatus;
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/taskinstance", method = RequestMethod.GET)
	public ModelAndView getAllProcessesDefinitions(Model model) {

		SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy:HH:mm:ss ");
		
		ModelAndView mav = new ModelAndView("taskList");
		String url = "http://" + environment.getEnvironment() + "/jbpm-console/rest/task/query";

		taskStatus = new TaskStatus();
		ArrayList<TaskStatus> taskList = new ArrayList<>();

		JSONArray array = jsonObjectReader.jsonReader(url, "taskSummaryList");
		for (int i = 0; i < array.length(); i++) {
			JSONObject jsn = array.getJSONObject(i);
			Iterator<String> keys = jsn.keys();
			while (keys.hasNext()) {
				keys.next();
				taskStatus.setTask(jsn.get("name").toString().trim());
				taskStatus.setStatus(jsn.get("status").toString().trim());
				taskStatus.setPriority(jsn.get("priority").toString().trim());
				String createdOn = jsn.get("created-on").toString().trim();
				Timestamp createdOnStamp = new Timestamp(Long.valueOf(createdOn));
				Date cDate = new Date(createdOnStamp.getTime());
				Date createdDate;

				try {
					createdDate = format.parse(format.format(cDate));
					taskStatus.setCreatedOn(createdDate);
				} catch (ParseException e) {
					e.printStackTrace();
					logger.error(e);
				}
			}
			
			taskList.add(new TaskStatus(taskStatus.getTask(), taskStatus.getPriority(), taskStatus.getStatus(),
					taskStatus.getCreatedOn(), null));
		}

		mav.addObject("task", taskList);
		return mav;
	}
}
