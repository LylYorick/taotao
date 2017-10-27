package com.taotao.portal.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.HttpClientUtil;
import com.taotao.common.utils.JsonUtils;
import com.taotao.pojo.TbItemDesc;
import com.taotao.pojo.TbItemParam;
import com.taotao.pojo.TbItemParamItem;
import com.taotao.portal.pojo.TbItemPortal;
import com.taotao.portal.service.ItemService;
@Service 
public class ItemServiceImpl implements ItemService {
	@Value("${REST_BASE_URL}")
	private String REST_BASE_URL;

	@Value("${REST_ITEM_BASE_URL}")
	private String REST_ITEM_BASE_URL;
	@Value("${REST_ITEM_DESC_URL}")
	private String REST_ITEM_DESC_URL;
	
	@Value("${REST_ITEM_PARA_URL}")
	private String REST_ITEM_PARA_URL;

	@Override
	public TbItemPortal getItemById(Long itemId) {
		String url = REST_BASE_URL + REST_ITEM_BASE_URL +itemId;
		String json = HttpClientUtil.doGet(url);
		TaotaoResult taotaoResult = TaotaoResult.formatToPojo(json,TbItemPortal.class);
		TbItemPortal item = (TbItemPortal) taotaoResult.getData();
		return item;
	}

	@Override
	public TbItemDesc getItemDescById(Long itemId) {
		String url = REST_BASE_URL + REST_ITEM_DESC_URL +itemId;
		String json = HttpClientUtil.doGet(url);
		TaotaoResult taotaoResult = TaotaoResult.formatToPojo(json,TbItemDesc.class);
		TbItemDesc itemDesc = (TbItemDesc) taotaoResult.getData();
		return itemDesc;
	}
	@Override
	public String getItemParamById(Long itemId) {
		String url = REST_BASE_URL + REST_ITEM_PARA_URL +itemId;
		String json = HttpClientUtil.doGet(url);
		TaotaoResult taotaoResult = TaotaoResult.formatToPojo(json,TbItemParamItem.class);
		TbItemParamItem itemParamItem = (TbItemParamItem) taotaoResult.getData();
		String paramJson = itemParamItem.getParamData();
		// 把规格参数的json数据转换成java对象
		// 转换成java对象
		List<Map> mapList = JsonUtils.jsonToList(paramJson, Map.class);
		// 遍历list生成html
		StringBuffer sb = new StringBuffer();

		sb.append("<table cellpadding=\"0\" cellspacing=\"1\" width=\"100%\" border=\"0\" class=\"Ptable\">\n");
		sb.append("	<tbody>\n");
		for (Map map : mapList) {
			sb.append("		<tr>\n");
			sb.append("			<th class=\"tdTitle\" colspan=\"2\">" + map.get("group") + "</th>\n");
			sb.append("		</tr>\n");
			// 取规格项
			List<Map> mapList2 = (List<Map>) map.get("params");
			for (Map map2 : mapList2) {
				sb.append("		<tr>\n");
				sb.append("			<td class=\"tdTitle\">" + map2.get("k") + "</td>\n");
				sb.append("			<td>" + map2.get("v") + "</td>\n");
				sb.append("		</tr>\n");
			}
		}
		sb.append("	</tbody>\n");
		sb.append("</table>");

		return sb.toString();
	} 
	
	
	
}
