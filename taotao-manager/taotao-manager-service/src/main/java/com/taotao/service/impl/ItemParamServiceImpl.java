package com.taotao.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.mapper.TbItemParamMapper;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemExample;
import com.taotao.pojo.TbItemParam;
import com.taotao.pojo.TbItemParamExample;
import com.taotao.pojo.TbItemParamExample.Criteria;
import com.taotao.service.ItemParamService;

@Service
public class ItemParamServiceImpl implements ItemParamService {

	@Autowired
	private TbItemParamMapper itemParamMapper;
	
	@Override
	public TaotaoResult getItemParamByCid(Long cid) {
		//根据cid查询规格参数模板
		TbItemParamExample example = new TbItemParamExample();
		Criteria criteria = example.createCriteria();
		criteria.andItemCatIdEqualTo(cid);
		//执行查询
		List<TbItemParam>list = itemParamMapper.selectByExampleWithBLOBs(example);
		//判断是否查询到结果
		if (list != null&&list.size() > 0) {
			TbItemParam itemParam = list.get(0);
			return TaotaoResult.ok(itemParam);
		}
		return TaotaoResult.ok();
	}

	@Override
	public EasyUIDataGridResult getItemParamList(Integer page, Integer rows) {
		//分页处理
		PageHelper.startPage(page, rows);
		//拼接查询条件
		TbItemParamExample example = new TbItemParamExample();
		List<TbItemParam> list = itemParamMapper.selectByExampleWithBLOBs(example);
		//获取分页信息
		PageInfo<TbItemParam> info = new PageInfo<>(list); 
		//返回处理结果
		EasyUIDataGridResult result = new EasyUIDataGridResult();
		result.setTotal(info.getTotal());
		result.setRows(info.getList());
		return result;
	}

	@Override
	public TaotaoResult createItemParam(Long cid, String paramData) {
		TbItemParam itemParam = new TbItemParam();
		//设置商品类目id 和 参数模板
		itemParam.setItemCatId(cid);
		itemParam.setParamData(paramData);
		//设置创建时间和更新时间
		Date date = new Date();
		itemParam.setCreated(date);
		itemParam.setUpdated(date);
		//保存此记录
		itemParamMapper.insert(itemParam);
		//返回执行结果 
		return TaotaoResult.ok();
	}

	@Override
	public TaotaoResult deleteItemParam(Long ids) {
		//删除id为ids的itemParamMapper
		int sum = itemParamMapper.deleteByPrimaryKey(ids);
		if(sum == 1){
			return TaotaoResult.ok();
		}
		return TaotaoResult.build(402, "删除失败");
	}

}

