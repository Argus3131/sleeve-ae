package com.yan.missyou.dao;

import com.yan.missyou.model.Banner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Argus
 * @className BannerDao
 * @description: TODO
 * @date 2020/3/16 9:32
 * @Version V1.0
 */
@Repository
public interface BannerDao extends JpaRepository<Banner,Long> {

    Banner findBannerById(Long id);

    Banner findBannerByName(String name);
}