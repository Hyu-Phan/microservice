package com.elcom.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "book")
public class Book {
    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "author")
    private String author;

    @Column(name = "category")
    private String category;

    public Book(){

    }
    public Book(Integer id, String name, String author, String category) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.category = category;
    }


}
