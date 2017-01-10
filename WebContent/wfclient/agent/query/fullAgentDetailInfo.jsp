<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file = "/wfclient/common/common.jsp"%>
<html>
	<head>
		<title>Title</title>
	</head>
    <body  style="background:#FFFFFF;repeat-x;margin-top:10px;margin-left:12px;margin-buttom:0px;">
	<table border="0" cellpadding="0" cellspacing="0" class="workarea" width="100%" height="100%">
 		 <tr>
   			<td width="100%" valign="top">
<form action="" method="post" name="detailform">
<table border="0" cellpadding="0" cellspacing="0" class="workarea" width="100%">
  <tr>
    <td class="workarea_title" valign="middle"><bc:message key='query.AgentQuery'/> &gt; <h3><bc:message key='query.AgentDetails'/></h3></td>
  </tr>
  <tr>
    <td width="100%" valign="top">
      <table cellpadding="0" width="100%" cellspacing="0" border="0">
        <tr>
          <td>
            <table border="0" class="EOS_panel_body" width="100%">
              <tr>
                <td class="EOS_panel_head"><bc:message key='query.AgentRealation'/></td>
              </tr>
              <tr>
                <td width="100%">
                 
                          <table width="100%" border="0" cellpadding="0" cellspacing="0" class="form_table">
                            <tr>
                              <td  nowrap class="EOS_table_row"><bc:message key='query.Principal'/>:</td>
                              <td  nowrap><bc:displayParticipantName typeCode='${leafType}' participantID="${AgentDetail.agentFrom}" /></td>
                              <td nowrap class="EOS_table_row"><bc:message key='query.Agents'/>:</td>
                              <td nowrap><bc:displayParticipantName typeCode='${AgentDetail.agentToType}' participantID="${AgentDetail.agentTo}" /></td>
                            </tr>
                            <tr>
                               <td class="EOS_table_row"><bc:message key='query.EffTime'/>:</td>
                               <td><bc:timePatternConvert fromPattern="yyyyMMddHHmmss" toPattern="yyyy-MM-dd HH:mm:ss" fromTimeString='${AgentDetail.startTime}' /></td>
                               <td class="EOS_table_row"><bc:message key='query.EndTime'/>:</td>
                               <td><bc:timePatternConvert fromPattern="yyyyMMddHHmmss" toPattern="yyyy-MM-dd HH:mm:ss" fromTimeString='${AgentDetail.endTime}' /></td>
                            </tr>
							<tr>
							  <td class="EOS_table_row"><bc:message key='query.AgentApproach'/>:</td>
                              <td colspan="3"><bc:message key='query.FullAgency'/></td>
                            </tr>
							<tr>
							  <td class="EOS_table_row" nowrap="nowrap"><bc:message key='query.AgentReason'/>:</td>
                              <td colspan="3" style="table-layout:fixed;word-wrap: break-word"><div style="width: 800px">${AgentDetail.agentReason}</div></td>
                            </tr>
                          </table>
                </td>
              </tr>
            </table>
          </td>
        </tr>
        <tr>
          <td>
            <table border="0" class="EOS_panel_body" width="100%">
              <tr>
                <td class="EOS_panel_head" valign="middle"><bc:message key='query.ListProcess'/></td>
              </tr>
              <tr>
                <td width="100%"  height="440"  valign="top">
                  <table border="0" class="EOS_table" width="100%">
                    <tr class="EOS_table_head">
                      <th width="35%"><bc:message key='query.ProDID'/></th>
                      <th width="65%"><bc:message key='query.PronName'/></th>
                    </tr>
                  
                      <% int flag =0;%>
                	  <c:forEach var="agentInfo" items="${agentDetailInfoList}">
                	        <tr class="EOS_table_row" onmouseover="this.className='EOS_table_selectRow'" onmouseout="this.className='EOS_table_row'">
		                      <td>${agentInfo.defID}</td>
		                      <td>${agentInfo.defCHName}(${agentInfo.proDefName})</td>
		                    </tr>
		              	<%flag++; %>
                	  </c:forEach>
                	  <%
				            if(flag<5){
				              for(int i = flag; i < 5;i++){ %> 
				         <tr class="EOS_table_row">
		                      <td></td>
		                      <td></td>
		                 </tr>
				      <%      }
					        } %> 
				    
                  </table>
                  <table width="100%" border="0" cellspacing="0" cellpadding="0" height="30">
                    <tr>
                      <td class="form_bottom">
					      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" name="Button1" value="<bc:message key='query.Back'/>" class="button"  onClick="history.go(-1);">
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
