package com.sayone.ebazzar.service;

import com.sayone.ebazzar.entity.*;
import com.sayone.ebazzar.exception.ErrorMessages;
import com.sayone.ebazzar.exception.RequestException;
import com.sayone.ebazzar.model.request.ReviewRequestModel;
import com.sayone.ebazzar.model.response.ReviewResponseModel;
import com.sayone.ebazzar.repository.*;
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

    @Autowired
    CartRepository cartRepository;

    @Autowired
    OrderRepository orderRepository;

    public ReviewResponseModel createReview(ReviewRequestModel reviewRequestModel, Long userId) {

        if (reviewRequestModel.getProductId() == null)
            throw new RequestException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessages());

        ProductEntity productEntity = getProductById(reviewRequestModel.getProductId());

        List<CartEntity> cartEntityList = cartRepository.findByUserIdAndStatus(userId, "closed");
        if (cartEntityList.isEmpty())
            throw new RequestException(ErrorMessages.NO_ORDER_FOUND.getErrorMessages());

        boolean productPurchased = false;
        for (int i = 0; i < cartEntityList.size(); i++) {
            for (int j = 0; j < cartEntityList.get(i).getCartItemEntityList().size(); j++) {
                if (cartEntityList.get(i).getCartItemEntityList().get(j).getProductEntity().getProductId() == reviewRequestModel.getProductId()) {
                    productPurchased = true;
                    break;
                }
            }
            if (productPurchased)
                break;

        }
        if (!productPurchased)
            throw new RequestException(ErrorMessages.PRODUCT_NOT_PURCHASED.getErrorMessages());

        boolean orderDelivered = false;
        for (CartEntity cartEntity : cartEntityList) {
            OrderEntity orderEntity = orderRepository.findBycartIdandStatus(cartEntity.getCartId(), "delivered");
            if (orderEntity != null) {
                CartEntity cartEntity1 = cartRepository.getById(orderEntity.getCartEntity().getCartId());
                for (CartItemEntity cartItemEntity : cartEntity1.getCartItemEntityList()) {
                    if (cartItemEntity.getProductEntity().getProductId() == reviewRequestModel.getProductId()) {
                        orderDelivered = true;
                        break;
                    }
                }

            }
            if (orderDelivered)
                break;
        }
        if (!orderDelivered)
            throw new RequestException(ErrorMessages.ORDER_NOT_DELIVERED.getErrorMessages());

        UserEntity userEntity = getUserById(userId);

        Optional<ReviewEntity> reviewEntity1 = reviewRepository.findByProductAndUser(productEntity.getProductId(), userEntity.getUserId());
        if (reviewEntity1.isPresent())
            throw new RequestException(ErrorMessages.REVIEW_ALREADY_GIVEN.getErrorMessages());

        if (reviewRequestModel.getRating() > 5)
            throw new RequestException(ErrorMessages.INVALID_RATING.getErrorMessages());

        ReviewEntity reviewEntity = new ReviewEntity();
        BeanUtils.copyProperties(reviewRequestModel, reviewEntity);

        reviewEntity.setProductEntity(productEntity);
        reviewEntity.setUserEntity(userEntity);

        ReviewEntity storedReview = reviewRepository.save(reviewEntity);
        ReviewResponseModel reviewResponseModel = new ReviewResponseModel();
        BeanUtils.copyProperties(storedReview, reviewResponseModel);
        reviewResponseModel.setProductName(storedReview.getProductEntity().getProductName());
        reviewResponseModel.setProductDescription(storedReview.getProductEntity().getDescription());

        return reviewResponseModel;
    }

    public ProductEntity getProductById(Long productId) {
        Optional<ProductEntity> productEntity = productRepository.findById(productId);
        if (productEntity.isEmpty()) {
            throw new RequestException(ErrorMessages.NO_PRODUCT_FOUND.getErrorMessages());
        }
        return productEntity.get();
    }

    public UserEntity getUserById(Long userId) {
        Optional<UserEntity> userEntity = userRepository.findById(userId);
        if (userEntity.isEmpty()) {
            throw new RequestException(ErrorMessages.NO_USER_FOUND.getErrorMessages());
        }
        return userEntity.get();

    }

    public ReviewResponseModel updateReview(ReviewRequestModel reviewRequestModel, Long userId) {

        getProductById(reviewRequestModel.getProductId());

        Optional<ReviewEntity> reviewEntity = reviewRepository.findByProductAndUser(reviewRequestModel.getProductId(), userId);
        if (reviewEntity.isEmpty())
            throw new RequestException(ErrorMessages.NO_REVIEW_ID_FOUND.getErrorMessages());

        if (reviewRequestModel.getRating() > 5)
            throw new RequestException(ErrorMessages.INVALID_RATING.getErrorMessages());

        reviewEntity.get().setRating(reviewRequestModel.getRating());
        reviewEntity.get().setDescription(reviewRequestModel.getDescription());

        ReviewEntity updatedReview = reviewRepository.save(reviewEntity.get());
        ReviewResponseModel reviewResponseModel = new ReviewResponseModel();
        BeanUtils.copyProperties(updatedReview, reviewResponseModel);
        reviewResponseModel.setProductName(updatedReview.getProductEntity().getProductName());
        reviewResponseModel.setProductDescription(updatedReview.getProductEntity().getDescription());

        return reviewResponseModel;
    }

    public ReviewResponseModel findReviewForProduct(Long productId, Long userId) {

        getProductById(productId);

        Optional<ReviewEntity> reviewEntity = reviewRepository.findByProductAndUser(productId, userId);
        if (reviewEntity.isEmpty())
            throw new RequestException(ErrorMessages.NO_REVIEW_FOUND.getErrorMessages());

        ReviewResponseModel reviewResponseModel = new ReviewResponseModel();
        BeanUtils.copyProperties(reviewEntity.get(), reviewResponseModel);
        reviewResponseModel.setProductName(reviewEntity.get().getProductEntity().getProductName());
        reviewResponseModel.setProductDescription(reviewEntity.get().getProductEntity().getDescription());

        return reviewResponseModel;
    }

    public void deleteReview(Long productId, Long userId) {

        getProductById(productId);

        Integer status = reviewRepository.deleteReview(productId, userId);
        if (status == 0)
            throw new RequestException(ErrorMessages.NO_REVIEW_FOUND.getErrorMessages());
    }

    public List<ReviewResponseModel> findReviewsByUser(long userId) {
        List<ReviewEntity> reviewEntityList = reviewRepository.findByUserId(userId);
        if (reviewEntityList.isEmpty())
            throw new RequestException(ErrorMessages.NO_REVIEW_GIVEN.getErrorMessages());

        List<ReviewResponseModel> reviewResponseModels = new ArrayList<>();
        for (ReviewEntity reviewEntity : reviewEntityList) {
            ReviewResponseModel reviewResponseModel = new ReviewResponseModel();
            BeanUtils.copyProperties(reviewEntity, reviewResponseModel);
            reviewResponseModel.setProductName(reviewEntity.getProductEntity().getProductName());
            reviewResponseModel.setProductDescription(reviewEntity.getProductEntity().getDescription());
            reviewResponseModels.add(reviewResponseModel);
        }
        return reviewResponseModels;
    }
}
