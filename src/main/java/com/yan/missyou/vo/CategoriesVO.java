package com.yan.missyou.vo;

import com.yan.missyou.model.Category;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Argus
 * @className CategoryVO
 * @description: TODO
 * @date 2020/3/18 11:44
 * @Version V1.0
 */
@Getter
@Setter
public class CategoriesVO {
    private List<CategoryPureVO> roots;
    private List<CategoryPureVO> subs;

    public CategoriesVO(List<Category> roots, List<Category> subs) {
        this.roots = coverToAttr(roots);
        this.subs = coverToAttr(subs);
    }

    /**
     * stream 流去遍历 但是不会改变数据源
     *
     * @param list
     * @return
     */
    private List<CategoryPureVO> coverToAttr(List<Category> list) {
        return list.stream().map(item -> {
//            System.out.println(item);
//            CategoryPureVO pureVO = new CategoryPureVO();
//            pureVO.setCategoryPureVO(item);
            return new CategoryPureVO(item);
        }).collect(Collectors.toList());
    }
}