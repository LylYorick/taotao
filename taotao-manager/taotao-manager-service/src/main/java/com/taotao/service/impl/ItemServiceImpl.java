package com.taotao.service.impl;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.mapper.TbItemMapper;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemExample;
import com.taotao.pojo.TbItemExample.Criteria;
import com.taotao.service.ItemService;

@Service
public class ItemServiceImpl implements ItemService {
	@Autowired
	private TbItemMapper itemMapper;

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

}