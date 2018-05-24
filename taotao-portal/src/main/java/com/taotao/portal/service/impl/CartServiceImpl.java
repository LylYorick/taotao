package com.taotao.portal.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.CookieUtils;
import com.taotao.common.utils.JsonUtils;
import com.taotao.portal.pojo.CartItem;
import com.taotao.portal.pojo.TbItemPortal;
import com.taotao.portal.service.CartService;
import com.taotao.portal.service.ItemService;

@Service
public class CartServiceImpl implements CartService{

	@Autowired
	private ItemService itemService;
	@Value("${COOKIE_EXPIRE}")
	private Integer COOKIE_EXPIRE;

	
	@Override
	public TaotaoResult addCart(HttpServletRequest request, HttpServletResponse response, Long itemId, Integer num) {
		// 1.从cookie中取出历史购物车信息 并转成 List<cartItem>
		List<CartItem> list = getCartItemList(request);
		//2.判断list 中是否有此商品 如果有就新增原商品的数量
		boolean have = false; //当前商品是否存在购物车 默认为 不存在
		for (CartItem cartItem : list) {
			if (cartItem.getId().longValue() == itemId ) {
				cartItem.setNum(num + cartItem.getNum());
				have = true; 
				break;
			}
		}
		//3. 如果没有就新增此商品
		if (!have) {
			CartItem cartItem = new CartItem();
			TbItemPortal itemPortal = itemService.getItemById(itemId);
			cartItem.setId(itemId);
			cartItem.setImage(itemPortal.getImages()[0]);
			cartItem.setTitle(itemPortal.getTitle());
			cartItem.setPrice(itemPortal.getPrice());
			cartItem.setNum(num);
			list.add(cartItem);
		}
		//4.将list转化保存到cookie中去
		String json = JsonUtils.objectToJson(list);
		CookieUtils.setCookie(request, response, "TT_CART", json, COOKIE_EXPIRE, true);
		return TaotaoResult.ok();
	}
	
	/**从cookie中获取历史购物车信息*/
	public List<CartItem> getCartItemList(HttpServletRequest request){
		try {
			String json = CookieUtils.getCookieValue(request, "TT_CART", true);
			List<CartItem> list = JsonUtils.jsonToList(json, CartItem.class);
			return list == null ? new ArrayList<CartItem>() : list;
		} catch (Exception e) {
			e.printStackTrace();
			return  new ArrayList<CartItem>();
		}
	}

	@Override
	public List<CartItem> listCart(HttpServletRequest request) {
		return getCartItemList(request);
	}


	@Override
	public TaotaoResult updateCartItem(HttpServletRequest request, HttpServletResponse response, Long itemId,
			Integer num) {
		// 1.从cookie中取出历史购物车信息 并转成 List<cartItem>
		List<CartItem> list = getCartItemList(request);
		for (CartItem cartItem : list) {
			if (cartItem.getId().longValue() == itemId ) {
				cartItem.setNum(num);
				break;
			}
		}
		//4.将list转化保存到cookie中去
		String json = JsonUtils.objectToJson(list);
		CookieUtils.setCookie(request, response, "TT_CART", json, COOKIE_EXPIRE, true);
		return TaotaoResult.ok();
	}

	@Override
	public TaotaoResult deleteCart(HttpServletRequest request, HttpServletResponse response, Long itemId) {
		// 1.从cookie中取出历史购物车信息 并转成 List<cartItem>
		List<CartItem> list = getCartItemList(request);
		for (CartItem cartItem : list) {
			if (cartItem.getId().longValue() == itemId ) {
				list.remove(cartItem);
				break;
			}
		}
		//4.将list转化保存到cookie中去
		String json = JsonUtils.objectToJson(list);
		CookieUtils.setCookie(request, response, "TT_CART", json, COOKIE_EXPIRE, true);
		return TaotaoResult.ok();
	}
	
}
