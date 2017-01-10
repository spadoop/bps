<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file = "/wfclient/common/common.jsp"%>
<%@page import="com.bps.client.common.Constants"%>
<%@page import="com.bps.client.common.IPageNavigation"%>
<%@page import="com.bps.client.tag.help.AgentParticipantResourceTreeLoader"%>
<%@page import="com.bps.client.tag.help.ProcessStaticTreeNodeLoader"%>
<%@page import="com.bps.client.common.i18n.ResourceMessageUtil"%>
<html>
	<head>
		<title>Title</title>
	</head>
    <body  style="background:#FFFFFF;repeat-x;margin-top:10px;margin-left:12px;margin-buttom:0px;">
	<table border="0" cellpadding="0" cellspacing="0" class="workarea" width="100%" height="100%">
 		 <tr>
   			<td width="100%" valign="top">
<form  method="post" name="fullform">
<input type="hidden" id="methodActionID" name="method" />
<table border="0" cellpadding="0" cellspacing="0" class="workarea" width="100%">
  <tr>
    <td class="workarea_title" valign="middle"><bc:message key='set.SetAgent'/> &gt; <h3><bc:message key='set.AddFAgent'/></h3></td>
  </tr>
  <tr>
    <td width="100%" valign="top">
      <table cellpadding="0" width="100%" cellspacing="0" border="0">
        <tr>
          <td>
            <table border="0" class="EOS_panel_body" width="100%">
              <tr>
                <td class="EOS_panel_head"><bc:message key='set.SetARealation'/></td>
              </tr>
              <tr>
                <td width="100%">
                 
                          <table width="100%" border="0" cellpadding="0" cellspacing="0" class="form_table">
                            <tr>
                              <td  nowrap class="EOS_table_row"><bc:message key='query.Principal'/>:</td>
                              <td  nowrap>
                                ${AgentFromName}
                                <input type="hidden" name="AgentFromName" value="${AgentFromName}"/>
                                <input type="hidden" name="AgentFromID" value="${AgentFromID}"/>
                              </td>
                              <td nowrap class="EOS_table_row"><bc:message key='query.AgentApproach'/>:</td>
                              <td nowrap><bc:message key='query.FullAgency'/>
                              	<input type="hidden" name="AgentType" value="ALL"/>
                              </td>
                            </tr>
                            <tr>
                              <td class="EOS_table_row"><bc:message key='query.Agents'/>:</td>
                              <td>
                              <% String choose = ResourceMessageUtil.getResourceMessage("Common.Choose", session); %>
                              <% String Participant = ResourceMessageUtil.getResourceMessage("myTask.Participant", session); %>
                              	<bc:selector name="AgentToName" loader="<%=AgentParticipantResourceTreeLoader.class.getName()%>" 
							      	loaderParam="${AgentFromID}" resourceTitle="<%=Participant%>" buttonValue="<%=choose%>"/>                              	
                              	<font style="color:red">*</font></td>
                              <td class="EOS_table_row"><bc:message key='query.EffTime'/>:</td>
                              <td>
                              	<bc:calendar name="StartTime" pattern="yyyy-MM-dd HH:mm:ss"/>
                              	<font style="color:red">*</font></td>
                            </tr>
							<tr>
                              <td class="EOS_table_row"></td>
                              <td></td>
                              <td class="EOS_table_row"><bc:message key='query.EndTime'/>:</td>
                              <td>
                              	<bc:calendar name="EndTime" pattern="yyyy-MM-dd HH:mm:ss"/>
                                <font style="color:red">*</font></td>
                            </tr>
							<tr>							 
							  <td class="EOS_table_row"><bc:message key='set.AddExcProcess'/>:</td>
                              <td>							      
                               <% String Process = ResourceMessageUtil.getResourceMessage("query.Process", session); %>
							      <bc:selector name="processAndActivitys" useText="false" maxCount="256" 
							      	loader="<%=ProcessStaticTreeNodeLoader.class.getName()%>" resourceTitle="<%=Process%>" buttonValue="<%=choose%>"/> 							      
							  </td>
                              <td class="EOS_table_row"><bc:message key='query.AgentReason'/>:</td>
                              <td>
                                 <input type="textarea" style="width: 206px;height: 96px"  name="AgentReason" />
						     	 <font style="color:red">*</font></td>
							</tr>
							<tr>
                              <td colspan="4" class="form_bottom">
                                 <input  type="button" id="submitButtonID" value="<bc:message key='myTask.OK'/>" class="button" onclick="doCreatFullAgent();">
                                 <input  type="button" id="cancelButtonID" value="<bc:message key='myTask.Cancel'/>" class="button" onClick="doBack();">
                               </td>
                            </tr>
                          </table>
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
</td>
  </tr>
</table>
</body>
</html>
<script  language="JavaScript"  type="text/javascript">
<!--  
   
	var formAction = '<%=request.getContextPath() + IPageNavigation.INSTANCE.getPageURL(Constants.AGENT_FUNCTION, "agent.do") %>';
	
    function checkForm(){
    	var agtoNamesObj = document.forms['fullform'].elements['AgentToName.names'];
        var agtoname=document.forms['fullform'].elements['AgentToName.ids'].value;
        var stime=document.forms['fullform'].elements['StartTime'].value;
        var etime=document.forms['fullform'].elements['EndTime'].value;
        var agrea=document.forms['fullform'].elements['AgentReason'].value;
        
        if(trimString(agtoname)==""){
			alertValidateMessage("<bc:message key='set.SelAAlert'/>", agtoNamesObj);
			return false;
		}
		if(stime==""){
			alertValidateMessage("<bc:message key='set.SelEffTimeAlert'/>", document.forms['fullform'].elements['StartTime']);
			return false;
		}
		if(etime==""){
			alertValidateMessage("<bc:message key='set.SelEndTimeAlert'/>", document.forms['fullform'].elements['EndTime']);
			return false;
		}
		if(trimString(agrea)==""){
			alertValidateMessage("<bc:message key='set.FillAgentRAlert'/>", document.forms['fullform'].elements['AgentReason']);
			return false;
		}
		
		var agfromid=document.forms['fullform'].elements['AgentFromID'].value;
		var agfromtc='${leafType}';
		var agToIdsArray = agtoname.split(",");
		var agToTypeCodesArray = document.forms['fullform'].elements['AgentToName.typeCodes'].value.split(",");
	    for(var i=0;i<=agToIdsArray.length;i++){
	        if(agToIdsArray[i] == agfromid && agToTypeCodesArray[i] == agfromtc){
	           alertValidateMessage("<bc:message key='set.NotAgentsAlert'/>", agtoNamesObj);
			   return false;
	        }
	    }
		
		if(stime>=etime){//以后要改为>=
			alertValidateMessage("<bc:message key='set.EffTimeAndEND'/>", document.forms['fullform'].elements['StartTime']);
			return false;
		}
		
		if(trimString(agrea).length > 120){
			alertValidateMessage("<bc:message key='set.AgentLength'/>", document.forms['fullform'].elements['AgentReason']);
			return false;
		}
		
		return true;
    }

	function doCreatFullAgent(){
	    if(!checkForm()) {
	    	document.forms['fullform'].elements["submitButtonID"].disabled = false;
	    	return false;
	    }
	    document.forms['fullform'].action = formAction;
	    document.forms['fullform'].elements["methodActionID"].value="addAgent";	   
	    document.forms['fullform'].submit();
	}
	
	function doBack(){
       document.forms['fullform'].action = formAction;
	    document.forms['fullform'].elements["methodActionID"].value="querySetAgentInfoList";	  
	    document.forms['fullform'].submit();
    }
	
//-->
</script>
