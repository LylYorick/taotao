package com.taotao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.HttpClientUtil;
import com.taotao.pojo.TbContent;
import com.taotao.service.ContentService;

@Controller
@RequestMapping("/content")
public class ContenyController {
	@Autowired
	private ContentService contentService;
	
	@Value("${REST_BASE_URL}")
	private String REST_BASE_URL;
	
	@Value("${REST_CONTENT_SYSNC_URL}")
	private String REST_CONTENT_SYSNC_URL;
	
	@RequestMapping("/save")
	@ResponseBody
	public TaotaoResult insertContent(TbContent content){
		TaotaoResult result = contentService.insertContent(content);
		//调用rest层的服务，同步缓存
		HttpClientUtil.doGet(REST_BASE_URL+REST_CONTENT_SYSNC_URL+content.getCategoryId());
		return  result;
	}
	@RequestMapping("/edit")
	@ResponseBody
	public TaotaoResult editContent(TbContent content){
		TaotaoResult result = contentService.editContent(content);
		//调用rest层的服务，同步缓存
		HttpClientUtil.doGet(REST_BASE_URL+REST_CONTENT_SYSNC_URL+content.getCategoryId());
		return  result;
	}
	
	@RequestMapping("/query/list")
	@ResponseBody
	public EasyUIDataGridResult getList(Long categoryId ,Integer page,Integer rows){
		return  contentService.getContentList(categoryId,page,rows);
	}
}
