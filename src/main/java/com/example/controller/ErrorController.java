package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
public class ErrorController {

		@RequestMapping("/Myerror")
		public String error(){
			return "error";
		}
		@RequestMapping("/test/testError")
		public String exception() throws Exception{
			int i=0;
			if(i==0)
				throw new Exception();
			return "error";
		}
}

/*@Controller  
public class ErrorHandleController implements ErrorController {  
    *//** 
     * @return 
     * @see org.springframework.boot.autoconfigure.web.ErrorController#getErrorPath() 
     *//*  
    @Override  
    public String getErrorPath() {  
        return "/screen/error";  
    }  
       
    @RequestMapping  
    public String errorHandle(){  
        return getErrorPath();  
    }  
}  */