package com.intellij;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Terril-Den on 5/28/17.
 */

public class SqliteHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "intellj";
    private static final  String TABLE_NAME = "intellj_items";
    private static final int DATABASE_VERSION = 1;

    private static final String COL_ITEM_ID = "id";
    private static final String COL_ITEM_ADD = "item_add";

    private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
            + COL_ITEM_ID + " INTEGER PRIMARY KEY," + COL_ITEM_ADD + " TEXT"
            + ")";

    public SqliteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

        // Create tables again
        onCreate(db);
    }

    // Adding Items
    public void addItem(ItemModel item) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COL_ITEM_ADD, item.getItem()); // Add Item

        // Inserting Row
        db.insert(TABLE_NAME, null, values);
        db.close(); // Closing database connection
    }

    // Updating Items
    public void  updateItem(ItemModel item, String oldText) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COL_ITEM_ADD, item.getItem());

        // updating row
        db.update(TABLE_NAME, values, COL_ITEM_ADD + " LIKE ?",
                new String[] { oldText });
        db.close();
    }

    // Deleting Items
    public void deleteItem(ItemModel item) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, COL_ITEM_ID + " = ?",
                new String[] { String.valueOf(item.getID()) });
        db.close();
    }

    // Getting All Items
    public List<ItemModel> getAllItems() {
        List<ItemModel> itemList = new ArrayList<ItemModel>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                // Adding items to list
                ItemModel itemModel = new ItemModel();
                itemModel.setID(Integer.parseInt(cursor.getString(0)));
                itemModel.setItem(cursor.getString(1));
                itemList.add(itemModel);
            } while (cursor.moveToNext());
        }

        // return item list
        return itemList;
    }

}
