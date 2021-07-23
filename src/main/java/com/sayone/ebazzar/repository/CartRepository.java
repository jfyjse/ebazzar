package com.sayone.ebazzar.repository;
import com.sayone.ebazzar.entity.CartEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<CartEntity,Long> {

    @Query(value = "select * from cart c where c.user_id = ?1 and  c.cart_status=?2",nativeQuery = true)
    CartEntity getCartByUserId(Long userId,String status);

    Optional<CartEntity> findByCartId(Long cartId);

    @Query(value = "select * from cart c where c.user_id = ?1 and c.cart_status = ?2",nativeQuery = true)
    List<CartEntity> findByUserIdAndStatus(Long userId, String status);

    @Query(value = "select * from cart c where c.user_id = ?1 and c.cart_status = ?2",nativeQuery = true)
    Optional<CartEntity> findByUserId(Long userId,String status);

    @Query(value = "select * from cart c where c.user_id=?1 and c.cart_status=?2 ",nativeQuery = true)
    Optional<CartEntity> findCartById(Long userId,String status);
}





