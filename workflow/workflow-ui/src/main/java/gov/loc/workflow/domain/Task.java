package gov.loc.workflow.domain;

import java.util.Date;

public class Task {

	private String taskId;
	private String taskName;
	private String taskDescription;
	private String taskStatus;
	private String createdBy;
	private Date createdOn;
	private String actualOwner;
	private String processId;
	private String processInstanceId;
	private String potentialOwner;
	
	private String resubmit;

	public Task() {
	}



	public Task(String taskId, String taskName, String taskDescription, String taskStatus,
			String createdBy, Date createdOn, String actualOwner, String processId, String processInstanceId,
			String potentialOwner, String resubmit) {
		this.taskId = taskId;
		this.taskName = taskName;
		this.taskDescription = taskDescription;
		this.taskStatus = taskStatus;
		this.createdBy = createdBy;
		this.createdOn = createdOn;
		this.actualOwner = actualOwner;
		this.processId = processId;
		this.processInstanceId = processInstanceId;
		this.potentialOwner = potentialOwner;
		this.resubmit = resubmit;
	}

	public String getResubmit() {
		return resubmit;
	}

	public void setResubmit(String resubmit) {
		this.resubmit = resubmit;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getTaskDescription() {
		return taskDescription;
	}

	public void setTaskDescription(String taskDescription) {
		this.taskDescription = taskDescription;
	}

	public String getTaskStatus() {
		return taskStatus;
	}

	public void setTaskStatus(String taskStatus) {
		this.taskStatus = taskStatus;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getActualOwner() {
		return actualOwner;
	}

	public void setActualOwner(String actualOwner) {
		this.actualOwner = actualOwner;
	}

	public String getProcessId() {
		return processId;
	}

	public void setProcessId(String processId) {
		this.processId = processId;
	}
	
	public String getProcessInstanceId() {
		return processInstanceId;
	}
	
	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public String getPotentialOwner() {
		return potentialOwner;
	}

	public void setPotentialOwner(String potentialOwner) {
		this.potentialOwner = potentialOwner;
	}

}
