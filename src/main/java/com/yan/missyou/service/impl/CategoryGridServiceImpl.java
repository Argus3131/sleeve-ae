package com.yan.missyou.service.impl;

import com.yan.missyou.dao.CategoryGridDao;
import com.yan.missyou.model.GridCategory;
import com.yan.missyou.service.ICategoryGridService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Argus
 * @className CategoryGridService
 * @description: TODO
 * @date 2020/3/18 15:49
 * @Version V1.0
 */
@Service
public class CategoryGridServiceImpl implements ICategoryGridService {
    @Autowired
    private CategoryGridDao categoryGridDao;

    @Override
    public List<GridCategory> getAllGridCategory(){
        return categoryGridDao.findAll();
    }
}