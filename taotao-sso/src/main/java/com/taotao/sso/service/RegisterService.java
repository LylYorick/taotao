package com.taotao.sso.service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbUser;

public interface RegisterService {
	 /**
	 * @param param
	 * @param type 1、2、3分别代表username、phone、email
	 * @return
	 */
	TaotaoResult checkData(String param,int type);
	 TaotaoResult register(TbUser user);
}
