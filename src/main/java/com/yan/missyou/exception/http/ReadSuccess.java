package com.yan.missyou.exception.http;

/**
 * @author Argus
 * @className ReadSuccess
 * @description: TODO
 * @date 2020/3/30 14:30
 * @Version V1.0
 */
public class ReadSuccess extends HttpException{


    private static final long serialVersionUID = 1062021868528549364L;

    public ReadSuccess(int code) {
        this.httpStatusCode = 200;
        this.code = code;
    }

}