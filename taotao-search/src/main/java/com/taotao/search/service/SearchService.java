package com.taotao.search.service;

import com.taotao.common.pojo.TaotaoResult;

public interface SearchService {
	TaotaoResult search(String queryString, Integer page,Integer rows)throws Exception;
}
