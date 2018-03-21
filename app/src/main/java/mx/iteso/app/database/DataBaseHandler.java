package mx.iteso.app.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DataBaseHandler extends SQLiteOpenHelper {
    private static final String TAG = "Debug " + DataBaseHandler.class.getSimpleName();
    private static final String DATABASE_NAME = "ITESO_APP.db";
    private static final int DATABASE_VERSION = 1;

    private static DataBaseHandler dataBaseHandler;

    private DataBaseHandler (Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static DataBaseHandler getInstance(Context context) {
        if (dataBaseHandler == null)
            dataBaseHandler = new DataBaseHandler(context);
        return dataBaseHandler;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String city =
                "CREATE TABLE City (" +
                        "id INTEGER PRIMARY KEY," +
                        "name TEXT" +
                        ");";

        String category =
                "CREATE TABLE Category (" +
                        "id INTEGER PRIMARY KEY," +
                        "name TEXT" +
                        ");";

        String store =
                "CREATE TABLE Store (" +
                        "id INTEGER PRIMARY KEY," +
                        "name TEXT," +
                        "phone TEXT," +
                        "idcity INTEGER," +
                        "thumbnail INTEGER," +
                        "latitude DOUBLE," +
                        "longitude DOUBLE" +
                        ");";

        String product =
                "CREATE TABLE Product (" +
                        "idproduct INTEGER PRIMARY KEY, " +
                        "title TEXT," +
                        "description TEXT, " +
                        "image INTEGER," +
                        "idcategory INTEGER" +
                        ");";

        String storeProduct =
                "CREATE TABLE StoreProduct (" +
                        "id INTEGER PRIMARY KEY," +
                        "idproduct INTEGER," +
                        "idstore INTEGER" +
                        ");";
        //  Start transaction
        db.beginTransaction();

        db.execSQL(city);
        db.execSQL(category);
        db.execSQL(store);
        db.execSQL(product);
        db.execSQL(storeProduct);

        // Add immediately three categories
        String[] categories = new String[]{"TECHNOLOGY", "HOME", "ELECTRONICS"};
        for(String categoryName: categories)
            db.execSQL("INSERT INTO Category (name) VALUES ('" + categoryName + "');");

        // Add immediately cities
        String[] cities = new String[] {
                "Ciudad de Mexico",
                "Ecatepec",
                "Guadalajara",
                "Puebla de Zaragoza",
                "Ciudad Juarez",
                "Tijuana",
                "Leon",
                "Zapopan",
                "Monterrey",
                "Nezahualcoyotl",
                "Chihuahua",
                "Naucalpan",
                "Merida",
                "San Luis Potosi",
                "Aguascalientes",
                "Hermosillo",
                "Saltillo",
                "Mexicali",
                "Culiacan",
                "Guadalupe",
                "Acapulco de Juarez",
                "Tlalnepantla de Baz",
                "Cancun",
                "Santiago de Queretaro",
                "Chimalhuacan",
                "Torreon",
                "Morelia",
                "Reynosa",
                "Tlaquepaque",
                "Tuxtla Gutierrez",
                "Victoria de Durango",
                "Toluca de Lerdo",
                "Ciudad Lopez Mateos",
                "Cuautitlan Izcalli",
                "Apodaca",
                "Heroica Matamoros",
                "San Nicolas",
                "Veracruz",
                "Xalapa",
                "Tonala",
                "Mazatlan",
                "Lazaro Cardenas",
                "Tepic",
                "Zacatecas"
        };

        for (String city_name : cities)  {
            city_name = city_name.replaceAll("\\s+","").toLowerCase() ;
            db.execSQL("INSERT INTO City (name) VALUES ('" + city_name + "');");
        }

        try {
            checkDataBase(db);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.d(TAG, e.toString());
        } finally {
            db.endTransaction();
        }
    }

    public void checkDataBase(SQLiteDatabase db) throws Exception {
        String[] tableNames = new String[]{"City", "Category", "Store", "Product", "StoreProduct"};
        Cursor cursor;

        // Check if all tables were created correctly
        String query;
        for (String tableName : tableNames) {
            query = "SELECT name FROM sqlite_master WHERE type='table' AND name={'" + tableName +"'};";
            cursor = db.rawQuery(query, null);

            if (cursor == null || cursor.getCount() == 0)
                throw new Exception("Table " + tableName + " was not created.");
        }

        // Check if Category tables contains three elements
        query = "SELECT id, name FROM Category;";
        cursor = db.rawQuery(query, null);
        if (cursor.getCount() != 3)
            throw  new Exception("Category table does not contain three elements.");

        // Check if cities were inserted
        query = "SELECT id, name FROM City;";
        cursor = db.rawQuery(query, null);
        if (cursor.getCount() != 43)
            throw new Exception("City table does not contain all cities.");

        cursor.close();
        cursor = null;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
