package com.yan.missyou.service;

import com.yan.missyou.model.Banner;

/**
 * @author Argus
 * @className IBannerService
 * @description: TODO
 * @date 2020/3/16 9:25
 * @Version V1.0
 */
public interface IBannerService {

     Banner getBannerById(Long id);

     Banner getBannerByName(String name);
}
