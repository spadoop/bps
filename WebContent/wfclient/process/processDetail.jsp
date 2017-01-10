<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.bps.client.common.Constants"%>
<%@ page import="com.bps.client.common.IPageNavigation"%>
<%@ include file="/wfclient/common/common.jsp"%>
<html>
<head>
<title><bc:message key='process.ViewProcess'/></title>
<script type="text/javascript">

//*** 缩放比例 ***//
function handleZoom(selectObj) {
	frm.action='<%=request.getContextPath()%>/wfclient/task/view/graph.jsp';	
	frm.zoom.value= selectObj.value;
	frm.submit();
}

//*** 启动 ***//
function doStart(){
	var form1 =document.getElementById('form1');
	form1.action="<%=request.getContextPath() + IPageNavigation.INSTANCE.getPageURL(Constants.PROCESS_FUNCTION, "process.do")%>";	
	form1.elements["methodActionID"].value="displayStartProcessPage";	
	form1.submit();
}
</script>
</head>

<body style="background:#FFFFFF;repeat-x;margin-top:10px;margin-left:12px;margin-buttom:0px;" >
<table border="0" cellpadding="0" cellspacing="0" class="workarea" width="100%" height="100%">
  <tr>
    <td class="workarea_title" valign="middle"><bc:message key='query.StartProcess'/> &gt; <bc:message key='process.ProcessList'/> &gt; <h3><bc:message key='process.ProcessDatails'/></h3></td>
  </tr>
<tr>
<td height="100%" valign="top">
<table  border="0" cellspacing="0" cellpadding="0" class="EOS_panel_body"style="width: 100%;height: 100%;table-layout:fixed;"> 
  <tr>
    <td class="EOS_table_row" width="60%"  valign="top">
	    <form action="#" name="frm" id="frm" target="graph" method="post" style="height:100%">
	   	<input type="hidden" name="processID" value="${process.processDefID}">
	   
	    <input type="hidden" id="zoom" name="zoom">
	    <table width="100%" bgcolor="fefefe" height="100%" border="0">
	        <tr height="20" valign="top">  
	          <td align="right" height="12"><bc:message key='view.ScalFeatures'/>：
	          	<select onChange="handleZoom(this)" size="1" name="zoomQuotiety">
		          <option value="1.0"><bc:message key='view.Automatic'/></option>
		          <option value="0.4">40%</option>
		          <option value="0.55">55%</option>
		          <option value="0.7">70%</option>
		          <option value="0.85">85%</option>
		          <option value="1.0">100%</option>
		          <option value="1.5">150%</option>
		          <option value="2.0">200%</option> 
	        	</select>
	          </td>
	         </tr>
	        <tr>
	        	<td id="graphTD" valign="top">        	
	        		<iframe id="graph" name="graph" 
	        				src='<%=request.getContextPath()%>/wfclient/task/view/graph.jsp?zoomQuotiety=1.0&processID=${process.processDefID}'
	        				scrolling="auto" frameBorder="0" style="size:auto; height:430px; border: 1px; red" width="98%">
	        		</iframe>
	        	</td>
	        </tr>
	    </table>
	    </form>
    </td>
    <td class="EOS_table_row" width="40%" valign="top">
    	<form action="#" name="form1" id="form1">
    	    <input type="hidden" id="processDefID" name="processDefID" value="${process.processDefID}">
			
			 <input type="hidden" id="methodActionID" name="method" />
    		<table class="workarea" width="100%"  >
            <tr> 
              <td valign="top">			    
                <table width="100%" border="0" cellspacing="0" class="form_table" cellpadding="0" height="100%" >
	                <tr class="tableHeadTR" align="center">
		                 <td colspan="2" class="EOS_panel_head" nowrap="nowrap"><bc:message key='process.ProcessInformation'/></td>
	                </tr>

	                <tr> 
						<td nowrap class="EOS_table_row" width="25%"><bc:message key='query.ProDID'/>:</td>
						<td>${process.processDefID}</td>
					</tr>

	                <tr> 
		    			<td nowrap class="EOS_table_row"><bc:message key='query.PronName'/>:</td>
		    			<td>
		    			<div id="processDefNameTD" style="width:300px;word-break:break-all;">${process.processChName}(${process.processDefName})</div>
		    			</td>
		    		</tr>
	                <tr> 	
						<td nowrap class="EOS_table_row"><bc:message key='myTask.Creation-time'/>:</td>
						<td><bc:timePatternConvert fromPattern="yyyyMMddHHmmss" toPattern="yyyy-MM-dd HH:mm:ss" fromTimeString='${process.createTime}' /></td>
					</tr>
	                <tr> 	
						<td nowrap class="EOS_table_row"><bc:message key='process.Update-Time'/>:</td>
						<td><bc:timePatternConvert fromPattern="yyyyMMddHHmmss" toPattern="yyyy-MM-dd HH:mm:ss" fromTimeString='${process.updateTime}' /></td>
					</tr>
					<tr> 	
						<td nowrap class="EOS_table_row"><bc:message key='process.VersionNumber'/>:</td>
						<td>${process.versionSign}</td>
					</tr>
					<tr> 	
						<td nowrap class="EOS_table_row"><bc:message key='process.VersionDescription'/>:</td>
						<td>${process.versionDesc}</td>
					</tr>
	                <tr> 	
						<td nowrap class="EOS_table_row"><bc:message key='process.PackageName'/>:</td>
						<td>${process.packageName}</td>
					</tr>
                </table>                
              </td>
            </tr>
            <tr>             
              <td align="center" class="form_bottom">
			 	<input type="button" name="btSubmit" value="<bc:message key='process.Start'/>" class="button" onclick='doStart();'/> 					  
			    <input type="button" name="btReturn" value="<bc:message key='query.Back'/>" class="button" onclick='history.go(-1);'/>
			  </td>                 
            </tr>           
          </table>
      </form>   
      </td>
    </tr>
</table>
</td>
</tr>
</table>
</body>
</html>
