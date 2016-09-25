# SmsSender

SmsSender是一个配置化的短信发送工具类，它具备同步和异步两种发送方式分别适用于触发式短信和批量短信发送。

### 项目结构
1. smgsender : 短信发送器
2. numberfilter : 短信号码过滤器
3. logger : 日志打印接口
4. factory : 发送的初始化和配置加载
5. concurrency : 多线程控制
