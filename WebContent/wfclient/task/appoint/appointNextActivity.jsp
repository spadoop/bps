<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/wfclient/common/common.jsp"%>

<%@page import="com.bps.client.common.Constants"%>
<%@page import="com.bps.client.common.IPageNavigation"%>
<%
	String processDefID = request.getParameter("processDefID");
	if (processDefID == null || processDefID.trim().length() == 0) {
		processDefID = (String)request.getAttribute("processDefID");
	}
	String workItemID = request.getParameter("workItemID");
	if (workItemID == null || workItemID.trim().length() == 0) {
		workItemID = (String)request.getAttribute("workItemID");
	}

%>


<html>
	<head><title><bc:message key='appoint.Assigned'/></title></head>
	<body>
		
		<table align="center" border="0" cellpadding="0" cellspacing="0" width="90%">
			<tr> 
				<td height="30">
					<font size="30"><bc:message key='appoint.Assigned'/></font>
				</td>
			</tr>
			<tr> 
				<td>
					<iframe name="appointedNext" width="100%" height="200px" style="overflow:auto;"
						src='<%=request.getContextPath() + IPageNavigation.INSTANCE.getPageURL(Constants.TASK_FUNCTION, "nextActivityList") + "?processDefID=" + processDefID + "&workItemID=" + workItemID%>' style="margin-top:0" align="top" scrolling="auto" frameborder="1" allowtransparency="true"></iframe>
				</td>
			</tr>
			<tr> 
				<td height="30">
					<font size="30"><bc:message key='appoint.OptialList'/></font>
				</td>
			</tr>
			<tr>  
			  <td valign="top">
			    	<iframe name="optionAppointed"  width="100%" height="200px" style="overflow:auto;"
			    		src='<%=request.getContextPath() + IPageNavigation.INSTANCE.getPageURL(Constants.TASK_FUNCTION, "optionNextActivityList") + "?processDefID=" + processDefID + "&workItemID=" + workItemID%>'" style="margin-top:0" align="top" scrolling="auto" frameborder="1" allowtransparency="true"></iframe>
			  </td>
			</tr>
		</table>
	</body>
</html> 