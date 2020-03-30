package com.yan.missyou.service.impl;

import com.yan.missyou.dao.ActivityDao;
import com.yan.missyou.model.Activity;
import com.yan.missyou.service.IActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Argus
 * @className ActivityServiceImpl
 * @description: TODO
 * @date 2020/3/25 11:14
 * @Version V1.0
 */
@Service
public class ActivityServiceImpl implements IActivityService {
    @Autowired
    private ActivityDao activityDao;
    @Override
    public Activity getHomeActivityByName(String name) {
        return activityDao.getActivityByName(name);
    }
}