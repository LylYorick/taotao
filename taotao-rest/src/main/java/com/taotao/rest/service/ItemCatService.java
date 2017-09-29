package com.taotao.rest.service;

import java.util.List;

import com.taotao.rest.pojo.ItemCatResult;

public interface ItemCatService {
	ItemCatResult getItemCatList();
	List getItemCatList(Long id);
}
