package cn.edu.sysu.bpm.service;

import java.util.List;
import java.util.Map;

import cn.edu.sysu.bpm.entity.WFMyWorkItem;

import com.eos.workflow.api.IWFActivityInstManager;
import com.eos.workflow.api.IWFAgentManager;
import com.eos.workflow.api.IWFBackActivityManager;
import com.eos.workflow.api.IWFCommonManager;
import com.eos.workflow.api.IWFDefinitionQueryManager;
import com.eos.workflow.api.IWFDelegateManager;
import com.eos.workflow.api.IWFProcessInstManager;
import com.eos.workflow.api.IWFWorkItemManager;
import com.eos.workflow.api.IWFWorklistQueryManager;
import com.eos.workflow.data.WFActivityInst;
import com.eos.workflow.data.WFConnector;
import com.eos.workflow.data.WFProcessInst;
import com.eos.workflow.data.WFTimePeriod;
import com.eos.workflow.data.WFWorkItem;
import com.eos.workflow.omservice.WFParticipant;
import com.eos.workflow.omservice.WIParticipantInfo;
import com.primeton.workflow.api.PageCond;
import com.primeton.workflow.api.WFReasonableException;
import com.primeton.workflow.api.WFServiceException;

/**
 * @author Administrator
 * 
 */
public interface IBaseBpsInterfaceMgr {

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
	 */
	public String createProcessInstance(String processDefName,
			String processInstName, String processInstDesc)
			throws WFServiceException;

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
	public void startProcessInstAndFinishFirstWorkItem(String processInstID,
			boolean isTransactionSplit, Object[] params)
			throws WFServiceException;

	/**
	 * @see 根据流程实例ID，批量设置相关数据中指定路径下的值
	 * @param processInstID
	 * @param relaDatas
	 * @throws WFServiceException
	 */

	/**
	 * @ 流程启动
	 */
	public long startProcess(String processDefName, String processInstName,
			String processInstDesc, boolean isTransactionSplit,
			Object[] params, WFParticipant parts[],
			Map<String, Object> relaDatas) throws WFServiceException, WFReasonableException;
	
	
	/**
	 * @ 流程启动
	 */
	public long startProcessWithBiz(String processDefName, String processInstName,
			String processInstDesc, boolean isTransactionSplit,
			Object[] params, WFParticipant parts[],
			Map<String, Object> relaDatas,String tableName,Map<String, Object> bizInfo) throws WFServiceException, WFReasonableException;
	
	/**
	 * @ 流程启动
	 */
	public long startProcessForDraft(String processDefName, String processInstName,
			String processInstDesc, boolean isTransactionSplit,
			Object[] params, WFParticipant parts[],
			Map<String, Object> relaDatas,String tableName,Map<String, Object> bizInfo) throws WFServiceException, WFReasonableException;

	/**
	 * @ 提交工作项
	 */
	public void submitTask(long workItemID, String activityDefID,
			boolean transactionSpan, WFParticipant parts[], String dealOpinion, String dealResult)
			throws WFServiceException, WFReasonableException;

	/**
	 * @ 获取待办
	 */
	public List<WFMyWorkItem> getMyWaitingTasks(String personId,
			String permission, String scope, PageCond pagecond)
			throws WFServiceException;

	/**
	 * @ 根据业务冗余数据获取待办
	 */
	public List<WFMyWorkItem> getMyWaitingTasksWithBizInfo(String personId,
			String permission, String scope, String bizTableName,
            String wiSql, String bizSql, List<String> wiBindList, List<String> bizBindList,PageCond pagecond)
			throws WFServiceException;
	/**
	 * @ 获取待办记录数
	 */
	public int getMyWaitingTaskCount(String personId, String permission,
			String scope) throws WFServiceException;

	/**
	 * @ 结合业务冗余数据获取待办记录数
	 */
	public int getMyWaitingTaskCountWithBizInfo(String personId, String permission,String bizTableName,
            String wiSql, String bizSql, List<String> wiBindList, List<String> bizBindList,
			String scope) throws WFServiceException;

	/**
	 * @ 获取已办
	 */
	public List<WFMyWorkItem> getMyCompletedTasks(String personId,
			String permission, boolean forHistory, PageCond pageCond)
			throws WFServiceException;

	/**
	 * @ 结合业务冗余数据获取已办
	 */
	public List<WFMyWorkItem> getMyCompletedTasksWithBizInfo(String personId,
			String permission, boolean forHistory, String bizTableName,
            String wiSql, String bizSql, List<String> wiBindList, List<String> bizBindList, PageCond pageCond)
					throws WFServiceException;

