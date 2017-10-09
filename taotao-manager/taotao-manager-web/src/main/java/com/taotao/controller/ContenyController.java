package com.taotao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbContent;
import com.taotao.service.ContentService;

@Controller
@RequestMapping("/content")
public class ContenyController {
	@Autowired
	private ContentService contentService;
	
	@RequestMapping("/save")
	@ResponseBody
	public TaotaoResult insertContent(TbContent content){
		return  contentService.insertContent(content);
	}
	@RequestMapping("/query/list")
	@ResponseBody
	public EasyUIDataGridResult getList(Long categoryId ,Integer page,Integer rows){
		return  contentService.getContentList(categoryId,page,rows);
	}
}
