<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript">
	function checkNumber(obj){
		var value = obj.value;
		if(value){
			var reg = /^\d+$/;
			return reg.test(value);
		}
		return true;
	}
	function validate(){
		return checkNumber(get("notificatioinID_01"));
	}
</script>
 <table border="0" class="EOS_panel_body" width="100%">
 <tr>
   <td class="EOS_panel_head"><bc:message key='myTask.QConditions'/></td>
 </tr>
 <tr>
   <td width="100%">
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="form_table">
<tr align="left">
  <td width="15%" class="form_label"><bc:message key='notification.notificationID'/>:</td>
  <td align="left">
  	<c:if test="${notification.notificationID>0}">
  		<c:set var="notificationId" value="${notification.notificationID}"></c:set>
  	</c:if>
      <input type="text" id="notificatioinID_01" name="notification.notificationID" value="${notificationId}" onblur="checkNumber(this)" />
  </td>
  <td width="15%" class="form_label"><bc:message key='notification.title'/>:</td>
  <td align="left">
      <input type="text" name="notification.title" value="${notification.title}">
  </td>
</tr>
<tr align="left">
  <td width="15%" class="form_label"><bc:message key='notification.message'/>:</td>
  <td align="left">
      <input type="text" name="notification.message" value="${notification.message}">
  </td>
  <td width="15%" class="form_label"><bc:message key='notification.timeOutFlag'/>:</td>
  <td align="left">
  	<select name="notification.timeOutFlag">
  		<option value="">请选择</option>
  		<option value="<%=RunTimeConst.TIMEOUT_TRUE%>"><bc:message key="notification.yes" /></option>
  		<option value="<%=RunTimeConst.TIMEOUT_FALSE%>"><bc:message key="notification.no" /></option>
  	</select>
  </td>
</tr>
<tr align="left">
  <td colspan="4" align="center" class="form_bottom">
    <input type="button" class="button" value = "<bc:message key='myTask.query'/>" onclick="javascript:query();">
          </td>
        </tr>
      </table>
       </td>
  </tr>
</table>