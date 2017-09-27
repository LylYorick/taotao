package com.taotao.service;

import org.springframework.web.bind.annotation.PathVariable;

import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;

public interface ItemParamService {
	TaotaoResult getItemParamByCid(Long cid);
	EasyUIDataGridResult getItemParamList(Integer page,Integer rows);
	TaotaoResult createItemParam(Long cid,String paramData);
	TaotaoResult deleteItemParam(Long ids);
}
