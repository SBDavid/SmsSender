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
		double d = 12;
		for (int i = 0; i < 10000; i++) {
			for (int y = 0; y < 1000; y++) {
				for (int z = 0; z < 1000; z++) {
					d++;
				}
			}
		}
		
		return true;
	}

}
