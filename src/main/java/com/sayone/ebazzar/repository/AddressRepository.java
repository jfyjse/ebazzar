package com.sayone.ebazzar.repository;

import com.sayone.ebazzar.entity.AddressEntity;
import com.sayone.ebazzar.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<AddressEntity,Long> {

    Optional<AddressEntity> findByAddressId(Long shippingAddress);


}
