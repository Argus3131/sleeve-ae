package com.yan.missyou.controller;

import com.yan.missyou.exception.http.NotFoundException;
import com.yan.missyou.model.Theme;
import com.yan.missyou.service.IThemeService;
import com.yan.missyou.utils.BeanMapper;
import com.yan.missyou.vo.ThemeSimplifyVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * @author Argus
 * @className ThemeController
 * @description: TODO
 * @date 2020/3/16 10:28
 * @Version V1.0
 */
@RestController
@RequestMapping("/v1/theme")
public class ThemeController {
    @Autowired
    private IThemeService iThemeService;

    @GetMapping("/by/names")
    @ResponseBody
    public List<ThemeSimplifyVO> getThemeListByNames(@RequestParam @NotBlank String names) {
        System.out.println(names);
        List<String> namesList = Arrays.asList(names.split(","));
        List<Theme> themeList = iThemeService.findThemesByNames(namesList);
        List<ThemeSimplifyVO> themeSimplifyVOList = BeanMapper.mapList(themeList, ThemeSimplifyVO.class);
        return themeSimplifyVOList;
    }

    @GetMapping("/name/{name}/with_spu")
    @ResponseBody
    public Theme getThemeListByName(@PathVariable @NotBlank String name) {
//        Theme theme = iThemeService.findThemeByName(name);
//        if (null == theme) throw new NotFoundException(30003);
        Optional<Theme> optionalTheme = this.iThemeService.findThemeByName(name);
        return optionalTheme.orElseThrow(()->new NotFoundException(30003));
    }


}