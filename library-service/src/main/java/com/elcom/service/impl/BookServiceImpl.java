package com.elcom.service.impl;

import com.elcom.model.Book;
import com.elcom.repository.BookRepository;
import com.elcom.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;
    @Override
    public List<Book> getBooks() {
        return bookRepository.findAll();
    }

    @Override
    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public Book getBookByName(String name) {
        return bookRepository.findByName(name);
    }

    @Override
    public String updateBook(Book book) {
        String msg = "";
        try {
            bookRepository.save(book);
            msg = "Sửa thông tin sách thành công";
        } catch (Exception e){
            msg = "Không sửa được thông tin sách";
        }
        return msg;
    }

    @Override
    public String deleteBook(int id) {
        String msg = "";
        try{
            bookRepository.deleteById(id);
            msg = "Xóa sách thành công";
        } catch (Exception e){
            e.printStackTrace();
            msg = "Không tồn tại sách";
        }
        return msg;
    }
}
