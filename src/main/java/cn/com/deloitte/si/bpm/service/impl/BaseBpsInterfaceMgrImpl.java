package cn.com.deloitte.si.bpm.service.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.deloitte.si.bpm.entity.WFMyWorkItem;
import cn.com.deloitte.si.bpm.service.IBaseBpsInterfaceMgr;
import cn.com.deloitte.si.bpm.utils.DateUtils;

import com.eos.workflow.api.BPSServiceClientFactory;
import com.eos.workflow.api.IBPSServiceClient;
import com.eos.workflow.api.IWFActivityInstManager;
import com.eos.workflow.api.IWFAgentManager;
import com.eos.workflow.api.IWFBackActivityManager;
import com.eos.workflow.api.IWFCommonManager;
import com.eos.workflow.api.IWFDefinitionQueryManager;
import com.eos.workflow.api.IWFDelegateManager;
import com.eos.workflow.api.IWFProcessInstManager;
import com.eos.workflow.api.IWFRelativeDataManager;
import com.eos.workflow.api.IWFWorkItemManager;
import com.eos.workflow.api.IWFWorklistQueryManager;
import com.eos.workflow.data.WFActivityInst;
import com.eos.workflow.data.WFConnector;
import com.eos.workflow.data.WFProcessDefine;
import com.eos.workflow.data.WFProcessInst;
import com.eos.workflow.data.WFTimePeriod;
import com.eos.workflow.data.WFWorkItem;
import com.eos.workflow.omservice.WFParticipant;
import com.eos.workflow.omservice.WIParticipantInfo;
import com.primeton.workflow.api.PageCond;
import com.primeton.workflow.api.WFReasonableException;
import com.primeton.workflow.api.WFServiceException;
import com.primeton.workflow.model.exception.WFException;

/**
 * 此类每次使用使用时都需要初始化， 不提供默认构造方法，必须使用userid或者client初始化
 * 
 * @author Zhang chongjun
 * 
 */
public class BaseBpsInterfaceMgrImpl implements IBaseBpsInterfaceMgr {

	private IBPSServiceClient client;
	// work item待领取
	private static final int WORK_ITEM_STATUS_PENNDING = 2;

	public BaseBpsInterfaceMgrImpl(String userId, String userName) {
		BPSServiceClientFactory.getLoginManager().setCurrentUser(userId,
				userName);

		try {
			client = BPSServiceClientFactory.getDefaultClient();
		} catch (WFServiceException e) {
			e.printStackTrace();
		}
	}

