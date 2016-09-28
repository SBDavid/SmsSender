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
### 配置文件说明
```xml
<smsfactory>
	<!-- 整个配置文件分为两部分 1)异步短信发送资源配置 2)发信平台的配置 -->
	
    <!-- 异步短信发送资源配置 -->
    <asynchronous>
        <!-- 并行短信发送线程的数量 -->
        <ComsumerAmount>5</ComsumerAmount>
        <!-- 发信请求的等待缓存容量 -->
        <BufferSize>50</BufferSize>
        <!-- 等待缓存的超时设置，如果buffer始终为满的状态，则等待等待一定时间后返回错误消息 -->
        <SendingTimeOut>10000</SendingTimeOut>
    </asynchronous>
    
	<!-- 发信平台的配置，可以同时配置多个发信平台，调用init方法是写明使用哪一个平台 -->
	<controllers>
	    <!-- 短信发送控制器名称，name的值在构造方法中指定 -->
		<controller name="test">
			<!-- 短信发送控制器的实现类 -->
			<sender type="main.resources.david.tang.sms.smgsender.test.TestSender">	
			</sender>
			<!-- 短信号码过滤器的实现类，如果不想使用任何过滤器，则可以删除filter节点 -->
			<filter type="main.resources.david.tang.sms.numberfilter.WhiteListFilter">
				<numbers>
					<number>18121022433</number>
				</numbers>
			</filter>
		</controller>
		<controller name="normal">
			<sender type="main.resources.david.tang.sms.smgsender.yunpian.YunPianSmsHandlerImpl">
				<params>
					<param key="apikey" value="b1e4e308af4f31f9b4d8516ef12da1e9"/>
				</params>
			</sender>
			<filter type="main.resources.david.tang.sms.numberfilter.BlackListFilter">
				<numbers>
					<number>18121022432</number>
				</numbers>
			</filter>
		</controller>
	</controllers>
</smsfactory>
```
### 项目结构
1. smgsender : 短信发送器
2. numberfilter : 短信号码过滤器
3. logger : 日志打印接口
4. factory : 发送的初始化和配置加载
5. concurrency : 多线程控制
