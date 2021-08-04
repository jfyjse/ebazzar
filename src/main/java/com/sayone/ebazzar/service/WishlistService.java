package com.sayone.ebazzar.service;

import com.sayone.ebazzar.entity.ProductEntity;
import com.sayone.ebazzar.entity.WishlistEntity;
import com.sayone.ebazzar.exception.ErrorMessages;
import com.sayone.ebazzar.exception.RequestException;
import com.sayone.ebazzar.repository.ProductRepository;
import com.sayone.ebazzar.repository.UserRepository;
import com.sayone.ebazzar.repository.WishlistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WishlistService {

    @Autowired
    WishlistRepository wishlistRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ProductRepository productRepository;

    public  void removeProductFromWishlist(long userId, Long productId) {
        Optional<WishlistEntity> wishlistEntity =wishlistRepository.findByUserId(userId);
        WishlistEntity wishlistEntity1 = wishlistEntity.get();
        Long wishlistId =wishlistEntity1.getWishlistId();
        wishlistRepository.deleteProduct(wishlistId,productId);
    }

    public WishlistEntity addProductToWishlist(Long userId,Long productId) {
        Optional<WishlistEntity> wishlistEntity = wishlistRepository.findByUserId(userId);
        WishlistEntity wishlistEntity1 = new WishlistEntity();
        if(wishlistEntity.isEmpty()) {
            wishlistEntity1.setUserEntity(userRepository.findByUserId(userId));
            ProductEntity productEntity = productRepository.findById(productId).get();
            wishlistEntity1.setProductEntityList(List.of(productEntity));
        }
        else{
            wishlistEntity1 = wishlistEntity.get();
            ProductEntity productEntity = productRepository.findById(productId).get();
            for (int i=0;i<wishlistEntity1.getProductEntityList().size();i++){
                if(productEntity == wishlistEntity1.getProductEntityList().get(i)){
                    throw new RequestException(ErrorMessages.WISH_PRODUCT_EXISTS.getErrorMessages());
                }
            }
            wishlistEntity1.getProductEntityList().add(productEntity);
        }
        return wishlistRepository.save(wishlistEntity1);
    }

    public List<ProductEntity> getWishlistItems(long userId) {
        WishlistEntity wishlistEntity =wishlistRepository.getByUserId(userId);
        return wishlistEntity.getProductEntityList();
    }
}
