package main.resources.david.tang.sms.concurrency;

import java.util.LinkedList;

import main.resources.david.tang.sms.logger.NormalLoggerI;

public class Producer{
	private LinkedList<Request> buffer = null;
	
	private int maxBufferSize = 5;
	
	private NormalLoggerI logger;
	
	private int sendingTimeOut = 30000;
	
	public Producer(LinkedList<Request> buffer, int maxBufferSize, NormalLoggerI logger, int sendingTimeOut) {
		this.buffer = buffer;
		this.maxBufferSize = maxBufferSize;
		this.logger = logger;
		this.sendingTimeOut = sendingTimeOut;
	}

	public boolean add(Request request) {
		try {
			synchronized (buffer) {
				 while (buffer.size() == maxBufferSize) {
					 try {
//						 System.out.println("buffer is full, " + "Producer thread waiting for " + "consumer to take something from buffer"); 
						 buffer.wait(sendingTimeOut); 
					 }
					 catch (Exception ex) { 
						 System.out.println(ex.getMessage());
					 } 
				 }
				 
				 buffer.add(request);
				 buffer.notifyAll();
			}
			return true;
		}
		catch (Exception ex) {
			logger.error("Producer : fail to add request");
			logger.error(ex.getMessage());
			return false;
		}
	}
}
