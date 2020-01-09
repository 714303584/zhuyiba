package com.shorturl.server.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.shorturl.server.model.ZUsers;

@Controller
@RequestMapping("/user")
public class UserPublicController extends PublicController  {
	 @RequestMapping(value = "/show")
	 public ModelAndView index(ModelAndView modelAndView) {
	        modelAndView.setViewName("user/show");
	        
	        ZUsers user = new ZUsers();
	        
	        user.setId(1L);
	        user.setNickName("zhuss");
	        modelAndView.addObject("user", user);
	        return modelAndView;
	    }
}
