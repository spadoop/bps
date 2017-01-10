<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.bps.client.common.IPageNavigation"%>
<%@ page import="com.bps.client.common.Constants"%>
<%@page import="com.eos.workflow.data.WFNotificationInst"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.ParseException"%>
<%@page import="java.util.Date"%>
<%@page import="com.primeton.workflow.model.consts.RunTimeConst"%>
<%@ include file="/wfclient/common/common.jsp"%>

<html>
<head>
<title><bc:message key='notification.unViewNotification'/></title>
<script src="<%=request.getContextPath()%>/wfclient/js/notification.js"></script>
<script type="text/javascript">

//*** 执行 ***// 
function doExecute(id){
	var form1 = document.getElementById('form1');
	form1.action="<%=request.getContextPath() + IPageNavigation.INSTANCE.getPageURL(Constants.NOTIFICATION_FUNCTION, "confirmNotification.do")%>";
	form1.notificationID.value=id;
	form1.submit();
}

//*** 查看 ***//
function showDetail(id, url){
	var form1 = document.getElementById('form1');
	form1.action="<%=request.getContextPath() + IPageNavigation.INSTANCE.getPageURL(Constants.NOTIFICATION_FUNCTION, "showNotificationDetail.do")%>";
	if(url){
		form1.action=url;
	}
    form1.notificationID.value=id;
	form1.submit();
}
function query(){
	if(!validate()){
		return;
	}
	var form1 = document.getElementById('form1');
	form1.action="<%=request.getContextPath() + IPageNavigation.INSTANCE.getPageURL(Constants.NOTIFICATION_FUNCTION, "queryUnviewNotification.do")%>";
	form1.submit();
}
</script>
</head>
<body style="repeat-x;margin-top:0px;margin-left:0px; margin-right:0px;margin-buttom:0px;width:100%" >
<form action="#" name="form1" id="form1" method="post">
<table border="0" cellpadding="0" cellspacing="0" class="workarea" width="100%">
  <tr>
    <td class="workarea_title"><bc:message key='myTask.client'/> &gt;
        <h3><bc:message key='notification.unViewNotification'/></h3>
    </td>
  </tr>
  <tr>
    <td>
    
      <%@include file="/wfclient/notification/queryForm.jsp" %>
      
      <table border="0" class="EOS_panel_body" width="100%">
        <tr>
          <td class="EOS_panel_head"><bc:message key='notification.notificationList'/> </td>
        </tr>
        <tr>
          <td>
                      
            <input type="hidden" id="notificationID" name="notificationID">
            <input type="hidden" name="showTitle" value="<bc:message key="notification.unViewNotification"/>" />
            <table border="0" cellpadding="0" cellspacing="0" width="100%">
              <tr>
                <td>
                
                  <%@include file="/wfclient/notification/list.jsp" %>
                  
                </td>
              </tr>
              <tr class="result_bottom">
                <td colspan="10" class="result_bottom" align="right">
                	<c:if test="${flag eq 'page'}">              		
						<c:set var="action" value='<%=request.getContextPath() + IPageNavigation.INSTANCE.getPageURL(Constants.NOTIFICATION_FUNCTION, "unViewNotificationList.do")%>'/>
					</c:if>		
                	<c:if test="${flag eq 'query'}">
						<c:set var="action" value='<%=request.getContextPath() + IPageNavigation.INSTANCE.getPageURL(Constants.NOTIFICATION_FUNCTION, "queryUnviewNotification.do")%>'/>
					</c:if>
					<%@ include file="/wfclient/common/pagination.jsp"%>
                </td>
              </tr>
          </table>
          </td>
        </tr>
      </table>
    </td>
  </tr>
</table>
</form>
</body>
</html>
