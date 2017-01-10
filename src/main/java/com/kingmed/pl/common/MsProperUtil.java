/**
 * Project Name:km-ms
 * File Name:MsProperUtil.java
 * Package Name:com.kingmed.ms.util
 * Date:2015年3月31日下午9:30:17
 * Copyright (c) 2015, chenzhou1025@126.com All Rights Reserved.
 *
 */

package com.kingmed.pl.common;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * 属性文件帮助类 ClassName:MsProperUtil <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2015年3月31日 下午9:30:17 <br/>
 * 
 * @author tanw
 * @version
 * @since JDK 1.7
 * @see
 */
public class MsProperUtil {

	private static final Logger LOGGER = Logger.getLogger(MsProperUtil.class);

	public MsProperUtil() {
	}

	private static MsProperUtil instance = new MsProperUtil();

	public static MsProperUtil getInstance() {
		return instance;
	}

	private static String mailTemplateTitleCompact;// 邮件主题
	private static String mailTemplateContentCompactSubmit;// 提交时，给审批人发
	private static String mailTemplateContentCompactFail;// 驳回时，给提交人发
	private static String mailTemplateContentCompactSuccess;// 完成时，给提交人发
	
	private static String mailTemplateTitlePromotion;// 邮件主题
	private static String mailTemplateContentPromotionSubmit;// 提交时，给审批人发
	private static String mailTemplateContentPromotionFail;// 驳回时，给提交人发
	private static String mailTemplateContentPromotionSuccess;// 完成时，给提交人发
	
	private static String mailTemplateTitlePrice;// 邮件主题
	private static String mailTemplateContentPriceSubmit;// 提交时，给审批人发
	private static String mailTemplateContentPriceFail;// 驳回时，给提交人发
	private static String mailTemplateContentPriceSuccess;// 完成时，给提交人发
	
	private static String mailTemplateTitleSuit;// 邮件主题
	private static String mailTemplateContentSuitSubmit;// 提交时，给审批人发
	private static String mailTemplateContentSuitFail;// 驳回时，给提交人发
	private static String mailTemplateContentSuitSuccess;// 完成时，给提交人发
	
	private static String mailTemplateTitleCustomer;// 邮件主题
	private static String mailTemplateContentCustomerSubmit;// 提交时，给审批人发
	private static String mailTemplateContentCustomerFail;// 驳回时，给提交人发
	private static String mailTemplateContentCustomerSuccess;// 完成时，给提交人发
	
	private static String mailTemplateTitleTarget;// 邮件主题
	private static String mailTemplateContentTargetSubmit;// 提交时，给审批人发
	private static String mailTemplateContentTargetFail;// 驳回时，给提交人发
	private static String mailTemplateContentTargetSuccess;// 完成时，给提交人发

	private static String hostname;
	private static String username;
	private static String password;
	private static String setFrom;
	private static String engineSessionHandle;
	private static String emailSendIsFlag;
	
	
	//private static String bigClientContractFlow;//大客户合同流程定义名
	//private static String draftActivity;//大客户合同流程定义名
	
	//private static String bigClientPromotionFlow;//大客户合同流程定义名

