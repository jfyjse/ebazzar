package com.sayone.ebazzar.repository;

import com.sayone.ebazzar.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

import java.util.Optional;

@Repository
public interface ProductRepository extends PagingAndSortingRepository<ProductEntity,Long> {

    ProductEntity findByProductId(Long Id);

    Optional<ProductEntity> findByProductName(String name);


}
