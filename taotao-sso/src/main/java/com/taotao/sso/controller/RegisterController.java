package com.taotao.sso.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.ExceptionUtil;
import com.taotao.pojo.TbUser;
import com.taotao.sso.service.RegisterService;

@Controller
@RequestMapping("/user")
public class RegisterController {
	@Autowired
	private RegisterService registerService;
	@RequestMapping("/check/{param}/{type}")
	@ResponseBody
	public Object checkData(@PathVariable String param,@PathVariable int type,String callback){
		try {
			TaotaoResult taotaoResult = registerService.checkData(param, type);
			//检测回调函数是否存在
			if(StringUtils.isNotBlank(callback)){
				MappingJacksonValue jacksonValue = new MappingJacksonValue(taotaoResult);
				jacksonValue.setJsonpFunction(callback);
				return jacksonValue;
			}
			return taotaoResult;
		} catch (Exception e) {
			e.printStackTrace();
			return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}
	@RequestMapping("/register")
	@ResponseBody
	public TaotaoResult register(TbUser user){
		try {
			TaotaoResult result = registerService.register(user);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}
}
