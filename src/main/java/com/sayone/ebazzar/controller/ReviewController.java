package com.sayone.ebazzar.controller;

import com.sayone.ebazzar.common.RestResources;
import com.sayone.ebazzar.dto.ReviewDto;
import com.sayone.ebazzar.exception.CustomException;
import com.sayone.ebazzar.exception.ErrorMessages;
import com.sayone.ebazzar.model.request.ReviewRequestModel;
import com.sayone.ebazzar.model.response.ReviewListModel;
import com.sayone.ebazzar.model.response.ReviewResponseModel;
import com.sayone.ebazzar.service.ReviewService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
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
   @ApiImplicitParams({@ApiImplicitParam(name = "authorization", value = "${userController.authorizationHeader.description}", paramType = "header")})
    @PostMapping
    public ResponseEntity<ReviewResponseModel> createReview(@RequestBody ReviewRequestModel reviewRequestModel) throws Exception {

        if (reviewRequestModel.getProductId() == null || reviewRequestModel.getUserId() == null)
            throw new CustomException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());

        return new ResponseEntity(reviewService.createReview(reviewRequestModel),HttpStatus.CREATED);
    }

   // http://localhost:8080/reviews/update/1
   @ApiImplicitParams({@ApiImplicitParam(name = "authorization", value = "${userController.authorizationHeader.description}", paramType = "header")})
    @PutMapping(path = RestResources.UPDATE_RATING_BY_ID)
    public ResponseEntity<ReviewResponseModel> updateRating(@PathVariable Long reviewId,
                                  @RequestBody ReviewRequestModel reviewRequestModel) {

        ReviewDto reviewDto = new ReviewDto();
        BeanUtils.copyProperties(reviewRequestModel,reviewDto);
        return new ResponseEntity<>(reviewService.updateReview(reviewId,reviewDto),HttpStatus.OK);
    }

    // http://localhost:8080/reviews/1
    @ApiImplicitParams({@ApiImplicitParam(name = "authorization", value = "${userController.authorizationHeader.description}", paramType = "header")})
    @GetMapping(path = RestResources.GET_RATING_PID)
    public ResponseEntity<List<ReviewListModel>> getRatingUsingPid(@PathVariable Long pid){

       List<ReviewListModel> reviewListModelList = reviewService.findRatingByProductId(pid);
       if(reviewListModelList.isEmpty()){
           return new ResponseEntity<>(HttpStatus.NO_CONTENT);
       }
       else
       {
           return new ResponseEntity<>(reviewListModelList,HttpStatus.OK);
       }
    }

    // http://localhost:8080/reviews/delete?pid=1&uid=2
    @ApiImplicitParams({@ApiImplicitParam(name = "authorization", value = "${userController.authorizationHeader.description}", paramType = "header")})
    @DeleteMapping(path = RestResources.DELETE_REVIEW)
    public ResponseEntity<?> deleteRating(@RequestParam(value = "pid") Long productId,
                                  @RequestParam(value = "uid") Long userId) {

        reviewService.deleteReview(productId,userId);
        return new ResponseEntity<>(HttpStatus.OK);

    }


}
