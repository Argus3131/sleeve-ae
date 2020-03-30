package com.yan.missyou.exception.http;

/**
 * @author Argus
 * @className InsertSuccess
 * @description: TODO
 * @date 2020/3/30 14:29
 * @Version V1.0
 */
public class InsertSuccess extends HttpException{

    private static final long serialVersionUID = 4163042274272108009L;

    public InsertSuccess(int code) {
        this.httpStatusCode = 201;
        this.code = code;
    }

    public static void success(Integer code) {
        throw new InsertSuccess(code);
    }
}