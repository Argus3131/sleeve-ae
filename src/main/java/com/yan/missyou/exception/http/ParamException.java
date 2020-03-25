package com.yan.missyou.exception.http;

/**
 * @author Argus
 * @className ParamException
 * @description: TODO
 * @date 2020/3/24 11:03
 * @Version V1.0
 */
public class ParamException extends HttpException{

    private static final long serialVersionUID = -5905076629374805602L;

    public ParamException(int code) {
        // 请求无效
        this.httpStatusCode = 400;
        this.code = code;
    }
}