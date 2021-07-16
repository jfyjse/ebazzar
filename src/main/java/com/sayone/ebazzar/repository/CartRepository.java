package com.sayone.ebazzar.repository;

import com.sayone.ebazzar.entity.CartEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<CartEntity,Long> {
    @Query(value = "select * from cart c where c.user_id = ?1 and c.cart_status = ?2",nativeQuery = true)
    CartEntity findByUserId(Long userId, String status);
}
