package com.yan.missyou.service.impl;

import com.yan.missyou.dao.BannerDao;
import com.yan.missyou.model.Banner;
import com.yan.missyou.service.IBannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Argus
 * @className BannerServiceImpl
 * @description: TODO
 * @date 2020/3/16 9:29
 * @Version V1.0
 */
@Service
public class BannerServiceImpl implements IBannerService {
    @Autowired
    private BannerDao bannerDao;

    @Transactional
    @Override
    public Banner getBannerById(Long id) {
        Banner banner = bannerDao.findBannerById(id);
        return banner;
    }

    @Override
    public Banner getBannerByName(String name) {
        Banner banner = bannerDao.findBannerByName(name);
        return banner;
    }
}