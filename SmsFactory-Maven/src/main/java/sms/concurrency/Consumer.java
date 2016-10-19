package sms.concurrency;

import java.util.LinkedList;

import sms.factory.SmsControler;
import sms.logger.NormalLoggerI;

public class Consumer extends Thread {
	private LinkedList<Request> buffer = null;
	private SmsControler controller;
	private NormalLoggerI logger;
	
	private boolean isRuning;
	
	public Consumer(LinkedList<Request> buffer, SmsControler controller, NormalLoggerI logger) {
		this.buffer = buffer;
		this.controller = controller;
		this.logger = logger;
		this.isRuning = false;
	}
	
	public boolean isRunning() {
		return this.isRuning;
	}
	
	@Override 
	public void run() {
		while (true) {
			this.isRuning = true;
			Request request = null;
			synchronized (buffer) {
				while (buffer.isEmpty()) {
					logger.debug("Queue is empty, Consumer thread is waiting for producer thread to put something in queue");
					try {
						buffer.wait();
						if (buffer.size() == 0 && Thread.currentThread().isInterrupted()) {
							logger.debug("Consumer thread is exit in the waiting");
							this.isRuning = false;
							return;
						}
					}
					catch (InterruptedException ex) {
						logger.debug("Consumer thread is exit beacouse of InterruptedException");
						this.isRuning = false;
						return;
					}
					catch (Exception ex) {
						logger.exception(ex);
					}
				}
				
				request = buffer.pop();
				buffer.notifyAll();
			}
			
			controller.send(request.getType(), request.getText(), request.getMobile());
			synchronized (buffer) {
				if (buffer.size() == 0 && Thread.currentThread().isInterrupted()) {
					logger.debug("Consumer thread is exit beacouse of Interrupte status");
					this.isRuning = false;
					return;
				}
			}
		}

	}
}
