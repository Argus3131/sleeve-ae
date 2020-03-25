package com.yan.missyou.dao;

import com.yan.missyou.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author Argus
 * @className CategoryDao
 * @description: TODO
 * @date 2020/3/18 12:18
 * @Version V1.0
 */
public interface CategoryDao extends JpaRepository<Category,Long> {

    List<Category> findByIsRootEqualsOrderByIndexAsc(Boolean isRoot);
}
