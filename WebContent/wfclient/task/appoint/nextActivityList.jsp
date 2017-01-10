<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="/wfclient/common/common.jsp"%>

<%@page import="java.util.List"%>
<%@page import="com.primeton.workflow.model.definition.Participant"%>

<%@page import="com.bps.client.tag.help.AppointParticipantResourceTreeLoader"%>
<%@page import="com.bps.client.common.FreeFlowAndAppiontFacade"%>
<%@page import="com.bps.client.common.BPSServiceFacade"%>
<%@page import="com.eos.workflow.data.WFActivityDefine"%>

<%@page import="com.bps.client.common.Constants"%>
<%@page import="com.bps.client.common.IPageNavigation"%>
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
	
	List<WFActivityDefine> actDefList = BPSServiceFacade.getInstance().getFreeFlowManager().queryAppointedNextActivities(Long.valueOf(workItemID).longValue());
%>



<html>
	<head><title></title>
	
<script>
	 function onSelected(chkObj){
	 	var chkAll = document.getElementById("AllCheckboxID");
	 	if(chkAll.checked){
	        chkAll.checked=false;
	        chkObj.checked=false;
		}else{
			if(chkObj.checked){
				  chkObj.checked=true;
			}else{
				  chkObj.checked=false;
			}
			var el = document.getElementsByName('sid');
	      	var len = el.length;
	       	var temp=0;
	        for(var i=0; i<len; i++){  
	          if(el[i].checked == true){
	             temp=temp+1;
	             el[i].value;
	          }
	        }
	        if(temp==len)
	        	chkAll.checked=true;
		}
			 
	}
			 
			 
			 
	 //检查ID是否存在
	 function checkID(){
	    var el = document.getElementsByName('sid');
	    if(el.length==0)
			 return false;
	    else
	         return true;
	 }

	 //复选框全选操作
	 function SelectAllChecka(obj){
	     if(checkID()){
	        var el = document.getElementsByName('sid');
	        var len = el.length;
	        if(obj.checked==true){ 
	            for(var i=0; i<len; i++){  
	              el[i].checked = true; 
	            } 
	        } else { 
	            for(var i=0; i<len; i++){ 
	              el[i].checked = false; 
	            } 
	        }
	     }else
	        return false;
	 }
	 
		 
				
	function removeElement() 
	{	
		if (!checkID()) {
			return false ;
		}
		var el = document.getElementsByName('sid');
		var isChecked = false;
		for(var i=0; i<el.length; i++){
			if (el[i].checked==true) {
				isChecked = true;
				break;
			}
	    }
	    if (!isChecked) {
	    	alert("<bc:message key='appoint.ActivityAlert'/>");
	    	return;
	    }
		if (confirm('<bc:message key='appoint.RemoveAlert'/>')) {
			submitFrm();
		}
	}
			
		function submitFrm () 
		{
			var args = new Array();
			args[0] = <%=workItemID%>;
			
			var ids = "";
			var sid = document.getElementsByName('sid');
			for(var i = 0; i< sid.length; i++){
				if(sid[i].checked){
					ids +=sid[i].value+",";
				}
			}
			args[1] = ids;
			
			callMethodByAjax("post", "<%=request.getContextPath() %>", "com.bps.client.common.FreeFlowAndAppiontFacade", 
				"removeSelectFreeFlow", args, callbackSubmit);
				
		}
		
		function callbackSubmit(returnValue) {
			if (returnValue.success == true) {
				parent.frames['appointedNext'].location='<%=request.getContextPath() + IPageNavigation.INSTANCE.getPageURL(Constants.TASK_FUNCTION, "nextActivityList") + "?processDefID=" + processDefID + "&workItemID=" + workItemID%>';
				parent.frames['optionAppointed'].location='<%=request.getContextPath() + IPageNavigation.INSTANCE.getPageURL(Constants.TASK_FUNCTION, "optionNextActivityList") + "?processDefID=" + processDefID + "&workItemID=" + workItemID%>';
			}
		}
			
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
			
		</script>
	</head>
	<body>
	  <form name="appointedActivity">
		<table border="1" cellpadding="0" cellspacing="0" align="center" width="100%"  class="EOS_table">
			<tr height="20">
				<th align="center" nowrap="nowrap">
				<input type="checkbox" id="AllCheckboxID" onClick="SelectAllChecka(this);"><bc:message key='set.Choose'/></th>
				<th align="center"><bc:message key='appoint.ActivityID'/></th>
				<th align="center"><bc:message key='appoint.ActivityName'/></th>
				<th align="center"><bc:message key='appoint.PartAssigned'/></th>
				<th align="center"><bc:message key='myTask.Operation'/></th>
			</tr>
			<%
			
			if(actDefList!=null&&actDefList.size()>0){			
				for (WFActivityDefine act : actDefList){
			 %>
						<tr>
				<td align="center"><input type="checkbox" onClick="onSelected(this)" name="sid" value="<%=act.getId() %>"/></td>
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
				<% if("manual".equals(act.getType()) && act.isAllowAppoint()) {%>
				<%String participant = ResourceMessageUtil.getResourceMessage("myTask.Participant", session); %>  
				<%String setparticipant = ResourceMessageUtil.getResourceMessage("appoint.SetParticipants", session); %>  
					<bc:selector maxCount="5" name="setParti" buttonValue="<%=setparticipant%>" 
							loader="<%=AppointParticipantResourceTreeLoader.class.getName()%>"
							loaderParam='<%=processDefID + "|" + act.getId()%>'
							resourceTitle="<%=participant%>"
							needReturn="false" callback="handleSetParticipant" callbackOtherParam="<%=act.getId()%>"/>
				
				<%}%>
				</td>
			</tr>
		<%
			}//the end of:for (WFActivityDefine act : actDefList){
			
		}
		 %>
		</table><br>
		<table border="0" cellpadding="0" cellspacing="0" align="center" width="100%">
			<tr>
				<td width="8%" align="center"><a href="#" onclick="removeElement()"> <bc:message key='set.Delete'/> </a></td>
				<td colspan="4">&nbsp;</td>
			</tr>
		</table>
		</form>
	</body>
</html>