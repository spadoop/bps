<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.bps.client.common.IPageNavigation"%>
<%@ page import="com.bps.client.common.Constants"%>
<%@ include file="/wfclient/common/common.jsp"%>

<html>
<head>
<title><bc:message key='myTask.handled'/></title>
<script type="text/javascript">

//*** 查看 ***//
function showDetail(id){
	var form1 = document.getElementById('form1');
	form1.action="<%=request.getContextPath() + IPageNavigation.INSTANCE.getPageURL(Constants.TASK_FUNCTION, "watchTask.do")%>";
    form1.workitemID.value=id;
	form1.showType.value="fin";
	form1.submit();
}

//*** 任务来源(全部) ***//
function doCheckAll2(chkObj){
    var self = document.getElementById("self");
    var agent = document.getElementById("agent");
    var deleg = document.getElementById("deleg");
    var help = document.getElementById("help");        
    if(chkObj.checked){
        self.checked=true;
	    agent.checked=true;
		deleg.checked=true;
		help.checked=true;
	}else{
		self.checked=false;
		agent.checked=false;
		deleg.checked=false;
		help.checked=false;
	}
}

//*** 任务来源(自己/代理/代办/协办) ***//
function doSelect2(chkObj,n){
    var chkAll2 = document.getElementById("chkAll2");
    var self = document.getElementById("self");
    var agent = document.getElementById("agent");
    var deleg = document.getElementById("deleg");
    var help = document.getElementById("help");
    if(chkAll2.checked){
        chkAll2.checked=false;
        if(n=="1"){
        	self.checked=false;
        }
        if(n=="2"){
        	agent.checked=false;
        }
        if(n=="3"){
            deleg.checked=false;
        }
        if(n=="4"){
            help.checked=false;
        }
	}else{
		if(chkObj.checked){
			chkObj.checked=true;
		}else{
			chkObj.checked=false;
		}
		if(n=="1"&&agent.checked&&deleg.checked&&help.checked)
			chkAll2.checked=true;
			if(n=="2"&&self.checked&&deleg.checked&&help.checked)
				chkAll2.checked=true;
			if(n=="3"&&self.checked&&agent.checked&&help.checked)
			    chkAll2.checked=true;
			if(n=="4"&&self.checked&&agent.checked&&deleg.checked)
			    chkAll2.checked=true;
	}
}

//*** 页面加载，初始被选中复选框 ***//
function selectOfChk(){
   	var bizPara = '${bizPara}';
       
    var chkAll2 = document.getElementById("chkAll2");
    var self = document.getElementById("self");
    var agent = document.getElementById("agent");
    var deleg = document.getElementById("deleg");
    var help = document.getElementById("help");
        
    if(bizPara=="" || bizPara.length==0){
		self.checked=true;
		agent.checked=true;
		deleg.checked=true;
		help.checked=true; 
		chkAll2.checked=true;
	}else{        
	    bizPara = bizPara.split(",");
	    for(var j=0;j<bizPara.length;j++){
	        if(bizPara[j]=="SELF")
				self.checked=true;
			if(bizPara[j]=="AGENT")
				agent.checked=true;
			if(bizPara[j]=="DELEG")
				deleg.checked=true;
			if(bizPara[j]=="HELP")
			    help.checked=true; 
			if(bizPara[j]=="ALL"){ 
				self.checked=true;
				agent.checked=true;
				deleg.checked=true;
				help.checked=true; 
				chkAll2.checked=true;
			}
		}
	}
}

//*** 查询 ***//
function query(){
    var chkAll2 = document.getElementById("chkAll2");
    var self = document.getElementById("self");
    var agent = document.getElementById("agent");
    var deleg = document.getElementById("deleg");
    var help = document.getElementById("help");
	var bizPara = "";
	    
	if(chkAll2.checked)
	    bizPara = chkAll2.value;
	else {
	    if(self.checked)
        	bizPara = bizPara + self.value + ",";
        if(agent.checked)
           	bizPara =bizPara  + agent.value + ",";
        if(deleg.checked)
           	bizPara =bizPara  + deleg.value + ",";
        if(help.checked)
           	bizPara =bizPara  + help.value;     
        if(self.checked&&agent.checked&&deleg.checked&&help.checked)
           	bizPara = chkAll2.value;
        if(bizPara==""){
	       	alert("<bc:message key='myTask.SouAlter'/>");
	       	return false;
	    }
	}
    var workItemFrm = document.getElementById('workItemFrm');		
    workItemFrm.action="<%=request.getContextPath() + IPageNavigation.INSTANCE.getPageURL(Constants.TASK_FUNCTION, "taskConditionQueryforOthers.do")%>";
    workItemFrm.bizPara.value=bizPara;
    workItemFrm.submit();
}

</script>
</head>

<body onload="selectOfChk()" style="repeat-x;margin-top:0px;margin-left:0px; margin-right:0px;margin-buttom:0px;width:90%">
<table border="0" cellpadding="0" align="center" cellspacing="0" class="workarea" width="90%">
  <tr>
    <td class="workarea_title"><!-- <bc:message key='myTask.client'/> &gt; <h3><bc:message key='myTask.handled'/></h3> -->&nbsp;&nbsp;</td>
  </tr>
