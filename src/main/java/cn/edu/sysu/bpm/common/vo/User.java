package cn.edu.sysu.bpm.common.vo;

import java.io.Serializable;

public class User implements Serializable {
	private static final long serialVersionUID = 1L;
	private String userId;
	private String userName;
	private String userDeptId;
	private String userDept;
	private String userCode;
	private String userDeptIdPath;
	private String userDeptPath;
	private String mobile;
	private String areaId;
	private String participantMemo;

	@Deprecated
	public String getUserCode() {
		return userCode;
	}
	@Deprecated
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserDept() {
		return userDept;
	}
	public void setUserDept(String userDept) {
		this.userDept = userDept;
	}
	public String getUserDeptId() {
		return userDeptId;
	}
	public void setUserDeptId(String userDeptId) {
		this.userDeptId = userDeptId;
	}
	public String getUserDeptIdPath() {
		return userDeptIdPath;
	}
	public void setUserDeptIdPath(String userDeptIdPath) {
		this.userDeptIdPath = userDeptIdPath;
	}
	public String getUserDeptPath() {
		return userDeptPath;
	}
	public void setUserDeptPath(String userDeptPath) {
		this.userDeptPath = userDeptPath;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getAreaId() {
		return areaId;
	}
	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}
	public String getParticipantMemo() {
		return participantMemo;
	}
	public void setParticipantMemo(String participantMemo) {
		this.participantMemo = participantMemo;
	}
	
}
