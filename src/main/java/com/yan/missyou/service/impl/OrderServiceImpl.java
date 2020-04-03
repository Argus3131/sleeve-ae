package com.yan.missyou.service.impl;

import com.yan.missyou.common.OrderStatus;
import com.yan.missyou.dao.CouponDao;
import com.yan.missyou.dao.OrderDao;
import com.yan.missyou.dao.SkuDao;
import com.yan.missyou.dao.UserCouponDao;
import com.yan.missyou.dto.LocalUser;
import com.yan.missyou.dto.OrderDTO;
import com.yan.missyou.exception.http.ForbiddenException;
import com.yan.missyou.exception.http.NotFoundException;
import com.yan.missyou.exception.http.ParamException;
import com.yan.missyou.exception.http.ServerErrorException;
import com.yan.missyou.logic.CouponChecker;
import com.yan.missyou.logic.OrderChecker;
import com.yan.missyou.model.*;
import com.yan.missyou.service.IOrderService;
import com.yan.missyou.utils.CommonUtils;
import com.yan.missyou.utils.FullOff;
import com.yan.missyou.utils.OrderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author Argus
 * @className OrderServiceImpl
 * @description: TODO
 * @date 2020/4/1 8:10
 * @Version V1.0
 */
@Service
public class OrderServiceImpl implements IOrderService {
    @Autowired
    private CouponDao couponDao;
    @Autowired
    private UserCouponDao userCouponDao;
    @Autowired
    private SkuDao skuDao;
    @Autowired
    private FullOff fullOff;
    @Value("${missyou.order.max-limit-num}")
    private Integer maxCount;
    @Value("${missyou.order.pay-time-limit}")
    private Integer payTimeLimit;
    @Autowired
    private OrderDao orderDao;

    /**
     * 当前优惠券是否存在
     * 当前优惠券是否被当前用户领取
     */
    public OrderChecker isOk(Long uid, OrderDTO orderDTO) {
        // 校验折后价
        if (orderDTO.getFinalTotalPrice().compareTo(new BigDecimal("0")) <= 0) {
            throw new ServerErrorException(50011);
        }
        Long cid = orderDTO.getCouponId();
        List<Long> skuIds = orderDTO.getSkuIds();
        if (skuIds.isEmpty()) {
            throw new ForbiddenException(50000);
        }
        // 购物车 的skuList
        List<Sku> skuList = skuDao.findAllByIdIn(skuIds);
        CouponChecker couponChecker = null;
        // 校验优惠券存在时的情况
        if (null != cid && cid >= 0) {
            System.out.println(cid);
            // 校验优惠券是否存在
            Coupon coupon = couponDao.findById(cid)
                    .orElseThrow(() -> new NotFoundException(40004));
            // 校验优惠券是否被领取 且可用
            userCouponDao.getUserCouponAndStatus(uid, cid)
                    .orElseThrow(() -> new NotFoundException(40014));
            couponChecker = new CouponChecker(orderDTO, coupon, fullOff);
            OrderChecker orderChecker = new OrderChecker(orderDTO, skuList, maxCount, couponChecker);
            orderChecker.isOk();
        }
        // 优惠券不存在时的情况
        OrderChecker orderChecker = new OrderChecker(orderDTO, skuList, maxCount, couponChecker);
        orderChecker.isOk();
        return orderChecker;
    }

    @Transactional
    @Override
    public Long placeOrder(Long uid, OrderDTO orderDTO, OrderChecker orderChecker) {
        String orderNo = OrderUtil.makeOrderNo();
        Calendar now = Calendar.getInstance();
        Calendar now1 = (Calendar) now.clone();
        Order order = Order.builder()
                .orderNo(orderNo)
                .totalPrice(orderDTO.getTotalPrice())
                .finalTotalPrice(orderDTO.getFinalTotalPrice())
                .userId(uid)
                .totalCount(orderChecker.getTotalCount().longValue())
                .snapImg(orderChecker.getLeaderImg())
                .snapTitle(orderChecker.getLeaderTitle())
                .status(OrderStatus.UNPAID.getValue())
                .expiredTime(CommonUtils.addSomeSeconds(now, this.payTimeLimit).getTime())
                .placedTime(now1.getTime())
                .build();
        order.setSnapAddress(orderDTO.getAddress());
        order.setSnapItems(orderChecker.getOrderSkuList());
        orderDao.save(order);
        // 预扣除库存
        this.reduceStock(orderChecker);
        // 用了优惠券的情况 核销优惠券
        if (orderDTO.getCouponId() != null) {
            this.writeOffCoupon(orderDTO.getCouponId(), order.getId(), uid);
        }
        // 加入到延迟消息队列
        return order.getId();
    }

    @Override
    public Optional<Order> getOrderDetail(Long oid) {
        Long uid = LocalUser.getUser().getId();
        return this.orderDao.findFirstByUserIdAndId(uid, oid);
    }

    @Override
    public Page<Order> getUnpaid(Integer page, Integer size) {
        // 根据时间倒序分页
        Pageable pageable = PageRequest.of(page, size, Sort.by("createTime").descending());
        Long uid = LocalUser.getUser().getId();
        Date now = new Date();
        return this.orderDao.findByExpiredTimeGreaterThanAndStatusAndUserId(now, OrderStatus.UNPAID.getValue(), uid, pageable);
    }

    @Override
    public Page<Order> getByStatus(Integer status, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createTime").descending());
        Long uid = LocalUser.getUser().getId();
        if (status == OrderStatus.ALL.getValue()) {
            return this.orderDao.findByUserId(uid, pageable);
        }
        return this.orderDao.findByUserIdAndStatus(uid, status, pageable);
    }

    /**
     * 减去库存 校验生成订单时候的及时库存
     *
     * @param orderChecker
     */
    public void reduceStock(OrderChecker orderChecker) {
        List<OrderSku> orderSkuList = orderChecker.getOrderSkuList();
        for (OrderSku orderSku : orderSkuList) {
            Long id = orderSku.getId();
            System.out.println(orderSku.getCount().longValue());
            Long quantity = orderSku.getCount().longValue();
            int result = skuDao.reduceStock(id, orderSku.getCount().longValue());
            if (result != 1) {
                throw new ParamException(50003);
            }
        }
    }

    /**
     * 核销优惠券
     */
    public void writeOffCoupon(Long couponId, Long orderId, Long uid) {
        int result = userCouponDao.writeOff(couponId, orderId, uid);
        if (result != 1) {
            throw new ParamException(40012);
        }
    }
}