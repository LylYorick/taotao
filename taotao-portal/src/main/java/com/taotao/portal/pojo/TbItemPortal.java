package com.taotao.portal.pojo;

import java.awt.Image;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.taotao.pojo.TbItem;

public class TbItemPortal extends TbItem{

	public String[] getImages(){
		String images = this.getImage();
		if(!StringUtils.isBlank(images)){
			String[] strings = images.split(",");
			return strings;
		}
		return null;
	}
}
