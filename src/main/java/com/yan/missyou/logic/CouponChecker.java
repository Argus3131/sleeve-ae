package com.yan.missyou.logic;

import com.yan.missyou.bo.SkuOrderBO;
import com.yan.missyou.common.CouponType;
import com.yan.missyou.dto.OrderDTO;
import com.yan.missyou.exception.http.ForbiddenException;
import com.yan.missyou.exception.http.ServerErrorException;
import com.yan.missyou.model.Coupon;
import com.yan.missyou.model.Sku;
import com.yan.missyou.utils.CommonUtils;
import com.yan.missyou.utils.DisHalfUp;
import com.yan.missyou.utils.FullOff;


import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Argus
 * @className CouponChecker
 * @description: check:
 * 校验优惠券的是否可用：
 * 优惠券是否过期
 * 优惠券在当前品类是否可用
 * 校验优惠券可允许使用的额度是否达到了预期金额：
 * 优惠券在当前的优惠套餐下的边界金额是否达到
 * 校验折扣之后的价格是否与前端传递的价格一致否则throw Exception
 * @date 2020/4/1 8:31
 * @Version V1.0
 */
public class CouponChecker {
    private OrderDTO orderDTO;
    private Coupon coupon;
    private FullOff fullOff;

    public CouponChecker(OrderDTO orderDTO, Coupon coupon, FullOff fullOff) {
        this.orderDTO = orderDTO;
        this.coupon = coupon;
        this.fullOff = fullOff;
    }


    public void isOk(BigDecimal totalServerPrice, List<SkuOrderBO> skuOrderBOList) {
        this.isInTimeLine();
        this.canBeUsed(skuOrderBOList);
        BigDecimal categoryTotalPrice = this.getCategoryTotalPrice(skuOrderBOList);
        this.finalTotalPriceCheck(totalServerPrice, categoryTotalPrice);
    }

    /**
     * 校验优惠券时间是否过期
     */
    public void isInTimeLine() {
        Date now = new Date();
        boolean isInTimeLine = CommonUtils.isTimeIn(now, this.coupon.getStartTime(), this.coupon.getEndTime());
        if (!isInTimeLine) {
            throw new ForbiddenException(40005);
        }
    }

    /**
     * 校验订单的skuList 是否允许使用该优惠券
     *
     * @param skuList
     */
//    public void categoryCheck(List<Sku> skuList) {
//        // 购物车 sku对应的categoryIds
//        List<Long> skuCategoriesIdList = skuList.stream().map(Sku::getCategoryId).collect(Collectors.toList());
//        // 获取当前使用优惠券的品类范围
//        List<Long> categoryIds = coupon.getCategoryIds();
//        // 如果不是全场券 且 限定品类
//        if (!coupon.getWholeStore() && !categoryIds.isEmpty()) {
//            categoryIds.forEach(id -> {
//                Boolean exist = this.isExist(skuCategoriesIdList, id);
//                if (!exist) {
//                    throw new ForbiddenException(40009);
//                }
//            });
//        }
//    }

    /**
     * 待检验的 skuList
     * 存在对应范围sku则允许使用
     * @param skuCategoriesIdList
     */
//    public Boolean isExist(List<Long> skuCategoriesIdList, Long id) {
//        Optional<Long> any = skuCategoriesIdList.stream().filter(i -> i.equals(id))
//                .findAny();
//        return any.isPresent();
//    }

    /**
     * 计算折后价 并校验前端传递的FinalTotalPrice和orderFinalTotalPrice是否相等
     *
     * @param totalServerPrice
     * @param categoryTotalPrice
     * @return
     */
    public Boolean finalTotalPriceCheck(BigDecimal totalServerPrice, BigDecimal categoryTotalPrice) {
        BigDecimal orderFinalTotalPrice;
        BigDecimal categoryFinalTotalPrice;
        switch (CouponType.toType(this.coupon.getType())) {
            case FULL_MINUS:
            case NO_THRESHOLD_MINUS:
                categoryFinalTotalPrice = categoryTotalPrice.subtract(this.coupon.getMinus());
                break;
            case FULL_OFF:
                categoryFinalTotalPrice = this.fullOff.discount(categoryTotalPrice.multiply(this.coupon.getRate()));
                break;
            default:
                throw new ServerErrorException(50005);
        }
        if (categoryFinalTotalPrice.compareTo(new BigDecimal("0")) <= 0) {
            throw new ServerErrorException(50005);
        }
        BigDecimal categoryTotalPriceNotInCoupon = this.getCategoryTotalPriceNotInCoupon(totalServerPrice, categoryTotalPrice);
        orderFinalTotalPrice = categoryTotalPriceNotInCoupon.add(categoryFinalTotalPrice);
        return this.moneyCheck(orderDTO.getFinalTotalPrice(), orderFinalTotalPrice);
    }


    /**
     * 校验前端折后价 和 后端计算最终价是否相等
     *
     * @param finalTotalPrice      校验前端折后价
     * @param orderFinalTotalPrice 后端计算最终价是否相等
     */
    public Boolean moneyCheck(BigDecimal finalTotalPrice, BigDecimal orderFinalTotalPrice) {
        int i = finalTotalPrice.compareTo(orderFinalTotalPrice);
        if (i != 0) {
            throw new ForbiddenException(50008);
        }
        return true;
    }

    /**
     * 比如限定某类1000-200
     * 当前限定品类价格达到起付线
     *
     * @param skuOrderBOList
     * @return
     */
    public void canBeUsed(List<SkuOrderBO> skuOrderBOList) {
        // 得到同类型的 SKU累加值
        BigDecimal categoryTotalPrice = this.getCategoryTotalPrice(skuOrderBOList);
        this.categoryPriceOverBorder(categoryTotalPrice);
    }


    //todo
    public BigDecimal getCategoryTotalPrice(List<SkuOrderBO> skuOrderBOList) {
        BigDecimal categoryTotalPrice = this.coupon.getCategoryIds().stream()
                .map(id -> this.getCategoryPriceList(skuOrderBOList, id))
                .reduce(BigDecimal::add)
                .orElse(new BigDecimal("0"));
        System.out.println("categoryTotalPrice : "+categoryTotalPrice);
        return categoryTotalPrice;
    }

    private void categoryPriceOverBorder(BigDecimal categoryTotalPrice) {
        int i = categoryTotalPrice.compareTo(coupon.getFullMoney());
        if (i < 0) {
            throw new ForbiddenException(40008);
        }
    }

    /**
     * @param skuOrderBOList 根据分类将一组SKUOrderBO进行累加
     * @param cid
     * @return
     */
    public BigDecimal getCategoryPriceList(List<SkuOrderBO> skuOrderBOList, Long cid) {
        // 过滤出 同类的一组List 取出同类产品的累加价
        return skuOrderBOList.stream()
                .filter(bo -> bo.getCategoryId().equals(cid))
                .map(SkuOrderBO::getSkuPrice)
                .reduce(BigDecimal::add)
                .orElse(new BigDecimal("0"));

    }

    /**
     * @param totalServerPrice
     * @param categoryTotalPrice
     * @return 不属于优惠券优惠活动的商品
     */
    public BigDecimal getCategoryTotalPriceNotInCoupon(BigDecimal totalServerPrice, BigDecimal categoryTotalPrice) {
        return totalServerPrice.subtract(categoryTotalPrice);
    }

}