package com.intellij;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements ItemView, View.OnClickListener {

    private String DEBUG_TAG = MainActivity.class.getSimpleName();
    private ItemPresenterImpl mItemPresenter;
    private List<ItemModel> mAdapterData;
    private ItemAdapter mAdapter;
    private EditText edtAddItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edtAddItems = (EditText) findViewById(R.id.edt_addItems);
        findViewById(R.id.btn_add_item).setOnClickListener(this);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recylerview_item);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        mItemPresenter = new ItemPresenterImpl(this, this);
        mAdapterData = mItemPresenter.getItems();
        mAdapter = new ItemAdapter();
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onItemAdded(ItemModel addItem) {
        mAdapterData.add(addItem);
        mAdapter.notifyItemInserted(mAdapterData.size() - 1);
    }

    @Override
    public void onItemDeleted(ItemModel deleteItemPosition) {
        mAdapterData.remove(deleteItemPosition);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemEdited(ItemModel itemModel, String oldText) {
        mAdapterData.set(itemModel.getID(), itemModel);
        mAdapter.notifyItemChanged(itemModel.getID());
    }

    @Override
    public void onClick(View v) {
        String addedItemText = edtAddItems.getText().toString().trim();
        ItemModel itemModel = new ItemModel(addedItemText);
        if (!addedItemText.isEmpty()) {
            mItemPresenter.addItem(itemModel);
        }
    }

    private class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getBaseContext()).inflate(R.layout.view_items, null);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.textItems.setText(mAdapterData.get(position).getItem());

        }


        @Override
        public int getItemCount() {
            return mAdapterData.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            TextView textItems;
            ImageButton imbtnDelete, imbtnEdit;

            public ViewHolder(View itemView) {
                super(itemView);
                textItems = (TextView) itemView.findViewById(R.id.tv_items);
                imbtnEdit = (ImageButton) itemView.findViewById(R.id.imbtn_edit);
                imbtnDelete = (ImageButton) itemView.findViewById(R.id.imbtn_delete);

                imbtnDelete.setOnClickListener(this);
                imbtnEdit.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {
                int position = getAdapterPosition();
                Log.d(DEBUG_TAG, "Position: " + position);
                switch (v.getId()) {
                    case R.id.imbtn_edit:
                        showEditDialog(mAdapterData.get(position), position);
                        break;
                    case R.id.imbtn_delete:
                        mItemPresenter.deleteItem(mAdapterData.get(position));
                        break;
                }

            }
        }
    }

    private void showEditDialog(final ItemModel itemText, final int postion) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                MainActivity.this);
        // set title
        final EditText input = new EditText(this);
        input.setText(itemText.getItem());
        alertDialogBuilder.setView(input);
        alertDialogBuilder.setTitle(R.string.edit_item);
        alertDialogBuilder.setCancelable(true);
        // set dialog message
        alertDialogBuilder
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        String updatedItemText = input.getText().toString().trim();
                        if (!updatedItemText.isEmpty()) {
                         ItemModel itemModel = new ItemModel(postion,updatedItemText);
                            mItemPresenter.editItem(itemModel, itemText.getItem());
                        }
                    }
                });
        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }
}
