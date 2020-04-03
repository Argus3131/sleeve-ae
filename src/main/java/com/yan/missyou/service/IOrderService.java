package com.yan.missyou.service;

import com.yan.missyou.dto.OrderDTO;
import com.yan.missyou.logic.CouponChecker;
import com.yan.missyou.logic.OrderChecker;
import com.yan.missyou.model.Order;
import org.springframework.data.domain.Page;

import java.util.Optional;

/**
 * @author Argus
 * @className IOrderService
 * @description: TODO
 * @date 2020/4/1 8:09
 * @Version V1.0
 */
public interface IOrderService {

    OrderChecker isOk(Long uid, OrderDTO orderDTO);

    Long placeOrder(Long uid, OrderDTO orderDTO,OrderChecker orderChecker);

    Optional<Order> getOrderDetail(Long oid);

    Page<Order> getUnpaid(Integer page, Integer size);


    Page<Order> getByStatus(Integer status, Integer page, Integer size);
}
