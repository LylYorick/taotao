package com.taotao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.service.ItemParamService;

@Controller
@RequestMapping("/item/param")
public class ItemParamController {

	@Autowired
	private ItemParamService itemParamService;
	
	@RequestMapping("/query/itemcatid/{cid}")
	@ResponseBody
	public TaotaoResult getItemCatByCid(@PathVariable Long cid) {
		TaotaoResult result = itemParamService.getItemParamByCid(cid);
		return result;
	}
	
	@RequestMapping("/list")
	@ResponseBody
	public EasyUIDataGridResult getItemParamList(Integer page,Integer rows){
		return itemParamService.getItemParamList(page, rows);
	}
	@RequestMapping("/save/{cid}")
	@ResponseBody
	public TaotaoResult createItemParam(@PathVariable Long cid,String paramData){
		return itemParamService.createItemParam(cid, paramData);
	}
	@RequestMapping("/delete")
	@ResponseBody
	public TaotaoResult deleteItemParam(Long ids){
		return itemParamService.deleteItemParam(ids);
	}
}
