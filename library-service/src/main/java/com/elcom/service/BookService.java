package com.elcom.service;

import com.elcom.model.Book;
import org.springframework.stereotype.Service;

import java.util.List;


public interface BookService {
    List<Book> getBooks();

    Book saveBook(Book book);

    Book getBookByName(String name);

    String updateBook(Book book);

    String deleteBook(int id);

}
