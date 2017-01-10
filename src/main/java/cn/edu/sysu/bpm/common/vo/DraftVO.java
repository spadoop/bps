package cn.edu.sysu.bpm.common.vo;

import java.util.Date;

public class DraftVO {

	/**
	 * Form Main ID
	 */
	private String formMainID;

	private String workOrderNo;

	private String workOrderTitle;

	/**
	 * 派发人员
	 */
	private String senderID;

	/**
	 * 派发部门
	 */
	private String senderDeptID;

	/**
	 * 建单时间
	 */
	private Date sendTime;
	private String reqType;

	public String getFormMainID() {
		return formMainID;
	}

	public void setFormMainID(String mainID) {
		this.formMainID = mainID;
	}

	public String getWorkOrderNo() {
		return workOrderNo;
	}

	public void setWorkOrderNo(String workOrderNo) {
		this.workOrderNo = workOrderNo;
	}

	public String getWorkOrderTitle() {
		return workOrderTitle;
	}

	public void setWorkOrderTitle(String workOrderTitle) {
		this.workOrderTitle = workOrderTitle;
	}

	public String getSenderID() {
		return senderID;
	}

	public void setSenderID(String senderID) {
		this.senderID = senderID;
	}

	public Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	public String getSenderDeptID() {
		return senderDeptID;
	}

	public void setSenderDeptID(String senderDeptID) {
		this.senderDeptID = senderDeptID;
	}

	/**
	 * <pre>
	 * reqType的取得
	 * </pre>
	 * 
	 * @return reqType
	 */
	public String getReqType() {
		return reqType;
	}

	/**
	 * <pre>
	 * reqType的设置
	 * </pre>
	 * 
	 * @param reqType
	 *            the reqType to set
	 */
	public void setReqType(String reqType) {
		this.reqType = reqType;
	}

}
