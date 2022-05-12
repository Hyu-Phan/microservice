package com.elcom.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Component
@Entity
@Table(name = "author")
public class Author implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "dob")
    private String dob;

    @Column(name = "biography")
    private String biography;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "author")
    private List<Book> books = new ArrayList<>();
}
