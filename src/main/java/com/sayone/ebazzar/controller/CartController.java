package com.sayone.ebazzar.controller;

import com.sayone.ebazzar.dto.CartDto;
import com.sayone.ebazzar.entity.CartEntity;
import com.sayone.ebazzar.entity.UserEntity;
import com.sayone.ebazzar.repository.CartRepository;
import com.sayone.ebazzar.service.CartService;
import com.sayone.ebazzar.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController

public class CartController {
    @Autowired
    UserService userService;
    @Autowired
    CartRepository cartRepository;
    @Autowired
    CartService cartService;
    @RequestMapping(path = "cart")
    @GetMapping
    public List<CartEntity> getCart(){
        return cartService.getCartListById();
    }
    @RequestMapping(path = "cart/add/{productId}")
    @PutMapping
    public void addToCart(@PathVariable(value = "productId") String productId , HttpServletRequest request){
        UserEntity userEntity =userService.findUserByEmail();
        if (cartRepository.findById(userEntity.getId())==null){
            cartRepository.save(new CartEntity(userEntity.getId()));
        }


    }



//    @PostMapping
//    public CartDto addCart(@RequestBody )
}
