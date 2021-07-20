
package com.sayone.ebazzar.repository;

import com.sayone.ebazzar.entity.CartEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface CartRepository extends JpaRepository<CartEntity,Long> {
    @Query(value = "select * from cart c where c.user_id = ?1 and c.cart_status = ?2",nativeQuery = true)
    CartEntity findByUserId(Long userId, String status);
//    @Query(value = "select * from cart c where c.product_id = ?1",nativeQuery = true)
//    CartEntity findProductExist(Long productId);


    @Query(value = "select cart_id from cart c where c.user_id = ?1",nativeQuery = true)
    CartEntity getCartId(Long userId);

}