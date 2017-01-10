/** 
 * Override the LoginController in bps-client.jar 
 * 
 * 此类需要单独放在<普元bps安装目录>/webapps/default/web-inf/classes/...下
 * 并且修改bps-servlet.xml，如下
 *   <bean name="loginController" class="com.bps.client.user.KMLoginController" /> 
 *   
 */
package cn.edu.sysu.bpm.ctrl;

import com.bps.client.common.BPSServiceFacade;
import com.bps.client.common.IPageNavigation;
import com.bps.client.common.Util;
import com.bps.client.common.i18n.ResourceMessageUtil;
import com.bps.client.user.LoginController;
import com.bps.client.user.UserBean;
import com.bps.client.user.UserManager;
import com.eos.data.datacontext.DataContextManager;
import com.eos.data.datacontext.UserObject;
import com.eos.workflow.omservice.IWFPermissionService;
import com.eos.workflow.omservice.WFParticipant;
import com.primeton.bps.component.manager.api.BPSMgrServiceClientFactory;
import com.primeton.ext.data.datacontext.http.MUODataContext;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

public class KMLoginController extends AbstractController {
	private static Log logger = LogFactory.getLog(LoginController.class);

	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String language = (String) Util.getValue(request, "language");

		if (null == language) {
			language = (String) Util.getValue(request, "locale");
		}

		if (null == language) {
			language = "zh_CN";
		}

		String userName = (String) Util.getValue(request, "userName");

		String password = (String) Util.getValue(request, "password");

		String tenantID = (String) Util.getValue(request, "tenantId");

		HttpSession session = request.getSession(false);

		session.setAttribute("locale", language);

		//if (validate(userName, password, tenantID)) {
		if(true){ //无验证！验证过程放在调用方
			UserBean userbean = new UserBean();
			userbean.setUserID(userName);
			try {
				WFParticipant thisParti = BPSServiceFacade
						.getInstance()
						.getOMService()
						.findParticipantByID(Util.getLeafParticipantType(),
								userName);
				if (thisParti != null) {
					userbean.setUserName(thisParti.getName());
					userbean.setUserTypeCode(thisParti.getTypeCode());
				}
			} catch (Throwable t) {
				logger.debug(null, t);
			}

			UserManager.login(session, userbean);
			session.setAttribute("TENANT_ID", tenantID);

			response.sendRedirect(request.getContextPath()
					+ IPageNavigation.INSTANCE
							.getPageURL("user", "function.do"));

			return null;
		}
		if (BPSMgrServiceClientFactory.getDefaultClient().getBPSWSManager()
				.isMultiTenantMode())
			request.setAttribute("errorMessage", ResourceMessageUtil
					.getResourceMessage("MultiTenancyErrorLogin", session));
		else {
			request.setAttribute("errorMessage", ResourceMessageUtil
					.getResourceMessage("errorLogin", session));
		}
		request.setAttribute("language", language);
		request.setAttribute("userName", userName);
		request.setAttribute("password", password);

		return new ModelAndView(IPageNavigation.INSTANCE.getPageURL("user",
				"login"));
	}

	private boolean validate(String userName, String password, String domainID) {
		if ((userName == null) || (userName.trim().length() == 0)) {
			return false;
		}

		UserObject userObj = new UserObject();
		userObj.setUserId(userName);

		userObj.getAttributes().put("TENANT_ID", domainID);

		Map map = new HashMap();
		map.put("userObject", userObj);
		MUODataContext context = new MUODataContext(map,
				new String[] { "userObject" });
		DataContextManager.current().setMUODataContext(context);
		try {
			IWFPermissionService permissionService = BPSServiceFacade
					.getInstance().getWFPermissionService();
			if ((permissionService != null)
					&& (permissionService.validate(userName, password, null))) {
				return true;
			}
		} catch (Exception e) {

			return false;
		} finally {
			DataContextManager.current().clear();
		}
		return false;
	}
}