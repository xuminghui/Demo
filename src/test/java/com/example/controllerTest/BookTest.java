package com.example.controllerTest;

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
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.client.AsyncRestTemplate;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;

import com.example.DemoApplication;
import com.example.MockBeansConfig;
import com.example.entity.Author;
import com.example.entity.Book;
import com.example.repository.BookRepository;
import com.example.util.JSONUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = { DemoApplication.class, MockBeansConfig.class }) // 找到spring的配置，以及Mock的Repository
@WebIntegrationTest("server.port:0")
public class BookTest {
	public static final int ENTITY_COUNT = 1;
	@Autowired
	private WebApplicationContext context;
	@Autowired
	private BookRepository repository;
	@Mock
	private Page<Book> pageBook;
	@Value("${local.server.port}")
	private int port;
	private MockMvc mockMvc;
	private RestTemplate restTemplate = new TestRestTemplate();
	private AsyncRestTemplate asyncTemplate = new AsyncRestTemplate();  
	//private static boolean loadDataFixtures = true;  装载特定的数据

	@Before
	public void initMocks() {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
		MockitoAnnotations.initMocks(this);
		List<Book> books = new ArrayList<Book>();
		for (int i = 0; i < 100; i++) {
			Book book = new Book("isbn" + i, "bookName" + i, new Author("authorName" + i), "remark" + i);
			book.setId(Long.valueOf(i));
			books.add(book);
		}
		Mockito.when(repository.findOne(Mockito.anyLong())).thenReturn(books.get(0));
		Mockito.when(pageBook.getContent()).thenReturn(books);
		Mockito.when(repository.findAll(Mockito.any(Pageable.class))).thenReturn(pageBook);
	}

	@Test
	public void webappBookSaveApi() throws Exception {
		Author author = new Author();
		author.setName("author");
		Book requestBook = new Book("isbn", "bookName123456345345", author, "remark");
		HttpHeaders headers = new HttpHeaders();
		MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
		headers.setContentType(type);
		headers.add("Accept", MediaType.APPLICATION_JSON.toString());
		ObjectMapper mapper = new ObjectMapper();
		mapper.findAndRegisterModules();
		String json = mapper.writeValueAsString(requestBook);
		HttpEntity<String> formEntity = new HttpEntity<String>(json, headers);
		ResponseEntity<String> response = restTemplate
				.postForEntity("http://localhost:" + port + "/springboot/books/save", formEntity, String.class);
		System.out.println(response.getBody()+" "+response.getStatusCode());
		Assert.assertNotNull(response.getBody());
	}

	@Test
	public void webappGetBookApiforTemplate() {
		Book book = restTemplate.getForObject("http://localhost:" + port + "/springboot/books/0",
				Book.class);
		Assert.assertNotNull(book);
		Assert.assertEquals(Long.valueOf(0), book.getId());
	}
	
	@Test
	public void testForAysncRestTemplate() {
		//调用完后立即返回（没有阻塞）  
	    ListenableFuture<ResponseEntity<String>> future = asyncTemplate.getForEntity("http://localhost:" + port + "/springboot/books/async", String.class);  
	    //设置异步回调  
	    future.addCallback(new ListenableFutureCallback<ResponseEntity<String>>() {  
	        @Override  
	        public void onSuccess(ResponseEntity<String> result) {  
	            System.out.println("======client get result : " + result.getBody());  
	        }  
	  
	        @Override  
	        public void onFailure(Throwable t) {  
	            System.out.println("======client failure : " + t);  
	        }  
	    });  
	    System.out.println("==no wait"); 
	    try {
			Thread.sleep(100000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void webappGetBookApi() throws Exception {
		MvcResult mvcResult=mockMvc.perform(get("/books/0")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8.toString())).andDo(MockMvcResultHandlers.print()).andExpect(jsonPath("$.id").value(0)).andReturn();
		String content = mvcResult.getResponse().getContentAsString();
		Book book = JSONUtil.JSONToObj(content,Book.class);
		System.out.println(book.getBookName());
		System.out.println(JSONUtil.objectToJson(book));
		JSON.toJSONStringWithDateFormat(date, "yyyy-MM-ddHH:mm:ss.SSS");
	}

	/**
	 * 通过Mockito进行的一个controller的测试，首先模拟组装数据，不依赖数据库的真实数据。
	 * 分页查询BookList
	 * @throws Exception
	 */
	@Test
	public void webappBookListApi() throws Exception {
		mockMvc.perform(get("/books/bookList").param("pageNum", "1").param("orderBy", "bookName"))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8.toString()))
				.andDo(MockMvcResultHandlers.print()).andExpect(jsonPath("$[0].isbn").isString()).andExpect(jsonPath("$").isArray());
		Mockito.verify(repository,Mockito.times(1)).findAll(Mockito.any(Pageable.class));
	}
	
	/*
	 * if (loadDataFixtures) { ResourceDatabasePopulator populator = new
	 * ResourceDatabasePopulator(
	 * context.getResource("classpath:/test-data.sql"));
	 * DatabasePopulatorUtils.execute(populator, ds); loadDataFixtures =
	 * false; }
	 */
}
