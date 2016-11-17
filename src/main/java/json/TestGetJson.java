package json;

import java.io.IOException;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class TestGetJson {

    public static void main(String[] args) {
        String url = "test.com";
        JSONObject params = new JSONObject();
        params.put("SRC_STM_CODE", "wsf");
        params.put("TENANT_ID", "123");
        params.put("NM", "����");
        params.put("BRTH_DT", "1983-01-20");
        params.put("GND_CODE", "1");
        JSONArray params2 = new JSONArray();
        JSONObject param3 = new JSONObject();
        param3.put("DOC_TP_CODE", "10100");
        param3.put("DOC_NBR", "100200198301202210");
        param3.put("DOC_CUST_NM", "test");
        params2.add(param3);
        params.put("DOCS", params2);
        String ret = doPost(url, params).toString();
        System.out.println(ret);
    }

    /**
     * httpClient��get����ʽ2
     *
     * @return
     * @throws Exception
     */
    public static String doGet(String url, String charset)
            throws Exception {
        /*
		 * ʹ�� GetMethod ������һ�� URL ��Ӧ����ҳ,ʵ�ֲ���: 1:����һ�� HttpClinet ����������Ӧ�Ĳ�����
		 * 2:����һ�� GetMethod ����������Ӧ�Ĳ����� 3:�� HttpClinet ���ɵĶ�����ִ�� GetMethod ���ɵ�Get
		 * ������ 4:������Ӧ״̬�롣 5:����Ӧ���������� HTTP ��Ӧ���ݡ� 6:�ͷ����ӡ�
		 */
		/* 1 ���� HttpClinet �������ò��� */
        HttpClient httpClient = new HttpClient();
        // ���� Http ���ӳ�ʱΪ2��
        httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(2000);
		/* 2 ���� GetMethod �������ò��� */
        GetMethod getMethod = new GetMethod(url);
        // ���� get ����ʱΪ2 ��
        getMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, 2000);
        // �����������Դ����õ���Ĭ�ϵ����Դ�����������
        getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());
        String response = "";
		/* 3 ִ�� HTTP GET ���� */
        try {
            int statusCode = httpClient.executeMethod(getMethod);
			/* 4 �жϷ��ʵ�״̬�� */
            if (statusCode != HttpStatus.SC_OK) {
                System.err.println("�������: " + getMethod.getStatusLine());
            }
			/* 5 ���� HTTP ��Ӧ���� */
            // HTTP��Ӧͷ����Ϣ
            Header[] headers = getMethod.getResponseHeaders();
            for (Header h : headers)
                System.out.println(h.getName() + "------------ " + h.getValue());
            // ��ȡ HTTP ��Ӧ����
            byte[] responseBody = getMethod.getResponseBody();// ��ȡΪ�ֽ�����
            response = new String(responseBody, charset);
            System.out.println("----------response:" + response);
            // ��ȡΪ InputStream������ҳ������������ʱ���Ƽ�ʹ��
            // InputStream response = getMethod.getResponseBodyAsStream();
        } catch (HttpException e) {
            // �����������쳣��������Э�鲻�Ի��߷��ص�����������
            System.out.println("���������URL!");
            e.printStackTrace();
        } catch (IOException e) {
            // ���������쳣
            System.out.println("���������쳣!");
            e.printStackTrace();
        } finally {
			/* 6 .�ͷ����� */
            getMethod.releaseConnection();
        }
        return response;
    }

    /**
     * post����
     *
     * @param url
     * @param json
     * @return
     */
    public static JSONObject doPost(String url, JSONObject json) {
        DefaultHttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(url);
        JSONObject response = null;
        try {
            StringEntity s = new StringEntity(json.toString());
            s.setContentEncoding("UTF-8");
            s.setContentType("application/json");//����json������Ҫ����contentType
            post.setEntity(s);
            HttpResponse res = client.execute(post);
            if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity entity = res.getEntity();
                String result = EntityUtils.toString(res.getEntity());// ����json��ʽ��
                response = JSONObject.fromObject(result);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return response;
    }


}
