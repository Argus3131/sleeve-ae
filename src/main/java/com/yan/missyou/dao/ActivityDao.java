package com.yan.missyou.dao;

import com.yan.missyou.model.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

/**
 * @author Argus
 * @className ActivityDao
 * @description: TODO
 * @date 2020/3/25 11:54
 * @Version V1.0
 */
@Repository
public interface ActivityDao extends JpaRepository<Activity,Long> {
    Activity getActivityByName(String name);

    Optional<Activity> getByCouponListId(Long cid);
}
