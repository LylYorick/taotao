package com.taotao.portal.controller;

import java.util.List;

import javax.jws.WebParam.Mode;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.portal.pojo.CartItem;
import com.taotao.portal.service.CartService;

@Controller
public class CartController {
	
	@Autowired
	private CartService cartSevice;
	
	@RequestMapping("/cart/add/{itemId}")
	public String addCart(@PathVariable Long itemId, Integer num,
			HttpServletResponse response, HttpServletRequest request) {
		cartSevice.addCart(request, response, itemId, num);
		return "cartSuccess";
	}
	@RequestMapping("/cart/cart")
	public String listCart(HttpServletRequest request,Model model){
		List<CartItem> cartList = cartSevice.listCart(request);
		model.addAttribute("cartList", cartList);
		return "cart";
	}
	@RequestMapping("/cart/update/num/{itemId}/{num}")
	@ResponseBody
	public TaotaoResult upadteCart(@PathVariable Long itemId, @PathVariable Integer num,
			HttpServletResponse response, HttpServletRequest request) {
		TaotaoResult result = cartSevice.updateCartItem(request, response, itemId, num);
		return result;
	}
	@RequestMapping("/cart/delete/{itemId}")
	public String deleteCart(@PathVariable Long itemId, 
			HttpServletResponse response, HttpServletRequest request) {
		TaotaoResult result = cartSevice.deleteCart(request, response, itemId);
		return "redirect:/cart/cart.html";
	}
}
