package com.sayone.ebazzar.controller;

import com.sayone.ebazzar.common.RestResources;
import com.sayone.ebazzar.dto.ReviewDto;
import com.sayone.ebazzar.dto.UserDto;
import com.sayone.ebazzar.exception.CustomException;
import com.sayone.ebazzar.exception.ErrorMessages;
import com.sayone.ebazzar.model.request.ReviewRequestModel;
import com.sayone.ebazzar.model.response.ReviewResponseModel;
import com.sayone.ebazzar.service.ReviewService;
import com.sayone.ebazzar.service.UserService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(RestResources.REVIEW_ROOT)
public class ReviewController {

    @Autowired
    ReviewService reviewService;

    @Autowired
    UserService userService;

   // http://localhost:8080/reviews
   @ApiImplicitParams({@ApiImplicitParam(name = "authorization", value = "${userController.authorizationHeader.description}", paramType = "header")})
    @PostMapping
    public ResponseEntity<ReviewResponseModel> createReview(@RequestBody ReviewRequestModel reviewRequestModel) throws Exception {

       Authentication auth = SecurityContextHolder.getContext().getAuthentication();
       UserDto user = userService.getUser(auth.getName());

        if (reviewRequestModel.getProductId() == null)
            throw new CustomException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessages());

        return new ResponseEntity(reviewService.createReview(reviewRequestModel,user.getUserId()),HttpStatus.CREATED);
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

    // http://localhost:8080/reviews/all
    @ApiImplicitParams({@ApiImplicitParam(name = "authorization", value = "${userController.authorizationHeader.description}", paramType = "header")})
    @GetMapping(path = RestResources.GET_ALL_REVIEWS)
    public ResponseEntity<List<ReviewResponseModel>> getAllReview(){

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDto user = userService.getUser(auth.getName());
        List<ReviewResponseModel> reviewResponseModels = reviewService.findReviewsByUser(user.getUserId());
        if(reviewResponseModels.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        else
        {
            return new ResponseEntity<>(reviewResponseModels,HttpStatus.OK);
        }
    }
    // http://localhost:8080/reviews/all/1
    @ApiImplicitParams({@ApiImplicitParam(name = "authorization", value = "${userController.authorizationHeader.description}", paramType = "header")})
    @GetMapping(path = RestResources.GET_RATING_FOR_PRODUCT)
    public ResponseEntity<ReviewResponseModel> getRatingUsingPid(@PathVariable Long pid){

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDto user = userService.getUser(auth.getName());

        ReviewResponseModel reviewResponseModel = reviewService.findReviewForProduct(pid,user.getUserId());
       if(reviewResponseModel == null ){
           return new ResponseEntity<>(HttpStatus.NO_CONTENT);
       }
       else
       {
           return new ResponseEntity<>(reviewResponseModel,HttpStatus.OK);
       }
    }

    // http://localhost:8080/reviews/delete?pid=1
    @ApiImplicitParams({@ApiImplicitParam(name = "authorization", value = "${userController.authorizationHeader.description}", paramType = "header")})
    @DeleteMapping(path = RestResources.DELETE_REVIEW)
    public ResponseEntity<?> deleteRating(@RequestParam(value = "pid") Long productId) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDto user = userService.getUser(auth.getName());

        reviewService.deleteReview(productId,user.getUserId());
        return new ResponseEntity<>(HttpStatus.OK);

    }


}
