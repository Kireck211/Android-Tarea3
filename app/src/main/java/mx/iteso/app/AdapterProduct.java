package mx.iteso.app;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import mx.iteso.app.listeners.OnItemClickListener;
import mx.iteso.app.beans.ItemProduct;

import static mx.iteso.app.utils.Constants.CHANGE_PRODUCT_INFO;
import static mx.iteso.app.utils.Constants.ITEM_INTENT;

public class AdapterProduct extends RecyclerView.Adapter<AdapterProduct.ViewHolder> implements OnItemClickListener {
    public List<ItemProduct> products;

    public AdapterProduct (ArrayList<ItemProduct> products) {
        this.products =  products;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView mTitle;
        TextView mStore;
        TextView mLocation;
        TextView mPhone;
        ImageView mImage;
        RelativeLayout mLayout;
        OnItemClickListener listener;
        ItemProduct itemProduct;

        ViewHolder(View v) {
            super(v);
            mTitle = v.findViewById(R.id.item_product_title);
            mStore = v.findViewById(R.id.item_product_store);
            mLocation = v.findViewById(R.id.item_product_location);
            mPhone = v.findViewById(R.id.item_product_phone);
            mImage = v.findViewById(R.id.item_product_image);
            mLayout = v.findViewById(R.id.item_product_layout);
            mPhone.setOnClickListener(this);
            mLayout.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.item_product_phone:
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+getString(mPhone)));
                    v.getContext().startActivity(intent);
                    break;
                case R.id.item_product_layout:
                    listener.onItemClick(itemProduct, v.getContext());
                    break;
            }
        }

        private String getString(TextView textView) {
            return textView.getText().toString();
        }

        void bind(ItemProduct itemProduct, OnItemClickListener listener) {
            this.itemProduct = itemProduct;
            this.listener = listener;
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_product, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ItemProduct item = products.get(position);
        holder.mTitle.setText(item.getTitle());
        holder.mStore.setText(item.getStore());
        holder.mLocation.setText(item.getLocation());
        holder.mPhone.setText(item.getPhone());
        holder.mImage.setImageResource(item.getImage());
        holder.bind(item, this);
    }

    @Override
    public void onItemClick(ItemProduct itemProduct, Context context) {
        Intent intent = new Intent(context, ActivityProduct.class);
        intent.putExtra(ITEM_INTENT, itemProduct);
        ((ActivityMain) context).startActivityForResult(intent, CHANGE_PRODUCT_INFO);
    }

    @Override
    public int getItemCount() {
        return products.size();
    }
}
