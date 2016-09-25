package main.resources.david.tang.sms.factory;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import main.resources.david.tang.sms.logger.ConsoleLogger;
import main.resources.david.tang.sms.logger.NormalLoggerI;
import main.resources.david.tang.sms.numberfilter.NumberFilterBase;
import main.resources.david.tang.sms.smgsender.SmsSenderBase;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

public class SynchronousFactory {
	private static NormalLoggerI logger = new ConsoleLogger();
	
	private static Map<String, SmsControler> controlers = new HashMap<String, SmsControler>();
	
	private static SAXBuilder builder = new SAXBuilder();
	
	private static Document jdomDoc = null;
	
	private static Element root;
    
	public static SmsControler getSmsControler(String name, String configPath, NormalLoggerI loggerI) throws Exception {
		if (controlers.containsKey(name)) {
			return controlers.get(name);
		}
		else {
			SmsControler controller = loadController(name, configPath, loggerI);
			if (controller == null) {
				throw new Exception("loadController return null");
			}
			controlers.put(name, loadController(name, configPath, loggerI));
			return controlers.get(name);
		}
	}
	
	private static SmsControler loadController(String name, String configPath, NormalLoggerI loggerI) throws JDOMException, IOException, Exception {
		
		if (jdomDoc == null) {
			jdomDoc = builder.build(new File(configPath));
		}
		if (root == null) {
			root = jdomDoc.getRootElement();
		}
		Element controllerNode = root
				.getChild("controllers")
				.getChildren()
				.stream()
				.filter(controller -> controller.getAttributeValue("name").equals(name))
				.findFirst()
				.orElse(null)
				;

		if (controllerNode == null) {
			throw new Exception("fail to load config file");
		}
		else {
			// build a logger
			if (loggerI != null) {
				logger = loggerI;
			}
			
			// build a sender
			String senderName = controllerNode.getChild("sender").getAttributeValue("type");
			Class senderClassType = Class.forName(senderName);
			SmsSenderBase sender = (SmsSenderBase)senderClassType.newInstance(); 
			sender.setLogger(logger);
			
			// build a filter
			String filterName = controllerNode.getChild("filter").getAttributeValue("type");
			Class filterClassType = Class.forName(filterName);
			NumberFilterBase filter = (NumberFilterBase)filterClassType.newInstance();
			filter.setLogger(logger);
			controllerNode
					.getChild("filter")
					.getChild("numbers")
					.getChildren("number")
					.stream()
					.forEach(number -> filter.addNumber(number.getText()));
			
			// build a controller
			SmsControler controller = new SmsControler(logger, sender, filter);
			return controller;
		}	
		
	}
}
