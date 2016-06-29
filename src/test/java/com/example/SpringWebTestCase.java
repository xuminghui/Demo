package com.example;

import java.net.URI;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.xml.xpath.XPathExpressionException;

import org.hamcrest.Matcher;
import org.springframework.http.HttpMethod;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultHandler;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.ContentResultMatchers;
import org.springframework.test.web.servlet.result.CookieResultMatchers;
import org.springframework.test.web.servlet.result.FlashAttributeResultMatchers;
import org.springframework.test.web.servlet.result.HandlerResultMatchers;
import org.springframework.test.web.servlet.result.HeaderResultMatchers;
import org.springframework.test.web.servlet.result.JsonPathResultMatchers;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.result.ModelResultMatchers;
import org.springframework.test.web.servlet.result.RequestResultMatchers;
import org.springframework.test.web.servlet.result.StatusResultMatchers;
import org.springframework.test.web.servlet.result.ViewResultMatchers;
import org.springframework.test.web.servlet.result.XpathResultMatchers;
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.setup.StandaloneMockMvcBuilder;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;

public class SpringWebTestCase{  
      
    public static MockHttpServletRequestBuilder get(String urlTemplate, Object... urlVariables) {  
        return MockMvcRequestBuilders.get(urlTemplate, urlVariables);  
    }  
  
    public static MockHttpServletRequestBuilder get(URI uri) {  
        return MockMvcRequestBuilders.get(uri);  
    }  
  
    public static MockHttpServletRequestBuilder post(String urlTemplate, Object... urlVariables) {  
        return MockMvcRequestBuilders.post(urlTemplate, urlVariables);  
    }  
  
    public static MockHttpServletRequestBuilder post(URI uri) {  
        return MockMvcRequestBuilders.post(uri);  
    }  
      
    public static MockHttpServletRequestBuilder request(HttpMethod httpMethod, String urlTemplate, Object... urlVariables) {  
        return MockMvcRequestBuilders.request(httpMethod, urlTemplate, urlVariables);  
    }  
  
    public static MockHttpServletRequestBuilder request(HttpMethod httpMethod, URI uri) {  
        return MockMvcRequestBuilders.request(httpMethod, uri);  
    }  
  
    public static MockMultipartHttpServletRequestBuilder fileUpload(String urlTemplate, Object... urlVariables) {  
        return MockMvcRequestBuilders.fileUpload(urlTemplate, urlVariables);  
    }  
  
    public static MockMultipartHttpServletRequestBuilder fileUpload(URI uri) {  
        return MockMvcRequestBuilders.fileUpload(uri);  
    }  
  
    public static RequestBuilder asyncDispatch(final MvcResult mvcResult) {  
        return MockMvcRequestBuilders.asyncDispatch(mvcResult);  
    }  
  
    public static ResultHandler print() {  
        return MockMvcResultHandlers.print();  
    }  
      
    public static DefaultMockMvcBuilder webAppContextSetup(WebApplicationContext context) {  
        return MockMvcBuilders.webAppContextSetup(context);  
    }  
  
    public static StandaloneMockMvcBuilder standaloneSetup(Object... controllers) {  
        return MockMvcBuilders.standaloneSetup(controllers);  
    }  
      
    public static RequestResultMatchers request() {  
        return MockMvcResultMatchers.request();  
    }  
  
    public static HandlerResultMatchers handler() {  
        return MockMvcResultMatchers.handler();  
    }  
  
    public static ModelResultMatchers model() {  
        return MockMvcResultMatchers.model();  
    }  
  
    public static ViewResultMatchers view() {  
        return MockMvcResultMatchers.view();  
    }  
  
    public static FlashAttributeResultMatchers flash() {  
        return MockMvcResultMatchers.flash();  
    }  
  
    public static ResultMatcher forwardedUrl(final String expectedUrl) {  
        return MockMvcResultMatchers.forwardedUrl(expectedUrl);  
    }  
  
    public static ResultMatcher forwardedUrlPattern(final String urlPattern) {  
        return MockMvcResultMatchers.forwardedUrlPattern(urlPattern);  
    }  
  
    public static ResultMatcher redirectedUrl(final String expectedUrl) {  
        return MockMvcResultMatchers.redirectedUrl(expectedUrl);  
    }  
  
    public static ResultMatcher redirectedUrlPattern(final String expectedUrl) {  
        return MockMvcResultMatchers.redirectedUrlPattern(expectedUrl);  
    }  
  
    public static StatusResultMatchers status() {  
        return MockMvcResultMatchers.status();  
    }  
  
    public static HeaderResultMatchers header() {  
        return MockMvcResultMatchers.header();  
    }  
  
    public static ContentResultMatchers content() {  
        return MockMvcResultMatchers.content();  
    }  
  
    public static JsonPathResultMatchers jsonPath(String expression, Object ... args) {  
        return MockMvcResultMatchers.jsonPath(expression, args);  
    }  
  
    public static <T> ResultMatcher jsonPath(String expression, Matcher<T> matcher) {  
        return MockMvcResultMatchers.jsonPath(expression, matcher);  
    }  
  
    public static XpathResultMatchers xpath(String expression, Object... args) throws XPathExpressionException {  
        return MockMvcResultMatchers.xpath(expression, args);  
    }  
  
    public static XpathResultMatchers xpath(String expression, Map<String, String> namespaces, Object... args)  
            throws XPathExpressionException {  
  
        return MockMvcResultMatchers.xpath(expression, namespaces, args);  
    }  
  
    public static CookieResultMatchers cookie() {  
        return MockMvcResultMatchers.cookie();  
    }  
      
    public byte[] convertObjectToFormUrlEncodedBytes(Object object) {  
        ObjectMapper mapper = new ObjectMapper();  
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);  
        @SuppressWarnings("unchecked")  
        Map<String, Object> propertyValues = mapper.convertValue(object, Map.class);  
        Set<String> propertyNames = propertyValues.keySet();  
        Iterator<String> nameIter = propertyNames.iterator();  
  
        StringBuilder formUrlEncoded = new StringBuilder();  
  
        for (int index = 0; index < propertyNames.size(); index++) {  
            String currentKey = nameIter.next();  
            Object currentValue = propertyValues.get(currentKey);  
  
            formUrlEncoded.append(currentKey);  
            formUrlEncoded.append("=");  
            formUrlEncoded.append(currentValue);  
  
            if (nameIter.hasNext()) {  
                formUrlEncoded.append("&");  
            }  
        }  
  
        return formUrlEncoded.toString().getBytes();  
    }  
} 