package mx.iteso.app.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import mx.iteso.app.beans.City;
import mx.iteso.app.beans.Store;

public class StoreControl {
    public void addStore(Store store, DataBaseHandler dh) {
        SQLiteDatabase db = dh.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id", store.getId());
        values.put("name", store.getName());
        values.put("phone", store.getPhone());
        values.put("idcity", store.getCity().getId());
        values.put("thumbnail", store.getThumbnail());
        values.put("latitude", store.getLatitude());
        values.put("longitude", store.getLongitude());
    }

    public ArrayList<Store> getStores(DataBaseHandler dh) {
        ArrayList<Store> stores = new ArrayList<>();
        String select = "SELECT id, name, phone, thumbnail, latitude, longitude, City.id, City.name FROM Store INNER JOIN City ON Store.idcity = City.id";
        SQLiteDatabase db = dh.getReadableDatabase();
        Cursor cursor = db.rawQuery(select, null);
        while (cursor.moveToNext()) {
            Store store = new Store();
            store.setId(cursor.getInt(0));
            store.setName(cursor.getString(1));
            store.setPhone(cursor.getString(2));
            store.setThumbnail(cursor.getInt(3));
            store.setLatitude(cursor.getDouble(4));
            store.setLongitude(cursor.getDouble(5));
            City city =  new City(cursor.getInt(cursor.getInt(6), cursor.getString()));
            store.setCity(city);
        }
    }
}