	/**
	 * @throws WFServiceException
	 *             @ 获取已办记录数
	 */
	public int getMyCompletedTaskCount(String personId, String permission,
			boolean forHistory) throws WFServiceException;

	/**
	 * 结合业务冗余数据获取已办记录数
	 * @throws WFServiceException
	 */
	public int getMyCompletedTaskCountWithBizInfo(String personId, String permission,String bizTableName,
            String wiSql, String bizSql, List<String> wiBindList, List<String> bizBindList,
			boolean forHistory) throws WFServiceException;

	/**
	 * @see 根据流程实例ID，批量设置相关数据中指定路径下的值
	 * @param processInstID
	 * @param relaDatas
	 * @throws NumberFormatException
	 * @throws WFServiceException
	 */
	public void setRelativeDataBatch(String processInstID,
			Map<java.lang.String, java.lang.Object> relaDatas)
			throws WFServiceException;

	/**
	 * @see 根据业务流程名称，和活动定义ID查询活动的后继活动连线信息
	 * @param processDefName
	 *            业务流程名称
	 * @param activityID
	 *            活动实例ID
	 * @return
	 * @throws WFServiceException
	 */
	public List<WFConnector> queryNextTransition(String processDefName,
			String activityID) throws WFServiceException;

	/**
	 * @see 根据业务流程ID，和活动定义ID查询活动的后继活动连线信息.
	 * @param processDefID
	 * @param activityID
	 * @return
	 * @throws WFServiceException
	 */
	public List<WFConnector> queryNextTransition(long processDefID,
			String activityID) throws WFServiceException;

	/**
	 * @see 查询出当前活动完成后生成下一个活动的工作项,用在新增流程时
	 * @param processInstID
	 *            流程实例ID
	 * @param fetchParticipant
	 *            工作项中是否否包含参与者，ture为包含参与者
	 * @return 工作项ID
	 * @throws WFServiceException
	 */
	public List<WFWorkItem> queryNextWorkItemsByProcessInstID(
			long processInstID, boolean fetchParticipant)
			throws WFServiceException;

	/**
	 * @see 查询出已完成的工作项生成的后继活动的工作项
	 * @param workItemID
	 *            工作项ID
	 * @param fetchParticipant
	 *            工作项中是否否包含参与者，ture为包含参与者
	 * @return
	 * @throws WFServiceException
	 */
	public List<WFWorkItem> queryNextWorkItemsByWorkItemID(long workItemID,
			boolean fetchParticipant) throws WFServiceException;

	/**
	 * @see 查询指定工作项的参与者信息
	 * @param workItemID
	 *            工作项ID
	 * @return
	 * @throws WFServiceException
	 */
	public List<WIParticipantInfo> queryWorkItemParticipantInfo(long workItemID)
			throws WFServiceException;

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
	public void finishWorkItemWithRelativeData(long workItemID,
			java.util.Map<java.lang.String, java.lang.Object> map,
			boolean transactionSpan) throws WFServiceException;

	/**
	 * @see 完成指定工作项
	 * @param workItemID
	 *            工作项ID
	 * @param transactionSpan
	 *            参数为true，则启用事务分割；参数为false，则不启用事务分割
	 * @return
	 * @throws WFServiceException
	 */
	public void finishWorkItem(long workItemID, boolean transactionSpan)
			throws WFServiceException, WFReasonableException;

	/*
	 * 向流程图设置相关数据，判断流程走向
	 * 
	 * @param processInstId 流程实例ID
	 * 
	 * @param lineName 流程图线上条件的名字
	 * 
	 * @param controlValue 判断流程走向的值 如 1通过 2不通过
	 */
	public void setRelativeData(long processInstId, String lineName,
			Object relativeData) throws WFServiceException;

	/**
	 * @see 转派
	 * @throws WFServiceException
	 */
	public void forwardTask(WFWorkItem item, WFParticipant[] participantArr)
			throws WFServiceException;

	/**
	 * @see 协办
	 * @throws WFServiceException
	 */
	public void delegateTask(WFWorkItem item, WFParticipant parts[])
			throws WFServiceException;

	/**
	 * @see 修改任务预订完成时间
	 * @throws WFServiceException
	 */
	public void resetTaskTimeOut(WFWorkItem item, WFTimePeriod start,
			WFTimePeriod end) throws WFServiceException;

