<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.bps.client.common.Constants"%>
<%@ page import="com.bps.client.common.IPageNavigation"%>
<%@ include file = "/wfclient/common/common.jsp"%>
<html>
<head>
<title><bc:message key='process.ProcessList'/></title>
</head>
<body style="background:#FFFFFF;repeat-x;margin-top:10px;margin-left:12px;margin-buttom:0px;">
<table border="0" cellpadding="0" cellspacing="0" class="workarea" width="100%" height="100%">
	<tr>
		<td class="workarea_title"><bc:message key='query.StartProcess'/> &gt; <h3><bc:message key='process.ProcessList'/></h3></td>
	</tr>
	<tr>
		<td width="100%" valign="top">
		<form action="#" id="form1" name="form1" method="post">
		<input type="hidden" id="processDefID" name="processDefID">
		<input type="hidden" id="flag" name="flag" value=""/>
		<input type="hidden" id="methodActionID" name="method" value="queryProcessInfoList"/>
		<table border="0" width="100%" class="EOS_panel_body">
			<tr>
				<td class="EOS_panel_head"><bc:message key='process.ProcessDList'/></td>
			</tr>
			<tr>
				<td>
				<table border="0" class="EOS_table" width="100%">
					<tr  align="center" style="width:100%">
						<th height="25" width="5%" nowrap="nowrap"><bc:message key='myTask.Operation'/></th>
						<th height="25" width="20%" nowrap="nowrap"><bc:message key='myTask.BPName'/></th>
						<th height="25" width="10%" nowrap="nowrap"><bc:message key='process.ProcessVerNumber'/></th>
						<th height="25" width="10%" nowrap="nowrap"><bc:message key='process.ProcessOwner'/></th>
						<th height="25" width="10%" nowrap="nowrap"><bc:message key='process.LastModified'/></th>
					</tr>
					<% int flag =0;%>					
					<c:forEach items="${processDefinesList}" var="process" >
						<tr class="EOS_table_row" onMouseOver="this.className='EOS_table_selectRow'" onMouseOut="this.className='EOS_table_row'">
							<td style="text-align:center;"><a class="link" href='javaScript:startProcess(${process.processDefID});'><bc:message key='process.Start'/></a></td>
							<td nowrap="nowrap"><a class="link"	href='javaScript:viewProcess(${process.processDefID});'>${process.processChName}(${process.processDefName})</a></td>
							<td>${process.versionSign}</td>
							<td>${process.operator}</td>
							<td nowrap="nowrap"><bc:timePatternConvert fromPattern="yyyyMMddHHmmss" toPattern="yyyy-MM-dd HH:mm:ss" fromTimeString='${process.updateTime}' /></td>
						</tr>
						<%flag++; %>
					</c:forEach>					
					<%for(int i = flag; i < 10;i++){ %>
						<tr class="EOS_table_row"><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td></tr>
					<%} %>
				</table>
				</td>
			</tr>
			<tr>
				<td colspan="10" class="result_bottom" align="right">
					<c:set var="action" value='<%=request.getContextPath() + IPageNavigation.INSTANCE.getPageURL(Constants.PROCESS_FUNCTION, "process.do")%>'/>
					<%@ include file="/wfclient/common/pagination.jsp"%>
				</td>
			</tr>
		</table>
		</form>
		</td>
	</tr>
</table>
</body>
</html>
<script type="text/javascript">

var form1 =document.getElementById('form1');

window.onload = function() {
	form1.elements["methodActionID"].value="queryProcessInfoList";
};

//*** 启动流程 ***//
function startProcess(id){	
	form1.action="<%=request.getContextPath() + IPageNavigation.INSTANCE.getPageURL(Constants.PROCESS_FUNCTION, "process.do")%>";
    form1.processDefID.value = id;
    form1.elements["methodActionID"].value="displayStartProcessPage";	
	form1.submit();
}

//查看流程
function viewProcess(id){
	form1.action="<%=request.getContextPath() + IPageNavigation.INSTANCE.getPageURL(Constants.PROCESS_FUNCTION, "process.do")%>";
    form1.processDefID.value = id;
    form1.elements["methodActionID"].value="queryProcessInfoDetail";	
	form1.submit();
}
</script>
