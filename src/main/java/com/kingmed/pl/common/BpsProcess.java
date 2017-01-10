/**
 * 
 */
package com.kingmed.pl.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import cn.com.deloitte.si.bpm.service.IBaseBpsInterfaceMgr;
import cn.com.deloitte.si.bpm.service.impl.BaseBpsInterfaceMgrImpl;

import com.eos.das.entity.DASManager;
import com.eos.das.entity.ExpressionHelper;
import com.eos.das.entity.IDASCriteria;
import com.eos.workflow.api.BPSServiceClientFactory;
import com.eos.workflow.api.IBPSServiceClient;
import com.eos.workflow.api.IWFQueryManager;
import com.eos.workflow.api.IWFWorkItemManager;
import com.eos.workflow.data.WFActivityInst;
import com.eos.workflow.data.WFWorkItem;
import com.eos.workflow.omservice.WFParticipant;
import com.eos.workflow.omservice.WIParticipantInfo;
import com.kingmed.pl.common.vo.BizInfoVo;
import com.kingmed.pl.common.vo.Constant;
import com.kingmed.pl.common.vo.User;
import com.kingmed.pl.common.vo.WfVo;
import com.primeton.workflow.api.PageCond;
import com.primeton.workflow.api.WFReasonableException;
import com.primeton.workflow.api.WFServiceException;
/**
 * Bps 的管理类 包括启动流程，终止流程，结束流程
 * 
 * @author Zhong An-Jing
 *   
 */
public class BpsProcess {
	static final String BIZSTATE_REJECT = "REJECT";
	static final String BIZSTATE_DELEGATE = "DELEGATE";
	
	/**
	 * 管理类
	 */
	IBaseBpsInterfaceMgr bpsMgr;

	/**
	 * 当前处理人
	 */
	User user;

	/**
	 * 流程配置信息
	 */
	WfVo wfVo;

	/**
	 * 流程数据
	 */
	BizInfoVo biVo;

	/**  
	 * 返回 biVo 的值   
	 * @return biVo  
	 */
	
	public BizInfoVo getBiVo() {
		return biVo;
	}

	/**  
	 * 设置 biVo 的值  
	 * @param biVo
	 */
	
	public void setBiVo(BizInfoVo biVo) {
		this.biVo = biVo;
	}

	public BpsProcess(User user, WfVo wfVo, BizInfoVo biVo) {
		super();
		this.user = user;
		this.wfVo = wfVo;
		this.biVo = biVo;
		if (bpsMgr == null)
			bpsMgr = new BaseBpsInterfaceMgrImpl(user.getUserId(),
					user.getUserName());
	}

	public BpsProcess(IBaseBpsInterfaceMgr bpsMgr, User user, WfVo wfVo, BizInfoVo biVo) {
		super();
		this.bpsMgr = bpsMgr;
		this.user = user;
		this.wfVo = wfVo;
		this.biVo = biVo;
		if (this.bpsMgr == null)
			this.bpsMgr = new BaseBpsInterfaceMgrImpl(user.getUserId(),
					user.getUserName());
	}
	
	/**
	 * 修改流程实例名称
	 * @param processId
	 * @param newName
	 * @param user
	 * @throws WFServiceException
	 */
	public static void changeProcessName(long processId, String newName, User user) throws WFServiceException{
		BpsProcess bps = new BpsProcess(  user,   null,   null);
		bps.changeProcessName(processId, newName);
	}
	
	/**
	 * 修改流程实例名称
	 * @param processId
	 * @param newName
	 * @throws WFServiceException
	 */
	private void changeProcessName(long processId, String newName) throws WFServiceException{
		bpsMgr.changeProcessName(processId, newName);
	}

