package com.taotao.portal;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

public class TestHttpClient {
	@Test
	public void testHttpGet() throws Exception {
//		第一步：把HttpClient使用的jar包添加到工程中。
//		第二步：创建一个HttpClient的测试类
//		第三步：创建测试方法。
//		第四步：创建一个HttpClient对象
		CloseableHttpClient client = HttpClients.createDefault();
//		第五步：创建一个HttpGet对象，需要制定一个请求的url
		HttpGet get = new HttpGet("http://www.itheima.com");
//		第六步：执行请求。
		CloseableHttpResponse response = client.execute(get);
//		第七步：接收返回结果。HttpEntity对象。
		HttpEntity entity = response.getEntity();
//		第八步：取响应的内容。
		String html = EntityUtils.toString(entity,"utf-8");
		System.out.println(html);
//		第九步：关闭HttpResponse、HttpClient。
		response.close();
		client.close();
	}
	@Test
	public void testHttpPost() throws Exception {
//		第一步：创建一个httpClient对象
		CloseableHttpClient client = HttpClients.createDefault();
//		第二步：创建一个HttpPost对象。需要指定一个url
		HttpPost post = new HttpPost("http://localhost:8082/testPost.html");
//		第三步：创建一个list模拟表单，list中每个元素是一个NameValuePair对象
		List<NameValuePair> list = new  ArrayList<>();
		list.add(new BasicNameValuePair("name", "张三"));
		list.add(new BasicNameValuePair("pass", "123"));
//		第四步：需要把表单包装到Entity对象中。StringEntity
		StringEntity entity = new UrlEncodedFormEntity(list,"utf-8");
		post.setEntity(entity);
//		第五步：执行请求。
		CloseableHttpResponse response = client.execute(post);
//		第六步：接收返回结果
		HttpEntity result = response.getEntity();
		System.out.println("测试"+EntityUtils.toString(result));
//		第七步：关闭流。
		response.close();
		client.close();

	}
}
