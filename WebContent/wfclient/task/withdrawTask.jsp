<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ page import="com.bps.client.common.IPageNavigation"%>
<%@ page import="com.bps.client.common.Constants"%>
<%@ include file="/wfclient/common/common.jsp"%>
<html>
<head>
<title><bc:message key='myTask.WorkExecu'/></title>
<script type="text/javascript">

//*** 收回 ***//
function doWithDraw() {
	
	var url = '<%=request.getContextPath() + IPageNavigation.INSTANCE.getPageURL(Constants.TASK_FUNCTION, "actionReason")%>' + "?flag=withdraw";
	if (isIEBrowser()) 	//IE判断
   	{
		var returnValue = window.showModalDialog(url, "", "dialogWidth:504px;dialogHeight:300px;center:yes;status:no;unadorned:no");
		if (typeof(returnValue) == 'undefined') {
			return;
		}
		doRevocationCallback(returnValue);
   	}
   	else
   	{
		window.open(url, "", "width=504px,height=300px,location=no,menubar=no,modal=yes");     	
	}
}

//*** 回调方法 ***//
function doRevocationCallback(returnValueObj) {
	var reason = returnValueObj.reason;
	var delegType = returnValueObj.delegType;
	document.getElementById('reason').value=reason;
	document.getElementById('delegType').value=delegType;
	var form1 = document.getElementById('form1');
	form1.action="<%=request.getContextPath() + IPageNavigation.INSTANCE.getPageURL(Constants.TASK_FUNCTION, "delegateWorkItem.do")%>";
	form1.method = "POST";				
	form1.submit();
}
</script>
</head>

