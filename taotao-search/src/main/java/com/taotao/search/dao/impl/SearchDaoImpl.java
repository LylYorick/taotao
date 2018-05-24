package com.taotao.search.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.search.dao.SearchDao;
import com.taotao.search.pojo.SearchItem;
import com.taotao.search.pojo.SearchResult;

@Repository
public class SearchDaoImpl implements SearchDao{
	@Autowired
	private SolrServer solrServer ;
	
	@Override
	public SearchResult search(SolrQuery query) throws Exception {
		//执行查询
		QueryResponse response = solrServer.query(query);
		//取查询结果列表
		SolrDocumentList solrDocumentList = response.getResults();
		//创建searchResult对象
		SearchResult searchResult = new SearchResult();
		List<SearchItem> list = new ArrayList<SearchItem>();
		//获取全部的高亮map
		Map<String, Map<String, List<String>>> highlightingMap = response.getHighlighting();
		for (SolrDocument solrDocument : solrDocumentList) {
			SearchItem searchItem = new SearchItem();
			//创建一个SearchItem对象
			searchItem.setId((String) solrDocument.get("id"));
			searchItem.setCategory_name((String) solrDocument.get("item_category_name"));
			searchItem.setSell_point((String) solrDocument.get("item_sell_point"));
			searchItem.setImage((String) solrDocument.get("item_image"));
			searchItem.setPrice((Long) solrDocument.get("item_price"));
			//从高亮map中找到这个id独有的高亮map
			Map<String, List<String>> map = highlightingMap.get(searchItem.getId());
			//从这个这个id独有的高亮map中找到item_title的titleList
			List<String> titleList = map.get("item_title");
			String title = (String) solrDocument.get("item_title");
			if(titleList != null && !titleList.isEmpty()){
				title = titleList.get(0);
			}
			//取高亮后的结果
			searchItem.setTitle(title);
			//添加到列表
			list.add(searchItem);
		}
		searchResult.setItemList(list);
		//查询结果总数量
		searchResult.setRecordCount(solrDocumentList.getNumFound());
		return searchResult;
	}

}
