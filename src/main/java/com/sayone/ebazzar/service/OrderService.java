package com.sayone.ebazzar.service;

import com.sayone.ebazzar.entity.*;
import com.sayone.ebazzar.exception.CustomException;
import com.sayone.ebazzar.exception.ErrorMessages;
import com.sayone.ebazzar.model.request.OrderRequestModel;
import com.sayone.ebazzar.repository.*;
import com.sayone.ebazzar.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    CartRepository cartRepository;

    @Autowired
    EmailService emailService;

    public OrderEntity createOrder(OrderRequestModel orderRequestModel,
                                String url) throws Exception {

        if (orderRequestModel.getCartId() == null)
            throw new CustomException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());

        CartEntity cartEntity = findCartById(orderRequestModel.getCartId());
        if(cartEntity.getCartStatus().equalsIgnoreCase("closed"))
            throw new CustomException(ErrorMessages.CART_ALREADY_CHECKED_OUT.getErrorMessage());

        for(CartItemEntity cartItemEntity : cartEntity.getCartItemEntityList()){
           if(cartItemEntity.getProductEntity().getQuantity()<cartItemEntity.getQuantity())
           {
               throw  new CustomException(ErrorMessages.OUT_OF_STOCK.getErrorMessage());
           }
        }

        AddressEntity shippingEntity = findAddressById(orderRequestModel.getShippingAddress());
        AddressEntity billingEntity = findAddressById(orderRequestModel.getBillingAddress());

        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setShippingAddress(shippingEntity);
        orderEntity.setBillingAddress(billingEntity);
        orderEntity.setOrderStatus("confirmed");
        orderEntity.setOrderAmount(cartEntity.getTotalAmount());

        cartEntity.setCartStatus("closed");
        CartEntity storedCartEntity = cartRepository.save(cartEntity);

        orderEntity.setCartEntity(storedCartEntity);

        OrderEntity storedEntity = orderRepository.save(orderEntity);
        for(CartItemEntity cartItemEntity:storedEntity.getCartEntity().getCartItemEntityList()){
            ProductEntity productEntity=productRepository.findByProductId(cartItemEntity.getProductEntity().getProductId());
            productEntity.setQuantity((productEntity.getQuantity())-(cartItemEntity.getQuantity()));
            productRepository.save(productEntity);
        }
        triggerMailForOrderPlacement(orderEntity,url);

        return storedEntity;
    }

    public Optional<OrderEntity> getOrderById(Long id) {

        return orderRepository.findByOrderId(id);
    }

    public List<OrderEntity> findAllOrders(Long userId) throws Exception {

        List<OrderEntity> orderEntityList = new ArrayList<OrderEntity>();

        List<CartEntity> cartEntityList = cartRepository.findByUserIdAndStatus(userId,"closed");
        if(cartEntityList.isEmpty())
            throw new CustomException(ErrorMessages.NO_ORDER_FOUND.getErrorMessage());

        for(CartEntity cartEntity : cartEntityList)
        {
            OrderEntity orderEntity = orderRepository.findByCartId(cartEntity.getCartId());
           orderEntityList.add(orderEntity);
        }
         return orderEntityList;
    }

    public OrderEntity updateStatus(Long orderId, String status,String url) throws Exception {
        String status1 = status.toLowerCase();
        Optional<OrderEntity> orderEntity=orderRepository.findByOrderId(orderId);
        if(!orderEntity.isPresent())
            throw new CustomException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
        OrderEntity orderEntity1=orderEntity.get();
        orderEntity1.setOrderStatus(status1);

        OrderEntity orderEntity2 = orderRepository.save(orderEntity1);
        triggerMailForOrderPlacement(orderEntity2,url);
        return orderEntity2;

    }

    public List<OrderEntity> findOrderByStatus(String status) {

        String status1 = status.toLowerCase();

        return orderRepository.findByOrderStatus(status1);

    }

    public OrderEntity cancelOrder(Long orderId,String url) throws Exception {

        Optional<OrderEntity> orderEntity = orderRepository.findByOrderId(orderId);
        if(!orderEntity.isPresent())
            throw new CustomException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());

        OrderEntity orderEntity1 = orderEntity.get();
        orderEntity1.setOrderStatus("cancelled");
        OrderEntity cancelledOrder = orderRepository.save(orderEntity1);
        triggerMailForOrderPlacement(cancelledOrder,url);

        return cancelledOrder;

    }

    public AddressEntity findAddressById(Long shippingAddress) throws Exception {
        Optional<AddressEntity> addressEntity = addressRepository.findByAddressId(shippingAddress);

        if(!addressEntity.isPresent()){
            throw new CustomException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
        }
        return addressEntity.get();

    }

    public CartEntity findCartById(Long cartId) throws Exception {
        Optional<CartEntity> cartEntity = cartRepository.findByCartId(cartId);

        if(!cartEntity.isPresent()){
            throw new CustomException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
        }
        return  cartEntity.get();
    }

    public String getSiteURL(HttpServletRequest request) {
        String siteURL = request.getRequestURL().toString();

        return siteURL.replace(request.getServletPath(), "");
    }

    public void triggerMailForOrderPlacement(OrderEntity orderEntity,String url)
            throws MessagingException, UnsupportedEncodingException {

        emailService.sendOrderConfirmedEmail(orderEntity,url);

    }
}
