<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/wfclient/common/common.jsp"%>
<%@page import="com.bps.client.common.Constants"%>
<%@page import="com.bps.client.common.IPageNavigation"%>
<%@page
	import="com.primeton.bps.component.manager.api.BPSMgrServiceClientFactory"%>
<%
	boolean mul = false;
	try {
		mul = BPSMgrServiceClientFactory.getDefaultClient()
		.getBPSWSManager().isMultiTenantMode();
	} catch (Exception e) {
		e.printStackTrace();
	}
%>
<%
	String language = (String) request.getParameter("language");
	String lan = request.getLocale().toString();
	if (language == null || "".equals(language)) {
		if (lan.equalsIgnoreCase("en_US")
		|| lan.equalsIgnoreCase("zh_CN")) {
			language = lan;
		} else {
			language = "en_US";
		}
	}
	session.setAttribute(Constants.LOCALE, language);
%>
<html>
<head>
<title><bc:message key='login_jsp.title' /></title>

<script>
	if(top!=window){
		top.location = window.location;
	}
	window.onload=function (){
		focusUserName();
	}
		
	function focusUserName() {
		if(<%=mul %> == true){ 
			document.forms["userlogin"].elements["tenantId"].focus();
		}else
			document.forms["userlogin"].elements["userName"].focus();
	}		
		
	function doSubmit(){
		var userObj = document.forms["userlogin"].elements["userName"];
			
		 if(trimString(userObj.value) == ''){
		 	alertValidateMessage("<bc:message key='user.Name'/>", userObj)
		 	focusUserName();
		 	return false;
		 } else {		 		
		 	return true;
		 }		 		
	}
	
	function fadeInTip(){		
		var imageHelp = document.getElementById('help1');
		var box = document.getElementById('tip12');			
		box.style.display = "";
		box.style.left= imageHelp.offsetLeft + 20 + "px";
		box.style.top= imageHelp.offsetTop + 5 + "px";
		
	}

	function fadeOutTip(event){
		var box = document.getElementById('tip12');		
		box.style.display = "none";
	}
	
	function changeLanguage(){
		var frm = document.userlogin;
		frm.action = "<%=request.getContextPath()%>/wfclient/user/login.jsp";
		frm.submit();
	}
</script>

