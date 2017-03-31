package com.app.emlaee.modal;

import java.io.Serializable;

/**
 * Created by sahil on 1/10/2017.
 */

public class LevelItemModal implements Serializable {
    String id = "";
    String name = "";
    String description = "";
    int image;
    int background;

    public int getBackground() {
        return background;
    }

    public void setBackground(int background) {
        this.background = background;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
