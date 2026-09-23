package com.sky.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SetmealDishMapper {

    /**
     * 根据菜品ids查询套餐idss
     * @param
     */
    List<Long> getSetmealIdByDishIds(List<Long> dishIds);
}
