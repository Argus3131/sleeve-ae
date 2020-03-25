/**
 * @ClassName UnifyResponse
 * @description: TODO
 * @author Argus
 * @Date 2020/3/10 15:27
 * @Version V1.0
 */
package com.yan.missyou.core;

import lombok.Getter;
import lombok.Setter;

/**
 * 统一错误响应类
 * 让前端体验更好
 * {
 *     code:10001,
 *     message:xxx,
 *     request:GET url
 * }
 */
@Getter
public class UnifyResponse {
    private int code;
    private String message;
    private String request;

    public UnifyResponse(int code, String message, String request) {
        this.code = code;
        this.message = message;
        this.request = request;
    }
}
