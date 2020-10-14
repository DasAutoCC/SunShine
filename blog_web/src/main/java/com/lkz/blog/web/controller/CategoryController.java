package com.lkz.blog.web.controller;

import com.lkz.blog.pojo.Category;
import com.lkz.blog.web.common.RespPojo;
import com.lkz.blog.web.mapper.CategoryMapper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class CategoryController {

    @Resource
    private CategoryMapper categoryMapper;

    @RequestMapping("/category")
    public RespPojo getAllCategory(){
        List<Category> allCategory = categoryMapper.getAllCategory();
        return RespPojo.success(allCategory);
    }

}
