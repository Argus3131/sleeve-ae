package com.yan.missyou.service;

import com.yan.missyou.model.Theme;

import java.util.List;
import java.util.Optional;

/**
 * @author Argus
 * @className IThemeService
 * @description: TODO
 * @date 2020/3/16 10:29
 * @Version V1.0
 */
public interface IThemeService {

    List<Theme> findThemesByNames(List<String> names);

    Optional<Theme> findThemeByName(String name);
}
