package demo;

import sms.factory.*;

public class AsynchronousDemo {
    public static void main(String[] args) {
        try {
            AsynchronousFactory.init("test", "smsfactory.xml", null);
            AsynchronousFactory.init("test", "smsfactory.xml", null);
            for (int i = 0; i < 1; i++) {
            	 AsynchronousFactory.send("type", "msgContent", "18121022433");
            }
            Thread.sleep(15000);
            AsynchronousFactory.stopGracefully();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        // prevent child thread be killed by the ending of main thread
        try {
            Thread.sleep(100000000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}