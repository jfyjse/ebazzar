package com.sayone.ebazzar.service;

import com.sayone.ebazzar.dto.ReviewDto;
import com.sayone.ebazzar.entity.ProductEntity;
import com.sayone.ebazzar.entity.ReviewEntity;
import com.sayone.ebazzar.entity.UserEntity;
import com.sayone.ebazzar.exception.CustomException;
import com.sayone.ebazzar.exception.ErrorMessages;
import com.sayone.ebazzar.model.request.ReviewRequestModel;
import com.sayone.ebazzar.model.response.ReviewResponseModel;
import com.sayone.ebazzar.repository.ProductRepository;
import com.sayone.ebazzar.repository.ReviewRepository;
import com.sayone.ebazzar.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    public ReviewResponseModel createReview(ReviewRequestModel reviewRequestModel,Long userId) throws Exception {

        ProductEntity productEntity = getProductById(reviewRequestModel.getProductId());
        UserEntity userEntity = getUserById(userId);

        ReviewEntity reviewEntity1 = reviewRepository.findByProductAndUser(productEntity.getProductId(),userEntity.getUserId());
        if(reviewEntity1 != null)
            throw new CustomException(ErrorMessages.REVIEW_ALREADY_GIVEN.getErrorMessages());

        ReviewEntity reviewEntity = new ReviewEntity();
        BeanUtils.copyProperties(reviewRequestModel,reviewEntity);

        reviewEntity.setProductEntity(productEntity);
        reviewEntity.setUserEntity(userEntity);

        ReviewEntity storedReview = reviewRepository.save(reviewEntity);
        ReviewResponseModel reviewResponseModel = new ReviewResponseModel();
        BeanUtils.copyProperties(storedReview,reviewResponseModel);
        reviewResponseModel.setProductName(storedReview.getProductEntity().getProductName());
        reviewResponseModel.setProductDescription(storedReview.getProductEntity().getDescription());


        return reviewResponseModel;
    }

    public ProductEntity getProductById(Long productId) throws Exception {
        Optional<ProductEntity> productEntity= productRepository.findById(productId);
        if(!productEntity.isPresent()){
            throw new CustomException(ErrorMessages.NO_RECORD_FOUND.getErrorMessages());
        }
        return productEntity.get();
    }

    public UserEntity getUserById(Long userId) throws Exception {
        Optional<UserEntity> userEntity = userRepository.findById(userId);
        if(!userEntity.isPresent()){
            throw new CustomException(ErrorMessages.NO_RECORD_FOUND.getErrorMessages());
        }
        return  userEntity.get();

    }

    public ReviewResponseModel updateReview(Long reviewId, ReviewDto reviewDto) {

        ModelMapper modelMapper = new ModelMapper();

        Optional<ReviewEntity> reviewEntity = reviewRepository.findById(reviewId);
        if(!reviewEntity.isPresent())
            throw new CustomException(ErrorMessages.NO_RECORD_FOUND.getErrorMessages());

        reviewEntity.get().setRating(reviewDto.getRating());
        reviewEntity.get().setDescription(reviewDto.getDescription());

        ReviewEntity updatedReview =  reviewRepository.save(reviewEntity.get());
        ReviewResponseModel reviewResponseModel = new ReviewResponseModel();
        BeanUtils.copyProperties(updatedReview,reviewResponseModel);
        reviewResponseModel.setProductName(updatedReview.getProductEntity().getProductName());
        reviewResponseModel.setProductDescription(updatedReview.getProductEntity().getDescription());


        return reviewResponseModel;
    }

    public ReviewResponseModel findReviewForProduct(Long productId,Long userId) {

        ReviewEntity reviewEntity= reviewRepository.findByProductAndUser(productId,userId);
        if(reviewEntity == null)
            throw new CustomException(ErrorMessages.NO_REVIEW_FOUND.getErrorMessages());

        ReviewResponseModel reviewResponseModel = new ReviewResponseModel();
        BeanUtils.copyProperties(reviewEntity,reviewResponseModel);
        reviewResponseModel.setProductName(reviewEntity.getProductEntity().getProductName());
        reviewResponseModel.setProductDescription(reviewEntity.getProductEntity().getDescription());

        return reviewResponseModel;
    }

    public void deleteReview(Long productId, Long userId) {

        Integer status = reviewRepository.deleteReview(productId,userId);
        if(status == 0)
            throw new CustomException(ErrorMessages.NO_REVIEW_FOUND.getErrorMessages());
    }

    public List<ReviewResponseModel> findReviewsByUser(long userId) {
        List<ReviewEntity> reviewEntityList = reviewRepository.findByUserId(userId);
        if(reviewEntityList.isEmpty())
            throw new CustomException(ErrorMessages.NO_REVIEW_GIVEN.getErrorMessages());

        List<ReviewResponseModel> reviewResponseModels = new ArrayList<ReviewResponseModel>();
        for(ReviewEntity reviewEntity:reviewEntityList)
        {
            ReviewResponseModel reviewResponseModel = new ReviewResponseModel();
            BeanUtils.copyProperties(reviewEntity,reviewResponseModel);
            reviewResponseModel.setProductName(reviewEntity.getProductEntity().getProductName());
            reviewResponseModel.setProductDescription(reviewEntity.getProductEntity().getDescription());
            reviewResponseModels.add(reviewResponseModel);
        }
        return reviewResponseModels;
    }
}
