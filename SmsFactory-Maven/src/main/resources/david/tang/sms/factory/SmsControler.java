package main.resources.david.tang.sms.factory;

import org.apache.commons.lang3.StringUtils;

import main.resources.david.tang.sms.logger.NormalLoggerI;
import main.resources.david.tang.sms.numberfilter.NumberFilterI;
import main.resources.david.tang.sms.smgsender.SmsSendlerI;

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
	
	public boolean send(String type,String text, String mobile) {
		
		if (StringUtils.isEmpty(type) || StringUtils.isEmpty(text) || StringUtils.isEmpty(mobile)) {
			logger.error("The param is not illegal !");
			return false;
		}
		
		if (!filter.Filter(mobile)) {
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
