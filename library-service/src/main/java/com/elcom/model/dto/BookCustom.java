package com.elcom.model.dto;

import java.io.Serializable;

public interface BookCustom extends Serializable {
    Integer getId();

    String getName();

    String getAuthor();

    String getCategory();
}
