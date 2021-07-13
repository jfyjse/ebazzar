package com.sayone.ebazzar.repository;

import com.sayone.ebazzar.entity.Category;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Component
public interface CategoryRepository extends JpaRepository<Category,Long> {

}

