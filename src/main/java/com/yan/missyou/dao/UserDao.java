package com.yan.missyou.dao;

import com.yan.missyou.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Argus
 * @className TokenDao
 * @description: TODO
 * @date 2020/3/21 18:06
 * @Version V1.0
 */
@Repository
public interface UserDao extends JpaRepository<User,Long> {

    User findByOpenid(String openid);

    User findByEmail(String email);

}