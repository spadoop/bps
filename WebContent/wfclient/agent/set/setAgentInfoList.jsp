<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file = "/wfclient/common/common.jsp"%>
<%@page import="com.bps.client.common.Constants"%>
<%@page import="com.bps.client.common.IPageNavigation"%>
<html>
<head>
	<title>Title</title>
</head>

<body style="background:#FFFFFF;repeat-x;margin-top:10px;margin-left:12px;margin-buttom:0px;">
<table border="0" cellpadding="0" cellspacing="0" class="workarea" width="100%" height="100%">
	 <tr>
		<td width="100%" valign="top">
		<form action="" method="post" name="agentform">
		<input type="hidden" id="methodActionID" name="method" />
		<table border="0" cellpadding="0" cellspacing="0" class="workarea" width="100%">
		  <tr>
			<td class="workarea_title"><bc:message key='set.SetAgent'/> &gt; <h3><bc:message key='set.AgentInf'/></h3></td>
		  </tr>
		  <tr>
			<td width="100%" valign="top">
			  <table cellpadding="0" width="100%" cellspacing="0" border="0">
				<tr>
				  <td>
					<table border="0" class="EOS_panel_body" width="100%">
					  <tr>
						<td class="EOS_panel_head" valign="middle">【<bc:message key='set.AgentInf'/>】</td>
					  </tr>
					  <tr>
						<td width="100%" valign="top">
						  <table border="0" class="EOS_table" width="100%">
								<tr class="EOS_table_head">
									  <th width="5%" nowrap><input type="checkbox" id="AllCheckboxID" value="checkbox" onclick="SelectAllCheck(this);">&nbsp;<bc:message key='set.Choose'/></th>							 
										<th width="10%" nowrap><bc:message key='query.Agents'/></th>
										<th width="10%" nowrap><bc:message key='query.AgentType'/></th>
										<th width="10%" nowrap><bc:message key='query.AgentApproach'/></th>
										<th width="10%" nowrap><bc:message key='query.EffTime'/></th>
										<th width="10%" nowrap><bc:message key='query.EndTime'/></th>
										<th width="15%" nowrap><bc:message key='query.AgentStatus'/>		                      
										   <select id="usefulID" name="useful"onchange="doQuery();">
												<option value="ALL" ${useful == 'ALL' || empty (useful) ? 'selected' : ''}><bc:message key='myTask.all'/>
												<option value="Y" ${useful == 'Y' ? 'selected' : ''}><bc:message key='query.Effectiv'/>
												<option value="N" ${useful == 'N' ? 'selected' : ''}><bc:message key='query.NOTEffective'/>
											</select>
									  </th>
								</tr>
						   
							  <% 
								int flag =0;
							  %>
							 
						<c:forEach var="agentInfo" items="${agentList}">
							  <c:if test="${(useful == 'Y' && (agentInfo.startTime < CurrentTime && agentInfo.endTime > CurrentTime)) 
										|| (useful == 'N' && (agentInfo.startTime > CurrentTime || agentInfo.endTime < CurrentTime))
										|| (useful == 'ALL' || useful == null)}">
								<tr class="EOS_table_row" onmouseover="this.className='EOS_table_selectRow'" onmouseout="this.className='EOS_table_row'">
									  <td style="text-align:center;" nowrap="nowrap"> 
										  <input type="checkbox" name="agentIDs" value="${agentInfo.agentID}" onclick="doSelect(this);"/>&nbsp;
										  <a href='javascript:doQueryDetail(${agentInfo.agentID});'><bc:message key='myTask.View'/></a>
									  </td>
									  <td>		                         
										  <bc:displayParticipantName typeCode='${agentInfo.agentToType}' participantID="${agentInfo.agentTo}" />
									  </td> 
									  <td>		                          
										  <bc:displayTypeCode typeCode='${agentInfo.agentToType}' />
									  </td>
									  <td>
										  <c:if test="${agentInfo.agentType == 'ALL'}">
												<bc:message key='query.FullAgency'/>
										  </c:if>
										  <c:if test="${agentInfo.agentType == 'PART'}">
												<bc:message key='query.PartAgency'/>
										  </c:if>
										  
									  </td>
									  <td nowrap="nowrap">${agentInfo.startTime}</td>
									  <td nowrap="nowrap">${agentInfo.endTime}</td>
									  <td>
										  <c:if test="${agentInfo.startTime < CurrentTime && agentInfo.endTime > CurrentTime}">
												<bc:message key='query.Effective'/>
										  </c:if>										
										  <c:if test="${agentInfo.startTime > CurrentTime || agentInfo.endTime < CurrentTime}">											
												<bc:message key='query.NOTEffective'/>
										  </c:if>
									  </td>			
								 </tr>
								 <%
									flag++; 						 	
								 %>
							</c:if>
						 </c:forEach>
							  
								 <%
									if(flag<10){
									  for(int i = flag; i < 10;i++){ %>
								 <tr class="EOS_table_row">
									  <td></td>
									  <td></td>
									  <td></td>
									  <td></td>
									  <td></td>
									  <td></td>
									  <td></td>
								  </tr>
								<%    }
									} %>
							
						  </table>
						  <table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
							  <td align="left" >
								  <%if(flag !=0 ){ %>
									<input type="button" name="Button1" value="<bc:message key='set.Alter'/>" class="button" onclick="doModifyAgent();">
									<input type="button" name="Button2" value="<bc:message key='set.Delete'/>" class="button" onclick="doDeleteAgent();">
								  <%}else{ %>
									<input type="button" name="Button1" value="<bc:message key='set.Alter'/>" class="button" onclick="doModifyAgent();" disabled>
									<input type="button" name="Button2" value="<bc:message key='set.Delete'/>" class="button" onclick="doDeleteAgent();" disabled>
									<%} %>
								  <input type="button" name="Button3" value="<bc:message key='set.AddFullAgent'/>" class="button" onClick="doAddFullAgent();">
								  <input type="button" name="Button4" value="<bc:message key='set.AddIperAgent'/>" class="button" onClick="doAddPartAgent();">
							  </td>
							  <td colspan="10" class="result_bottom" align="right">
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
	 
	 //修改某代理关系
	 function doModifyAgent(){
	     if(checkAgentID()){
	       	 var el = document.getElementsByName('agentIDs');
	      	 var len = el.length;
	       	 var temp=0;
	         for(var i=0; i<len; i++){  
	           if(el[i].checked == true){
	              temp=temp+1;
	           }
	         }
	         if(temp==0){
			    alertValidateMessage("<bc:message key='set.SeleAgencyModified'/>");
			    return false;
		     }
		     if(temp>1){
			    alertValidateMessage("<bc:message key='set.ModifyOne'/>");
			    return false;
		     }
		     document.forms['agentform'].action = formAction;	 
		     document.forms['agentform'].elements["methodActionID"].value="displayModifyAgent";
	    	 document.forms['agentform'].submit();
		     
	     }else
	        return false;
	 } 
	 
	 //删除代理
	 function doDeleteAgent(){
	    if(checkAgentID()){
	         var el = document.getElementsByName('agentIDs');
	      	 var len = el.length;
	       	 var temp=0;
	         for(var i=0; i<len; i++){  
	           if(el[i].checked == true){
	              temp=temp+1;
	           }
	         }
	         if(temp==0){
			    alertValidateMessage("<bc:message key='set.SelDeleted'/>");
			    return false;
		     }
		     if(confirm('<bc:message key='set.SelDeletedOption'/>')){
			    	document.forms['agentform'].action = formAction;
			    	document.forms['agentform'].elements["methodActionID"].value="deleteAgent";
	    	        document.forms['agentform'].submit();
			 }else
				return false;
	    }else
	        return false;
	 }
	
     //添加完全代理
     function doAddFullAgent(){
        document.forms['agentform'].action = formAction + '?AgentType=ALL';
	    document.forms['agentform'].elements["methodActionID"].value="displayAddAgent";
	    document.forms['agentform'].submit();
     }
     
     //添加部分代理
     function doAddPartAgent(){
        document.forms['agentform'].action = formAction + '?AgentType=PART';
	    document.forms['agentform'].elements["methodActionID"].value="displayAddAgent";
	    document.forms['agentform'].submit();
     }
     
     //查询
     function doQuery(){
     	document.forms['agentform'].action = formAction;
	    document.forms['agentform'].elements["methodActionID"].value="querySetAgentInfoList";	    
        document.forms['agentform'].submit();
	 }
	 
	 //查询某代理详细信息
	 function doQueryDetail(agID){
	 	document.forms['agentform'].action = formAction + '?agentID='+agID;
	    document.forms['agentform'].elements["methodActionID"].value="queryAgentDetail";
	    document.forms['agentform'].submit();
	 }
	 
	 //检查agentID是否存在
	 function checkAgentID(){
	    var el = document.getElementsByName('agentIDs');
	    if(el.length==0)
			 return false;
	    else
	         return true;
	 }
	 
	 //复选框全选操作
	 function SelectAllCheck(obj){
	     if(checkAgentID()){
	        var el = document.getElementsByName('agentIDs');
	        var len = el.length;
	        if(obj.checked==true){ 
	            for(var i=0; i<len; i++){  
	              el[i].checked = true; 
	            } 
	        } else { 
	            for(var i=0; i<len; i++){ 
	              el[i].checked = false; 
	            } 
	        }
	     }else
	        return false;
	 }
	 
	 function doSelect(chkObj){
	 	var chkAll = document.getElementById("AllCheckboxID");
	 	if(chkAll.checked){
	        chkAll.checked=false;
	        chkObj.checked=false;
		}else{
			if(chkObj.checked){
				  chkObj.checked=true;
			}else{
				  chkObj.checked=false;
			}
			var el = document.getElementsByName('agentIDs');
	      	var len = el.length;
	       	var temp=0;
	        for(var i=0; i<len; i++){  
	          if(el[i].checked == true){
	             temp=temp+1;
	          }
	        }
	        if(temp==len)
	        	chkAll.checked=true;
		}
	 }	 
	 
//-->
</script>
