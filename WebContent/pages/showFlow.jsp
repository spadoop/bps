<html>
<head>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page session="false"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>流程图查看</title>
</head>
<body>
<%
	String procInstID = request.getParameter("procInstID");	
	String showBackButton = request.getParameter("showBackButton");
	String attr = "";
	if(showBackButton!=null){
		attr = "block";
	} else {
		attr = "none";
	}
%>
<%@ taglib uri="http://eos.primeton.com/tags/workflow/base" prefix="wf"%>
<%@ taglib uri="http://www.kingmed.com.cn" prefix="km"%>

<script src="<%=request.getContextPath()%>/js/Graphic.js"></script>
 <km:processGraphDecorate>
<wf:processGraph processInstID="<%=procInstID%>" zoomQuotiety="1" />
</km:processGraphDecorate>
<div style="display:<%=attr%>">
	<br>
	<input type=button value=" 返回 " onclick="history.go(-1)	">
</div>
</body>
</html>
