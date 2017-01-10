
/**    
 * Copyright (C),Kingmed
 * @FileName: ParticipantsCalcProxy.java  
 * @Package: com.kingmed.pl.common  
 * @Description: //模块目的、功能描述  
 * @Author maricao  
 * @Date 2015年4月17日 下午3:03:40  
 * @History: //修改记录
 * 〈author〉      〈time〉      〈version〉       〈desc〉
 * 修改人姓名            修改时间            版本号              描述   
*/  

package com.kingmed.pl.common;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.eos.system.utility.StringUtil;
import com.eos.workflow.omservice.WFParticipant;
import com.kingmed.pl.common.vo.AuditorType;

/**  
 * 调用平台restful服务的代理类
 * 功能详细描述
 * @author caozhongyi
 * @create 2015年4月17日 下午3:03:40 
 * @version 1.0.0
 * @see [相关类/方法]（可选）
 * @since 1.0
 */

public class AuditorCalcProxy {
	static ApplicationContext ctx;
	static{
		ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
	}
	public List<WFParticipant> getAuditorsByPositionAndBranch(String subSysID, String positionId,String branchId){
		//List<AuditConfig> acList = AuditConfigUtil.findByPosCodeAndBranchId(subSysID, pad36withZero(positionId), branchId);
		List<WFParticipant> resultList = new ArrayList<WFParticipant>();		
		/*AuditorType at = (AuditorType) ctx.getBean("auditorType");
		for(AuditConfig ac:acList){
			System.out.println(ac.getAuditerId());
			System.out.println(ac.getAuditType());
			WFParticipant part = new WFParticipant();
			part.setId(ac.getAuditerId());
			part.setName(ac.getAuditerId());
			try {
				part.setTypeCode( (String)AuditorType.class.getMethod
						("getType_"+ac.getAuditType(), null).invoke(at, null) );
			} catch ( NoSuchMethodException e) {
				part.setTypeCode(ac.getAuditType());
			}catch ( Exception e) {
				e.printStackTrace();
			}

			System.out.println(part.getId());
			System.out.println(part.getTypeCode());
			resultList.add(part);
		}*/
		return resultList;
	}
	
	/**
	 * 因为db定义是char（36）
	 *
	 * @param in
	 * @return
	 */
	private static String pad36withZero(String in){
		//assuming no null
		return StringUtil.rightPad(in, 36, '0');
	}
	
	public static void main(String ...strings ){
		AuditorCalcProxy acp = new AuditorCalcProxy();
		acp.getAuditorsByPositionAndBranch("MS",
				"GS-YWZL",
				"K0101"); 	 
//		Demo d = acp.new Demo();
//		for(int i=0;i<1000;i++)
//		new Thread( (d)).start();	  
	}

	class Demo implements Runnable{
		int c=1;
		public void show(){
			System.out.println(c++ + ", "+Thread.currentThread().getName());		
		}
		@Override
		public void run(){
			show(); 
		}
	}
}
