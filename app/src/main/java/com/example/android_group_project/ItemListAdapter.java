package com.example.android_group_project;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;


//https://www.youtube.com/watch?v=a4o9zFfyIM4

public class ItemListAdapter extends RecyclerView.Adapter<ItemListAdapter.ItemViewHold> {


    //this context we will use to inflate the layout
    private Context ctxItem;

    //we are storing all the products in a list
    private List<ItemList> itemList;

    //getting the context and product list with constructor
    public ItemListAdapter(Context ctxItem, List<ItemList> itemList) {
        this.ctxItem = ctxItem;
        this.itemList = itemList;
    }

    @Override
    public ItemViewHold onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflating and returning our view holder
        LayoutInflater inflate = LayoutInflater.from(ctxItem);
        View view = inflate.inflate(R.layout.item_list, null);

        return new ItemViewHold(view);
    }

    @Override
    public void onBindViewHolder(ItemViewHold itemHolder, int position) {
        //getting the product of the specified position
        final ItemList item = itemList.get(position);

        //binding the data with the viewholder views
        itemHolder.nameTextView.setText(item.getItemName());
        itemHolder.descTextView.setText(item.getItemDesc());
        itemHolder.ratingTextView.setText(String.valueOf(item.getItemStock()));
        itemHolder.priceTextView.setText(String.valueOf(item.getItemPrice()));
//        itemHolder.barcodeTextView.setText(String.valueOf(item.getItemBarcode()));

        itemHolder.itemImageView.setImageDrawable(ctxItem.getResources().getDrawable(item.getItemImage()));
        itemHolder.buttonToDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ctxItem,view_product.class);
                intent.putExtra("id",item.getId());
                ctxItem.startActivity(intent);
            }
        });

    }


    @Override
    public int getItemCount() {
        return itemList.size();
    }


    class ItemViewHold extends RecyclerView.ViewHolder {

        TextView nameTextView, descTextView, ratingTextView, priceTextView, barcodeTextView;
        ImageView itemImageView;
        Button buttonToDetail;

        public ItemViewHold(View itemView) {
            super(itemView);

            nameTextView = itemView.findViewById(R.id.itemNameTextView);
            descTextView = itemView.findViewById(R.id.itemDescTextView);
            ratingTextView = itemView.findViewById(R.id.itemRatingTextView);
            priceTextView = itemView.findViewById(R.id.itemPriceTextView);
//            barcodeTextView = itemView.findViewById(R.id.);
            itemImageView = itemView.findViewById(R.id.imageView);
            buttonToDetail=itemView.findViewById(R.id.viewItem);
        }
    }
}