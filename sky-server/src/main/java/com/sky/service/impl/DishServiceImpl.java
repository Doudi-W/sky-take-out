package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.entity.SetmealDish;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.DishFlavorsMapper;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealDishMapper;
import com.sky.result.PageResult;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DishServiceImpl implements DishService {

    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private DishFlavorsMapper dishFlavorsMapper;
    @Autowired
    private SetmealDishMapper setmealDishMapper;
    /**
     * 分页查询
     */
    @Override
    public PageResult page(DishPageQueryDTO dishPageQueryDTO) {

        PageHelper.startPage(dishPageQueryDTO.getPage(), dishPageQueryDTO.getPageSize());

        List<DishVO> records = dishMapper.page(dishPageQueryDTO);

        Page<DishVO> p = (Page<DishVO>) records;

        return new PageResult(p.getTotal(), p.getResult());
    }

    /**
     * 修改菜品状态
     *
     * @param status
     * @param id
     */
    @Override
    public void Status(Integer status, Long id) {
        Dish dish = Dish.builder()
                .id(id)
                .status(status)
                .build();

        dishMapper.update(dish);
    }

    /**
     * 新增菜品
     *
     * @param dishDTO
     */
    @Override
    public void saveWithFlavors(DishDTO dishDTO) {
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO, dish);
        dishMapper.insert(dish);

        Long dishId = dish.getId();

        List<DishFlavor> flavors = dishDTO.getFlavors();
        if(dishDTO.getFlavors() != null && dishDTO.getFlavors().size() > 0){
            flavors.forEach(flavor -> {
                flavor.setDishId(dishId);
            });
            // 批量插入菜品口味
            dishFlavorsMapper.insertBatch(flavors);
        }
    }

    /**
     * 批量删除菜品
     *
     * @param ids
     */
    @Override
    public void deleteForBatch(List<Long> ids) {
        //判断状态是否为在售，如果为1则不能删除
        for (Long id : ids) {
            Dish dish = dishMapper.getById(id);
            if (dish.getStatus() == StatusConstant.ENABLE) {
                throw new DeletionNotAllowedException(MessageConstant.DISH_ON_SALE);
            }
        }
        //判断套餐是否关联了该菜品，如果关联了则不能删除
        List<Long> setmealIds = setmealDishMapper.getSetmealIdByDishIds(ids);
        if(setmealIds != null && setmealIds.size() > 0) {
            throw new DeletionNotAllowedException(MessageConstant.DISH_BE_RELATED_BY_SETMEAL);
        }
        //删除菜品

        dishMapper.deleteBatchById(ids);
        //删除菜品口味
        dishFlavorsMapper.deleteByDishIds(ids);

    }

    /**
     * 根据id查询菜品详情，包含口味
     *
     * @param id
     * @return
     */
    @Override
    public DishVO getByIdWithFlavors(Long id) {
        //查询菜品基本信息
        Dish dish = dishMapper.getById(id);

        //口味信息查询
        List<DishFlavor> flavors = dishFlavorsMapper.getByDishId(id);
        //封装成VO对象返回
        DishVO dishVO = new DishVO();
        BeanUtils.copyProperties(dish, dishVO);
        dishVO.setFlavors(flavors);
        return dishVO;
    }

    /**
     * 修改菜品
     *
     * @param dishDTO
     */
    @Transactional
    @Override
    public void updateWithFlavors(DishDTO dishDTO) {
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO, dish);
        //更新菜品基本信息
        dishMapper.update(dish);
        //更新菜品口味信息：先删除所有菜品口味信息，再插入新的菜品口味信息
        List<DishFlavor> flavors = dishDTO.getFlavors();
        if(flavors != null){
            dishFlavorsMapper.deleteByDishId(dishDTO.getId());
            if(flavors.size() > 0){
                flavors.forEach(flavor -> {
                    flavor.setDishId(dishDTO.getId());
                });
                dishFlavorsMapper.insertBatch(flavors);
            }
        }
    }
}
