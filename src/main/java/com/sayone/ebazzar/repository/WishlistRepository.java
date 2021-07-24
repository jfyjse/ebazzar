package com.sayone.ebazzar.repository;

import com.sayone.ebazzar.entity.WishlistEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WishlistRepository extends JpaRepository<WishlistEntity,Long>{
    @Query(value = "select * from wishlist w where w.user_id = ?1",nativeQuery = true)
    Optional<WishlistEntity> findByUserId(Long userId);
}
