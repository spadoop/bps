package com.kingmed.pl.common;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.log4j.Logger;

import com.eos.workflow.omservice.IWFOMService;
import com.eos.workflow.omservice.WFParticipant;
import com.kingmed.pl.common.ConnectManager.SQLManager;
import com.kingmed.pl.common.vo.Constant;
import com.kingmed.pl.common.vo.User;
import com.primeton.workflow.api.PageCond;
import com.primeton.workflow.api.ParticipantType;
import com.primeton.workflow.api.WFServiceException;

/**
 * 
 * 获取流程参与者，参与者的所属组织与角色。 设计如下的表： 1. info_person 参与者 2. info_department &&
 * info_department_sub 组织机构 3. info_team 角色
 * 
 * 此类一般做单例调用
 * 
 * @author Huang, Hai
 * @author Zhong, An-Jing
 * 
 */
public class Fetch4AData extends _Cache implements IWFOMService {
	private Logger log = Logger.getLogger(Fetch4AData.class.getName());
	
	/**
	 * 内部静态类，实现单例
	 * 
	 * @author Zhong An-Jing
	 * 
	 */
	private static class SingletonHolder {
		private final static Fetch4AData INSTANCE = new Fetch4AData();
	}

	/**
	 * 单例实现
	 * 
	 * @return
	 */
	public static Fetch4AData getInstance() {
		return SingletonHolder.INSTANCE;
	}

	/**
	 * 单例实现，禁止直接访问
	 */
	public Fetch4AData() {
		log.info("Fetch4AData Constructor start");
		load();
		connManager = ConnectManager.getInstance();
		log.info("Fetch4AData Constructor end");
	}

	/**
	 * 模糊匹配参与者
	 */
	public List<WFParticipant> fuzzyFindParticipantByName(String typeCode,
			String participantName) {
		log.info("Fetch4AData::fuzzyFindParticipantByID: start");
		List<WFParticipant>  l = new ArrayList<WFParticipant>(); 
		if (!_checkCode(typeCode))
			return l;
//		if ("role".equals(typeCode))return new WFParticipant();
		String sql = "";
		if (typeCode.equals(Constant.ROLE)) {
			sql = SQLManager.getSql("roleAll");
		} else if (typeCode.equals(Constant.ORGANIZATION)) {
			sql = SQLManager.getSql("orgAll");
		} else if (typeCode.equals(Constant.PERSON)) {
			sql = SQLManager.getSql("personAll");
		} else if (typeCode.equals(Constant.POSITION)) {
			sql = SQLManager.getSql("positionAll");
		}
//		sql = "select tt.ID, tt.NAME, tt.TypeCode from (" + sql
//				+ " ) tt where tt.name like '%" + participantName + "%'";
		sql = "select tt.ID, tt.NAME, tt.TypeCode from (" + sql
				+ " ) tt where tt.name like ? ";
		
//		l = _execute(sql);
		
		LinkedHashMap<Integer, String> param = new LinkedHashMap<Integer, String>();
		param.put(1, "%" + participantName + "%");
		l = _execute2(sql, param);
		
		log.info("Fetch4AData::fuzzyFindParticipantByID: end");
		return l;
	}

