package com.yan.missyou.dao;

import com.yan.missyou.model.Category;
import com.yan.missyou.model.GridCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author Argus
 * @className Category
 * @description: TODO
 * @date 2020/3/18 13:26
 * @Version V1.0
 */
public interface CategoryGridDao extends JpaRepository<GridCategory,Long> {

    List<GridCategory> findAll();
}
