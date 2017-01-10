<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.bps.client.common.IPageNavigation"%>
<%@ page import="com.bps.client.common.Constants"%>
<%@page import="com.eos.workflow.data.WFNotificationInst"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.ParseException"%>
<%@page import="java.util.Date"%>
<%@page import="com.bps.client.common.i18n.ResourceMessageUtil"%>
<%@ include file="/wfclient/common/common.jsp"%>
<html>
<head>
<title><bc:message key='view.WorkItemD'/></title>
<%!
  	private static String parseDescriptionDate(String timeString){
  		try{
      		SimpleDateFormat sdf = new SimpleDateFormat();
			sdf.applyPattern("yyyyMMddHHmmss");
			if(null == timeString){
				return "";
			}
			Date dt = sdf.parse(timeString);
			sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			return sdf.format(dt);
  		}catch(ParseException e){
  			return "";
  		}
  	}
	public static boolean isViewedNotification(WFNotificationInst notification){
		if(null != notification && WFNotificationInst.State.VIEWED.name().equals(notification.getState())){
			return true;
		}
		return false;
	}
		 	%>
<%
WFNotificationInst inst = (WFNotificationInst)request.getAttribute("notification");
  %>
<script type="text/javascript">

//*** 执行 ***//
function doExecute(id){
	var form1 =document.getElementById('form1');
	form1.action="<%=request.getContextPath() + IPageNavigation.INSTANCE.getPageURL(Constants.NOTIFICATION_FUNCTION, "confirmNotification.do")%>";
    form1.notificationID.value=id;
	form1.submit();
}

//*** 返回 ***//
function doBack(){
	var form1 =document.getElementById('form1');
	<%
	String action = "";
		if(isViewedNotification(inst)){
			action = request.getContextPath() + IPageNavigation.INSTANCE.getPageURL(Constants.NOTIFICATION_FUNCTION, "viewedNotificationList.do");
			
		}else{
			action = request.getContextPath() + IPageNavigation.INSTANCE.getPageURL(Constants.NOTIFICATION_FUNCTION, "unViewNotificationList.do");
		}
	%>
	form1.action="<%=action%>";
	form1.target='_self';
	form1.submit();
}
</script>
</head>
<body style="repeat-x;margin-top:0px;margin-left:0px; margin-right:0x;margin-buttom:0px">
	<table border="0" cellpadding="0" cellspacing="0" class="workarea" width="100%">
	<tr>
		<td class="workarea_title">
			<bc:message key='myTask.client'/> &gt; ${showTitle} &gt; <h3><bc:message key='notification.showNotificationDetail'/></h3>
		</td>
	</tr>
	<tr>
		<td>
		<table width="100%" border="0" cellspacing="0" cellpadding="0"
			class="EOS_panel_body">
			<tr>
				<td class="EOS_table_row" width="40%" valign="top">
				<form action="#" name="form1" id="form1" method="post">
					<input type="hidden" id="notificationID" name="notificationID">
					<table class="workarea" width="100%" height="100%">
						<tr>
							<td>
							<table width="100%" border="0" cellspacing="0" class="form_table"
								cellpadding="0" height="100%">
								<tr class="tableHeadTR" align="center">
									<td colspan="2" class="EOS_panel_head"><bc:message key='notification.showNotificationDetail'/></td>
								</tr>
								<tr>
									<td class="EOS_table_row"><bc:message key='notification.notificationID'/>:</td>
									<td>${notification.notificationID}</td>
								</tr>
								<tr>
									<td class="EOS_table_row"><bc:message key='notification.title'/>:</td>
									<td>${notification.title}</td>
								</tr>
								<tr>
									<td class="EOS_table_row" width="25%"><bc:message key='notification.message'/>:</td>
									<td>${notification.message}</td>
								</tr>
								<tr>
									<td class="EOS_table_row"><bc:message key='notification.sender'/>:</td>
									<td>${notification.sender}</td>
								</tr>
								<tr>
									<td class="EOS_table_row" width="15%"><bc:message key='notification.state'/>:</td>
									<td>
									<%
										if(isViewedNotification(inst)){
											out.print(ResourceMessageUtil.getResourceMessage("notification.viewed", request));
										}else{
											out.print(ResourceMessageUtil.getResourceMessage("notification.unView", request));
										}
									%>
									</td>
								</tr>
								<tr>
									<td class="EOS_table_row"><bc:message key='notification.createTime'/>:</td>
									<td>
										<%
							      		if(null != inst){
							      			String createTimeStr = inst.getCreateTime();
							      			out.println(parseDescriptionDate(createTimeStr));
							      		}
								      	%>
									</td>
								</tr>
								<tr>
									<td class="EOS_table_row"><bc:message key='notification.confirmTime'/>:</td>
									<td>
									 <%
						      		if(null != inst){
						      			String confirmTime = inst.getConfirmTime();
						      			out.println(parseDescriptionDate(confirmTime));
						      		}
							      	 %>
									</td>
								</tr>
								<tr>
									<td class="EOS_table_row"><bc:message key='notification.remindTime'/>:</td>
									<td>
									 <%
						      		if(null != inst){
						      			String remindTimeStr = inst.getRemindTime();
						      			out.println(parseDescriptionDate(remindTimeStr));
						      		}
							      	 %>
									</td>
								</tr>
								<tr>
									<td class="EOS_table_row"><bc:message key='notification.timeOutFlag'/>:</td>									
									<td nowrap>
			                      		<c:if test="${notification.timeOutFlag == 'Y'}"><bc:message key='notification.yes'/></c:if>
			                      		<c:if test="${notification.timeOutFlag == 'N'}"><bc:message key='notification.no'/></c:if>
			                      	</td>
								</tr>
								<tr>
									<td class="EOS_table_row"><bc:message key='notification.actionURL'/>:</td>
									<td>${notification.actionURL}</td>
								</tr>
								<tr>
									<td class="EOS_table_row"><bc:message key='notification.procInstName'/>:</td>
									<td style="table-layout:fixed;word-wrap: break-word">
									${notification.procInstName}
									</td>
								</tr>
								<tr>
									<td class="EOS_table_row"><bc:message key='notification.actInstName'/>:</td>
									<td>${notification.actInstName}</td>
								</tr>
								<tr>
									<td class="EOS_table_row"><bc:message key='notification.workItemName'/>:</td>
									<td>${notification.workItemName}</td>
								</tr>
							</table>
							</td>
						</tr>
						<tr>
							<td align="center" class="form_bottom">
								<%
									if(!isViewedNotification(inst)){
										%>
										<input type="button" name="btSubmit" value="<bc:message key='notification.confirm'/>" class="button" onclick='doExecute(${notification.notificationID});'>
										<%
									}
								%>
								<input type="button" name="btReturn" value="<bc:message key='query.Back'/>" class="button" onclick='doBack();'>
							</td>
						</tr>
					</table>
				</form>
				</td>
			</tr>
		</table>
		</td>
	</tr>
</table>
</body>
</html>
