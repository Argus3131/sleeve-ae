package com.yan.missyou.logic;

import com.yan.missyou.bo.SkuOrderBO;
import com.yan.missyou.dto.OrderDTO;
import com.yan.missyou.dto.SkuInfoDTO;
import com.yan.missyou.exception.http.ForbiddenException;
import com.yan.missyou.model.OrderSku;
import com.yan.missyou.model.Sku;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Argus
 * @className OrderChecker
 * @description: check:
 * 校验商品：
 * 商品是否下架
 * 优惠券在当前品类是否可用
 * 校验金额：
 * 优惠券在当前的优惠套餐下的边界金额是否达到
 * 校验折扣之后的价格是否与前端传递的价格一致否则throw Exception
 * @date 2020/4/1 8:39
 * @Version V1.0
 */
public class OrderChecker {
    private OrderDTO orderDTO;
    private List<Sku> serverSkuList;
    private CouponChecker couponChecker;
    private Integer maxCount;
    @Getter
    private List<OrderSku> orderSkuList = new ArrayList<>(0);

    public OrderChecker(OrderDTO orderDTO, List<Sku> skuList, Integer maxCount, CouponChecker couponChecker) {
        this.orderDTO = orderDTO;
        this.serverSkuList = skuList;
        this.couponChecker = couponChecker;
        this.maxCount = maxCount;
    }


    public String getLeaderImg() {
        return this.serverSkuList.get(0).getImg();
    }

    public String getLeaderTitle() {
        return this.serverSkuList.get(0).getTitle();
    }

    public Integer getTotalCount() {
        return this.orderDTO.getSkuInfoList().stream().map(SkuInfoDTO::getCount).reduce(Integer::sum).orElse(0);
    }

    public void isOk() {
        BigDecimal totalServerPrice = new BigDecimal("0");
        List<SkuOrderBO> skuOrderBOList = new ArrayList<>(0);
        // 校验 前端传递的sku购物车集合 和  后端查询得到的购物车列表长度校验
        // 因为 serverSKUList 是根据orderDTO去查到的
        // 下架 假如查不到就下架了
        System.out.println(orderDTO.getSkuInfoList().size());
        System.out.println(this.serverSkuList.size());
        // 前端Id不重复的商品和后端的商品进行比较
//        int feSize = this.filterSkuInfoListSkus();
        this.skuNotOnSale(orderDTO.getSkuInfoList().size(), this.serverSkuList.size());
        for (int i = 0; i < serverSkuList.size(); i++) {
            Sku sku = serverSkuList.get(i);
            // serverSkuList 是根据  skuInfoList 查询得到的
            SkuInfoDTO skuInfoDTO = orderDTO.getSkuInfoList().get(i);
            // 售罄
            this.isSaleOut(sku);
            // 超出库存
            this.isOverStock(sku, skuInfoDTO);
            // 超出最大购买数量
            this.isOverCount(skuInfoDTO);
            BigDecimal price = this.getPrice(sku, skuInfoDTO.getCount());
            totalServerPrice = totalServerPrice.add(price);
            // 将Sku 和skuInfoDTO转换为BO
            skuOrderBOList.add(new SkuOrderBO(sku, skuInfoDTO));
            this.orderSkuList.add(new OrderSku(sku, skuInfoDTO));
        }
        System.out.println(orderDTO.getTotalPrice());
        System.out.println(totalServerPrice);
        this.totalPriceCheck(orderDTO.getTotalPrice(), totalServerPrice);
        if (couponChecker != null) {
            couponChecker.isOk(totalServerPrice, skuOrderBOList);
        }
    }

    private int filterSkuInfoListSkus() {
        int  accumulater = 0;
        List<SkuInfoDTO> skuInfoDTOList= orderDTO.getSkuInfoList();
        for (int i = 0; i < skuInfoDTOList.size(); i++) {
            for (int j = 0; j < skuInfoDTOList.size()-i-1; j++) {
                if (skuInfoDTOList.get(j).getId().equals(skuInfoDTOList.get(j + 1).getId())) {
                    accumulater+=1;
                }
            }
        }
        return accumulater;
    }

    public void skuNotOnSale(int count1, int count2) {

        if (count1 != count2) {
            if (this.filterSkuInfoListSkus() == count2) {
                throw new ForbiddenException(50012);
            }
            throw new ForbiddenException(50002);
        }
    }

    public void isSaleOut(Sku sku) {
        if (sku.getStock() == 0) {
            throw new ForbiddenException(50001);
        }
    }

    private void isOverStock(Sku sku, SkuInfoDTO skuInfoDTO) {
        if (skuInfoDTO.getCount() > sku.getStock()) {
            throw new ForbiddenException(50003);
        }
    }

    private void isOverCount(SkuInfoDTO skuInfoDTO) {
        if (skuInfoDTO.getCount() > maxCount) {
            throw new ForbiddenException(50004);
        }
    }


    public BigDecimal getPrice(Sku sku, Integer count) {
        BigDecimal multiply = sku.getActcalMoney().multiply(new BigDecimal(count));
        return multiply;
    }


    /**
     * 校验前端总价 和 后端计算总价是否相等
     *
     * @param totalPrice
     * @param totalServerPrice
     * @return
     */
    public void totalPriceCheck(BigDecimal totalPrice, BigDecimal totalServerPrice) {
        int i = totalPrice.compareTo(totalServerPrice);
        if (i != 0) {
            throw new ForbiddenException(50005);
        }
    }


}