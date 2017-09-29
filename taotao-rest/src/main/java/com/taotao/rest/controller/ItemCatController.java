package com.taotao.rest.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.utils.JsonUtils;
import com.taotao.rest.pojo.ItemCatResult;
import com.taotao.rest.service.ItemCatService;

@Controller
@RequestMapping("/item/cat")
public class ItemCatController {
	
	@Autowired
	private ItemCatService itemCatService;
	
	@RequestMapping("/list")
	@ResponseBody
	public Object getItemCatList(String callback){
		ItemCatResult result  = itemCatService.getItemCatList();
		if (StringUtils.isBlank(callback)) {
			return result;
		}
		MappingJacksonValue jacksonValue = new MappingJacksonValue(result);
		jacksonValue.setJsonpFunction(callback);
		return jacksonValue;
	}
	
	@RequestMapping(value="/list2",produces=MediaType.APPLICATION_JSON_VALUE+";charset=utf-8")
	@ResponseBody
	public String getItemCatList2(String callback){
		ItemCatResult result  = itemCatService.getItemCatList();
		if (StringUtils.isBlank(callback)) {
			return JsonUtils.objectToJson(result);
		}
		String  callbackString = callback + "(" + JsonUtils.objectToJson(result) + ");";
		return callbackString;
	}
}
