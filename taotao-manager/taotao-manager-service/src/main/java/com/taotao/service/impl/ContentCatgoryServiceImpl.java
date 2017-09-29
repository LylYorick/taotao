package com.taotao.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.EasyUITreeNode;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.mapper.TbContentCategoryMapper;
import com.taotao.pojo.TbContentCategory;
import com.taotao.pojo.TbContentCategoryExample;
import com.taotao.pojo.TbContentCategoryExample.Criteria;
import com.taotao.service.ContentCatgoryService;

@Service
public class ContentCatgoryServiceImpl implements ContentCatgoryService{

	@Autowired
	public TbContentCategoryMapper contentCategoryMapper;
	
	@Override
	public List<EasyUITreeNode> getContentCatList(Long parentId) {
		TbContentCategoryExample example = new TbContentCategoryExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		List<TbContentCategory> list = contentCategoryMapper.selectByExample(example);
		List<EasyUITreeNode> resultList = new ArrayList<EasyUITreeNode>();
		for (TbContentCategory item : list) {
			EasyUITreeNode node = new EasyUITreeNode();
			node.setId(item.getId());
			node.setText(item.getName());
			node.setState(item.getIsParent()?"closed":"open");
			resultList.add(node);
		}
		return resultList;
	}

	@Override
	public TaotaoResult insertCatgory(Long parentId, String name) {
		TbContentCategory contentCategory = new TbContentCategory();
		contentCategory.setParentId(parentId);
		contentCategory.setName(name);
		//新建的结点不是父节点
		contentCategory.setIsParent(false);
		//1(正常),2(删除)
		contentCategory.setStatus(1);
		//'排列序号，表示同级类目的展现次序，如数值相等则按名称次序排列。取值范围:大于零的整数'
		//这里初始化为1
		contentCategory.setSortOrder(1);
		Date date = new Date();
		contentCategory.setCreated(date);
		contentCategory.setUpdated(date);
		//插入数据
		contentCategoryMapper.insert(contentCategory);
		//取返回的主键
		Long id = contentCategory.getId();
		//查询父节点
		TbContentCategory parentNode = contentCategoryMapper.selectByPrimaryKey(parentId);
		//判断父节点的isparent属性
		if(!parentNode.getIsParent()){
			parentNode.setIsParent(true);
			parentNode.setUpdated(date);
			//更新父节点
			contentCategoryMapper.updateByPrimaryKeySelective(parentNode);
		}
		//返回主键
		return TaotaoResult.ok(id);
	}

	@Override
	public TaotaoResult updateCatgory(Long id, String name) {
		//从数据库中查询出原型
		TbContentCategory record = contentCategoryMapper.selectByPrimaryKey(id);
		//设置修改属性
		record.setName(name);
		record.setUpdated(new Date());
		contentCategoryMapper.updateByPrimaryKeySelective(record);
		return TaotaoResult.ok(name);
	}

	@Override
	public TaotaoResult deleteCatgory(Long id) {
		//1.如果当前结点有子节点应先删除完全部的子节点后再删除当前结点。
		
		//2.如果当前结点是父阶点的最后一个子节点。
		//   在删除当前结点后应将其父节点的isparent属性设为false;
		//结论:需要用到递归。
		
		//1.先删除全部的子节点
			deleteCatgoryByParentId(id);
		//查询当前结点是否是父阶点的最后一个子节点
		//(1).查询出当前的结点
		 TbContentCategory currentNode = contentCategoryMapper.selectByPrimaryKey(id);
		 Long parentId = currentNode.getParentId(); 
		//(2).查询父节点的子节点数是否小于2；
		 TbContentCategoryExample example = new TbContentCategoryExample();
		 Criteria criteria = example.createCriteria();		 
		 criteria.andParentIdEqualTo(parentId);
		 int sum = contentCategoryMapper.countByExample(example);
		//(2).查询父节点的子节点数小于1,则设置父阶点的isParent属性为false；
		 Date  date = new Date();
 		 if(sum < 2){
			 TbContentCategory parentNode = contentCategoryMapper.selectByPrimaryKey(parentId);
			 parentNode.setIsParent(false);
			 parentNode.setUpdated(date);
			 contentCategoryMapper.updateByPrimaryKeySelective(parentNode);
		 }
 		 //3.删除当前结点
 		 contentCategoryMapper.deleteByPrimaryKey(id);
		 return TaotaoResult.ok();
	}

	@Override
	public void deleteCatgoryByParentId(Long parentId) {
		 TbContentCategoryExample example = new TbContentCategoryExample();
		 Criteria criteria = example.createCriteria();
		 criteria.andParentIdEqualTo(parentId);
		 List<TbContentCategory> sonList = contentCategoryMapper.selectByExample(example);
		 for (TbContentCategory item : sonList) {
			 //删除当前子节点的全部子节点
			 if (item.getIsParent()) {
				 deleteCatgoryByParentId(item.getId());
			}
			 //删除当前子节点
			 contentCategoryMapper.deleteByPrimaryKey(item.getId());
		 }
	}
	
	
	
}
