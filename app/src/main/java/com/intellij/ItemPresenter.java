package com.intellij;

import java.util.List;

/**
 * Created by Terril-Den on 5/28/17.
 */

public interface ItemPresenter {

    void addItem(ItemModel item);
    void deleteItem(ItemModel item);
    void editItem(ItemModel item, String oldText);
    List<ItemModel> getItems();
}
