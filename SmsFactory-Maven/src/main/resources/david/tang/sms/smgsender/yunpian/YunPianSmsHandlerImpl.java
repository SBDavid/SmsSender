package main.resources.david.tang.sms.smgsender.yunpian;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import main.resources.david.tang.sms.smgsender.SmsSenderBase;
import net.sf.json.JSONObject;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class YunPianSmsHandlerImpl extends SmsSenderBase{
	
	public YunPianSmsHandlerImpl(){}
	
	private static class YunPianSmsHandlerImplHolder{
		private static YunPianSmsHandlerImpl instance = new YunPianSmsHandlerImpl();
	}
	
	public static YunPianSmsHandlerImpl getInstance() {
		return YunPianSmsHandlerImplHolder.instance;
	}
	
	@Override
	public synchronized boolean sendSms(String text, String mobile) {
		try {
	        Map<String, String> params = new HashMap<String, String>();
	        params.put("apikey", Configure.APIKEY);
	        params.put("text", text);
	        params.put("mobile", mobile);
	        String returnResult = post(Configure.URI_SEND_SMS, params);
	        JSONObject jsonobject = JSONObject.fromObject(returnResult);
	        
	        if (jsonobject.get("code").equals(ReturnFlag.SUCCESS)) {
	        	logger.info(">>>>>>成功发送手机短信，手机号码为:" + mobile + ", 信息内容为: " + text);
	        	return true;
	        }
	        else {
	        	logger.info("=======发送手机短信失败，手机号码为:" + mobile + ", 信息内容为:  " + text + ", msg:" + jsonobject.get("msg") + ", detail:" + jsonobject.get("detail"));
	        	return false;
	        }
		}
		catch(Exception ex) {
			logger.info("=======发送手机短信失败，手机号码为:" + mobile + ", 信息内容为: " + text);
			logger.info(ex.getMessage());
			return false;
		}
    }
	
	public synchronized String post(String url, Map<String, String> paramsMap) {
		
        CloseableHttpClient client = HttpClients.createDefault();
        String responseText = "";
        CloseableHttpResponse response = null;
        try {
            HttpPost method = new HttpPost(url);
            if (paramsMap != null) {
                List<NameValuePair> paramList = new ArrayList<NameValuePair>();
                for (Map.Entry<String, String> param : paramsMap.entrySet()) {
                    NameValuePair pair = new BasicNameValuePair(param.getKey(), param.getValue());
                                        
                    paramList.add(pair);
                }
                method.setEntity(new UrlEncodedFormEntity(paramList, Configure.ENCODING));
            }
            response = client.execute(method);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                responseText = EntityUtils.toString(entity);
            }
        } catch(Exception e){
			logger.info(e.getMessage());
			return null;
		}
        finally {
            try {
                response.close();
            } catch (Exception e) {
            	logger.info(e.getMessage());
            }
        }
        return responseText;
    }

}
