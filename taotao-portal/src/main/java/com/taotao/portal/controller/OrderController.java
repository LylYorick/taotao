package com.taotao.portal.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpRequest;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.taotao.pojo.TbUser;
import com.taotao.portal.pojo.CartItem;
import com.taotao.portal.pojo.OrderInfo;
import com.taotao.portal.service.CartService;
import com.taotao.portal.service.OrderService;

@Controller
@RequestMapping("/order/")
public class OrderController {
	@Autowired
	private CartService cartService;
	@Autowired
	private OrderService orderService;
	
	@RequestMapping("/order-cart")
	public String showOrderCart(Model model,HttpServletRequest request){
		List<CartItem> listCart = cartService.listCart(request);
		model.addAttribute("cartList", listCart);
		return "order-cart";
	}
	
	@RequestMapping("/create")
	public String createOrder(Model model,HttpServletRequest request,OrderInfo orderInfo){
		//获取用户信息
		TbUser user = (TbUser) request.getAttribute("user");
		//补全orderIn的属性
		orderInfo.setUserId(user.getId());
		orderInfo.setBuyerNick(user.getUsername());
		//调用服务
		String orderId = orderService.createOrder(orderInfo);
		model.addAttribute("orderId", orderId);
		model.addAttribute("payment", orderInfo.getPayment());
		DateTime date = new DateTime();
		date = date.plusDays(3);
		model.addAttribute("date", date.toString("yyyy-mm-dd"));
		
		return "success";
		
	}
}
