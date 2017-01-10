/* ========================================
 * System Name　　：DGCUVM 
 * SubSystem Name ：DGCUVM System
 * ----------------------------------------
 * Create Date/Change History 
 *
 * ----------------------------------------
 * 2014年1月15日 　Hu Shaolin   Create
 * 
 * 
 * ----------------------------------------
 * Copyright 2013 Global Delivery Center,Deloitte Consulting All Rights Reserved.
 */
package com.kingmed.pl.common.vo;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.eos.workflow.omservice.WFParticipant;

/**
 * <pre>
 *  class名
 *  class流程相关信息VO
 * </pre>
 * 
 * @author caozhongyi
 * @version 1.00
 */
public class WfVo implements Serializable{

	private static final long serialVersionUID = 5644337856656292934L;
	

	// 流程实例ID
	private long prcsInstId;
	// 流程定义名称
	private String processDefName;
	// 流程定义名称
	private String processChName;
	// 流程定义描述
	private String processDescription;

	// 环节定义
	private String activityDefID;
	// 环节实例ID
	private long activityInstId;
	// 环节实例名称
	private String activityInsName;

	// 工作项ID
	private long workitemId;

	// 下一环节参与者
	private List<WFParticipant> nextParticipant;
	private String status;

	// 操作flag:待处理时 0;已处理 1
	private String doFlag;

	// 环节操作标志,如：aduitPass、aduitReject、busiSubmitAduit、deptSign等
	private String checkFlag;
	private String optType;
	
	private String dealOpinion;
	
	private String dealResult;
	
	public String getDealOpinion() {
		return dealOpinion;
	}

	public void setDealOpinion(String dealOpinion) {
		this.dealOpinion = dealOpinion;
	}

	public String getDealResult() {
		return dealResult;
	}

	public void setDealResult(String dealResult) {
		this.dealResult = dealResult;
	}

	/**  
	 * 返回 checkFlag 的值   
	 * @return checkFlag  
	 */
	
	public String getCheckFlag() {
		return checkFlag;
	}

	/**  
	 * 设置 checkFlag 的值  
	 * @param checkFlag
	 */
	
	public void setCheckFlag(String checkFlag) {
		this.checkFlag = checkFlag;
	}

	/**  
	 * 返回 optType 的值   
	 * @return optType  
	 */
	
	public String getOptType() {
		return optType;
	}

	/**  
	 * 设置 optType 的值  
	 * @param optType
	 */
	
	public void setOptType(String optType) {
		this.optType = optType;
	}

	/**  
	 * 设置 nextParticipant 的值  
	 * @param nextParticipant
	 */
	
	public void setNextParticipant(List<WFParticipant> nextParticipant) {
		this.nextParticipant = nextParticipant;
	}

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


	/**
	 * <pre>
	 * prcsInstId的取得
	 * </pre>
	 * 
	 * @return prcsInstId
	 */
	public long getPrcsInstId() {
		return prcsInstId;
	}

	/**
	 * <pre>
	 * prcsInstId的设置
	 * </pre>
	 * 
	 * @param prcsInstId
	 *            the prcsInstId to set
	 */
	public void setPrcsInstId(long prcsInstId) {
		this.prcsInstId = prcsInstId;
	}

	/**
	 * <pre>
	 * processDefName的取得
	 * </pre>
	 * 
	 * @return processDefName
	 */
	public String getProcessDefName() {
		return processDefName;
	}

	/**
	 * <pre>
	 * processDefName的设置
	 * </pre>
	 * 
	 * @param processDefName
	 *            the processDefName to set
	 */
	public void setProcessDefName(String processDefName) {
		this.processDefName = processDefName;
	}

	/**
	 * <pre>
	 * processChName的取得
	 * </pre>
	 * 
	 * @return processChName
	 */
	public String getProcessChName() {
		return processChName;
	}

	/**
	 * <pre>
	 * processChName的设置
	 * </pre>
	 * 
	 * @param processChName
	 *            the processChName to set
	 */
	public void setProcessChName(String processChName) {
		this.processChName = processChName;
	}

	/**
	 * <pre>
	 * processDescription的取得
	 * </pre>
	 * 
	 * @return processDescription
	 */
	public String getProcessDescription() {
		return processDescription;
	}

	/**
	 * <pre>
	 * processDescription的设置
	 * </pre>
	 * 
	 * @param processDescription
	 *            the processDescription to set
	 */
	public void setProcessDescription(String processDescription) {
		this.processDescription = processDescription;
	}

	/**
	 * <pre>
	 * activityInstId的取得
	 * </pre>
	 * 
	 * @return activityInstId
	 */
	public long getActivityInstId() {
		return activityInstId;
	}

	/**
	 * <pre>
	 * activityInstId的设置
	 * </pre>
	 * 
	 * @param activityInstId
	 *            the activityInstId to set
	 */
	public void setActivityInstId(long activityInstId) {
		this.activityInstId = activityInstId;
	}

	/**
	 * <pre>
	 * activityInsName的取得
	 * </pre>
	 * 
	 * @return activityInsName
	 */
	public String getActivityInsName() {
		return activityInsName;
	}

	/**
	 * <pre>
	 * activityInsName的设置
	 * </pre>
	 * 
	 * @param activityInsName
	 *            the activityInsName to set
	 */
	public void setActivityInsName(String activityInsName) {
		this.activityInsName = activityInsName;
	}

	/**
	 * <pre>
	 * workitemId的取得
	 * </pre>
	 * 
	 * @return workitemId
	 */
	public long getWorkitemId() {
		return workitemId;
	}

	/**
	 * <pre>
	 * workitemId的设置
	 * </pre>
	 * 
	 * @param workitemId
	 *            the workitemId to set
	 */
	public void setWorkitemId(long workitemId) {
		this.workitemId = workitemId;
	}

	/**
	 * <pre>
	 * nextParticipant的取得
	 * </pre>
	 * 
	 * @return nextParticipant
	 */
	public List<WFParticipant> getNextParticipant() {
		return this.nextParticipant;
	}


	/**
	 * <pre>
	 * status的取得
	 * </pre>
	 * 
	 * @return status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * <pre>
	 * status的设置
	 * </pre>
	 * 
	 * @param status
	 *            the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * <pre>
	 * doFlag的取得
	 * </pre>
	 * 
	 * @return doFlag
	 */
	public String getDoFlag() {
		return doFlag;
	}

	/**
	 * <pre>
	 * doFlag的设置
	 * </pre>
	 * 
	 * @param doFlag
	 *            the doFlag to set
	 */
	public void setDoFlag(String doFlag) {
		this.doFlag = doFlag;
	}

	/**
	 * <pre>
	 * activityDefID的取得
	 * </pre>
	 * 
	 * @return activityDefID
	 */
	public String getActivityDefID() {
		return activityDefID;
	}

	/**
	 * <pre>
	 * activityDefID的设置
	 * </pre>
	 * 
	 * @param activityDefID
	 *            the activityDefID to set
	 */
	public void setActivityDefID(String activityDefID) {
		this.activityDefID = activityDefID;
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

}