	/**
	 * 查找指定的参与者 根据ID和类型获取相应参与者
	 */
	public WFParticipant findParticipantByID(String typeCode,
			String participantID) {
		log.info("Fetch4AData::findParticipantByID: start");
		if (!_checkCode(typeCode))
			return new WFParticipant();
//		if ("role".equals(typeCode))return new WFParticipant();
		String sql = "";
		WFParticipant p;
		if (typeCode.equals(Constant.ROLE)) {
			sql = SQLManager.getSql("roleAll");
		} else if (typeCode.equals(Constant.ORGANIZATION)) {
			sql = SQLManager.getSql("orgAll");
		} else if (typeCode.equals(Constant.PERSON)) {
			
//			p = CacheData.getFindParticipantByIDWithPerson(participantID);
//			
//			if(p != null){
//				log.info("Fetch4AData::findParticipantByID: end");
//				return p;
//			}
			
//			if(RedisUtil.exist("BPS.BIZ_DATA.findParticipantByID.person")){
//				return (WFParticipant)RedisUtil.getObject("BPS.BIZ_DATA.findParticipantByID.person");
//			}
			
//			redisService.getObject("BPS.BIZ_DATA.findParticipantByID.person");
			
			sql = SQLManager.getSql("personAll");
		} else if (typeCode.equals(Constant.POSITION)) {
			
//			p = CacheData.getFindParticipantByIDWithPosition(participantID);
//			
//			if(p != null){
//				log.info("Fetch4AData::findParticipantByID: end");
//				return p;
//			}
			
			sql = SQLManager.getSql("positionAll");
		}
		
		List<WFParticipant> l;
//		if(typeCode.equals(Constant.PERSON) || typeCode.equals(Constant.POSITION)){
			sql = "select tt.ID, tt.NAME, tt.TypeCode from (" + sql
					+ " ) tt where tt.id = ? ";
			
			LinkedHashMap<Integer, String> param = new LinkedHashMap<Integer, String>();
			param.put(1, participantID);
			l = _execute2(sql, param);
			
//		}else{
//			sql = "select tt.ID, tt.NAME, tt.TypeCode from (" + sql
//					+ " ) tt where tt.id = '" + participantID + "'";
//			l = _execute(sql);
//		}
		
		log.info("Fetch4AData::findParticipantByID: end");
		
		if (l.size() > 0) {
			
//			if(typeCode.equals(Constant.PERSON)){
//				CacheData.setFindParticipantByIDWithPerson(participantID, l.get(0));
//			}else if(typeCode.equals(Constant.POSITION)){
//				CacheData.setFindParticipantByIDWithPosition(participantID, l.get(0));
//			}
			
//			if(typeCode.equals(Constant.PERSON)){
//				RedisUtil.setObj("BPS.BIZ_DATA.findParticipantByID.person", l.get(0));
//			}
			
			return l.get(0);
		}
			
		return null;
	}

	/**
	 * 获取某类型下的根参与者<br>
	 * 该方法主要是指获取某类型下的根参与者。对于没有嵌套类型的，就返回所有的列表， 比如类型为角色，角色之间不嵌套，那么返回的就是整个角色列表。
	 * 比如：类型为机构，返回根机构，如果该类型的showAtRootArea为false,便是没有根。
	 */

	public List<WFParticipant> findRootParticipants(String typeCode) {
		log.info("Fetch4AData::findRootParticipants: start");
//		log.info("type :"+typeCode);
		String sql = "";
		List<WFParticipant>  l = new ArrayList<WFParticipant>(); 
		//|| "role".equals(typeCode)
//		if("organization".equals(typeCode)){
		if (!_checkCode(typeCode))
			return  l;
		if (typeCode.equals(ROLE)) {
			sql = SQLManager.getSql("roleAll");
		} else if (typeCode.equals(ORGANIZATION)) {
			sql = SQLManager.getSql("orgRoot");
		} else if (typeCode.equals(Constant.PERSON)) {
			sql = SQLManager.getSql("personAll");
		} else if (typeCode.equals(Constant.POSITION)) {
			sql = SQLManager.getSql("positionAll");
		}
//		l =_execute(sql);
		l = _execute2(sql, null);
		
		log.info("■■■■ size :"+ l.size());
//		}
		log.info("Fetch4AData::findRootParticipants: end");
		return l;
	}

	/**
	 * 查询所有下级参与者<br>
	 * 比如机构，则获取该机构下的子机构列表（不需要包含子机构的子机构）＋该机构下的用户＋该机构下的角色＋该机构下的岗位；
	 * 比如角色，则获取到该角色下的用户以及该角色下的子角色（如果角色有级别）。
	 * 
	 * == 东莞联通中，角色不存在层级关系
	 */

	public List<WFParticipant> getAllChildParticipants(String typeCode,
			String participantID) {
		log.info("Fetch4AData::getAllChildParticipants: start");
		List<WFParticipant> l = new ArrayList<WFParticipant>();
		if (!_checkCode(typeCode))
			return l;
		if (typeCode.equals(ROLE)) {
			l.addAll(getChildParticipants(typeCode, participantID, PERSON));
		}
		if (typeCode.equals(ORGANIZATION)) { // ，获取组织的下级与用户组织不需要递归加载,BPS已经实现
			l.addAll(getChildParticipants(typeCode, participantID, ORGANIZATION));
			l.addAll(getChildParticipants(typeCode, participantID, PERSON));
		}
		if (typeCode.equals(POSITION)) {
			l.addAll(getChildParticipants(typeCode, participantID, PERSON));
		}
		log.info("■■■■ size :"+ l.size());
		log.info("Fetch4AData::getAllChildParticipants: end");
		return l;
	}

