package sms.smgsender;

import java.util.Map;

import sms.logger.NormalLoggerI;

public class SmsSenderBase implements SmsSendlerI{
	protected NormalLoggerI logger;
	
	public void setLogger(NormalLoggerI logger) {
		this.logger = logger;
	}

	@Override
	public boolean sendSms(String text, String mobile) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean init(Map<String, String> params) {
		// TODO Auto-generated method stub
		return true;
	}
}
