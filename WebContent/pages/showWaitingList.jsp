<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page session="false"%>
<%@ page import="java.util.List,java.util.Map,java.util.HashMap,java.text.SimpleDateFormat" %>
<%@ page import="com.kingmed.pl.common.BpsQueryUtil"%>
<%@ page import="com.kingmed.pl.common.vo.User, com.kingmed.pl.common.vo.SearchTaskVO"%>
<%@ page import="cn.com.deloitte.si.bpm.entity.WFMyWorkItem"%>
<%!
    public String shiftDate(String in) throws Exception{ 	
	
	    SimpleDateFormat formatter1 = new SimpleDateFormat("yyyyMMddHHmmss");
	    SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    
	    if(in==null||"".equals(in))return "";
	    
	    return formatter2.format( formatter1.parse(in));
    }
%>
<%
	String userID = request.getParameter("userID");
	User user = new User();
	user.setUserCode(userID);
	user.setUserName("");
 	BpsQueryUtil bpsUtil = new BpsQueryUtil(user, null, 0, 100);
 	List<WFMyWorkItem> result = bpsUtil.getMyUndoTasksWithBizInfo(user, new SearchTaskVO(), 0, 100);
 	Map<Integer,String> stateMap = new HashMap<Integer,String>();
 	stateMap.put(4, "待领取");
 	stateMap.put(8, "挂起");
 	stateMap.put(10, "处理中");
 	stateMap.put(12, "完成");
 	stateMap.put(13, "退回");

%>

<div id=bpsHistory class="row">
	<table class="bpsTable">
	<thead><tr>
		<th>序号</th>
		<th>工作项</th> 							
		<th>处理人员</th> 							
		<th>创建时间</th> 
		<th>到达时间</th>			 
	</tr></thead>
	<% int i = 1;
	for(WFMyWorkItem item:result){  
		out.print("<tr>"+
				"<td>"+ i +"</td>"+
				"<td>"+item.getWorkItemName()+"</td>"+
				"<td>"+item.getParam1()+"</td>"+
				"<td>"+shiftDate(item.getCreateTime())+"</td>"+
				"<td>"+shiftDate(item.getStartTime())+"</td>"+
				"<td>"+ item.getProcessInstID() +"</td>"+
				"</tr>");		
		i++;
	 }	 %>
	</table>
</div>