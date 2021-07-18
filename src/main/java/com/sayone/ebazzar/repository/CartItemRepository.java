package com.sayone.ebazzar.repository;

import com.sayone.ebazzar.entity.CartEntity;
import com.sayone.ebazzar.entity.CartItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepository extends JpaRepository<CartItemEntity, Long> {

    @Query(value = "select * from cart c where c.product_id = ?1",nativeQuery = true)
    CartEntity findProductExist(Long productId);
}
