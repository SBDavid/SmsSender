package main.resources.david.tang.sms.logger;

public interface NormalLoggerI {
	boolean sendRecord(String type, String number, String content, String status);
	
	boolean info(String info);
	
	boolean error(String error);
}