	static {
		InputStream inputstream = null;
		try {
			inputstream = getInstance().getClass().getResourceAsStream(
					"/msConfig.properties");
			Properties properties = new Properties();
			properties.load(inputstream);

			mailTemplateTitleCompact = properties
					.getProperty("mail.template.title.compact");
			mailTemplateContentCompactSubmit = properties
					.getProperty("mail.template.content.compact.submit");
			mailTemplateContentCompactFail = properties
					.getProperty("mail.template.content.compact.aduit.fail");
			mailTemplateContentCompactSuccess = properties
					.getProperty("mail.template.content.compact.aduit.success");
			
			mailTemplateTitlePromotion = properties
					.getProperty("mail.template.title.promotion");
			mailTemplateContentPromotionSubmit = properties
					.getProperty("mail.template.content.promotion.submit");
			mailTemplateContentPromotionFail = properties
					.getProperty("mail.template.content.promotion.aduit.fail");
			mailTemplateContentPromotionSuccess = properties
					.getProperty("mail.template.content.promotion.aduit.success");
			
			mailTemplateTitleTarget = properties
					.getProperty("mail.template.title.target");
			mailTemplateContentTargetSubmit = properties
					.getProperty("mail.template.content.target.submit");
			mailTemplateContentTargetFail = properties
					.getProperty("mail.template.content.target.aduit.fail");
			mailTemplateContentTargetSuccess = properties
					.getProperty("mail.template.content.target.aduit.success");
			
			mailTemplateTitleCustomer = properties
					.getProperty("mail.template.title.customer");
			mailTemplateContentCustomerSubmit = properties
					.getProperty("mail.template.content.customer.submit");
			mailTemplateContentCustomerFail = properties
					.getProperty("mail.template.content.customer.aduit.fail");
			mailTemplateContentCustomerSuccess = properties
					.getProperty("mail.template.content.customer.aduit.success");
			
			mailTemplateTitlePrice = properties
					.getProperty("mail.template.title.price");
			mailTemplateContentPriceSubmit = properties
					.getProperty("mail.template.content.price.submit");
			mailTemplateContentPriceFail = properties
					.getProperty("mail.template.content.price.aduit.fail");
			mailTemplateContentPriceSuccess = properties
					.getProperty("mail.template.content.price.aduit.success");
			
			mailTemplateTitleSuit = properties
					.getProperty("mail.template.title.suit");
			mailTemplateContentSuitSubmit = properties
					.getProperty("mail.template.content.suit.submit");
			mailTemplateContentSuitFail = properties
					.getProperty("mail.template.content.suit.aduit.fail");
			mailTemplateContentSuitSuccess = properties
					.getProperty("mail.template.content.suit.aduit.success");
			
			/*bigClientPromotionFlow = properties
					.getProperty("big.client.promotion.flow");*/

			hostname = properties.getProperty("mail.hostname");
			username = properties.getProperty("mail.username");
			password = properties.getProperty("mail.password");
			setFrom = properties.getProperty("mail.setfrom");
			engineSessionHandle = properties.getProperty("engine.service.session.connect.handle");
			emailSendIsFlag = properties.getProperty("email.send.isflag");
			
			//draftActivity = properties.getProperty("draft.activity");
			
			/*bigClientContractFlow=properties
					.getProperty("big.client.contract.flow");*/
			inputstream.close();
		} catch (IOException e) {
			LOGGER.error("e:", e);
		} finally {
			if (inputstream != null) {
				try {
					inputstream.close();
				} catch (IOException e) {
					LOGGER.error("e:", e);

				}
			}
		}
	}

/*	public static String getBigClientPromotionFlow() {
		return bigClientPromotionFlow;
	}*/
	public static String getMailTemplateTitleSuit() {
		return mailTemplateTitleSuit;
	}

	public static String getMailTemplateContentSuitSubmit() {

		return mailTemplateContentSuitSubmit;
	}

	public static String getMailTemplateContentSuitFail() {

		return mailTemplateContentSuitFail;
	}
	
	public static String getMailTemplateContentSuitSuccess() {

		return mailTemplateContentSuitSuccess;
	}

	public static String getMailTemplateTitlePrice() {
		return mailTemplateTitlePrice;
	}

	public static String getMailTemplateContentPriceSubmit() {

		return mailTemplateContentPriceSubmit;
	}

	public static String getMailTemplateContentPriceFail() {

		return mailTemplateContentPriceFail;
	}
	
	public static String getMailTemplateContentPriceSuccess() {

		return mailTemplateContentPriceSuccess;
	}
	
	public static String getMailTemplateTitleCustomer() {
		return mailTemplateTitleCustomer;
	}

	public static String getMailTemplateContentCustomerSubmit() {

		return mailTemplateContentCustomerSubmit;
	}

	public static String getMailTemplateContentCustomerFail() {

		return mailTemplateContentCustomerFail;
	}

	public static String getMailTemplateContentCustomerSuccess() {

		return mailTemplateContentCustomerSuccess;
	}
	
	public static String getMailTemplateTitleTarget() {
		return mailTemplateTitleTarget;
	}

	public static String getMailTemplateContentTargetSubmit() {

		return mailTemplateContentTargetSubmit;
	}

	public static String getMailTemplateContentTargetFail() {

		return mailTemplateContentTargetFail;
	}

	public static String getMailTemplateContentTargetSuccess() {

		return mailTemplateContentTargetSuccess;
	}
	
	public static String getMailTemplateTitlePromotion() {
		return mailTemplateTitlePromotion;
	}

	public static String getMailTemplateContentPromotionSubmit() {

		return mailTemplateContentPromotionSubmit;
	}

	public static String getMailTemplateContentPromotionFail() {

		return mailTemplateContentPromotionFail;
	}

	public static String getMailTemplateContentPromotionSuccess() {

		return mailTemplateContentPromotionSuccess;
	}
	
	public static String getMailTemplateTitleCompact() {
		return mailTemplateTitleCompact;
	}

	public static String getMailTemplateContentCompactSubmit() {

		return mailTemplateContentCompactSubmit;
	}

	public static String getMailTemplateContentCompactFail() {

		return mailTemplateContentCompactFail;
	}

	public static String getMailTemplateContentCompactSuccess() {

		return mailTemplateContentCompactSuccess;
	}

	public static String getHostname() {

		return hostname;
	}

	public static String getUsername() {

		return username;
	}

	public static String getPassword() {

		return password;
	}
	
	public static String getSetFrom() {

		return setFrom;
	}
	
	public static String getEngineSessionHandle() {

		return engineSessionHandle;
	}
	
	public static String getEmailSendIsFlag() {

		return emailSendIsFlag;
	}
	
	/*public static String getBigClientContractFlow() {

		return bigClientContractFlow;
	}
	
	public static String getDraftActivity() {

		return draftActivity;
	}*/
}