<style>
/* 以下为纯CSS圆角 */
.divBox {padding: 10px;border-right: 1px solid #aaa;border-left: 1px solid #aaa; }
b, u, i {display: block;height: 1px;overflow: hidden;}
/* 第一种圆角 */
b.f1 {margin: 0 4px;background: #aaa;}
b.f2 {margin: 0 2px;border-right: 2px solid #aaa;border-left: 2px solid #aaa;}
b.f3 {height: 2px;margin: 0 1px;border-right: 1px solid #aaa;border-left: 1px solid #aaa;}
</style>
<link rel="shortcut icon"
	href="<%=request.getContextPath()%>/wfclient/images/favicon/favicon.ico"
	type="image/x-icon">
</head>

<body topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0"
	marginwidth="0">

<table width="100%" border="0" cellspacing="0" cellpadding="0"
	height="100%">
	<tr height="50">
		<td valign="top">
		<table width="100%" border="0" align="left" cellpadding="0"
			cellspacing="0">
			<tr>
				<td width="772" height="50"><img
					src="<%=request.getContextPath()%>/wfclient/images/BPS6.0-Bar.jpg"></td>
				<td style="background:#016F88;border-bottom:1px solid #fff;">&nbsp;</td>
			</tr>
		</table>
		</td>
	</tr>
	<tr
		style="background:url(<%=request.getContextPath()%>/wfclient/images/BPSBg.jpg);background-repeat:repeat-x;">
		<td height="105" align="center" valign="top">&nbsp;</td>
	</tr>
	<tr>
		<td align="center" valign="top" style="background:#76BAC5;">
		<table width="491" height="424" border="0" cellpadding="0"
			cellspacing="0"
			background="<%=request.getContextPath()%>/wfclient/images/BPSClientLogin.jpg">
			<tr>
				<td align="center" valign="middle">
				<form name="userlogin"
					action="<%=request.getContextPath() + IPageNavigation.INSTANCE.getPageURL(Constants.USER_FUNCTION, "login.do") %>"
					method="post" onsubmit="return doSubmit();">
				<table width="100%" height="80" border="0" cellpadding="2"
					cellspacing="0">
					<tr>
						<td width="28%">&nbsp;</td>
						<td width="72%">&nbsp;</td>
					</tr>
					<tr>
						<td width="28%">&nbsp;</td>
						<td width="72%"><span style="color:red">${errorMessage}</span></td>
					</tr>
					<%
					if (mul) {
					%>
					<tr>
						<td align="right"><bc:message key='login_jsp.tenantId' />&nbsp;&nbsp;&nbsp;</td>
						<td align="left"><input type="text" name="tenantId"
							class="textbox" style="width:200px" size="30" /></td>
					</tr>
					<%
					}
					%>

					<tr>
						<td align="right"><bc:message key='login_jsp.userName' />&nbsp;&nbsp;&nbsp;</td>
						<td align="left"><input type="text" name="userName"
							value="${userName}" class="textbox" style="width:200px" size="30" />
						<img id="help1" width="16px" height="16px"
							style="position:absolute;"
							src="<%=request.getContextPath()%>/wfclient/images/help.gif"
							onmouseover="fadeInTip(event);" onmouseout="fadeOutTip(event);">
						</td>
					</tr>
					<tr>
						<td align="right"><bc:message key='login_jsp.password' />&nbsp;&nbsp;&nbsp;</td>
						<td align="left"><input type="password" name="password"
							value="${password}" class="textbox" style="width:200px" size="30" />
						</td>
					</tr>
					<tr>
	                <td align="right"><bc:message key='login_jsp.language'/>&nbsp;&nbsp;&nbsp;</td>
	                <td align="left">
	                  <select name="language" onchange="changeLanguage();" class="select" style="width:200px">
	                    <option value="en_US" <% if (language != null && language.equals("en_US")) out.println("selected"); %>>
	                      <bc:message key='login_jsp.language.en_US'/>
	                    </option>
	                    <option value="zh_CN" <% if (language != null && language.equals("zh_CN")) out.println("selected");%>>
	                      <bc:message key='login_jsp.language.zh_CN'/>
	                    </option>
	                  </select>
	                </td></tr>
					<tr>
						<td align="center" colspan="2"><input type="submit"
							value="<bc:message key='login_jsp.btnLogin'/>" class="button" />&nbsp;&nbsp;
						<input type="reset" value="<bc:message key='login_jsp.btnReset'/>"
							class="button" onclick="focusUserName();" /></td>
					</tr>
				</table>
				</form>
				</td>
			</tr>
		</table>
		</td>
	</tr>
	<tr height="27">
		<td>
		<div id="detail" style="z-index: 3; position: absolute;float: left"></div>
		<table height="27" cellSpacing="0" cellPadding="0" width="100%"
			border="0">
			<tr>
				<td vAlign="top" align="left" background="../images/BottomBg.jpg">
				<table height="100%" cellSpacing="0" cellPadding="0" width="100%"
					border="0">
					<tr>
						<td nowrap="nowrap" align="center" valign="middle" height="27">
						<font style="font-size:9pt">Copyright &reg; 2011 Primeton
						.All Rights Reserved.</font></td>
					</tr>
				</table>
				</td>
			</tr>
		</table>
		</td>
	</tr>
</table>

<div id="tip12"
	style="display:none;position:absolute;top:350px;left:760px;width:200px;background:#E4F2FD;">
<b class="f1"></b><b class="f2"></b><b class="f3"></b>
<div class="divBox"><bc:message key='login_jsp.question' /></div>
<b class="f3"></b><b class="f2"></b><b class="f1"></b></div>
</body>
</html>
