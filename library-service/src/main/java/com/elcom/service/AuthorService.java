package com.elcom.service;

import com.elcom.model.Author;

import java.util.List;

public interface AuthorService {
    List<Author> getAuthors();

    Author getAuthorById(int id);

    Author getAuthorByName(String name);

    Author saveAuthor(Author author);

    String updateAuthor(Author author);

    String deleteAuthorById(int id);
}
