package com.example.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.entity.Apple;
@Controller
@RequestMapping("/testContentType")
public class RequestContentTypeController {
	/**
	 * headers表示服务端只对请求头是headers的类型进行处理。
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "ContentType", method = RequestMethod.GET,headers = "Content-Type=text/html;charset=UTF-8") 
	public  String showData(HttpServletRequest request){
		System.out.println("contentType: "+request.getContentType());
		return "testAny";
	}
	/**
	 * 通过@RequestBody将JSON数据组装成实体对象
	 * @param apple
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "ContentTypeForJson", method = RequestMethod.POST,headers = "Content-Type=application/json;charset=UTF-8")
	public  String dealWithJson(@RequestBody Apple apple, HttpServletRequest request){
		System.out.println("contentType: "+request.getContentType());
		System.out.println(apple.getColor()+" : "+apple.getName());
		return "testAny";
	}
	/**
	 * 响应的contentType
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value="contentTypeForResponse")  
	public void contentTypeForResponse(HttpServletResponse response) throws IOException {  
	    //①表示响应的内容区数据的媒体类型为html格式，且编码为utf-8(客户端应该以utf-8解码)  
	    response.setContentType("text/html;charset=utf-8");  
	    //②写出响应体内容  
	    response.getWriter().write("<font style='color:red'>hello</font>");  
	} 
	/**
	 * 处理contentType为xml的类型
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "ContentTypeForXml", headers = "Accept=application/xml")  
	public void contentTypeForXml(HttpServletResponse response) throws IOException {  
	    //①表示响应的内容区数据的媒体类型为xml格式，且编码为utf-8(客户端应该以utf-8解码)  
	    response.setContentType("application/xml;charset=utf-8");  
	    //②写出响应体内容  
	    String xmlData = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";  
	    xmlData += "<user><username>zhang</username><password>123</password></user>";  
	    response.getWriter().write(xmlData);  
	} 
	/**
	 * 验证请求contentType不匹配情况
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "ContentTypeForCheck", headers = "ContentType=application/xml")  
	public void check(HttpServletResponse response) throws IOException {  
	    //①表示响应的内容区数据的媒体类型为xml格式，且编码为utf-8(客户端应该以utf-8解码)  
	    response.setContentType("application/xml;charset=utf-8");  
	    //②写出响应体内容  
	    String xmlData = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";  
	    xmlData += "<user><username>zhang</username><password>123</password></user>";  
	    response.getWriter().write(xmlData);  
	} 
	
	/**
	 * 表示服务器可接受的contentType类型，通过request的contentType头匹配，相当于上面的headers="ContentType=text/html"
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "ContentTypeForConsumes", consumes={"text/html"})  
	public void consumes(HttpServletResponse response) throws IOException {  
	    //①表示响应的内容区数据的媒体类型为text/html格式，且编码为utf-8(客户端应该以utf-8解码)  
	   // response.setContentType("text/hxml;charset=utf-8");  
	    //②写出响应体内容  
	    String htmlData = "<font style='color:red'>hello</font>";  
	    response.getWriter().write(htmlData);  
	} 
	/**
	 * 表示功能处理方法将产生text/html类型的数据，是和request头中的Accept进行匹配的
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "ContentTypeForProduces", produces={"text/html"})  
	public void produces(HttpServletResponse response) throws IOException {  
	    //①表示响应的内容区数据的媒体类型为text/html格式，且编码为utf-8(客户端应该以utf-8解码)  
	    response.setContentType("text/hxml;charset=utf-8");  
	    //②写出响应体内容  
	    String htmlData = "<font style='color:red'>hello</font>";  
	    response.getWriter().write(htmlData);  
	} 
}
