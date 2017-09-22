package com.taotao.service;

import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.pojo.TbItem;

public interface ItemService {
	TbItem getItemById(Long id);
	EasyUIDataGridResult getItemList(Integer page, Integer rows);
}
