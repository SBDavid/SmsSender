package sms.concurrency;

import java.util.LinkedList;

import sms.factory.SmsControler;
import sms.logger.NormalLoggerI;

public class Consumer extends Thread {
	private LinkedList<Request> buffer = null;
	private SmsControler controller;
	private NormalLoggerI logger;
	
	public Consumer(LinkedList<Request> buffer, SmsControler controller, NormalLoggerI logger) {
		this.buffer = buffer;
		this.controller = controller;
		this.logger = logger;
	}
	
	@Override 
	public void run() {
		while (true) {
			Request request = null;
			synchronized (buffer) {
				while (buffer.isEmpty()) {
					logger.debug("Queue is empty, Consumer thread is waiting for producer thread to put something in queue");
					try {
						buffer.wait();
					}
					catch (Exception ex) {
						logger.exception(ex);
					}
				}
				
				request = buffer.pop();
				buffer.notifyAll();
			}
			
			controller.send(request.getType(), request.getText(), request.getMobile());
		}
	}
}
