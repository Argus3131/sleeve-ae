package com.yan.missyou.vo;

import com.yan.missyou.model.Coupon;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Argus
 * @className CouponCategoryVO
 * @description: TODO
 * @date 2020/3/30 17:34
 * @Version V1.0
 */
@Getter
@Setter
@NoArgsConstructor
public class CouponCategoryVO extends CouponPureVO{
   private List<CategoryPureVO> categoryList;

   public CouponCategoryVO(Coupon coupon) {
      super(coupon);
      this.categoryList = CategoryPureVO.getList(coupon.getCategoryList());
   }

   public static List<CouponCategoryVO> getCouponCategoryVOList(List<Coupon> couponList) {
      return couponList.stream().map(CouponCategoryVO::new).collect(Collectors.toList());
   }
}