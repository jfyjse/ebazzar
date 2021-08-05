package com.sayone.ebazzar;

import com.sayone.ebazzar.entity.Category;
import com.sayone.ebazzar.entity.SubCategoryEntity;
import com.sayone.ebazzar.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class Runner implements CommandLineRunner {

    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Add Categories");

        if (categoryRepository.findAll().isEmpty()) {

            //ADD CATEGORY AND SUB-CATEGORY  OF ELECTRONICS
            Category electronics = new Category("Electronics");
            String[] electronicsCategories = {"Laptop", "Mobile", "Camera"};
            for (String cat : electronicsCategories) {
                SubCategoryEntity subCategory = new SubCategoryEntity(cat);
                electronics.getSubCategories().add(subCategory);
            }
            categoryRepository.save(electronics);

            //ADD CATEGORY AND SUB-CATEGORY  OF FASHION
            Category fashion = new Category("Fashion");
            String[] fashionCategories = {"Men's Wear", "Women's Wear", "Kid's Wear"};
            for (String cat : fashionCategories) {
                SubCategoryEntity subCategory = new SubCategoryEntity(cat);
                fashion.getSubCategories().add(subCategory);
            }
            categoryRepository.save(fashion);

            //ADD CATEGORY AND SUB-CATEGORY  OF BOOKS
            Category books = new Category("Books");
            String[] booksCategories = {"Literature and Fiction", "Academic and Non-Fiction", "Children's Book"};
            for (String cat : booksCategories) {
                SubCategoryEntity subCategory = new SubCategoryEntity(cat);
                books.getSubCategories().add(subCategory);
            }
            categoryRepository.save(books);

            //ADD CATEGORY AND SUB-CATEGORY  OF GROCERY
            Category grocery = new Category("Grocery");
            String[] groceryCategories = {"Fruits and Vegetables", "Eggs,Fish and Poultry", "Bakery,Snacks and Beverages"};
            for (String cat : groceryCategories) {
                SubCategoryEntity subCategory = new SubCategoryEntity(cat);
                grocery.getSubCategories().add(subCategory);
            }
            categoryRepository.save(grocery);

            //ADD CATEGORY AND SUB-CATEGORY  OF Beauty
            Category beauty = new Category("Beauty");
            String[] beautyCategories = {"Men's Grooming", " Women's MakeUps"};
            for (String cat : beautyCategories) {
                SubCategoryEntity subCategory = new SubCategoryEntity(cat);
                beauty.getSubCategories().add(subCategory);
            }
            categoryRepository.save(beauty);

            Category others = new Category("Others");
            SubCategoryEntity othersubcategories = new SubCategoryEntity("Others");
            others.setSubCategories(Arrays.asList(othersubcategories));
            categoryRepository.save(others);

        }
    }
}

