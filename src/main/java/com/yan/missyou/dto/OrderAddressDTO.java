package com.yan.missyou.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Argus
 * @className OrderAddressDTO
 * @description: TODO
 * @date 2020/3/31 13:07
 * @Version V1.0
 */
@Getter
@Setter
public class OrderAddressDTO {
    @JsonProperty("user_name")
    private String userName;

    private String province;

    private String city;

    private String county;

    private String mobile;
    @JsonProperty("national_code")
    private String nationalCode;
    @JsonProperty("postal_code")
    private String postalCode;

    private String detail;
}