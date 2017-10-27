package com.taotao.portal.service;

import com.taotao.pojo.TbItemDesc;
import com.taotao.portal.pojo.TbItemPortal;

public interface ItemService {
	TbItemPortal getItemById(Long itemId);
	TbItemDesc getItemDescById(Long itemId);
	String getItemParamById(Long itemId);
}
