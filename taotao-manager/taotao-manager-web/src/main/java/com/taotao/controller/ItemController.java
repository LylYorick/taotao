package com.taotao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbItem;
import com.taotao.service.ItemService;

@Controller
public class ItemController {
	
	@Autowired
	private ItemService itemService;
	
	@RequestMapping("/item/{itemId}")
	@ResponseBody
	private TbItem geTbItemById(@PathVariable Long itemId){
		return itemService.getItemById(itemId);
	}
	
	@RequestMapping("/item/list")
	@ResponseBody
	private EasyUIDataGridResult getItemList(Integer page,Integer rows){
		return itemService.getItemList(page, rows);
	}
	@RequestMapping("/item/save")
	@ResponseBody
	private TaotaoResult createItem(TbItem item,String desc,String itemParams){
		return itemService.createItem(item, desc,itemParams);
	}
	@RequestMapping("/item/page/{itemId}")
	public String showItemParam(@PathVariable Long itemId, Model model) {
		String html = itemService.getItemParamHtml(itemId);
		model.addAttribute("myhtml", html);
		return"itemparam";
	}

}
