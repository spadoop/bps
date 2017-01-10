package com.kingmed.pl.common.vo;

import java.util.Date;

public class TaskVO {

	private String pk_id;
	
	/**
	 * 工作项ID
	 */
	private String taskInstID;

	/**
	 * 工单主题
	 */
	private String jobTitle;

	/**
	 * 派发部门
	 */
	private String senderDept;

	/**
	 * 派发人员
	 */
	private String senderID;

	/**
	 * 派发时间
	 */
	private Date createDate;

	/**
	 * 活动流水号
	 */
	private String activityInstID;

	/**
	 * 当前环节
	 */
	private String activityInstName;
	
	/**
	 * 环节ID
	 */
	private String activityDefID;

	/**
	 * 任务流水号
	 */
	private String jobID;


	/**
	 * 模板名称
	 */
	private String wfName;
	
	/**
	 * 
	 */
	private String processInstID;

	private String shard;
	
	private String url;
	
	/**
	 * 完成时限
	 */
	private String deadline;

	private String formMainId;
	
	/**
	 * 工作项业务状态
	 * 0  COMMON  正常  
		1  AGENT  代理  
		2  DELGATE  代办  
		3  HELP  协办  
		4  HELP_WAITAFFIRM  待确认  
		6  HELP_REDO  修改  

	 */
	private int bizState;
	
	private String isTimeOut;
	
	private String remindTime;
	
	private String finalTime;
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getFormMainId() {
		return formMainId;
	}

	public void setFormMainId(String formMainId) {
		this.formMainId = formMainId;
	}
	
	public String getPk_id() {
		return pk_id;
	}

	public void setPk_id(String pk_id) {
		this.pk_id = pk_id;
	}

	public String getTaskInstID() {
		return taskInstID;
	}

	public void setTaskInstID(String taskInstID) {
		this.taskInstID = taskInstID;
	}

	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	public String getSenderDept() {
		return senderDept;
	}

	public void setSenderDept(String senderDept) {
		this.senderDept = senderDept;
	}

	public String getSenderID() {
		return senderID;
	}

	public void setSenderID(String senderID) {
		this.senderID = senderID;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getActivityInstID() {
		return activityInstID;
	}

	public void setActivityInstID(String activityInstID) {
		this.activityInstID = activityInstID;
	}

	public String getActivityInstName() {
		return activityInstName;
	}

	public void setActivityInstName(String activityInstName) {
		this.activityInstName = activityInstName;
	}

	public String getJobID() {
		return jobID;
	}

	public void setJobID(String jobID) {
		this.jobID = jobID;
	}

	public String getProcessInstID() {
		return processInstID;
	}

	public void setProcessInstID(String processInstID) {
		this.processInstID = processInstID;
	}

	public String getShard() {
		return shard;
	}

	public void setShard(String shard) {
		this.shard = shard;
	}

	public String getWfName() {
		return wfName;
	}

	public void setWfName(String wfName) {
		this.wfName = wfName;
	}

	/**
	 * <pre>
	 * bizState的取得
	 * </pre>
	 * @return bizState
	 */
	public int getBizState() {
		return bizState;
	}

	/**
	 * <pre>
	 * bizState的设置
	 * </pre>
	 * @param bizState the bizState to set
	 */
	public void setBizState(int bizState) {
		this.bizState = bizState;
	}

	public String getDeadline() {
		return deadline;
	}

	public void setDeadline(String deadline) {
		this.deadline = deadline;
	}

	public String getActivityDefID() {
		return activityDefID;
	}

	public void setActivityDefID(String activityDefID) {
		this.activityDefID = activityDefID;
	}

	public String getIsTimeOut() {
		return isTimeOut;
	}

	public void setIsTimeOut(String isTimeOut) {
		this.isTimeOut = isTimeOut;
	}

	public String getRemindTime() {
		return remindTime;
	}

	public void setRemindTime(String remindTime) {
		this.remindTime = remindTime;
	}

	public String getFinalTime() {
		return finalTime;
	}

	public void setFinalTime(String finalTime) {
		this.finalTime = finalTime;
	}


}
