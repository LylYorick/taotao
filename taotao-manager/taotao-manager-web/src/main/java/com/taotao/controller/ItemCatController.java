package com.taotao.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.EasyUITreeNode;
import com.taotao.service.ItemCatService;

@Controller
@RequestMapping("item/cat")
public class ItemCatController {
	@Autowired
	private ItemCatService itemservice;
	
	@RequestMapping("/list")
	@ResponseBody
	public List<EasyUITreeNode> getItemCatList(@RequestParam(value="id",defaultValue="0")long parentId){
		return  itemservice.getItemCatList(parentId);
	}
	
}
