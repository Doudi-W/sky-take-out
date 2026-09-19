package com.sky.controller.admin;

import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/category")
@Slf4j
public class CategoryController {

    @Autowired
    private CategoryService categoryService;
    /**
     * 分页查询
     *
     */
    @GetMapping("/page")
    public Result page(CategoryPageQueryDTO categoryPageQueryDTO) {
        log.info("分页查询分类，参数：{}", categoryPageQueryDTO);
        PageResult pageResult = categoryService.page(categoryPageQueryDTO);
        return Result.success(pageResult);
    }

    /**
     * 列表查询
     *
     */
    @GetMapping("/list")
    public Result<List<Category>> list(Integer type) {

        List<Category> categoryList = categoryService.list(type);

        return Result.success(categoryList);
    }

    /**
     * 新增分类
     *
     */
    @PostMapping
    public Result save(@RequestBody CategoryDTO categoryDTO) {
        log.info("新增分类，参数：{}", categoryDTO);
        categoryService.save(categoryDTO);
        return Result.success();
    }

    /**
     * 修改分类状态
     *
     */
    @PostMapping("/status/{status}")
    public Result Status(@PathVariable Integer status,Long id) {
        log.info("修改分类状态，参数：{}", status, id);
        categoryService.Status(status, id);
        return Result.success();
    }
}
