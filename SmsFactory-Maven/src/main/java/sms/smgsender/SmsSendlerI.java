package sms.smgsender;

import java.util.Map;

public interface SmsSendlerI {
	public boolean init(Map<String, String> params);
	public boolean sendSms(String text, String mobile);
}
