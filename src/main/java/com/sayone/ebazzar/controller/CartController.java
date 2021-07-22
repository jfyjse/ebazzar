package com.sayone.ebazzar.controller;

import com.sayone.ebazzar.entity.CartEntity;
import com.sayone.ebazzar.entity.CartItemEntity;
import com.sayone.ebazzar.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {
    @Autowired
    CartService cartService;


    @PutMapping(value = "/add/{productId}")
    public ResponseEntity<CartEntity> addCartItem(@PathVariable (value = "productId") Long productId,
                                                  @RequestParam (value = "quantity") Integer quantity) throws Exception {
        Long userId=1L;
        System.out.println("1");
        CartEntity cartEntity = cartService.addCartItem(userId,productId,quantity);
        System.out.println("2");
        return new ResponseEntity<>(cartEntity, HttpStatus.CREATED);

    }

    @GetMapping(value = "/get/{uid}")
    public List<CartItemEntity> getCartItems(@PathVariable (value = "uid") Long userId){
        List<CartItemEntity> cartItemEntityList = cartService.getCartItems(userId);

        return cartItemEntityList;
    }

    @DeleteMapping(value = "/delete/{pid}")
    public void deleteProductFromCart(@PathVariable(value = "pid") Long productId){
        cartService.deleteProductFromCart(productId);
    }

    @DeleteMapping(value = "/delete/all/{cid}")
    public void deleteAllProductFromCart(@PathVariable(value = "cid") Long cartId){
        cartService.deleteAllProductsFromCart(cartId);
    }


}