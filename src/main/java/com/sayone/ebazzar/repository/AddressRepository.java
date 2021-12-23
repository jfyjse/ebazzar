package com.sayone.ebazzar.repository;

import com.sayone.ebazzar.entity.AddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<AddressEntity,Long> {

    Optional<AddressEntity> findByAddressId(Long shippingAddress);
}
