package com.sayone.ebazzar.controller;
import com.sayone.ebazzar.common.RestResources;
import com.sayone.ebazzar.dto.UserDto;
import com.sayone.ebazzar.entity.CartEntity;
import com.sayone.ebazzar.entity.CartItemEntity;
import com.sayone.ebazzar.exception.ErrorMessages;
import com.sayone.ebazzar.exception.RequestException;
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
@RequestMapping(RestResources.CART_ROOT)
public class CartController {
    @Autowired
    CartService cartService;
    @Autowired
    UserService userService;
    @ApiImplicitParams({@ApiImplicitParam(name = "authorization", value = "${userController.authorizationHeader.description}", paramType = "header")})
    //http://localhost:8080/cart/add/1?quantity=3
    @PutMapping(path = RestResources.ADD_TO_CART)
    public ResponseEntity<CartEntity> addCartItem(@PathVariable (value = "productId") Long productId,
                                                  @RequestParam (value = "quantity") Integer quantity) throws Exception {
        if (productId == null || !(quantity>0)){
            throw new RequestException(ErrorMessages.CART_QUANTITY_PID_ERROR.getErrorMessages());}
        else {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            UserDto user = userService.getUser(auth.getName());
            CartEntity cartEntity = cartService.addCartItem(user.getUserId(), productId, quantity);
            return new ResponseEntity<>(cartEntity, HttpStatus.CREATED);
        }


    }
    @ApiImplicitParams({@ApiImplicitParam(name = "authorization", value = "${userController.authorizationHeader.description}", paramType = "header")})
    //http://localhost:8080/cart/get/1?
    @GetMapping( path = RestResources.GET_ALL_CART_ITEMS)
    public List<CartItemEntity> getCartItems(@PathVariable (value = "uid") Long userId){
        List<CartItemEntity> cartItemEntityList = cartService.getCartItems(userId);

        return cartItemEntityList;
    }
    @ApiImplicitParams({@ApiImplicitParam(name = "authorization", value = "${userController.authorizationHeader.description}", paramType = "header")})
    //http://localhost:8080/cart/remove/1
    @PutMapping(path = RestResources.REMOVE_PRODUCT_FROM_CART)
    public void removeProductFromCart(@PathVariable(value = "pid") Long productId){
        if (productId == null ){
            throw new RequestException(ErrorMessages.CART_PRODUCTID_NOTFOUND.getErrorMessages());}
        else {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            UserDto user = userService.getUser(auth.getName());
            cartService.removeProductFromCart(user.getUserId(), productId);

        }

    }

}