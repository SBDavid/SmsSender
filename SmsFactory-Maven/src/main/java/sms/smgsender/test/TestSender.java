package sms.smgsender.test;

import sms.smgsender.SmsSenderBase;

public class TestSender extends SmsSenderBase {
	
	public TestSender(){};
	
	@Override
	public boolean sendSms(String content, String number) {
		logger.info(String.format("msg is sent by %s, number : %s, content : %s", 
				this.getClass().getName(),
				number,
				content));
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			logger.error(e.getMessage());
		}
		return true;
	}

}
