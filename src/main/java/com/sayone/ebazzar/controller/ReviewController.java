package com.sayone.ebazzar.controller;

import com.sayone.ebazzar.common.RestResources;
import com.sayone.ebazzar.dto.ProductDto;
import com.sayone.ebazzar.dto.ReviewDto;
import com.sayone.ebazzar.dto.UserDto;
import com.sayone.ebazzar.model.request.ReviewRequestModel;
import com.sayone.ebazzar.service.ReviewService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(RestResources.REVIEW_ROOT)
public class ReviewController {

    @Autowired
    ReviewService reviewService;

   // http://localhost:8080/reviews
    @PostMapping
    public ReviewDto createReview(@RequestBody ReviewRequestModel reviewRequestModel) {

        if (reviewRequestModel.getProductId() == null)
            throw new NullPointerException("Please specify the product for which review is to be added");

        if (reviewRequestModel.getUserId() == null)
            throw new NullPointerException("Please specify the user who is adding the review");

        ProductDto productDto = reviewService.getProductById(reviewRequestModel.getProductId());
        UserDto userDto = reviewService.getUserById(reviewRequestModel.getUserId());

        ReviewDto reviewDto = new ReviewDto();
        BeanUtils.copyProperties(reviewRequestModel, reviewDto);

        reviewDto.setUserDto(userDto);
        reviewDto.setProductDto(productDto);

        ReviewDto returnValue = reviewService.createReview(reviewDto);

        return returnValue;


    }

   // http://localhost:8080/reviews/update/1
    @PutMapping(path = RestResources.UPDATE_RATING_BY_ID)
    public ReviewDto updateRating(@PathVariable Long reviewId,
                                  @RequestBody ReviewRequestModel reviewRequestModel) {

        ReviewDto reviewDto = new ReviewDto();
        BeanUtils.copyProperties(reviewRequestModel,reviewDto);
        ReviewDto returnValue = reviewService.updateReview(reviewId,reviewDto);
        return returnValue;
    }

    // http://localhost:8080/reviews/1
    @GetMapping(path = RestResources.GET_RATING_PID)
    public ReviewDto getRatingUsingPid(@PathVariable Long productId){

        return null;
    }

    // http://localhost:8080/reviews/delete?pid=1&uid=2
    @DeleteMapping(path = RestResources.DELETE_REVIEW)
    public ReviewDto deleteRating(@RequestParam(value = "pid") Long productId,
                                  @RequestParam(value = "uid") Long userId) {

        return null;
    }


}
