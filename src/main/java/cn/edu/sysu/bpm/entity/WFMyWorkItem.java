/* ========================================
 * System Name　　：DGCUVM 
 * SubSystem Name ：DGCUVM System
 * ----------------------------------------
 * Create Date/Change History 
 *
 * ----------------------------------------
 * 2014年1月16日 　zhang chong jun   Create
 * 
 * 
 * ----------------------------------------
 * Copyright 2013 Global Delivery Center,Deloitte Consulting All Rights Reserved.
 */
package cn.edu.sysu.bpm.entity;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 *  class名
 *  class功能说明
 * </pre>
 * @author zhang chong jun
 * @version 1.00
 */
public class WFMyWorkItem {
	String formSeq;
	String formTitle;
	String sendUserId;
	Date sendTime;
	String formMainId;
	

	long workItemID;
    String workItemName;
    String workItemType;
    String workItemDesc;
    int currentState;
    String partiName;
    int priority;
    String isTimeOut;
    long limitNum;
    String limitNumDesc;
    String createTime;
    String startTime;
    String endTime;
    String finalTime;
    String remindTime;
    String actionURL;
    long processInstID;
    long activityInstID;
    String statesList;
    int timeOutNum;
    String timeOutNumDesc;
    String actionMask;
    String processInstName;
    String activityInstName;
    long processDefID;
    String processDefName;
    String processChName;
    String activityDefID;
    String allowAgent;
    int bizState;
    String assistantName;
    String urlType;
    long rootProcInstID;
    String participant;
    String assistant;
	private Map bizObject;
    String catalogUUID;
    String catalogName;
    String dealOpinion;
    String dealResult;
    String url;
    String param1;
    String param2;
    String param3;
    String param4;
    String param5;
    
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
    private List participants;
    
