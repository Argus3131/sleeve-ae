package com.yan.missyou.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Argus
 * @className BaseEntity
 * @description: 定为抽象类 作为目标 继承时间属性 让数据库自动更新时间
 *               注解 @JsonIgnore 避免序列化时候返回到前端
 *                 具体解释：https://www.cnblogs.com/zqyanywn/p/7753596.html
 *                 @MappedSuperclass :https://www.cnblogs.com/zqyanywn/p/7753596.html
 * @date 2020/3/12 23:27
 * @Version V1.0
 */
@Getter
@Setter
@MappedSuperclass
public abstract class BaseEntity implements Serializable {
    private static final long serialVersionUID = 6467879635176965829L;
    @JsonIgnore
    private Date createTime;
    @JsonIgnore
    private Date updateTime;
    @JsonIgnore
    private Date deleteTime;
}