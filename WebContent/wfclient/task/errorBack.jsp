<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.bps.client.common.IPageNavigation"%>
<%@ page import="com.bps.client.common.Constants"%>
<%@ include file="/wfclient/common/common.jsp"%>
<html>
<body>
<form action="<%=request.getContextPath() + IPageNavigation.INSTANCE.getPageURL(Constants.TASK_FUNCTION, "tasklistforSelf.do")%>" name="form1" id="form1" target="_self"></form>
</body>
<script language="JavaScript">
	alert("<bc:message key='myTask.OpError'/>");
	form1.submit();
</script>
</html>