package com.sayone.ebazzar.controller;

import com.sayone.ebazzar.dto.ProductDto;
import com.sayone.ebazzar.dto.ReviewDto;
import com.sayone.ebazzar.dto.UserDto;
import com.sayone.ebazzar.entity.ProductEntity;
import com.sayone.ebazzar.entity.UserEntity;
import com.sayone.ebazzar.model.request.ReviewRequestModel;
import com.sayone.ebazzar.model.response.ReviewResponseModel;
import com.sayone.ebazzar.repository.ProductRepository;
import com.sayone.ebazzar.repository.UserRepository;
import com.sayone.ebazzar.service.ReviewService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    @Autowired
    ReviewService reviewService;

    @PostMapping(
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ReviewDto createReview(
            @RequestBody ReviewRequestModel reviewRequestModel)  {

        ReviewDto returnValue = new ReviewDto();

        if (reviewRequestModel.getProductId() == 0)
            throw new NullPointerException("Please specify the product for which review is to be added");

        if (reviewRequestModel.getUserId() == 0)
            throw new NullPointerException("Please specify the user who is adding the review");

        ProductDto productDto = reviewService.getProductById(reviewRequestModel.getProductId());
        UserDto userDto = reviewService.getUserById(reviewRequestModel.getUserId());

        ReviewDto reviewDto = new ReviewDto();
        BeanUtils.copyProperties(reviewRequestModel,reviewDto);

        reviewDto.setUserDto(userDto);
        reviewDto.setProductDto(productDto);

        try {
            returnValue = reviewService.createReview(reviewDto);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        finally {
            return returnValue;
        }


    }
}
