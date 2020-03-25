package com.yan.missyou.controller;

import com.yan.missyou.exception.http.NotFoundException;
import com.yan.missyou.model.GridCategory;
import com.yan.missyou.service.ICategoryGridService;
import com.yan.missyou.service.ICategoryService;
import com.yan.missyou.vo.CategoriesVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Argus
 * @className Category
 * @description: TODO
 * @date 2020/3/17 19:58
 * @Version V1.0
 */
@RestController
@RequestMapping("/v1/category")
public class CategoryController {
    @Autowired
    private ICategoryService iCategoryService;
    @Autowired
    private ICategoryGridService iCategoryGridService;


    @GetMapping("/all")
    @ResponseBody
    public CategoriesVO getCategoryAll() {
        CategoriesVO allCategory = iCategoryService.getAllCategory();
        if(CollectionUtils.isEmpty(allCategory.getRoots()) && CollectionUtils.isEmpty(allCategory.getRoots())) {
            throw new NotFoundException(30001);
        }
        return allCategory;
    }

    @GetMapping("/grid/all")
    @ResponseBody
    public List<GridCategory> getCategoryGridAll() {
        List<GridCategory> allGridCategory = iCategoryGridService.getAllGridCategory();
        if (CollectionUtils.isEmpty(allGridCategory)) {
            throw new NotFoundException(30009);
        }
        return allGridCategory;
    }
}