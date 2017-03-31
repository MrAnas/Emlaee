package com.app.emlaee.modal;

import java.io.Serializable;

/**
 * Created by sahil on 1/11/2017.
 */

public class CategoriesModal implements Serializable {

    String category_id = "";
    String name = "";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }
}
