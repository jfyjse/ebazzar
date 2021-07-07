package com.sayone.ebazzar.service;
import com.sayone.ebazzar.dto.ProductDto;
import com.sayone.ebazzar.dto.UserDto;
import com.sayone.ebazzar.dto.WishlistDto;
import com.sayone.ebazzar.entity.Product;
import com.sayone.ebazzar.entity.UserEntity;
import com.sayone.ebazzar.entity.WishlistEntity;
import com.sayone.ebazzar.repository.ProductRepository;
import com.sayone.ebazzar.repository.UserRepository;
import com.sayone.ebazzar.repository.WishlistRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WishlistService {

    @Autowired
    ProductRepository productRepository;
    @Autowired
    WishlistRepository wishlistRepository;
    @Autowired
    UserRepository userRepository;
//    @Autowired
    WishlistEntity wishlistEntity = new WishlistEntity();
//    @Autowired
    UserEntity userEntity = new UserEntity();

    public List<WishlistEntity> getWishlist(){

        return wishlistRepository.findAll();
    }

    public ProductDto getProductById(Long productId) {
        Optional<Product> productEntity = productRepository.findById(productId);
        if(!productEntity.isPresent()){
            throw new NullPointerException("Product not Present");
        }
        ModelMapper modelMapper = new ModelMapper();
        ProductDto productDto = modelMapper.map(productEntity.get(),ProductDto.class);
        return  productDto;
    }

    public UserDto getUserById(long userId) {
        Optional<UserEntity> userEntity = userRepository.findById(userId);
        if(!userEntity.isPresent()){
            throw new NullPointerException("no user found");
        }
        ModelMapper modelMapper = new ModelMapper();
        UserDto userDto= modelMapper.map(userEntity.get(),UserDto.class);
        return userDto;
    }

    public WishlistDto createWishlist(WishlistDto wishlistDto) {
        ModelMapper modelMapper = new ModelMapper();
       wishlistEntity = modelMapper.map(wishlistDto, WishlistEntity.class);

        ProductDto productDto = wishlistDto.getProductDto();
        Product productEntity = modelMapper.map(productDto, Product.class);
        wishlistEntity.setProduct(productEntity);

        UserDto userDto = wishlistDto.getUserDto();

        UserEntity userEntity = modelMapper.map(userDto, UserEntity.class);
        wishlistEntity.setUser(userEntity);

        WishlistEntity storedWishlistEntity = wishlistRepository.save(wishlistEntity);

        WishlistDto returnValue = modelMapper.map(storedWishlistEntity, WishlistDto.class);

        productEntity = storedWishlistEntity.getProduct();
        productDto = modelMapper.map(productEntity, ProductDto.class);
        returnValue.setProductDto(productDto);

        userEntity = storedWishlistEntity.getUser();
        userDto = modelMapper.map(userEntity, UserDto.class);
        returnValue.setUserDto(userDto);

        return returnValue;
    }
}
