package com.sayone.ebazzar.service;

import com.sayone.ebazzar.dto.ProductDto;
import com.sayone.ebazzar.dto.ReviewDto;
import com.sayone.ebazzar.dto.UserDto;
import com.sayone.ebazzar.entity.ProductEntity;
import com.sayone.ebazzar.entity.ReviewEntity;
import com.sayone.ebazzar.entity.UserEntity;
import com.sayone.ebazzar.repository.ProductRepository;
import com.sayone.ebazzar.repository.ReviewRepository;
import com.sayone.ebazzar.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ReviewService {

    @Autowired
    ReviewRepository reviewRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    UserRepository userRepository;

    public ReviewDto createReview(ReviewDto reviewDto) {

        ModelMapper modelMapper = new ModelMapper();
        ReviewEntity reviewEntity = modelMapper.map(reviewDto, ReviewEntity.class);

        ProductDto productDto = reviewDto.getProductDto();
        ProductEntity productEntity = modelMapper.map(productDto, ProductEntity.class);
        reviewEntity.setProductEntity(productEntity);

        UserDto userDto = reviewDto.getUserDto();
        UserEntity userEntity = modelMapper.map(userDto, UserEntity.class);
        reviewEntity.setUserEntity(userEntity);

        ReviewEntity storedReviewEntity = reviewRepository.save(reviewEntity);

        ReviewDto returnValue = modelMapper.map(storedReviewEntity, ReviewDto.class);

        productEntity = storedReviewEntity.getProductEntity();
        productDto = modelMapper.map(productEntity, ProductDto.class);
        returnValue.setProductDto(productDto);

        userEntity = storedReviewEntity.getUserEntity();
        userDto = modelMapper.map(userEntity, UserDto.class);
        returnValue.setUserDto(userDto);

        return returnValue;
    }

    public ProductDto getProductById(Long productId) {
        Optional<ProductEntity> productEntity1 = productRepository.findById(productId);
        if(!productEntity1.isPresent()){
            throw new NullPointerException("Product not Present");
        }
        ModelMapper modelMapper = new ModelMapper();
        ProductDto productDto = modelMapper.map(productEntity1.get(),ProductDto.class);
        return  productDto;
    }

    public UserDto getUserById(Long userId) {
        Optional<UserEntity> userEntity1 = userRepository.findById(userId);
        if(!userEntity1.isPresent()){
            throw new NullPointerException("User not Present");
        }
        ModelMapper modelMapper = new ModelMapper();
        UserDto userDto = modelMapper.map(userEntity1.get(),UserDto.class);
        return  userDto;

    }

    public ReviewDto updateReview(Long reviewId, ReviewDto reviewDto) {

        ModelMapper modelMapper = new ModelMapper();

        Optional<ReviewEntity> reviewEntity = reviewRepository.findById(reviewId);
        if(!reviewEntity.isPresent())
            throw new NullPointerException("Invalid Review Id");
        ReviewEntity reviewEntity1 = new ReviewEntity();
        BeanUtils.copyProperties(reviewEntity.get(),reviewEntity1);

        reviewEntity1.setRating(reviewDto.getRating());
        reviewEntity1.setDescription(reviewDto.getDescription());

        ReviewEntity storedEntity = reviewRepository.save(reviewEntity1);
        ReviewDto returnValue = modelMapper.map(storedEntity,ReviewDto.class);

        returnValue.setProductDto(modelMapper.map(storedEntity.getProductEntity(),ProductDto.class));
        returnValue.setUserDto(modelMapper.map(storedEntity.getUserEntity(),UserDto.class));
        return returnValue;

    }
}
