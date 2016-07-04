package com.example.controllerTest;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.sql.DataSource;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;

import com.example.DemoApplication;
import com.example.MockBeansConfig;
import com.example.entity.Author;
import com.example.entity.Book;
import com.example.repository.BookRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {DemoApplication.class,MockBeansConfig.class}) // 找到spring的配置，以及Mock的Repository
@WebIntegrationTest("server.port:0")
public class BookTest {
	public static final int ENTITY_COUNT = 1;
	@Autowired
	private WebApplicationContext context;
	@Autowired
	private DataSource ds;
	@Autowired
	private BookRepository repository;
	@Value("${local.server.port}")
	private int port;
	private MockMvc mockMvc;
	private RestTemplate restTemplate = new TestRestTemplate();
	private static boolean loadDataFixtures = true;

	@Before
	public void setupMockMvcAndInitData() {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
	}

	@Before
	public void loadDataFixtures() {
		if (loadDataFixtures) {
			ResourceDatabasePopulator populator = new ResourceDatabasePopulator(
					context.getResource("classpath:/test-data.sql"));
			DatabasePopulatorUtils.execute(populator, ds);
			loadDataFixtures = false;
		}
	}
	//包含了import.sql和加载的测试test-data.sql中的数据
	@Test
	public void contextLoads() {
		assertEquals(ENTITY_COUNT, repository.count());
	}

	@Test
	public void webappBookSaveApi() throws Exception {
		Author author=new Author();
		author.setName("author");
		Book requestBook = new Book("isbn", "bookName", author, "remark");
		HttpHeaders headers = new HttpHeaders();
		MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
		headers.setContentType(type);
		headers.add("Accept", MediaType.APPLICATION_JSON.toString());
		ObjectMapper mapper = new ObjectMapper();
		mapper.findAndRegisterModules();
		String json=mapper.writeValueAsString(requestBook);
		HttpEntity<String> formEntity = new HttpEntity<String>(json, headers);
		ResponseEntity<String> response = restTemplate
				.postForEntity("http://localhost:" + port + "/springboot/books/save", formEntity, String.class);
		System.out.println(response.getBody());
		Assert.assertNotNull(response.getBody());
	}

	@Test
	public void webappBookIsbnApi() {
		Book book = restTemplate
				.getForObject("http://localhost:" + port + "/springboot/books/getbook/isbn1", Book.class);
		Assert.assertNotNull(book);
	}

	@Test
	public void webappGetBookApi() throws Exception {
		mockMvc.perform(get("/books/getbook/isbn")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8.toString()))
				.andExpect(content().string(containsString("isbn"))).andDo(MockMvcResultHandlers.print());
				//.andExpect(jsonPath("$.isbn").value("isbn"));
	}
}
