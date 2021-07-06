package com.sayone.ebazzar.repository;

import com.sayone.ebazzar.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity,Long> {

    OrderEntity findByOrderId(Long id);
}
