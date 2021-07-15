package com.sayone.ebazzar.repository;

import com.sayone.ebazzar.entity.ProductEntity;
import com.sayone.ebazzar.entity.ReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<ReviewEntity,Long> {

    List<ReviewEntity> findByRating(Integer rating);

    List<ReviewEntity> findByProductEntity(ProductEntity productEntity);

    @Transactional
    @Modifying      // to mark delete or update query
    @Query(value = "DELETE FROM reviews r WHERE r.product_id = ?1 and r.user_id = ?2",nativeQuery = true)
    void deleteReview(Long productId, Long userId);

}
