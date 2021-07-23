package com.sayone.ebazzar.service;

import com.sayone.ebazzar.dto.UserDto;
import com.sayone.ebazzar.entity.*;
import com.sayone.ebazzar.exception.CustomException;
import com.sayone.ebazzar.exception.ErrorMessages;
import com.sayone.ebazzar.model.request.OrderRequestModel;
import com.sayone.ebazzar.model.response.AddressResponseModel;
import com.sayone.ebazzar.model.response.CartItemDetails;
import com.sayone.ebazzar.model.response.OrderDetailsModel;
import com.sayone.ebazzar.model.response.OrderResponsemodel;
import com.sayone.ebazzar.repository.*;
import com.sayone.ebazzar.service.EmailService;
import org.springframework.beans.BeanUtils;
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

    public OrderResponsemodel createOrder(Long userId, OrderRequestModel orderRequestModel,
                                          String url) throws Exception {

        CartEntity cartEntity = findCartByUserId(userId);

        if(cartEntity.getCartItemEntityList().isEmpty())
            throw new CustomException(ErrorMessages.EMPTY_CART.getErrorMessages());

        for(CartItemEntity cartItemEntity : cartEntity.getCartItemEntityList()){
           if(cartItemEntity.getProductEntity().getQuantity()<cartItemEntity.getQuantity())
           {
               throw  new CustomException(ErrorMessages.OUT_OF_STOCK.getErrorMessages());
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

        triggerMailForOrderPlacement(orderEntity,url);

        OrderResponsemodel orderResponsemodel = new OrderResponsemodel();
        BeanUtils.copyProperties(storedEntity,orderResponsemodel);

        AddressResponseModel addressResponseModel = new AddressResponseModel();
        BeanUtils.copyProperties(storedEntity.getShippingAddress(),addressResponseModel);
        orderResponsemodel.setShippingAddress(addressResponseModel);
        return orderResponsemodel;
    }

    public List<OrderDetailsModel> findAllOrders(Long userId) throws Exception {

        List<CartEntity> cartEntityList = cartRepository.findByUserIdAndStatus(userId,"closed");

        if(cartEntityList.isEmpty())
            throw new CustomException(ErrorMessages.NO_ORDER_FOUND.getErrorMessages());

        List<OrderDetailsModel> orderDetailsModels = new ArrayList<OrderDetailsModel>();
        for(CartEntity cartEntity : cartEntityList)
        {
            OrderEntity orderEntity = orderRepository.findByCartId(cartEntity.getCartId());
            OrderDetailsModel orderDetailsModel = new OrderDetailsModel();
            BeanUtils.copyProperties(orderEntity,orderDetailsModel);

            List<CartItemDetails> cartItemDetails = new ArrayList<CartItemDetails>();
            for(CartItemEntity cartItemEntity:orderEntity.getCartEntity().getCartItemEntityList())
            {
                CartItemDetails cartItemDetail = new CartItemDetails();
                cartItemDetail.setProductName(cartItemEntity.getProductEntity().getProductName());
                cartItemDetail.setQuantity(cartItemEntity.getQuantity());
                cartItemDetail.setTotalPrice(cartItemEntity.getTotalPrice());
                cartItemDetails.add(cartItemDetail);
            }
            orderDetailsModel.setCartItemDetailsList(cartItemDetails);

            AddressResponseModel billingAddress = new AddressResponseModel();
            BeanUtils.copyProperties(orderEntity.getBillingAddress(),billingAddress);
            orderDetailsModel.setBillingAddress(billingAddress);

            AddressResponseModel shippingAddress = new AddressResponseModel();
            BeanUtils.copyProperties(orderEntity.getShippingAddress(),shippingAddress);
            orderDetailsModel.setShippingAddress(shippingAddress);

            orderDetailsModels.add(orderDetailsModel);
        }

        return orderDetailsModels;
    }

    public OrderDetailsModel getOrderById(Long id) {

        Optional<OrderEntity> orderEntity= orderRepository.findByOrderId(id);
        if(!orderEntity.isPresent())
            throw new CustomException(ErrorMessages.INVALID_ORDERID.getErrorMessages());

        OrderDetailsModel orderDetailsModel = new OrderDetailsModel();
        BeanUtils.copyProperties(orderEntity.get(),orderDetailsModel);

        List<CartItemDetails> cartItemDetails = new ArrayList<CartItemDetails>();
        for(CartItemEntity cartItemEntity:orderEntity.get().getCartEntity().getCartItemEntityList())
        {
            CartItemDetails cartItemDetail = new CartItemDetails();
            cartItemDetail.setProductName(cartItemEntity.getProductEntity().getProductName());
            cartItemDetail.setQuantity(cartItemEntity.getQuantity());
            cartItemDetail.setTotalPrice(cartItemEntity.getTotalPrice());
            cartItemDetails.add(cartItemDetail);
        }
        orderDetailsModel.setCartItemDetailsList(cartItemDetails);

        AddressResponseModel billingAddress = new AddressResponseModel();
        BeanUtils.copyProperties(orderEntity.get().getBillingAddress(),billingAddress);
        orderDetailsModel.setBillingAddress(billingAddress);

        AddressResponseModel shippingAddress = new AddressResponseModel();
        BeanUtils.copyProperties(orderEntity.get().getShippingAddress(),shippingAddress);
        orderDetailsModel.setShippingAddress(shippingAddress);

        return orderDetailsModel;
    }



    public OrderResponsemodel updateStatus(Long orderId, String status,String url) throws Exception {
        String status1 = status.toLowerCase();
        OrderResponsemodel orderResponsemodel = new OrderResponsemodel();

        Optional<OrderEntity> orderEntity=orderRepository.findByOrderId(orderId);
        if(!orderEntity.isPresent())
            throw new CustomException(ErrorMessages.INVALID_ORDERID.getErrorMessages());
        OrderEntity orderEntity1=orderEntity.get();
        orderEntity1.setOrderStatus(status1);

        OrderEntity orderEntity2 = orderRepository.save(orderEntity1);
        triggerMailForOrderPlacement(orderEntity2,url);

        BeanUtils.copyProperties(orderEntity2,orderResponsemodel);

        AddressResponseModel addressResponseModel = new AddressResponseModel();
        BeanUtils.copyProperties(orderEntity2.getShippingAddress(),addressResponseModel);
        orderResponsemodel.setShippingAddress(addressResponseModel);
        return orderResponsemodel;
    }

    public List<OrderDetailsModel> findOrderByStatus(Long userId,String status) {

        String status1 = status.toLowerCase();
        List<OrderDetailsModel> orderDetailsModels = new ArrayList<OrderDetailsModel>();
        List<CartEntity> cartEntityList = cartRepository.findByUserIdAndStatus(userId,"closed");

        if(cartEntityList.isEmpty())
            throw new CustomException(ErrorMessages.NO_ORDER_FOUND.getErrorMessages());

        for(CartEntity cartEntity : cartEntityList)
        {
            OrderEntity orderEntity = orderRepository.findBycartIdandStatus(cartEntity.getCartId(),status1);
            if(orderEntity != null){
                OrderDetailsModel orderDetailsModel = new OrderDetailsModel();
                BeanUtils.copyProperties(orderEntity,orderDetailsModel);

                List<CartItemDetails> cartItemDetails = new ArrayList<CartItemDetails>();
                for(CartItemEntity cartItemEntity:orderEntity.getCartEntity().getCartItemEntityList())
                {
                    CartItemDetails cartItemDetail = new CartItemDetails();
                    cartItemDetail.setProductName(cartItemEntity.getProductEntity().getProductName());
                    cartItemDetail.setQuantity(cartItemEntity.getQuantity());
                    cartItemDetail.setTotalPrice(cartItemEntity.getTotalPrice());
                    cartItemDetails.add(cartItemDetail);
                }
                orderDetailsModel.setCartItemDetailsList(cartItemDetails);

                AddressResponseModel billingAddress = new AddressResponseModel();
                BeanUtils.copyProperties(orderEntity.getBillingAddress(),billingAddress);
                orderDetailsModel.setBillingAddress(billingAddress);

                AddressResponseModel shippingAddress = new AddressResponseModel();
                BeanUtils.copyProperties(orderEntity.getShippingAddress(),shippingAddress);
                orderDetailsModel.setShippingAddress(shippingAddress);

                orderDetailsModels.add(orderDetailsModel);
            }

        }
        return orderDetailsModels;
    }

    public OrderEntity cancelOrder(Long orderId,String url) throws Exception {

        Optional<OrderEntity> orderEntity = orderRepository.findByOrderId(orderId);
        if(!orderEntity.isPresent())
            throw new CustomException(ErrorMessages.INVALID_ORDERID.getErrorMessages());

        OrderEntity orderEntity1 = orderEntity.get();
        orderEntity1.setOrderStatus("cancelled");
        OrderEntity cancelledOrder = orderRepository.save(orderEntity1);

        for(CartItemEntity cartItemEntity : orderEntity1.getCartEntity().getCartItemEntityList())
        {
            ProductEntity productEntity = productRepository.findByProductId(cartItemEntity.getProductEntity().getProductId());
            productEntity.setQuantity(productEntity.getQuantity()+cartItemEntity.getQuantity());
            productRepository.save(productEntity);
        }


        triggerMailForOrderPlacement(cancelledOrder,url);

        return cancelledOrder;

    }

    public AddressEntity findAddressById(Long shippingAddress) throws Exception {
        Optional<AddressEntity> addressEntity = addressRepository.findByAddressId(shippingAddress);

        if(!addressEntity.isPresent()){
            throw new CustomException(ErrorMessages.INVALID_ADDRESS.getErrorMessages());
        }
        return addressEntity.get();

    }

    private CartEntity findCartByUserId(Long userId) {
        Optional<CartEntity> cartEntity = cartRepository.findByUserId(userId,"open");

        if(!cartEntity.isPresent()){
            throw new CustomException(ErrorMessages.CART_ALREADY_CHECKED_OUT.getErrorMessages());
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