<tr>
<td>
<table border="0" class="EOS_panel_body" width="100%">
  <tr> 
    <td class="EOS_panel_head"><!-- <bc:message key='myTask.QConditions'/> -->&nbsp;&nbsp;&nbsp;</td>
  </tr>
  <tr> 
    <td width="100%">
	<!--查询条件显示开始-->
	<form action="#" name="workItemFrm" id="workItemFrm" method="post">
    	<input type="hidden" id="bizPara" name="bizPara"></input>
	 	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="form_table">              
	      <tr align="left">
	        <td width="15%" class="EOS_table_row"><bc:message key='myTask.source'/>:</td>
	        <td align="left">
	            <input type="checkbox" id="chkAll2" name="chkAll2" value="ALL" class=" " onClick="doCheckAll2(this);">
                <bc:message key='myTask.all'/>&nbsp;&nbsp;&nbsp;
                <input type="checkbox" id="deleg" name="deleg" value="DELEG" class=" " onClick="doSelect2(this,3);">
                <bc:message key='myTask.delegate'/>&nbsp;&nbsp;&nbsp;
                <input type="checkbox" id="help" name="help" value="HELP" class=" " onClick="doSelect2(this,4);">
                <bc:message key='myTask.help'/>&nbsp;&nbsp;&nbsp;
                <input type="checkbox" id="agent" name="agent" value="AGENT" class=" " onClick="doSelect2(this,2);">
                <bc:message key='myTask.agent'/>&nbsp;&nbsp;&nbsp;
                <input type="checkbox" id="self" name="self" value="SELF" class=" " onClick="doSelect2(this,1);">
                <bc:message key='myTask.own'/> 
	        </td>
	        <td  width="40%"  align="left" class="">
	          <input type="button" class="btn btn-info btn-sm" value = "<bc:message key='myTask.query'/>" onclick="javascript:query();">
	        </td>
	      </tr>
	      <tr align="left">
	        <td width="15%" class="EOS_table_row"> &nbsp;</td>
	        <td align="left"> </td>
	        <td  width="40%"  align="center" class="form_bottom"> </td>
	      </tr>
		</table>
	</form>
    <!--查询条件显示结束-->
	</td>	
  </tr>
</table>
<table border="0" class=" " width="90%">
  <tr> 
	<td class=" "><!-- <bc:message key='myTask.tList'/> -->&nbsp;&nbsp;&nbsp;</td>
  </tr>
  <tr> 
    <td>
    <form action="#" name="form1" id="form1" method="post">    
    <input type="hidden" id="workitemID" name="workitemID">
    <input type="hidden" id="showType" name="showType">
    <input type="hidden" name="showTitle" value="<bc:message key='myTask.handled'/>">
    <input type="hidden" id="bizPara" name="bizPara" value='${bizPara}' />
	<table border="0" cellpadding="0" cellspacing="0" width="100%">
	<tr>
	<td>
	<table border="0" class="table table-condensed table-striped table-bordered table-hove" width="100%">
     <tr class=" " align="center">
           <th height="25" width="5%" nowrap="nowrap"><bc:message key='myTask.Operation'/> </th>
           <th height="25" width="5%" nowrap="nowrap"><bc:message key='myTask.WorkID'/></th>
           <th height="25" width="10%" nowrap="nowrap"><bc:message key='myTask.WorkName'/></th>
           <!-- <th height="25" width="20%" nowrap="nowrap"><bc:message key='myTask.BPName'/></th> -->
           <th height="25" width="10%" nowrap="nowrap"><bc:message key='myTask.BIName'/></th>
           <th height="25" width="5%" nowrap="nowrap"><bc:message key='myTask.CState'/></th>
           <th height="25" width="5%" nowrap="nowrap"><bc:message key='myTask.STime'/></th>
           <th height="25" width="5%" nowrap="nowrap"><bc:message key='myTask.TLimit'/></th>
           <th height="25" width="5%" nowrap="nowrap"><bc:message key='myTask.Timeout'/></th>
           <th height="25" width="5%" nowrap="nowrap"><bc:message key='myTask.ExeResult'/></th>
    </tr>
	<%int flag = 0; %>
	<c:forEach items="${tasklist}" var="task">
		<tr class=" "  onmouseover="this.className=' '" onmouseout="this.className=' '">
		    <td style="text-align:center;" nowrap><a class="link" href='javascript:showDetail(${task.workItemID});'><bc:message key='myTask.View'/></a></td>
		    <td>${task.workItemID}</td>
		    <td nowrap>${task.workItemName}</td><!-- 
		    <td nowrap>${task.processChName}(${task.processDefName})</td> -->
		    <td nowrap>${task.processInstName}</td>
		    <td><span class="label label-sm label-warning ng-binding"><bc:displayProInstCurrentState currentState='${task.currentState}' flag='task'/></span></td>
  			<td nowrap><bc:timePatternConvert fromPattern="yyyyMMddHHmmss" toPattern="yyyy-MM-dd HH:mm:ss" fromTimeString='${task.createTime}' /></td>
  			<td nowrap>${task.limitNumDesc}</td>
  			<td nowrap>
				<c:if test="${task.isTimeOut == 'Y'}"><bc:message key='myTask.Yes'/></c:if>
	            <c:if test="${task.isTimeOut == 'N'}"><bc:message key='myTask.No'/></c:if>			
			</td>
			<td nowrap>${task.dealOpinion}</td>
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
    	</tr>
    <%} %>
	</table>     
	</td>
	</tr>
	<tr>
		<td colspan="10" class="result_bottom" align="right">
        	<c:if test="${flag eq 'default'}">
				<c:set var="action" value='<%=request.getContextPath() + IPageNavigation.INSTANCE.getPageURL(Constants.TASK_FUNCTION, "tasklistforOthers.do")%>'/>
			</c:if>
        	<c:if test="${flag eq 'condition'}">              		
				<c:set var="action" value='<%=request.getContextPath() + IPageNavigation.INSTANCE.getPageURL(Constants.TASK_FUNCTION, "taskConditionQueryforOthers.do")%>'/>
			</c:if>			
			<%@ include file="/wfclient/common/pagination.jsp"%>
		</td>
	</tr>
	</table>
	</form>
	</td>
  </tr>
</table>
</td></tr>
</table> 
</body>
</html>
