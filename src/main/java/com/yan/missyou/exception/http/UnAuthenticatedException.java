/**
 * @ClassName ForbiddenException
 * @description: TODO
 * @author Argus
 * @Date 2020/3/10 15:05
 * @Version V1.0
 */
package com.yan.missyou.exception.http;

import lombok.Getter;

/**
 * 用户没有访问权限
 */
@Getter
public class UnAuthenticatedException extends HttpException{

    private static final long serialVersionUID = -5809295037703223722L;

    public UnAuthenticatedException(int code) {
        this.httpStatusCode = 401;
        this.code = code;
    }
}
