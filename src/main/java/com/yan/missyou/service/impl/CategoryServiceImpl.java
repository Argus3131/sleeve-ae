package com.yan.missyou.service.impl;

import com.yan.missyou.dao.BannerDao;
import com.yan.missyou.dao.CategoryDao;
import com.yan.missyou.dao.CategoryGridDao;
import com.yan.missyou.model.Category;
import com.yan.missyou.model.GridCategory;
import com.yan.missyou.service.ICategoryService;
import com.yan.missyou.vo.CategoriesVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Argus
 * @className CategoryServiceImpl
 * @description: TODO
 * @date 2020/3/17 20:05
 * @Version V1.0
 */
@Service
public class CategoryServiceImpl implements ICategoryService {
    @Autowired
    private CategoryDao categoryDao;


    @Override
    public CategoriesVO getAllCategory() {
        List<Category> roots = categoryDao.findByIsRootEqualsOrderByIndexAsc(true);
        List<Category> subs = categoryDao.findByIsRootEqualsOrderByIndexAsc(false);
        CategoriesVO categoriesVO = new CategoriesVO(roots,subs);
        return categoriesVO;
    }


}