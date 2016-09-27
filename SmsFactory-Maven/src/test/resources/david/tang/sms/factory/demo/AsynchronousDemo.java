package test.resources.david.tang.sms.factory.demo;

import main.resources.david.tang.sms.factory.AsynchronousFactory;

public class AsynchronousDemo {
	public static void main(String[] args) {
		try {
			AsynchronousFactory.init("test", "smsfactory.xml", null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		AsynchronousFactory.send("type", "msgContent", "18121022433");
		
		// prevent child thread be killed by the ending of main thread
		try {
			Thread.sleep(1000000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
