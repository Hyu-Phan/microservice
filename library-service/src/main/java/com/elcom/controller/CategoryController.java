package com.elcom.controller;

import com.elcom.message.ResponseMessage;
import com.elcom.model.Category;
import com.elcom.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class CategoryController extends BaseController{
    @Autowired
    private CategoryService categoryService;

    public ResponseMessage getCategories(Map<String, String> headerParam) {
        ResponseMessage checkAuthen = authenToken(headerParam);
        if(checkAuthen != null && checkAuthen.getStatus() == HttpStatus.OK.value()) {
            List<Category> authors = categoryService.getCategories();
            ResponseMessage response = new ResponseMessage(HttpStatus.OK.value(), HttpStatus.OK.toString(), authors);
            return response;
        }
        return checkAuthen;
    }

    public ResponseMessage addCategory(Map<String, String> headerParam, Map<String, Object> bodyParam) {
        ResponseMessage checkAuthen = authenToken(headerParam);
        if(checkAuthen != null && checkAuthen.getStatus() == HttpStatus.OK.value()) {
            Category category = new Category();
            String name = (String) bodyParam.get("name");
            String description = (String) bodyParam.get("description");
            category.setName(name);
            category.setDescription(description);

            Category newCategory = categoryService.saveCategory(category);
            ResponseMessage response = new ResponseMessage(HttpStatus.OK.value(), "Thêm tác giả thành công", newCategory);
            return response;
        }
        return checkAuthen;
    }

    public ResponseMessage editCategory(Map<String, String> headerParam, Map<String, Object> bodyParam) {
        ResponseMessage checkAuthen = authenToken(headerParam);
        if(checkAuthen != null && checkAuthen.getStatus() == HttpStatus.OK.value()) {
            Category category = new Category();
            Integer id = Integer.parseInt((String) bodyParam.get("id"));
            String name = (String) bodyParam.get("name");
            String description = (String) bodyParam.get("description");
            category.setId(id);
            category.setName(name);
            category.setDescription(description);

            String msg = categoryService.updateCategory(category);
            ResponseMessage response = new ResponseMessage(HttpStatus.OK.value(), msg, null);
            return response;
        }
        return checkAuthen;
    }

    public ResponseMessage deleteAuthorById(Map<String, String> headerParam, String pathParam) {
        ResponseMessage checkAuthen = authenToken(headerParam);
        if(checkAuthen != null && checkAuthen.getStatus() == HttpStatus.OK.value()) {
            Integer id = Integer.parseInt(pathParam);
            String msg = categoryService.deleteCategoryById(id);
            ResponseMessage response = new ResponseMessage(HttpStatus.OK.value(), msg, null);
            return response;
        }
        return checkAuthen;
    }
}
