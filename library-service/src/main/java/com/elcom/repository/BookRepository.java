package com.elcom.repository;

import com.elcom.model.Book;
import com.elcom.model.dto.BookCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
    Book findByName(String name);

    @Query(value = "SELECT b.id as id, b.name as name, a.name as author, c.name as category FROM library.book b, library.author a, library.category c\n" +
            "where b.author_id = a.id and b.cate_id = c.id;", nativeQuery = true)
    List<BookCustom> findReportBooks();

}
