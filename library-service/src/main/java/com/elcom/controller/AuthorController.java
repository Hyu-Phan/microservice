package com.elcom.controller;

import com.elcom.message.ResponseMessage;
import com.elcom.model.Author;
import com.elcom.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class AuthorController extends BaseController{
    @Autowired
    private AuthorService authorService;

    public ResponseMessage getAuthors(Map<String, String> headerParam) {
        ResponseMessage checkAuthen = authenToken(headerParam);
        if(checkAuthen != null && checkAuthen.getStatus() == HttpStatus.OK.value()) {
            List<Author> authors = authorService.getAuthors();
            ResponseMessage response = new ResponseMessage(HttpStatus.OK.value(), HttpStatus.OK.toString(), authors);
            return response;
        }
        return checkAuthen;
    }

    public ResponseMessage getAuthorById(Map<String, String> headerParam, String pathParam) {
        ResponseMessage checkAuthen = authenToken(headerParam);
        if(checkAuthen != null && checkAuthen.getStatus() == HttpStatus.OK.value()) {
            Integer id = Integer.parseInt((String) pathParam);
            Author author = authorService.getAuthorById(id);
            ResponseMessage response = null;
            if(author != null) {
                response = new ResponseMessage(HttpStatus.OK.value(), HttpStatus.OK.toString(), author);
            }
            else {
                response = new ResponseMessage(HttpStatus.NOT_FOUND.value(), "Không tìm thấy tác giả", null);
            }
            return response;
        }
        return checkAuthen;
    }

    public ResponseMessage addAuthor(Map<String, String> headerParam, Map<String, Object> bodyParam) {
        ResponseMessage checkAuthen = authenToken(headerParam);
        if(checkAuthen != null && checkAuthen.getStatus() == HttpStatus.OK.value()) {
            Author author = new Author();
            String name = (String) bodyParam.get("name");
            String dob = (String) bodyParam.get("dob");
            String biography = (String) bodyParam.get("biography");
            author.setName(name);
            author.setDob(dob);
            author.setBiography(biography);

            Author newAuthor = authorService.saveAuthor(author);
            ResponseMessage response = new ResponseMessage(HttpStatus.OK.value(), "Thêm tác giả thành công", newAuthor);
            return response;
        }
        return checkAuthen;
    }

    public ResponseMessage editAuthor(Map<String, String> headerParam, Map<String, Object> bodyParam) {
        ResponseMessage checkAuthen = authenToken(headerParam);
        if(checkAuthen != null && checkAuthen.getStatus() == HttpStatus.OK.value()) {
            Author author = new Author();
            Integer id = Integer.parseInt((String) bodyParam.get("id"));
            String name = (String) bodyParam.get("name");
            String dob = (String) bodyParam.get("dob");
            String biography = (String) bodyParam.get("biography");
            author.setId(id);
            author.setName(name);
            author.setDob(dob);
            author.setBiography(biography);

            String msg = authorService.updateAuthor(author);
            ResponseMessage response = new ResponseMessage(HttpStatus.OK.value(), msg, null);
            return response;
        }
        return checkAuthen;
    }

    public ResponseMessage deleteAuthorById(Map<String, String> headerParam, String pathParam) {
        ResponseMessage checkAuthen = authenToken(headerParam);
        if(checkAuthen != null && checkAuthen.getStatus() == HttpStatus.OK.value()) {
            Integer id = Integer.parseInt(pathParam);
            String msg = authorService.deleteAuthorById(id);
            ResponseMessage response = new ResponseMessage(HttpStatus.OK.value(), msg, null);
            return response;
        }
        return checkAuthen;
    }
}
