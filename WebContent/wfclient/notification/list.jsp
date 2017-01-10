<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<table border="0" class="EOS_table" width="100%">
     <tr class="EOS_table_head" align="center">
        <th height="25" width="5%" nowrap="nowrap"><bc:message key='notification.Operation'/> </th>
        <th height="25" width="5%" nowrap="nowrap"><bc:message key='notification.notificationID'/></th>
        <th height="25" width="5%" nowrap="nowrap"><bc:message key='notification.title'/></th>
        <th height="25" width="10%" nowrap="nowrap"><bc:message key='notification.message'/></th>
        <th height="25" width="10%" nowrap="nowrap"><bc:message key='notification.sender'/></th>
        <th height="25" width="5%" nowrap="nowrap"><bc:message key='notification.workItemID'/></th>
        <th height="25" width="5%" nowrap="nowrap"><bc:message key='notification.createTime'/></th>
        <th height="25" width="5%" nowrap="nowrap"><bc:message key='notification.remindTime'/></th>
        <th height="25" width="5%" nowrap="nowrap"><bc:message key='notification.finalTime'/></th>
        <th height="25" width="5%" nowrap="nowrap"><bc:message key='notification.timeOutFlag'/></th>
     </tr>
     <%int flag = 0; 
	WFNotificationInst inst = null;
	%>
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
	<c:forEach items="${notifications}" var="notification">
	      <tr class="EOS_table_row" onmouseover="this.className='EOS_table_selectRow'" onmouseout="this.className='EOS_table_row'">
	          	<td style="text-align:center;" nowrap>
	          	<%
	inst = (WFNotificationInst)pageContext.getAttribute("notification");
	%>
	<%
		if(!isViewedNotification(inst)){
			%>
		&nbsp;
		<a class="link" href='javascript:doExecute("${notification.notificationID}");'><bc:message key='notification.confirm'/></a>
		<%
		}
	%>
          	&nbsp;<a class="link" href="javascript:showDetail('${notification.notificationID}', '${notification.actionURL}');"><bc:message key='notification.view'/></a></td>
        	<td nowrap>${notification.notificationID}</td>
        	<td nowrap>${notification.title}</td>
        	<td nowrap>${notification.message}</td>
        	<td nowrap>${notification.sender}</td>
        	<td nowrap>${notification.workItemID}</td>
        	<td nowrap>
        		<%
				if(null != inst){
					String createTimeStr = inst.getCreateTime();
					out.println(parseDescriptionDate(createTimeStr));
				}
				%>
        	</td>
        	<td nowrap>
        	<%
			if(null != inst){
				String remindTimeStr = inst.getRemindTime();
				out.println(parseDescriptionDate(remindTimeStr));
			}
			%>
        	</td>
        	<td nowrap>
        	<%
			if(null != inst){
				String finalTimeStr = inst.getFinalTime();
				out.println(parseDescriptionDate(finalTimeStr));
			}
			%>
        	</td>
        	<td nowrap>
        		<c:if test="${notification.timeOutFlag == 'Y'}"><bc:message key='notification.yes'/></c:if>
        		<c:if test="${notification.timeOutFlag == 'N'}"><bc:message key='notification.no'/></c:if>
        	</td>
      </tr>
      <%flag++; %>						
	</c:forEach>

               <%for(int i = flag;i <10;i++ ){ %>
     	<tr class="EOS_table_row">
     		<td>&nbsp;</td>
     		<td>&nbsp;</td>
     		<td>&nbsp;</td>
     		<td>&nbsp;</td>
     		<td>&nbsp;</td>
     		<td>&nbsp;</td>
     		<td>&nbsp;</td>
     		<td>&nbsp;</td>
     		<td>&nbsp;</td>
     		<td>&nbsp;</td>
     	</tr>
     <%} %>
</table>