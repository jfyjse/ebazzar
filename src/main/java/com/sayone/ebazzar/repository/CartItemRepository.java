package com.sayone.ebazzar.repository;

import com.sayone.ebazzar.entity.CartEntity;
import com.sayone.ebazzar.entity.CartItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItemEntity, Long> {

    @Query(value = "select * from cart c where c.product_id = ?1",nativeQuery = true)
    CartEntity findProductExist(Long productId);
    @Transactional
    @Modifying
    @Query(value = "delete from cartitem ci where ci.cart_id = ?1", nativeQuery = true)
    void deleteAllCartItem(CartEntity cartId);

    //delete product
    @Transactional
    @Modifying
    @Query(value = "delete from cartitem ci where ci.product_id =?1", nativeQuery = true)
    void deleteProduct(Long productId);
    //delete all products
    @Transactional
    @Modifying
    @Query(value = "delete from cartitem ci where ci.cart_id = ?1",nativeQuery = true)
    void deleteAllProducts(Long cartId);
}