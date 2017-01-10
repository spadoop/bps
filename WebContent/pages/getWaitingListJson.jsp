<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page session="false"%>
<%@ page import="java.util.List,java.util.Map,java.util.HashMap,java.text.SimpleDateFormat" %>
<%@ page import="com.eos.workflow.omservice.WFParticipant"%>
<%@ page import="cn.edu.sysu.bpm.common.BpsQueryUtil"%>
<%@ page import="cn.edu.sysu.bpm.common.Fetch4AData"%>
<%@ page import="cn.edu.sysu.bpm.common.vo.User, cn.edu.sysu.bpm.common.vo.SearchTaskVO"%>
<%@ page import="cn.edu.sysu.bpm.entity.WFMyWorkItem"%>
<%
	String userID = request.getParameter("userID");
	String pIdStr = request.getParameter("pID");
	long pID = 0;
	if(pIdStr!=null)pID = Long.parseLong(pIdStr);
	
	User user = new User();
	user.setUserId(userID);
	user.setUserName("");	
 	BpsQueryUtil bpsUtil = new BpsQueryUtil(null, null, 0, 100);
 	//List<WFMyWorkItem> list = bpsUtil.getMyUndoTasksWithBizInfo(user, new SearchTaskVO(), 0, 100);
 	List<String> currpartlist = bpsUtil.getCurrentParticipants(pID);
	
 	Fetch4AData f4a = new Fetch4AData();
 	List<WFParticipant> mypartlist = f4a.getParticipantScope("person", userID);
 	
 	String result = "false";

 	for(WFParticipant mypart:mypartlist){
 		for(String currpart:currpartlist){
 			if(mypart.getId().equals(currpart )){
 				result = "true";
 				break;
 			}
 			
 		}
 	}
 	out.print(result);
 	
%>