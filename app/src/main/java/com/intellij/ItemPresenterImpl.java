package com.intellij;

import android.content.Context;

import java.util.List;

/**
 * Created by Terril-Den on 5/28/17.
 */

public class ItemPresenterImpl implements ItemPresenter {
    private ItemView mItemView;
    private Context mContext;
    private  SqliteHelper mSqliteHelper;
    public ItemPresenterImpl(Context context, ItemView itemView) {
        mContext = context;
        mItemView = itemView;
        mSqliteHelper = new SqliteHelper(context);
    }

    @Override
    public void addItem(ItemModel item) {
        mSqliteHelper.addItem(item);
        mItemView.onItemAdded(item);
    }

    @Override
    public void deleteItem(ItemModel item) {
        mSqliteHelper.deleteItem(item);
        mItemView.onItemDeleted(item);
    }

    @Override
    public void editItem(ItemModel item, String oldText) {
        mSqliteHelper.updateItem(item, oldText);
        mItemView.onItemEdited(item, oldText);
    }

    @Override
    public List<ItemModel> getItems() {
        return mSqliteHelper.getAllItems();
    }
}
