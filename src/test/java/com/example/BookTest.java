package com.example;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

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
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;

import com.example.entity.Book;
import com.example.repository.BookRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = DemoApplication.class) // 找到spring
																	// context配置并加载
@WebIntegrationTest("server.port:0")
public class BookTest {
	public static final int ENTITY_COUNT = 100;
	@Autowired
	private WebApplicationContext context;
	@Autowired
	private BookRepository repository;
	@Value("${local.server.port}")
	private int port;
	private MockMvc mockMvc;
	private RestTemplate restTemplate = new TestRestTemplate();

	@Before
	public void setupMockMvcAndInitData() {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
		List<Book> list = new ArrayList<Book>();
		for (int i = 0; i < ENTITY_COUNT; i++) {
			Book book = new Book("978-3-16-148410-" + i, "bookName" + i, "author" + i,"remark"+i);
			list.add(book);
		}
		repository.save(list);
	}

	@Test
	public void contextLoads() {
		assertEquals(ENTITY_COUNT, repository.count());
	}

	@Test
	public void webappBookSaveApi() throws Exception{
		Book requestBook = new Book("isbn", "bookName", "author","remark");
		HttpHeaders headers = new HttpHeaders();
		MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
		headers.setContentType(type);
		headers.add("Accept", MediaType.APPLICATION_JSON.toString());
		ObjectMapper mapper = new ObjectMapper();  
        String json = mapper.writeValueAsString(requestBook); 
        System.out.println(json);
		HttpEntity<Book> formEntity = new HttpEntity<Book>(requestBook, headers);
		ResponseEntity<Book> response = restTemplate.postForEntity("http://localhost:" + port + "/springboot/books/save",
				formEntity, Book.class);
		Assert.assertNotNull(response.getBody());
	}

	@Test
	public void webappBookIsbnApi() {
		Book book = restTemplate.getForObject("http://localhost:" + port + "/springboot/books/getbook/978-1-78528-415-1",
				Book.class);
		Assert.assertNotNull(book);
	}

	@Test
	public void webappPublisherApi() throws Exception {
		mockMvc.perform(get("/books/getbook/978-1-78528-415-1")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8.toString()))
				.andExpect(content().string(containsString("978-1-78528-415-1"))).andDo(MockMvcResultHandlers.print()).andExpect(jsonPath("$.isbn").value("978-1-78528-415-1"));
	}
}
