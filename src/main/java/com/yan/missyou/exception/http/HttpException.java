/**
 * @ClassName HttpException
 * @description: TODO
 * @author Argus
 * @Date 2020/3/10 13:06
 * @Version V1.0
 */
package com.yan.missyou.exception.http;

import lombok.Getter;

/**
 * 自定义异常类
 * 作为一个父类 可以供很多自定义异常子类去继承属性
 * 这边采用一个比较折中的方式去定义异常类 就是
 * 根据http状态码去定义类 状态码的个数是有限的否则根据异常情况去定义就太多了
 */
@Getter
public class HttpException extends RuntimeException{
    private static final long serialVersionUID = -2498777186748463979L;
    protected Integer code;
    protected Integer httpStatusCode = 500;
}
