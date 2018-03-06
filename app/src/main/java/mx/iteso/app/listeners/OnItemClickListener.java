package mx.iteso.app.listeners;

import android.content.Context;

import mx.iteso.app.beans.ItemProduct;

public interface OnItemClickListener {
    void onItemClick(ItemProduct itemProduct, Context context);
}
