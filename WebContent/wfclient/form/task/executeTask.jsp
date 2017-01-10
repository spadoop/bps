<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://eos.primeton.com/tags/workflow/base" prefix="wfBase"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="bc" uri="http://bps.primeton.com/tags/bpsclient" %>
<link href="<%=request.getContextPath()%>/wfclient/css/style-custom.css" type="text/css" rel="stylesheet">
<link href="<%=request.getContextPath()%>/wfclient/css/style-component.css" type="text/css" rel="stylesheet">
<%@page import="com.bps.client.common.Util"%>
<%@page import="com.bps.client.user.UserBean"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="com.bps.client.common.BPSServiceFacade"%>
<html>
<head>
<title><bc:message key='myTask.WorkExecu'/></title>

<script type="text/javascript">

//*** 执行 / 确定 / 保存工作项 ***//
function finishWorkItem(){
	document.getElementById('buttonType').value="execute";
	var form1 =document.getElementById('form1');	
	
	if(!formEngine.prepareForm()){
		return;
	}

	form1.action='<%=request.getContextPath()%>'+"/wfclient/form/task/executeForm.jsp";
	
	form1.submit();
}

function saveWorkItem(){
	document.getElementById('buttonType').value="save";
	var form1 =document.getElementById('form1');	
	
	if(!formEngine.prepareForm()){
		return;
	}

	form1.action='<%=request.getContextPath()%>'+"/wfclient/form/task/executeForm.jsp";
	
	form1.submit();
}

</script>
</head>

<body style="repeat-x;margin-top:0px;margin-left:0px; margin-right:0x;margin-buttom:0px" valign="top">
<form name="form1" id="form1" method="post" action="" target="_self">
<input type="hidden" id="workitemID" name="workitemID" value='${workitem.workItemID}'/>
<input type="hidden" id="processInstID" name="processInstID" value='${workitem.processInstID}'/>
<input type="hidden" id="processDefID" name="processDefID" value='${workitem.processDefID}'/>
<input type="hidden" id="buttonType" name="buttonType"/>


<table  class="workarea" width="100%" valign="top" height="100%">
	<tr>
    	<td class="workarea_title"><bc:message key='myTask.client'/> &gt; <bc:message key='myTask.pending'/> &gt;
        	<h3><bc:message key='myTask.WorkExecu'/></h3>
    	</td>
  	</tr>
	
	<tr>
		<td>
	<% 	
		String workItemID = Util.getValue(request,"workItemID")+"";
		
		UserBean user = (UserBean)Util.getValue(session,"user");
	
		Map params = new HashMap();
		
		String locale = (String) session.getAttribute("locale");
		params.put("locale",locale);
		params.put("userID",user.getUserID());
		params.put("userName",user.getUserName());
		
		String htmls = BPSServiceFacade.getInstance().getBPSFormManager().showWorkItemForm(Long.parseLong(workItemID), params, request.getContextPath());
	%>
	<%=htmls %>
		</td>
	</tr>
</table>
</form>
</body>
</html>
