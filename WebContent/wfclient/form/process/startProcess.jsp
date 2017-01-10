<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/wfclient/common/common.jsp"%>

<%@page import="com.bps.client.common.Util"%>
<%@page import="com.bps.client.user.UserBean"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="com.eos.workflow.data.WFProcessDefine"%>
<%@page import="com.bps.client.common.BPSServiceFacade"%>
<html>
<head>
<title><bc:message key='intergration.StartForm'/></title>
<%

	String formName = Util.getValue(request,"formName")+"";
	WFProcessDefine process = (WFProcessDefine)Util.getValue(request,"process");
	
 %>
<script language="JavaScript">

//*** 启动流程 ***//
function startProcess(){
	
	var len= arguments.length;
	
	if(len == 0){
		document.getElementById("processInstName").value = '<%=process.getProcessChName() %>';
	
	}else if(len == 1){
    	document.getElementById("processInstName").value = arguments[0];
    }else if(len == 2){
	    document.getElementById("processInstName").value = arguments[0];
		document.getElementById("processInstDesc").value = arguments[1];
    }
	
	if(!formEngine.prepareForm()){
		return;
	}

	form1.action='<%=request.getContextPath() + "/wfclient/form/process/executeProcess.jsp"%>';	
	form1.submit();
}

</script>
</head>
<body style="background:#FFFFFF;repeat-x;margin-top:10px;margin-left:12px;margin-buttom:0px;">
<table border="0" cellpadding="0" cellspacing="0" class="workarea" width="100%" height="100%">
 		 <tr>
   			<td width="100%" valign="top">
<form action="#" id="form1" name="form1" method="post">
<input type="hidden" id="processDefID" name="processDefID" value="${process.processDefID}" />
<input type="hidden" id="processDefName" name="processDefName" value="${process.processDefName}" />
<input type="hidden" id="methodActionID" name="method" />
<input type="hidden" id="processInstName" name="processInstName" />
<input type="hidden" id="processInstDesc" name="processInstDesc" />
<table border="0px" class="workarea" width="100%" cellspacing="0px" cellpadding="0px">
	<tr>
		<td class="workarea_title"><bc:message key='query.StartProcess'/> &gt; <bc:message key='process.ProcessList'/> &gt; <h3><bc:message key='intergration.StartForm'/></h3></td>
	</tr>
	<tr>
		<td>   
			<% 	
			
			String processDefName = process.getProcessDefName();
			
			UserBean user = (UserBean)Util.getValue(session,"user");
		
			Map params = new HashMap();
			
			String locale = (String) session.getAttribute("locale");
			params.put("locale",locale);
			params.put("userID",user.getUserID());
			params.put("userName",user.getUserName());
					
			String htmls = BPSServiceFacade.getInstance().getBPSFormManager().showProcessForm(processDefName, params, request.getContextPath());
		%>
		<%=htmls %>
		</td>
	</tr>
</table>
</form>
</td>
  </tr>
</table>
</body>
</html>
