package com.yan.missyou.exception.http;

import lombok.Getter;

/**
 * @author Argus
 * @className ValidationException
 * @description: TODO
 * @date 2020/3/17 20:51
 * @Version V1.0
 */
@Getter
public class ServerErrorException extends HttpException{
    private static final long serialVersionUID = 2601505955669397035L;

    public ServerErrorException(Integer code){
        this.httpStatusCode = 500;
        this.code = code;
    }
}