package com.yan.missyou.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
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
 *                 Spring boot使用jpa自动添加当前时间:
 *                 https://www.jianshu.com/p/0c8cde81de7d
 * @date 2020/3/12 23:27
 * @Version V1.0
 */
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@MappedSuperclass
public abstract class BaseEntity implements Serializable {
    private static final long serialVersionUID = 6467879635176965829L;
    @CreatedDate
    @JsonIgnore
    private Date createTime;
    @LastModifiedDate
    @JsonIgnore
    private Date updateTime;
    @JsonIgnore
    private Date deleteTime;
}