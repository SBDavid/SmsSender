package main.resources.david.tang.sms.smgsender.yunpian;

public class Configure {

	//�޸�Ϊ����apikey.apikey���ڹ�����http://www.yuanpian.com)��¼���ȡ
		public static final String APIKEY = "b1e4e308af4f31f9b4d8516ef12da1e9";
	    
	    //���˻���ϢURL
		public static final String URI_GET_USER_INFO = "https://sms.yunpian.com/v1/user/get.json";

	    //����ƥ��ģ�淢�ͽӿ�
		public static final String URI_SEND_SMS = "https://sms.yunpian.com/v1/sms/send.json";

	    //ģ�巢�ͽӿ�
		public static final String URI_TPL_SEND_SMS = "https://sms.yunpian.com/v1/sms/tpl_send.json";
		
		//��ȡ״̬����
		public static final String URI_PULL_STATUS = "https://sms.yunpian.com/v1/sms/pull_status.json"; 

	    //����������֤��ӿ�
		public static final String URI_SEND_VOICE = "https://voice.yunpian.com/v1/voice/send.json";

	    //�����ʽ�����ͱ����ʽͳһ��UTF-8
		public static final String ENCODING = "UTF-8";
		
		//����ǰ��ǩ��
		public static final String PRE_SIGN = "����Ͷ����";

}
