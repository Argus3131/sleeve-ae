package com.yan.missyou.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Argus
 * @className JWTToken
 * @description: 生成JWT令牌https://blog.csdn.net/qq_39474604/article/details/100016352
 * https://www.jianshu.com/p/a27dbf92f4a8
 * @date 2020/3/21 17:57
 * @Version V1.0
 */
@Component
public class JWTToken {
    private static String secret;
    @Value("${missyou.security.token-expired-in}")
    private static Integer expiredTimeIn;

    public static Integer defaultScope = 8;

    @Value("${missyou.security.jwt-key}")
    private void setSECRET(String secret) {
        JWTToken.secret = secret;
    }

    @Value("${missyou.security.token-expired-in}")
    private void setExpiredTimeIn(Integer expiredTime) {
        JWTToken.expiredTimeIn = expiredTime;
    }

    /**
     * 传入scope等级
     *
     * @param uid
     * @return
     */
    public static String makeToken(Long uid) {
        return JWTToken.getToken(uid, JWTToken.defaultScope);
    }

    public static String makeToken(Long uid, Integer scope) {
        return JWTToken.getToken(uid, scope);
    }

    /**
     * 解析token 读取令牌 Claim的方法
     *
     * @return
     */
    public static Map<String, Claim> verifyAndGetClaims(String token) {
//        假如需要进行一个过期时间判断就得拿expiredTime和now做比较
        // 解密
        Algorithm algorithm = Algorithm.HMAC256(JWTToken.secret);
        JWTVerifier verifier = JWT.require(algorithm).build();
        Map<String, Claim> map;
        DecodedJWT jwt;
        // 解析token
        try {
            jwt = verifier.verify(token);
        } catch (JWTVerificationException e) {
            return null;
        }
        map = jwt.getClaims();
        return map;
    }

    /**
     * 校验令牌是否失效
     *
     * @param token
     * @return
     */
    public static Boolean verify(String token) {
//        假如需要进行一个过期时间判断就得拿expiredTime和now做比较
        // 解密
        Algorithm algorithm = Algorithm.HMAC256(JWTToken.secret);
        JWTVerifier verifier = JWT.require(algorithm).build();
        Map<String, Claim> map;
        // 解析token
        try {
            verifier.verify(token);
        } catch (JWTVerificationException e) {
            return false;
        }
        return true;
    }

    public static String getToken(Long uid, Integer scope) {
        Algorithm algorithm = Algorithm.HMAC256(secret);
        Map<String, Date> map = JWTToken.calculateExpiredIssues();
        String token = null;
        try {
            token = JWT.create()
                    //  记录用户id
                    .withClaim("uid", uid)
                    //  level scope等级
                    .withClaim("scope", scope)
                    //  设置过期时间为10天
                    .withExpiresAt(map.get("expiredTime"))
                    .withIssuedAt(map.get("now"))
                    //  加密
                    .sign(algorithm);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return token;

    }

    private static Map<String, Date> calculateExpiredIssues() {
        // 使用map去记录两个时间
        Map<String, Date> map = new HashMap<>();
        Calendar calendar = Calendar.getInstance();
        // 获取一个以秒计算的当前时间
        Date now = calendar.getTime();
        // 调用add方法 累加时间
        calendar.add(Calendar.SECOND, JWTToken.expiredTimeIn);
        // 设置一个当前的 登录时间
        map.put("now", now);
        // 设置新增后的 过期时间 以秒计算获取时间
        map.put("expiredTime", calendar.getTime());
        return map;
    }

}