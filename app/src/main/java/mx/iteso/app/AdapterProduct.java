package mx.iteso.app;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import mx.iteso.app.beans.ItemProduct;

public class AdapterProduct extends RecyclerView.Adapter<AdapterProduct.ViewHolder>{
    private final ArrayList<ItemProduct> products;

    public AdapterProduct (ArrayList<ItemProduct> products) {
        this.products =  products;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView mTitle;
        public TextView mStore;
        public TextView mLocation;
        public TextView mPhone;
        public ImageView mImage;
        public RelativeLayout mLayout;

        public ViewHolder(View v) {
            super(v);
            mTitle = (TextView) v.findViewById(R.id.item_product_title);
            mStore = (TextView) v.findViewById(R.id.item_product_store);
            mLocation = (TextView) v.findViewById(R.id.item_product_location);
            mPhone = (TextView) v.findViewById(R.id.item_product_phone);
            mImage = (ImageView) v.findViewById(R.id.item_product_image);
            mLayout = (RelativeLayout) v.findViewById(R.id.item_product_layout);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.item_product_phone:
                    String phone = "tel:" + mPhone.getText().toString();
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse(phone));
                    v.getContext().startActivity(intent);
                    break;
                case R.id.item_product_layout:
                    break;
            }
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
        holder.mTitle.setText(products.get(position).getTitle());
        holder.mStore.setText(products.get(position).getStore());
        holder.mLocation.setText(products.get(position).getLocation());
        holder.mPhone.setText(products.get(position).getPhone());
        holder.mImage.setImageResource(products.get(position).getImage());
    }

    @Override
    public int getItemCount() {
        return products.size();
    }
}
