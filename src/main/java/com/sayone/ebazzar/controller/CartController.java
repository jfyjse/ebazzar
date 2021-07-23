package com.sayone.ebazzar.controller;
import com.sayone.ebazzar.dto.UserDto;
import com.sayone.ebazzar.entity.CartEntity;
import com.sayone.ebazzar.entity.CartItemEntity;
import com.sayone.ebazzar.exception.CustomException;
import com.sayone.ebazzar.exception.ErrorMessages;
import com.sayone.ebazzar.service.CartService;
import com.sayone.ebazzar.service.UserService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {
    @Autowired
    CartService cartService;
    @Autowired
    UserService userService;
    @ApiImplicitParams({@ApiImplicitParam(name = "authorization", value = "${userController.authorizationHeader.description}", paramType = "header")})
    @PutMapping(value = "/add/{productId}")
    public ResponseEntity<CartEntity> addCartItem(@PathVariable (value = "productId") Long productId,
                                                  @RequestParam (value = "quantity") Integer quantity) throws Exception {
        if (productId == null || !(quantity>0)){
            throw new CustomException(ErrorMessages.CART_QUANTITY_PID_ERROR.getErrorMessage());}
        else {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            UserDto user = userService.getUser(auth.getName());
            CartEntity cartEntity = cartService.addCartItem(user.getUserId(), productId, quantity);
            return new ResponseEntity<>(cartEntity, HttpStatus.CREATED);
        }


    }
    @ApiImplicitParams({@ApiImplicitParam(name = "authorization", value = "${userController.authorizationHeader.description}", paramType = "header")})
    @GetMapping(value = "/get/{uid}")
    public List<CartItemEntity> getCartItems(@PathVariable (value = "uid") Long userId){
        List<CartItemEntity> cartItemEntityList = cartService.getCartItems(userId);

        return cartItemEntityList;
    }
    @ApiImplicitParams({@ApiImplicitParam(name = "authorization", value = "${userController.authorizationHeader.description}", paramType = "header")})
    @PutMapping(value = "/remove/{pid}")
    public void removeProductFromCart(@PathVariable(value = "pid") Long productId){
        if (productId == null ){
            throw new CustomException(ErrorMessages.CART_PRODUCTID_NOTFOUND.getErrorMessage());}
        else {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            UserDto user = userService.getUser(auth.getName());
            cartService.removeProductFromCart(user.getUserId(), productId);

        }

    }
    @ApiImplicitParams({@ApiImplicitParam(name = "authorization", value = "${userController.authorizationHeader.description}", paramType = "header")})
    @DeleteMapping(value = "/delete/all/{cid}")
    public void deleteAllProductFromCart(@PathVariable(value = "cid") Long cartId){
        cartService.deleteAllProductsFromCart(cartId);

    }


}