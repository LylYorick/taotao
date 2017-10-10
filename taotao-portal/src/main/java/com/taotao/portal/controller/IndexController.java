package com.taotao.portal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.taotao.portal.service.ContentService;

@Controller
public class IndexController {
	@Autowired
	private ContentService contentService;
	
	@RequestMapping("/index")
	public String showIndex(Model model) {
		//获取大广告内容
		String json = contentService.getAd1List();
		//传递给页面
		model.addAttribute("ad1",json);
		return "index";
	}
	
	@RequestMapping("/testPost")
	public String postTest(String name , String pass){
		System.out.println(name);
		System.out.println(pass);
		return "ok";
	}
} 
