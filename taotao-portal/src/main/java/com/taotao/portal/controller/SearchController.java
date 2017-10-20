package com.taotao.portal.controller;

import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.ExceptionUtil;
import com.taotao.portal.pojo.SearchResult;
import com.taotao.portal.service.SearchService;

@Controller
public class SearchController {
	@Autowired
	private SearchService searchService;
	
	@RequestMapping("/search")
	public String query(@RequestParam("q")String keyword,
			@RequestParam(defaultValue="1")Integer page,
			@RequestParam(defaultValue="30")Integer rows,Model model) {
		SearchResult result;
			try {
				keyword = new String(keyword.getBytes("iso8859-1"),"utf-8");
			} catch (UnsupportedEncodingException e) {
				keyword  = "";
				e.printStackTrace();
			}
			result = searchService.search(keyword, page, rows);
			model.addAttribute("totalPages", result.getPageCount());
			model.addAttribute("page", result.getCurPage());
			model.addAttribute("itemList", result.getItemList());
			return "search";
	}
}
