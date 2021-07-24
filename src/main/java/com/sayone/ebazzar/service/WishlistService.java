package com.sayone.ebazzar.service;

import com.sayone.ebazzar.entity.ProductEntity;
import com.sayone.ebazzar.entity.WishlistEntity;
import com.sayone.ebazzar.entity.WishlistItemEntity;
import com.sayone.ebazzar.exception.RequestException;
import com.sayone.ebazzar.repository.ProductRepository;
import com.sayone.ebazzar.repository.UserRepository;
import com.sayone.ebazzar.repository.WishlistItemRepository;
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
    @Autowired
    WishlistItemRepository wishlistItemRepository;

    public WishlistEntity addProductToWishlist(Long userId,Long productId) {
        Optional<WishlistEntity> wishlistEntity = wishlistRepository.findByUserId(userId);
        if(wishlistEntity.isEmpty()){
            WishlistEntity wishlistEntity1 = new WishlistEntity();
            WishlistItemEntity wishlistItemEntity = new WishlistItemEntity();
            wishlistEntity1.setUserEntity(userRepository.findById(userId).get());
            ProductEntity productEntity = productRepository.findById(productId).get();

            wishlistItemEntity.setProductEntity(productEntity);
            wishlistEntity1.setWishlistItemEntityList(List.of(wishlistItemEntity));
            wishlistItemRepository.save(wishlistItemEntity);
            return wishlistRepository.save(wishlistEntity1);

        }
        else{

            WishlistEntity wishlistEntity1= wishlistEntity.get();
            ProductEntity productEntity = productRepository.findById(productId).get();

            for (int i=0;i<wishlistEntity1.getWishlistItemEntityList().size();i++){
                if(productEntity == wishlistEntity1.getWishlistItemEntityList().get(i).getProductEntity()){
                    throw new RequestException("product exist");
                }
            }

            WishlistItemEntity wishlistItemEntity = new WishlistItemEntity();
            wishlistItemEntity.setProductEntity(productEntity);
            wishlistEntity1.getWishlistItemEntityList().add(wishlistItemEntity);
            wishlistItemRepository.save(wishlistItemEntity);
            return wishlistRepository.save(wishlistEntity1);

        }

    }
}
