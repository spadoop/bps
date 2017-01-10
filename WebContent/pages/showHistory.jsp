<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page session="false"%>
<%@ page import="java.util.List,java.util.Map,java.util.HashMap,java.text.SimpleDateFormat" %>
<%@ page import="cn.edu.sysu.bpm.common.BpsQueryUtil"%>
<%@ page import="cn.edu.sysu.bpm.common.Fetch4AData"%>
<%@ page import="cn.edu.sysu.bpm.common.vo.User"%>
<%@ page import="com.eos.workflow.data.WFWorkItem"%>
<%@ page import="com.eos.workflow.omservice.WFParticipant"%>
<%!
    public String shiftDate(String in) { 	
	
	    SimpleDateFormat formatter1 = new SimpleDateFormat("yyyyMMddHHmmss");
	    SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    
	    if(in==null||"".equals(in))return "";
	    
	    try{
	    	return formatter2.format( formatter1.parse(in));
	    } catch (Exception e){
	    	return in;
	    }
    }
	public String getBizStatus(int status, String url) { 	

	 	Map<Integer,String> stateMap = new HashMap<Integer,String>();
	 	stateMap.put(4, "待审批");
	 	stateMap.put(8, "挂起");
	 	stateMap.put(10, "处理中");
	 	stateMap.put(12, "完成");
	 	stateMap.put(13, "退回");
	    
	    
	    if("REJECT".equals(url)){
	    	return "驳回";
	    } else {
	    	return stateMap.get(new Integer(status));
	    }
    }
%>
<%
	String procInstID = request.getParameter("procInstID");
	if(procInstID==null){
		procInstID = request.getParameter("pID");
	}
		
	User user = new User();
	user.setUserCode("tiger");
	user.setUserName("tiger");
 	BpsQueryUtil bpsUtil = new BpsQueryUtil(user, null, 0, 100);
 	List<WFWorkItem> result = bpsUtil.getProcHistory(Long.parseLong(procInstID));

%>

<div id=bpsHistory class="row">
	<table class="bpsTable">
	<thead><tr>
		<th nowrap>序号</th>
		<th>工作项</th> 							
		<th>处理人员</th> 							
		<th>创建时间</th> 
		<th>开始时间</th> 							
		<th>结束时间</th>
		<th nowrap>处理状态</th>
		<th width="40%">意见</th>
	</tr></thead>
	<% int i = 1;
	if(result!=null){
	for(WFWorkItem item:result){  
		String remark = item.getDealResult()==null?"&nbsp;":item.getDealResult();
		WFParticipant p = Fetch4AData.getInstance().findParticipantByID("person", item.getParticipant());
		out.print("<tr>"+
				"<td>"+ i +"</td>"+
				"<td>"+item.getWorkItemName()+"</td>"+
				"<td>"+((p==null)?"":p.getName())+"</td>"+
				"<td>"+shiftDate(item.getCreateTime())+"</td>"+
				"<td>"+shiftDate(item.getStartTime())+"</td>"+
				"<td>"+shiftDate(item.getEndTime())+"</td>"+
				"<td>"+getBizStatus( item.getCurrentState(),item.getActionURL())+"</td>"+
				"<td>"+remark+"</td>"+
				"</tr>");		
		i++;
	 }	
	}%>
	</table>
</div>