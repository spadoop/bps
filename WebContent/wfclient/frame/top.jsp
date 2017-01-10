<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ page import="com.bps.client.common.Constants"%>
<%@ page import="com.bps.client.common.IPageNavigation"%>
<%@ include file="/wfclient/common/common.jsp"%>
<html>
<head>
<title><bc:message key='top_jsp.title'/></title>
<script>
	function redirectLocation(id) {
		var array=new Array();
		array[0]="myTask";
		array[1]="startProcess";
		array[2]="setAgent";
		array[3]="queryAgent";		
		
		for(var i=0;i<array.length;i++){
			if(id==array[i]){
				document.getElementById(id).style.color="red";					//设置 当前被选中项的 字体颜色为“红色”
			}
			else
			{
				document.getElementById(array[i]).style.color="#00414E";		//其它选项的 字体颜色为“黑色”
			}						
		}			
		
		var urlPath = "<%=request.getContextPath()%>" ;
		if(id == "myTask")
		{
			urlPath+="<%=IPageNavigation.INSTANCE.getPageURL(Constants.TASK_FUNCTION, "myTask") %>";		//我的任务
		} 
		else if(id == "startProcess") 
		{
			urlPath+="<%=IPageNavigation.INSTANCE.getPageURL(Constants.PROCESS_FUNCTION, "process.do") %>" + "?method=queryProcessInfoList" ;		//启动流程
		} 
		else if(id == "setAgent") 
		{
			urlPath+="<%=IPageNavigation.INSTANCE.getPageURL(Constants.AGENT_FUNCTION, "agent.do") %>" + "?method=querySetAgentInfoList" ;	//代理设置
		} 
		else if(id == "queryAgent") 
		{
			urlPath+="<%=IPageNavigation.INSTANCE.getPageURL(Constants.AGENT_FUNCTION, "agent.do") %>" + "?method=queryAgentInfoList";    //代理查看
		} 
		parent.document.getElementById("contentWin").src=urlPath;

		
		//parent.frames['contentWin'].location=urlPath; 
	}
</script>
</head>

<body style="height:100%;width:100%;overflow: hidden;overflow-y:auto;" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr><td width="772" height="50" style="background:url('../images/BPSClient.jpg') no-repeat;"></td>
		<td valign="bottom" align="center" nowrap="nowrap" background="../images/Banner_bg.gif">
			<font style="font-size:9pt; color:#FFFFFF"><bc:message key='top_jsp.currentUser'/>：${user.userID} &nbsp;
			<%
				Object u=session.getAttribute("TENANT_ID");
				if(u!=null){
					out.print("("+String.valueOf(u)+")");
				}
			%>
			|&nbsp; 
				<a href='<%=request.getContextPath() + IPageNavigation.INSTANCE.getPageURL(Constants.USER_FUNCTION, "logout") %>' target="_top" style="text-decoration:none; color:#ffffff"><bc:message key='top_jsp.logout'/></a>&nbsp;
			</font>
		</td>
		</tr>
		<tr>
			<td valign="middle" style="table-layout:fixed"  colspan="2" height="22" background="../images/MenuBg.gif">
				<font style="font-size:9pt" color="#00414E">
					<a id="myTask" href="javascript:redirectLocation('myTask');"  style="text-decoration:none; color:red">&nbsp;&nbsp;<bc:message key='top_jsp.myTask'/></a>&nbsp;|&nbsp;
					<a id="startProcess" href="javascript:redirectLocation('startProcess');" style="text-decoration:none; color:#00414E"><bc:message key='top_jsp.startProcess'/></a>&nbsp;|&nbsp;
					<a id="setAgent" href="javascript:redirectLocation('setAgent');" style="text-decoration:none; color:#00414E"><bc:message key='top_jsp.agentSet'/></a>&nbsp;|&nbsp;
					<a id="queryAgent" href="javascript:redirectLocation('queryAgent');" style="text-decoration:none; color:#00414E"><bc:message key='top_jsp.agentView'/></a>
				</font>
			</td>
		</tr>
	</table>
</body>
</html>