	public BaseBpsInterfaceMgrImpl(String[] user) {
		BPSServiceClientFactory.getLoginManager().setCurrentUser(user[0],
				user[1]);

		try {
			client = BPSServiceClientFactory.getDefaultClient();
		} catch (WFServiceException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see 创建工作流实例
	 * @param processDefName
	 *            流程定义名称
	 * @param processInstName
	 *            流程实例名称
	 * @param processInstDesc
	 *            流程实例描述
	 * @return 流程实例ID
	 * @throws WFServiceException
	 * @throws WFServiceException
	 */
	@Override
	public String createProcessInstance(String processDefName,
			String processInstName, String processInstDesc)
			throws WFServiceException {
		String processInstID = null;

		processInstID = Long.toString(client.getProcessInstManager()
				.createProcessInstance(processDefName, processInstName,
						processInstDesc));

		return processInstID;
	}

	/**
	 * @see 启动工作流实例,并完成第一个工作项
	 * @param processInstID
	 *            流程实例ID
	 * @param isTransactionSplit
	 *            表示本次调用是否启用事务分割功能，true开启，false关闭
	 * @param params
	 *            流程参数，传入null表示参数为空
	 * @throws WFServiceException
	 */
	@Override
	public void startProcessInstAndFinishFirstWorkItem(String processInstID,
			boolean isTransactionSplit, Object[] params)
			throws WFServiceException {
		client.getProcessInstManager().startProcessInstAndFinishFirstWorkItem(
				Long.parseLong(processInstID), isTransactionSplit, params);
	}

	/**
	 * @throws WFReasonableException 
	 * @ 流程启动
	 */
	@Override
	public long startProcess(String processDefName, String processInstName,
			String processInstDesc, boolean isTransactionSplit,
			Object[] params, WFParticipant parts[],
			Map<String, Object> relaDatas) throws WFServiceException, WFReasonableException {

		long processInstID;

		processInstID = client.getProcessInstManager().createProcessInstance(
				processDefName, processInstName, processInstDesc);

		if (parts != null) {
			client.getRelativeDataManager().setRelativeData(processInstID,
					"nextParticipant", parts);
		}

		if (relaDatas != null) {
			client.getRelativeDataManager().setRelativeDataBatch(processInstID,
					relaDatas);
		}
	

		client.getProcessInstManager().startProcessInstAndFinishFirstWorkItem(processInstID, isTransactionSplit, params);

		return processInstID;
	}
	
	/**
	 * 更改流程实例的名称
	 * @param processId
	 * @param newName
	 * @throws WFServiceException
	 */
	public void changeProcessName(long processId, String newName) throws WFServiceException{
		Map<String, Object> attr = new HashMap<String, Object>();
		attr.put("name", newName);
		attr.put("description", newName);
		client.getProcessInstManager().setProcessInstAttributeBatch(processId, attr);
	}
	
	/**
	 * @throws WFReasonableException 
	 * @ 流程启动
	 */
	@Override
	public long startProcessWithBiz(String processDefName, String processInstName,
			String processInstDesc, boolean isTransactionSplit,
			Object[] params, WFParticipant parts[],
			Map<String, Object> relaDatas,String tableName,Map<String, Object> bizInfo) throws WFServiceException, WFReasonableException {

		long processInstID;

		processInstID = client.getProcessInstManager().createProcessInstance(
				processDefName, processInstName, processInstDesc);

		if (parts != null) {
			client.getRelativeDataManager().setRelativeData(processInstID,
					"nextParticipant", parts);
		}

		if (relaDatas != null) {
			client.getRelativeDataManager().setRelativeDataBatch(processInstID,
					relaDatas);
		}
		
		client.getProcessInstManager().startProcessInstanceWithBizInfo(processInstID, isTransactionSplit, params, tableName, bizInfo);
		
		List<WFWorkItem> ll = client.getWorkItemManager().queryNextWorkItemsByProcessInstID(processInstID, false);
		
		if(ll != null){
			client.getWorkItemManager().finishWorkItem(ll.get(0).getWorkItemID(), false);
		}

		//client.getProcessInstManager().startProcessInstAndFinishFirstWorkItem(processInstID, isTransactionSplit, params);

		return processInstID;
	}
	
	
	/**
	 * @throws WFReasonableException 
	 * @ 流程启动
	 */
	@Override
	public long startProcessForDraft(String processDefName, String processInstName,
			String processInstDesc, boolean isTransactionSplit,
			Object[] params, WFParticipant parts[],
			Map<String, Object> relaDatas,String tableName,Map<String, Object> bizInfo) throws WFServiceException, WFReasonableException {

		long processInstID;

		processInstID = client.getProcessInstManager().createProcessInstance(
				processDefName, processInstName, processInstDesc);

		if (parts != null) {
			client.getRelativeDataManager().setRelativeData(processInstID,
					"nextParticipant", parts);
		}

		if (relaDatas != null) {
			client.getRelativeDataManager().setRelativeDataBatch(processInstID,
					relaDatas);
		}
		
		client.getProcessInstManager().startProcessInstanceWithBizInfo(processInstID, isTransactionSplit, params, tableName, bizInfo);

		return processInstID;
	}

	/**
	 * @ 提交工作项
	 */
	@Override
	public void submitTask(long workItemID, String activityDefID,
			boolean transactionSpan, WFParticipant parts[], String dealOpinion, String dealResult)
			throws WFServiceException, WFReasonableException {
		if (parts != null) {
			client.getAppointActivityManager().appointActivityParticipant(
					workItemID, activityDefID, parts);
		}
		
		/* 2015-6-30 */
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("TRANSCATIONSPAN", new String(""+transactionSpan));
		map.put("dealOpinion", dealOpinion);
		map.put("dealResult", dealResult);
		
		client.getWorkItemManager().finishWorkItemWithOption(workItemID, map, null);
	}

	/**
	 * @ 获取待办
	 */
	@Override
	public List<WFMyWorkItem> getMyWaitingTasks(String personId,
			String permission, String scope, PageCond pagecond)
			throws WFServiceException {
		IWFWorklistQueryManager worklistQueryManager = client
				.getWorklistQueryManager();

		List<WFWorkItem> wis = worklistQueryManager.queryPersonWorkItems(
				personId, permission, scope, pagecond);
		List<WFMyWorkItem> wisNew = generateMyWaitingWorkItems(wis);
		return wisNew;
	}

	@Override
	public List<WFMyWorkItem> getMyWaitingTasksWithBizInfo(String personId,
			String permission, String scope, String bizTableName, String wiSql,
			String bizSql, List<String> wiBindList, List<String> bizBindList,
			PageCond pagecond) throws WFServiceException {
		IWFWorklistQueryManager worklistQueryManager = client.getWorklistQueryManager();
		List<WFWorkItem> wis = worklistQueryManager.queryPersonWorkItemsWithBizInfo(
				personId, permission, scope, bizTableName, wiSql,
				bizSql, wiBindList, bizBindList, pagecond);
		List<WFMyWorkItem> wisNew = generateMyWaitingWorkItems(wis);
		return wisNew;
	}

	private List<WFMyWorkItem> generateMyWaitingWorkItems(List<WFWorkItem> wis)
			throws WFServiceException {
		List<WFMyWorkItem> wisNew = new ArrayList<WFMyWorkItem>();
		List<String> listKeys = new ArrayList<String>();
		listKeys.add("formSeq");
		listKeys.add("formTitle");
		listKeys.add("sendUserId");
		listKeys.add("sendTime");
		listKeys.add("formMainId");
		listKeys.add("url");
		listKeys.add("param1");
		listKeys.add("param2");
		listKeys.add("param3");
		listKeys.add("param4");
		listKeys.add("param5");

		for (int i = 0; i < wis.size(); ++i) {
			WFWorkItem wi = wis.get(i);
			WFMyWorkItem wmw = new WFMyWorkItem();
			List<Object> listObj = new ArrayList<Object>();

			listObj = getRelativeData(wi, listKeys);
			wmw.setFormMainId(null2String(listObj.get(4)));
			wmw.setFormSeq(null2String(listObj.get(0)));
			wmw.setFormTitle(null2String(listObj.get(1)));
			String time = null2String(listObj.get(3));
			Date d = null;
			try {
				d = DateUtils.datetimeFormat.parse(time);
			} catch (ParseException e) {
				e.printStackTrace();
				//2014-05-21 by David_xun
				//临时增加在出错的情况下输出内容，以便于定位问题
				System.out.println("error on process[" + wi.getProcessInstID() + "] and workitem[ " + wi.getWorkItemName() + "]");
				System.out.println("print listObj: ");
				for (int j = 0; j < listObj.size(); j++) {
					System.out.println(listObj.get(j));
				}
			}
			wmw.setSendTime(d);
			wmw.setSendUserId(null2String(listObj.get(2)));
			String urlTemp = (String) listObj.get(5);
			String url = "";
			if(urlTemp!=null){
				if (urlTemp.contains("?")) {
					url = urlTemp;
				} else {
					url = urlTemp + "?1=1";
				}
			}	
			wmw.setUrl(null2String(url));
			wmw.setParam1(null2String(listObj.get(6)));
			wmw.setParam2(null2String(listObj.get(7)));
			wmw.setParam3(null2String(listObj.get(8)));
			wmw.setParam4(null2String(listObj.get(9)));
			wmw.setParam5(null2String(listObj.get(10)));
			wmw.setActionMask(wi.getActionMask());
			wmw.setActivityDefID(wi.getActivityDefID());
			wmw.setActivityInstID(wi.getActivityInstID());
			wmw.setActivityInstName(wi.getActivityInstName());
			wmw.setParticipant(wi.getParticipant());
			wmw.setParticipants(wi.getParticipants());
			wmw.setPartiName(wi.getPartiName());
			wmw.setProcessChName(wi.getProcessChName());
			wmw.setProcessDefID(wi.getProcessDefID());
			wmw.setProcessDefName(wi.getProcessDefName());
			wmw.setProcessInstID(wi.getProcessInstID());
			wmw.setProcessInstName(wi.getProcessInstName());
			wmw.setStartTime(wi.getStartTime());
			wmw.setActionURL(wi.getActionURL());
			wmw.setAllowAgent(wi.getAllowAgent());
			wmw.setAssistant(wi.getAssistant());
			wmw.setAssistantName(wi.getAssistantName());
			wmw.setBizObject(wi.getBizObject());
			wmw.setBizState(wi.getBizState());
			wmw.setCatalogName(wi.getCatalogName());
			wmw.setCatalogUUID(wi.getCatalogUUID());
			wmw.setCreateTime(wi.getCreateTime());
			wmw.setCurrentState(wi.getCurrentState());
			wmw.setDealOpinion(wi.getDealOpinion());
			wmw.setDealResult(wi.getDealResult());
			wmw.setEndTime(wi.getEndTime());
			wmw.setFinalTime(wi.getFinalTime());
			wmw.setIsTimeOut(wi.getIsTimeOut());
			wmw.setLimitNum(wi.getLimitNum());
			wmw.setLimitNumDesc(wi.getLimitNumDesc());
			wmw.setPriority(wi.getPriority());
			wmw.setRemindTime(wi.getRemindTime());
			wmw.setRootProcInstID(wi.getRootProcInstID());

			wmw.setStatesList(wi.getStatesList());
			wmw.setTimeOutNum(wi.getTimeOutNum());
			wmw.setTimeOutNumDesc(wi.getTimeOutNumDesc());
			wmw.setUrlType(wi.getUrlType());
		
			wmw.setWorkItemDesc(wi.getWorkItemDesc());
			wmw.setWorkItemID(wi.getWorkItemID());
			wmw.setWorkItemName(wi.getWorkItemName());
			wmw.setWorkItemType(wi.getWorkItemType());
			wisNew.add(wmw);
		}
		return wisNew;
	}

	/**
	 * @ 获取代办记录数
	 */
	@Override
	public int getMyWaitingTaskCount(String personId, String permission,
			String scope) throws WFServiceException {
		IWFWorklistQueryManager worklistQueryManager = client.getWorklistQueryManager();
		List<WFWorkItem> wis = worklistQueryManager.queryPersonWorkItems(personId, permission, scope, null);
		return (wis == null) ? 0 : wis.size();
	}

	@Override
	public int getMyWaitingTaskCountWithBizInfo(String personId,
			String permission, String bizTableName, String wiSql,
			String bizSql, List<String> wiBindList, List<String> bizBindList,
			String scope) throws WFServiceException {
		IWFWorklistQueryManager worklistQueryManager = client.getWorklistQueryManager();
		List<WFWorkItem> wis = worklistQueryManager.queryPersonWorkItemsWithBizInfo(personId, permission, scope, bizTableName, wiSql,
				bizSql, wiBindList, bizBindList, null);
		return (wis == null) ? 0 : wis.size();
	}

	/**
	 * @ 获取已办
	 */
	@Override
	public List<WFMyWorkItem> getMyCompletedTasks(String personId,
			String permission, boolean forHistory, PageCond pageCond)
			throws WFServiceException {
		IWFWorklistQueryManager worklistQueryManager = client.getWorklistQueryManager();
		List<WFWorkItem> wis = worklistQueryManager.queryPersonFinishedWorkItems(personId, permission, false, pageCond);
		List<WFMyWorkItem> wisNew = generateMyCompleteWorkItems(wis);
		return wisNew;
	}

	@Override
	public List<WFMyWorkItem> getMyCompletedTasksWithBizInfo(String personId,
			String permission, boolean forHistory, String bizTableName,
			String wiSql, String bizSql, List<String> wiBindList,
			List<String> bizBindList, PageCond pageCond)
			throws WFServiceException {
		IWFWorklistQueryManager worklistQueryManager = client.getWorklistQueryManager();
		List<WFWorkItem> wis = worklistQueryManager.queryPersonFinishedWorkItemsWithBizInfo(personId, permission, false, bizTableName, wiSql,
				bizSql, wiBindList, bizBindList, pageCond);
		List<WFMyWorkItem> wisNew = generateMyCompleteWorkItems(wis);
		return wisNew;
	}

	private List<WFMyWorkItem> generateMyCompleteWorkItems(List<WFWorkItem> wis)
			throws WFServiceException {
		List<WFMyWorkItem> wisNew = new ArrayList<WFMyWorkItem>();
		List<String> listKeys = new ArrayList<String>();
		listKeys.add("formSeq");
		listKeys.add("formTitle");
		listKeys.add("sendUserId");
		listKeys.add("sendTime");
		listKeys.add("formMainId");
		listKeys.add("url");
		listKeys.add("param1");
		listKeys.add("param2");
		listKeys.add("param3");
		listKeys.add("param4");
		listKeys.add("param5");
		for (int i = 0; i < wis.size(); ++i) {
			WFWorkItem wi = wis.get(i);
			WFMyWorkItem wmw = new WFMyWorkItem();
			List<Object> listObj = new ArrayList<Object>();

			listObj = getRelativeData(wi, listKeys);
			wmw.setFormMainId(null2String(listObj.get(4)));
			wmw.setFormSeq(null2String(listObj.get(0)));
			wmw.setFormTitle(null2String(listObj.get(1)));
			String time = null2String(listObj.get(3));
			Date d = null;
			try {
				if (time != null && !time.equalsIgnoreCase("")) {
					d = DateUtils.datetimeFormat.parse(time);
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
			wmw.setSendTime(d);
			wmw.setSendUserId(null2String(listObj.get(2)));
			String urlTemp = (String) listObj.get(5);
			String url = "";
			if (urlTemp.contains("?")) {
				url = urlTemp;
			} else {
				url = urlTemp + "?1=1";
			}
			wmw.setUrl(null2String(url));
			wmw.setParam1(null2String(listObj.get(6)));
			wmw.setParam2(null2String(listObj.get(7)));
			wmw.setParam3(null2String(listObj.get(8)));
			wmw.setParam4(null2String(listObj.get(9)));
			wmw.setParam5(null2String(listObj.get(10)));
			wmw.setActionMask(wi.getActionMask());
			wmw.setActivityDefID(wi.getActivityDefID());
			wmw.setActivityInstID(wi.getActivityInstID());
			wmw.setActivityInstName(wi.getActivityInstName());
			wmw.setParticipant(wi.getParticipant());
			wmw.setParticipants(wi.getParticipants());
			wmw.setPartiName(wi.getPartiName());
			wmw.setProcessChName(wi.getProcessChName());
			wmw.setProcessDefID(wi.getProcessDefID());
			wmw.setProcessDefName(wi.getProcessDefName());
			wmw.setProcessInstID(wi.getProcessInstID());
			wmw.setProcessInstName(wi.getProcessInstName());
			wmw.setStartTime(wi.getStartTime());
			wmw.setActionURL(wi.getActionURL());
			wmw.setAllowAgent(wi.getAllowAgent());
			wmw.setAssistant(wi.getAssistant());
			wmw.setAssistantName(wi.getAssistantName());
			wmw.setBizObject(wi.getBizObject());
			wmw.setBizState(wi.getBizState());
			wmw.setCatalogName(wi.getCatalogName());
			wmw.setCatalogUUID(wi.getCatalogUUID());
			wmw.setCreateTime(wi.getCreateTime());
			wmw.setCurrentState(wi.getCurrentState());
			wmw.setDealOpinion(wi.getDealOpinion());
			wmw.setDealResult(wi.getDealResult());
			wmw.setEndTime(wi.getEndTime());
			wmw.setFinalTime(wi.getFinalTime());
			wmw.setIsTimeOut(wi.getIsTimeOut());
			wmw.setLimitNum(wi.getLimitNum());
			wmw.setLimitNumDesc(wi.getLimitNumDesc());
			wmw.setPriority(wi.getPriority());
			wmw.setRemindTime(wi.getRemindTime());
			wmw.setRootProcInstID(wi.getRootProcInstID());
			
			wmw.setStatesList(wi.getStatesList());
			wmw.setTimeOutNum(wi.getTimeOutNum());
			wmw.setTimeOutNumDesc(wi.getTimeOutNumDesc());
			wmw.setUrlType(wi.getUrlType());
			
			wmw.setWorkItemDesc(wi.getWorkItemDesc());
			wmw.setWorkItemID(wi.getWorkItemID());
			wmw.setWorkItemName(wi.getWorkItemName());
			wmw.setWorkItemType(wi.getWorkItemType());
			wisNew.add(wmw);
		}
		return wisNew;
	}

	@Override
	public int getMyCompletedTaskCount(String personId, String permission, boolean forHistory) throws WFServiceException {
		IWFWorklistQueryManager worklistQueryManager = client.getWorklistQueryManager();
		List<WFWorkItem> wis = worklistQueryManager.queryPersonFinishedWorkItems(personId, permission, false, null);
		return (wis == null) ? 0 : wis.size();

	}

	@Override
	public int getMyCompletedTaskCountWithBizInfo(String personId,
			String permission, String bizTableName, String wiSql,
			String bizSql, List<String> wiBindList, List<String> bizBindList,
			boolean forHistory) throws WFServiceException {
		IWFWorklistQueryManager worklistQueryManager = client.getWorklistQueryManager();
		List<WFWorkItem> wis = worklistQueryManager.queryPersonFinishedWorkItemsWithBizInfo(personId, permission, false, bizTableName, wiSql,
				bizSql, wiBindList, bizBindList, null);
		return (wis == null) ? 0 : wis.size();
	}

	/**
	 * @see 根据流程实例ID，批量设置相关数据中指定路径下的值
	 * @param processInstID
	 * @param relaDatas
	 * @throws NumberFormatException
	 * @throws WFServiceException
	 */
	public void setRelativeDataBatch(String processInstID,
			Map<String, Object> relaDatas) throws WFServiceException,
			NumberFormatException {

		client.getRelativeDataManager().setRelativeDataBatch(
				Long.parseLong(processInstID), relaDatas);

	}


	/**
	 * @see 根据业务流程名称，和活动定义ID查询活动的后继活动连线信息
	 * @param processDefName
	 *            业务流程名称
	 * @param activityID
	 *            活动实例ID
	 * @return
	 * @throws WFServiceException
	 * @throws WFServiceException
	 */
	@Override
	public List<WFConnector> queryNextTransition(String processDefName,
			String activityID) throws WFServiceException {
		// 连线信息集合
		List<WFConnector> connectorList = null;

		connectorList = client.getDefinitionQueryManager().queryNextTransition(
				processDefName, activityID);

		return connectorList;
	}

	/**
	 * @see 根据业务流程ID，和活动定义ID查询活动的后继活动连线信息.
	 * @param processDefID
	 * @param activityID
	 * @return
	 * @throws WFServiceException
	 */
	@Override
	public List<WFConnector> queryNextTransition(long processDefID,
			String activityID) throws WFServiceException {
		// 连线信息集合
		List<WFConnector> connectorList = null;

		connectorList = client.getDefinitionQueryManager().queryNextTransition(
				processDefID, activityID);

		return connectorList;
	}

	/**
	 * @see 查询出当前活动完成后生成下一个活动的工作项,用在新增流程时
	 * @param processInstID
	 *            流程实例ID
	 * @param fetchParticipant
	 *            工作项中是否否包含参与者，ture为包含参与者
	 * @return 工作项ID
	 * @throws WFServiceException
	 * @throws WFException
	 * @throws WFServiceException
	 */
	@Override
	public List<WFWorkItem> queryNextWorkItemsByProcessInstID(
			long processInstID, boolean fetchParticipant)
			throws WFServiceException {
		// 后继活动的工作项集合
		List<WFWorkItem> nextWorkItemList = null;

		nextWorkItemList = client.getWorkItemManager()
				.queryNextWorkItemsByProcessInstID(processInstID,
						fetchParticipant);

		return nextWorkItemList;
	}

	/**
	 * @see 查询出已完成的工作项生成的后继活动的工作项
	 * @param workItemID
	 *            工作项ID
	 * @param fetchParticipant
	 *            工作项中是否否包含参与者，ture为包含参与者
	 * @return
	 * @throws WFServiceException
	 */
	@Override
	public List<WFWorkItem> queryNextWorkItemsByWorkItemID(long workItemID,
			boolean fetchParticipant) {
		// 后继活动的工作项集合
		List<WFWorkItem> nextWorkItemList = null;

		try {
			nextWorkItemList = client.getWorkItemManager()
					.queryNextWorkItemsByWorkItemID(workItemID,
							fetchParticipant);
		} catch (WFServiceException e) {
			e.printStackTrace();
			// System.out.println("根据已完成工作项查询后继活动的工作项发生错误："+e.getMessage()) ;
		}

		return nextWorkItemList;
	}

	/**
	 * @see 查询指定工作项的参与者信息
	 * @param workItemID
	 *            工作项ID
	 * @return
	 * @throws WFServiceException
	 * @throws WFServiceException
	 */
	@Override
	public List<WIParticipantInfo> queryWorkItemParticipantInfo(long workItemID)
			throws WFServiceException {
		// 参与者列表
		List<WIParticipantInfo> participantList = null;

		participantList = client.getWorkItemManager()
				.queryWorkItemParticipantInfo(workItemID);

		return participantList;
	}

	/**
	 * @see 完成指定工作项
	 * @param workItemID
	 *            工作项ID
	 * @param transactionSpan
	 *            参数为true，则启用事务分割；参数为false，则不启用事务分割
	 * @param map
	 *            存放设置在相关数据中的信息
	 * @return
	 * @throws WFServiceException
	 */
	@Override
	public void finishWorkItemWithRelativeData(long workItemID,
			Map<String, Object> map, boolean transactionSpan)
			throws WFServiceException {
		// client = initLogin();
		IWFWorkItemManager workItemMananger = client.getWorkItemManager();
		workItemMananger.finishWorkItemWithRelativeData(workItemID, map,
				transactionSpan);

	}

	/**
	 * @see 完成指定工作项
	 * @param workItemID
	 *            工作项ID
	 * @param transactionSpan
	 *            参数为true，则启用事务分割；参数为false，则不启用事务分割
	 * @return
	 * @throws WFServiceException
	 */
	@Override
	public void finishWorkItem(long workItemID, boolean transactionSpan)
			throws WFServiceException, WFReasonableException {
		client.getWorkItemManager().finishWorkItem(workItemID, transactionSpan);
	}

	/*
	 * 向流程图设置相关数据，判断流程走向
	 * 
	 * @param processInstId 流程实例ID
	 * 
	 * @param lineName 流程图线上条件的名字
	 * 
	 * @param relativeData 判断流程走向的值 如 1通过 2不通过
	 */

	@Override
	public void setRelativeData(long processInstId, String lineName,
			Object relativeData) throws WFServiceException {
		IWFRelativeDataManager relativeDataManager = client
				.getRelativeDataManager();
		relativeDataManager.setRelativeData(processInstId, lineName,
				relativeData);
	}

	/**
	 * @see 转派
	 * @throws WFServiceException
	 */
	@Override
	public void forwardTask(WFWorkItem item, WFParticipant[] participantArr)
			throws WFServiceException {

		client.getWorkItemManager().reassignWorkItemEx(item.getWorkItemID(),
				participantArr);
	}

	/**
	 * @see 协办
	 * @throws WFServiceException
	 */
	@Override
	public void delegateTask(WFWorkItem item, WFParticipant parts[])
			throws WFServiceException {

		client.getDelegateManager().delegateWorkItem(item.getWorkItemID(),
				parts, "DELEG");
	}

	/**
	 * @see 修改任务预订完成时间
	 * @throws WFServiceException
	 */
	public void resetTaskTimeOut(WFWorkItem item, WFTimePeriod start,
			WFTimePeriod end) throws WFServiceException {

		client.getWorkItemManager().setWorkItemTimeLimit(item.getWorkItemID(),
				start, end);
	}

	/**
	 * 获取当前环节的流程基本信息(动作/路由/链接等)
	 * 
	 * @param WFWorkItem
	 *            当前工作项
	 */
	@Override
	public String getActivityExtendAttributes(WFWorkItem item)
			throws WFServiceException {
		String returns = "";

		returns = client.getDefinitionQueryManager().getExtendAttribute(
				item.getProcessDefID(), item.getActivityDefID());

		return returns;
	}

	/**
	 * 获取指定流程模型的指定环节的流程基本信息(动作/路由/链接等)
	 * 
	 * @param processModelName
	 *            流程模型名称
	 * @param activityDefID
	 *            环节名称
	 */
	@Override
	public String getActivityExtendAttributes(String processModelName,
			String activityDefID) throws WFServiceException {
		String returns = "";

		PageCond pagec = new PageCond(2147483647);
		List<?> listWpd = client.getDefinitionQueryManager()
				.queryProcessesByName(processModelName, "published", pagec);
		if (listWpd == null || listWpd.size() == 0) {
		} else {
			WFProcessDefine wfd1 = (WFProcessDefine) listWpd.get(0);
			returns = client.getDefinitionQueryManager().getExtendAttribute(
					wfd1.getProcessDefID().longValue(), activityDefID);
		}

		return returns;
	}

	/**
	 * 获取流程业务字段信息
	 * 
	 * @param processModelName
	 *            流程模型名称
	 * @param keys
	 *            业务字段名
	 */
	@Override
	public Object getRelativeData(long processInstID, String key)
			throws WFServiceException {

		return client.getRelativeDataManager().getRelativeData(processInstID,
				key);

	}

	/**
	 * 获取流程业务字段信息
	 * 
	 * @param processModelName
	 *            流程模型名称
	 * @param keys
	 *            业务字段名
	 */
	@Override
	public List<Object> getRelativeData(WFWorkItem item, List<String> keys)
			throws WFServiceException {
		List<Object> result = null;

		String array[] = new String[keys.size()];
		result = client.getRelativeDataManager().getRelativeDataBatch(
				item.getProcessInstID(), (String[]) keys.toArray(array));

		return result;
	}

	@Override
	public WFProcessInst getProcessInstByPrsIstId(long processInstId)
			throws WFServiceException {
		return client.getProcessInstManager().queryProcessInstDetail(
				processInstId);
	}

	@Override
	public List<WFActivityInst> getAcitityListByPrsIstId(long processInstId,
			PageCond pageCond) throws WFServiceException {
		List<WFActivityInst> activityInsts = client.getActivityInstManager()
				.queryActivityInstsByProcessInstID(processInstId, null);

		return activityInsts;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.com.deloitte.si.bpm.service.IBaseBpsInterfaceMgr#getWorkItemListByPrsIstId
	 * (long)
	 */
	@Override
	public List<WFWorkItem> getWorkItemListByActIstId(long activityInstID,
			PageCond pc) throws WFServiceException {
		List<WFWorkItem> workItems = client.getWorkItemManager()
				.queryWorkItemsByActivityInstID(activityInstID, null);

		return workItems;
	}

	/**
	 * @ 根据工作项id设置时限
	 */
	@Override
	public int setWorkItemTimeLimit(long workItemID, WFTimePeriod timeLimit,
			WFTimePeriod remindTime) throws WFServiceException {
		return client.getWorkItemManager().setWorkItemTimeLimit(workItemID,
				timeLimit, remindTime);
	}

	/**
	 * @ 根据流程实例ID获取启动的流程时产生的新工作项列表，仅限流程启动使用
	 */
	@Override
	public List<WFWorkItem> getNextWorkItemListByProcessInstId(
			long processInstId) throws WFServiceException {
		List<WFActivityInst> asList = client.getActivityInstManager()
				.queryActivityInstsByProcessInstID(processInstId,
						new PageCond());
		List<WFWorkItem> wiList = new ArrayList<WFWorkItem>();
		for (int i = 0; i < asList.size(); i++) {
			WFActivityInst ai = new WFActivityInst();
			ai = asList.get(i);
			if (ai.getCurrentState() == WORK_ITEM_STATUS_PENNDING) {
				List<WFWorkItem> wi = client.getWorkItemManager()
						.queryWorkItemsByActivityInstID(
								asList.get(2).getActivityInstID(),
								new PageCond(10));
				wiList.addAll(wi);
			}
		}
		return wiList;
	}

	/*
	 * 删除流程实例，目前只有测试代码在用
	 * 
	 * @see
	 * cn.com.deloitte.si.bpm.service.IBaseBpsInterfaceMgr#deleteProcessInstById
	 * (long)
	 */
	@Override
	public void deleteProcessInstById(long processInstId)
			throws WFServiceException {
		client.getProcessInstManager().deleteProcessInstance(processInstId);

	}

	/**
	 * 代理管理接口
	 * 
	 * @throws WFServiceException
	 */
	@Override
	public IWFAgentManager getAgentManager() throws WFServiceException {
		IWFAgentManager iwfa = client.getAgentManager();
		return iwfa;
	}

	/**
	 * 定义管理接口
	 * 
	 * @throws WFServiceException
	 */
	@Override
	public IWFDefinitionQueryManager getDefinitionQueryManager()
			throws WFServiceException {
		IWFDefinitionQueryManager iwfd = client.getDefinitionQueryManager();
		return iwfd;
	}

	/**
	 * 工作项管理接口
	 * 
	 * @throws WFServiceException
	 */
	@Override
	public IWFWorkItemManager getWorkItemManager() throws WFServiceException {
		IWFWorkItemManager iwfw = client.getWorkItemManager();
		return iwfw;
	}

	/**
	 * 如果为null，返回“”
	 */
	private String null2String(Object s) {
		return s == null ? "" : s.toString();
	}

	/**
	 * 代办管理接口
	 * 
	 * @throws WFServiceException
	 */
	@Override
	public IWFDelegateManager getDelegateManager() throws WFServiceException {
		IWFDelegateManager idm = client.getDelegateManager();
		return idm;
	}

	/**
	 * 回退管理接口
	 * 
	 * @throws WFServiceException
	 */
	@Override
	public IWFBackActivityManager getBackActivityManager() throws WFServiceException {
		IWFBackActivityManager ibam = client.getBackActivityManager();
		return ibam;
	}
	/**
	 * 工作项查询管理接口
	 * 
	 * @throws WFServiceException
	 */
	@Override
	public IWFWorklistQueryManager getWorklistQueryManager() throws WFServiceException {
		IWFWorklistQueryManager iwqm = client.getWorklistQueryManager();
		return iwqm;
	}

	/**
	 * 流程实例管理接口
	 * 
	 * @throws WFServiceException
	 */
	@Override
	public IWFProcessInstManager getProcessInstManager() throws WFServiceException {
		IWFProcessInstManager ipim = client.getProcessInstManager();
		return ipim;
	}

	/**
	 * 活动实例管理接口
	 * 
	 * @throws WFServiceException
	 */
	@Override
	public IWFActivityInstManager getActivityInstManager() throws WFServiceException {
		IWFActivityInstManager iaim = client.getActivityInstManager();
		return iaim;
	}
	
	/**
	 * 通用服务接口
	 * 
	 * @throws WFServiceException
	 */
	@Override
	public IWFCommonManager getCommonManager() throws WFServiceException {
		IWFCommonManager icm = client.getCommonManager();
		return icm;
	}
}
