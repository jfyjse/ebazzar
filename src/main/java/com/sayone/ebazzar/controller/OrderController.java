package com.sayone.ebazzar.controller;

import com.sayone.ebazzar.common.RestResources;
import com.sayone.ebazzar.dto.*;
import com.sayone.ebazzar.model.request.OrderRequestModel;
import com.sayone.ebazzar.model.response.OrderResponsemodel;
import com.sayone.ebazzar.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.List;

@RestController
@RequestMapping(RestResources.ORDER_ROOT)
public class OrderController {

    @Autowired
    OrderService orderService;

    //http:localhost:8080/orders
    @PostMapping
    public OrderDto createOrder(@RequestBody OrderRequestModel orderRequestModel,
                                HttpServletRequest request) throws MessagingException, UnsupportedEncodingException {

        String url = orderService.getSiteURL(request);
        OrderDto returnValue = orderService.createOrder(orderRequestModel,url);

        return returnValue;
    }

    //http:localhost:8080/orders/1
    @GetMapping(path = RestResources.GET_ORDER_BY_ID)
    public OrderDto getOrder(@PathVariable Long id) {

        OrderDto orderDto = orderService.getOrderById(id);

        return orderDto;
    }

    //http:localhost:8080/orders/1?status="Shipped"
    @PutMapping(path = RestResources.UPDATE_ORDER_STATUS)
    public OrderDto updateOrderStatus(@PathVariable Long orderId,
                                      @RequestParam(value = "status") String status) {

       OrderDto orderDto = orderService.updateStatus(orderId,status);

        return orderDto;
    }

   // http:localhost:8080/orders/users/1
    @GetMapping(path = RestResources.VIEW_ALL_ORDERS)
    public List<OrderResponsemodel> getAllOrders(@PathVariable Long userid) {

        List<OrderResponsemodel> orderResponsemodels = orderService.findAllOrders(userid);

        return orderResponsemodels;
    }

    // http:localhost:8080/orders/users?status=Confirmed
    @GetMapping(path = RestResources.GET_ORDER_BY_STATUS)
    public List<OrderResponsemodel> getOrderByStatus(@RequestParam(value = "status") String status) {

        List<OrderResponsemodel> orderResponsemodels = orderService.findOrderByStatus(status);

        return orderResponsemodels;
    }

    // http:localhost:8080/orders/1
    @DeleteMapping(path = RestResources.CANCEL_ORDER)
    public OrderResponsemodel cancelOrder(@PathVariable Long orderId){

        OrderResponsemodel orderResponsemodel= orderService.cancelOrder(orderId);

        return orderResponsemodel;
    }

}
