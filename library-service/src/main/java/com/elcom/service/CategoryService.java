package com.elcom.service;

import com.elcom.model.Category;

import java.util.List;

public interface CategoryService {
    List<Category> getCategories();

    Category getCategoryById(int id);

    Category getCateByName(String name);

    Category saveCategory(Category category);

    String updateCategory(Category category);

    String deleteCategoryById(int id);

}
