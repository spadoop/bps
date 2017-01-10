/**
 * 
 */
package cn.edu.sysu.bpm.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.primeton.workflow.api.ParticipantType;

/**
 * 缓存类，其主要功能必须被继承后才能
 * 
 *  ???如果为了提高性能，我们是否应该把某些元素缓存起来
 *  
 * @author Zhong, An-Jing
 *
 */
public class _Cache implements Constant {
	
	/**
	 * 数据库连接
	 */
	protected ConnectManager connManager;
	
	public Map<String, ParticipantType> parTypes= new HashMap<String, ParticipantType>();
	
	/**
	 * 一般在初始化的时候，加载此类
	 */
	protected void load(){
		ParticipantType pt = new ParticipantType();
		pt.setCode(PERSON);
		pt.setPrefix('P');
		pt.setDisplayName("用户");
		pt.setLeafParticipant(true);
		pt.setShowAtRootArea(false);

		// role
		ParticipantType ptRole = new ParticipantType();
		ptRole.setCode(ROLE);
		ptRole.setPrefix('R');
		ptRole.setDisplayName("角色");
		ptRole.setShowAtRootArea(true);
		List<String> ltRole = new ArrayList<String>();
		ltRole.add(PERSON);
		ptRole.setJuniorParticipantTypeCodes(ltRole);

		ParticipantType ptPosition = new ParticipantType();
		ptPosition.setCode(POSITION);
		ptPosition.setPrefix('G');
		ptPosition.setDisplayName("职务");
		ptPosition.setShowAtRootArea(true);
		ptPosition.setJointParticipantType(true);
		ArrayList<String> jointTypeList = new ArrayList<String>();
		jointTypeList.add(ROLE);
		jointTypeList.add(ORGANIZATION);
		ptPosition.setJointTypeCodeList(jointTypeList);
		List<String> ltPosition = new ArrayList<String>();
		ltPosition.add(PERSON);
		ptPosition.setJuniorParticipantTypeCodes(ltPosition);
		// ptList.add(ptPosition);

		ParticipantType ptVirtualPosition = new ParticipantType();
		ptVirtualPosition.setCode(VIRTUAL_POSTION);
		ptVirtualPosition.setPrefix('V');
		ptVirtualPosition.setDisplayName("虚拟岗位");
		ptVirtualPosition.setJointParticipantType(true);

		ArrayList<String> ltVirtualPosition = new ArrayList<String>();
		ltVirtualPosition.add("person");
		ptVirtualPosition.setJuniorParticipantTypeCodes(ltVirtualPosition);
		ArrayList<String> jointTypeListVirtual = new ArrayList<String>();
		jointTypeListVirtual.add(ROLE);
		jointTypeListVirtual.add(ORGANIZATION);
		ptVirtualPosition.setJointTypeCodeList(jointTypeListVirtual);
		// ptVirtualPosition.setLeafParticipant(true);

		// organization
		ParticipantType ptOrg = new ParticipantType();
		ptOrg.setCode(ORGANIZATION);
		ptOrg.setPrefix('O');
		ptOrg.setDisplayName("组织机构");
		ptOrg.setShowAtRootArea(true);
		List<String> ltOrg = new ArrayList<String>();
		ltOrg.add(PERSON);
		ltOrg.add(ORGANIZATION);
		ptOrg.setJuniorParticipantTypeCodes(ltOrg);
		
		parTypes.put(PERSON, pt);
		parTypes.put(ROLE, ptRole);
		parTypes.put(ORGANIZATION, ptOrg);
		parTypes.put(POSITION, ptPosition);
		parTypes.put(VIRTUAL_POSTION, ptVirtualPosition);
	}
}
