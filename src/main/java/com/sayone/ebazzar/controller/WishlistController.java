package com.sayone.ebazzar.controller;

import com.sayone.ebazzar.dto.ProductDto;
import com.sayone.ebazzar.dto.UserDto;
import com.sayone.ebazzar.dto.WishlistDto;
import com.sayone.ebazzar.entity.WishlistEntity;
import com.sayone.ebazzar.model.request.WishlistRequestModel;
import com.sayone.ebazzar.service.WishlistService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/wishlist")
public class WishlistController {

    @Autowired
    WishlistService wishlistService;

//    @Autowired
    WishlistRequestModel wishlistRequestModel = new WishlistRequestModel();


    @GetMapping
    public List<WishlistEntity> get(){
        return wishlistService.getWishlist();
    }

    @PostMapping
    public WishlistDto createWishlist(@RequestBody WishlistRequestModel wishlistRequestModel){


        ProductDto productDto = wishlistService.getProductById(wishlistRequestModel.getProductId());
        UserDto userDto = wishlistService.getUserById(wishlistRequestModel.getUserId());
        ModelMapper modelMapper= new ModelMapper();
        WishlistDto wishlistDto = modelMapper.map(wishlistRequestModel,WishlistDto.class);
        wishlistDto.setProductDto(productDto);
        wishlistDto.setUserDto(userDto);
        WishlistDto returnValue = wishlistService.createWishlist(wishlistDto);

        return returnValue;

    }


}
