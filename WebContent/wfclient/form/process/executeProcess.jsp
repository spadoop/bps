<%@page import="com.bps.client.common.BPSServiceFacade"%>
<%@page import="com.bps.client.common.Util"%>
<%	
	String processDefID = (String) Util.getValue(request, "processDefID");
	String processDefName = (String) Util.getValue(request, "processDefName");
	String processInstName = (String) Util.getValue(request, "processInstName");
	String processInstDesc = (String) Util.getValue(request, "processInstDesc");

	String formDefID = (String) Util.getValue(request, "formDefID"); 
	String formInstID = (String) Util.getValue(request, "formInstID"); 
	String formDefName = (String) Util.getValue(request, "formDefName");  
	String dataXml = (String) Util.getValue(request, "dataXml"); 
	
	long processInstID = BPSServiceFacade.getInstance().getBPSFormManager().
		startProcessWithForm(Long.parseLong(processDefID),processDefName,processInstName,processInstDesc,
				formDefID,formInstID,formDefName,dataXml,null);

	request.setAttribute("processInstID",processInstID);
	
	request.getRequestDispatcher("/wfclient/process/startProcessResult.jsp").forward(request,response);
	
%>