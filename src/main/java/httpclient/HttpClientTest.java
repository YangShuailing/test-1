package httpclient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.httpclient.NameValuePair;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class HttpClientTest {
	/*
	 * http://blog.csdn.net/mr_tank_/article/details/17454315
	 * POST ��ôʵ�֣�
	 */
	public static void main(String args[]) {
		  //����HttpClientBuilder
	    HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
	    //HttpClient
	    CloseableHttpClient closeableHttpClient = httpClientBuilder.build();

	    HttpGet httpGet = new HttpGet("http://www.gxnu.edu.cn/default.html");
	    System.out.println(httpGet.getRequestLine());
	    try {
	        //ִ��get����
	        HttpResponse httpResponse = closeableHttpClient.execute(httpGet);
	        //��ȡ��Ӧ��Ϣʵ��
	        HttpEntity entity = httpResponse.getEntity();
	        //��Ӧ״̬
	        System.out.println("status:" + httpResponse.getStatusLine());
	        //�ж���Ӧʵ���Ƿ�Ϊ��
	        if (entity != null) {
	            System.out.println("content Encoding:" + entity.getContentEncoding());
	            System.out.println("response content:" + EntityUtils.toString(entity));
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            //�ر������ͷ���Դ
	            closeableHttpClient.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	    
	    main1(args);
	    
	}

	//TODO
	public static void main1(String args[]) {
//	    //����HttpClientBuilder
//	    HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
//	    //HttpClient
//	    CloseableHttpClient closeableHttpClient = httpClientBuilder.build();
//	
//	    HttpPost httpPost = new HttpPost("http://tutor.sturgeon.mopaas.com/tutor/search");
//	    httpPost.setConfig(DEFAULT);
//	    // ������������
//	    List<NameValuePair> formparams = new ArrayList<NameValuePair>();
//	    formparams.add(new BasicNameValuePair("searchText", "Ӣ��"));
//	
//	    UrlEncodedFormEntity entity;
//	    try {
//	        entity = new UrlEncodedFormEntity(formparams, "UTF-8");
//	        httpPost.setEntity(entity);
//	
//	        HttpResponse httpResponse;
//	        //post����
//	        httpResponse = closeableHttpClient.execute(httpPost);
//	
//	        //getEntity()
//	        HttpEntity httpEntity = httpResponse.getEntity();
//	        if (httpEntity != null) {
//	            //��ӡ��Ӧ����
//	            System.out.println("response:" + EntityUtils.toString(httpEntity, "UTF-8"));
//	        }
//	        //�ͷ���Դ
//	        closeableHttpClient.close();
//	    } catch (Exception e) {
//	        e.printStackTrace();
//	    }
	}
	
}