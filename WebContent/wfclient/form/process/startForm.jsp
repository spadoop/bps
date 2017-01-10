<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.bps.client.common.Util"%>
<%@page import="com.bps.client.user.UserBean"%>
<html>
<head>

<script type="text/javascript">


</script>
</head>
<body  style="repeat-x;margin-top:0px;margin-left:0px; margin-right:0px;margin-buttom:0px;width:100%" >
	<% 	
		String formName = (String) Util.getValue(request,"formName");
		String processDefID = (String) Util.getValue(request,"procDefID");
		String processDefName = (String) Util.getValue(request,"procDefName");
		String activityDefName = (String) Util.getValue(request,"activityDefName");
		String activityDefID = (String) Util.getValue(request,"activityDefID");
		
		UserBean user = (UserBean)Util.getValue(session,"user");
	
		Map params = new HashMap();
		
		params.put("userID",user.getUserID());
		params.put("userName",user.getUserName());
		params.put("processDefID",processDefID);
		params.put("processDefName",processDefName);
		params.put("activityDefName",activityDefName);
		params.put("activityDefID",activityDefID);
	
		com.primeton.bfs.client.api.IFormEngineHandle formEngine = com.primeton.bfs.client.api.FormClientFactory.getFormEngineHandleInstance();
		String localeHtmls = formEngine.createLocale(request.getContextPath(),(String) session.getAttribute("locale"));
	   	String htmls = formEngine.createFormHTMLs(formName,null,params,request.getContextPath());	
	   	String pageHtml = formEngine.createPageHtml(request.getContextPath());
	%>
	<%=localeHtmls %>
	<%=pageHtml %>
	<%=htmls %>
</body>
</html>
