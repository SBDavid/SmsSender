package test.resources.david.tang.sms.factory.demo;

import main.resources.david.tang.sms.factory.SynchronousFactory;

public class SynchronousDemo {
	public static void main(String[] args) {
		try {
			SynchronousFactory.init("test", "smsfactory.xml", null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		SynchronousFactory.send("type", "msgContent", "18121022433");
	}
}
