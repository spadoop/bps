package com.kingmed.pl.common.vo;

import java.util.Date;

/**
 * 待办已办查询的条件
 * 
 * @author dxun
 *
 */
public class SearchTaskVO {

	/**
	 * 业务类型
	 */
	private String bizType;
	/**
	 * 需求类型
	 */
	private String workOrderType;
	/**
	 * 工单类型
	 */
	private String reqType;

	/**
	 * 工单类型: 处理中-Constant.FORM_STATUS_ONGOING 已完成-Constant.FORM_STATUS_COMPLETE
	 */
	private String formStatus;

	/**
	 * 工单主题
	 */
	private String formTitle;

	/**
	 * 工单流水号
	 */
	private String formSeq;

	/**
	 * 派发人员
	 */
	private String sendUserId;

	/**
	 * 派发时间-开始
	 */
	private Date fromSendTime;

	/**
	 * 派发时间-结束
	 */
	private Date toSendTime;

	/**
	 * 处理时间-开始
	 */
	private Date fromOptTime;
	
	/**
	 * 处理时间-结束
	 */
	private Date toOptTime;

	/**
	 * 是否超时 
	 * ALL-所有
	 * Y-是 
	 * N-否
	 */
	private String isTimeOut;

	/**
	 * ALL-所有（默认）
	 * SENT - 我派发的
	 * PROCESS - 我处理的
	 */
	private String scope = "ALL";

	public String getFormTitle() {
		return formTitle;
	}

	public void setFormTitle(String formTitle) {
		this.formTitle = formTitle;
	}

	public String getFormSeq() {
		return formSeq;
	}

	public void setFormSeq(String formSeq) {
		this.formSeq = formSeq;
	}

	public String getSendUserId() {
		return sendUserId;
	}

	public void setSendUserId(String sendUserId) {
		this.sendUserId = sendUserId;
	}

	public Date getFromSendTime() {
		return fromSendTime;
	}

	public void setFromSendTime(Date fromSendTime) {
		this.fromSendTime = fromSendTime;
	}

	public Date getToSendTime() {
		return toSendTime;
	}

	public void setToSendTime(Date toSendTime) {
		this.toSendTime = toSendTime;
	}

	public String getReqType() {
		return reqType;
	}

	public void setReqType(String reqType) {
		this.reqType = reqType;
	}
	

	public String getBizType() {
		return bizType;
	}

	public void setBizType(String bizType) {
		this.bizType = bizType;
	}

	public String getWorkOrderType() {
		return workOrderType;
	}

	public void setWorkOrderType(String workOrderType) {
		this.workOrderType = workOrderType;
	}

	public Date getFromOptTime() {
		return fromOptTime;
	}

	public void setFromOptTime(Date fromOptTime) {
		this.fromOptTime = fromOptTime;
	}

	public Date getToOptTime() {
		return toOptTime;
	}

	public void setToOptTime(Date toOptTime) {
		this.toOptTime = toOptTime;
	}

	public String getFormStatus() {
		return formStatus;
	}

	public void setFormStatus(String formStatus) {
		this.formStatus = formStatus;
	}

	public String getIsTimeOut() {
		return isTimeOut;
	}

	public void setIsTimeOut(String isTimeOut) {
		this.isTimeOut = isTimeOut;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}
}
