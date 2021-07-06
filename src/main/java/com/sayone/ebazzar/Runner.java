package com.sayone.ebazzar;

import com.sayone.ebazzar.entity.Category;
import com.sayone.ebazzar.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

//@Component
public class Runner implements CommandLineRunner {

    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Add Categories");

        String[] catogories = {"Electronics", "Fashion", "Book", "Grocery", "Beauty"};

        for (String cat : catogories) {
            Category category = new Category(cat);
            categoryRepository.save(category);
        }
    }


}
