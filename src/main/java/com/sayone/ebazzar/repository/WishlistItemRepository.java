package com.sayone.ebazzar.repository;

import com.sayone.ebazzar.entity.WishlistItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface WishlistItemRepository extends JpaRepository<WishlistItemEntity,Long> {

    @Transactional
    @Modifying
    @Query(value = "delete from wish_item wi where wi.wish_id =?1 and wi.product_id=?2",nativeQuery = true)
    void deleteProductFromWishlist(Long wishlistId,Long productId);
}
