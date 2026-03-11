package com.sky.service;

import com.sky.dto.OrdersDTO;
import com.sky.dto.OrdersPageQueryDTO;
import com.sky.dto.OrdersSubmitDTO;
import com.sky.result.PageResult;
import com.sky.vo.OrderPaymentVO;
import com.sky.vo.OrderStatisticsVO;
import com.sky.vo.OrderSubmitVO;
import com.sky.vo.OrderVO;

public interface OrdersService {
    
    OrderSubmitVO submitOrder(OrdersSubmitDTO ordersSubmitDTO);
    
    void payment(OrdersSubmitDTO ordersSubmitDTO);
    
    OrderPaymentVO paymentSuccess(String outTradeNo);
    
    void userCancelOrder(Long id);
    
    void remindOrder(Long id);
    
    PageResult<OrderVO> pageQuery4User(OrdersPageQueryDTO ordersPageQueryDTO);
    
    OrderVO details(Long id);
    
    PageResult<OrderVO> pageQuery4Admin(OrdersPageQueryDTO ordersPageQueryDTO);
    
    OrderStatisticsVO statistics();
    
    void confirmOrder(Long id);
    
    void rejectionOrder(Long id);
    
    void cancelOrder(Long id);
    
    void deliveryOrder(Long id);
    
    void completeOrder(Long id);
}
