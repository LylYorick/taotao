package com.taotao.portal.service;


import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.portal.pojo.CartItem;

public interface CartService {
   TaotaoResult addCart(HttpServletRequest request,HttpServletResponse response,Long itemId,Integer num);
   List<CartItem> listCart(HttpServletRequest request);
   TaotaoResult updateCartItem(HttpServletRequest request,HttpServletResponse response,Long itemId,Integer num);
   TaotaoResult deleteCart(HttpServletRequest request,HttpServletResponse response,Long itemId);
}
