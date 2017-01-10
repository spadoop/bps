/**
 * 
 */
package com.kingmed.pl.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;












import java.util.Map;

import cn.com.deloitte.si.bpm.entity.WFMyWorkItem;
import cn.com.deloitte.si.bpm.service.IBaseBpsInterfaceMgr;
import cn.com.deloitte.si.bpm.service.impl.BaseBpsInterfaceMgrImpl; 
















import com.eos.workflow.api.IWFWorkItemManager;
import com.eos.workflow.data.WFActivityInst;
import com.eos.workflow.data.WFWorkItem;
import com.kingmed.pl.common.vo.SearchTaskVO;
import com.kingmed.pl.common.vo.User;
import com.kingmed.pl.common.vo.WfVo;
import com.primeton.workflow.api.PageCond;
import com.primeton.workflow.api.WFServiceException;
/**
 * Bps 的管理类 包括待办和已办查询
 * 
 * @author Zhong Chong-Jun
 * 
 */
public class BpsQueryUtil {
	

	/**
	 * 管理类
	 */
	IBaseBpsInterfaceMgr bpsMgr;

	/**
	 * 当前处理人
	 */
	User user;

	/**
	 *  查询条件
	 */
	SearchTaskVO searchTaskVO;
	
	/**
	 *  开始页
	 */
	int beginIndex;
	
	/**
	 *  每页条数
	 */
	int pageCount;

	public BpsQueryUtil(User user, SearchTaskVO searchTaskVO,int beginIndex,int pageCount) {
		super();
		if(user==null){
			user = new User();
			user.setUserCode("tiger");
			user.setUserName("tiger");
		} 
		this.user = user;
		
		this.searchTaskVO = searchTaskVO;
		this.beginIndex = beginIndex;
		this.pageCount = beginIndex;
		if (bpsMgr == null)
			bpsMgr = new BaseBpsInterfaceMgrImpl(user.getUserCode(),user.getUserName());
	}

	/**
	 * 查询待办列表
	 * 
	 */
	public List<WFMyWorkItem> getMyUndoTasksWithBizInfo(User user, SearchTaskVO searchTaskVO, int beginIndex, int pageCount) {
		List<WFMyWorkItem> wfWorkItems = null;
		try {
			PageCond pc = new PageCond(pageCount);
			pc.setBeginIndex((beginIndex - 1) * pageCount);

			BizInfoParams infoParams = getBizInfoParams(searchTaskVO, user);
			
			if((infoParams.sqlBizInfo == null || infoParams.sqlBizInfo.toString().equals("")) &&
					(infoParams.sqlWiInfo == null || infoParams.sqlWiInfo.toString().equals(""))){ //按照没有条件的来查
				wfWorkItems = new BaseBpsInterfaceMgrImpl(
					user.getUserId(), user.getUserName()).getMyWaitingTasks(user.getUserId(), "ALL",
					"ALL", pc);
			}else{
				wfWorkItems = new BaseBpsInterfaceMgrImpl(
					user.getUserId(), user.getUserName()).getMyWaitingTasksWithBizInfo(user.getUserId(), "ALL",
					"ALL", "T_FORM_MAIN", infoParams.sqlWiInfo.toString(), infoParams.sqlBizInfo.toString(), infoParams.wiBindList, infoParams.bizBindList, pc);
			}
		} catch (Exception e) {
			//e.printStackTrace();
			throw new RuntimeException("查询用户[" + user.getUserId() + "]的待办发生错误");
		}

		return wfWorkItems;
	}
	
