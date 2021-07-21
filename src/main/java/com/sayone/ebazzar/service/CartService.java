package com.sayone.ebazzar.service;


import com.sayone.ebazzar.entity.CartEntity;
import com.sayone.ebazzar.entity.CartItemEntity;
import com.sayone.ebazzar.entity.ProductEntity;
import com.sayone.ebazzar.entity.UserEntity;
import com.sayone.ebazzar.repository.CartItemRepository;
import com.sayone.ebazzar.repository.CartRepository;
import com.sayone.ebazzar.repository.ProductRepository;
import com.sayone.ebazzar.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    CartItemRepository cartItemRepository;
    @Autowired
    CartRepository cartRepository;

    public CartEntity addCartItem(Long userId, Long productId, Integer quantity) {
        System.out.println("start");

        CartEntity cartEntity = cartRepository.findByUserIdCart(userId, "open");
        System.out.println("3");

        //CartEntity productIdInCart = cartItemRepository.findProductExist(productId);


        if (cartEntity == null) {
            System.out.println("4");
            cartEntity.setCartStatus("open");
            cartEntity.setUserEntity(userRepository.findById(userId).get());

            CartItemEntity cartItemEntity = new CartItemEntity();
            ProductEntity productEntity = productRepository.findById(productId).get();
            cartItemEntity.setProductEntity(productEntity);
            cartItemEntity.setQuantity(quantity);
            cartItemEntity.setTotalPrice(quantity * productEntity.getPrice());
            cartEntity.setCartItemEntityList(List.of(cartItemEntity));
            return cartRepository.save(cartEntity);
        } else {
            System.out.println("5");
            CartItemEntity cartItemEntity = new CartItemEntity();
            ProductEntity productEntity = productRepository.findById(productId).get();
            cartItemEntity.setProductEntity(productEntity);
            cartItemEntity.setQuantity(quantity);
            cartItemEntity.setTotalPrice(quantity * productEntity.getPrice());
            cartEntity.getCartItemEntityList().add(cartItemEntity);
            return cartRepository.save(cartEntity);
        }


    }

    public void deleteProductFromCart(Long productId) {
        cartItemRepository.deleteProduct(productId);

    }

    public void deleteAllProductsFromCart(Long cartId) {
        cartItemRepository.deleteAllProducts(cartId);
    }

    public List<CartItemEntity> getCartItems(Long userId) {

        CartEntity cartEntity= cartRepository.getCartByUserId(userId,"open");
        return cartEntity.getCartItemEntityList();
    }
}
