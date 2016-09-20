package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.redisCache.RedisUtil;
/**
 * 验证Redis cluster  spring data redis  spring  cache
 * @author xu
 *
 */
@RestController
@RequestMapping(value = "/test")
public class RedisCacheController {
	@Autowired
	private RedisUtil redisUtil;

	@RequestMapping(value = "cache", method = RequestMethod.GET)
	@Cacheable(value="test")
	public String create() {
		try {
			redisUtil.set("test", "测试");
			System.out.println("进入了方法");
			String string= redisUtil.get("test").toString();
			return string;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping(value = "tttt", method = RequestMethod.GET)
	@Cacheable(value="test")
	public String test() {
		System.out.println(redisUtil);
		return "124";
	}
}
