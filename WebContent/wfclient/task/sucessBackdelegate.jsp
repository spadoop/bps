<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.bps.client.common.IPageNavigation"%>
<%@ page import="com.bps.client.common.Constants"%>
<%@ include file="/wfclient/common/common.jsp"%>
<html>
<body>
<form name="form1" id="form1" target="_self">
<input type="hidden" id="method" name="method">
</form>
</body>
<script language="JavaScript">
	alert("<bc:message key='myTask.OpeSuccess'/>");
	var form1 =document.getElementById('form1');
	form1.action='<%=request.getContextPath() + IPageNavigation.INSTANCE.getPageURL(Constants.TASK_FUNCTION, "tasklist.do")%>';	
	document.getElementById("method").value="finishedforOthers";
	form1.submit();
</script>
</html>