	/**
	 * 启动流程
	 * 
	 * @return 0 失败
	 * @throws Exception 
	 * @throws WFServiceException
	 */
	public long start() throws  Exception {
		long pId = 0;
		if (bpsMgr == null)
			bpsMgr = new BaseBpsInterfaceMgrImpl("tiger",
					"tiger");

		Map<String, Object> relaData = new HashMap<String, Object>();
		WFParticipant[] nextpart = null;
		
		if(getWfVo().getNextParticipant()!=null)
			nextpart = getWfVo().getNextParticipant().toArray(new WFParticipant[0]);
		
		relaData.put(Constant.RELADATA_KEY_NEXTPARTICIPANT, nextpart);
		
		Map<String, Object> bizInfo = new HashMap<String, Object>();
		bizInfo.put("REQ_TYPE", this.biVo.getReqType());
		bizInfo.put("FORM_SEQ", this.biVo.getFormSeq());
		bizInfo.put("FORM_TITLE", this.biVo.getFormTitle());	
		bizInfo.put("SEND_USER_ID", this.biVo.getSendUserId());
		//需要设置成长整形的字符串，便于BPS查询，BPS针对datetime类型的查询有问题
		bizInfo.put("SEND_TIME",  Long.toString(System.currentTimeMillis()));
		bizInfo.put("FORM_STATUS",  this.biVo.getFormStatus());
	
		relaData.put("reqType",biVo.getReqType());
		relaData.put("formSeq",biVo.getFormSeq());
		relaData.put("formTitle",biVo.getFormTitle());
		relaData.put("sendUserId",biVo.getSendUserId());
		relaData.put("sendTime",biVo.getSendTime());
		relaData.put("formStatus",biVo.getFormStatus());
		relaData.put("param1",biVo.getParam1());
		relaData.put("param2",biVo.getParam2());
		relaData.put("param3",biVo.getParam3());
		relaData.put("param4",biVo.getParam4());
		relaData.put("param5",biVo.getParam5());
		
		relaData.put("checkFlag", wfVo.getCheckFlag());
		
		try {
			pId = bpsMgr.startProcessWithBiz(wfVo.getProcessDefName(),
					wfVo.getProcessChName(), wfVo.getProcessDescription(), false,
					new Object[0], 
					nextpart, relaData, "T_FORM_MAIN", bizInfo );
		} catch (WFServiceException e) {			
			if(e.getCause().getMessage().indexOf("cann't find participant")>=0){
				throw new Exception("The user/organization ID does not exist!");
			} else {
				e.printStackTrace();
			}
		} catch (WFReasonableException e){
			throw e;
		}
	
		// 如果在此不设置wfVo对象的流程实例ID，在下一步的设置时限时就使用了0作为实例号，导致永远不会设置时限
		wfVo.setPrcsInstId(pId);
		setLimitTime(false);
		return pId;
	}

	/**
	 * 启动流程-静态调用
	 * 
	 * @return 0 失败
	 * @throws Exception 
	 * @throws WFServiceException
	 */
	public static long start(User user, WfVo wfVo, BizInfoVo biVo) throws Exception {
		BpsProcess bps = new BpsProcess(  user,   wfVo,   biVo);
		return bps.start();
	}
	/**
	 * 保存草稿
	 * 
	 * @return 0 失败
	 * @throws WFServiceException
	 * @throws WFReasonableException 
	 */
	public long saveDraft() throws WFServiceException, WFReasonableException {
		long pId = 0;
		if (bpsMgr == null)
			bpsMgr = new BaseBpsInterfaceMgrImpl("tiger",
					"tiger");		

		Map<String, Object> relaData = new HashMap<String, Object>();
		WFParticipant[] nextpart = null;
		
		if(getWfVo().getNextParticipant()!=null)
			nextpart = getWfVo().getNextParticipant().toArray(new WFParticipant[0]);
		
		relaData.put(Constant.RELADATA_KEY_NEXTPARTICIPANT, nextpart);
		
		Map<String, Object> bizInfo = new HashMap<String, Object>();
		bizInfo.put("REQ_TYPE", this.biVo.getReqType());
		bizInfo.put("FORM_SEQ", this.biVo.getFormSeq());
		bizInfo.put("FORM_TITLE", this.biVo.getFormTitle());	
		bizInfo.put("SEND_USER_ID", this.biVo.getSendUserId());
		//需要设置成整形，便于BPS查询，BPS针对datetime类型的查询有问题
		bizInfo.put("SEND_TIME", Long.toString(System.currentTimeMillis()));
		bizInfo.put("FORM_STATUS",  this.biVo.getFormStatus());
	
		relaData.put("reqType",biVo.getReqType());
		relaData.put("formSeq",biVo.getFormSeq());
		relaData.put("formTitle",biVo.getFormTitle());
		relaData.put("sendUserId",biVo.getSendUserId());
		relaData.put("sendTime",biVo.getSendTime());
		relaData.put("formStatus",biVo.getFormStatus());
		relaData.put("param1",biVo.getParam1());
		relaData.put("param2",biVo.getParam2());
		relaData.put("param3",biVo.getParam3());
		relaData.put("param4",biVo.getParam4());
		relaData.put("param5",biVo.getParam5());
		
		relaData.put("checkFlag", wfVo.getCheckFlag());
		
		pId = bpsMgr.startProcessForDraft(wfVo.getProcessDefName(),
				wfVo.getProcessChName(), wfVo.getProcessDescription(), false,
				null, nextpart, relaData, "T_FORM_MAIN", bizInfo );
	
		// 如果在此不设置wfVo对象的流程实例ID，在下一步的设置时限时就使用了0作为实例号，导致永远不会设置时限
		wfVo.setPrcsInstId(pId);
		return pId;
	}

