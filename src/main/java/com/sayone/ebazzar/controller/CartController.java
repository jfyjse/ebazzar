package com.sayone.ebazzar.controller;

import com.sayone.ebazzar.dto.CartDto;
import com.sayone.ebazzar.entity.CartEntity;
import com.sayone.ebazzar.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(name = "cart")
public class CartController {
    @Autowired
    CartService cartService;
    @PutMapping(value = "/add/{productId}")
    public ResponseEntity<CartEntity> addCartItem(@PathVariable (value = "productId") Long productId,
                                                  @RequestParam (value = "quantity") Integer quantity) throws Exception {
        Long userId=2L;
        System.out.println("1");
        CartEntity cartEntity = cartService.addCartItem(userId,productId,quantity);
        System.out.println("2");
        return new ResponseEntity<>(cartEntity, HttpStatus.CREATED);

    }

    }

