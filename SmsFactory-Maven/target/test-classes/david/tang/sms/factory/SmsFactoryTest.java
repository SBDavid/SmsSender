package test.resources.david.tang.sms.factory;

import main.resources.david.tang.sms.factory.AsynchronousFactory;
import main.resources.david.tang.sms.logger.ConsoleLogger;

import org.junit.Before;
import org.junit.Test;

public class SmsFactoryTest {
	
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testGetSmsControler() {
		try {
			AsynchronousFactory.init("test", "smsfactory.xml", new ConsoleLogger());
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		AsynchronousFactory.send("11", "test", "18121022433");
		AsynchronousFactory.send("11", "test", "18121022433");
		
//		try {
//			Thread.sleep(1);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		System.out.println("main quit");
		
	}

}
