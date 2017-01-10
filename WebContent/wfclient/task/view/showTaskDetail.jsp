<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.bps.client.common.IPageNavigation"%>
<%@ page import="com.bps.client.common.Constants"%>
<%@ include file="/wfclient/common/common.jsp"%>
<html>
<head>
<title><bc:message key='view.WorkItemD'/></title>
<script type="text/javascript">

//*** 执行 ***//
function doExecute(id){
	var showType = '${showType}';
	var form1 =document.getElementById('form1');
	form1.action="<%=request.getContextPath() + IPageNavigation.INSTANCE.getPageURL(Constants.TASK_FUNCTION, "executeTask.do")%>";
	if(showType == "entr" ){
		form1.action="<%=request.getContextPath() + IPageNavigation.INSTANCE.getPageURL(Constants.TASK_FUNCTION, "withdrawTask.do")%>";
	}
    form1.workitemID.value=id;
	form1.submit();
}

//*** 缩放比例 ***//
function handleZoom(selectObj) {
	frm.action='<%=request.getContextPath()%>/wfclient/task/view/graph.jsp';
	frm.processInstID.value='${task.processInstID}';
	frm.zoom.value= selectObj.value;
	frm.submit();		
}

//*** 返回 ***//
function doBack(){
	var showType = '${showType}';
	var form1 =document.getElementById('frm');
	if(showType == "exe"){
		form1.action="<%=request.getContextPath() + IPageNavigation.INSTANCE.getPageURL(Constants.TASK_FUNCTION, "tasklistforSelf.do")%>";
	}
	if(showType == "fin"){
		form1.action="<%=request.getContextPath() + IPageNavigation.INSTANCE.getPageURL(Constants.TASK_FUNCTION, "tasklistforOthers.do")%>";		
	}
	if(showType == "entr" ){
		form1.action="<%=request.getContextPath() + IPageNavigation.INSTANCE.getPageURL(Constants.TASK_FUNCTION, "tasklistfinishedforOthers.do")%>";
	}	
	if(showType == "entrfin"){
		form1.action="<%=request.getContextPath() + IPageNavigation.INSTANCE.getPageURL(Constants.TASK_FUNCTION, "tasklistfinishedforSelf.do")%>";
	}
	
	frm.target='_self';
	frm.submit();

//	history.go(-1);
}
</script>
</head>
<body style="repeat-x;margin-top:0px;margin-left:0px; margin-right:0x;margin-buttom:0px">
	<table border="0" cellpadding="0" cellspacing="0" class="workarea" width="100%">
	<tr>
		<td class="workarea_title">
			<bc:message key='myTask.client'/> &gt; ${showTitle} &gt; <h3><bc:message key='view.WorkView'/></h3>
		</td>
	</tr>
	<tr>
		<td>
		<table width="100%" border="0" cellspacing="0" cellpadding="0"
			class="EOS_panel_body">
			<tr>
				<td class="EOS_table_row" width="60%" height="100%">
				<form action="#" name="frm" id="frm" target="graph" method="post">
					<input type="hidden" id="processInstID" name="processInstID">
					<input type="hidden" id="zoom" name="zoom">
					<table width="100%" bgcolor="fefefe" height="100%">
						<tr height="20" valign="top">
							<td align="right" height="12"><bc:message key='view.ScalFeatures'/>: 
								<select onChange="handleZoom(this)" size="1" name="zoomQuotiety">
									<option value="1.0"><bc:message key='view.Automatic'/></option>
									<option value="0.4">40%</option>
									<option value="0.55">55%</option>
									<option value="0.7">70%</option>
									<option value="0.85">85%</option>
									<option value="1.0">100%</option>
									<option value="1.5">150%</option>
									<option value="2.0">200%</option>
								</select>
							</td>
						</tr>
						<tr>
							<td valign="top">
								<iframe id="graph" name="graph" 
										src='<%=request.getContextPath()%>/wfclient/task/view/graph.jsp?zoomQuotiety=1.0&processInstID=${task.processInstID}'
										scrolling="auto" frameBorder="0" style="size:auto; height:385px; border: 1px; red" width="98%"></iframe>
							</td>
						</tr>
					</table>
				</form>
				</td>
				<td class="EOS_table_row" width="40%" valign="top">
				<form action="#" name="form1" id="form1">
					<input type="hidden" id="workitemID" name="workitemID">
					<table class="workarea" width="100%" height="100%">
						<tr>
							<td>
							<table width="100%" border="0" cellspacing="0" class="form_table"
								cellpadding="0" height="100%">
								<tr class="tableHeadTR" align="center">
									<td colspan="2" class="EOS_panel_head"><bc:message key='myTask.WorkDetails'/></td>
								</tr>
								<tr>
									<td class="EOS_table_row"><bc:message key='myTask.BIID'/>:</td>
									<td>${task.processInstID}</td>
								</tr>
								<tr>
									<td class="EOS_table_row"><bc:message key='myTask.AIID'/>:</td>
									<td>${task.activityInstID}</td>
								</tr>
								<tr>
									<td class="EOS_table_row" width="25%"><bc:message key='myTask.WorkID'/>:</td>
									<td>${task.workItemID}</td>
								</tr>
								<tr>
									<td class="EOS_table_row"><bc:message key='myTask.WorkName'/>:</td>
									<td>${task.workItemName}</td>
								</tr>
								<tr>
									<td class="EOS_table_row" width="15%"><bc:message key='myTask.Participant'/>:</td>
									<td>${task.partiName}</td>
								</tr>
								<tr>
									<td class="EOS_table_row"><bc:message key='myTask.CState'/>:</td>
									<td><bc:displayProInstCurrentState currentState='${task.currentState}' flag='task'/></td>
								</tr>
								<tr>
									<td class="EOS_table_row"><bc:message key='myTask.Creation-time'/>:</td>
									<td><bc:timePatternConvert fromPattern="yyyyMMddHHmmss" toPattern="yyyy-MM-dd HH:mm:ss" fromTimeString='${task.createTime}' /></td>
								</tr>
								<tr>
									<td class="EOS_table_row"><bc:message key='myTask.TLimit'/>:</td>
									<td>${task.limitNumDesc}</td>
								</tr>
								<tr>
									<td class="EOS_table_row"><bc:message key='myTask.Reminder'/>:</td>									
									<td><bc:timePatternConvert fromPattern="yyyyMMddHHmmss" toPattern="yyyy-MM-dd HH:mm:ss" fromTimeString='${task.remindTime}' /></td>
								</tr>
								<tr>
									<td class="EOS_table_row"><bc:message key='myTask.Timeout'/>:</td>
									<td>
										<c:if test="${task.isTimeOut == 'Y'}"><bc:message key='myTask.Yes'/></c:if>
	                      				<c:if test="${task.isTimeOut == 'N'}"><bc:message key='myTask.No'/></c:if>
									</td>
								</tr>
								<tr>
									<td class="EOS_table_row"><bc:message key='myTask.amount'/>:</td>
									<td>${task.timeOutNumDesc}</td>
								</tr>
								<tr>
									<td class="EOS_table_row"><bc:message key='view.CompletionTime'/>:</td>
									<td><bc:timePatternConvert fromPattern="yyyyMMddHHmmss" toPattern="yyyy-MM-dd HH:mm:ss" fromTimeString='${task.endTime}' /></td>
								</tr>
								<tr>
									<td class="EOS_table_row"><bc:message key='myTask.BIName'/>:</td>
									<td style="table-layout:fixed;word-wrap: break-word">
									${task.processInstName}</td>
								</tr>
								<tr>
									<td class="EOS_table_row"><bc:message key='myTask.AIName'/>:</td>
									<td>${task.activityInstName}</td>
								</tr>
							</table>
							</td>
						</tr>
						<tr>
							<td align="center" class="form_bottom">
								<c:if test="${task.currentState == 10 || task.currentState == 4}">
									<input type="button" name="btSubmit" value="<bc:message key='myTask.Executive'/>" class="button" onclick='doExecute(${task.workItemID});'>
								</c:if>
								<input type="button" name="btReturn" value="<bc:message key='query.Back'/>" class="button" onclick='doBack();'>
							</td>
						</tr>
					</table>
				</form>
				</td>
			</tr>
		</table>
		</td>
	</tr>
	<tr>
		<td>
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td class="EOS_panel_head"><bc:message key='myTask.WOpInformation'/></td>
			</tr>
			<tr>
				<td>
				<table width="100%" class="EOS_table" border="0"
					style="border-collapse: collapse" id="plist">
					<tr class="EOS_table_head">
						<th nowrap="nowrap" align="center" width="20%"><bc:message key='myTask.Time'/></th>
						<th nowrap="nowrap" align="center" width="20%"><bc:message key='myTask.OpType'/></th>
						<th nowrap="nowrap" align="center" width="40%"><bc:message key='myTask.Content'/></th>
						<th nowrap="nowrap" align="center" width="20%"><bc:message key='myTask.Tsource'/></th>
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
</table>
</body>
</html>
