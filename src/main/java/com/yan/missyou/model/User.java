package com.yan.missyou.model;

import com.yan.missyou.utils.JsonAndMap;
import lombok.*;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Objects;

/**
 * @author Argus
 * @className User
 * @description: TODO
 * @date 2020/3/21 18:07
 * @Version V1.0
 */
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Setter
@Builder
@Where(clause = "delete_time is null")
@ToString
public class User extends BaseEntity implements Serializable {
    private static final long serialVersionUID = -195004502368190595L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String openid;
    private String nickname;
    private Integer unifyUid;
    private String email;
    private String password;
    private String mobile;
    @Convert(converter = JsonAndMap.class)
    private HashMap<String,Object> wxProfile;




}