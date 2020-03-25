package com.yan.missyou.service;

/**
 * @author Argus
 * @className IWxAuthenticationService
 * @description: TODO
 * @date 2020/3/21 14:54
 * @Version V1.0
 */
public interface IWxAuthenticationService {
    /**
     *
     * @param code 是小程序发起的code码无法伪造 需要小程序去调试
     * @return
     */
    String code2session(String code);
}
