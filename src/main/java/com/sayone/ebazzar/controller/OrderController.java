package com.sayone.ebazzar.controller;

import com.sayone.ebazzar.common.RestResources;
import com.sayone.ebazzar.dto.UserDto;
import com.sayone.ebazzar.model.request.OrderRequestModel;
import com.sayone.ebazzar.model.response.OrderDetailsModel;
import com.sayone.ebazzar.model.response.OrderResponsemodel;
import com.sayone.ebazzar.service.OrderService;
import com.sayone.ebazzar.service.UserService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping(RestResources.ORDER_ROOT)
public class OrderController {

    @Autowired
    OrderService orderService;

    @Autowired
    UserService userService;

    //http:localhost:8080/orders
    @ApiImplicitParams({@ApiImplicitParam(name = "authorization", value = "${userController.authorizationHeader.description}", paramType = "header")})
    @PostMapping
    public ResponseEntity<OrderResponsemodel> createOrder(@RequestBody OrderRequestModel orderRequestModel, HttpServletRequest request) throws Exception {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDto user = userService.getUser(auth.getName());

        String url = orderService.getSiteURL(request);
        OrderResponsemodel orderResponsemodel = orderService.createOrder(user.getUserId(),orderRequestModel, url);

        return new ResponseEntity<>(orderResponsemodel,HttpStatus.CREATED);

    }


    // http:localhost:8080/orders/all
    @ApiImplicitParams({@ApiImplicitParam(name = "authorization", value = "${userController.authorizationHeader.description}", paramType = "header")})
    @GetMapping(path = RestResources.VIEW_ORDERS)
    public ResponseEntity<List<OrderDetailsModel>> getLoggedInUserOrders() throws Exception {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDto user = userService.getUser(auth.getName());

        List<OrderDetailsModel> orderDetailsModels = orderService.findAllOrders(user.getUserId());
        if(orderDetailsModels.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        else
        {
            return new ResponseEntity<>(orderDetailsModels,HttpStatus.OK);
        }
    }

    //http:localhost:8080/orders/all/1
    @ApiImplicitParams({@ApiImplicitParam(name = "authorization", value = "${userController.authorizationHeader.description}", paramType = "header")})
    @GetMapping(path = RestResources.GET_ORDER_BY_ID)
    public ResponseEntity<OrderDetailsModel> getOrder(@PathVariable Long orderId) {

        OrderDetailsModel orderDetailsModel = orderService.getOrderById(orderId);
        return new ResponseEntity<>(orderDetailsModel, HttpStatus.OK);

    }

    //http:localhost:8080/orders/1?status="Shipped"
    @ApiImplicitParams({@ApiImplicitParam(name = "authorization", value = "${userController.authorizationHeader.description}", paramType = "header")})
    @PutMapping(path = RestResources.UPDATE_ORDER_STATUS)
    public ResponseEntity<OrderResponsemodel> updateOrderStatus(@PathVariable Long orderId,
                                      @RequestParam(value = "status") String status,
                                      HttpServletRequest request) throws Exception {

        String url = orderService.getSiteURL(request);
        return new ResponseEntity<>(orderService.updateStatus(orderId, status,url),HttpStatus.ACCEPTED);


    }

    // http://localhost:8080/orders?status=Confirmed
    @ApiImplicitParams({@ApiImplicitParam(name = "authorization", value = "${userController.authorizationHeader.description}", paramType = "header")})
    @GetMapping
    public ResponseEntity<List<OrderDetailsModel>> getOrderByStatus(@RequestParam(value = "status") String status) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDto user = userService.getUser(auth.getName());

        List<OrderDetailsModel> orderDetailsModels = orderService.findOrderByStatus(user.getUserId(),status);
        if(orderDetailsModels.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else
        {
            return new ResponseEntity<>(orderDetailsModels,HttpStatus.OK);
        }
    }



    // http:localhost:8080/orders/1
    @ApiImplicitParams({@ApiImplicitParam(name = "authorization", value = "${userController.authorizationHeader.description}", paramType = "header")})
    @DeleteMapping(path = RestResources.CANCEL_ORDER)
    public ResponseEntity<?> cancelOrder(@PathVariable Long orderId,
                                          HttpServletRequest request) throws Exception {

        String url = orderService.getSiteURL(request);
        orderService.cancelOrder(orderId,url);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);

    }

}
