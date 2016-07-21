package com.example.controllerTest;

import java.io.IOException;
import java.io.InputStream;
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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.AsyncRestTemplate;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;

import com.example.DemoApplication;
import com.example.MockBeansConfig;
import com.example.entity.Apple;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = { DemoApplication.class, MockBeansConfig.class }) // 找到spring的配置，以及Mock的Repository
@WebIntegrationTest("server.port:0")
public class TestModelAttribute {
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
	public void testRedirect() throws IOException, URISyntaxException{
		//请求的地址  
				String url = "http://localhost:"+port+"/springboot/test/firstModelAttribute";  
				//①创建Http Request(内部使用HttpURLConnection)  
				ClientHttpRequest request = new SimpleClientHttpRequestFactory().createRequest(new URI(url), HttpMethod.POST);  
				//②设置请求头的内容类型头和内容编码（GBK）  
				request.getHeaders().set("Content-Type", "application/json;charset=utf-8");  
				Apple apple=new Apple();
				apple.setColor("color");
				apple.setName("name");
				ObjectMapper mapper = new ObjectMapper();
				mapper.findAndRegisterModules();
				String jsonData = mapper.writeValueAsString(apple);
				System.out.println(jsonData);
				request.getBody().write(jsonData.getBytes("utf-8"));
				//④发送请求并得到响应  
				ClientHttpResponse response = request.execute(); 
				InputStream is = response.getBody();
				//获取响应内容的长度
			    byte bytes[] = new byte[(int)response.getHeaders().getContentLength()];  
			    is.read(bytes);  
			    jsonData = new String(bytes);  
			    System.out.println("json data : " + jsonData);  
				System.out.println(response.getStatusCode());  
	}

}
