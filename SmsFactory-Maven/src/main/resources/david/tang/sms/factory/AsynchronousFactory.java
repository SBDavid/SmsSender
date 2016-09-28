package main.resources.david.tang.sms.factory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import main.resources.david.tang.sms.concurrency.Consumer;
import main.resources.david.tang.sms.concurrency.Producer;
import main.resources.david.tang.sms.concurrency.Request;
import main.resources.david.tang.sms.logger.ConsoleLogger;
import main.resources.david.tang.sms.logger.NormalLoggerI;
import main.resources.david.tang.sms.numberfilter.NumberFilterBase;
import main.resources.david.tang.sms.smgsender.SmsSenderBase;

import org.apache.commons.lang3.StringUtils;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

public class AsynchronousFactory {
	
	private static NormalLoggerI logger = new ConsoleLogger();
	
	private static SAXBuilder builder = new SAXBuilder();
	
	private static Document jdomDoc = null;
	
	private static Element root;
	
	private static SmsControler controller = null;
	
	private static Producer producer = null;
	
	private static List<Consumer> consumers = new ArrayList<Consumer>();
	
	private static LinkedList<Request> buffer = new LinkedList<Request>();
	
	private static int consumerAmount = 5;
	
	private static int bufferSize = 100;
	
	private static int sendingTimeOut = 30000;
	
	/**
	 * initiate the target SmsController
	 * it should be called before any text sending
	 * @param senderName the name of the sender to initialize, which is define in the xml file. eg. <controller name="normal">
	 * @param configPath the absolute path of xml file
	 * @param loggerI the customized logger class, null if you want to print log info on console.
	 * @throws JDOMException
	 * @throws IOException
	 * @throws Exception
	 */
	public static void init(String senderName, String configPath, NormalLoggerI loggerI) throws Exception {
		
		if (loggerI != null ) {
			logger = loggerI;
		}
		
		if (jdomDoc == null) {
			jdomDoc = builder.build(new File(configPath));
		}
		if (root == null) {
			root = jdomDoc.getRootElement();
		}
		
		controller = loadController(senderName);
		
		consumerAmount = loadConsumerAmount();
		
		bufferSize = loadBufferSize();
		
		sendingTimeOut = loadSendingTimeOut();
		
		producer = new Producer(buffer, bufferSize, logger, sendingTimeOut);
		
		for (int i = 0; i < consumerAmount; i++) {
			consumers.add(new Consumer(buffer, controller, logger));
		}
		
		for (Consumer consumer : consumers) {
			consumer.start();
		}
	}
	
	public static boolean send(String type, String text, String mobile) {
		try {
			Request request = new Request();
			request.setText(text);
			request.setType(type);
			request.setMobile(mobile);
			return producer.add(request);
		}
		catch (Exception ex) {
			logger.exception(ex);
			return false;
		}
	}
	
	/**
	 * load consumer amount from xml file
	 * the amount should be great than 0 and less than 50, 
	 * a large amount of consumer will use many machine resource
	 * @return
	 */
	private static int loadConsumerAmount() {
		try {
			Element consumerAmountNode = root
					.getChild("asynchronous")
					.getChild("ComsumerAmount");
			
			int amount = Integer.parseInt(consumerAmountNode.getText());
			if (amount < 0) {
				logger.error("load consumer amount fail, amount is less than 0, return default amount 1");
				return 1;
			}
			else if (amount > 50) {
				logger.error("load consumer amount fail, amount is greater than 50, return max amount 50");
				return 50;
			}
			else {
				return amount;
			}
		}
		catch(Exception ex) {
			logger.error("load consumer amount fail, return default amount 1");
			logger.exception(ex);
			return 1;
		}
	}
	
	/**
	 * load the size of queue
	 * @return
	 */
	private static int loadBufferSize() {
		try {
			Element bufferSizeNode = root
					.getChild("asynchronous")
					.getChild("BufferSize");
			
			int size = Integer.parseInt(bufferSizeNode.getText());
			if (size < 5) {
				logger.error("load buffer size fail, buffer size is less than 5, return default buffer size 5");
				return 5;
			}
			else if (size > 500) {
				logger.error("load buffer size fail, buffer size is greater than 500, return max buffer size 500");
				return 500;
			}
			else {
				return size;
			}
		}
		catch(Exception ex) {
			logger.error("load buffer size fail, return default buffer size 5");
			logger.exception(ex);
			return 5;
		}
	}
	
	private static int loadSendingTimeOut() {
		try {
			Element sendingTimeOutNode = root
					.getChild("asynchronous")
					.getChild("SendingTimeOut");
			
			int timeout = Integer.parseInt(sendingTimeOutNode.getText());
			if (timeout < 0) {
				logger.error("load timeout fail, timeout is less than 5, return default timeout 30000");
				return 30000;
			}
			else if (timeout > 120000) {
				logger.error("load timeout fail, timeout is greater than 12000, return max timeout 30000");
				return 30000;
			}
			else {
				return timeout;
			}
		}
		catch(Exception ex) {
			logger.error("load timeout fail, return default timeout 5");
			logger.exception(ex);
			return 5;
		}
	}
	
	private static SmsControler loadController(String name) throws Exception {
		
		
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
			SmsControler controller = new SmsControler(logger, sender, filter);
			return controller;
		}	
		
	}
}
