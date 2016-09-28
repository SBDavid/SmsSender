package sms.logger;

public interface NormalLoggerI {
	boolean sendRecord(String type, String number, String content, String status);
	
	boolean info(String msg);
	
	boolean error(String msg);
	
	boolean debug(String msg);
	
	boolean exception(Throwable ex);
}
