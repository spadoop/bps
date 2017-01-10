<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@include file="/wfclient/common/common.jsp"%>
<%@page import="java.net.URLDecoder"%>
<%@page import="com.bps.client.common.i18n.ResourceMessageUtil"%>

<script>

window.onload = function() {
 	if (isIEBrowser()) {
		currentSelectorInfo = window.dialogArguments;	
	} else {
		currentSelectorInfo = window.opener.currentSelectorInfo;
	}
	if (trimString(currentSelectorInfo.typeCodes).length > 0) {
		var typeCodesArray = currentSelectorInfo.typeCodes.split(",");
		var namesArray = currentSelectorInfo.names.split(",");
		var idsArray = currentSelectorInfo.ids.split(",");
		for (var i = 0; i < typeCodesArray.length; i++) {
			addOptionItem(typeCodesArray[i], namesArray[i], idsArray[i]);
		}
	}
	
	adjustSelectWidth(document.getElementById("selectedSelectors"));
	
};

//增加一个选项
function addOptionItem(nodeType, nodeLabel, nodeValue) {
	var selectedList = document.getElementById("selectedSelectors");
	
	var hasAleadySelected = false;
	for(var i = 0; i < selectedList.options.length; i++) { 
		if (selectedList.options[i].value == (nodeType + "," + nodeValue)) {
			hasAleadySelected = true;
			break;
		}
	}
	if (!hasAleadySelected) {
		//固定只选择一个
		if (currentSelectorInfo.maxCount == 1) {
			delAllOptions();
		} else if (selectedList.options.length == currentSelectorInfo.maxCount) {
			alertValidateMessage("<bc:message key='tag.AlertLength'/>:" + currentSelectorInfo.maxCount);
			return;
		}
		
		var oOption = document.createElement("OPTION");
		oOption.text = nodeLabel;
		oOption.value = nodeType + "," + nodeValue;
		oOption.selected = true;
		selectedList.options.add(oOption);
	} else {
		alertValidateMessage("<bc:message key='tag.ResourceChoose'/>");
	}	
	adjustSelectWidth(document.getElementById("selectedSelectors"));
}

//删除一些选中的选项
function delOption() {
	var selectedList = document.getElementById("selectedSelectors");
	
	for(var i = selectedList.options.length - 1; i >= 0; i--) { 		
		if (selectedList.options[i].selected) {
			selectedList.remove(i);
		}
	}
	adjustSelectWidth(document.getElementById("selectedSelectors"));
}

//删除所有选项
function delAllOptions() {
	var selectedList = document.getElementById("selectedSelectors");
	
	for(var i = selectedList.options.length - 1; i >= 0; i--) { 
		selectedList.remove(i);
	}
	adjustSelectWidth(document.getElementById("selectedSelectors"));
}

//窗口关闭，并且返回值
function closeWindowAndReturnValue() {	
	var selectedList = document.getElementById("selectedSelectors");	
	var returnValueObj = new Array();	
	for(var i = 0; i < selectedList.options.length; i++) { 
		var selectedValue = {
			 id:"",
			 name:"",
			 typeCode:""
			};
		var values = selectedList.options[i].value.split(",");
		selectedValue.typeCode = values[0];
		selectedValue.name = selectedList.options[i].text;
		selectedValue.id = values[1];
		returnValueObj.push(selectedValue);
	}
	
	if (isIEBrowser()) {		
		window.returnValue = returnValueObj;
	} else {
		if (currentSelectorInfo.needReturn) {
			setSelector(window.opener.document, currentSelectorInfo.textName, returnValueObj);
		}		
		
		if (currentSelectorInfo.callback == undefined || currentSelectorInfo.callback == 'undefined' 
				|| currentSelectorInfo.callback == "" || currentSelectorInfo.callback == null || currentSelectorInfo.callback == "null") {			
		} else {		
			var retrunValueSelectorString = returnValueObj.toJSONString();
			eval("window.opener." + currentSelectorInfo.callback + "( " + retrunValueSelectorString + ", '" + currentSelectorInfo.callbackParam + "');");	
		}
	}
	
	window.close();
}

//窗口关闭
function closeWindowOnly() {	
	window.close();
}
</script>

<%
String loader = request.getParameter("loader");
if (loader == null || loader.trim().length() == 0) {
	loader = (String)request.getAttribute("loader");
}
String loaderParam = request.getParameter("loaderParam");
if (loaderParam == null || loaderParam.trim().length() == 0) {
	loaderParam = (String)request.getAttribute("loaderParam");
}
if (loaderParam == null || loaderParam.trim().length() == 0) {
	loaderParam = ResourceMessageUtil.getResourceMessage("jsp.resource", session);
}
loaderParam = URLDecoder.decode(loaderParam,"UTF-8");
String title = request.getParameter("title");
if (title == null || title.trim().length() == 0) {
	title = (String)request.getAttribute("title");
}
if (title == null || title.trim().length() == 0) {
	title = ResourceMessageUtil.getResourceMessage("jsp.resource", session);
}
title = URLDecoder.decode(title,"UTF-8");
%>

<html>
	<head>
	<title><bc:message key='set.Choose'/><%=title%></title>
	</head>
	<body topmargin="0" leftmargin="0" rightmargin="0" marginwidth="0">	
<table class="workarea" height="100%"  cellpadding="0" cellspacing="0" style='table-layout:fixed;'>
  <tr>
    <th width="45%" height="23" scope="col" style="vertical-align:middle;padding-top:3px;text-align:left;"><bc:message key='tag.PleaseChoose'/><%=title%></th>
    <th width="70" scope="col">&nbsp;</th>
    <th width="45%" scope="col" style="vertical-align:middle; padding-top:3px; text-align:left; "><bc:message key='tag.HaveChoosed'/><%=title%></th>
  </tr>
  <tr>
    <td height="261" valign="top"  style="border:1px solid #CCCCCC;" bgcolor="#FFFFFF"> 
   		<div id="fromDiv"  style="height:100%;width:100%;overflow:auto;"> 
   			<nobr>
			<bc:tree loader="<%=loader%>" loaderParam="<%=loaderParam%>"/>
			</nobr>
		</div>
     </td>
    <td><table border="0" width="100%">
				<tr><td align="center"><input type="button" class="button" style="width: 60px;text-align: center;" value="<bc:message key='set.Delete'/>" onClick="delOption()"></td></tr>
				<tr><td align="center"><input type="button" class="button" style="width: 60px;text-align: center;" value="<bc:message key='tag.DeleteAll'/>" onClick="delAllOptions()"></td></tr>
		</table>
	</td>
    <td valign="top" height="261" >
	    <div id="toDiv" style="height:100%;width:100%;overflow:auto;"> 
	    	<select id="selectedSelectors" multiple="multiple" size="17" style="height:100%;width:100%;"> </select>
	  	</div>
  	</td>
  </tr>

  <tr>
    <td align="center" colspan="4"  valign="baseline">
    <hr><br>
    <input type="button" class="button" name="close" value="<bc:message key='myTask.OK'/>" onClick="closeWindowAndReturnValue()">
			&nbsp;<input type="button" class="button" name="close" value="<bc:message key='myTask.Cancel'/>" onClick="closeWindowOnly()">
	</td>
  </tr>
</table>
	</body>
</html>	