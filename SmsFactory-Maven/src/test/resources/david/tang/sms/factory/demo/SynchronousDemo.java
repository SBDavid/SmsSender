package test.resources.david.tang.sms.factory.demo;

import java.io.IOException;

import org.jdom2.JDOMException;

import main.resources.david.tang.sms.factory.SynchronousFactory;

public class SynchronousDemo {
	public static void main(String[] args) {
		try {
			SynchronousFactory.init("test", "smsfactory.xml", null);
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
		SynchronousFactory.send("type", "msgContent", "18121022433");
	}
}