	/**
	 * 查询所有上级参与者 查询所有上级参与者和查询所有下级参与者的概念是一致的，
	 * 组织机构中叶子参与者类型是只有上级没有下级的，比如用户类型只有上级机构、岗位、角色等，没有下级参与者类型。
	 */

	public List<WFParticipant> getAllParentParticipants(String typeCode,
			String participantID) {
		log.info("Fetch4AData::getAllParentParticipants: start");
		// TODO: 获取用户的组织机构，以及角色，需要此sql
		List<WFParticipant> l = new ArrayList<WFParticipant>();
		if (_checkCode(typeCode)){
			if (typeCode.equals(PERSON)) {
				// 获取人员的角色
				l.addAll(getParentParticipants(typeCode, participantID, ROLE));
				// 获取人员组织
				l.addAll(getParentParticipants(typeCode, participantID,
						ORGANIZATION));
			} else if (typeCode.equals(ORGANIZATION)) {
				l.addAll(getParentParticipants(typeCode, participantID,
						ORGANIZATION));
			}
		}
		log.info("■■■■ size :"+ l.size());
		log.info("Fetch4AData::getAllParentParticipants: end");
		return l;
	}

	/**
	 * 查询某类型的下属参与者<br>
	 * 比如机构的下属参与者包含用户、机构、岗位等， 但如果传入的childType为机构，则通过这个方法获取到的下属参与者类型都是机构。
	 * 如果childType为岗位，获取到的下属参与者类型为岗位
	 * 
	 * typcode: role, org
	 * 
	 */

	public List<WFParticipant> getChildParticipants(String typeCode,
			String participantID, String childType) {
		log.info("Fetch4AData::getChildParticipants: start");
		String sql = "";
		LinkedHashMap<Integer, String> param = new LinkedHashMap<Integer, String>();
		if (_checkCode(typeCode)){
			if (typeCode.equals(ROLE)) {
				if (childType.equals(PERSON)){
//					sql = String.format(SQLManager.getSql("roleUser"),
//							participantID);
					sql = SQLManager.getSql("roleUser");
//					param.put(1, participantID);
				}
				else if (childType.equals(ORGANIZATION)){
//					sql = String.format(SQLManager.getSql("roleOrg"), 
//							participantID);
					sql = SQLManager.getSql("roleOrg");
//					param.put(1, participantID);
				}
				else if (childType.equals(Constant.POSITION)){
//					sql = String.format(SQLManager.getSql("rolePosition"),
//							participantID);
					sql = SQLManager.getSql("rolePosition");
//					param.put(1, participantID);
					
				}
			} else if (typeCode.equals(ORGANIZATION)) {
				if (childType.equals(ORGANIZATION)){// 获取组织下的子组织
//					sql = String.format(SQLManager.getSql("orgChild"),
//							participantID);
					sql = SQLManager.getSql("orgChild");
//					param.put(1, participantID);
				}
				else if (childType.equals(PERSON)){ // 获取组织下的人员
//					sql = String.format(SQLManager.getSql("orgUser"), 
//							participantID, 
//							participantID);
					sql = SQLManager.getSql("orgUser");
					param.put(1, participantID);
//					param.put(2, participantID);
				}
				else if (childType.equals(ROLE)){
//					sql = String.format(SQLManager.getSql("orgRole"),
//							participantID);
					sql = SQLManager.getSql("orgRole");
//					param.put(1, participantID);
				}
				else if (childType.equals(Constant.POSITION)){
//					sql = String.format(SQLManager.getSql("orgPosition"),
//							participantID);
					sql = SQLManager.getSql("orgPosition");
//					param.put(1, participantID);
					
				}
			} else if (typeCode.equals(Constant.POSITION)) {
				if (childType.equals(PERSON)){
//					sql = String.format(SQLManager.getSql("positionUser"),
//							participantID);
					sql = SQLManager.getSql("positionUser");
					param.put(1, participantID);
				}
			}
		}
//		List<WFParticipant> l =_execute(sql);
		List<WFParticipant> l =_execute2(sql, param);
		log.info("■■■■ size :"+ l.size());
		log.info("Fetch4AData::getChildParticipants: end");
		return l;
	}

