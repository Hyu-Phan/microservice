package com.elcom.service.impl;

import com.elcom.model.Author;
import com.elcom.repository.AuthorRepository;
import com.elcom.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class AuthorServiceImpl implements AuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    @Override
    public List<Author> getAuthors() {
        return authorRepository.findAll();
    }

    @Override
    public Author getAuthorById(int id) {
        return authorRepository.findById(id).orElse(null);
    }

    @Override
    public Author getAuthorByName(String name) {
        return authorRepository.findByName(name).orElse(null);
    }

    @Override
    public Author saveAuthor(Author author) {
        return authorRepository.save(author);
    }

    @Override
    public String updateAuthor(Author author) {
        String msg = "";
        try {
            authorRepository.save(author);
            msg = "Sửa thông tin tác giả thành công";
        } catch (Exception e){
            msg = "Không sửa được thông tin tác giả";
        }
        return msg;
    }

    @Override
    public String deleteAuthorById(int id) {
        String msg = "";
        try {
            authorRepository.deleteById(id);
            msg = "Xóa tác giả thành công";
        } catch (Exception e){
            msg = "Không xóa được tác giả";
        }
        return msg;
    }
}
