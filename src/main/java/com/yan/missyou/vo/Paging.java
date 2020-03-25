package com.yan.missyou.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * 这个类作为返回前端的泛型类
 * 返回 对应元类类型的分页数据List
 * 分页对象 类似jpa原生的Page对象功能一样保存分页数据 只不过自定义了一下
 * @author Argus
 * @className Paging
 * @description: TODO
 * @date 2020/3/16 20:26
 * @Version V1.0
 */
@Getter
@Setter
@NoArgsConstructor
public class Paging<T> {
    private Long total;
    private Integer count;
    private Integer page;
    private Integer totalPage;
    private List<T> items;

    public Paging(Page<T> page) {
        this.items = page.getContent();
        this.coverToPagingVO(page);
    }

    public void coverToPagingVO(Page<T> page) {
        this.totalPage = page.getTotalPages();
        this.page = page.getPageable().getPageNumber();
        this.count = page.getPageable().getPageSize();
        this.total = page.getTotalElements();
    }
}