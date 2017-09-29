package com.taotao.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.EasyUITreeNode;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.service.ContentCatgoryService;

@Controller
@RequestMapping("/content/category")
public class ContentCatgoryController {
	@Autowired
	private ContentCatgoryService catgoryService;
	
	@RequestMapping("/list")
	@ResponseBody
	public List<EasyUITreeNode> getContentCatList(@RequestParam(value = "id",defaultValue = "0")Long parentId){
		return catgoryService.getContentCatList(parentId);
		
	}
	@RequestMapping("/create")
	@ResponseBody
	public TaotaoResult createNode(long parentId,String name){
		return  catgoryService.insertCatgory(parentId, name);
	}
	@RequestMapping("/update")
	@ResponseBody
	public TaotaoResult updateNode(long id,String name){
		return  catgoryService.updateCatgory(id, name);
	}
	@RequestMapping("/delete")
	@ResponseBody
	public TaotaoResult deleteNode(long id){
		return  catgoryService.deleteCatgory(id);
	}
}
