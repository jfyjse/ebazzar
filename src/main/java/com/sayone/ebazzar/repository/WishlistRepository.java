package com.sayone.ebazzar.repository;

import com.sayone.ebazzar.entity.WishlistEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WishlistRepository extends JpaRepository<WishlistEntity,Long>{
}