	/**
	 * 查询交集类型参与者的所有下属参与者 查询到不同类型的参与者集合中有相同的参与者，
	 * 比如张三这个用户既属于角色A，又属于机构A，那么角色A和机构A的交集中就有张三这个参与者，
	 * 查询到的参与者不一定全是叶子参与者，还有可能是一个集合，比如机构＋角色可以查询到很多岗位。
	 */

	public List<WFParticipant> getJointChildParticipant(String typeCode,
			List<String> jointType) {
		log.info("Fetch4AData::getJointChildParticipant: start");
		log.info("Fetch4AData::getJointChildParticipant: end");
		// TODO 暂不实现
		return null;
	}
	
	/**
	 * 查询某类型的上级参与者 查询某类型的上级参与者和查询某类型的下级参与者的概念是一致的，
	 * 比如机构的上级参与者包含用户、机构、岗位等，但如果传入的childCode为机构，则通过这个方法只会获取到上级类型为机构的参与者。
	 * 
	 * ==东莞联通价值管理项目，角色没有父角色
	 * 
	 */

	public List<WFParticipant> getParentParticipants(String typeCode,
			String participantID, String childType) {
		log.info("Fetch4AData::getParentParticipants: start");
		List<WFParticipant> l = new ArrayList<WFParticipant>();
		LinkedHashMap<Integer, String> param = new LinkedHashMap<Integer, String>();
		String sql = "";
		if (_checkCode(typeCode)){
			if (typeCode.equals(ORGANIZATION)) {
				if (childType.equals(ORGANIZATION)) {
//					sql = String.format(SQLManager.getSql("orgParent"),
//							participantID);
					sql = SQLManager.getSql("orgParent");
					param.put(1, participantID);
//					l.addAll(_execute(sql));
					l.addAll(_execute2(sql, param));
				}
			} else if (typeCode.equals(PERSON)) {
				if (childType.equals(ROLE)) { // 获取人员的角色
//					sql = String.format(SQLManager.getSql("userrole"),
//							participantID);
					sql = SQLManager.getSql("userrole");
//					param.put(1, participantID);
//					l.addAll(_execute(sql));
					l.addAll(_execute2(sql, param));
				}
				else if (childType.equals(ORGANIZATION)) {// 获取人员组织
//					sql = String.format(SQLManager.getSql("userOrg"), 
//							participantID);
					sql = SQLManager.getSql("userOrg");
					param.put(1, participantID);
//					l.addAll(_execute(sql));
					l.addAll(_execute2(sql, param));
				}
				else if (childType.equals(Constant.POSITION)) {// 获取人员岗位
//					sql = String.format(SQLManager.getSql("userPosition"),
//							participantID);
					sql = SQLManager.getSql("userPosition");
//					param.put(1, participantID);
//					l.addAll(_execute(sql));
					l.addAll(_execute2(sql, param));
				}
			}
		}
		log.info("■■■■ size :"+ l.size());
		log.info("Fetch4AData::getParentParticipants: end");
		return l;
	}

	/**
	 * 获取某个参与者的参与范围<br>
	 * 在参与者执行工作任务的时候，需要看是否存在权限， 那么对于某参与者所拥有的工作任务列表是否有直接执行的权限，
	 * 就看他是否在这个方法所获取的参与者范围之内， 一般只需要对叶子参与者类型实现该方法。
	 * 比如张三这个用户可以执行分配给他个人的工作项，也可以执行分配给他所属的角色的工作项， 还可以执行分配给他所在的机构的工作项，
	 * 那么这个方法获取的结果就包括张三所在的机构、张三所在的角色，以及张三自己。
	 */