<body style="repeat-x;margin-top:0px;margin-left:0px; margin-right:0x;margin-buttom:0px">
<form name="form1" id="form1" method="post" action="" target="_self">
<table  class="workarea" width="100%" >
	<tr>
    	<td class="workarea_title"><bc:message key='myTask.client'/> &gt; <bc:message key='myTask.entrusted'/> &gt; <h3><bc:message key='myTask.RecWork'/></h3>
    	</td>
  	</tr>
	<tr>
		<td>
			<table border="0" class="form_table" width="100%">
				<tr>
					<td colspan="4" class="EOS_panel_head"><bc:message key='myTask.WorkDetails'/></td>
				</tr>
				<tr> 
					<td class="EOS_table_row" width="15%"><bc:message key='myTask.WorkID'/>:</td>
					<td width="35%">${task.workItemID}</td>
					<td class="EOS_table_row" width="15%"><bc:message key='myTask.Participant'/>:</td>
					<td>${task.partiName}</td>
				</tr>
				<tr> 
	    			<td class="EOS_table_row"><bc:message key='myTask.WorkName'/>:</td>
	    			<td>${task.workItemName}</td>
	    			<td class="EOS_table_row"><bc:message key='myTask.Priority'/>:</td>
	    			<td><bc:displayPriority priority='${task.priority}'/></td>
				</tr>
				<tr> 
	   				<td class="EOS_table_row"><bc:message key='myTask.CState'/>:</td>
					<td><bc:displayProInstCurrentState currentState='${task.currentState}' flag='task'/></td>
					<td class="EOS_table_row"><bc:message key='myTask.Creation-time'/>:</td>
					<c:choose>
   						<c:when test="${not empty task.createTime}">
   							<td><bc:timePatternConvert fromPattern="yyyyMMddHHmmss" toPattern="yyyy-MM-dd HH:mm:ss" fromTimeString='${task.createTime}' /></td>
   						</c:when>
   						<c:otherwise>
   							<td></td>
   						</c:otherwise>
					</c:choose>
				</tr>
				<tr> 
					<td class="EOS_table_row"><bc:message key='myTask.TLimit'/>:</td>
					<td>${task.limitNumDesc}</td>
					<td class="EOS_table_row"><bc:message key='myTask.Reminder'/>:</td>
					<c:choose>
   						<c:when test="${not empty task.remindTime}">
   							<td><bc:timePatternConvert fromPattern="yyyyMMddHHmmss" toPattern="yyyy-MM-dd HH:mm:ss" fromTimeString='${task.remindTime}' /></td>
   						</c:when>
   						<c:otherwise>
   							<td></td>
   						</c:otherwise>
					</c:choose>
				</tr>
				<tr> 
					<td class="EOS_table_row"><bc:message key='myTask.Timeout'/>:</td>
					<td>
						<c:if test="${task.isTimeOut == 'Y'}"><bc:message key='myTask.Yes'/></c:if>
          				<c:if test="${task.isTimeOut == 'N'}"><bc:message key='myTask.No'/></c:if>
					</td>
					<td class="EOS_table_row"><bc:message key='myTask.amount'/>:</td>
					<td>${task.timeOutNumDesc}</td>
				</tr>
				<tr> 
	   				<td class="EOS_table_row"><bc:message key='myTask.BIID'/>:</td>
					<td>${task.processInstID}</td>
					<td class="EOS_table_row"><bc:message key='myTask.BIName'/>:</td>
					<td>${task.processInstName}</td>
				</tr>
				<tr> 
	   				<td class="EOS_table_row"><bc:message key='myTask.AIID'/>:</td>
					<td>${task.activityInstID}</td>
					<td class="EOS_table_row"><bc:message key='myTask.AIName'/>:</td>
					<td>${task.activityInstName}</td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td>
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
    			<tr>
					<td  class="EOS_panel_head"><bc:message key='myTask.WOpInformation'/></td>
				</tr>
				<tr> 
	    			<td> 
  						<table width="100%" class="EOS_table" border="0" style="border-collapse: collapse" id="plist">
			  				<tr class="EOS_table_head">
			        			<th nowrap="nowrap" align="center" width="20%" ><bc:message key='myTask.Time'/></th>
			        			<th nowrap="nowrap" align="center" width="20%" ><bc:message key='myTask.OpType'/></th>
			       				<th nowrap="nowrap" align="center" width="40%"><bc:message key='myTask.Content'/></th>
			       				<th nowrap="nowrap" align="center" width="20%" ><bc:message key='myTask.Tsource'/></th>
				          	</tr>
				          	<c:forEach items="${messagelist}" var="message">
				  				<tr class="EOS_table_row" align="center">
					              	<td nowrap="nowrap"><bc:timePatternConvert fromPattern="yyyyMMddHHmmss" toPattern="yyyy-MM-dd HH:mm:ss" fromTimeString='${message.time}' /></td>
					              	<td nowrap="nowrap">${message.operateType}</td>     
					              	<td style="word-break:break-all; ">${message.content}</td>     
					              	<td nowrap="nowrap">${message.from}</td> 
					          	</tr>
			  				</c:forEach>
			       		</table>
       				</td>
    			</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td>
			
			<input type="hidden" id="workitemID" name="workitemID" value="${task.workItemID}">
			<input type="hidden" id="delegType" name="delegType">
			<input type="hidden" id="reason" name="reason">			
            <span id="oData"></span>
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td  class="EOS_panel_head"><bc:message key='myTask.ExeFormDate'/></td>
				</tr>
				<tr>
					<td>
						<table width="100%" border="0" cellspacing="0" cellpadding="0" class="form_table">
							<wfBase:showWorkItemForm id="view" workItemID="${task.workItemID}" formName="form1">
								<tr>
									<td align="left" width="15%" class="EOS_table_row">${view.name}</td>
									<td align="left">${view.htmlComponentCode}</td>
								</tr>
							</wfBase:showWorkItemForm>								
						</table>
					</td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td>
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td>
        				<table width="100%" border="0" cellspacing="0" cellpadding="0">
          					<tr>
              					<td width="50%"> 
                					<input type="button"  name="btGet"  class="button" value = "<bc:message key='myTask.Reclaim'/>" onClick="doWithDraw();">
                    			</td>
          					</tr>
						</table>
					</td>
				</tr>
			</table>

</td>
</tr>
</table>
</form>
</body>
</html>