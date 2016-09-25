package main.resources.david.tang.sms.smgsender.yunpian;

public class Configure {

	//修改为您的apikey.apikey可在官网（http://www.yuanpian.com)登录后获取
		public static final String APIKEY = "b1e4e308af4f31f9b4d8516ef12da1e9";
	    
	    //查账户信息URL
		public static final String URI_GET_USER_INFO = "https://sms.yunpian.com/v1/user/get.json";

	    //智能匹配模版发送接口
		public static final String URI_SEND_SMS = "https://sms.yunpian.com/v1/sms/send.json";

	    //模板发送接口
		public static final String URI_TPL_SEND_SMS = "https://sms.yunpian.com/v1/sms/tpl_send.json";
		
		//获取状态报告
		public static final String URI_PULL_STATUS = "https://sms.yunpian.com/v1/sms/pull_status.json"; 

	    //发送语音验证码接口
		public static final String URI_SEND_VOICE = "https://voice.yunpian.com/v1/voice/send.json";

	    //编码格式。发送编码格式统一用UTF-8
		public static final String ENCODING = "UTF-8";
		
		//短信前置签名
		public static final String PRE_SIGN = "【长投网】";

}
