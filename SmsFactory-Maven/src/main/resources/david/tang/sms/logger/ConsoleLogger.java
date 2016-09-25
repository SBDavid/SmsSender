package main.resources.david.tang.sms.logger;

public class ConsoleLogger implements NormalLoggerI {

	@Override
	public boolean sendRecord(String type, String number, String content,
			String status) {
		System.out.println(String.format("sendRecord Type : %s, number : %s, content : %s, status : %s", 
				type, number, content, status));
		return true;
	}

	@Override
	public boolean info(String info) {
		System.out.println(info);
		return false;
	}

	@Override
	public boolean error(String error) {
		System.out.println(error);
		return false;
	}

}
