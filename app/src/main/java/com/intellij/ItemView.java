package com.intellij;

/**
 * Created by Terril-Den on 5/28/17.
 */

public interface ItemView {

    void onItemAdded(ItemModel addedItem);
    void onItemDeleted(ItemModel deletedItem);
    void onItemEdited(ItemModel model, String oldText);
}
