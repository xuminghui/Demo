package com.example.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.redisCache.RedisUtil;

@RestController
@RequestMapping(value = "/test")
public class RedisCacheController {
	@Autowired
	private RedisUtil redisUtil;

	/*
	 * @Autowired private Validator validator;
	 */
	// 此处是在禁用了CSRF的功能后调试成功的，在开启情况下如何通过验证，有待于进一步研究
	@RequestMapping(value = "cache", method = RequestMethod.GET)
	//@Cacheable(value="test")
	public String create() {
		try {
			redisUtil.set("123", "测试");
			System.out.println("进入了方法");
			String string= redisUtil.get("123").toString();
			return string;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping(value = "tttt", method = RequestMethod.GET)
	//@Cacheable(value="adfafd")
	public String test() {
		System.out.println(redisUtil);
		return "124";
	}
}
