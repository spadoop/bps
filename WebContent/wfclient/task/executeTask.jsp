<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ page import="com.bps.client.common.IPageNavigation"%>
<%@ page import="com.bps.client.common.Constants"%>
<%@ page import="com.bps.client.user.UserDataContext"%>
<%@ include file="/wfclient/common/common.jsp"%>
<html>
<head>
<title><bc:message key='myTask.WorkExecu'/></title>
<script type="text/javascript">

//*** 领取 ***//
function doGet(){
	disableButton();
	var form1 = document.getElementById('form1');
	form1.action='<%=request.getContextPath() + IPageNavigation.INSTANCE.getPageURL(Constants.TASK_FUNCTION, "getWorkItemToSelf.do")%>';
	form1.submit();
}

//*** 取消领取 ***//
function doCancelGet(){
	disableButton();
	var form1 = document.getElementById('form1');
	form1.action='<%=request.getContextPath() + IPageNavigation.INSTANCE.getPageURL(Constants.TASK_FUNCTION, "cancelGetWorkItem.do")%>';
	form1.submit();	
}

//*** 代办 ***//   
function doDelegate() {
    var url = '<%=request.getContextPath() + IPageNavigation.INSTANCE.getPageURL(Constants.TASK_FUNCTION, "delegate")%>';
	if (isIEBrowser()) 	//IE判断
   	{
   		var returnValue = window.showModalDialog(url, "", "dialogWidth:504px;dialogHeight:300px;center:yes;status:no;unadorned:no");
   		if (typeof(returnValue) == 'undefined') {
			return;
		}
   		doDelegateCallback(returnValue);
   	}
   	else
   	{
		window.open(url, "", "width=504px,height=300px,location=no,menubar=no,modal=yes");
	}
}

//*** 协办 ***//
function doHelp(){
	var url = '<%=request.getContextPath() + IPageNavigation.INSTANCE.getPageURL(Constants.TASK_FUNCTION, "help")%>';
   	if (isIEBrowser()) 	//IE判断
   	{
   		var returnValue = window.showModalDialog(url, "", "dialogWidth:504px;dialogHeight:300px;center:yes;status:no;unadorned:no");
   		if (typeof(returnValue) == 'undefined') {
			return;
		}
   		doDelegateCallback(returnValue);
   	}
   	else
   	{
		window.open(url, "", "width=504px,height=300px,location=no,menubar=no,modal=yes");     	
	}
    
}

