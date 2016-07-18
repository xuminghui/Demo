package com.example.controller;

import java.util.List;
import java.util.concurrent.Callable;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Book;
import com.example.service.BookService;

@RestController
@RequestMapping(value="/books")
public class BookController {
	@Autowired
	private BookService bookService;
	// 此处是在禁用了CSRF的功能后调试成功的，在开启情况下如何通过验证，有待于进一步研究
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public Book create(@RequestBody Book book) {
		//bookRepository.save(book);
		return book;
	}


	@RequestMapping(value = "{id}", method = RequestMethod.GET)
	public Book getBook(@PathVariable("id") long id) {
		return bookService.getBook(id);
	}

	@RequestMapping(value = "/books/session", method = RequestMethod.GET)
	public String getSessionId(HttpServletRequest request) {
		return request.getSession().getId();
	}
	@RequestMapping(value = "bookList", method = RequestMethod.GET)
	public List<Book> findBookList(@RequestParam MultiValueMap<String,String> params) {
		Page<Book> pageBooks=bookService.findBooks(Integer.valueOf(params.get("pageNum").get(0)), params.get("orderBy").get(0));
		System.out.println(pageBooks);
		return pageBooks.getContent();
	}
	
	@RequestMapping(value = "async", method = RequestMethod.GET)
    @ResponseBody
    public Callable<String> callable() {
		System.out.println("222222222222");
        // 这么做的好处避免web server的连接池被长期占用而引起性能问题，
        // 调用后生成一个非web的服务线程来处理，增加web服务器的吞吐量。
        return new Callable<String>() {
            @Override
            public String call() throws Exception {
                Thread.sleep(3 * 1000L);
                return "小单 - " + System.currentTimeMillis();
            }
        };
    }
}
