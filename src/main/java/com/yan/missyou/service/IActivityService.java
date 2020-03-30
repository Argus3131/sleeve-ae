package com.yan.missyou.service;

import com.yan.missyou.model.Activity;

/**
 * @author Argus
 * @className IActivityService
 * @description: TODO
 * @date 2020/3/25 11:13
 * @Version V1.0
 */
public interface IActivityService {

    Activity getHomeActivityByName(String name);
}
