package com.yan.missyou.exception.http;

/**
 * @author Argus
 * @className DeleteSuccess
 * @description: TODO
 * @date 2020/3/30 14:29
 * @Version V1.0
 */
public class DeleteSuccess extends HttpException{
    private static final long serialVersionUID = 4998549348944136897L;

    public DeleteSuccess(int code) {
        this.httpStatusCode = 200;
        this.code = code;
    }
}