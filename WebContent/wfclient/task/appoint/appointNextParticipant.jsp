<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/wfclient/common/common.jsp"%>

<%@page import="java.util.List"%>
<%@page import="com.primeton.workflow.model.definition.Participant"%>
<%@page import="com.bps.client.common.BPSServiceFacade"%>
<%@page import="com.eos.workflow.api.IWFAppointActivityManager"%>
<%@page import="com.eos.workflow.data.WFActivityDefine"%>
<%@page import="com.bps.client.tag.help.AppointParticipantResourceTreeLoader"%>
<%@page import="com.bps.client.common.FreeFlowAndAppiontFacade"%>
<%@page import="com.bps.client.common.i18n.ResourceMessageUtil"%>

<%
	
	String processDefID = request.getParameter("processDefID");
	if (processDefID == null || processDefID.trim().length() == 0) {
		processDefID = (String)request.getAttribute("processDefID");
	}
	
	String workItemID = request.getParameter("workItemID");
	if (workItemID == null || workItemID.trim().length() == 0) {
		workItemID = (String)request.getAttribute("workItemID");
	}		
	
	IWFAppointActivityManager mgr = BPSServiceFacade.getInstance().getAppointActivityManager();
	
	List<WFActivityDefine> actDefList = mgr.queryNextActivitesNeedAppointParticipant(Long.valueOf(workItemID).longValue());
	String myTaskOperation = ResourceMessageUtil.getResourceMessage("appoint.SetParticipants", session);
%>

<script>
			
			//设定参与者
			function handleSetParticipant(participantArray, activityDefID) {
				var partiStr = "";
				var args = new Array();
				var paticipantString = " ";
				for(var i = 0; i<participantArray.length; i++){
					partiStr += participantArray[i].id + "|";
					partiStr += participantArray[i].name + "|";
					partiStr += participantArray[i].typeCode + "$";
					paticipantString += participantArray[i].name + " ";
				}
			
			
				args[0] = '<%=workItemID%>';
				args[1] = activityDefID;
				args[2] = partiStr;
				
				callMethodByAjax("post", "<%=request.getContextPath() %>", "<%=FreeFlowAndAppiontFacade.class.getName() %>", 
					"appointActivityParticipant", args, function(returnValue) {
						if (returnValue.success == true) {
							document.getElementById(activityDefID).innerHTML = paticipantString;
						}
					});	
			}
			
		function closeWindow()  {
			if (confirm('<bc:message key='appoint.ConfirmClose'/>')) {
				window.close();
			}
		}
</script>


<html>
	<head><title><bc:message key='appoint.ParticipantsActivities'/></title>		
	</head>
	<body>
	<table border="0" class="EOS_panel_body" width="100%">
        <tr>
          <td class="EOS_panel_head"><bc:message key='appoint.ParticipantsActivities'/></td>
        </tr>
        <tr>
          <td>
	  	<form name="appointedActivity">
		<table border="0" cellpadding="0" cellspacing="0" align="center" class="EOS_table" width="100%">
			<tr class="EOS_table_head" align="center">
				<th align="center"><bc:message key='appoint.ActivityID'/></th>
				<th align="center"><bc:message key='appoint.ActivityName'/></th>
				<th align="center"><bc:message key='appoint.PartAssigned'/></th>
				<th width="13%" align="center"><bc:message key='myTask.Operation'/></th>
			</tr>
			<%int flag = 0; %>
			<%
				for (WFActivityDefine act : actDefList){
			 %>
			<tr class="EOS_table_row">
				<td align="center">&nbsp;<%=act.getId() %></td>
				<td align="center">&nbsp;<%=act.getName() %></td>
				<td align="center">&nbsp;<span id="<%=act.getId() %>">
					<%
						List<Participant>  partList = act.getAppointedParticipants();
						
						if (partList != null && partList.size()>0) {
							for (Participant  parti : partList){							 	
					%>  
					
						<%=parti.getParticipantName() %>&nbsp;
							
					<%
							}						
						}//the end of :if (partList != null && partList.size()>0){
					%>
					</span>
				</td>
				<td align="center">&nbsp;
    							<%String participant = ResourceMessageUtil.getResourceMessage("myTask.Participant", session); %> 
						<bc:selector maxCount="5" name="setParti" buttonValue="<%=myTaskOperation%>"
							loader="<%=AppointParticipantResourceTreeLoader.class.getName()%>" 
							loaderParam='<%=processDefID + "|" + act.getId()%>'
							resourceTitle="<%=participant%>"
							needReturn="false" callback="handleSetParticipant" callbackOtherParam="<%=act.getId()%>">
							
							</bc:selector>
				</td>
			</tr>
			
					
		<%
			flag++;
			}//the end of:for (WFActivityDefine act : actDefList){
		 %>
			<%for(int i = flag;i <10;i++ ){ %>
            <tr class="EOS_table_row"><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td></tr>
            <%} %>
            
            
		</table>
		</form>
		</td>
        </tr>
        
        <tr>
			<td align="center" colspan="4"  valign="baseline">
			    <hr><br><input type="button" class="button" name="close" value="<bc:message key='appoint.Closed'/>" onClick="closeWindow();">
				</td>
			  </tr>
      </table>
	</body>
</html>