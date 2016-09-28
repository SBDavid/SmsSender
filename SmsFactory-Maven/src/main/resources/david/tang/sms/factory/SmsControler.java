package main.resources.david.tang.sms.factory;

import org.apache.commons.lang3.StringUtils;

import main.resources.david.tang.sms.logger.NormalLoggerI;
import main.resources.david.tang.sms.numberfilter.NumberFilterI;
import main.resources.david.tang.sms.smgsender.SmsSendlerI;
/**
 * 
 * it controls the process of sending
 * step 1. check if the input params is valid
 * step 2. check if the phone number meets the condition of numberfilter
 * step 3. send text through a SmsSender
 */
public class SmsControler{
	
	protected static final String SmsSendSuccess = "Y";
	
	protected static final String SmsSendFail = "N";
	
	private NormalLoggerI logger;
	
	private SmsSendlerI sendler;
	
	private NumberFilterI filter;
	
	public SmsControler(NormalLoggerI logger
			, SmsSendlerI sendler
			, NumberFilterI filter) {
		this.logger = logger;
		this.sendler = sendler;
		this.filter = filter;
	}
	
	/**
	 * send text
	 * @param type it is customized field, it is useful when you print log info 
	 * @param text the content of sms text
	 * @param mobile target phone number
	 * @return
	 */
	public boolean send(String type,String text, String mobile) {
		
		if (StringUtils.isEmpty(type) || StringUtils.isEmpty(text) || StringUtils.isEmpty(mobile)) {
			logger.error("The param is not illegal !");
			return false;
		}
		
		if (filter != null && !filter.Filter(mobile)) {
			logger.sendRecord(type, mobile, text, SmsSendFail);
			return false;
		}
		if (sendler.sendSms(text, mobile)) {
			logger.sendRecord(type, mobile, text, SmsSendSuccess);
			return true;
		}
		else {
			logger.sendRecord(type, mobile, text, SmsSendFail);
			return false;
		}
	}
}
