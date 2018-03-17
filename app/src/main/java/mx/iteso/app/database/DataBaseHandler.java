package mx.iteso.app.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHandler extends SQLiteOpenHelper {
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
                        ")";

        String category =
                "CREATE TABLE Category (" +
                        "id INTEGER PRIMARY KEY," +
                        "name TEXT" +
                        ")";

        String store =
                "CREATE TABLE Store (" +
                        "id INTEGER PRIMARY KEY," +
                        "name TEXT," +
                        "phone TEXT," +
                        "idcity INTEGER," +
                        "thumbnail INTEGER," +
                        "latitude DOUBLE," +
                        "longitude DOUBLE" +
                        ")";

        String product =
                "CREATE TABLE Product (" +
                        "idproduct INTEGER PRIMARY KEY, " +
                        "title TEXT," +
                        "image INTEGER," +
                        "idcategory INTEGER" +
                        ")";

        String storeProduct =
                "CREATE TABLE StoreProduct (" +
                        "id INTEGER PRIMARY KEY," +
                        "idproduct INTEGER," +
                        "idstore INTEGER" +
                        ")";
        db.execSQL(city);
        db.execSQL(category);
        db.execSQL(store);
        db.execSQL(product);
        db.execSQL(storeProduct);

        // Add immediately three categories
        db.execSQL("INSERT INTO Category (id, name) VALUES (0, 'TECHNOLOGY')");
        db.execSQL("INSERT INTO Category (id, name) VALUES (1, 'HOME')");
        db.execSQL("INSERT INTO Category (id, name) VALUES (2, 'ELECTRONICS')");

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
                "Zacatecas"
        };
        for(int i = 0; i < cities.length; i++)
            db.execSQL("INSERT INTO City (id, name) VALUES (" + i + ", '" + cities[i] + "')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
