<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.bps.client.common.Constants"%>
<%@ page import="com.bps.client.common.IPageNavigation"%>
<%@ include file="/wfclient/common/common.jsp"%>
<html>
<head>
<title><bc:message key='process.ResultImplements'/></title>
<script type="text/javascript">
function back(){
   form1.submit();
}
</script>
</head>

<body  style="background:#FFFFFF;repeat-x;margin-top:10px;margin-left:12px;margin-buttom:0px;">
	<table border="0" cellpadding="0" cellspacing="0" class="workarea" width="100%" height="100%">
 		 <tr>
   			<td width="100%" valign="top">
<form action='<%=request.getContextPath() + IPageNavigation.INSTANCE.getPageURL(Constants.PROCESS_FUNCTION, "process.do") + "?method=queryProcessInfoList" %>' 
   name="form1" id="form1" method="post">
<table class="result" border="0" width="30%" align="center">

 <tr class="result_content_" border="0" width="30%"> 
    <td align="center" colspan="2"><b><bc:message key='process.ResultImplements'/>:</b></td>
  </tr>
  <tr><td></td></tr>
  <tr><td></td></tr>
   <tr class="result_content_" border="0" width="30%"> 
   		<c:if test="${processInstID>0 || processInstID<0}">
   			<td align="center" colspan="2"><b><bc:message key='process.ProcessSuccess'/></b></td>
   		</c:if>
		<c:if test="${processInstID==0}">
   			<td align="center" colspan="2"><b><bc:message key='process.ProcessFailed'/></b></td>
   		</c:if>
  </tr>
<tr>
	<td align="center">
		<input type="button" name="btBack" value="<bc:message key='process.Continue'/>" class="button" onclick="javascript:back();">
	</td>
</tr>
</table>
</form>
</td>
  </tr>
</table>
</body>
</html>
