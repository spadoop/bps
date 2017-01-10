<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ include file="/wfclient/common/common.jsp"%>
<html>
<head>
<title><bc:message key='myTask.RecWork'/></title>
<%
	String flag = request.getParameter("flag");
	if (flag == null || flag.trim().length() == 0) {
		flag = (String)request.getAttribute("flag");
	}
%>
<script type="text/javascript">

//*** 初始数据 ***//
function init() {
	var argument = '<%=flag %>';	
	refreshText(argument);
	document.getElementById('buttonOK').onclick = function () {
		var txtreason = document.getElementById('txtreason').value;
		if(txtreason == "")
		{
			txtreason="<bc:message key='Common.Nil'/>";
		}
		var obj = {		
			reason			: txtreason,
			delegType		: argument
		};
		if (isIEBrowser()) {
			window.returnValue = obj;
			
		} else {
			window.opener.doRevocationCallback(obj);
		}
		window.close();		
	}
}

//*** 标题显示 ***//
function refreshText(arg){
    if(arg=="reject"){
    	document.getElementById('j1').style.display='block';
    	document.getElementById('j2').style.display='block';
    	document.getElementById('c1').style.display='none';
    	document.getElementById('c2').style.display='none';
    	document.getElementById('s1').style.display='none';
    	document.getElementById('s2').style.display='none';
    }
	if(arg=="redo"){
    	document.getElementById('j1').style.display='none';
    	document.getElementById('j2').style.display='none';
    	document.getElementById('s1').style.display='none';
    	document.getElementById('s2').style.display='none';
    	document.getElementById('c1').style.display='block';
    	document.getElementById('c2').style.display='block';
    }
    if(arg=="withdraw"){
    	document.getElementById('j1').style.display='none';
    	document.getElementById('j2').style.display='none';
    	document.getElementById('s1').style.display='block';
    	document.getElementById('s2').style.display='block';
    	document.getElementById('c1').style.display='none';
    	document.getElementById('c2').style.display='none';
    }
}

</script>

</head>
<body marginheight="0" marginwidth="0" leftmargin="0" rightmargin="0" onload="init()">
<form action="" name="frm" id="frm">
<table width="497" border="0" cellspacing="0" cellpadding="0" class="EOS_panel_body" height="100%">
  <tr>
    <td class="EOS_panel_head" align="left" colspan="4"><div id="j1"><bc:message key='myTask.RefusedWork'/></div><div id="c1"><bc:message key='myTask.ReWork'/></div><div id="s1"><bc:message key='myTask.RecWork'/></div></td>
  </tr>
  <tr>
    <td class="EOS_table_row" align="left" colspan="4">&nbsp;</td>
  </tr>
  <tr valign="middle">
    <td class="EOS_table_row" nowrap="nowrap" width="15%" valign="top"><div id="j2">&nbsp;&nbsp;<bc:message key='myTask.RefReason'/>:</div><div id="c2">&nbsp;&nbsp;<bc:message key='myTask.ReaReason'/>:</div><div id="s2">&nbsp;&nbsp;<bc:message key='myTask.RecReason'/>:</div></td>
    <td class="EOS_table_row" valign="top">
		<textarea rows="10" cols="50" id="txtreason" name="txtreason" class="textbox"></textarea>
    </td>
  </tr>
  <tr>
    <td class="EOS_table_row" align="center" colspan="4" valign="top">
		<input type="button" value="<bc:message key='myTask.OK'/>" class="button" id="buttonOK">
		<input type="reset"  value="<bc:message key='myTask.Reset'/>" class="button">
		<input type="button" value="<bc:message key='myTask.Cancel'/>" class="button" onclick="window.close();">
    </td>
  </tr>
</table>
</form>
</body>
</html>