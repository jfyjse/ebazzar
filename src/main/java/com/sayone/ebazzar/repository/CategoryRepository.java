package com.sayone.ebazzar.repository;


import com.sayone.ebazzar.entity.Category;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface CategoryRepository extends JpaRepository<Category,Long> {


}

