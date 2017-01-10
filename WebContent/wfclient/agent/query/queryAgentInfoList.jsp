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
		<table border="0" cellpadding="0" cellspacing="0" class="workarea"	width="100%">
			<tr>
				<td class="workarea_title" valign="middle"><bc:message key='query.AgentView'/> &gt; <h3><bc:message key='query.AgentInformations'/></h3>
				</td>
			</tr>
			<tr>
				<td width="100%" valign="top">
				<table cellpadding="0" width="100%" cellspacing="0" border="0">
					<tr>
						<td>
						<table border="0" class="EOS_panel_body" width="100%">
							<tr>
								<td class="EOS_panel_head" valign="middle">【<bc:message key='query.AgentInformations'/>】</td>
							</tr>
							<tr>
								<td width="100%" valign="top">
								<table border="0" class="EOS_table" width="100%">
									<tr class="EOS_table_head">
										<th width="5%" nowrap><bc:message key='myTask.View'/></th>
										<th width="10%" nowrap><bc:message key='query.Principal'/></th>
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
										int flag = 0;
										%>
					
					<c:forEach var="agentInfo" items="${agentList}">
						<c:if test="${(useful == 'Y' && (agentInfo.startTime < CurrentTime && agentInfo.endTime > CurrentTime)) 
										|| (useful == 'N' && (agentInfo.startTime > CurrentTime || agentInfo.endTime < CurrentTime))
										|| (useful == 'ALL' || useful == null)}">
										
										<tr class="EOS_table_row"
												onmouseover="this.className='EOS_table_selectRow'"
												onmouseout="this.className='EOS_table_row'">
												<td style="text-align:center;">
													<a href='javascript:doQueryDetail(${agentInfo.agentID});'><bc:message key='myTask.View'/></a>
												</td>
												<td><bc:displayParticipantName typeCode='${leafType}' participantID="${agentInfo.agentFrom}" /></td>
												<td><bc:displayParticipantName typeCode='${agentInfo.agentToType}' participantID="${agentInfo.agentTo}" /></td>
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
										<%	flag++;	%>
						</c:if>
					</c:forEach>	
										
										<%
												
											if (flag < 10) {
												for (int i = flag; i < 10; i++) {
										%>
										<tr class="EOS_table_row">
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
										</tr>
										<%
												}
											}
										%>

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
	
	 //查询某代理详细信息
	 function  doQueryDetail(agID){
	    document.forms['agentform'].action = formAction + '?agentID=' + agID;
	    document.forms['agentform'].elements["methodActionID"].value="queryAgentDetail";	   
	    document.forms['agentform'].submit();
	 }
	 
	 //分类查询
	 function  doQuery(){        
	    document.forms['agentform'].action = formAction;
	    document.forms['agentform'].elements["methodActionID"].value = "queryAgentInfoList";
	    document.forms['agentform'].submit();
	 }
//-->
</script>
