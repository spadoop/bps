<%@page import="com.bps.client.user.UserManager"%>
<%@page import="com.bps.client.common.Constants"%>
<%@page import="com.bps.client.common.IPageNavigation"%>
<%
	//注销当前用户，重新登录
	UserManager.logout(session);
	response.sendRedirect(request.getContextPath() + IPageNavigation.INSTANCE.getPageURL(Constants.USER_FUNCTION, "login"));
 %>