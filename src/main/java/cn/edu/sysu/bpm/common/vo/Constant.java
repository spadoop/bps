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
package cn.edu.sysu.bpm.common.vo;


/**
 * <pre>
 *  class名
 *  class功能说明
 * </pre>
 * 
 * @author Hu Shaolin
 * @version 1.00
 */
public interface Constant {

	// 工单状态： 1：草稿
	public static final String FORM_STATUS_DRAFT = "1";
	// 工单状态： 2：运行中
	public static final String FORM_STATUS_ONGOING = "2";
	// 工单状态： 3：归档
	public static final String FORM_STATUS_ARCHIVE = "3";
	// 工单状态： 4：撤销
	public static final String FORM_STATUS_CANCEL = "4";
	// 工单状态： 5：删除
	public static final String FORM_STATUS_DELETE = "5";
	// 工单状态： 6：挂起
	public static final String FORM_STATUS_HANGUP = "6";
	// 工单状态： 10：完成
	public static final String FORM_STATUS_COMPLETE = "10";

	// 工单业务表名
	public static final String TABLE_NAME_T_FORM_PRESALES = "T_FORM_PRESALES";

	// 工单业务表名--资源核查
	public static final String TABLE_NAME_T_FORM_RESCHECK = "T_FORM_RESCHECK";

	// 处理类型：提交申请
	public static final String OPT_TYPE_APPLY = "提交申请";

	public static final Integer DELETE_FLG_FALSE = 0;
	public static final Integer DELETE_FLG_TRUE = 1;

	// 流程相关数据区关键字
	public static final String RELATIVE_KEY = "_relative_data";
	public static final String RELADATA_KEY_OBJS = "objs";
	public static final String RELADATA_KEY_OBJFB = "objfb";
	public static final String RELADATA_KEY_OBJSFB = "objsfb";
	public static final String RELADATA_KEY_CHECKFLG = "checkFlag";
	public static final String RELADATA_KEY_FLAG = "flag";
	public static final String RELADATA_KEY_FORMMAINID = "formMainId";
	public static final String RELADATA_KEY_FORMSEQ = "formSeq";
	public static final String RELADATA_KEY_FORMTITLE = "formTitle";
	public static final String RELADATA_KEY_SENDUSERID = "sendUserId";
	public static final String RELADATA_KEY_SENDTIME = "sendTime";
	public static final String RELADATA_KEY_URL = "url";
	public static final String RELADATA_KEY_ISSUB = "isSub";
	public static final String RELADATA_KEY_NEXTPARTICIPANT = "nextParticipant";
	public static final String RELADATA_KEY_STARTPARTICIPANT = "startParticipant";
	
	public static final String RELADATA_KEY_SUB_CHECKFLG = "subCheckFlag";
	public static final String RELADATA_KEY_SUB_FORMMAINID = "subFormMainId";
	public static final String RELADATA_KEY_SUB_FORMSEQ = "subFormSeq";
	public static final String RELADATA_KEY_SUB_FORMTITLE = "subFormTitle";
	public static final String RELADATA_KEY_SUB_SENDUSERID = "subSendUserId";
	public static final String RELADATA_KEY_SUB_SENDTIME = "subSendTime";
	public static final String RELADATA_KEY_SUB_URL = "subUrl";
	public static final String RELADATA_KEY_SUB_ISSUB = "subIsSub";
	public static final String RELADATA_KEY_AREA = "area";
	//传递流程启动者到子流程
	public static final String RELADATA_KEY_SUB_STARTPARTICIPANT = "subStartParticipant";
	//传递子流程中默认的变量参与者，如果子流程中某些环节与默认值不一样，则需要单独设置
	public static final String RELADATA_KEY_SUB_NEXTPARTICIPANT = "subNextParticipant";
	
	public static final String DRAFT_NODE_DEFID = "DraftActivity";
	
	
	public static final int INT_1 = 1;
	public static final int INT_0 = 0;
	// TODO 需要确认 资源核查URL
	public static final String RESCHECK_DETAIL_URL = "/resCheck/getDetail";

	public static final String SLA_SUFFIX="Limit";
	
	//定时任务的设置(单位：秒)
	//定时任务在启动后延迟多长时间开始执行
	public static final int SCHEDULE_DELAY = 60 * 30;
	//定时任务每次执行之间的间隔
	public static final int SCHEDULE_INTERVAL = 60 * 30;

	public static final String BACK_STRATEGY_SIMPLE = "simple";
	public static final String BACK_STRATEGY_PATH = "path";
	public static final String BACK_STRATEGY_RECENT_MANUAL = "recent_manual";
	
	public static final String VIRTUAL_POSTION = "virtualPostion";
	public static final String COMPANY = "company";
	public static final String ROLE = "role";
	public static final String POSITION = "position";
	public static final String ORGANIZATION = "organization";
	public static final String PERSON = "person";
}
