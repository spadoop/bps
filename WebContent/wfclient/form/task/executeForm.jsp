<%@page import="com.bps.client.common.BPSServiceFacade"%>
<%@page import="com.bps.client.common.Constants"%>
<%@page import="com.bps.client.common.Util"%>
<%	
	String buttonType = (String) Util.getValue(request, "buttonType"); 
	String workItemID = (String) Util.getValue(request, "workitemID"); 

	String formDefID = (String) Util.getValue(request, "formDefID"); 
	String formInstID = (String) Util.getValue(request, "formInstID"); 
	String formDefName = (String) Util.getValue(request, "formDefName"); 
	String dataXml = (String) Util.getValue(request, "dataXml"); 
	
	long result = 0;
	if(buttonType.equals(Constants.BUTTON_TYPE_OK) || buttonType.equals(Constants.BUTTON_TYPE_EXECUTE)){
		result = BPSServiceFacade.getInstance().getBPSFormManager().
			finishWorkItemWithForm(Long.parseLong(workItemID),formDefID,formInstID,formDefName,dataXml,null,false);
	}else{
		result = BPSServiceFacade.getInstance().getBPSFormManager().
			saveWorkItemWithForm(Long.parseLong(workItemID),formDefID,formInstID,formDefName,dataXml);
	}
	
	if(result>0){
		request.getRequestDispatcher("/wfclient/task/sucessBack.jsp").forward(request,response);
	}else{
		request.getRequestDispatcher("/wfclient/task/errorBack.jsp").forward(request,response);
	}
	
%>