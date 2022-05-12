package com.elcom.controller;

import com.elcom.message.ResponseMessage;
import com.elcom.model.Author;
import com.elcom.model.Book;
import com.elcom.model.Category;
import com.elcom.service.AuthorService;
import com.elcom.service.BookService;
import com.elcom.service.CategoryService;
import com.elcom.service.impl.AuthorServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class BookController extends BaseController{
    @Autowired
    private BookService bookService;

    @Autowired
    private AuthorService authorService;

    @Autowired
    private CategoryService categoryService;

    public ResponseMessage getAllBook(Map<String, String> headerParam, String requestPath, String urlParam) throws JsonProcessingException {
        ResponseMessage response = null;
        response = authenToken(headerParam);
        if(response != null && response.getStatus() == HttpStatus.OK.value()) {
            List<Book> books = bookService.getBooks();
            if (books != null){
                response = new ResponseMessage(HttpStatus.OK.value(), HttpStatus.OK.toString(), books);
            }
        }
        return response;
    }

    public ResponseMessage addBook( String requestPath, String urlParam, Map<String, String> headerParam, Map<String, Object> bodyParam) throws JsonProcessingException {
        ResponseMessage response = null;
        response = authenToken(headerParam);
        if(response != null && response.getStatus() == HttpStatus.OK.value()) {
            String name = (String) bodyParam.get("name");
            String time = (String) bodyParam.get("time");
            Integer authorId = Integer.parseInt((String) bodyParam.get("author"));
            Author author = authorService.getAuthorById(authorId);

            Integer categoryId = Integer.parseInt((String) bodyParam.get("author"));
            Category category = categoryService.getCategoryById(categoryId);
            Book newBook = new Book();
            newBook.setName(name);
            newBook.setTime(time);
            newBook.setAuthor(author);
            newBook.setCategory(category);
            bookService.saveBook(newBook);
            return new ResponseMessage(HttpStatus.OK.value(), HttpStatus.OK.toString(),"Thêm sách thành công");
        }
        return response;
    }

    public ResponseMessage editBook( String requestPath, String urlParam, Map<String, String> headerParam, Map<String, Object> bodyParam) throws JsonProcessingException {
        ResponseMessage response = null;
        response = authenToken(headerParam);
        if(response != null && response.getStatus() == HttpStatus.OK.value()) {
            Integer id = Integer.parseInt((String) bodyParam.get("id"));
            String name = (String) bodyParam.get("name");
            String time = (String) bodyParam.get("time");
            Integer authorId = Integer.parseInt((String) bodyParam.get("author"));
            Author author = authorService.getAuthorById(authorId);

            Integer categoryId = Integer.parseInt((String) bodyParam.get("category"));
            Category category = categoryService.getCategoryById(categoryId);

            Book newBook = new Book();
            newBook.setId(id);
            newBook.setName(name);
            newBook.setTime(time);
            newBook.setAuthor(author);
            newBook.setCategory(category);
            String msg = bookService.updateBook(newBook);
            return new ResponseMessage(HttpStatus.OK.value(), msg,null);
        }
        return response;
    }

    public ResponseMessage deleteBook(Map<String, String> headerParam,String pathParam) throws JsonProcessingException {
        ResponseMessage response = null;
        response = authenToken(headerParam);
        if(response != null && response.getStatus() == HttpStatus.OK.value()) {
            Integer id = Integer.parseInt(pathParam);
            String msg = bookService.deleteBook(id);
            return new ResponseMessage(HttpStatus.OK.value(), HttpStatus.OK.toString(),msg);
        }
        return response;
    }
}
