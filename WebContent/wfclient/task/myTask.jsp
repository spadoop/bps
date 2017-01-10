<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.bps.client.common.IPageNavigation"%>
<%@ page import="com.bps.client.common.Constants"%>
<%@ include file="/wfclient/common/common.jsp"%>
<html>
<head>
<title><bc:message key='myTask.myTask'/></title>

<script>
window.onload = function() {
 	if (isIEBrowser()) {
		document.getElementById("tabHeightID").style.height="22";
		document.getElementById("tabULID").style.marginLeft="1px";	
	} else {
		document.getElementById("tabHeightID").style.height="14";
		document.getElementById("tabULID").style.marginLeft="11px";
	}	
};
//*** 初始选项卡 ***//
function setTab(cursel){
 	for(i=1;i<=2;i++){
	  	var menu=document.getElementById("tab"+i);		//menu = (one1)，(one2)，(one3)，(one4)
	  	//menu.className=i==cursel?"hover":"";
	  	menu.className=i==cursel?"active":"";
	}

	var url="";	  	  
	switch(cursel){
		case 1:url=url+"<%=request.getContextPath() + IPageNavigation.INSTANCE.getPageURL(Constants.TASK_FUNCTION, "tasklistforSelf.do")%>";break;
		case 2:url=url+"<%=request.getContextPath() + IPageNavigation.INSTANCE.getPageURL(Constants.TASK_FUNCTION, "taskConditionQueryforOthers.do")%>";
			document.getElementById("contentIFrame").contentWindow.query(url); // 特例！因为原代码有bug，不能正常显示分页
			return;
			break;
		case 3:url=url+"<%=request.getContextPath() + IPageNavigation.INSTANCE.getPageURL(Constants.TASK_FUNCTION, "tasklistfinishedforOthers.do")%>";break;
	    case 4:url=url+"<%=request.getContextPath() + IPageNavigation.INSTANCE.getPageURL(Constants.TASK_FUNCTION, "tasklistfinishedforSelf.do")%>";break;
	    case 5:url=url+"<%=request.getContextPath() + IPageNavigation.INSTANCE.getPageURL(Constants.NOTIFICATION_FUNCTION, "unViewNotificationList.do")%>";break;
	    case 6:url=url+"<%=request.getContextPath() + IPageNavigation.INSTANCE.getPageURL(Constants.NOTIFICATION_FUNCTION, "viewedNotificationList.do")%>";break;
	}
	document.getElementById("contentIFrame").src=url;	
}
</script>
</head>
<body>
	<div id="lib_Tab1">
		<table style="height:100%;width:100%">
			<tr>
				<td class="">	
				<!-- <td class="lib_Menubox lib_tabborder"> -->					
						<ul id="tabULID" class="nav nav-pills">
							<li id="tab1" class="active" ><a href="javascript:setTab(1);"><bc:message key='myTask.pending'/></a></li>
							<li id="tab2" ><a href="javascript:setTab(2);"><bc:message key='myTask.handled'/></a></li>
							<!-- li id="tab3" onclick="setTab(3)"><bc:message key='myTask.entrusted'/></li>
							<li id="tab4" onclick="setTab(4)"><bc:message key='myTask.finished'/></li>
							<li id="tab5" onclick="setTab(5)"><bc:message key='notification.unViewNotification'/></li>
							<li id="tab6" onclick="setTab(6)"><bc:message key='notification.viewedNotification'/></li> -->
						</ul>
					
				</td>
			</tr>
			
			<tr>
				<td id="tabHeightID"></td>
			</tr>

			<tr>
				<td class="lib_Contentbox lib_tabborder">						
							<iframe id="contentIFrame" 
									src="<%=request.getContextPath() + IPageNavigation.INSTANCE.getPageURL(Constants.TASK_FUNCTION, "tasklistforSelf.do")%>"
									style="border:10px;overflow:auto;" frameborder="0" width="100%" height="720px"></iframe>
						
				</td>
			</tr>
		</table>
	</div>
</body>
</html>
