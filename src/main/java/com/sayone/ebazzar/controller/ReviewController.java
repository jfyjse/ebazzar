package com.sayone.ebazzar.controller;

import com.sayone.ebazzar.common.RestResources;
import com.sayone.ebazzar.dto.ReviewDto;
import com.sayone.ebazzar.entity.ReviewEntity;
import com.sayone.ebazzar.exception.CustomException;
import com.sayone.ebazzar.exception.ErrorMessages;
import com.sayone.ebazzar.model.request.ReviewRequestModel;
import com.sayone.ebazzar.service.ReviewService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(RestResources.REVIEW_ROOT)
public class ReviewController {

    @Autowired
    ReviewService reviewService;

   // http://localhost:8080/reviews
    @PostMapping
    public ResponseEntity<ReviewEntity> createReview(@RequestBody ReviewRequestModel reviewRequestModel) throws Exception {

        if (reviewRequestModel.getProductId() == null || reviewRequestModel.getUserId() == null)
            throw new CustomException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());

        return new ResponseEntity(reviewService.createReview(reviewRequestModel),HttpStatus.CREATED);
    }

   // http://localhost:8080/reviews/update/1
    @PutMapping(path = RestResources.UPDATE_RATING_BY_ID)
    public ResponseEntity<ReviewEntity> updateRating(@PathVariable Long reviewId,
                                  @RequestBody ReviewRequestModel reviewRequestModel) {

        ReviewDto reviewDto = new ReviewDto();
        BeanUtils.copyProperties(reviewRequestModel,reviewDto);
        return new ResponseEntity<>(reviewService.updateReview(reviewId,reviewDto),HttpStatus.OK);
    }

    // http://localhost:8080/reviews/1
    @GetMapping(path = RestResources.GET_RATING_PID)
    public ResponseEntity<List<ReviewEntity>> getRatingUsingPid(@PathVariable Long pid){

       List<ReviewEntity> reviewEntityList = reviewService.findRatingByProductId(pid);
       if(reviewEntityList.isEmpty()){
           return new ResponseEntity<>(HttpStatus.NO_CONTENT);
       }
       else
       {
           return new ResponseEntity<>(reviewEntityList,HttpStatus.OK);
       }
    }

    // http://localhost:8080/reviews/delete?pid=1&uid=2
    @DeleteMapping(path = RestResources.DELETE_REVIEW)
    public ResponseEntity<?> deleteRating(@RequestParam(value = "pid") Long productId,
                                  @RequestParam(value = "uid") Long userId) {

        reviewService.deleteReview(productId,userId);
        return new ResponseEntity<>(HttpStatus.OK);

    }


}
