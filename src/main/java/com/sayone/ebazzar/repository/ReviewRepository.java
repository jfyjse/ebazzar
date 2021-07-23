package com.sayone.ebazzar.repository;

import com.sayone.ebazzar.entity.ReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<ReviewEntity, Long> {

    @Transactional
    @Modifying      // to mark delete or update query
    @Query(value = "DELETE FROM reviews r WHERE r.product_id = ?1 and r.user_id = ?2", nativeQuery = true)
    Integer deleteReview(Long productId, Long userId);

    @Query(value = "select * from reviews r where r.product_id = ?1 and r.user_id = ?2", nativeQuery = true)
    ReviewEntity findByProductAndUser(Long productId, long userId);

    @Query(value = "select * from reviews r where r.user_id = ?1", nativeQuery = true)
    List<ReviewEntity> findByUserId(long userId);
}
