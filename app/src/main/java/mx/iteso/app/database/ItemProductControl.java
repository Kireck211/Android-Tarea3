package mx.iteso.app.database;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import mx.iteso.app.beans.ItemProduct;

public class ItemProductControl {
    public void addItemProduct(ItemProduct itemProduct, DataBaseHandler dh) {
        SQLiteDatabase db = dh.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("idproduct", itemProduct.getCode());
        values.put("title", itemProduct.getTitle());
        values.put("image", itemProduct.getImage());
        values.put("idcategory", itemProduct.getCategory().getId());
        db.insert("Product", null, values);

        values = new ContentValues();
        values.put("idproduct", itemProduct.getCode());
        values.put("idstore", itemProduct.getStore().getId());
        db.insert("StoreProduct", null, values);

        try {
            db.close();
        } catch (Exception e) {

        }
        db = null;
        values = null;
    }
}
