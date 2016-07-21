package com.example.controllerTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.AsyncRestTemplate;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;

import com.example.DemoApplication;
import com.example.MockBeansConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = { DemoApplication.class, MockBeansConfig.class }) // 找到spring的配置，以及Mock的Repository
@WebIntegrationTest("server.port:0")
public class TestRequestContentType {
	@Autowired
	private WebApplicationContext context;
	@Value("${local.server.port}")
	private int port;
	private MockMvc mockMvc;
	private RestTemplate restTemplate = new TestRestTemplate();
	private AsyncRestTemplate asyncTemplate = new AsyncRestTemplate();

	@Before
	public void before() {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
	}

	@Test
	public void test1() throws Exception {
		mockMvc.perform(get("/testContentType/ContentType")).andDo(MockMvcResultHandlers.print());
	}
	//利用SPRING提供的HTTP 客户端API
	@Test
	public void testHttpRequestForTextHtml() throws IOException, URISyntaxException{
		//请求的地址  
		String url = "http://localhost:"+port+"/springboot/testContentType/ContentType";  
		//①创建Http Request(内部使用HttpURLConnection)  
		ClientHttpRequest request = new SimpleClientHttpRequestFactory().createRequest(new URI(url), HttpMethod.GET);  
		//②设置请求头的内容类型头和内容编码（GBK）  
		request.getHeaders().set("Content-Type", "text/html;charset=utf-8");  
		/*
		//③以GBK编码写出请求内容体  
		String jsonData = "{\"username\":\"zhang\", \"password\":\"123\"}";  
		request.getBody().write(jsonData.getBytes("gbk"));  */
		//④发送请求并得到响应  
		ClientHttpResponse response = request.execute(); 
		//获取响应体的编码方式
		Charset charset = response.getHeaders().getContentType().getCharSet();
		System.out.println("charset: "+charset.name());
		System.out.println(response.getStatusCode());  
	}
	//利用SPRING提供的HTTP 客户端API
		@Test
		public void testHttpRequestForJson() throws IOException, URISyntaxException{
			//请求的地址  
			String url = "http://localhost:"+port+"/springboot/testContentType/ContentTypeForJson";  
			//①创建Http Request(内部使用HttpURLConnection)  
			ClientHttpRequest request = new SimpleClientHttpRequestFactory().createRequest(new URI(url), HttpMethod.POST);  
			//②设置请求头的内容类型头和内容编码（UTF-8）  
			request.getHeaders().set("Content-Type", "application/json;charset=utf-8");  
			
			//③以GBK编码写出请求内容体  
			String jsonData = "{\"name\":\"zhang\", \"color\":\"123\"}";  
			request.getBody().write(jsonData.getBytes("utf-8"));
			//④发送请求并得到响应  
			ClientHttpResponse response = request.execute();  
			System.out.println(response.getStatusCode());  
		}
		/**
		 * 测试resonse的ContentType
		 * @throws IOException
		 * @throws URISyntaxException
		 */
		@Test
		public void testHttpForResponseContentType() throws IOException, URISyntaxException{
			//请求的地址  
			String url = "http://localhost:"+port+"/springboot/testContentType/contentTypeForResponse";  
			//①创建Http Request(内部使用HttpURLConnection)  
			ClientHttpRequest request = new SimpleClientHttpRequestFactory().createRequest(new URI(url), HttpMethod.GET);  
			//②设置请求头的内容类型头和内容编码（UTF-8）  
			request.getHeaders().set("Content-Type", "text/html;charset=utf-8");  
			//④发送请求并得到响应  
			ClientHttpResponse response = request.execute();  
			//获取响应体的编码方式
			Charset charset = response.getHeaders().getContentType().getCharSet();
			
			/*String body=readStream(response.getBody());*/
			InputStream is = response.getBody();
			//获取响应内容的长度
		    byte bytes[] = new byte[(int)response.getHeaders().getContentLength()];  
		    is.read(bytes);  
		    String jsonData = new String(bytes, charset);  
		    System.out.println("charset : " + charset + ", json data : " + jsonData);  
			System.out.println(response.getStatusCode());  
		}
		@Test
		public void testHttpForApplicationXml() throws IOException, URISyntaxException{
			//请求的地址  
			String url = "http://localhost:"+port+"/springboot/testContentType/ContentTypeForXml";  
			//①创建Http Request(内部使用HttpURLConnection)  
			ClientHttpRequest request = new SimpleClientHttpRequestFactory().createRequest(new URI(url), HttpMethod.POST);  
			//设置客户端可接受的媒体类型（即需要什么类型的响应体数据）
			 request.getHeaders().set("Accept", "application/xml");  
			//④发送请求并得到响应  
			ClientHttpResponse response = request.execute();  
			//获取响应体的编码方式
			Charset charset = response.getHeaders().getContentType().getCharSet();
			
			/*String body=readStream(response.getBody());*/
			InputStream is = response.getBody();
			//获取响应内容的长度
		    byte bytes[] = new byte[(int)response.getHeaders().getContentLength()];  
		    is.read(bytes);  
		    String jsonData = new String(bytes, charset);  
		    System.out.println("charset : " + charset + ", XML data : " + jsonData);  
			System.out.println(response.getStatusCode());  
		}
		//验证ContentType不匹配情况
		@Test
		public void testHttpCheck() throws IOException, URISyntaxException{
			//请求的地址  
			String url = "http://localhost:"+port+"/springboot/testContentType/ContentTypeForCheck";  
			//①创建Http Request(内部使用HttpURLConnection)  
			ClientHttpRequest request = new SimpleClientHttpRequestFactory().createRequest(new URI(url), HttpMethod.POST);  
			//设置客户端可接受的媒体类型（即需要什么类型的响应体数据）
			 request.getHeaders().set("Content-Type", "text/html");  
			//④发送请求并得到响应  
			ClientHttpResponse response = request.execute();  
			System.out.println(response.getStatusText());
		}
		//测试consumes：服务端可消费的MIME类型
		@Test
		public void testConsumes() throws IOException, URISyntaxException{
			//请求的地址  
			String url = "http://localhost:"+port+"/springboot/testContentType/ContentTypeForConsumes";  
			//①创建Http Request(内部使用HttpURLConnection)  
			ClientHttpRequest request = new SimpleClientHttpRequestFactory().createRequest(new URI(url), HttpMethod.POST);  
			//设置客户端可接受的媒体类型（即需要什么类型的响应体数据） 注意写法是Content-Type
			 request.getHeaders().set("Content-Type", "text/html");
			//④发送请求并得到响应  
			ClientHttpResponse response = request.execute();  
			System.out.println(response.getStatusText());
		}
		//测试produces客户端可接受的MIME类型 ，根据request头的Accept匹配
		@Test
		public void testProduces() throws IOException, URISyntaxException{
			//请求的地址  
			String url = "http://localhost:"+port+"/springboot/testContentType/ContentTypeForProduces";  
			//①创建Http Request(内部使用HttpURLConnection)  
			ClientHttpRequest request = new SimpleClientHttpRequestFactory().createRequest(new URI(url), HttpMethod.GET);  
			//设置客户端可接受的媒体类型（即需要什么类型的响应体数据） 注意写法是Content-Type
			 request.getHeaders().set("Accept", "text/html");
			//④发送请求并得到响应  
			ClientHttpResponse response = request.execute();  
			System.out.println(response.getStatusText());
		}
		
		private String readStream(InputStream inputStream) throws IOException{
			ByteArrayOutputStream outSteam = new ByteArrayOutputStream();  
			byte[] buffer=new byte[1024];
			int len=-1;
			while((len=inputStream.read(buffer))!=-1){
				outSteam.write(buffer, 0, len);
			}
			outSteam.close();  
			inputStream.close();  
		    byte[] bytes=outSteam.toByteArray();
		    String content=new String(bytes,"UTF-8");
		    return content;
		}

}
