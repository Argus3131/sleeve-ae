package com.yan.missyou.service.impl;

import com.yan.missyou.dao.SpuDao;
import com.yan.missyou.dao.ThemeDao;
import com.yan.missyou.model.Spu;
import com.yan.missyou.service.ISpuService;
import com.yan.missyou.vo.SpuSimplifyVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Argus
 * @className SpuServiceImpl
 * @description: TODO
 * @date 2020/3/16 16:17
 * @Version V1.0
 */
@Service
public class SpuServiceImpl implements ISpuService {
    @Autowired
    private SpuDao spuDao;

    @Override
    public Spu findSpuDetail(Long id) {
        System.out.println(id);
        return spuDao.findOneById(id);
    }

    /**
     * 返回的是spu数据
     * @return
     */
    @Override
    public Page<Spu> findSpuLatestPaging(Integer page,Integer size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createTime","updateTime").descending());
        // 返回的是
        return this.spuDao.findAll(pageable);
    }

    @Override
    public Page<Spu> findSpuByCategoryId(Long id, Boolean isRoot, Integer page, Integer size) {
        Pageable pageable = new PageRequest(page, size);
        if (isRoot) {
            return spuDao.findByRootCategoryIdOrderByCreateTimeDesc(id,pageable);
        }
        return spuDao.findByCategoryIdOrderByCreateTimeDesc(id,pageable);
    }
}