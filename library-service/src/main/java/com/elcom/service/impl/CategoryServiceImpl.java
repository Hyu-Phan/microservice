package com.elcom.service.impl;

import com.elcom.model.Category;
import com.elcom.repository.CategoryRepository;
import com.elcom.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<Category> getCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category getCategoryById(int id) {
        return categoryRepository.findById(id).orElse(null);
    }

    @Override
    public Category getCateByName(String name) {
        return categoryRepository.findByName(name);
    }

    @Override
    public Category saveCategory(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public String updateCategory(Category category) {
        String msg = "";
        try {
            categoryRepository.save(category);
            msg = "Sửa thông tin loại sách thành công";
        } catch (Exception e){
            msg = "Không sửa được thông tin loại sách";
        }
        return msg;
    }

    @Override
    public String deleteCategoryById(int id) {
        String msg = "";
        try {
            categoryRepository.deleteById(id);
            msg = "Xóa loại sách thành công";
        } catch (Exception e){
            msg = "Không xóa được loại sách";
        }
        return msg;
    }
}
