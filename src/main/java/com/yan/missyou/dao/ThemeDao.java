package com.yan.missyou.dao;

import com.yan.missyou.model.Spu;
import com.yan.missyou.model.Theme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

/**
 * @author Argus
 * @className ThemeDao
 * @description: TODO
 * @date 2020/3/16 10:30
 * @Version V1.0
 */
@Repository
public interface ThemeDao extends JpaRepository<Theme, Long> {

    List<Theme> findByNameIn(List<String> names);

    //JPQL
    @Query("select t from Theme t where t.name in (:names)")
    List<Theme> findByNames(@Param("names") List<String> names);

    Optional<Theme> findThemeByName(String name);
}
