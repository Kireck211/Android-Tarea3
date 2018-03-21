package mx.iteso.app.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import mx.iteso.app.beans.Category;
import mx.iteso.app.beans.City;
import mx.iteso.app.beans.ItemProduct;
import mx.iteso.app.beans.Store;

import static mx.iteso.app.utils.Constants.SQL_ERROR_INSERT;

public class ItemProductControl {
    private static final String TAG = "Debug " + ItemProductControl.class.getSimpleName();

    public static boolean addItemProduct(ItemProduct itemProduct, DataBaseHandler dh) {
        SQLiteDatabase db = dh.getWritableDatabase();
        ContentValues values = new ContentValues();
        // Begin transaction
        db.beginTransaction();

        values.put("idproduct", itemProduct.getCode());
        values.put("title", itemProduct.getTitle());
        values.put("image", itemProduct.getImage());
        values.put("idcategory", itemProduct.getCategory().getId());
        final long resultProduct = db.insert("Product", null, values);

        values = new ContentValues();
        values.put("idproduct", itemProduct.getCode());
        values.put("idstore", itemProduct.getStore().getId());
        final long resultRelation = db.insert("StoreProduct", null, values);

        if (resultProduct != SQL_ERROR_INSERT && resultRelation != SQL_ERROR_INSERT)
            db.setTransactionSuccessful();

        // Close transaction
        db.endTransaction();

        try {
            db.close();
        } catch (Exception e) {

        }
        db = null;
        values = null;

        return resultProduct != SQL_ERROR_INSERT && resultRelation != SQL_ERROR_INSERT;
    }

    public ArrayList<ItemProduct> getItemProductsByCategory(int idCategory, DataBaseHandler dh) {
        ArrayList<ItemProduct> itemProducts = new ArrayList<>();
        SQLiteDatabase db = dh.getReadableDatabase();
        String closeQuery = ";";
        String selectCategory = "SELECT id, name FROM Category WHERE id = " + idCategory + closeQuery;
        Cursor cursor = db.rawQuery(selectCategory, null);

        Category category = new Category();

        if (cursor != null) {
            cursor.moveToNext();
            category.setId(cursor.getInt(0));
            category.setName(cursor.getString(1));
        } else {
            return itemProducts; // There is no category with the id given
        }

        // Continues constructing Item Product
        String limitInQuery = " LIMIT 1 ";
        String selectProducts = "SELECT Product.idproduct, Product.title, Product.description, Product.image," +            // idproduct(0), title(1), description(2), image(3)
                " Store.id, Store.name, Store.phone, Store.idcity, Store.thumbnail, Store.latitude, Store.longitude," +     // id(4), name(5), phone(6), idcity(7), thumbnail(8), latitude(9), longitude(10)
                " City.id, City.name," +                                                                                    // id(11), name(12)
                " FROM Product WHERE idcategory = " + idCategory +
                " JOIN StoreProduct ON Product.idproduct = StoreProduct = idproduct" +
                " JOIN Store ON StoreProduct.idstore = Store.id" +
                " JOIN City ON Store.idcity = City.id";
        cursor = db.rawQuery(selectProducts + limitInQuery + closeQuery, null);
        Store store;
        City city;

        if (cursor != null) {
            city = new City();
            city.setId(cursor.getInt(11));
            city.setName(cursor.getString(12));

            store = new Store();
            store.setId(cursor.getInt(4));
            store.setName(cursor.getString(5));
            store.setPhone(cursor.getString(6));
            store.setThumbnail(cursor.getInt(8));
            store.setLatitude(cursor.getDouble(9));
            store.setLongitude(cursor.getDouble(10));
            store.setCity(city);
        } else {
            return itemProducts; // No products with that category
        }

        // Continues constructing Item Product
        cursor = db.rawQuery(selectProducts + closeQuery, null);
        ItemProduct itemProduct;

        while(cursor.moveToNext()) {
            itemProduct = new ItemProduct();

            itemProduct.setCode(cursor.getInt(0));
            itemProduct.setTitle(cursor.getString(1));
            itemProduct.setDescription(cursor.getString(2));
            itemProduct.setImage(cursor.getInt(3));
            itemProduct.setStore(store);
            itemProduct.setCategory(category);
            itemProducts.add(itemProduct);
        }

        try {
            cursor.close();
            db.close();
        } catch (Exception e) {
            Log.d(TAG, e.toString());
        }
        db = null;
        cursor = null;
        return itemProducts;
    }
}
