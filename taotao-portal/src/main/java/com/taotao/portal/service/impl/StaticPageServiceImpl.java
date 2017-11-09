package com.taotao.portal.service.impl;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.portal.pojo.TbItemPortal;
import com.taotao.portal.service.ItemService;
import com.taotao.portal.service.StaticPageService;

import freemarker.template.Configuration;
import freemarker.template.Template;

@Service
public class StaticPageServiceImpl implements StaticPageService {
	
	@Autowired
	private ItemService itemService;
	@Autowired
	private FreeMarkerConfigurer freeMarkerConfigurer;
	
	@Value("${STATIC_PAGE_PATH}")
	private String STATIC_PAGE_PATH;

	@Override
	public TaotaoResult genItemHtml(Long itemId) throws Exception {
		//商品基本信息
		TbItemPortal item = itemService.getItemById(itemId);
		//商品描述
		String itemDesc = itemService.getItemDescById(itemId).getItemDesc();
		//规格参数
		String itemParam = itemService.getItemParamById(itemId);
		//生成静态页面
		//通过freeMarkerConfigurer 获取configuration对象
		Configuration configuration = freeMarkerConfigurer.getConfiguration();
		//告诉configuration模板文件在哪 并获取模板对象
		Template template = configuration.getTemplate("item.ftl");
		//创建一个数据集
		Map<String, Object> map = new HashMap<>();
		//向数据集中添加属性
		map.put("item", item);
		map.put("itemDesc", itemDesc);
		map.put("itemParam", itemParam);
		//创建一个Writer对象
		Writer out = new FileWriter(STATIC_PAGE_PATH + itemId + ".html");
		//生成静态文件
		template.process(map, out);
		//关闭文件输出流
		out.flush();
		out.close();
		//返回成功
		return TaotaoResult.ok();
	}
	
	

}

