package test.resources.david.tang.sms.factory.demo;

import java.io.IOException;

import main.resources.david.tang.sms.factory.AsynchronousFactory;

import org.jdom2.JDOMException;

public class AsynchronousDemo {
	public static void main(String[] args) {
		try {
			AsynchronousFactory.init("test", "smsfactory.xml", null);
		} catch (JDOMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
