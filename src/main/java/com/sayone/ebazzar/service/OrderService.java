package com.sayone.ebazzar.service;

import com.sayone.ebazzar.dto.*;
import com.sayone.ebazzar.entity.*;
import com.sayone.ebazzar.model.request.OrderRequestModel;
import com.sayone.ebazzar.model.response.OrderResponsemodel;
import com.sayone.ebazzar.repository.*;
import com.sayone.ebazzar.service.addons.EmailService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    UserRepository userRepository;

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

    ModelMapper modelMapper = new ModelMapper();

    public UserDto findUserById(Long userId) {
        Optional<UserEntity> userEntity = userRepository.findById(userId);
        if(!userEntity.isPresent()){
            throw new NullPointerException("User not Present");
        }
        UserDto userDto = modelMapper.map(userEntity.get(),UserDto.class);
        return userDto;
    }

    public AddressEntity findAddressById(Long shippingAddress) {
        Optional<AddressEntity> addressEntity = addressRepository.findByAddressId(shippingAddress);

        if(!addressEntity.isPresent()){
            throw new NullPointerException("Address not Present");
        }
        return addressEntity.get();

    }

    public ProductDto findProductById(Long productId) {
        Optional<ProductEntity> productEntity1 = productRepository.findByProductId(productId);
        if(!productEntity1.isPresent()){
            throw new NullPointerException("Product not Present");
        }
        ProductDto productDto = modelMapper.map(productEntity1.get(),ProductDto.class);
        return  productDto;
    }

    public OrderDto createOrder(OrderRequestModel orderRequestModel,
                                String url) throws MessagingException, UnsupportedEncodingException {

        ModelMapper modelMapper = new ModelMapper();

        if (orderRequestModel.getCartId() == null)
            throw new NullPointerException("Please give a cart id");

        CartEntity cartEntity = findCartById(orderRequestModel.getCartId());
        if(cartEntity.getCartStatus().equalsIgnoreCase("closed"))
            throw new NullPointerException("Cart is Already checked out");

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

        this.triggerMailForAccountActivation(orderEntity,url);

        OrderDto returnValue = modelMapper.map(storedEntity,OrderDto.class);

        CartDto cartDto = modelMapper.map(storedEntity.getCartEntity(),CartDto.class);
        cartDto.setUserDto(modelMapper.map(storedEntity.getCartEntity().getUserEntity(),UserDto.class));
        List<ProductDto> productDtoList = new ArrayList<ProductDto>();
        for(ProductEntity productEntity:storedEntity.getCartEntity().getProductEntityList()){
            productDtoList.add(modelMapper.map(productEntity,ProductDto.class));
        }
        cartDto.setProductDtoList(productDtoList);
        returnValue.setCartDto(cartDto);

        returnValue.setShippingAddress(modelMapper.map(storedEntity.getShippingAddress(),AddressDto.class));
        returnValue.setBillingAddress(modelMapper.map(storedEntity.getBillingAddress(),AddressDto.class));

        return  returnValue;

    }

    public OrderDto getOrderById(Long id) {
        Optional<OrderEntity> orderEntity = orderRepository.findByOrderId(id);

        if(!orderEntity.isPresent()){
            throw new NullPointerException("Order not Present");
        }
        OrderDto returnValue = modelMapper.map(orderEntity.get(),OrderDto.class);

        CartDto cartDto = modelMapper.map(orderEntity.get().getCartEntity(),CartDto.class);
        cartDto.setUserDto(modelMapper.map(orderEntity.get().getCartEntity().getUserEntity(),
                UserDto.class));
        List<ProductDto> productDtoList = new ArrayList<ProductDto>();
        for(ProductEntity productEntity:orderEntity.get().getCartEntity().getProductEntityList())
        {
            ProductDto productDto = modelMapper.map(productEntity,ProductDto.class);
            productDtoList.add(productDto);
        }
        cartDto.setProductDtoList(productDtoList);
        returnValue.setCartDto(cartDto);

        returnValue.setBillingAddress(modelMapper.map(orderEntity.get().getBillingAddress(),
                AddressDto.class));
        returnValue.setShippingAddress(modelMapper.map(orderEntity.get().getShippingAddress(),
                AddressDto.class));
        
        return  returnValue;
    }

    public void triggerMailForAccountActivation(OrderEntity orderEntity,String url)
            throws MessagingException, UnsupportedEncodingException {

        emailService.sendOrderConfirmedEmail(orderEntity,url);

    }

    public CartEntity findCartById(Long cartId) {
        Optional<CartEntity> cartEntity = cartRepository.findByCartId(cartId);

        if(!cartEntity.isPresent()){
            throw new NullPointerException("Cart not Present");
        }

        return  cartEntity.get();

    }

    public String getSiteURL(HttpServletRequest request) {
        String siteURL = request.getRequestURL().toString();

        return siteURL.replace(request.getServletPath(), "");
    }

    public List<OrderResponsemodel> findAllOrders(Long userId) {

        List<OrderResponsemodel> orderResponsemodels = new ArrayList<OrderResponsemodel>();

        List<CartEntity> cartEntityList = cartRepository.findByUserIdAndStatus(userId,"closed");
        for(CartEntity cartEntity : cartEntityList)
        {
            OrderEntity orderEntity = orderRepository.findByCartId(cartEntity.getCartId());
            OrderResponsemodel OrderResponsemodel = modelMapper.map(orderEntity,OrderResponsemodel.class);
            orderResponsemodels.add(OrderResponsemodel);
        }
         return orderResponsemodels;
    }

    public OrderDto updateStatus(Long orderId, String status) {
        String status1 = status.toLowerCase();
        Optional<OrderEntity> orderEntity=orderRepository.findByOrderId(orderId);
        OrderEntity orderEntity1=orderEntity.get();
        orderEntity1.setOrderStatus(status1);

        OrderEntity orderEntity2 = orderRepository.save(orderEntity1);
        OrderDto returnValue = modelMapper.map(orderEntity2,OrderDto.class);

        return  returnValue;

    }

    public List<OrderResponsemodel> findOrderByStatus(String status) {

        String status1 = status.toLowerCase();
        List<OrderResponsemodel> returnValue = new ArrayList<OrderResponsemodel>();
        List<OrderEntity> orderEntityList = orderRepository.findByOrderStatus(status1);

        for ( OrderEntity orderEntity : orderEntityList)
        {
            OrderResponsemodel orderResponsemodel =  modelMapper.map(orderEntity,OrderResponsemodel.class);
            returnValue.add(orderResponsemodel);
        }
        return  returnValue;
    }

    public OrderResponsemodel cancelOrder(Long orderId) {

        Optional<OrderEntity> orderEntity = orderRepository.findByOrderId(orderId);
        OrderEntity orderEntity1 = orderEntity.get();
        orderEntity1.setOrderStatus("cancelled");
        OrderEntity cancelledOrder = orderRepository.save(orderEntity1);

        OrderResponsemodel returnValue = modelMapper.map(cancelledOrder,OrderResponsemodel.class);

        return returnValue;

    }
}
