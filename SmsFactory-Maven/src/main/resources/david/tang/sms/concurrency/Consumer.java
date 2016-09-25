package main.resources.david.tang.sms.concurrency;

import java.util.LinkedList;

import main.resources.david.tang.sms.factory.SmsControler;

public class Consumer extends Thread {
	private LinkedList<Request> buffer = null;
	private SmsControler controller;
	
	public Consumer(LinkedList<Request> buffer, SmsControler controller) {
		this.buffer = buffer;
		this.controller = controller;
	}
	
	@Override 
	public void run() {
		while (true) {
			Request request = null;
			synchronized (buffer) {
				while (buffer.isEmpty()) {
					System.out.println("Queue is empty," + "Consumer thread is waiting" + " for producer thread to put something in queue");
					try {
						buffer.wait();
					}
					catch (Exception ex) {
						System.out.println(ex.getMessage());
					}
				}
				
				request = buffer.pop();
				buffer.notifyAll();
			}
			
			controller.send(request.getType(), request.getText(), request.getMobile());
		}
	}
}
