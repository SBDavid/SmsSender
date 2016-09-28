package sms.factory;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import sms.logger.ConsoleLogger;
import sms.logger.NormalLoggerI;
import sms.numberfilter.NumberFilterBase;
import sms.smgsender.SmsSenderBase;

public class SynchronousFactory {
	private static NormalLoggerI logger = new ConsoleLogger();
	
	private static SAXBuilder builder = new SAXBuilder();
	
	private static Document jdomDoc = null;
	
	private static Element root;
	
	private static SmsControler controller = null;
	
	public static boolean send(String type, String text, String mobile) {
		try {
			return controller.send(type, text, mobile);
		}
		catch (Exception ex) {
			logger.exception(ex);
			return false;
		}
	}
	
	/**
	 * initiate the target SmsController
	 * it should be called before any text sending
	 * @param name the name of the sender to initialize, which is define in the xml file. eg. <controller name="normal">
	 * @param configPath the absolute path of xml file
	 * @param loggerI the customized logger class, null if you want to print log info on console.
	 * @throws JDOMException
	 * @throws IOException
	 * @throws Exception
	 */
	public static void init(String name, String configPath, NormalLoggerI loggerI) throws JDOMException, IOException, Exception {
		
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
			Element paramsNode = controllerNode.getChild("sender").getChild("params");
			if (paramsNode != null) {
				Map<String, String> paramMap = new HashMap<String, String>();
				paramsNode.getChildren("param").stream()
					.forEach(p -> paramMap.put(p.getAttributeValue("key"), p.getAttributeValue("value")));
				sender.init(paramMap);
			}
			sender.setLogger(logger);
			
			// build a filter
			NumberFilterBase filter = null;
			String filterName = controllerNode.getChild("filter").getAttributeValue("type");
			if (StringUtils.isEmpty(filterName)) {
				
				Class filterClassType = Class.forName(filterName);
				filter = (NumberFilterBase)filterClassType.newInstance();
				filter.setLogger(logger);
				List<Element> numbers = controllerNode
						.getChild("filter")
						.getChild("numbers")
						.getChildren("number");
				for (Element number : numbers) {
					filter.addNumber(number.getText());
				}
			}
			else {
				filter = null;
			}
			
			// build a controller
			controller = new SmsControler(logger, sender, filter);
		}	
		
	}
}
