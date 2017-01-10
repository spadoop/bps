<%@include file="/wfclient/common/common-java.jsp"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!-- 

<link href="<%=request.getContextPath()%>/wfclient/css/style-component.css" type="text/css" rel="stylesheet">
<link href="<%=request.getContextPath()%>/wfclient/css/style-custom.css" type="text/css" rel="stylesheet">
 -->

<link href="<%=request.getContextPath()%>/wfclient/css/ace.min.css" rel="stylesheet">
<link href="<%=request.getContextPath()%>/wfclient/css/bootstrap.min.css" rel="stylesheet">
<link href="<%=request.getContextPath()%>/wfclient/css/fontimport.css" rel="stylesheet">
<script src="<%=request.getContextPath()%>/wfclient/js/util.js"></script>
<script src="<%=request.getContextPath()%>/wfclient/js/tree.js"></script>
<script src="<%=request.getContextPath()%>/wfclient/js/selector.js"></script>
<script src="<%=request.getContextPath()%>/wfclient/js/json.js"></script>
<script src="<%=request.getContextPath()%>/wfclient/js/ajax.js"></script>
<script src="<%=request.getContextPath()%>/wfclient/js/pagination.js"></script>
<script src="<%=request.getContextPath()%><bc:message key='clint_calendar_js'/>"></script>