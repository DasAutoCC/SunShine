package com.lkz.blog.web.mapper;

import com.lkz.blog.pojo.Category;

import java.util.List;

public interface CategoryMapper {

    int insertCategory(Category record);

    Category getSpecifiedCategory(int categoryId);

    List<Category> getAllCategory();

}