	/**
	 * 流程存稿-静态调用
	 * 
	 * @return 0 失败
	 * @throws Exception 
	 * @throws WFServiceException
	 */
	public static long saveDraft(User user, WfVo wfVo, BizInfoVo biVo) throws Exception {
		BpsProcess bps = new BpsProcess(  user,   wfVo,   biVo);
		return bps.saveDraft();
	}
	/**
	 * 流程流转
	 * 
	 * @return
	 * @throws WFServiceException
	 * @throws WFReasonableException
	 */
	public long send() throws WFServiceException, WFReasonableException {	
		bpsMgr.setRelativeData(wfVo.getPrcsInstId(),Constant.RELADATA_KEY_CHECKFLG, wfVo.getCheckFlag());
		bpsMgr.submitTask(wfVo.getWorkitemId(), wfVo.getActivityDefID(), true, null, wfVo.getDealOpinion(), wfVo.getDealResult());
		return wfVo.getPrcsInstId();
	}

	/**
	 * 流程审批-静态调用
	 * 
	 * @return 0 失败
	 * @throws Exception 
	 * @throws WFServiceException
	 */
	public static long send(User user, WfVo wfVo, BizInfoVo biVo) throws Exception {
		BpsProcess bps = new BpsProcess(  user,   wfVo,   biVo);
		if("rejectToStart".equals(wfVo.getCheckFlag())) {
			IWFWorkItemManager wim = bps.getBpsMgr().getWorkItemManager();
			wim.modifyWorkItemUrl(wfVo.getWorkitemId(), "O", BpsProcess.BIZSTATE_REJECT);
			if(bps.finishOtherWorkItems(wim)){
				bps.send();
			}
			return 0;
		} else {
			return bps.send();
		}
	}
	
	/**
	 * 工作项转派-静态调用
	 * 
	 * @return 0 失败
	 * @throws Exception 
	 * @throws WFServiceException
	 */
	public static void reassign(User user, WfVo wfVo, String userId ) throws Exception {
		BpsProcess bps = new BpsProcess(  user, wfVo, null);

		IWFWorkItemManager wim = bps.getBpsMgr().getWorkItemManager();
		wim.modifyWorkItemUrl(wfVo.getWorkitemId(), "O", BpsProcess.BIZSTATE_REJECT);
		bps.bpsMgr.getWorkItemManager().reassignWorkItem(wfVo.getWorkitemId(), userId);		
	}
	
	/**
	 * @see 获得相关数据区的值
	 * @param relativeName
	 * @return list
	 * @throws WFServiceException
	 */
	public List<Object> getRelativeData(String relativeName)
			throws WFServiceException {
		@SuppressWarnings("unchecked")
		List<Object> relativeList = (List<Object>) bpsMgr.getRelativeData(
				wfVo.getPrcsInstId(), relativeName);
		return relativeList;
	}

	/**
	 * @see 根据父流程实例id获得相关数据区的值
	 * @param relativeName
	 * @param prcsInstId
	 * @return
	 * @throws WFServiceException
	 * @author zhyoy
	 */
	public Object getRelativeDataByCurrPrcsInstId(String relativeName, long prcsInstId)
			throws WFServiceException {
		Object relativeList = (Object) bpsMgr.getRelativeData(prcsInstId,
				relativeName);
		return relativeList;
	}


