package sms.logger;

import sms.logger.NormalLoggerI;

public class ConsoleLogger implements NormalLoggerI {

	@Override
	public boolean sendRecord(String type, String number, String content,
			String status) {
		System.out.println(String.format("sendRecord Type : %s, number : %s, content : %s, status : %s", 
				type, number, content, status));
		return true;
	}

	@Override
	public boolean info(String msg) {
		System.out.println(msg);
		return true;
	}

	@Override
	public boolean error(String msg) {
		System.out.println(msg);
		return true;
	}

	@Override
	public boolean debug(String msg) {
		System.out.println(msg);
		return true;
	}

	@Override
	public boolean exception(Throwable ex) {
		System.out.println(errorException(ex));
		return true;
	}
	
	private String errorException(Throwable e) {
		StackTraceElement[] ste = e.getStackTrace();
		StringBuffer sb = new StringBuffer();
		sb.append(e.toString() + "\n");
		
		for (int i = 0; i < ste.length; i++) {
			sb.append("    at "+ste[i].toString() + "\n");
		}
		return sb.toString();
	}
}

