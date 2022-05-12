package com.elcom.model.dto;

public class BookMapper {
    private Integer id;

    private String name;

    private String author;

    private String category;

    public BookMapper(BookCustom bookCustom) {
        this.id = bookCustom.getId();
        this.name = bookCustom.getName();
        this.author = bookCustom.getAuthor();
        this.category = bookCustom.getCategory();
    }
}
