package com.example.controller;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UserController {

  //@Autowired
 // UserDetailsServiceDAO userDetailsServiceDAO;

  //handle when logged user go to login page
  @RequestMapping("/login")
  public String login(){
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    if(auth instanceof AnonymousAuthenticationToken){
      return "login";
    }else{
      return "index"; 
    }
  }

  /*@RequestMapping(value = "/registration", method = RequestMethod.POST)
  public String registration(User newUser){
    try {
      if(userDetailsServiceDAO.loadUserEntityByUsername(newUser.getUsername()) != null){
        return "redirect:" + "/login?registration&error";
      }else{
        userDetailsServiceDAO.saveUser(newUser);
        return "redirect:" + "/login?registration&success";
      }
    }catch (Exception e){
      e.printStackTrace();
      return "redirect:" + "/login?registration&errorServer";
    }*/

}