	/**
	 * <pre>
	 * formSeq的取得
	 * </pre>
	 * @return formSeq
	 */
	public String getFormSeq() {
		return formSeq;
	}
	/**
	 * <pre>
	 * formSeq的设置
	 * </pre>
	 * @param formSeq the formSeq to set
	 */
	public void setFormSeq(String formSeq) {
		this.formSeq = formSeq;
	}
	/**
	 * <pre>
	 * formTitle的取得
	 * </pre>
	 * @return formTitle
	 */
	public String getFormTitle() {
		return formTitle;
	}
	/**
	 * <pre>
	 * formTitle的设置
	 * </pre>
	 * @param formTitle the formTitle to set
	 */
	public void setFormTitle(String formTitle) {
		this.formTitle = formTitle;
	}
	/**
	 * <pre>
	 * sendUserId的取得
	 * </pre>
	 * @return sendUserId
	 */
	public String getSendUserId() {
		return sendUserId;
	}
	/**
	 * <pre>
	 * sendUserId的设置
	 * </pre>
	 * @param sendUserId the sendUserId to set
	 */
	public void setSendUserId(String sendUserId) {
		this.sendUserId = sendUserId;
	}
	/**
	 * <pre>
	 * sendTime的取得
	 * </pre>
	 * @return sendTime
	 */
	public Date getSendTime() {
		return sendTime;
	}
	/**
	 * <pre>
	 * sendTime的设置
	 * </pre>
	 * @param sendTime the sendTime to set
	 */
	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}
	
	/**
	 * <pre>
	 * workItemID的取得
	 * </pre>
	 * @return workItemID
	 */
	public long getWorkItemID() {
		return workItemID;
	}
	/**
	 * <pre>
	 * workItemID的设置
	 * </pre>
	 * @param workItemID the workItemID to set
	 */
	public void setWorkItemID(long workItemID) {
		this.workItemID = workItemID;
	}
	/**
	 * <pre>
	 * workItemName的取得
	 * </pre>
	 * @return workItemName
	 */
	public String getWorkItemName() {
		return workItemName;
	}
	/**
	 * <pre>
	 * workItemName的设置
	 * </pre>
	 * @param workItemName the workItemName to set
	 */
	public void setWorkItemName(String workItemName) {
		this.workItemName = workItemName;
	}
	/**
	 * <pre>
	 * workItemType的取得
	 * </pre>
	 * @return workItemType
	 */
	public String getWorkItemType() {
		return workItemType;
	}
	/**
	 * <pre>
	 * workItemType的设置
	 * </pre>
	 * @param workItemType the workItemType to set
	 */
	public void setWorkItemType(String workItemType) {
		this.workItemType = workItemType;
	}
	/**
	 * <pre>
	 * workItemDesc的取得
	 * </pre>
	 * @return workItemDesc
	 */
	public String getWorkItemDesc() {
		return workItemDesc;
	}
	/**
	 * <pre>
	 * workItemDesc的设置
	 * </pre>
	 * @param workItemDesc the workItemDesc to set
	 */
	public void setWorkItemDesc(String workItemDesc) {
		this.workItemDesc = workItemDesc;
	}
	/**
	 * <pre>
	 * currentState的取得
	 * </pre>
	 * @return currentState
	 */
	public int getCurrentState() {
		return currentState;
	}
	/**
	 * <pre>
	 * currentState的设置
	 * </pre>
	 * @param currentState the currentState to set
	 */
	public void setCurrentState(int currentState) {
		this.currentState = currentState;
	}
	/**
	 * <pre>
	 * partiName的取得
	 * </pre>
	 * @return partiName
	 */
	public String getPartiName() {
		return partiName;
	}
	/**
	 * <pre>
	 * partiName的设置
	 * </pre>
	 * @param partiName the partiName to set
	 */
	public void setPartiName(String partiName) {
		this.partiName = partiName;
	}
	/**
	 * <pre>
	 * priority的取得
	 * </pre>
	 * @return priority
	 */
	public int getPriority() {
		return priority;
	}
	/**
	 * <pre>
	 * priority的设置
	 * </pre>
	 * @param priority the priority to set
	 */
	public void setPriority(int priority) {
		this.priority = priority;
	}
	/**
	 * <pre>
	 * isTimeOut的取得
	 * </pre>
	 * @return isTimeOut
	 */
	public String getIsTimeOut() {
		return isTimeOut;
	}
	/**
	 * <pre>
	 * isTimeOut的设置
	 * </pre>
	 * @param isTimeOut the isTimeOut to set
	 */
	public void setIsTimeOut(String isTimeOut) {
		this.isTimeOut = isTimeOut;
	}
	/**
	 * <pre>
	 * limitNum的取得
	 * </pre>
	 * @return limitNum
	 */
	public long getLimitNum() {
		return limitNum;
	}
	/**
	 * <pre>
	 * limitNum的设置
	 * </pre>
	 * @param limitNum the limitNum to set
	 */
	public void setLimitNum(long limitNum) {
		this.limitNum = limitNum;
	}
	/**
	 * <pre>
	 * limitNumDesc的取得
	 * </pre>
	 * @return limitNumDesc
	 */
	public String getLimitNumDesc() {
		return limitNumDesc;
	}
	/**
	 * <pre>
	 * limitNumDesc的设置
	 * </pre>
	 * @param limitNumDesc the limitNumDesc to set
	 */
	public void setLimitNumDesc(String limitNumDesc) {
		this.limitNumDesc = limitNumDesc;
	}
	/**
	 * <pre>
	 * createTime的取得
	 * </pre>
	 * @return createTime
	 */
	public String getCreateTime() {
		return createTime;
	}
	/**
	 * <pre>
	 * createTime的设置
	 * </pre>
	 * @param createTime the createTime to set
	 */
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	/**
	 * <pre>
	 * startTime的取得
	 * </pre>
	 * @return startTime
	 */
	public String getStartTime() {
		return startTime;
	}
	/**
	 * <pre>
	 * startTime的设置
	 * </pre>
	 * @param startTime the startTime to set
	 */
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	/**
	 * <pre>
	 * endTime的取得
	 * </pre>
	 * @return endTime
	 */
	public String getEndTime() {
		return endTime;
	}
	/**
	 * <pre>
	 * endTime的设置
	 * </pre>
	 * @param endTime the endTime to set
	 */
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	/**
	 * <pre>
	 * finalTime的取得
	 * </pre>
	 * @return finalTime
	 */
	public String getFinalTime() {
		return finalTime;
	}
	/**
	 * <pre>
	 * finalTime的设置
	 * </pre>
	 * @param finalTime the finalTime to set
	 */
	public void setFinalTime(String finalTime) {
		this.finalTime = finalTime;
	}
	/**
	 * <pre>
	 * remindTime的取得
	 * </pre>
	 * @return remindTime
	 */
	public String getRemindTime() {
		return remindTime;
	}
	/**
	 * <pre>
	 * remindTime的设置
	 * </pre>
	 * @param remindTime the remindTime to set
	 */
	public void setRemindTime(String remindTime) {
		this.remindTime = remindTime;
	}
	/**
	 * <pre>
	 * actionURL的取得
	 * </pre>
	 * @return actionURL
	 */
	public String getActionURL() {
		return actionURL;
	}
	/**
	 * <pre>
	 * actionURL的设置
	 * </pre>
	 * @param actionURL the actionURL to set
	 */
	public void setActionURL(String actionURL) {
		this.actionURL = actionURL;
	}
	/**
	 * <pre>
	 * processInstID的取得
	 * </pre>
	 * @return processInstID
	 */
	public long getProcessInstID() {
		return processInstID;
	}
	/**
	 * <pre>
	 * processInstID的设置
	 * </pre>
	 * @param processInstID the processInstID to set
	 */
	public void setProcessInstID(long processInstID) {
		this.processInstID = processInstID;
	}
	/**
	 * <pre>
	 * activityInstID的取得
	 * </pre>
	 * @return activityInstID
	 */
	public long getActivityInstID() {
		return activityInstID;
	}
	/**
	 * <pre>
	 * activityInstID的设置
	 * </pre>
	 * @param activityInstID the activityInstID to set
	 */
	public void setActivityInstID(long activityInstID) {
		this.activityInstID = activityInstID;
	}
	/**
	 * <pre>
	 * statesList的取得
	 * </pre>
	 * @return statesList
	 */
	public String getStatesList() {
		return statesList;
	}
	/**
	 * <pre>
	 * statesList的设置
	 * </pre>
	 * @param statesList the statesList to set
	 */
	public void setStatesList(String statesList) {
		this.statesList = statesList;
	}
	/**
	 * <pre>
	 * timeOutNum的取得
	 * </pre>
	 * @return timeOutNum
	 */
	public int getTimeOutNum() {
		return timeOutNum;
	}
	/**
	 * <pre>
	 * timeOutNum的设置
	 * </pre>
	 * @param timeOutNum the timeOutNum to set
	 */
	public void setTimeOutNum(int timeOutNum) {
		this.timeOutNum = timeOutNum;
	}
	/**
	 * <pre>
	 * timeOutNumDesc的取得
	 * </pre>
	 * @return timeOutNumDesc
	 */
	public String getTimeOutNumDesc() {
		return timeOutNumDesc;
	}
	/**
	 * <pre>
	 * timeOutNumDesc的设置
	 * </pre>
	 * @param timeOutNumDesc the timeOutNumDesc to set
	 */
	public void setTimeOutNumDesc(String timeOutNumDesc) {
		this.timeOutNumDesc = timeOutNumDesc;
	}
	/**
	 * <pre>
	 * actionMask的取得
	 * </pre>
	 * @return actionMask
	 */
	public String getActionMask() {
		return actionMask;
	}
	/**
	 * <pre>
	 * actionMask的设置
	 * </pre>
	 * @param actionMask the actionMask to set
	 */
	public void setActionMask(String actionMask) {
		this.actionMask = actionMask;
	}
	/**
	 * <pre>
	 * processInstName的取得
	 * </pre>
	 * @return processInstName
	 */
	public String getProcessInstName() {
		return processInstName;
	}
	/**
	 * <pre>
	 * processInstName的设置
	 * </pre>
	 * @param processInstName the processInstName to set
	 */
	public void setProcessInstName(String processInstName) {
		this.processInstName = processInstName;
	}
	/**
	 * <pre>
	 * activityInstName的取得
	 * </pre>
	 * @return activityInstName
	 */
	public String getActivityInstName() {
		return activityInstName;
	}
	/**
	 * <pre>
	 * activityInstName的设置
	 * </pre>
	 * @param activityInstName the activityInstName to set
	 */
	public void setActivityInstName(String activityInstName) {
		this.activityInstName = activityInstName;
	}
	/**
	 * <pre>
	 * processDefID的取得
	 * </pre>
	 * @return processDefID
	 */
	public long getProcessDefID() {
		return processDefID;
	}
	/**
	 * <pre>
	 * processDefID的设置
	 * </pre>
	 * @param processDefID the processDefID to set
	 */
	public void setProcessDefID(long processDefID) {
		this.processDefID = processDefID;
	}
	/**
	 * <pre>
	 * processDefName的取得
	 * </pre>
	 * @return processDefName
	 */
	public String getProcessDefName() {
		return processDefName;
	}
	/**
	 * <pre>
	 * processDefName的设置
	 * </pre>
	 * @param processDefName the processDefName to set
	 */
	public void setProcessDefName(String processDefName) {
		this.processDefName = processDefName;
	}
	/**
	 * <pre>
	 * processChName的取得
	 * </pre>
	 * @return processChName
	 */
	public String getProcessChName() {
		return processChName;
	}
	/**
	 * <pre>
	 * processChName的设置
	 * </pre>
	 * @param processChName the processChName to set
	 */
	public void setProcessChName(String processChName) {
		this.processChName = processChName;
	}
	/**
	 * <pre>
	 * activityDefID的取得
	 * </pre>
	 * @return activityDefID
	 */
	public String getActivityDefID() {
		return activityDefID;
	}
	/**
	 * <pre>
	 * activityDefID的设置
	 * </pre>
	 * @param activityDefID the activityDefID to set
	 */
	public void setActivityDefID(String activityDefID) {
		this.activityDefID = activityDefID;
	}
	/**
	 * <pre>
	 * allowAgent的取得
	 * </pre>
	 * @return allowAgent
	 */
	public String getAllowAgent() {
		return allowAgent;
	}
	/**
	 * <pre>
	 * allowAgent的设置
	 * </pre>
	 * @param allowAgent the allowAgent to set
	 */
	public void setAllowAgent(String allowAgent) {
		this.allowAgent = allowAgent;
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
	/**
	 * <pre>
	 * assistantName的取得
	 * </pre>
	 * @return assistantName
	 */
	public String getAssistantName() {
		return assistantName;
	}
	/**
	 * <pre>
	 * assistantName的设置
	 * </pre>
	 * @param assistantName the assistantName to set
	 */
	public void setAssistantName(String assistantName) {
		this.assistantName = assistantName;
	}
	/**
	 * <pre>
	 * urlType的取得
	 * </pre>
	 * @return urlType
	 */
	public String getUrlType() {
		return urlType;
	}
	/**
	 * <pre>
	 * urlType的设置
	 * </pre>
	 * @param urlType the urlType to set
	 */
	public void setUrlType(String urlType) {
		this.urlType = urlType;
	}
	/**
	 * <pre>
	 * rootProcInstID的取得
	 * </pre>
	 * @return rootProcInstID
	 */
	public long getRootProcInstID() {
		return rootProcInstID;
	}
	/**
	 * <pre>
	 * rootProcInstID的设置
	 * </pre>
	 * @param rootProcInstID the rootProcInstID to set
	 */
	public void setRootProcInstID(long rootProcInstID) {
		this.rootProcInstID = rootProcInstID;
	}
	/**
	 * <pre>
	 * participant的取得
	 * </pre>
	 * @return participant
	 */
	public String getParticipant() {
		return participant;
	}
	/**
	 * <pre>
	 * participant的设置
	 * </pre>
	 * @param participant the participant to set
	 */
	public void setParticipant(String participant) {
		this.participant = participant;
	}
	/**
	 * <pre>
	 * assistant的取得
	 * </pre>
	 * @return assistant
	 */
	public String getAssistant() {
		return assistant;
	}
	/**
	 * <pre>
	 * assistant的设置
	 * </pre>
	 * @param assistant the assistant to set
	 */
	public void setAssistant(String assistant) {
		this.assistant = assistant;
	}
	/**
	 * <pre>
	 * bizObject的取得
	 * </pre>
	 * @return bizObject
	 */
	public Map getBizObject() {
		return bizObject;
	}
	/**
	 * <pre>
	 * bizObject的设置
	 * </pre>
	 * @param bizObject the bizObject to set
	 */
	public void setBizObject(Map bizObject) {
		this.bizObject = bizObject;
	}
	/**
	 * <pre>
	 * catalogUUID的取得
	 * </pre>
	 * @return catalogUUID
	 */
	public String getCatalogUUID() {
		return catalogUUID;
	}
	/**
	 * <pre>
	 * catalogUUID的设置
	 * </pre>
	 * @param catalogUUID the catalogUUID to set
	 */
	public void setCatalogUUID(String catalogUUID) {
		this.catalogUUID = catalogUUID;
	}
	/**
	 * <pre>
	 * catalogName的取得
	 * </pre>
	 * @return catalogName
	 */
	public String getCatalogName() {
		return catalogName;
	}
	/**
	 * <pre>
	 * catalogName的设置
	 * </pre>
	 * @param catalogName the catalogName to set
	 */
	public void setCatalogName(String catalogName) {
		this.catalogName = catalogName;
	}
	/**
	 * <pre>
	 * dealOpinion的取得
	 * </pre>
	 * @return dealOpinion
	 */
	public String getDealOpinion() {
		return dealOpinion;
	}
	/**
	 * <pre>
	 * dealOpinion的设置
	 * </pre>
	 * @param dealOpinion the dealOpinion to set
	 */
	public void setDealOpinion(String dealOpinion) {
		this.dealOpinion = dealOpinion;
	}
	/**
	 * <pre>
	 * dealResult的取得
	 * </pre>
	 * @return dealResult
	 */
	public String getDealResult() {
		return dealResult;
	}
	/**
	 * <pre>
	 * dealResult的设置
	 * </pre>
	 * @param dealResult the dealResult to set
	 */
	public void setDealResult(String dealResult) {
		this.dealResult = dealResult;
	}
	/**
	 * <pre>
	 * participants的取得
	 * </pre>
	 * @return participants
	 */
	public List getParticipants() {
		return participants;
	}
	/**
	 * <pre>
	 * participants的设置
	 * </pre>
	 * @param participants the participants to set
	 */
	public void setParticipants(List participants) {
		this.participants = participants;
	}
	public String getParam1() {
		return param1;
	}
	public void setParam1(String param1) {
		this.param1 = param1;
	}
	public String getParam2() {
		return param2;
	}
	public void setParam2(String param2) {
		this.param2 = param2;
	}
	public String getParam3() {
		return param3;
	}
	public void setParam3(String param3) {
		this.param3 = param3;
	}
	public String getParam4() {
		return param4;
	}
	public void setParam4(String param4) {
		this.param4 = param4;
	}
	public String getParam5() {
		return param5;
	}
	public void setParam5(String param5) {
		this.param5 = param5;
	}
      
}
