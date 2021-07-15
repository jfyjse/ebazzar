package com.sayone.ebazzar.controller;

import com.sayone.ebazzar.common.RestResources;
import com.sayone.ebazzar.entity.OrderEntity;
import com.sayone.ebazzar.model.request.OrderRequestModel;
import com.sayone.ebazzar.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(RestResources.ORDER_ROOT)
public class OrderController {

    @Autowired
    OrderService orderService;

    //http:localhost:8080/orders
    @PostMapping
    public ResponseEntity<OrderEntity> createOrder(@RequestBody OrderRequestModel orderRequestModel, HttpServletRequest request) throws Exception {

        String url = orderService.getSiteURL(request);
        OrderEntity orderEntity = orderService.createOrder(orderRequestModel, url);

        return new ResponseEntity<>(orderEntity,HttpStatus.CREATED);

    }

    // http:localhost:8080/orders/users/1
    @GetMapping(path = RestResources.VIEW_ALL_ORDERS)
    public ResponseEntity<List<OrderEntity>> getAllOrders(@PathVariable Long userid) throws Exception {

        List<OrderEntity> orderEntityList = orderService.findAllOrders(userid);
        if(orderEntityList.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        else
        {
            return new ResponseEntity<>(orderEntityList,HttpStatus.OK);
        }
    }

    //http:localhost:8080/orders/1
    @GetMapping(path = RestResources.GET_ORDER_BY_ID)
    public ResponseEntity<OrderEntity> getOrder(@PathVariable Long id) {

        Optional<OrderEntity> orderEntity = orderService.getOrderById(id);
        if(orderEntity.isPresent()){
            return new ResponseEntity<>(orderEntity.get(), HttpStatus.OK);
        }
        else
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //http:localhost:8080/orders/1?status="Shipped"
    @PutMapping(path = RestResources.UPDATE_ORDER_STATUS)
    public ResponseEntity<OrderEntity> updateOrderStatus(@PathVariable Long orderId,
                                      @RequestParam(value = "status") String status,
                                      HttpServletRequest request) throws Exception {

        String url = orderService.getSiteURL(request);
        return new ResponseEntity<>(orderService.updateStatus(orderId, status,url),HttpStatus.ACCEPTED);


    }

    // http://localhost:8080/orders/users?status=Confirmed
    @GetMapping(path = RestResources.GET_ORDER_BY_STATUS)
    public ResponseEntity<List<OrderEntity>> getOrderByStatus(@RequestParam(value = "status") String status) {

        List<OrderEntity> orderEntityList = orderService.findOrderByStatus(status);
        if(orderEntityList.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else
        {
            return new ResponseEntity<>(orderEntityList,HttpStatus.OK);
        }
    }

    // http:localhost:8080/orders/1
    @DeleteMapping(path = RestResources.CANCEL_ORDER)
    public ResponseEntity<OrderEntity> cancelOrder(@PathVariable Long orderId,
                                          HttpServletRequest request) throws Exception {

        String url = orderService.getSiteURL(request);
        return new ResponseEntity<>(orderService.cancelOrder(orderId,url),HttpStatus.OK);

    }

}
