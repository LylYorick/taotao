package com.taotao.service.impl;



import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.IDUtils;
import com.taotao.common.utils.JsonUtils;
import com.taotao.mapper.TbItemDescMapper;
import com.taotao.mapper.TbItemMapper;
import com.taotao.mapper.TbItemParamItemMapper;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.pojo.TbItemExample;
import com.taotao.pojo.TbItemExample.Criteria;
import com.taotao.pojo.TbItemParamItem;
import com.taotao.pojo.TbItemParamItemExample;
import com.taotao.service.ItemService;

@Service
public class ItemServiceImpl implements ItemService {
	@Autowired
	private TbItemMapper itemMapper;
	@Autowired
	private TbItemDescMapper  itemDescMapper;
	@Autowired
	private TbItemParamItemMapper itemParamItemMapper;

	@Override
	public TbItem getItemById(Long id) {
		TbItemExample example = new TbItemExample();
		//统计查询条件
		Criteria criteria = example.createCriteria();
		criteria.andIdEqualTo(id);
		System.out.println("拼接好了查询条件");
		List<TbItem> list = itemMapper.selectByExample(example);
		TbItem item = null;
		System.out.println("查询出来数据"+list);
		if(list !=null && list.size()>0){
			item = list.get(0);
		}
		return item;
	}

	@Override
	public EasyUIDataGridResult getItemList(Integer page, Integer rows) {
		//分页处理
		PageHelper.startPage(page, rows);
		//拼接查询条件
		TbItemExample example = new TbItemExample();
		List<TbItem> list = itemMapper.selectByExample(example);
		//获取分页信息
		PageInfo<TbItem> info = new PageInfo<>(list); 
		//返回处理结果
		EasyUIDataGridResult result = new EasyUIDataGridResult();
		result.setTotal(info.getTotal());
		result.setRows(info.getList());
		return result;
	}

	@Override
	public TaotaoResult createItem(TbItem item, String desc,String itemParams) {
		//创建商品id
		long itemId = IDUtils.genItemId();
		item.setId(itemId);
		//商品状态，1-正常，2-下架，3-删除
		item.setStatus((byte) 1);
		//设置创建时间和更新时间
		Date date = new Date();
		item.setCreated(date);
		item.setUpdated(date);
		//创建描述对象 TbItemDesc
		TbItemDesc  itemDesc = new TbItemDesc();
		
		//设置描述对象属性
		//设置描述ID为刚刚创建的商品id
		itemDesc.setItemId(itemId);
		//设置描述的desc为传入的desc
		itemDesc.setItemDesc(desc);
		//设置创建时间和更新时间
		itemDesc.setCreated(date);
		itemDesc.setUpdated(date);
		
		//保存商品和描述两个对象
		itemMapper.insert(item);
		itemDescMapper.insert(itemDesc);
		
		TbItemParamItem  itemParamItem = new TbItemParamItem();
		itemParamItem.setItemId(itemId);
		itemParamItem.setParamData(itemParams);
		itemParamItem.setCreated(date);
		itemParamItem.setUpdated(date);
		
		itemParamItemMapper.insert(itemParamItem);
		//设置返回结果 成功
		return TaotaoResult.ok();
	}

	@Override
	public String getItemParamHtml(Long itemId) {
		TbItemParamItemExample example = new TbItemParamItemExample();
		com.taotao.pojo.TbItemParamItemExample.Criteria createCriteria = example.createCriteria();
		createCriteria.andItemIdEqualTo(itemId);
		List<TbItemParamItem> list = itemParamItemMapper.selectByExampleWithBLOBs(example);
		if(list ==null || list.isEmpty()){
			return "";
		}
		//获取查询的item
		TbItemParamItem item = list.get(0);
		//得到 paramData的json字符串
		String paramData = item.getParamData();
		List<Map> jsonList = JsonUtils.jsonToList(paramData, Map.class);
		StringBuffer sb = new StringBuffer();
		sb.append("<table cellpadding=\"0\" cellspacing=\"1\" width=\"100%\" border=\"0\" class=\"Ptable\">\n");
		sb.append("<tbody>\n");
		for(Map map:jsonList){
			sb.append("		<tr>\n");
			sb.append("			<th class=\"tdTitle\" colspan=\"2\">"+map.get("group")+"</th>\n");
			sb.append("		</tr>\n");
			//取规格项
			List<Map>mapList2 = (List<Map>) map.get("params");
			for (Map map2 : mapList2) {
				sb.append("		<tr>\n");
				sb.append("			<td class=\"tdTitle\">"+map2.get("k")+"</td>\n");
				sb.append("			<td>"+map2.get("v")+"</td>\n");
				sb.append("		</tr>\n");
			}
		}
		sb.append("	</tbody>\n");
		sb.append("</table>");
		return sb.toString();
	}

}
