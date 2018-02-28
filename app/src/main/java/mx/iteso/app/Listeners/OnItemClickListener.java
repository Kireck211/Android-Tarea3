package mx.iteso.app.Listeners;

import android.content.Context;

import mx.iteso.app.beans.ItemProduct;

public interface OnItemClickListener {
    void onItemClick(ItemProduct itemProduct, Context context);
}