	/**
	 * 获取工作项
	 * 
	 * @param id
	 * @return
	 */
	public List<WFWorkItem> queryNextWorkItemsByWorkItemID(long id) {
		List<WFWorkItem> wkItemList = null;
		try {
			wkItemList = bpsMgr.queryNextWorkItemsByWorkItemID(
					wfVo.getWorkitemId(), true);
		} catch (WFServiceException e) {
			e.printStackTrace();
		}

		return wkItemList;
	}

	/**
	 * 设置流程实现，根据发起时限来限制流程实现
	 * 
	 * @param hasStart
	 * @throws WFServiceException
	 */
	public void setLimitTime(boolean hasStart) throws WFServiceException {

		List<WFWorkItem> wkItemList = null;
		if (hasStart)
			wkItemList = bpsMgr.queryNextWorkItemsByWorkItemID(
					wfVo.getWorkitemId(), true);
		else{
			IBPSServiceClient clientNew = BPSServiceClientFactory.getDefaultClient(); 
			IWFQueryManager queryManager = clientNew.getCommonQueryManage(); 
			IDASCriteria criteria = DASManager.createCriteria("com.eos.workflow.data.WFWorkItem"); 
			// activityDefID为WFWorkItem实例的属性名 
			criteria.add(ExpressionHelper.eq("activityDefID", "DraftActivity")); 
			criteria.add(ExpressionHelper.eq("processInstID", wfVo.getPrcsInstId()));		 
			PageCond pageCond = new PageCond(10); 
			List<WFWorkItem> queryWorkItems = queryManager.queryWorkItemsCriteria(criteria, pageCond); 
			if(queryWorkItems.size()>0){
				wkItemList = bpsMgr.queryNextWorkItemsByWorkItemID(queryWorkItems.get(0).getWorkItemID(), true);
			}
		}

	}

	public IBaseBpsInterfaceMgr getBpsMgr() {
		return bpsMgr;
	}

