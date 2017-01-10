<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ page import="com.bps.client.tag.help.ParticipantResourceTreeLoader"%>
<%@ include file="/wfclient/common/common.jsp"%>
<%@page import="com.bps.client.common.i18n.ResourceMessageUtil"%>

<html>
<head>
<title><bc:message key='myTask.DelTask'/></title>
<script type="text/javascript">

//*** 初始数据 ***//
function init() {				
	var argument = window["dialogArguments"] ;
	document.getElementById('buttonOK').onclick = function () {
		var txtreason = document.getElementById('txtreason').value;
		var names = document.getElementsByName('DelegateToName.names');
		var ids = document.getElementsByName('DelegateToName.ids');
		var typeCodes = document.getElementsByName('DelegateToName.typeCodes');			
		if(txtreason == "")
		{
			txtreason="<bc:message key='Common.Nil'/>";
		}
		if(names[0].value == ""){
			alert("<bc:message key='myTask.DelAtel'/>");
		 	return;
		}
						
		var obj = {
			nameArray 		: names[0].value,
			idArray   		: ids[0].value,
			typeCodeArray 	: typeCodes[0].value,			
			reason			: txtreason,
			delegType		: "DELEG"
		};
		if (isIEBrowser()) {
			window.returnValue = obj;
		} else {
			window.opener.doDelegateCallback(obj);
		}
		window.close();
	}
}
</script>
</head>

<body marginheight="0" marginwidth="0" leftmargin="0" rightmargin="0" onload="init();">
<form action="" name="frm" id="frm">
<table width="497" border="0" cellspacing="0" cellpadding="0" class="EOS_panel_body" height="100%">
  <tr>
    <td class="EOS_panel_head" valign="middle" colspan="2"><bc:message key='myTask.SetDelTask'/></td>
  </tr>
  <tr>
    <td class="EOS_table_row" nowrap="nowrap" width="15%" align="left">&nbsp;&nbsp;<bc:message key='myTask.delegation'/>:</td>
    <td class="EOS_table_row">
    <% String choose = ResourceMessageUtil.getResourceMessage("Common.Choose", session); %>
	    <bc:selector name="DelegateToName" maxCount="5" loader="<%=ParticipantResourceTreeLoader.class.getName()%>" buttonValue="<%=choose %>"/>
	    <font style="color:red">*</font>
    </td>
  </tr>
  <tr>
    <td class="EOS_table_row" nowrap="nowrap" valign="top" width="15%" align="left">&nbsp;&nbsp;<bc:message key='myTask.DelReason'/>:</td>
    <td class="EOS_table_row" valign="top">
		<textarea rows="10" cols="50" id="txtreason" name="txtreason" class="textbox"></textarea>
    </td>
  </tr>
  <tr>
    <td class="EOS_table_row" align="center" colspan="2" valign="top">
		<input type="button" value="<bc:message key='myTask.OK'/>" class="button" id="buttonOK">
		<input type="reset"  value="<bc:message key='myTask.Reset'/>" class="button">
		<input type="button" value="<bc:message key='myTask.Cancel'/>" class="button" onclick="window.close();">
    </td>
  </tr>
</table>
</form>
</body>
</html>