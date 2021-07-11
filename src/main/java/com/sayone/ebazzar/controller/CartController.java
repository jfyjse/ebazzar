package com.sayone.ebazzar.controller;

import com.sayone.ebazzar.dto.CartDto;
import com.sayone.ebazzar.entity.CartEntity;
import com.sayone.ebazzar.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "cart")
public class CartController {
    @Autowired
    CartService cartService;

    @GetMapping
    public List<CartEntity> getCart(){
        return cartService.getCartListById();
    }

//    @PostMapping
//    public CartDto addCart(@RequestBody )
}
