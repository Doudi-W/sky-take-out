package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.StatusConstant;
import com.sky.context.BaseContext;
import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.mapper.CategoryMapper;
import com.sky.result.PageResult;
import com.sky.service.CategoryService;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    /**
     * 分页查询分类
     * @param categoryPageQueryDTO
     * @return
     */
    @Override
    public PageResult page(CategoryPageQueryDTO categoryPageQueryDTO) {
        PageHelper.startPage(categoryPageQueryDTO.getPage(), categoryPageQueryDTO.getPageSize());

        List<Category> records = categoryMapper.page(categoryPageQueryDTO);

        Page<Category> p = (Page<Category>) records;

        return new PageResult(p.getTotal(), p.getResult());
    }

    @Override
    public List<Category> list(Integer type) {
        return categoryMapper.list(type);
    }

    /**
     * 新增分类
     * @param categoryDTO
     */
    @Override
    public void save(CategoryDTO categoryDTO) {
        Category category = new Category();
        BeanUtils.copyProperties(categoryDTO, category);
        // 设置默认状态为禁用
        category.setStatus(StatusConstant.DISABLE);

        // 设置创建时间和更新时间
        category.setCreateTime(LocalDateTime.now());
        category.setUpdateTime(LocalDateTime.now());
        // 设置创建人和更新人
        category.setCreateUser(BaseContext.getCurrentId());
        category.setUpdateUser(BaseContext.getCurrentId());
        categoryMapper.insert(category);
    }

    /**
     * 修改分类状态
     * @param status
     * @param id
     */
    @Override
    public void Status(Integer status, Long id) {
        Category category = Category.builder()
                .status(status)
                .id(id)
                .build();
        categoryMapper.update(category);
    }
}
