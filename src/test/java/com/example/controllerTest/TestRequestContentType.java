package com.example.controllerTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

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
	public void testHttpRequest() throws IOException, URISyntaxException{
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
		System.out.println(response.getStatusCode());  
	}

}