	public List<WFParticipant> getParticipantScope(String typeCode,
			String participantID) {
		log.info("Fetch4AData::getParticipantScope: start");
		List<WFParticipant> l = new ArrayList<WFParticipant>();
		LinkedHashMap<Integer, String> param = new LinkedHashMap<Integer, String>();
		if (!_checkCode(typeCode))
			return l;
		if (typeCode.equals(PERSON)) {
//			String sql = String.format(SQLManager.getSql("userScope"),
//					participantID, participantID, participantID, participantID);
			
//			//从缓存获取
//			l = CacheData.getFindParticipantScopeWithPerson(participantID);
//			if(l != null && !l.isEmpty()){
//				log.info("■■■■ size :"+ l.size());
//				log.info("Fetch4AData::getParticipantScope: end");
//				return l;
//			}
			
			String sql = SQLManager.getSql("userScope");

			param.put(1, participantID);
			param.put(2, participantID);
			param.put(3, participantID);
			param.put(4, participantID);
			
//			System.out.println(sql);
//			l.addAll(_execute(sql));
			l.addAll(_execute2(sql, param));
			
			//在没有本身用户的情况下也要加进来
			if(l.size() == 0){
				l.add(new WFParticipant(participantID, "", Constant.PERSON));
			}
			
//			//将数据库的数据写进缓存
//			CacheData.setFindParticipantScopeWithPerson(participantID, l);
		}
		log.info("■■■■ size :"+ l.size());
		log.info("Fetch4AData::getParticipantScope: end");
		return l;
	}

	/**
	 * 根据code获取参与者类型
	 */

	public ParticipantType getParticipantType(String code) {
		log.info("Fetch4AData::getParticipantType: start");
		if (code == null || code.trim().length() == 0)
			code = PERSON;
		log.info("Fetch4AData::getParticipantType: end");
		return parTypes.get(code);
	}

	/**
	 * 获取所有的参与者类型
	 */

	public List<ParticipantType> getParticipantTypes() {
		log.info("Fetch4AData::getParticipantTypes: start");
		List<ParticipantType> l = new ArrayList<ParticipantType>();
		if (parTypes == null)
			return l;
		l.addAll(parTypes.values());
		log.info("Fetch4AData::getParticipantTypes: end");
		return l;
	}

	/**
	 * 获取某个类型的所有参与者 比如参与者类型为机构，那么就把所有机构返回为一个List，
	 * 因为返回的参与者需要按照PageCond的规则分页，所以取List中符合分页的记录.
	 */

	public List<WFParticipant> getParticipants(String typeCode,
			PageCond pageCond) {
		log.info("Fetch4AData::getParticipants: start");
		List<WFParticipant> l = new ArrayList<WFParticipant>();
		if (parTypes == null)
			return l;
		log.info("Fetch4AData::getParticipants: end");
		return new ArrayList<WFParticipant>();
	}

	/**
	 * 确定typecode是否为系统支持
	 * 
	 * @param typeCode
	 * @return
	 */
	private boolean _checkCode(String typeCode) {
		log.info("Fetch4AData::_checkCode: start");
		ParticipantType partType = parTypes.get(typeCode);
		if (partType == null) {
			return false;
		}
		log.info("Fetch4AData::_checkCode: end");
		return true;
	}
	
