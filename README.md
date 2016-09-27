# SmsSender

SmsSender是一个配置化的短信发送工具类，它具备同步和异步两种发送方式分别适用于触发式短信和批量短信发送。

### QuickStart

#### 以同步方式发送
- 优点：可以及时得到消息是否成功的反馈
- 缺点：速度慢，如果一个方法内循环发送60条短信，每天耗时1S,那么一分钟后方法才能返回
- 使用范围：触发式短信，例如发送验证码短信
```java
package test.resources.david.tang.sms.factory.demo;

import main.resources.david.tang.sms.factory.SynchronousFactory;

public class SynchronousDemo {
	public static void main(String[] args) {
		try {
			SynchronousFactory.init("test", "smsfactory.xml", null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		SynchronousFactory.send("type", "msgContent", "18121022433");
	}
}

```

#### 以异步方式发送
- 优点：速度快，即使发送100条短信也能做到即使放回
- 缺点：无法即使得到发送的反馈，但是可以把发送结果写入数据库方便日后复查
- 使用方位：后台批量短信
```java
package test.resources.david.tang.sms.factory.demo;

import main.resources.david.tang.sms.factory.AsynchronousFactory;

public class AsynchronousDemo {
	public static void main(String[] args) {
		try {
			AsynchronousFactory.init("test", "smsfactory.xml", null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		AsynchronousFactory.send("type", "msgContent", "18121022433");
		
		// prevent child thread be killed by the ending of main thread
		try {
			Thread.sleep(1000000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
```

### 项目结构
1. smgsender : 短信发送器
2. numberfilter : 短信号码过滤器
3. logger : 日志打印接口
4. factory : 发送的初始化和配置加载
5. concurrency : 多线程控制
