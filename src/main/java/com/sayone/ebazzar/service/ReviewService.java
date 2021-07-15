package com.sayone.ebazzar.service;

import com.sayone.ebazzar.dto.ReviewDto;
import com.sayone.ebazzar.entity.ProductEntity;
import com.sayone.ebazzar.entity.ReviewEntity;
import com.sayone.ebazzar.entity.UserEntity;
import com.sayone.ebazzar.exception.CustomException;
import com.sayone.ebazzar.exception.ErrorMessages;
import com.sayone.ebazzar.model.request.ReviewRequestModel;
import com.sayone.ebazzar.repository.ProductRepository;
import com.sayone.ebazzar.repository.ReviewRepository;
import com.sayone.ebazzar.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewService {

    @Autowired
    ReviewRepository reviewRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    UserRepository userRepository;

    public ReviewEntity createReview(ReviewRequestModel reviewRequestModel) throws Exception {

        ProductEntity productEntity = getProductById(reviewRequestModel.getProductId());
        UserEntity userEntity = getUserById(reviewRequestModel.getUserId());

        ReviewEntity reviewEntity = new ReviewEntity();
        BeanUtils.copyProperties(reviewRequestModel,reviewEntity);

        reviewEntity.setProductEntity(productEntity);
        reviewEntity.setUserEntity(userEntity);

        return reviewRepository.save(reviewEntity);
    }

    public ProductEntity getProductById(Long productId) throws Exception {
        Optional<ProductEntity> productEntity= productRepository.findById(productId);
        if(!productEntity.isPresent()){
            throw new CustomException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
        }
        return productEntity.get();
    }

    public UserEntity getUserById(Long userId) throws Exception {
        Optional<UserEntity> userEntity = userRepository.findById(userId);
        if(!userEntity.isPresent()){
            throw new CustomException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
        }
        return  userEntity.get();

    }

    public ReviewEntity updateReview(Long reviewId, ReviewDto reviewDto) {

        ModelMapper modelMapper = new ModelMapper();

        Optional<ReviewEntity> reviewEntity = reviewRepository.findById(reviewId);
        if(!reviewEntity.isPresent())
            throw new CustomException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());

        reviewEntity.get().setRating(reviewDto.getRating());
        reviewEntity.get().setDescription(reviewDto.getDescription());

        return reviewRepository.save(reviewEntity.get());

    }

    public List<ReviewEntity> findRatingByProductId(Long productId) {

        Optional<ProductEntity> productEntity = productRepository.findById(productId);
        return reviewRepository.findByProductEntity(productEntity.get());

    }

    public void deleteReview(Long productId, Long userId) {

        reviewRepository.deleteReview(productId,userId);
    }
}
