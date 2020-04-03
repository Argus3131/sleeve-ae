package com.yan.missyou.controller;

import com.yan.missyou.bo.PageCounter;
import com.yan.missyou.dto.LocalUser;
import com.yan.missyou.dto.OrderDTO;
import com.yan.missyou.exception.http.NotFoundException;
import com.yan.missyou.logic.OrderChecker;
import com.yan.missyou.model.Order;
import com.yan.missyou.model.OrderSku;
import com.yan.missyou.service.IOrderService;
import com.yan.missyou.validators.ScopeLevel;
import com.yan.missyou.vo.OrderIdVO;
import com.yan.missyou.vo.OrderPureVO;
import com.yan.missyou.vo.OrderSimplifyVO;
import com.yan.missyou.vo.PagingDozer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * @author Argus
 * @className OrderController
 * @description: TODO
 * @date 2020/4/1 7:54
 * @Version V1.0
 */
@RestController
@RequestMapping("v1/order")
@Validated
public class OrderController {
    @Autowired
    private IOrderService orderService;
    @Value("${missyou.order.pay-time-limit}")
    private Long payTimeLimit;

    @ScopeLevel(level = 8)
    @PostMapping
    public OrderIdVO getOrder(@RequestBody OrderDTO orderDTO) {
        Long uid = LocalUser.getUser().getId();
        // 验证前置条件
        OrderChecker orderChecker = this.orderService.isOk(uid, orderDTO);
        // 生成订单 并 扣减核销
        Long orderId = this.orderService.placeOrder(uid, orderDTO, orderChecker);
        return new OrderIdVO(orderId);
    }

    @ScopeLevel
    @GetMapping("/detail/{id}")
    public OrderPureVO getOrderDetail(@PathVariable(name = "id") Long oid) {
        Optional<Order> orderOptional = this.orderService.getOrderDetail(oid);
        return orderOptional.map((o) -> new OrderPureVO(o, payTimeLimit)).orElseThrow(() -> new NotFoundException(50009));
    }


    @ScopeLevel
    @GetMapping("/status/unpaid")
    @SuppressWarnings("unchecked")
    public PagingDozer getUnpaid(@RequestParam(defaultValue = "0")
                                         Integer start,
                                 @RequestParam(defaultValue = "10")
                                         Integer count) {
        PageCounter page = PageCounter.initPageCounter(start, count);
        Page<Order> orderPage = this.orderService.getUnpaid(page.getPage(), page.getCount());
        PagingDozer pagingDozer = new PagingDozer<>(orderPage, OrderSimplifyVO.class);
        pagingDozer.getItems().forEach((o) -> ((OrderSimplifyVO) o).setPeriod(this.payTimeLimit));
        return pagingDozer;
    }

    @ScopeLevel
    @GetMapping("/by/status/{status}")
    public PagingDozer getByStatus(@PathVariable int status,
                                   @RequestParam(name = "start", defaultValue = "0")
                                           Integer start,
                                   @RequestParam(name = "count", defaultValue = "10")
                                           Integer count) {
        PageCounter page = PageCounter.initPageCounter(start, count);
        Page<Order> paging = this.orderService.getByStatus(status, page.getPage(), page.getCount());
        PagingDozer pagingDozer = new PagingDozer<>(paging, OrderSimplifyVO.class);
        pagingDozer.getItems().forEach(o -> ((OrderSimplifyVO) o).setPeriod(this.payTimeLimit));
        return pagingDozer;
    }


}