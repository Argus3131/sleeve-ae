/**
 * @ClassName NotFoundException
 * @description: TODO
 * @author Argus
 * @Date 2020/3/10 15:02
 * @Version V1.0
 */
package com.yan.missyou.exception.http;

import lombok.Getter;

/**
 * 服务器无法正常提供信息，
 * 或是服务器无法回应，且不知道原因所返回的页面
 */
@Getter
public class NotFoundException extends HttpException{
    private static final long serialVersionUID = -4656090217054885851L;

    public NotFoundException(int code) {
        this.httpStatusCode = 404;
        this.code = code;
    }
}
