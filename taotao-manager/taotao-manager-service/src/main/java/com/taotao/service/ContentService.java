package com.taotao.service;

import java.util.List;

import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbContent;

public interface ContentService {
	TaotaoResult insertContent(TbContent content);
	/**
	 * @param page
	 * @param rows
	 * @return
	 */
	EasyUIDataGridResult getContentList(Long categoryId,Integer page,Integer rows);
}
