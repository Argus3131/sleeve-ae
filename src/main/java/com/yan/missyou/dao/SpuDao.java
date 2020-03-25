package com.yan.missyou.dao;

import com.yan.missyou.model.Spu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Argus
 * @className SpuDao
 * @description: TODO
 * @date 2020/3/16 16:11
 * @Version V1.0
 */
@Repository
public interface SpuDao extends JpaRepository<Spu,Long> {

    Spu findOneById(Long id);

    /**
     * 根据二级节点categoryId 查询符合条件的 倒序排序的 SPU分页数据
     * @param categoryId 查询条件-二级节点
     * @param pageable 分页参数
     * @return Page<Spu>
     */
    Page<Spu> findByCategoryIdOrderByCreateTimeDesc(Long categoryId, Pageable pageable);

    /**
     * 根据根节点 rootCategoryId 查询符合条件的 倒序排序的 SPU分页数据
     * @param rootCategoryId 查询条件-根节点
     * @param pageable 分页参数
     * @return Page<Spu>
     */
    Page<Spu> findByRootCategoryIdOrderByCreateTimeDesc(Long rootCategoryId, Pageable pageable);
}
