<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.bps.client.common.IPageNavigation"%>
<%@ page import="com.bps.client.common.Constants"%>
<%@ include file="/wfclient/common/common.jsp"%>
<html>
<head>
	<title><bc:message key='frame.BPSClient'/></title>
	<link href="../css/style-custom.css" type="text/css" rel="stylesheet" >
	<link href="../css/style.css" type="text/css" rel="stylesheet" >
    <link rel="shortcut icon" href="<%=request.getContextPath()%>/wfclient/images/favicon/favicon.ico" type="image/x-icon">
</head>

<frameset rows="73,*,25" frameborder="no" border="0" framespacing="0">
	<frame src="<%=request.getContextPath() + IPageNavigation.INSTANCE.getPageURL(Constants.FRAME_FUNCTION, "top")%>" name="top" scrolling="auto" noresize="noresize" id="top" style="height: 72px;"/>
  	<frame src="<%=request.getContextPath() + IPageNavigation.INSTANCE.getPageURL(Constants.TASK_FUNCTION, "myTask")%>" name="contentWin" id="contentWin"/>
  	<frame src="<%=request.getContextPath() + IPageNavigation.INSTANCE.getPageURL(Constants.FRAME_FUNCTION, "bottom")%>" name="bottom" scrolling="No" noresize="noresize" id="bottom"/>
</frameset>

<noframes>	
<body>
</body>
</noframes>
</html>
