package com.yan.missyou.service.impl;

import com.yan.missyou.dao.ThemeDao;
import com.yan.missyou.model.Theme;
import com.yan.missyou.service.IThemeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Argus
 * @className Service
 * @description: TODO
 * @date 2020/3/16 10:29
 * @Version V1.0
 */
@Service
public class ThemeServiceImpl implements IThemeService {
    @Autowired
    private ThemeDao themeDao;

    @Override
    public List<Theme> findThemesByNames(List<String> names) {
        if (null != names && names.size() > 0) return themeDao.findByNameIn(names);
            return new ArrayList<>(0);
    }

    @Override
    public Optional<Theme> findThemeByName(String name) {
        Optional<Theme>  themeOptional = themeDao.findThemeByName(name);
        return themeOptional;
    }
}