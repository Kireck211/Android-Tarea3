package mx.iteso.app.database;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import mx.iteso.app.beans.Category;

public class CategoryControl {
    ArrayList<Category> getCategories(DataBaseHandler dh) {
        ArrayList<Category> categories = new ArrayList<>();
        String select = "SELECT id, name FROM Category";
        SQLiteDatabase db = dh.getReadableDatabase();
        Cursor cursor = db.rawQuery(select, null);

        while(cursor.moveToNext()) {
            Category category = new Category();
            category.setId(cursor.getInt(0));
            category.setName(cursor.getString(1));
            categories.add(category);
        }
        try {
            cursor.close();
            db.close();
        } catch (Exception e) {
        }
        db = null;
        cursor = null;
        return categories;
    }
}
