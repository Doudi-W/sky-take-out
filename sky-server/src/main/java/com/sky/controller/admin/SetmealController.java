package com.sky.controller.admin;


import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.SetmealService;
import com.sky.vo.SetmealVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/admin/setmeal")
public class SetmealController {

    @Autowired
    private SetmealService setmealService;

    /**
     * 套餐分页查询
     */
    @GetMapping("/page")
    public Result page(SetmealPageQueryDTO setmealPageQueryDTO){
        log.info("分页查询套餐列表，参数：{}", setmealPageQueryDTO);
        PageResult pageResult = setmealService.page(setmealPageQueryDTO);
        return Result.success(pageResult);
    }


    /**
     * 新增套餐
     */
    @PostMapping
    public Result save(@RequestBody SetmealDTO setmealDTO){
        log.info("新增套餐，参数：{}", setmealDTO);
        setmealService.save(setmealDTO);
        return Result.success();
    }

    /**
     * 套餐状态修改
     */
    @PostMapping("/status/{status}")
    public Result status(@PathVariable Integer status, Long id){
        log.info("修改套餐状态，参数：{}，{}", status, id);
        setmealService.status(status, id);
        return Result.success();
    }

    /**
     * 根据id查询套餐回显
     */
    @GetMapping("/{id}")
    public Result getById(@PathVariable Long id){
        log.info("查询套餐详情，参数：{}", id);
        SetmealVO setmealVO = setmealService.getByIdWithDishes(id);
        return Result.success(setmealVO);
    }

    /**
     * 修改套餐
     */
    @PutMapping
    public Result update(@RequestBody SetmealDTO setmealDTO){
        log.info("修改套餐，参数：{}", setmealDTO);
        setmealService.update(setmealDTO);
        return Result.success();
    }

    @DeleteMapping
    public Result delete(@RequestParam List<Long> ids){
        log.info("删除套餐，参数：{}", ids);
        setmealService.delete(ids);
        return Result.success();
    }
}
