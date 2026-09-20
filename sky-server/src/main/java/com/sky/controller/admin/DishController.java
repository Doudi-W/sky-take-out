package com.sky.controller.admin;


import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 菜品管理
 */
@RestController
@RequestMapping("/admin/dish")
@Slf4j
public class DishController {

    @Autowired
    private DishService dishService;
    /**
     * 分页查询
     *
     */
    @GetMapping("/page")
    public Result page(DishPageQueryDTO dishPageQueryDTO) {
        log.info("分页查询菜品，参数：{}", dishPageQueryDTO);
        PageResult pageResult = dishService.page(dishPageQueryDTO);
        return Result.success(pageResult);
    }

    /**
     * 修改菜品状态
     *
     */
    @PostMapping("/status/{status}")
    public Result Status(@PathVariable Integer status, Long id) {
        log.info("修改菜品状态，状态：{}，id：{}", status, id);
        dishService.Status(status, id);
        return Result.success();
    }

    /*@PutMapping
    public Result update(@RequestBody DishDTO dishDTO) {
        log.info("修改菜品，参数：{}", dishDTO);
        dishService.update(dishDTO);
        return Result.success();
    }*/
}
