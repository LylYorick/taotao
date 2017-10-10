package com.taotao.portal.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.HttpClientUtil;
import com.taotao.common.utils.JsonUtils;
import com.taotao.pojo.TbContent;
import com.taotao.portal.pojo.AdNode;
import com.taotao.portal.service.ContentService;

@Service
public class ContentServiceImpl implements ContentService{
	@Value("${REST_BASE_URL}")
	private String REST_BASE_URL;
	@Value("${REST_CONTENT_URL}")
	private String REST_CONTENT_URL;
	@Value("${REST_CONTENT_AD1_CID}")
	private String REST_CONTENT_AD1_CID;

	/**
	 * 获得大广告位的内容
	 * <p>Title: getAd1List</p>
	 * <p>Description: </p>
	 * @return
	 * @see com.taotao.portal.service.ContentService#getAd1List()
	 */
	@Override
	public String getAd1List() {
		//调用服务获得数据
		String json = HttpClientUtil.doGet(REST_BASE_URL + REST_CONTENT_URL + REST_CONTENT_AD1_CID);
		//把json转换成java对象
		TaotaoResult result = TaotaoResult.formatToList(json, TbContent.class);
		//取data属性，内容列表
		List<TbContent> list = (List<TbContent>) result.getData();
		List<AdNode> nodeList = new ArrayList<>();
		//把内容列表转换成AdNode列表
		for (TbContent item : list) {
			AdNode node = new AdNode();
			node.setHeight(240);
			node.setWidth(670);
			node.setSrc(item.getPic());
			node.setAlt(item.getTitle());
			node.setHeightB(240);
			node.setWidthB(550);
			node.setSrcB(item.getPic2());
			//设置url
			node.setHref(item.getUrl());
			nodeList.add(node);
		}
		//需要把nodeListt转换成json数据
		String nodeJson = JsonUtils.objectToJson(nodeList);
		return nodeJson;

	}

}
