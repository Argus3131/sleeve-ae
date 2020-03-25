package com.yan.missyou.vo;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import com.yan.missyou.model.Spu;
import com.yan.missyou.utils.BeanMapper;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Paging的泛型约束不能加否则 会导致this.setItems(kList)需要强制类型转换;
 *
 * @author Argus
 * @className PagingDozer
 * @description: 用于转换po -> vo
 * @date 2020/3/16 20:36
 * @Version V1.0
 */
public class PagingDozer<T, K> extends Paging {

    @SuppressWarnings("unchecked")
    public PagingDozer(Page<T> page, Class<K> clazz) {
        // 调用父类Paging的初始化方法
        this.coverToPagingVO(page);
        // 调用转换方法
        this.converToVOList(page, clazz);
    }

    @SuppressWarnings("unchecked")
    void converToVOList(Page<T> page, Class<K> clazz) {
        List<T> tList = page.getContent();
        List<K> kList = BeanMapper.mapList(tList, clazz);
        this.setItems(kList);
    }

}