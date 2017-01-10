/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/
package com.primeton.workflow.engine.scheduler.event.handler.bizinfo;

import com.primeton.workflow.api.PageCond;
import com.primeton.workflow.engine.common.exception.WFEngineException;
import com.primeton.workflow.event.framework.WFBizEvent;
import com.primeton.workflow.event.framework.WFEvent;
import com.primeton.workflow.event.framework.WFEventHandler;
import com.primeton.workflow.model.exception.WFException;
import com.primeton.workflow.service.bizinfo.IWFBizInfoCacheService;
import com.primeton.workflow.service.bizinfo.WFBizTransferInfo;
import com.primeton.workflow.service.das.DataAccessService;
import com.primeton.workflow.service.das.SQLTemplate;
import com.primeton.workflow.service.das.exception.DataAccessException;
import com.primeton.workflow.service.das.model.entities.WFBizInfo;
import com.primeton.workflow.service.framework.WFServiceFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class WFBizEventHandler implements WFEventHandler {
	public void invoke(WFEvent event) throws WFException {
		WFBizEvent bizEvent = (WFBizEvent) event;
		if (bizEvent.getType() == 80001)
			insertBizInfo(bizEvent);
		else if (bizEvent.getType() == 80002)
			updateBizInfo(bizEvent);
		else if (bizEvent.getType() == 80003)
			deleteBizInfo(bizEvent);
	}

	private void insertBizInfo(WFBizEvent bizEvent) throws DataAccessException,
			WFException {
		Map bizInfoMap = bizEvent.getBizInfo();
		String bizTableName = bizEvent.getBizTableName();
		if (!(WFBizInfoUtil.checkBizInfoValid(bizTableName, bizInfoMap))) {
			throw new WFEngineException(
					"Business table mapping configuration error");
		}
		WFBizInfo bizInfo = new WFBizInfo();
		long processInstID = bizEvent.getProcInstID();
		bizInfo.setProcessInstID(processInstID);
		bizInfo.setBizTableName(bizTableName);
		WFBizInfoUtil.buildMappedBizInfo(bizTableName, bizInfo, bizInfoMap);
System.out.println(" ~~~ "+bizTableName);		
System.out.println(" ~~~ "+bizInfoMap);		
System.out.println(" ~~~ "+bizInfo.getVcColumn1() );
	
		DataAccessService accessServiceImpl = (DataAccessService) WFServiceFactory
				.getService(DataAccessService.class);
		accessServiceImpl.insertObject(bizInfo);
	}

	private void updateBizInfo(WFBizEvent bizEvent) throws WFException {
		try {
			long procInstID = bizEvent.getProcInstID();

			WFBizInfo bizInfo = new WFBizInfo();
			long processInstID = procInstID;
			bizInfo.setProcessInstID(processInstID);
			DataAccessService accessServiceImpl = (DataAccessService) WFServiceFactory
					.getService(DataAccessService.class);
			List bizList = accessServiceImpl.findBeanList(bizInfo,
					new PageCond(2147483647));
			if ((bizList != null) && (bizList.size() > 0))
				bizInfo = (WFBizInfo) bizList.get(0);
			else {
				throw new WFEngineException(
						"Process instance has not redundancy of data, cannot be updated");
			}
			String bizTableName = bizInfo.getBizTableName();
			Map bizInfoMap = bizEvent.getBizInfo();
			if (!(WFBizInfoUtil.checkBizInfoValid(bizTableName, bizInfoMap))) {
				throw new WFEngineException(
						"Business table mapping configuration error");
			}
			WFBizInfoUtil.buildMappedBizInfo(bizTableName, bizInfo, bizInfoMap);
			accessServiceImpl.updateObject(bizInfo);
		} catch (DataAccessException e) {
			new WFEngineException(e);
		}
	}

	private WFBizTransferInfo synchronizeBizInfoCache(String bizTableName,
			Map<String, Object> bizInfoMap, long processInstID) {
		IWFBizInfoCacheService bizInfoCacheService = (IWFBizInfoCacheService) WFServiceFactory
				.getService(IWFBizInfoCacheService.class);
		WFBizTransferInfo bizTransferInfo = WFBizInfoUtil.buildBizTransferInfo(
				bizTableName, bizInfoMap);
		bizInfoCacheService.setBizInfoUpdateSql(processInstID, bizTransferInfo);
		bizInfoCacheService.bizInfoUpdateSqlChanged(processInstID);
		return bizTransferInfo;
	}

	private void deleteBizInfo(WFBizEvent bizEvent) {
		DataAccessService accessServiceImpl = (DataAccessService) WFServiceFactory
				.getService(DataAccessService.class);
		try {
			WFBizInfo bizInfo = new WFBizInfo();
			long processInstID = bizEvent.getProcInstID();
			bizInfo.setProcessInstID(processInstID);
			List bizList = accessServiceImpl.findBeanList(bizInfo,
					new PageCond(2147483647));
			if ((bizList != null) && (bizList.size() > 0)) {
				bizInfo = (WFBizInfo) bizList.get(0);
			}
			accessServiceImpl.removeObject(bizInfo);
		} catch (DataAccessException e) {
			new WFEngineException(e);
		}
	}

	private void runUpdateSql(WFBizEvent bizEvent,
			WFBizTransferInfo bizTransferInfo, String sql) {
		List bindValues = new ArrayList();
		bindValues.addAll(bizTransferInfo.getBindValues());
		List bindTypes = new ArrayList();
		bindTypes.addAll(bizTransferInfo.getBindTypes());
		sql = sql + " WHERE processInstID=? ";
		bindValues.add(Long.valueOf(bizEvent.getProcInstID()));
		bindTypes.add(Integer.valueOf(2));
		SQLTemplate.execute(sql, bindValues, bindTypes);
	}
}