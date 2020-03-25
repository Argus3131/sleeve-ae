package com.yan.missyou.service;

import com.yan.missyou.dto.TokenDTO;

/**
 * @author Argus
 * @className ITokenService
 * @description: TODO
 * @date 2020/3/21 10:44
 * @Version V1.0
 */
public interface IAuthenticationService {

    void getTokenByEmail(TokenDTO userData);

    void validateByWx(TokenDTO userData);

    void register();
}
