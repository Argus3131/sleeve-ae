package com.yan.missyou.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author Argus
 * @className Banner
 * @description: TODO
 * @date 2020/3/12 23:08
 * @Version V1.0
 * 序列化需要getter和setter
 */
@Entity
@Getter
@Setter
public class Banner extends BaseEntity implements Serializable {
    private static final long serialVersionUID = -2603400293505352417L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private String title;
    private String img;
    /**
     * 单向一对多
     * 1 对 多 的 导航属性
     * 对应业务里面的一个Banner可能存在多个BannerItem信息的情况
     * 映射的是 BannerItem 的 banner_id字段作为逻辑外键
     * 1.声明关系：@OneToMany
     * 2.配置外键（配置中间表）：配置外键@JoinColumn
     * 在实体类上（1的一方） 添加了外键配置 所以有了维护外键的作用
     * 只需add到items即可
     *
     * tips: 这边虽然设置了fetch = FetchType.LAZY但是序列化的时候因为使用了getter读取
     * 还是会触发  因此会在访问接口时候读取到List<BannerItem> items
     * 官方内置了jackson库进行了序列化
     */
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "bannerId")
    private List<BannerItem> items;
}