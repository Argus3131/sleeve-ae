package com.yan.missyou.controller;

import com.yan.missyou.bo.PageCounter;
import com.yan.missyou.exception.http.NotFoundException;
import com.yan.missyou.model.Spu;
import com.yan.missyou.service.ISpuService;
import com.yan.missyou.vo.PagingDozer;
import com.yan.missyou.vo.SpuSimplifyVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

/**
 * @author Argus
 * @className SpuController
 * @description: TODO
 * @date 2020/3/16 16:15
 * @Version V1.0
 */
@Validated
@RestController
@RequestMapping("/v1/spu")
public class SpuController {
    @Autowired
    private ISpuService iSpuService;

    @GetMapping("/id/{id}/detail")
    @ResponseBody
    public Spu getSpuDetail(@PathVariable @Positive Long id) {
        Spu spu = iSpuService.findSpuDetail(id);
        if (null == spu) throw new NotFoundException(30002);
        return spu;
    }


    @GetMapping("/latest")
    @ResponseBody
    public PagingDozer<Spu,SpuSimplifyVO> getLatestSpuList(@RequestParam(value = "start", required = false, defaultValue = "0") Integer start,
                                      @RequestParam(value = "count", required = false, defaultValue = "10") Integer count) {
        PageCounter pageCounter = PageCounter.initPageCounter(start, count);
        Page<Spu> spuPage = iSpuService.findSpuLatestPaging(pageCounter.getPage(),pageCounter.getCount());
        PagingDozer<Spu,SpuSimplifyVO> pagingDozer = new PagingDozer<>(spuPage,SpuSimplifyVO.class);
        return pagingDozer;
    }


    /**
     * 按分类获取spu
     * @param id
     * @param isRoot
     * @param start
     * @param count
     * @return
     */
    @GetMapping("/by/category/{id}")
    @ResponseBody
    public PagingDozer<Spu,SpuSimplifyVO> getSpuDetail(@PathVariable(name = "id") @Positive(message = "{id.positive}") Long id,
                            @RequestParam(name = "is_root") @NotNull Boolean isRoot,
                            @RequestParam(name = "start",defaultValue = "0") Integer start,
                            @RequestParam(name = "count",defaultValue = "10") Integer count) {
        System.out.println(id);
        PageCounter pageCounter = PageCounter.initPageCounter(start, count);
        Page<Spu> spuByCategoryId = iSpuService.findSpuByCategoryId(id, isRoot, pageCounter.getPage(), pageCounter.getCount());
        PagingDozer<Spu,SpuSimplifyVO> pagingDozer = new PagingDozer<>(spuByCategoryId,SpuSimplifyVO.class);
        return pagingDozer;
    }

}