package com.intellij;

/**
 * Created by Terril-Den on 5/29/17.
 */

public class ItemModel {

    private int _id;
    private String _item;

    // Empty constructor
    public ItemModel() {

    }

    // constructor
    public ItemModel(int id, String name) {
        this._id = id;
        this._item = name;
    }

    // constructor
    public ItemModel(String item) {
        this._item = item;
    }

    // getting ID
    public int getID() {
        return this._id;
    }

    // setting id
    public void setID(int id) {
        this._id = id;
    }

    // getting item
    public String getItem() {
        return this._item;
    }

    // setting item
    public void setItem(String item) {
        this._item = item;
    }
}
