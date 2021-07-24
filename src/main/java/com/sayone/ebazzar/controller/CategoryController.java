package com.sayone.ebazzar.controller;

import com.sayone.ebazzar.common.RestResources;
import com.sayone.ebazzar.dto.CategoryDto;
import com.sayone.ebazzar.entity.Category;
import com.sayone.ebazzar.repository.CategoryRepository;
import com.sayone.ebazzar.service.CategoryService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(RestResources.GET_ALL_CATEGORY)
public class CategoryController {

    @Autowired
    CategoryService service;

    @ApiImplicitParams({@ApiImplicitParam(name = "authorization", value = "${userController.authorizationHeader.description}", paramType = "header")})
    @GetMapping()
    public List<Category> get(){
        return service.getCategory();

    }


}
