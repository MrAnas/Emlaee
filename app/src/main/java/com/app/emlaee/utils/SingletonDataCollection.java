package com.app.emlaee.utils;

import com.app.emlaee.modal.CategoriesModal;
import com.app.emlaee.modal.LevelItemModal;
import com.app.emlaee.modal.TestQustionsModal;

import java.util.ArrayList;

/**
 * Created by sahil on 1/12/2017.
 */

public class SingletonDataCollection {

    private static SingletonDataCollection singleton = new SingletonDataCollection();
    ArrayList<CategoriesModal> categoryArrayList = new ArrayList<CategoriesModal>();
    ArrayList<LevelItemModal> levelArrayList = new ArrayList<LevelItemModal>();
    ArrayList<TestQustionsModal> questionarr = new ArrayList<TestQustionsModal>();

    /* A private Constructor prevents any other
    * class from instantiating.
    */
    private SingletonDataCollection() {
    }

    /* Static 'instance' method */
    public static SingletonDataCollection getInstance() {

        return singleton;
    }



    public ArrayList<CategoriesModal> getCategoryArrayList() {
        return categoryArrayList;
    }
    public ArrayList<LevelItemModal> getLevelArrayList() {
        return levelArrayList;
    }

    public ArrayList<TestQustionsModal> getTestQustionsModalsArrayList() {
        return questionarr;
    }
}
