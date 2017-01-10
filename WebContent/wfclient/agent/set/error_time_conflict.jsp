<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file = "/wfclient/common/common.jsp"%>
<html>
	<head>
		<title><bc:message key='set.ErrorPage'/></title>
	</head>
    <body  style="background:#FFFFFF;repeat-x;margin-top:10px;margin-left:12px;margin-buttom:0px;">
	<table border="0" cellpadding="0" cellspacing="0" class="workarea" width="100%" height="100%">
 		 <tr>
   			<td width="100%" valign="top">
<form action="" method="post" name="errorform">
<input type="hidden" id="methodActionID" name="method" />
<table border="0" cellpadding="0" cellspacing="0" class="workarea" width="100%">
  <tr>
    <td class="workarea_title" valign="middle"><bc:message key='set.SetAgent'/> &gt; <h3><bc:message key='set.ErrorPage'/></h3></td>
  </tr>
  <tr>
    <td width="100%" valign="top">
      <table cellpadding="0" width="100%" cellspacing="0" border="0">
        <tr>
          <td>
            <table border="0" class="EOS_panel_body" width="100%">
              <tr>
                <td class="EOS_panel_head"><bc:message key='set.AgTimeConflict'/></td>
              </tr>
              <tr>
                <td width="100%">
                 
                          <table width="100%" border="0" cellpadding="0" cellspacing="0" class="form_table">
                            <tr>
                                <div align="center"><bc:message key='set.AgCAlert'/></div>
                            </tr>
							<tr>
                              <td colspan="4" class="form_bottom">
                                 <input  type="button" name="Button1" value="<bc:message key='query.Back'/>" class="button" onclick="doBack();">
                               </td>
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
                <td class="EOS_panel_head"><bc:message key='set.AgActConfilct'/></td>
              </tr>
              <tr>
                <td width="100%">
                 
                          <table width="100%" border="0" cellpadding="0" cellspacing="0" class="form_table">
                            <tr>
                                <div align="center"><bc:message key='set.AgActANDActOverlap'/></div>
                            </tr>
							<tr>
                              <td colspan="4" class="form_bottom">
                                 <input  type="button" name="Button1" value="<bc:message key='query.Back'/>" class="button" onclick="doBack();">
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
<script language="JavaScript" type="text/javascript">
<!-- 
    function doBack(){
        history.go(-1);
    }
//-->
</script>
