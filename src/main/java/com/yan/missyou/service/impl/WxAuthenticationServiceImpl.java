package com.yan.missyou.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yan.missyou.dao.UserDao;
import com.yan.missyou.exception.http.ParamException;
import com.yan.missyou.model.User;
import com.yan.missyou.service.IWxAuthenticationService;
import com.yan.missyou.utils.JWTToken;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;

/**
 * @author Argus
 * @className TokenServiceImpl
 * @description: TODO
 * @date 2020/3/21 10:45
 * @Version V1.0
 */
@Slf4j
@Service
public class WxAuthenticationServiceImpl implements IWxAuthenticationService {
    @Autowired
    private UserDao userDao;
    @Value("${wx.appid}")
    private String wxAppid;
    @Value("${wx.secret}")
    private String wxSecret;
    @Value("${wx.code2Session}")
    private String code2SessionUrl;
    @Autowired
    private ObjectMapper mapper;


    /**
     * https://blog.csdn.net/qq_39474604/article/details/100016352
     *
     * @param code 是小程序发起的code码无法伪造 需要小程序去调试
     * @return
     */
    @Override
    public String code2session(String code) {
        if (StringUtils.isEmpty(code)) {
            return null;
        }
        String url = String.format(code2SessionUrl, wxAppid, wxSecret, code);
//        服务器通过code请求api换回session_key和openid
        RestTemplate restTemplate = new RestTemplate();
        String body = restTemplate.getForEntity(url, String.class).getBody();
        HashMap<String, Object> map = new HashMap<>();
        try {
            map = mapper.readValue(body, HashMap.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return generateJWT(map);
    }

    public String generateJWT(HashMap<String, Object> map) {
        String openid = (String) map.get("openid");
        User user = userDao.findByOpenid(openid);
        String token;
        if (null == openid) {
            throw new ParamException(20004);
        }
        if (null == user) {
            user = User.builder().openid(openid).build();
            userDao.save(user);
        }
        // 这边的JWT需要获取用户的id 还有默认的用户等级  因为是在c端 没有做vip等级划分因此就使用默认值即可
        token = JWTToken.makeToken(user.getId());
        return token;
    }
}