	/**
	 * 查询待办数量
	 * 
	 */
	public int getMyWaitingTaskCountWithBizInfo(User user, SearchTaskVO searchTaskVO) {
		
		try {
			BizInfoParams infoParams = getBizInfoParams(searchTaskVO, user);
			int cn = 0;
			if((infoParams.sqlBizInfo == null || infoParams.sqlBizInfo.toString().equals("")) &&
					(infoParams.sqlWiInfo == null || infoParams.sqlWiInfo.toString().equals(""))){ //按照没有条件的来查
				cn = new BaseBpsInterfaceMgrImpl(
						user.getUserId(), user.getUserName()).getMyWaitingTaskCount(user.getUserId(), "ALL","ALL");
			}else{
				cn = new BaseBpsInterfaceMgrImpl(
						user.getUserId(), user.getUserName()).getMyWaitingTaskCountWithBizInfo(user.getUserId(),
								"ALL", "T_FORM_MAIN", infoParams.sqlWiInfo.toString(), infoParams.sqlBizInfo.toString(), infoParams.wiBindList, infoParams.bizBindList, "ALL");
			}
			
			return cn; 

		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	/**
	 * 查询已办列表
	 * 
	 */
	public List<WFMyWorkItem> getMyCompletedTasksWithBizInfo(User user, SearchTaskVO searchTaskVO, int beginIndex, int pageCount) {
		
		List<WFMyWorkItem> wfWorkItems = null;
		try {
			PageCond pc = new PageCond(pageCount);
			pc.setBeginIndex((beginIndex - 1) * pageCount);

			BizInfoParams infoParams = getBizInfoParams(searchTaskVO, user);
			
			if((infoParams.sqlBizInfo == null || infoParams.sqlBizInfo.toString().equals("")) &&
					(infoParams.sqlWiInfo == null || infoParams.sqlWiInfo.toString().equals(""))){ //按照没有条件的来查
				wfWorkItems = new BaseBpsInterfaceMgrImpl(
						user.getUserId(), user.getUserName()).getMyCompletedTasks(user.getUserId(),
						"ALL", false/* 不查历史 */, pc);
			}else{
				wfWorkItems = new BaseBpsInterfaceMgrImpl(
					user.getUserId(), user.getUserName()).getMyCompletedTasksWithBizInfo(user.getUserId(),
							"ALL", false/* 不查历史 */, "T_FORM_MAIN", infoParams.sqlWiInfo.toString(), infoParams.sqlBizInfo.toString(), infoParams.wiBindList, infoParams.bizBindList, pc);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("查询用户[" + user.getUserId() + "]的已办发生错误");
		}

		return wfWorkItems;
	}
	
	/**
	 * 查询已办数量
	 * 
	 */
	public int getMyCompletedTaskCountWithBizInfo(User user, SearchTaskVO searchTaskVO) {
		try {
			BizInfoParams infoParams = getBizInfoParams(searchTaskVO, user);
			int cn = 0;
			if((infoParams.sqlBizInfo == null || infoParams.sqlBizInfo.toString().equals("")) &&
					(infoParams.sqlWiInfo == null || infoParams.sqlWiInfo.toString().equals(""))){ //按照没有条件的来查
				cn = new BaseBpsInterfaceMgrImpl(
						user.getUserId(), user.getUserName()).getMyCompletedTaskCount(user.getUserId(),
						"ALL", false/* 不查历史 */);
			}else{
				cn = new BaseBpsInterfaceMgrImpl(
						user.getUserId(), user.getUserName()).getMyCompletedTaskCountWithBizInfo(user.getUserId(),
								"ALL", "T_FORM_MAIN", infoParams.sqlWiInfo.toString(), infoParams.sqlBizInfo.toString(), infoParams.wiBindList, infoParams.bizBindList, false/* 不查历史 */);
			}
			
			return cn; 

		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	/*
	 * WFBizInfo表查询
	 */
	private BizInfoParams getBizInfoParams(SearchTaskVO searchTaskVO, User user){
		BizInfoParams bizInfoParams = new BizInfoParams();
		
		List<String> bizBindList = new ArrayList<String>();
		List<String> wiBindList = new ArrayList<String>();
		
		StringBuilder sqlBizInfo = new StringBuilder();
		StringBuilder sqlWiInfo = new StringBuilder();
		if(searchTaskVO.getReqType() != null && !searchTaskVO.getReqType().equals("")){ //req_type
			bizBindList.add(searchTaskVO.getReqType());
			sqlBizInfo.append("REQ_TYPE = ? and ");
		}
		if(searchTaskVO.getFormSeq() != null && !searchTaskVO.getFormSeq().equals("")){ //form_seq
			bizBindList.add("%" + StaticMethod.removeBlank(searchTaskVO.getFormSeq()) + "%");
			sqlBizInfo.append("FORM_SEQ like ? and ");
		}
		if(searchTaskVO.getFormTitle() != null && !searchTaskVO.getFormTitle().equals("")){ //form_title
			bizBindList.add("%" + StaticMethod.removeBlank(searchTaskVO.getFormTitle()) + "%");
			sqlBizInfo.append("FORM_TITLE like ? and ");
		}
		if(searchTaskVO.getSendUserId() != null && !searchTaskVO.getSendUserId().equals("")){ //send_user_id
			bizBindList.add("%" + StaticMethod.removeBlank(searchTaskVO.getSendUserId()) + "%");
			sqlBizInfo.append("SEND_USER_ID like ? and ");
		}
		if(searchTaskVO.getScope() != null && !searchTaskVO.getScope().equals("") && !searchTaskVO.getScope().equals("ALL")){ //scope
			if(searchTaskVO.getScope().equals("SENT")){ // 取我的发起，即发起人是该用户的所有流程中的已办
				bizBindList.add(user.getUserId());
				sqlBizInfo.append("SEND_USER_ID = ? and ");
			}
			if(searchTaskVO.getScope().equals("PROCESS")){// 取我的处理，即发起人不是该用户的所有流程中的已办
				bizBindList.add(user.getUserId());
				sqlBizInfo.append("SEND_USER_ID <> ? and ");
			}
		}
		if(searchTaskVO.getFormStatus() != null && !searchTaskVO.getFormStatus().equals("")){ //form_status
			bizBindList.add( searchTaskVO.getFormStatus());
			sqlBizInfo.append("FORM_STATUS = ? and ");
		}
		if(searchTaskVO.getFromSendTime() != null && !searchTaskVO.getFromSendTime().equals("")){ //send_time
			bizBindList.add(Long.toString(searchTaskVO.getFromSendTime().getTime()));
			sqlBizInfo.append("SEND_TIME >= ? and ");
		}
		if(searchTaskVO.getToSendTime() != null && !searchTaskVO.getToSendTime().equals("")){ //send_time
			bizBindList.add(Long.toString(searchTaskVO.getToSendTime().getTime()));
			sqlBizInfo.append("SEND_TIME <= ? and ");
		}

		if(searchTaskVO.getIsTimeOut() != null && !searchTaskVO.getIsTimeOut().equals("") && !searchTaskVO.getIsTimeOut().equals("ALL")){ //isTimeout
			if(searchTaskVO.getIsTimeOut().equals("Y")){				
				wiBindList.add("Y");
				sqlWiInfo.append("isTimeOut = ? and ");
			}
			if(searchTaskVO.getIsTimeOut().equals("N")){
				wiBindList.add("N");
				sqlWiInfo.append("isTimeOut = ? and ");
			}
		}

		if(bizBindList.size() > 0){
			sqlBizInfo.delete(sqlBizInfo.length() - "and ".length(), sqlBizInfo.length());
		}
		if(wiBindList.size() > 0){
			sqlWiInfo.delete(sqlWiInfo.length() - "and ".length(), sqlWiInfo.length());
		}
				
		bizInfoParams.bizBindList = bizBindList;
		bizInfoParams.wiBindList = wiBindList;
		bizInfoParams.sqlBizInfo = sqlBizInfo;
		bizInfoParams.sqlWiInfo = sqlWiInfo;
		
		return bizInfoParams;
	}
	
	static class BizInfoParams{
		List<String> bizBindList = null;
		List<String> wiBindList = null;
		StringBuilder sqlBizInfo = null;
		StringBuilder sqlWiInfo = null;
	}
	
	/**
	 * 查询表单历史
	 * 
	 */
	public List<WFWorkItem> getProcHistory(long procInsID, String activityDefID) {
		
		List<WFActivityInst> wfActivityInst = null;
		List<WFWorkItem> wfWorkItems = new ArrayList<WFWorkItem>();
		try {
			PageCond pc = new PageCond(pageCount);
			pc.setBeginIndex((beginIndex - 1) * pageCount);
			
			IBaseBpsInterfaceMgr bpsInterface = new BaseBpsInterfaceMgrImpl(
					user.getUserId(), user.getUserName());
			wfActivityInst = bpsInterface.getAcitityListByPrsIstId(procInsID, null);
			
			for(WFActivityInst act:wfActivityInst){
				if(activityDefID!=null && !activityDefID.equals(act.getActivityDefID()))
					continue;
				
				wfWorkItems.addAll(bpsInterface.getWorkItemListByActIstId(act.getActivityInstID(), null));
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("查询流程实例[" + procInsID + "]的历史时发生错误");
		}

		return wfWorkItems;
	}
	
	/**
	 * 查询表单历史
	 * 多态
	 */
	public List<WFWorkItem> getProcHistory(long procInsID ) {
		return this.getProcHistory(procInsID, null);
	}
	
	/**
	 * 获取流程业务字段信息
	 * 
	 * @param list
	 *            工作信息
	 * @param keys
	 *            业务字段名
	 */
	public Map<Long,String> getRelativeDataBatch(List<WFWorkItem> list, String key)
			throws WFServiceException {

		Map<Long,String> result = new HashMap<Long,String>();
		for(WFWorkItem item:list){
			IBaseBpsInterfaceMgr bpsInterface = new BaseBpsInterfaceMgrImpl(
					user.getUserId(), user.getUserName());
			
			result.put(item.getProcessInstID(),
					(String) bpsInterface.getRelativeData(item.getProcessInstID(), key));
		}
		return result;
	}
	
	/**
	 * 获取流程当前所有参与者
	 *
	 */
	public List<String> getCurrentParticipants(long pID)
			throws WFServiceException {

	 
		IBaseBpsInterfaceMgr bpsInterface = new BaseBpsInterfaceMgrImpl(
				user.getUserId(), user.getUserName());
		
		List<WFActivityInst> alist = bpsInterface.getAcitityListByPrsIstId(pID,  new PageCond() );

		List<String> result = new ArrayList<String>();
		
		IWFWorkItemManager wim = bpsInterface.getWorkItemManager();
		
		for(WFActivityInst a:alist){
			if(a.getCurrentState()==2){
				List<WFWorkItem> wlist = 
					bpsInterface.getWorkItemListByActIstId(a.getActivityInstID(), new PageCond());
				
				for(WFWorkItem w:wlist){
					result.add (wim.queryWorkItemParticipantInfo(w.getWorkItemID()).get(0).getId());		
				}
				break;
			}
		}
		
		return result;
	}
	
	public static void main(String...arg) throws WFServiceException{

		User usr = new User();
		usr.setUserId("c777dfc1-2aa6-42f6-ac1b-38356f10bf14");
		usr.setUserName("xiaoli");

		WfVo wfVo = new WfVo();
		wfVo.setProcessDefName("cn.kingmed.bpm.customer.archivesInfoAudit");
		//wfVo.setWorkitemId(43); 

		SearchTaskVO searchTaskVO = new SearchTaskVO(); 
		BpsQueryUtil bpsUtil = new BpsQueryUtil( null,null,0,10);//usr, searchTaskVO, 0, 10 );
		
		//searchTaskVO.setFormTitle("名称0f858efe-6e24-42ea-9837-ab873c9a161f");
		//System.out.println(bpsUtil.getMyWaitingTaskCountWithBizInfo(usr, searchTaskVO));		
		//System.out.println(bpsUtil.getMyCompletedTaskCountWithBizInfo(usr, searchTaskVO));
		//new BpsQueryUtil(usr, searchTaskVO, 0, 10 ).getMyUndoTasksWithBizInfo(usr, searchTaskVO, 0, 10);
//		List<WFMyWorkItem> result = bpsUtil.getMyUndoTasksWithBizInfo(usr, searchTaskVO, 0, 10);
// 		List<WFWorkItem> result = bpsUtil.getRelativeDataBatch(list, key);

		System.out.println(bpsUtil.getCurrentParticipants( 678).size());
		/*for(WFWorkItem item:result){
			System.out.println(item.getWorkItemName() +" "+item.getProcessInstID() +" "+item.getActivityDefID()
					+" "+item.getPartiName()+" "+item.getCreateTime()+" "+item.getStartTime()+" "+item.getEndTime()+" "+item.getCurrentState()
					+" "+item.getActionURL());			
		}
		*/
	}
	
	
}
