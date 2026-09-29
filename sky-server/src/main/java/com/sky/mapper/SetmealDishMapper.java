package com.sky.mapper;

import com.sky.entity.SetmealDish;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SetmealDishMapper {

    /**
     * 根据菜品ids查询套餐idss
     * @param
     */
    List<Long> getSetmealIdByDishIds(List<Long> dishIds);

    /**
     * 批量插入
     * @param setmealDishes
     */
    void insertBatch(List<SetmealDish> setmealDishes);

    /**
     * 根据套餐id查询菜品
      * @param setmealId
     * @return
     */
    @Select("select * from setmeal_dish where setmeal_id = #{setmealId}")
    List<SetmealDish> getDishesBySetmealId(Long setmealId);

    /**
     * 根据套餐id删除套餐菜品
     * @param setmealId
     */
    void deleteBySetmealId(Long setmealId);
}
