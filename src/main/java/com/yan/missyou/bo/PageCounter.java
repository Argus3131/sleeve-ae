package com.yan.missyou.bo;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * @author Argus
 * @className Paging
 * @description: 作为转换分页数据的bo对象 返回给controller
 * @date 2020/3/16 18:55
 * @Version V1.0
 */
@Getter
@Setter
@Builder
public class PageCounter {
    // 显示spu记录的条数
    private Integer count;
    // 从第几页开始显示一页包含了20*1那就包含了20条记录
    private Integer page;

    public static PageCounter initPageCounter(Integer start,Integer count) {
        Integer page = start / count;
        return PageCounter.builder().count(count).page(page).build();
    }

}