package com.yan.missyou.model;

import com.fasterxml.jackson.core.type.TypeReference;
import com.yan.missyou.dto.OrderAddressDTO;
import com.yan.missyou.utils.GenericAndJson;
import lombok.*;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author Argus
 * @className Order
 * @description: TODO
 * @date 2020/4/1 8:01
 * @Version V1.0
 */
@Entity
@Getter
@Setter
@Where(clause = "delete_time is null")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "`Order`")
public class Order extends BaseEntity {
    private static final long serialVersionUID = -7624328123050828695L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String orderNo;
    private Long userId;
    private BigDecimal totalPrice;
    private Long totalCount;
    private String snapImg;
    private String snapTitle;
    private String snapItems;
    private String snapAddress;
    private String prepayId;
    private BigDecimal finalTotalPrice;
    private Integer status;
    private Date expiredTime;
    private Date placedTime;

    public OrderAddressDTO getSnapAddress() {
        OrderAddressDTO orderAddressDTO = GenericAndJson.jsonToObjOrCollection(this.snapAddress, new TypeReference<OrderAddressDTO>() {
        });
        return orderAddressDTO;
    }

    public void setSnapAddress(OrderAddressDTO orderAddressDTO) {
        this.snapAddress = GenericAndJson.ObjToJson(orderAddressDTO);
    }


    public List<OrderSku> getSnapItems() {
        List<OrderSku> orderSkuList = GenericAndJson.jsonToObjOrCollection(this.snapItems, new TypeReference<List<OrderSku>>() {
        });
        return orderSkuList;
    }

    public void setSnapItems(List<OrderSku> orderSkuList) {
        this.snapItems = GenericAndJson.ObjToJson(orderSkuList);
    }
}