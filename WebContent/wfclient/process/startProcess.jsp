<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.bps.client.common.Constants"%>
<%@ page import="com.bps.client.common.IPageNavigation"%>
<%@ include file="/wfclient/common/common.jsp"%>

<html>
<head>
<title><bc:message key='process.StartForm'/></title>
<script language="JavaScript">

//*** 启动流程 ***//
function startProcess(){
	var processInstNameObj = document.getElementById("proInstName");
	var processInstName = processInstNameObj.value;
	
    if (processInstName=="" || processInstName.length==0){
		alertValidateMessage("<bc:message key='process.ProcessAlert'/>", processInstNameObj);
		return ;
	}
	if(processInstName=="null" || processInstName=="NULL"){
		alertValidateMessage("<bc:message key='process.ProcessNullAlert'/>", processInstNameObj);
		return ;
	}
	if(!checkBpsCommonName(processInstNameObj)){
		return;
	}
	if (!checkFormField(form1)) {
		return;
	}
	form1.action='<%=request.getContextPath() + IPageNavigation.INSTANCE.getPageURL(Constants.PROCESS_FUNCTION, "process.do")%>';	
	form1.elements["methodActionID"].value="startProcess";
	form1.submit();
}

//判断是否满足BPS名称类校验规则
//只包含字母、数字、中文、下划线、英文符号【-()[]{}.:/】、中文符号【，。：‘’“”】
function isBPSNameFormat(str){
	//var reg4str = /^[\u4E00-\u9FA5\w]*$/;//中文和包括下划线的任何单词字符
    //var reg4symbol = /^[-:\.\(\)\[\]\{\}\/]*$/; //包含的英文符号-:.()[]{}/
    //var reg4symbol_cn = /^[\uff0c\u3002\uff1a\u2018\u2019\u201c\u201d]*$/; //包含的中文符号，。：‘’“”;
    var reg = /^[\u4E00-\u9FA5\w-:\.\(\)\[\]\{\}\/\uff0c\u3002\uff1a\u2018\u2019\u201c\u201d]*$/;
    if(reg.test(str)){
    	return true;
    }else{
    	return false;
    }
}

function checkBpsCommonName(obj){
	var str = obj.value;
	if(isBPSNameFormat(str)){
		return true;
	}else{
		alertValidateMessage("<bc:message key='process.check_bpscommname'/>", obj);
        return false;
	}
}
</script>
</head>
<body style="background:#FFFFFF;repeat-x;margin-top:10px;margin-left:12px;margin-buttom:0px;">
<table border="0" cellpadding="0" cellspacing="0" class="workarea" width="100%" height="100%">
 		 <tr>
   			<td width="100%" valign="top">
<form action="#" id="form1" name="form1" method="post">
<input type="hidden" id="processDefID" name="processDefID" value="${process.processDefID}" />
<input type="hidden" id="processDefName" name="processDefName" value="${process.processDefName}" />
<input type="hidden" id="methodActionID" name="method" />
<table border="0px" class="workarea" width="100%" cellspacing="0px" cellpadding="0px">
	<tr>
		<td class="workarea_title"><bc:message key='query.StartProcess'/> &gt; <bc:message key='process.ProcessList'/> &gt; <h3><bc:message key='process.StartForm'/></h3></td>
	</tr>
	<tr>
		<td>   
			<table width="100%" border="0" cellspacing="0" cellpadding="0" class="form_table">
				<tr>	
					<td class="form_label" width="15%" ><bc:message key='myTask.BIName'/>:</td>
					<td width="35%">
						<input type="text" class="textbox" maxlength="85" size="32" id="proInstName" name="processInstName"/>
					</td>
					<td class="form_label" width="15%" height="12"><bc:message key='process.ProcessInsDes'/>:</td>
					<td >
						<input type="text" class="textbox" maxlength="170" size="32" id="proInstDesc" name="processInstDesc"/>
					</td>
				</tr>
				<tr>  
				    <td class="form_label" ><bc:message key='myTask.BPName'/>:</td>
				    <td colspan="3">${process.processChName}(${process.processDefName})</td>
				</tr>
			</table>
					
		</td>
	<tr>
		<td>	
			<table width="100%" border="0" cellspacing="0" cellpadding="0" class="form_table">	
				<wfBase:showProcStartForm id="formField" processID="${process.processDefID}" prefix="__processPrefix" formName="form1">
					<tr> 						
						<td align="left" width="15%" class="form_label">${formField.name}</td>
						<td align="left">${formField.htmlComponentCode}</td>
					</tr>
				</wfBase:showProcStartForm>
			</table>	
		</td>
	</tr>
	<tr>
		<td>
			<table width="100%" class="form_table" style="border:0px;cellspacing:0px; cellpadding:0px;">	
				<tr class="form_bottom"> 
				    <td colspan="4" align="center" >
						<input type="button" value="<bc:message key='process.Start'/>" id="startup" class="button" onclick="javascript:startProcess();">
						<input type="button" value="<bc:message key='query.Back'/>" id="backup" class="button" onclick="history.go(-1);">
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
