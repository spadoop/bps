<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/wfclient/common/common.jsp"%>
<html>
<head>
<title>Title</title>
</head>
<body style="background:#FFFFFF;margin-top:0px;margin-left:0px;margin-buttom:0px;width: 100%;overflow: auto;">
<table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%">
	<tr>
		<td>
		<%
			String processInstID = request.getParameter("processInstID");
			String processID = request.getParameter("processID");
			String zoomQuotiety = request.getParameter("zoomQuotiety");
		%>
		<wfBase:processGraph processInstID="<%=processInstID %>" processID="<%=processID %>" zoomQuotiety="<%=zoomQuotiety %>" />
		</td>
	</tr>
</table>
</body>
</html>
