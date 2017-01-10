<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file = "/wfclient/common/common.jsp"%>
<%@page import="com.bps.client.common.Constants"%>
<%@page import="com.bps.client.common.IPageNavigation"%>
<%@page import="com.bps.client.tag.help.AgentParticipantResourceTreeLoader"%>
<%@page import="com.bps.client.tag.help.ProcessAndActivityStaticTreeNodeLoader"%>
<%@page import="com.bps.client.common.i18n.ResourceMessageUtil"%>
<html>
	<head>
		<title>Title</title>
	</head>
    <body  style="background:#FFFFFF;repeat-x;margin-top:10px;margin-left:12px;margin-buttom:0px;">
	<table border="0" cellpadding="0" cellspacing="0" class="workarea" width="100%" height="100%">
 		 <tr>
   			<td width="100%" valign="top">
<form action="" method="post" name="partform">
<input type="hidden" id="methodActionID" name="method" />
<table border="0" cellpadding="0" cellspacing="0" class="workarea" width="100%">
  <tr>
    <td class="workarea_title" valign="middle"><bc:message key='set.SetAgent'/> &gt; <h3><bc:message key='set.UpdatePartAgent'/></h3></td>
  </tr>
  <tr>
    <td width="100%" valign="top">
      <table cellpadding="0" width="100%" cellspacing="0" border="0">
        <tr>
          <td>
            <table border="0" class="EOS_panel_body" width="100%">
              <tr>
                <td class="EOS_panel_head"><bc:message key='set.UpdateAgentRealation'/></td>
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
                                <input type="hidden" name="AgentID" value="${AgentID}"/>
							  </td>
                              <td nowrap class="EOS_table_row"><bc:message key='query.AgentApproach'/>:</td>
                              <td nowrap><bc:message key='query.PartAgency'/>
                              	<input type="hidden" name="AgentType" value="PART"/>
                              </td>
                            </tr>
                            <tr>
                              <td class="EOS_table_row"><bc:message key='query.Agents'/>:</td>
                              <td>
                              <%String choose = ResourceMessageUtil.getResourceMessage("Common.Choose", session); %>  
    							<%String participant = ResourceMessageUtil.getResourceMessage("myTask.Participant", session); %> 
									<bc:selector name="AgentToName" value="AgentToNodes" loader="<%=AgentParticipantResourceTreeLoader.class.getName()%>" 
							      		buttonValue="<%=choose%>"loaderParam="${AgentFromID}" resourceTitle="<%=participant%>"/> 
                              		<font style="color:red">*</font></td>
                              <td class="EOS_table_row"><bc:message key='query.EffTime'/>:</td>
                              <td>
                              	<bc:calendar name="StartTime" pattern="yyyy-MM-dd HH:mm:ss" value="${AgentDetail.startTime}"/>
                                <font style="color:red">*</font></td>
                            </tr>
							<tr>
                              <td class="EOS_table_row"></td>
                              <td></td>
                              <td class="EOS_table_row"><bc:message key='query.EndTime'/>:</td>
                              <td>
							    <bc:calendar name="EndTime" pattern="yyyy-MM-dd HH:mm:ss" value="${AgentDetail.endTime}"/>
                                <font style="color:red">*</font></td>
                            </tr>
							<tr>
							  <td class="EOS_table_row"><bc:message key='set.Aproactivities'/>:</td>
							  <td>
							  <%String process = ResourceMessageUtil.getResourceMessage("query.Process", session); %>
							     <bc:selector name="processAndActivitys" useText="false" maxCount="256" value="processAndActivityNodes" 
							      	buttonValue="<%=choose%>" loader="<%=ProcessAndActivityStaticTreeNodeLoader.class.getName()%>" resourceTitle="<%=process%>"/> 
							      	
							     <input  type="button" id="ButtonSet" value="<bc:message key='set.ModifyAuthority'/>" class="button" onclick="setProcessAndActivityAuth();"> 	
							     <font style="color:red">*</font>
							  </td>
                              <td class="EOS_table_row"><bc:message key='query.AgentReason'/>:</td>
                              <td>
                                 <input type="textarea" style="width: 206px;height: 96px"  name="AgentReason" value="${AgentDetail.agentReason}"/>
						         <font style="color:red">*</font></td>
							</tr>
							<tr id="setProcessAndActivityAuthID" style="display:none;">
                              <td colspan="4">
                              		<table border="0" class="EOS_panel_body" width="100%">
						              <tr>
						                <td class="EOS_panel_head" valign="middle"><bc:message key='set.ModifyAgeActAuthority'/></td>
						              </tr>
						              <tr>
						                <td width="100%"  height="440"  valign="top">
						                  <table id="processAndActivietyTableID" border="0" class="EOS_table" width="100%">
								                    <tr class="EOS_table_head">
													  <th nowrap><bc:message key='query.ProAcDef'/></th>
													  <th nowrap><bc:message key='query.Type'/></th>
								                      <th nowrap><bc:message key='query.ProAcName'/></th>
								                      <th nowrap><bc:message key='query.ProOpeAuthority'/></th>
								                      <th nowrap><bc:message key='query.ProAct'/></th>
								                    </tr>
								           </table>
								        </td>
								       </tr>
								     </table>  
                              </td>
                            </tr>
							<tr>
                              <td colspan="4" class="form_bottom">
                                 <input  type="button" name="Button1" value="<bc:message key='myTask.OK'/>" class="button" onclick="modifyPartAgent();">
                                 <input  type="button" name="Button2" value="<bc:message key='myTask.Cancel'/>" class="button" onClick="doBack();">
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
<script>
<!--  

	var formAction = '<%=request.getContextPath() + IPageNavigation.INSTANCE.getPageURL(Constants.AGENT_FUNCTION, "agent.do") %>';
	var isModify = true;
    function checkForm(){
        var agtoname=document.forms['partform'].elements['AgentToName.ids'].value;
        var stime=document.forms['partform'].elements['StartTime'].value;
        var etime=document.forms['partform'].elements['EndTime'].value;
        var agrea=document.forms['partform'].elements['AgentReason'].value;
        var lenProcess=document.forms['partform'].processAndActivitys.length;
        
        if(trimString(agtoname)==""){
			alertValidateMessage("<bc:message key='set.SelAAlert'/>", document.forms['partform'].elements['AgentToName.ids']);
			return false;
		}
		if(stime==""){
			alertValidateMessage("<bc:message key='set.SelEffTimeAlert'/>", document.forms['partform'].elements['StartTime']);
			return false;
		}
		if(etime==""){
			alertValidateMessage("<bc:message key='set.SelEndTimeAlert'/>", document.forms['partform'].elements['EndTime']);
			return false;
		}
		if(lenProcess==0){
			alertValidateMessage("<bc:message key='set.SeleAPANDA'/>", document.forms['partform'].processAndActivitys);
			return false;
		}
		
		if(getElementObjectByName("operationAuth") == null){
			alertValidateMessage("<bc:message key='set.SeleProOAuthority'/>");
			return false;
		}
		if(trimString(agrea)==""){
			alertValidateMessage("<bc:message key='set.FillAgentRAlert'/>", document.forms['partform'].elements['AgentReason']);
			return false;
		}
		
		var agfromid=document.forms['partform'].elements['AgentFromID'].value;
		var agfromtc='${leafType}';
		var agToIdsArray = agtoname.split(",");
		var agToTypeCodesArray = document.forms['partform'].elements['AgentToName.typeCodes'].value.split(",");
	    for(var i=0;i<=agToIdsArray.length;i++){
	        if(agToIdsArray[i] == agfromid && agToTypeCodesArray[i] == agfromtc){
	           alertValidateMessage("<bc:message key='set.NotAgentsAlert'/>.", document.forms['partform'].elements['AgentToName.ids']);
			   return false;
	        }
	    }
		
		if(stime>=etime){
			alertValidateMessage("<bc:message key='set.EffTimeAndEND'/>", document.forms['partform'].elements['StartTime']);
			return false;
		}
		
		if(trimString(agrea).length > 120){
			alertValidateMessage("<bc:message key='set.AgentLength'/>", document.forms['partform'].elements['AgentReason']);
			return false;
		}
		
		return true;
    }
    
	function modifyPartAgent(){
		if(isModify){
			alert("<bc:message key='set.ModifyAuthorityButton'/>");
			return false;
		}
	    if(!checkForm()) 
	          return false;
	    document.forms['partform'].action = formAction;
	    document.forms['partform'].elements["methodActionID"].value="modifyAgent";	
	    var legth = document.forms['partform'].processAndActivitys.options.length; 
	    for(var i=0;i<legth;++i) {
	    	document.forms['partform'].processAndActivitys.options[i].selected = "true" ;
	    }
	    document.forms['partform'].submit();
	}
	
	function doBack(){
        document.forms['partform'].action = formAction;
	    document.forms['partform'].elements["methodActionID"].value="querySetAgentInfoList";	
	    document.forms['partform'].submit();
    } 
    
    var accssTypesArray = new Array(''
     <c:forEach var="accessType" items="${AgentAccessTypes}">
    	,'${accessType}'
     </c:forEach>
    );   
	
    var selectProcessOperationAuthString = [
				'<select name="operationAuth">',
				'    <option value="ALL" selected><bc:message key='myTask.all'/>',
				'    <option value="STARTPROC"><bc:message key='query.StartProcess'/>',
				'    <option value="EXEWI"><bc:message key='myTask.WorkExecu'/>',
				'</select>'
				].join('\n');
				
	var selectActivityOperationAuthString = [
				'<input type="hidden" name="operationAuth"><bc:message key='Common.Nil'/>'
				].join('\n');
				
	var initPage = true;
				
	//显示流程操作权限设置
    function setProcessAndActivityAuth() {
		if(!initPage){
			isModify = false;
		}
    	var selectProcessObj = getElementObjectByName("processAndActivitys");
    	if (selectProcessObj.options.length == 0) {
    		document.getElementById("setProcessAndActivityAuthID").style.display = "none";
    		alertValidateMessage("<bc:message key='set.SeleAPANDA'/>");
    		return;
    	}
    	
    	document.getElementById("setProcessAndActivityAuthID").style.display = "";
    	var processTable = document.getElementById("processAndActivietyTableID");
    	for (var i = processTable.rows.length - 1; i > 0; i--) {
    		processTable.deleteRow(i);
    	}
    	
    	for (var i = 0; i < selectProcessObj.options.length; i++) {
    		var values = selectProcessObj.options[i].value.split(",");
    		var newRow = processTable.insertRow(-1);
    		newRow.className = "EOS_table_row";
    		var newCell0 = newRow.insertCell(-1);
    		newCell0.innerHTML = values[1];//流程/活动定义ID
    		if (values[0] == "PROCESS") {
    			newCell0.innerHTML = values[1];//流程定义ID
    		} else {
    			newCell0.innerHTML = values[1].split("$")[0];//活动定义ID
    		}
    		
    		var newCell1 = newRow.insertCell(-1);
    		//类型
    		if(values[0] == "PROCESS"){
    			newCell1.innerHTML = "<bc:message key='query.Process'/>";
    		}else{
    			newCell1.innerHTML = "<bc:message key='query.Activity'/>";//类型
    		}
    		
    		var newCell2 = newRow.insertCell(-1);
    		newCell2.innerHTML = selectProcessObj.options[i].text;//名称
    		
    		var newCell3 = newRow.insertCell(-1);
    		if (values[0] == "PROCESS") {
    			if (initPage) {
    				newCell3.innerHTML = getSelectProcessOperationAuthString(accssTypesArray[i + 1]);//操作权限
    			} else {
    				newCell3.innerHTML = selectProcessOperationAuthString;//操作权限
    			}    			
    		} else {
    			newCell3.innerHTML = selectActivityOperationAuthString;
    		}
    		var newCell4 = newRow.insertCell(-1);
    		if (values[0] == "PROCESS") {
    			newCell4.innerHTML = "<bc:message key='Common.Nil'/>";//活动所属流程定义ID
    		} else {
    			newCell4.innerHTML = values[1].split("$")[1];
    		}
    	}
    	initPage = false;
    }
    
    function getSelectProcessOperationAuthString(accessType) {
    	var authString = '<select name="operationAuth">';
		if (accessType == "ALL") {
			authString = authString + '<option value="ALL" selected><bc:message key='myTask.all'/>';
		} else {
			authString = authString + '<option value="ALL"><bc:message key='myTask.all'/>';
		}
		if (accessType == "STARTPROC") {
			authString = authString + '<option value="STARTPROC" selected><bc:message key='query.StartProcess'/>';
		} else {
			authString = authString + '<option value="STARTPROC"><bc:message key='query.StartProcess'/>';
		}
		if (accessType == "EXEWI") {
			authString = authString + '<option value="EXEWI" selected><bc:message key='myTask.WorkExecu'/>';
		} else {
			authString = authString + '<option value="EXEWI"><bc:message key='myTask.WorkExecu'/>';
		}
		authString = authString + '</select>';
		return authString;
    }
    
    window.onload = function() {
    	setProcessAndActivityAuth();
    };
	
//-->
</script>