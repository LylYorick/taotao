package com.taotao.search;

import java.io.IOException;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CloudSolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

public class SolrTest {
	
	@Test
	public void testSolr() throws Exception {
		//创建连接
		SolrServer solrServer = new  HttpSolrServer("http://192.168.164.132:8080/solr");
		//创建文档对象
		SolrInputDocument document = new SolrInputDocument();
		//添加域
		document.addField("id", "100");
		document.addField("item_title", "标题");
		document.addField("item_sell_point", "卖点");
		//添加到索引
		solrServer.add(document);
		//提交
		solrServer.commit();
	}
	
	@Test
	public void testQuery() throws SolrServerException{
		//创建连接 solr 单机版使用HttpSolrServer
		SolrServer solrServer = new  HttpSolrServer("http://192.168.164.132:8080/solr");
		//创建一个查询连接
		SolrQuery query = new SolrQuery();
		query.setQuery("*:*");
		//执行查询
		QueryResponse response = solrServer.query(query);
		//获取查询结果
		SolrDocumentList documentList = response.getResults();
		for (SolrDocument item : documentList) {
			System.out.println(item.get("id"));
			System.out.println(item.get("item_title"));
			System.out.println(item.get("item_sell_point"));
		}
	}
	@Test
	public void testSlorCloud() throws Exception{
		//创建连接 solr 集群版使用
		CloudSolrServer solrServer = new CloudSolrServer("192.168.164.132:2181,192.168.164.132:2182,192.168.164.132:2183");
		
		//设置集群的collection
		solrServer.setDefaultCollection("collection2");
		//创建文档对象
		SolrInputDocument document = new SolrInputDocument();
		//添加域
		document.addField("id", "100");
		document.addField("item_title", "标题");
		document.addField("item_sell_point", "卖点");
		//添加到索引
		solrServer.add(document);
		
		solrServer.commit();

	}
}
