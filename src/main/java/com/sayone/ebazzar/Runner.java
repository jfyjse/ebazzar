package com.sayone.ebazzar;

import com.sayone.ebazzar.entity.Category;
import com.sayone.ebazzar.entity.SubCategory;
import com.sayone.ebazzar.repository.CategoryRepository;
import com.sayone.ebazzar.service.SubCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Runner implements CommandLineRunner {

    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Add Categories");

//ADD CATEGORY AND SUB-CATEGORY  OF ELECTRONICS
        Category electronics = new Category("Electronics");
        String[] electronicsCategories = {"Laptop", "Mobile", "Camera"};
        for (String cat : electronicsCategories) {
            SubCategory subCategory = new SubCategory(cat);
            electronics.getSubCategories().add(subCategory);
        }
        categoryRepository.save(electronics);

//ADD CATEGORY AND SUB-CATEGORY  OF FASHION
        Category fashion = new Category("Fashion");
        String[] fashionCategories = {"Men's Wear", "Women's Wear", "Kid's Wear"};
        for (String cat : fashionCategories) {
            SubCategory subCategory = new SubCategory(cat);
            fashion.getSubCategories().add(subCategory);
        }
        categoryRepository.save(fashion);

//ADD CATEGORY AND SUB-CATEGORY  OF BOOKS
        Category books = new Category("Books");
        String [] booksCategories = {"Literature and Fiction","Academic and Non-Fiction","Children's Book"};
        for (String cat : booksCategories) {
            SubCategory subCategory = new SubCategory(cat);
            books.getSubCategories().add(subCategory);
        }
        categoryRepository.save(books);

//ADD CATEGORY AND SUB-CATEGORY  OF GROCERY
        Category grocery = new Category("Grocery");
        String [] groceryCategories = {"Fruits and Vegetables","Eggs,Fish and Poultry","Bakery,Snacks and Beverages"};
        for (String cat : groceryCategories) {
            SubCategory subCategory = new SubCategory(cat);
            grocery.getSubCategories().add(subCategory);
        }
        categoryRepository.save(grocery);

//ADD CATEGORY AND SUB-CATEGORY  OF Beauty
        Category beauty = new Category("Beauty");
        String [] beautyCategories = {"Men's Grooming"," Women's MakeUps"};
        for (String cat : beautyCategories) {
            SubCategory subCategory = new SubCategory(cat);
            beauty.getSubCategories().add(subCategory);
        }
        categoryRepository.save(beauty);

    }
}

