 package com.taotao.sso.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.mapper.TbUserMapper;
import com.taotao.pojo.TbUser;
import com.taotao.pojo.TbUserExample;
import com.taotao.pojo.TbUserExample.Criteria;
import com.taotao.sso.service.RegisterService;

@Service
public class RegisterServiceImpl implements RegisterService {
	@Autowired
	private TbUserMapper userMapper;
	
	/* (non-Javadoc)
	 * @see com.taotao.sso.service.RegisterService#checkData(java.lang.String, int)
	 */
	@Override
	public TaotaoResult checkData(String param, int type) {
		TbUserExample example = new TbUserExample();
		Criteria criteria = example.createCriteria();
		//1、2、3分别代表username、phone、email
		if(type == 1){
			criteria.andUsernameEqualTo(param);
		}else if(type == 2){
			criteria.andPhoneEqualTo(param);
		}else if(type == 3){
			criteria.andEmailEqualTo(param);
		}
		List<TbUser> list = userMapper.selectByExample(example);
		//判断查询结果是否为空
		if(list == null || list.isEmpty()){
			//返回允许注册
			return TaotaoResult.ok(true);
		}
		//返回禁止注册
		return TaotaoResult.ok(false);
	}

	@Override
	public TaotaoResult register(TbUser user) {
		String username = user.getUsername();
		String password = user.getPassword();
		if(StringUtils.isBlank(username) || StringUtils.isBlank(password)){
			return TaotaoResult.build(400,"用户名或密码不能为空！");
		}
		TaotaoResult result = checkData(username, 1);
		
		if (!(boolean)result.getData()) {
			return TaotaoResult.build(400,"用户名重复！");
		}
		String phone = user.getPhone();
		if(StringUtils.isBlank(phone)){
			return TaotaoResult.build(400,"手机号不能为空！");
		}
		result = checkData(phone, 2);
		if (!(boolean)result.getData()) {
			return TaotaoResult.build(400,"手机号重复！");
		}
		
		String email = user.getEmail();
		if (!StringUtils.isBlank(email)) {
			result = checkData(email, 2);
			if (!(boolean)result.getData()) {
				return TaotaoResult.build(400,"邮箱重复！");
			}
		}
		user.setCreated(new Date());
		user.setUpdated(new Date());
		user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
		userMapper.insert(user);
		return TaotaoResult.ok();
	}


}
