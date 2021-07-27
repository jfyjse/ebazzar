package com.sayone.ebazzar.repository;

import com.sayone.ebazzar.entity.WishlistEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface WishlistRepository extends JpaRepository<WishlistEntity,Long>{
    @Query(value = "select * from wishlist w where w.user_id = ?1",nativeQuery = true)
    Optional<WishlistEntity> findByUserId(Long userId);

    @Query(value = "select * from wishlist w where w.user_id = ?1",nativeQuery = true)
    WishlistEntity getByUserId(long userId);

    @Transactional
    @Modifying
    @Query(value = "delete from wishlist_products w where w.wishlist_id=?1 and w.product_id=?2",nativeQuery = true)
    void deleteProduct(Long wishlistId, Long productId);
}
