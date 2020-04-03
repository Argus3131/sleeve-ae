package com.yan.missyou.dao;

import com.yan.missyou.model.Sku;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author Argus
 * @className SkuDao
 * @description: https://www.jianshu.com/p/d2ac26ca6525
 * @date 2020/4/2 9:37
 * @Version V1.0
 */
public interface SkuDao extends JpaRepository<Sku,Long> {

    List<Sku> findAllByIdIn(List<Long> ids);

    /**
     * 乐观锁：乐观并发的控制 每次扣减库存前 会先校验库存
     * 因为是写在where条件判断里面 所以具有原子性
     * @param sid   购买sku的id
     * @param quantity 购买的sku数量
     * @return
     */
    @Modifying
    @Query("update Sku s\n" +
            "set s.stock = s.stock - :quantity\n" +
            "where  s.id = :sid\n" +
            "and s.stock >= :quantity\n")
    int reduceStock(@Param("sid") Long sid, @Param("quantity") Long quantity);
}
