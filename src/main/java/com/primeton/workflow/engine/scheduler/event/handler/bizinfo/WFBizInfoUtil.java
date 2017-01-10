/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/
package com.primeton.workflow.engine.scheduler.event.handler.bizinfo;

import com.primeton.workflow.engine.common.exception.WFEngineException;
import com.primeton.workflow.service.bizinfo.IWFBizInfoCacheService;
import com.primeton.workflow.service.bizinfo.WFBizTransferInfo;
import com.primeton.workflow.service.das.model.entities.WFBizInfo;
import com.primeton.workflow.service.framework.WFServiceFactory;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.apache.commons.beanutils.PropertyUtils;

public class WFBizInfoUtil {
	public static WFBizInfo buildMappedBizInfo(String bizTableName,
			WFBizInfo info, Map<String, Object> bizInfoMap) {
		Map relation = ((IWFBizInfoCacheService) WFServiceFactory
				.getService(IWFBizInfoCacheService.class))
				.getBizMapping(bizTableName);

		for (Iterator iter = bizInfoMap.entrySet().iterator(); iter.hasNext();) {
			Map.Entry element = (Map.Entry) iter.next();
			String wfFieldName = (String) relation.get(element.getKey());
			Object value = element.getValue();
			try {
				PropertyUtils.setProperty(info, wfFieldName, value);
			} catch (IllegalAccessException e) {
				new WFEngineException(e);
			} catch (InvocationTargetException e) {
				new WFEngineException(e);
			} catch (NoSuchMethodException e) {
				new WFEngineException(e);
			}
		}
		return info;
	}

	public static boolean checkBizInfoValid(String bizTableName,
			Map<String, Object> bizInfoMap) throws WFEngineException {
		Map relation = ((IWFBizInfoCacheService) WFServiceFactory
				.getService(IWFBizInfoCacheService.class))
				.getBizMapping(bizTableName);

		boolean valid = true;
		for (Iterator ite = bizInfoMap.entrySet().iterator(); ite.hasNext();) {
			Map.Entry element = (Map.Entry) ite.next();
			if (!(relation.containsKey(element.getKey()))) {
				valid = false;
				break;
			}
		}
		return valid;
	}

	public static WFBizTransferInfo buildBizTransferInfo(String bizTableName,
			Map<String, Object> bizInfoMap) {
		WFBizTransferInfo bizTransferInfo = new WFBizTransferInfo();
		String updateSql = " bizTableName=? ";
		List bindValues = new ArrayList();
		List bindTypes = new ArrayList();
		bindValues.add(bizTableName);
		bindTypes.add(Integer.valueOf(12));
		Map relation = ((IWFBizInfoCacheService) WFServiceFactory
				.getService(IWFBizInfoCacheService.class))
				.getBizMapping(bizTableName);

		for (Iterator iter = bizInfoMap.entrySet().iterator(); iter.hasNext();) {
			Map.Entry element = (Map.Entry) iter.next();
			String wfFieldName = (String) relation.get(element.getKey());
			Object value = element.getValue();
			updateSql = updateSql + ", " + wfFieldName + "=? ";
			bindValues.add(value);
			if (wfFieldName.startsWith("nm"))
				bindTypes.add(Integer.valueOf(2));
			else {
				bindTypes.add(Integer.valueOf(12));
			}
		}
		bizTransferInfo.setUpdateSql(updateSql);
		bizTransferInfo.setBindValues(bindValues);
		bizTransferInfo.setBindTypes(bindTypes);
		return bizTransferInfo;
	}

	public static WFBizTransferInfo buildUpdateSqlForDel() {
		WFBizTransferInfo bizTransferInfo = new WFBizTransferInfo();
		String sql = " ";
		List bindValues = new ArrayList();
		List bindTypes = new ArrayList();
		Field[] declaredFields = WFBizInfo.class.getDeclaredFields();
		for (int i = 0; i < declaredFields.length; ++i) {
			Field field = declaredFields[i];
			String name = field.getName();
			if (!(name.equals("processInstID"))) {
				sql = sql + name + "=? ,";
				if (name.startsWith("nm")) {
					bindValues.add(Integer.valueOf(0));
					bindTypes.add(Integer.valueOf(2));
				} else if (name.startsWith("dt")) {
					bindValues.add(null);
					bindTypes.add(Integer.valueOf(93));
				} else {
					bindValues.add("");
					bindTypes.add(Integer.valueOf(12));
				}
			}
		}
		sql = sql.substring(0, sql.length() - 1);
		bizTransferInfo.setUpdateSql(sql);
		bizTransferInfo.setBindValues(bindValues);
		bizTransferInfo.setBindTypes(bindTypes);
		return bizTransferInfo;
	}
}