	/**
	 * 获取当前环节的流程基本信息(动作/路由/链接等)
	 * 
	 * @param WFWorkItem
	 *            当前工作项
	 */
	public String getActivityExtendAttributes(WFWorkItem item)
			throws WFServiceException;

	/**
	 * 获取指定流程模型的指定环节的流程基本信息(动作/路由/链接等)
	 * 
	 * @param processModelName
	 *            流程模型名称
	 * @param activityDefID
	 *            环节名称
	 */
	public String getActivityExtendAttributes(String processModelName,
			String activityDefID) throws WFServiceException;

	/**
	 * 获取流程业务字段信息
	 * 
	 * @param processModelName
	 *            流程模型名称
	 * @param keys
	 *            业务字段名
	 */
	public List<Object> getRelativeData(WFWorkItem item, List<String> keys)
			throws WFServiceException;

	/**
	 * 获取流程业务字段信息
	 * 
	 * @param processModelName
	 *            流程模型名称
	 * @param keys
	 *            业务字段名
	 */
	public Object getRelativeData(long processInstID, String key)
			throws WFServiceException;

	/**
	 * 获取流程的详细信息
	 * 
	 * @param processInstId
	 *            流程实例ID
	 * @return
	 * @throws WFServiceException
	 */
	public WFProcessInst getProcessInstByPrsIstId(long processInstId)
			throws WFServiceException;

	/**
	 * 获取流程实例id获取流程环节列表
	 */
	public List<WFActivityInst> getAcitityListByPrsIstId(long processInstId,
			PageCond pageCond) throws WFServiceException;

	/**
	 * 获取流程实例id获取对应工作项列表
	 */
	public List<WFWorkItem> getWorkItemListByActIstId(long activityInstID,
			PageCond pc) throws WFServiceException;

	/**
	 * @ 根据工作项id设置时限
	 */

	public int setWorkItemTimeLimit(long workItemID, WFTimePeriod timeLimit,
			WFTimePeriod remindTime) throws WFServiceException;

	/**
	 * @ 根据流程实例ID获取启动的流程时产生的新工作项列表，仅限流程启动使用
	 */
	public List<WFWorkItem> getNextWorkItemListByProcessInstId(
			long processInstId) throws WFServiceException;

	/**
	 * 删除流程实例，目前只有测试驱动用，其他慎用
	 * 
	 * @param processInstId
	 * @throws WFServiceException
	 */
	public void deleteProcessInstById(long processInstId)
			throws WFServiceException;

	/**
	 * 代理管理接口
	 * 
	 * @throws WFServiceException
	 */
	public IWFAgentManager getAgentManager() throws WFServiceException;

	/**
	 * 定义管理接口
	 * 
	 * @throws WFServiceException
	 */
	public IWFDefinitionQueryManager getDefinitionQueryManager()
			throws WFServiceException;

	/**
	 * 工作项管理接口
	 * 
	 * @throws WFServiceException
	 */

	public IWFWorkItemManager getWorkItemManager() throws WFServiceException;
	
	
	/**
	 * 代办管理接口
	 * 
	 * @throws WFServiceException
	 */

	public IWFDelegateManager getDelegateManager() throws WFServiceException;
	
	/**
	 * 回退管理接口
	 * 
	 * @throws WFServiceException
	 */

	public IWFBackActivityManager getBackActivityManager() throws WFServiceException;
	
	/**
	 * 工作项查询管理接口
	 * 
	 * @throws WFServiceException
	 */

	public IWFWorklistQueryManager getWorklistQueryManager() throws WFServiceException;
	
	/**
	 * 流程实例管理接口
	 * 
	 * @throws WFServiceException
	 */
	public IWFProcessInstManager getProcessInstManager() throws WFServiceException;
	
	/**
	 * 活动实例管理接口
	 * 
	 * @throws WFServiceException
	 */
	public IWFActivityInstManager getActivityInstManager() throws WFServiceException;
	
	/**
	 * 通用服务接口
	 * 
	 * @throws WFServiceException
	 */
	public IWFCommonManager getCommonManager() throws WFServiceException;
	
	/**
	 * 通用业务字段查询接口
	 * 
	 * @throws WFServiceException
	 
	public <T> Object getCommonBizField(  String key,Object o ) throws WFServiceException;
 
	 * 通用业务字段存储接口
	 * 
	 * @throws WFServiceException
	 
	public void setCommonBizField( String key,Object o ) throws WFServiceException;*/
	
	/**
	 * 修改流程实例名称
	 * @param processId
	 * @param newName
	 * @throws WFServiceException
	 */
	public void changeProcessName(long processId, String newName) throws WFServiceException;
}