//*** 重做 ***//
function doRedo(){	
	var url = '<%=request.getContextPath() + IPageNavigation.INSTANCE.getPageURL(Constants.TASK_FUNCTION, "actionReason")%>' + "?flag=redo";
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

//*** 拒绝 ***//
function doReject(){
	
	var url = '<%=request.getContextPath() + IPageNavigation.INSTANCE.getPageURL(Constants.TASK_FUNCTION, "actionReason")%>' + "?flag=reject";
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

//*** 执行 / 确定 / 保存工作项 ***//
function doSubmit(buttonType){
	document.getElementById('buttonType').value=buttonType;
	var form1 =document.getElementById('form1');	
	
	if (!checkFormField(form1)) {
		return;
	}
	form1.action='<%=request.getContextPath() + IPageNavigation.INSTANCE.getPageURL(Constants.TASK_FUNCTION, "executeTaskForm.do")%>';
	disableButton();
	form1.submit();
}

function disableButton(){
	if(document.getElementById("btGet"))
		document.getElementById("btGet").disabled=true;
	if(document.getElementById("btCancel"))
		document.getElementById("btCancel").disabled=true;
	if(document.getElementById("btDelegate"))
		document.getElementById("btDelegate").disabled=true;
	if(document.getElementById("btHelp"))
		document.getElementById("btHelp").disabled=true;
	if(document.getElementById("btRedo"))
		document.getElementById("btRedo").disabled=true;
	if(document.getElementById("btReject"))
		document.getElementById("btReject").disabled=true;
	if(document.getElementById("btSaveWorkItem"))
		document.getElementById("btSaveWorkItem").disabled=true;
	if(document.getElementById("btSubmit"))
		document.getElementById("btSubmit").disabled=true;
}

//*** 指派活动 ***//
function openWinAppointNextActivity(){
   
	var url = '<%=request.getContextPath() + IPageNavigation.INSTANCE.getPageURL(Constants.TASK_FUNCTION, "appointNextActivity")%>' + '?processDefID=${workitem.processDefID}&workItemID=${workitem.workItemID}';
	
	if (isIEBrowser()) {
		window.showModalDialog(url,null,"dialogWidth:700px;dialogHeight:500px;center:yes;status:no;unadorned:no");
	} else {
		window.open(url, "", "width=700px,height=500px,location=no,menubar=no,modal=yes"); 
	}
}

//*** 指派后继活动参与者 ***//
function openWinAppointNextAppointParticipant(){
   
    var url = '<%=request.getContextPath() + IPageNavigation.INSTANCE.getPageURL(Constants.TASK_FUNCTION, "appointNextParticipant")%>' + '?processDefID=${workitem.processDefID}&workItemID=${workitem.workItemID}';
	
	if (isIEBrowser()) {
		window.showModalDialog(url,null,"dialogWidth:700px;dialogHeight:500px;center:yes;status:no;unadorned:no");
	} else {
		window.open(url, "", "width=700px,height=500px,location=no,menubar=no,modal=yes"); 
	}
}

//*** 回调方法(代办、协办回调) ***//
function doDelegateCallback(returnValueObj) {
	var names = returnValueObj.nameArray;
	var ids = returnValueObj.idArray;
	var typeCodes = returnValueObj.typeCodeArray;
	var reason = returnValueObj.reason;
	var delegType = returnValueObj.delegType;
	document.getElementById('names').value=names;
	document.getElementById('ids').value=ids;
	document.getElementById('typeCodes').value=typeCodes;
	document.getElementById('reason').value=reason;	
	document.getElementById('delegType').value=delegType;
	if(names=="" || names.length==0){
		return false;
	}
	var currentUserID = "<%=UserDataContext.current().getUser().getUserID()%>";
	var nameslist = names.split(",");
	for(var i=0; i<nameslist.length; i++){
		if((nameslist[i].toLowerCase()) == (currentUserID.toLowerCase())){
			alert("<bc:message key='myTask.CallError'/>");
			return;
		}	
	}
	var form1 = document.getElementById('form1');
	form1.action="<%=request.getContextPath() + IPageNavigation.INSTANCE.getPageURL(Constants.TASK_FUNCTION, "delegate.do")%>";
	form1.method = "POST";				
	form1.submit();
}

//*** 回调方法(重做、拒绝、收回) ***//
function doRevocationCallback(returnValueObj) {
	var reason = returnValueObj.reason;
	var delegType = returnValueObj.delegType;
	document.getElementById('reason').value=reason;
	document.getElementById('delegType').value=delegType;
	if(reason=="" || reason.length==0){
		return false;
	}
	var form1 = document.getElementById('form1');
	form1.action="<%=request.getContextPath() + IPageNavigation.INSTANCE.getPageURL(Constants.TASK_FUNCTION, "delegateWorkItem.do")%>";
	form1.method = "POST";				
	form1.submit();
}
</script>
</head>

<body style="repeat-x;margin-top:0px;margin-left:0px; margin-right:0x;margin-buttom:0px" valign="top">
<form name="form1" id="form1" method="post" action="" target="_self">
<table  class="workarea" width="100%" valign="top">
	<tr>
    	<td class="workarea_title"><bc:message key='myTask.client'/> &gt; <bc:message key='myTask.pending'/> &gt;
        	<h3><bc:message key='myTask.WorkExecu'/></h3>
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
					<td width="35%">${workitem.workItemID}</td>
					<td class="EOS_table_row" width="15%"><bc:message key='myTask.Participant'/>:</td>
					<td>${workitem.partiName}</td>
				</tr>
				<tr> 
	    			<td class="EOS_table_row"><bc:message key='myTask.WorkName'/>:</td>
	    			<td>${workitem.workItemName}</td>
	    			<td class="EOS_table_row"><bc:message key='myTask.Priority'/>:</td>
	    			<td><bc:displayPriority priority='${workitem.priority}'/></td>
				</tr>
				<tr> 
	   				<td class="EOS_table_row"><bc:message key='myTask.CState'/>:</td>
					<td><bc:displayProInstCurrentState currentState='${workitem.currentState}' flag='task'/></td>
					<td class="EOS_table_row"><bc:message key='myTask.Creation-time'/>:</td>
					<td><bc:timePatternConvert fromPattern="yyyyMMddHHmmss" toPattern="yyyy-MM-dd HH:mm:ss" fromTimeString='${workitem.createTime}' /></td>
				</tr>
				<tr> 
					<td class="EOS_table_row"><bc:message key='myTask.TLimit'/>:</td>
					<td>${workitem.limitNumDesc}</td>
					<td class="EOS_table_row"><bc:message key='myTask.Reminder'/>:</td>					
					<td><bc:timePatternConvert fromPattern="yyyyMMddHHmmss" toPattern="yyyy-MM-dd HH:mm:ss" fromTimeString='${workitem.remindTime}' /></td>
				</tr>
				<tr> 
					<td class="EOS_table_row"><bc:message key='myTask.Timeout'/>:</td>
					<td>
                  		<c:if test="${workitem.isTimeOut == 'Y'}"><bc:message key='myTask.Yes'/></c:if>
                  		<c:if test="${workitem.isTimeOut == 'N'}"><bc:message key='myTask.No'/></c:if>					
					</td>
					<td class="EOS_table_row"><bc:message key='myTask.amount'/>:</td>
					<td>${workitem.timeOutNumDesc}</td>
				</tr>
				<tr> 
	   				<td class="EOS_table_row"><bc:message key='myTask.BIID'/>:</td>
					<td>${workitem.processInstID}</td>
					<td class="EOS_table_row"><bc:message key='myTask.BIName'/>:</td>
					<td>${workitem.processInstName}</td>
				</tr>
				<tr> 
	   				<td class="EOS_table_row"><bc:message key='myTask.AIID'/>:</td>
					<td>${workitem.activityInstID}</td>
					<td class="EOS_table_row"><bc:message key='myTask.AIName'/>:</td>
					<td>${workitem.activityInstName}</td>
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
				          	<c:if test="${empty (messagelist)}">
								<tr class="EOS_table_row" align="center">
					              	<td nowrap="nowrap"></td>
					              	<td nowrap="nowrap"></td>     
					              	<td style="word-break:break-all; "></td>     
					              	<td nowrap="nowrap"></td> 
					          	</tr>
							</c:if>
				          	<c:forEach items="${messagelist}" var="message">
				  				<tr class="EOS_table_row" align="center">
					              	<td nowrap="nowrap">
					              		<bc:timePatternConvert fromPattern="yyyyMMddHHmmss" toPattern="yyyy-MM-dd HH:mm:ss" fromTimeString='${message.time}' />
					              	</td>
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
	
	<input type="hidden" id="workitemID" name="workitemID" value='${workitem.workItemID}'>
	<input type="hidden" id="processInstID" name="processInstID" value='${workitem.processInstID}'>
	<input type="hidden" id="buttonType" name="buttonType">
	<input type="hidden" id="delegType" name="delegType">
	<input type="hidden" id="reason" name="reason">	
	<input type="hidden" id="names" name="names">
	<input type="hidden" id="ids" name="ids">
	<input type="hidden" id="typeCodes" name="typeCodes">
	<tr>
		<td>
			<table width="100%" border="0" cellspacing="0" cellpadding="0" class="EOS_panel_body">
				<tr>
					<td  class="EOS_panel_head"><bc:message key='myTask.Fdate'/></td>
				</tr>
				<tr>	
					<td>	
						<table width="100%" border="0" cellspacing="0" cellpadding="0" class="form_table">
							<wfBase:showWorkItemForm id="formField" workItemID="${workitem.workItemID}" prefix="__workitemPrefix" formName="form1">
								<tr>
									<td align="left" width="15%" class="EOS_table_row">${formField.name}</td>
									<td align="left">${formField.htmlComponentCode}</td>
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
 						<c:if test="${isFreeFlow == true}">
							<input type="button"  class="button"  value="<bc:message key='myTask.Activities'/>" onclick="openWinAppointNextActivity()">
						</c:if>
						<c:if test="${isFreeFlow == false}">
						<c:if test="${isNeedAppoint == true}">
								<input type="button"  class="button" value="<bc:message key='myTask.PFollow'/>" onclick="openWinAppointNextAppointParticipant()">
							</c:if>
						</c:if>
					</td>
				</tr>
				<tr>
				<td>&nbsp;&nbsp;
				</td>
				</tr>
				<tr>
					<td>
        				<table width="100%" border="0" cellspacing="0" cellpadding="0">
          					<tr>
          						<td width="50%"> 
                        			<c:if test="${fn:substring(workitem.actionMask,1,2) eq 'Y'}">
                    					<input type="button"  name="btGet"  class="button" value = "<bc:message key='myTask.Receive'/>" onClick="doGet();">
                    				</c:if>
                    				<c:if test="${fn:substring(workitem.actionMask,0,1) eq 'Y'}">
                    					<input type="button"  name="btCancel"  class="button" value = "<bc:message key='myTask.Creceived'/>" onClick="doCancelGet();">
                    				</c:if>
                    				<c:if test="${fn:substring(workitem.actionMask,3,4) eq 'Y'}">
                    					<input type="button"  name="btDelegate" class="button" value = "<bc:message key='myTask.delegate'/>" onclick="doDelegate();">
                    				</c:if>
                    				<c:if test="${fn:substring(workitem.actionMask,5,6) eq 'Y'}">
                    					<input type="button"  name="btHelp" class="button" value = "<bc:message key='myTask.help'/>"  onclick="doHelp();">
                    				</c:if>
                    				<c:if test="${fn:substring(workitem.actionMask,7,8) eq 'Y'}">
                    					<input type="button"  name="btRedo" class="button" value = "<bc:message key='myTask.Repect'/>" onclick="doRedo();">
                    				</c:if>
                    				<c:if test="${fn:substring(workitem.actionMask,6,7) eq 'Y'}">
                    					<input type="button"  name="btReject" class="button" value = "<bc:message key='myTask.Refused'/>" onclick="doReject();">
                    				</c:if>
                    				<c:if test="${fn:substring(workitem.actionMask,2,3) eq 'Y'}">
                    					<input type="button" name="btSaveWorkItem" value="<bc:message key='myTask.SaveDate'/>" onClick="doSubmit('save');" class="button">
                    				</c:if>
                   					<c:if test="${fn:substring(workitem.actionMask,2,3) eq 'Y'}">
                   						<c:if test="${workitem.bizState eq 4}">
                   							<input type="button"  name="btSubmit" class="button" value = "<bc:message key='myTask.OK'/>" onclick="doSubmit('ok');">
                   						</c:if>
                   						<c:if test="${workitem.bizState ne 4}">
                   							<input type="button"  name="btSubmit" class="button" value = "<bc:message key='myTask.Executive'/>" onclick="doSubmit('execute');">
                   						</c:if>
                   					</c:if>                      				                        				                        				                        				                        				
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
