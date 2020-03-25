/**
 * @ClassName BannerController
 * @description: TODO
 * @author Argus
 * @Date 2020/3/8 14:10
 * @Version V1.0
 */
package com.yan.missyou.controller;

import com.yan.missyou.exception.http.NotFoundException;
import com.yan.missyou.model.Banner;
import com.yan.missyou.service.IBannerService;
import com.yan.missyou.validators.ScopeLevel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/v1/banner")
public class BannerController {
    @Autowired
    private IBannerService iBannerService;

//    @ScopeLevel
    @GetMapping("/id/{id}")
    @ResponseBody
    public Banner getBannerById(@PathVariable @NotNull Long id) {
        Banner banner = iBannerService.getBannerById(id);
        if (banner == null) {
            throw new NotFoundException(30005);
        }
        return banner;
    }

    @GetMapping("/name/{name}")
    @ResponseBody
    public Banner getBannerById(@PathVariable @NotBlank String name) {
        Banner banner = iBannerService.getBannerByName(name);
        if (banner == null) {
            throw new NotFoundException(30005);
        }
        return banner;
    }
}
