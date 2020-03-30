package com.yan.missyou.vo;

import com.yan.missyou.model.Activity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Argus
 * @className ActivityPureVO
 * @description: TODO
 * @date 2020/3/25 11:17
 * @Version V1.0
 */
@Getter
@Setter
@NoArgsConstructor
public class ActivityPureVO {

    private Long id;
    private String title;
    private String entranceImg;
    private Boolean online;
    private String remark;
    private Date startTime;
    private Date endTime;


    public ActivityPureVO(Activity activity) {
        try {
            BeanUtils.copyProperties(this,activity);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

    }



}