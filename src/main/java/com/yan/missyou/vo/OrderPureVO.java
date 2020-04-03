/**
 * @作者 7七月
 * @微信公号 林间有风
 * @开源项目 $ http://talelin.com
 * @免费专栏 $ http://course.talelin.com
 * @我的课程 $ http://imooc.com/t/4294850
 * @创建时间 2020-03-30 13:42
 */
package com.yan.missyou.vo;

import com.yan.missyou.model.Order;
import com.yan.missyou.model.OrderSku;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class OrderPureVO extends Order {
    private static final long serialVersionUID = 7920140691460902064L;
    private Long period;
    private Date createTime;

    public OrderPureVO(Order order, Long period) {

        BeanUtils.copyProperties(order, this);
        this.period = period;
    }
}