	public void setBpsMgr(IBaseBpsInterfaceMgr bpsMgr) {
		this.bpsMgr = bpsMgr;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public WfVo getWfVo() {
		return wfVo;
	}

	public void setWfVo(WfVo wfVo) {
		this.wfVo = wfVo;
	}

	/**
	 * @see 根据流程实例id设置相关数据区
	 * @param procId
	 * @param str
	 * @param value
	 * @author zhyoy
	 * @throws Exception 
	 */
	public void setRelativeData(long prcsInstId, String key, Object value) throws Exception {
		try {
			bpsMgr.setRelativeData(prcsInstId, key, value);
		} catch (WFServiceException e) {
			e.printStackTrace();
			if(e.getMessage().indexOf("cann't find participant")>=0){
				throw new Exception("The user/organization ID does not exist!");
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
	}
	

	/**
	 * 转派
	 */
	
	public void tranSend() throws WFServiceException, WFReasonableException {
			
		bpsMgr.getWorkItemManager().reassignWorkItemAndUpdateStartTimeEx(wfVo.getWorkitemId(), wfVo.getNextParticipant().toArray(new WFParticipant[0]));
	}

	/**
	 * 插入
	 * 
	 */
	public void insertSend() throws WFServiceException, WFReasonableException {

		bpsMgr.getDelegateManager().delegateWorkItem(wfVo.getWorkitemId(), wfVo.getNextParticipant().toArray(new WFParticipant[0]), "HELP");
	}

	/**
	 * 插入处理
	 * 
	 */
	public WIParticipantInfo insertDeal() throws WFServiceException, WFReasonableException {
		
		bpsMgr.submitTask(wfVo.getWorkitemId(), wfVo.getActivityDefID(), true,
				null, null, null);
		WIParticipantInfo rewp = new WIParticipantInfo();
		List<WIParticipantInfo> ll = bpsMgr.getWorkItemManager().queryWorkItemParticipantInfo(wfVo.getWorkitemId());
		for(int i= 0 ;i< ll.size();i++){
			WIParticipantInfo wp = new WIParticipantInfo();
			wp = ll.get(i);
			if(wp.isCurrParticipant()){
				rewp = wp;
				break;
			}
		}
		return rewp;
	}
	
	/**
	 * 插入确认
	 * 
	 */
	public void insertCfm() throws WFServiceException, WFReasonableException {
		
		bpsMgr.getDelegateManager().confirmDelegatedWorkItem(wfVo.getWorkitemId());
	}

	/**
	 * 退回上一步
	 * 
	 * @author Zhang, Chong-Jun
	 */
	public void rollbackLast(String destActDefID, String rollBackStrategy)
			throws WFServiceException, WFReasonableException {
		
		bpsMgr.getBackActivityManager().backActivity(wfVo.getActivityInstId(),
				destActDefID, rollBackStrategy);
	}

	/**
	 * 退回上一步
	 * 
	 */
	public boolean rollbackDraft(String destActDefID, String rollBackStrategy)
			throws WFServiceException, WFReasonableException {
		List<WFActivityInst> activityInsts = bpsMgr.getActivityInstManager()
				.queryActivityInstsByProcessInstID(wfVo.getPrcsInstId(), null);
		int runningActivityInsts = 0;
		for (WFActivityInst wfActivityInst : activityInsts) {
			if (wfActivityInst.getCurrentState() == 2) {
				runningActivityInsts++;
			}
		}
		if (runningActivityInsts > 1) {// 超过1个的活动在运行，不能单独回退到
			return false;
		}

		// 终止当前的活动
		bpsMgr.getActivityInstManager().terminateActivityInstance(
				wfVo.getActivityInstId());
		WFActivityInst activityInst = bpsMgr.getActivityInstManager()
				.findLastActivityInstByActivityID(wfVo.getPrcsInstId(),
						destActDefID);
		if (activityInst == null) {// 没有则创建并运行草稿
			bpsMgr.getActivityInstManager().createAndStartActivityInstance(
					wfVo.getPrcsInstId(), destActDefID);
		} else { // 有则直接重启
			bpsMgr.getActivityInstManager().restartActivityInstance(
					activityInst.getActivityInstID());
		}
		return false;
	}
	
	/**
	 * 退回发单人
	 * 
	 */
	public boolean rollbackStart(String destActDefID, String rollBackStrategy)
			throws WFServiceException, WFReasonableException {

		List<WFActivityInst> activityInsts = bpsMgr.getActivityInstManager()
				.queryActivityInstsByProcessInstID(wfVo.getPrcsInstId(), null);

		for (WFActivityInst wfActivityInst : activityInsts) {
			if (wfActivityInst.getCurrentState() == 2) {
				bpsMgr.getActivityInstManager().terminateActivityInstance(
						wfActivityInst.getActivityInstID());
			}
		}

		bpsMgr.getActivityInstManager().createAndStartActivityInstance(wfVo.getPrcsInstId(), destActDefID);

		return true;
	}
	/**
	 * 在多方会审中，有一个人驳回，则用此方法移除其他"待领取：4"的工作项  
	 * 
	 */
	public boolean finishOtherWorkItems( IWFWorkItemManager wim )
			throws WFServiceException, WFReasonableException {

  
		WFActivityInst activityInst = bpsMgr.getActivityInstManager()
				.findLastActivityInstByActivityID(wfVo.getPrcsInstId(),
						wfVo.getActivityDefID());
		List<WFWorkItem> wilist=
				wim.queryWorkItemsByActivityInstID(wfVo.getActivityInstId(), new PageCond(10));
		for(WFWorkItem wi:wilist){
			//bpsMgr.getWorkItemManager().finishWorkItem(wi.getWorkItemID(), true);
			if((wi.getCurrentState()==4 || wi.getCurrentState()==10) 
					&& wi.getWorkItemID()!=wfVo.getWorkitemId()){
				wim.removeWorkItem(wi.getWorkItemID());
			}
		}
		return true;
	}
	/**
	 * 结束工单
	 * 
	 * @author Zhang, Chong-Jun
	 */
	public void stopProcess(Long procId) throws WFServiceException,
			WFReasonableException {		
		bpsMgr.getProcessInstManager().terminateProcessInstance(procId);
	}
	
	/**
	 * 追派(根据流程实例ID以及活动定义ID追派任务)
	 * 
	 * @return
	 * @throws WFServiceException
	 */
	public void addTask() throws WFServiceException {	
		WFActivityInst wai = new WFActivityInst();
		WFParticipant[] nextpart = null;
		wai = bpsMgr.getActivityInstManager().findLastActivityInstByActivityID(wfVo.getPrcsInstId(), wfVo.getActivityDefID());
		
		if(wai != null && getWfVo().getNextParticipant()!=null){
			nextpart = getWfVo().getNextParticipant().toArray(new WFParticipant[0]);
			bpsMgr.getWorkItemManager().addWorkItem(wai.getActivityInstID(), nextpart);
		}
		//TODO：改造一下这个接口，就是根据活动定义ID查询活动实例，查看活动实例是否结束，如果结束了就直接返回，提示不让追派
	}
	
	public static void startTest() throws Exception{
		User usr = new User();
		usr.setUserId("c777dfc1-2aa6-42f6-ac1b-38356f10bf14");//PL_USER表的主键，发起人
		usr.setUserName("xiaoli");

		WfVo wfVo = new WfVo();
		wfVo.setProcessDefName("cn.kingmed.bpm.customer.archivesInfoAudit");//业务流程唯一标识
		wfVo.setProcessChName("自动获取审批人");
		wfVo.setProcessDescription("测试");
		
		List<WFParticipant> listNP = new ArrayList<WFParticipant>();
		WFParticipant nextpart1 = new WFParticipant( );
//		nextpart1.setId("e5427a09-6469-48a1-a27c-acf479324444");//PL_USER表的主键，下个处理人
//		nextpart1.setTypeCode("person");
		nextpart1.setId("0a0f7f5e-7555-7e75-e050-a8c05f007225");//PL_ROLE表的主键，下个处理人
		nextpart1.setTypeCode("role");
		
//		WFParticipant nextpart2 = new WFParticipant();
//		nextpart2.setId("d8be1775-7b37-4ecb-bf34-df4e702b678d");//PL_USER表的主键，下个处理人
//		nextpart2.setTypeCode("person");
//		nextpart2.setId("a97f4727-f5fc-4ec4-a907-c85e4b7a34a9");//PL_ROLE表的主键，下个处理人
//		nextpart2.setTypeCode("role");
		
//		WFParticipant nextpart3 = new WFParticipant();
//		nextpart3.setId("3d279625-1c2d-4a0b-9a72-ec58ba22c9f2");//PL_ROLE表的主键，下个处理人
//		nextpart3.setTypeCode("role");
		
		listNP.add(nextpart1);
//		listNP.add(nextpart2);
//		listNP.add(nextpart3);
		wfVo.setNextParticipant(listNP);
		
		BizInfoVo biVo = new BizInfoVo();
		biVo.setReqType("MS");
		biVo.setFormSeq(UUID.randomUUID().toString());
		biVo.setFormTitle("名称"+UUID.randomUUID());
		biVo.setSendUserId(usr.getUserName());
		biVo.setSendTime( "2015-03-17 08:09:11");
		biVo.setFormStatus(Constant.FORM_STATUS_ONGOING);
		biVo.setParam1("http://mstest.kingmed.com.cn:8280/km-ms/html/personal/edit/e34b30b2-583e-4f6a-b941-aa57e12c6cb9/baseinfo");
		biVo.setParam2("9bf7c4e6-174f-44c3-9ee8-0ea34d84ed49");
		biVo.setParam3("参数33333");
		biVo.setParam4("参数44444");
		biVo.setParam5("参数55555");
		
		BpsProcess bp = new BpsProcess(usr, wfVo, biVo);
		
		try {
			System.out.println(bp.start());
		} catch (WFReasonableException e) {
			e.printStackTrace();
		}
	}
	
	public static void sendTest() throws Exception{
		User usr = new User();
		usr.setUserId("c777dfc1-2aa6-42f6-ac1b-38356f10bf14");//PL_USER表的主键
		usr.setUserName("xiaoli");

		WfVo wfVo = new WfVo();
		wfVo.setProcessDefName("cn.kingmed.bpm.customer.archivesInfoAudit");//业务流程唯一标识
		wfVo.setProcessChName("审批流程  by API");
		wfVo.setProcessDescription("测试");
		wfVo.setPrcsInstId(4402);
		wfVo.setActivityInstId(10926);
		wfVo.setWorkitemId(6814);
		wfVo.setActivityDefID( "ArchAuditActivity_END");  //活动节点唯一标识
		wfVo.setCheckFlag("rejectToStart");
		wfVo.setDealResult("不要超过一百二十五字不要超过一百二十五字不要超过一百二十五字不要超过一百二十五字不要超过一百二十五字不要超过一百二十五字不要超过一百二十五字不要超过一百二十五字不要超过一百二十五字不要超过一百二十五字不要超过一百二十五字不要超过一百二十五字不要超过一百二十五字");

		List<WFParticipant> listNP = new ArrayList<WFParticipant>();
		WFParticipant nextpart = new WFParticipant( );
		//nextpart.setId("c777dfc1-2aa6-42f6-ac1b-38356f10bf14");//PL_USER表的主键，下个处理人
		//nextpart.setName("xiaoli");
		nextpart.setId("e13d9776-13d4-435b-955c-6180be28bda4");//PL_ROLE表的主键，下个处理人
		nextpart.setTypeCode("role");
		listNP.add(nextpart);
		wfVo.setNextParticipant(listNP);
		
		BizInfoVo biVo = new BizInfoVo();
		biVo.setReqType("archivesInfoAudit");
		biVo.setFormSeq(UUID.randomUUID().toString());
		biVo.setFormTitle("名称"+UUID.randomUUID());
		biVo.setSendUserId(usr.getUserName());
		biVo.setSendTime( Long.toString(System.currentTimeMillis()));
		biVo.setFormStatus(Constant.FORM_STATUS_ONGOING);
		biVo.setParam1("参数一");
		biVo.setParam2("参数二");
		biVo.setParam5("参数五");
		
		BpsProcess bp = new BpsProcess(usr, wfVo, biVo);
		
		try {
			System.out.println(BpsProcess.send(usr, wfVo, biVo));
		} catch (WFServiceException e) {
			e.printStackTrace();
			if(e.getMessage().indexOf("cann\'t find participant")>=0){
				throw new Exception("The user/organization ID does not exist!");
			}
		} catch (WFReasonableException e) {
			e.printStackTrace();
		}
	}
	
	public static void rollbackLastTest() throws Exception{
		User usr = new User();
		usr.setUserId("tiger");
		usr.setUserName("tiger");

		WfVo wfVo = new WfVo();
		wfVo.setProcessDefName("cn.kingmed.bpm.customer.archivesInfoAudit");
		wfVo.setWorkitemId(43);
		wfVo.setActivityInstId(84); //TODO: 必须！

		BizInfoVo biVo = new BizInfoVo();
		biVo.setReqType("archivesInfoAudit");
		biVo.setFormSeq(UUID.randomUUID().toString());
		biVo.setFormTitle("名称"+UUID.randomUUID());
		biVo.setSendUserId(usr.getUserName());
		biVo.setSendTime( Long.toString(System.currentTimeMillis()));
		biVo.setFormStatus(Constant.FORM_STATUS_ONGOING);
		BpsProcess bp = new BpsProcess(usr, wfVo, biVo);
		try { 
			bp.rollbackLast(null, "recent_manual") ;
		} catch (WFServiceException e) {
			e.printStackTrace();
			if(e.getMessage().indexOf("cann\'t find participant")>=0){
				throw new Exception("The user/organization ID does not exist!");
			}
		} catch (WFReasonableException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * 多个审批者的情况下，判断该节点是否全部审批完毕。一般用于send方法之后
	 * @param user
	 * @param wfVo
	 * @return
	 * @throws WFServiceException
	 * 
	 * @since 1.0
	 */
	public static boolean isActivityDone(User user, WfVo wfVo ) throws WFServiceException{
		if(wfVo.getActivityInstId()<1){
			throw new WFServiceException("请先设置wfVo.activityInstId");
		}
		BpsProcess bps = new BpsProcess(  user, wfVo, null);
		long l = bps.bpsMgr.getActivityInstManager().getActivityInstState(wfVo.getActivityInstId());
		
		return l==7?true:false;
	}
	/**
	 * 
	 * 终止一个流程实例
	 * @param user
	 * @param wfVo
	 * @param biVo
	 * @return
	 * @throws WFServiceException
	 * 
	 * @since 1.0
	 */
	public static void terminateProcessInstance(User user, WfVo wfVo ) throws WFServiceException{
		if(wfVo.getPrcsInstId()<1){
			throw new WFServiceException("请先设置wfVo.prcsInstId");
		}
		BpsProcess bps = new BpsProcess(  user, wfVo, null);
		bps.bpsMgr.getProcessInstManager().terminateProcessInstance(wfVo.getPrcsInstId()); 	
	}
	/**
	 * 
	 * 挂起一个工作项实例
	 * @param user
	 * @param wfVo
	 * @param biVo
	 * @return
	 * @throws WFServiceException
	 * 
	 * @since 1.0
	 */
	public static void suspendProcessInstance(User user, WfVo wfVo ) throws WFServiceException{
		if(wfVo.getPrcsInstId()<1){
			throw new WFServiceException("请先设置wfVo.prcsInstId");
		}
		BpsProcess bps = new BpsProcess(  user, wfVo, null);
		bps.bpsMgr.getProcessInstManager().suspendProcessInstance(wfVo.getPrcsInstId()); 	
	}
	
	/**
	 * 
	 * 提交草稿 
	 * @param user
	 * @param wfVo
	 * @param biVo
	 * @return
	 * @throws WFServiceException
	 * @throws WFReasonableException 
	 * 
	 * @since 1.0
	 */
	public static void sendDraft(User user, WfVo wfVo ) throws WFServiceException, WFReasonableException{
		if(wfVo.getPrcsInstId()<1){
			throw new WFServiceException("请先设置wfVo.prcsInstId");
		}
		BpsProcess bps = new BpsProcess(  user, wfVo, null);
		
		List<WFWorkItem> wiList = bps.bpsMgr.queryNextWorkItemsByProcessInstID(wfVo.getPrcsInstId(), false);
		WFWorkItem wi = wiList.get(0);
		wfVo.setActivityDefID(wi.getActivityDefID());
		wfVo.setWorkitemId(wi.getWorkItemID());
		bps.send();
	}
	/**
	 * 
	 * 驳回 
	 * @param user
	 * @param wfVo
	 * @param biVo
	 * @return
	 * @throws WFServiceException
	 * @throws WFReasonableException 
	 * 
	 * @since 1.0
	 */
	/*public static void reject(User user, WfVo wfVo ) throws WFServiceException, WFReasonableException{
		if(wfVo.getPrcsInstId()<1){
			throw new WFServiceException("请先设置wfVo.prcsInstId");
		}
		BpsProcess bps = new BpsProcess(  user, wfVo, null);
		
		bps.rollbackStart(null, null);
	}*/
	
	public static void main(String...arg){

		User usr = new User();
		usr.setUserId("e5427a09-6469-48a1-a27c-acf479324444");
		usr.setUserName("xiaoZ");
//
		WfVo wfVo = new WfVo();
// 		wfVo.setWorkitemId(2063);
		wfVo.setPrcsInstId(3462);
		wfVo.setActivityInstId( 7540);
//		wfVo.setActivityDefID("ArchAuditActivity_END");
//		wfVo.setActivityInstId( 536);
//		wfVo.setCheckFlag("rejectToStart");
//		wfVo.setPrcsInstId(661);
//		wfVo.setWorkitemId(343);
//		wfVo.setActivityDefID( "ArchAuditActivity_END");		
		List<WFParticipant> listNP = new ArrayList<WFParticipant>();
		WFParticipant nextpart1 = new WFParticipant( );
		nextpart1.setId("e5427a09-6469-48a1-a27c-acf479324444");//PL_USER表的主键，下个处理人
		nextpart1.setTypeCode("person"); 
		
		WFParticipant nextpart2 = new WFParticipant();
		nextpart2.setId("d8be1775-7b37-4ecb-bf34-df4e702b678d");//PL_USER表的主键，下个处理人
		nextpart2.setTypeCode("person"); 
		
		listNP.add(nextpart1);
		listNP.add(nextpart2); 
		wfVo.setNextParticipant(listNP);
		try {
//			reassign(usr, wfVo, "f704d4d0-96bf-4e25-ab0b-c000b92a25bc");
//			send (usr,wfVo, null  ) ;
//			sendDraft(usr,wfVo   );
//			System.out.println(BpsProcess.isActivityDone(usr, wfVo));
//			BpsProcess.suspendProcessInstance(usr, wfVo);
			sendTest();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		//sendTest();
		//rollbackLastTest(); 
	}
}
