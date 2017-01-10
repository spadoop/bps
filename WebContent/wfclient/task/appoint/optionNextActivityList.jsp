<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/wfclient/common/common.jsp"%>

<%@page import="java.util.List"%>
<%@page import="com.eos.workflow.data.WFActivityDefine"%>
<%@page import="com.bps.client.common.BPSServiceFacade"%>

<%@page import="com.bps.client.common.Constants"%>
<%@page import="com.bps.client.common.IPageNavigation"%>

<%
	String processDefID = request.getParameter("processDefID");
	if (processDefID == null || processDefID.trim().length() == 0) {
		processDefID = (String)request.getAttribute("processDefID");
	}
	
	String workItemID = request.getParameter("workItemID");
	
	List<WFActivityDefine> actDefList = BPSServiceFacade.getInstance().getFreeFlowManager().queryNextFreeActivityScope(Long.valueOf(workItemID).longValue());
	
%>


<html>
	<head><title></title>
	</head>
	
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
	
		function checkAll(){
				if(document.forms['activityForm'].sid != null){
					var isChecked = document.forms['activityForm'].operation.checked;
				if(document.forms['activityForm'].sid.length == undefined)
					document.forms['activityForm'].sid.checked = isChecked;				
				else
					for(i = 0;i<document.forms['activityForm'].sid.length;i++)
						document.forms['activityForm'].sid[i].checked = isChecked;		
				}
		}
			
		function submitForm() {		    
			var args = new Array();
			args[0] = "<%=workItemID%>";
			
			var ids = "";
			var sid = document.getElementsByName('sid');
			for(var i = 0; i< sid.length; i++){
				if(sid[i].checked){
					ids +=sid[i].value+",";
				}
			}
			if (ids.length == 0) {
		    	alert("<bc:message key='appoint.FollowUpActivity'/>");
		    	return;
		    }
			args[1] = ids;
			
			callMethodByAjax("post", "<%=request.getContextPath() %>", "com.bps.client.common.FreeFlowAndAppiontFacade", 
				"appointActivity", args, callbackSubmit);
		}
		
		function callbackSubmit(returnValue) {
			if (returnValue.success == true) {
				parent.frames['appointedNext'].location='<%=request.getContextPath() + IPageNavigation.INSTANCE.getPageURL(Constants.TASK_FUNCTION, "nextActivityList") + "?processDefID=" + processDefID + "&workItemID=" + workItemID%>';
				parent.frames['optionAppointed'].location='<%=request.getContextPath() + IPageNavigation.INSTANCE.getPageURL(Constants.TASK_FUNCTION, "optionNextActivityList") + "?processDefID=" + processDefID + "&workItemID=" + workItemID%>';
			}
		}
			
		function closeWindow()  {
			if (confirm('<bc:message key='appoint.ConfirmClose'/>')) {
				parent.window.close();
			}
		}
	</script>	 
	<body>
		<form name="activityForm"><%int flag=0; %>
			<table border="1" cellpadding="0" cellspacing="0" class="EOS_table" align="center" width="100%">
				<tr height="20">
					<th width="10%" align="center" ><input type="checkbox" id="AllCheckboxID" name="operation" value="checkbox" onClick="checkAll();"><bc:message key='set.Choose'/></th>
					<th align="center"><bc:message key='appoint.ActivityID'/></th>
					<th align="center"><bc:message key='appoint.ActivityName'/></th>
				</tr> 
				
				<%
					if(actDefList!=null&&actDefList.size()>0){
					
						for(int i = 0; i<actDefList.size(); i++){
							WFActivityDefine act = actDefList.get(i);
							
				%>	
					<tr> 
						<td align="center"><input type="checkbox" onClick="onSelected(this)" name="sid" value="<%=act.getId() %>"></td>
						<td align="center"><%=act.getId() %></td>
						<td align="center"><%=act.getName() %></td>
					</tr>
						
				<%
							flag++;
						}
					}
				 %>
				
					<%for(; flag<5;flag++){ %>
					<tr><td></td><td></td><td></td><tr>
					<%} %>
			</table><br>
			<table border="0" cellpadding="0" cellspacing="0" align="center" width="100%">
				<tr align="center">
					<td>
						<input type="button" class="button" name="ok" value=" <bc:message key='myTask.OK'/> " onclick="submitForm()">&nbsp;&nbsp;
						<input type="button" class="button" name="cancel" value=" <bc:message key='appoint.Closed'/> " onclick="closeWindow()">
					</td>
				</tr>
			</table>
		</form>
	</body>
</html>