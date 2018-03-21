package mx.iteso.app.database;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import mx.iteso.app.beans.City;

public class CityControl {
    private static final String TAG = "Debug " + CityControl.class.getSimpleName();

    public static City getCityByName(String name, DataBaseHandler dh) {
        SQLiteDatabase db = dh.getReadableDatabase();
        name = name.replaceAll("\\s+","").toLowerCase();
        String select = "SELECT id FROM City WHERE name = '" + name + "';";
        Cursor cursor = db.rawQuery(select, null);
        City city = null;

        if (cursor != null && cursor.getCount() == 1) {
            cursor.moveToNext();
            city = new City();
            city.setId(cursor.getInt(0));
            city.setName(name.substring(0,1).toUpperCase() + name.substring(1));
        }
        return city;
    }
}
