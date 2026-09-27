package com.sky.service;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.vo.DishVO;

import java.util.List;

public interface DishService {
    /**
     * 分页查询菜品
     *
     * @param dishPageQueryDTO
     * @return
     */
    PageResult page(DishPageQueryDTO dishPageQueryDTO);

    /**
     * 修改菜品状态
     *
     * @param status
     * @param id
     */
    void Status(Integer status, Long id);

    /**
     * 新增菜品
     *
     * @param dishDTO
     */
    void saveWithFlavors(DishDTO dishDTO);

    /**
     * 批量删除菜品
     *
     * @param ids
     */
    void deleteForBatch(List<Long> ids);

    /**
     * 根据id查询菜品详情，包含口味
     *
     * @param id
     * @return
     */
    DishVO getByIdWithFlavors(Long id);

    /**
     * 修改菜品
     *
     * @param dishDTO
     */
    void updateWithFlavors(DishDTO dishDTO);

    /**
     * 根据分类id查询菜品列表
     *
     * @param categoryId
     * @return
     */
    List<DishVO> list(Long categoryId);
}
