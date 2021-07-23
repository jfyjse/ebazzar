package com.sayone.ebazzar.service;
import com.sayone.ebazzar.entity.CartEntity;
import com.sayone.ebazzar.entity.CartItemEntity;
import com.sayone.ebazzar.entity.ProductEntity;
import com.sayone.ebazzar.exception.CustomException;
import com.sayone.ebazzar.exception.ErrorMessages;
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

        Optional<CartEntity> cartEntity = cartRepository.findByUserId(userId, "open");

        if (!cartEntity.isPresent()) {

            CartEntity cartEntity1 = new CartEntity();
            cartEntity1.setCartStatus("open");
            cartEntity1.setUserEntity(userRepository.findById(userId).get());

            CartItemEntity cartItemEntity = new CartItemEntity();
            ProductEntity productEntity = productRepository.findById(productId).get();
            if(productEntity.getQuantity()<quantity)
                throw new CustomException(ErrorMessages.OUT_OF_STOCK.getErrorMessage());

            cartItemEntity.setProductEntity(productEntity);
            cartItemEntity.setQuantity(quantity);
            cartItemEntity.setTotalPrice(quantity * productEntity.getPrice());

            cartEntity1.setCartItemEntityList(List.of(cartItemEntity));

            cartEntity1.setTotalAmount(cartItemEntity.getTotalPrice());

            cartItemRepository.save(cartItemEntity);
            productEntity.setQuantity(productEntity.getQuantity()-quantity);
            productRepository.save(productEntity);
            return cartRepository.save(cartEntity1);

        } else {

            CartEntity cartEntity1 = cartEntity.get();

            ProductEntity productEntity = productRepository.findById(productId).get();
            if(productEntity.getQuantity()<quantity)
                throw new CustomException(ErrorMessages.OUT_OF_STOCK.getErrorMessage());

            for(int i=0;i< cartEntity1.getCartItemEntityList().size();i++){
                if(productEntity == cartEntity1.getCartItemEntityList().get(i).getProductEntity())
                {
                    cartEntity1.getCartItemEntityList().get(i).setProductEntity(productEntity);
                    cartEntity1.getCartItemEntityList().get(i).setQuantity(cartEntity1.getCartItemEntityList().get(i).getQuantity()+quantity);
                    cartEntity1.getCartItemEntityList().get(i).setTotalPrice(cartEntity1.getCartItemEntityList().get(i).getTotalPrice() + ( quantity * productEntity.getPrice()));

                    productEntity.setQuantity(productEntity.getQuantity()-quantity);
                    productRepository.save(productEntity);

                    double grandtotal = 0;
                    for(CartItemEntity cartItemEntity:cartEntity1.getCartItemEntityList()){
                        grandtotal += cartItemEntity.getTotalPrice();
                    }
                    cartEntity1.setTotalAmount(grandtotal);

                    cartItemRepository.save(cartEntity1.getCartItemEntityList().get(i));
                    return cartRepository.save(cartEntity1);
                }
            }
            productEntity.setQuantity(productEntity.getQuantity()-quantity);
            productRepository.save(productEntity);

            CartItemEntity cartItemEntity = new CartItemEntity();
            cartItemEntity.setProductEntity(productEntity);
            cartItemEntity.setQuantity(quantity);
            cartItemEntity.setTotalPrice(quantity * productEntity.getPrice());
            cartEntity1.getCartItemEntityList().add(cartItemEntity);

            double grandtotal = 0;
            for(CartItemEntity cartItemEntity1:cartEntity1.getCartItemEntityList()){
                grandtotal += cartItemEntity1.getTotalPrice();
            }
            cartEntity1.setTotalAmount(grandtotal);

            cartItemRepository.save(cartItemEntity);
            return cartRepository.save(cartEntity1);
        }

    }

    public void removeProductFromCart(Long userId,Long productId) {
        ProductEntity productEntity = productRepository.findById(productId).get();
        Optional<CartEntity> cartEntity = cartRepository.findCartById(userId, "open");
        CartEntity cartEntity1 = cartEntity.get();
        Long cartId=cartEntity1.getCartId();
        Integer quantity= cartItemRepository.getQuantity(productId);
        System.out.println(quantity + " quantity");
        System.out.println("cartid= "+cartId);

        productEntity.setQuantity(productEntity.getQuantity()+quantity);
        productRepository.save(productEntity);

        cartItemRepository.deleteAProduct(cartId,productId);


        Optional<CartEntity> cartEntity2 = cartRepository.findCartById(userId, "open");
        CartEntity cartEntity3 = cartEntity2.get();



        double grandTotal = 0;
        for(CartItemEntity cartItemEntity3:cartEntity3.getCartItemEntityList()){
            grandTotal += cartItemEntity3.getTotalPrice();
        }
        cartEntity1.setTotalAmount(grandTotal);
        cartRepository.save(cartEntity3);



    }


    public void deleteAllProductsFromCart(Long cartId) {
        cartItemRepository.deleteAllProducts(cartId);
        cartRepository.deleteById(cartId);

    }

    public List<CartItemEntity> getCartItems(Long userId) {

        CartEntity cartEntity= cartRepository.getCartByUserId(userId,"open");
        return cartEntity.getCartItemEntityList();
    }
}
