package com.sayone.ebazzar.repository;

import com.sayone.ebazzar.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity,Long>{
}
