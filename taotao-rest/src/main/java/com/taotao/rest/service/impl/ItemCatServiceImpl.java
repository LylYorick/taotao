package com.taotao.rest.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.mapper.TbItemCatMapper;
import com.taotao.pojo.TbItemCat;
import com.taotao.pojo.TbItemCatExample;
import com.taotao.pojo.TbItemCatExample.Criteria;
import com.taotao.rest.pojo.CatNode;
import com.taotao.rest.pojo.ItemCatResult;
import com.taotao.rest.service.ItemCatService;

@Service
public class ItemCatServiceImpl implements ItemCatService {
	@Autowired
	private TbItemCatMapper itemCatMapper;

	@Override
	public ItemCatResult getItemCatList() {
		List data = getItemCatList(0L);
		ItemCatResult itemCatResult = new ItemCatResult();
		itemCatResult.setData(data);
		return itemCatResult;
	}

	@Override
	public List getItemCatList(Long id) {
		TbItemCatExample example = new TbItemCatExample();
		Criteria createCriteria = example.createCriteria();
		createCriteria.andParentIdEqualTo(id);
		List<TbItemCat> selectByExample = itemCatMapper.selectByExample(example);
		List list = new ArrayList<>();
		int count = 0;
		for(TbItemCat itemCat : selectByExample) {
			if(count >= 14){
				break;
			}
			if(!itemCat.getIsParent()){
				//如果是叶子节点 只需要 String就行
				String leaf = "/products/"+itemCat.getId() +".html| "+itemCat.getName();
				list.add(leaf);
			}else{
				//如果是节点有子节点
				CatNode node = new CatNode();
				node.setUrl("/products/" + itemCat.getId() + ".html");
				if(itemCat.getParentId() == 0){
					count++;
					//如果是节点是一级节点 点击节点名称  会有链接
					node.setName("<a href='/products/" + itemCat.getId() + ".html'>" + itemCat.getName() + "</a>");
				}else{
					//如果是普通节点 点击节点名称 不要链接
					node.setName(itemCat.getName());
				}
				node.setItems(getItemCatList(itemCat.getId()));
				list.add(node);
			}
		}
		return list;
	}
}
