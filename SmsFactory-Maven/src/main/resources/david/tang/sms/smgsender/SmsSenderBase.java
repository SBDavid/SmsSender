package main.resources.david.tang.sms.smgsender;

import main.resources.david.tang.sms.logger.NormalLoggerI;

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
}
