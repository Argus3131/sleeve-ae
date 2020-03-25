package com.yan.missyou.service;

import com.yan.missyou.model.Spu;
import org.springframework.data.domain.Page;

import java.awt.print.Pageable;

/**
 * @author Argus
 * @className ISpuService
 * @description: TODO
 * @date 2020/3/16 16:17
 * @Version V1.0
 */
public interface ISpuService {

    Spu findSpuDetail(Long id);

    Page<Spu> findSpuLatestPaging(Integer page,Integer size);

    Page<Spu> findSpuByCategoryId(Long id,Boolean isRoot,Integer page,Integer size);
}