	/**
	 * 执行sql语句并映射为参与者对象
	 * 
	 * @param sql
	 * @return
	 */
	private List<WFParticipant> _execute2(String sql, LinkedHashMap<Integer, String> param) {
		log.info("Fetch4AData::_execute2: start");
		List<WFParticipant> l = new ArrayList<WFParticipant>();
		try {
			l.addAll(connManager.query2(sql, param, WFParticipant.class));
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		log.info("Fetch4AData::_execute2: end");
		return l;
	}

	/**
	 * 执行sql语句并映射为参与者对象
	 * 
	 * @param sql
	 * @return
	 */
	private List<WFParticipant> _execute(String sql) {
		log.info("Fetch4AData::_execute: start");
		List<WFParticipant> l = new ArrayList<WFParticipant>();
		try {
			l.addAll(connManager.query(sql, WFParticipant.class));
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		log.info("Fetch4AData::_execute: end");
		return l;
	}
	
	public static void main(String...org) throws WFServiceException{
		Fetch4AData thls = new Fetch4AData();
		WFParticipant req = new WFParticipant();
		req.setId("c777dfc1-2aa6-42f6-ac1b-38356f10bf14");
		List<WFParticipant> l = thls.getParticipantScope("person", "1AED01CD763574B0E0537C02A8C067BE____");  //thls.getDirectOrgAndRole(req, "003");
	 	BpsQueryUtil bpsUtil = new BpsQueryUtil(null, null, 0, 100);
	 	User user = new User();
		user.setUserId("1AED01CD763574B0E0537C02A8C067BE____");
		

//		//加载XSD和HBM
//		InputStream expStream = CsTaskOrderView.class.getResourceAsStream("/com/kingmed/pl/common/cstaskorderview.xsd");
//		DynamicXSDLoader.load(expStream, null, true);
//		InputStream expHbmStream = CsTaskOrderView.class.getResourceAsStream("/com/kingmed/pl/common/cstaskorderview.hbm");
//		DynamicHBMLoader.add("1", expHbmStream);
//		
//		InputStream expStream2 = CsTaskOrderView.class.getResourceAsStream("/com/kingmed/pl/common/criteria.xsd");
//		DynamicXSDLoader.load(expStream2, null, true);
//		
//		InputStream expStream3 = CsTaskOrderView.class.getResourceAsStream("/com/kingmed/pl/common/data.xsd");
//		DynamicXSDLoader.load(expStream3, null, true);
//		
//		InputStream expStream4 = CsTaskOrderView.class.getResourceAsStream("/com/kingmed/pl/common/foundation.xsd");
//		DynamicXSDLoader.load(expStream4, null, true);
//		
//		DataObject bizEntityFilter = null;
//		DataObject workItemFilter = null;
//		workItemFilter  = BPSDataFactory.createWFSDOCriteria(WFWorkItem.class);
//		workItemFilter.set("_entity", "com.eos.workflow.data.WFWorkItem");
//		bizEntityFilter  = BPSDataFactory.createWFSDOCriteria(WFUserObject.class);
//		bizEntityFilter.set("_entity","com.eos.workflow.csworkorder.CsWorkOrderView");
//		
//		setQueryCondition(new ArrayList<QueryCondition>(), bizEntityFilter);
//		
//		DataObject[] workItemDataObjects = BPSServiceClientFactory.getDefaultClient().getWorklistQueryManager().queryPersonBizEntities4SDO("1AED01CD763574B0E0537C02A8C067BE____",
//				"ALL", "ALL", bizEntityFilter, workItemFilter, "workItemId,workItemID", null);
//		
//	 	List<WFMyWorkItem> list = bpsUtil.getMyUndoTasksWithBizInfo(user, new SearchTaskVO(), 0, 100);
//	 	List<String> currpartlist = bpsUtil.getCurrentParticipants(5512);
//		
//	 	String result = "false";
//
//	 	for(WFParticipant mypart:l){
//	 		for(String currpart:currpartlist){
//	 			System.out.println(currpart);
//	 			if(mypart.getId().equals(currpart )){
//	 				result = "true";
//	 				break;
//	 			}
//	 		}
//	 		System.out.println(mypart);
//	 	}
//	 	System.out.print(result);
	}
	
//		private static void setQueryCondition(List<QueryCondition> queryConditionList, DataObject bizEntityFilter){
//			if (queryConditionList != null && queryConditionList.size() > 0) {
//				int size = queryConditionList.size();
//				for(int i = 0;i<size;i++){
//					String exprPre = "_expr["+(i+1)+"]/";
//					QueryCondition queryCondition = queryConditionList.get(i);
//					if("between".equals(queryCondition.getOper())){
//						//日期
//						bizEntityFilter.set(exprPre+queryCondition.getConditionName(),"");
//						bizEntityFilter.set(exprPre+"_min", queryCondition.getConditionValuesForDate()[0]);
//						bizEntityFilter.set(exprPre+"_max", queryCondition.getConditionValuesForDate()[1]);
//					}else if("like".equals(queryCondition.getOper())){
//						bizEntityFilter.set(exprPre+queryCondition.getConditionName(),queryCondition.getConditionValues()[0]);
//						bizEntityFilter.set(exprPre+"_likeRule", queryCondition.getLikeRule());
//					}else{
//						bizEntityFilter.set(exprPre+queryCondition.getConditionName(),queryCondition.getConditionValues()[0]);
//					}
//					
//					bizEntityFilter.set(exprPre+"_op", queryCondition.getOper());
//				}
//				
//	//			bizEntityFilter.set("_orderby[1]/_property","");
//	//			bizEntityFilter.set("_orderby[1]/_sort","desc");
//			}
//		}
}
