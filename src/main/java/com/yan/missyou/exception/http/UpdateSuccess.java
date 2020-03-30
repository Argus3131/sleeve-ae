package com.yan.missyou.exception.http;

/**
 * @author Argus
 * @className UpdateSuccess
 * @description: TODO
 * @date 2020/3/30 14:29
 * @Version V1.0
 */
public class UpdateSuccess extends HttpException {

    private static final long serialVersionUID = -1012176195678941716L;

    public UpdateSuccess(int code) {
        this.httpStatusCode = 200;
        this.code = code;
    }
}