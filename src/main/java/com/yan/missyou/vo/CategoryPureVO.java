package com.yan.missyou.vo;

import com.yan.missyou.model.BaseEntity;
import com.yan.missyou.model.Category;
import com.yan.missyou.model.Coupon;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.beanutils.BeanUtils;

import javax.persistence.Id;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Argus
 * @className CategoryPureVO
 * @description: VO作用是将其转换成
 * @date 2020/3/18 14:20
 * @Version V1.0
 */
@Getter
@Setter
public class CategoryPureVO extends BaseEntity {
    private static final long serialVersionUID = 19884092382914885L;
    private int id;
    private String name;
    private Boolean isRoot;
    private String img;
    private Integer parentId;
    private Integer index;

    public CategoryPureVO(Category category) {
        try {
            //                          待转换对象  数据源
            BeanUtils.copyProperties(this, category);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public static List<CategoryPureVO> getList(List<Category> categoryList) {
        return categoryList.stream().map(CategoryPureVO::new).collect(Collectors.toList());
    }
}