package solvit.com.scarlett.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by FranciscoPR on 19/01/16.
 */
public class Database extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "scarlett";
    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_CUARTO = "    CREATE TABLE Cuarto(\n" +
            "  cuartoid    INTEGER PRIMARY KEY, \n" +
            "  nombre  TEXT,\n" +
            "  tipo  TEXT,\n" +
            "  img  TEXT,\n" +
            "  orden  INTEGER \n" +
            ")\n";
    private static final String DATABASE_DEVICE =
            "CREATE TABLE Device(\n" +
                    "  deviceid     INTEGER PRIMARY KEY,\n" +
                    "  nombre_device       TEXT, \n" +
                    "  idcuarto       INTEGER  DEFAULT 0,     \n" +
                    "FOREIGN KEY(idcuarto) REFERENCES Cuarto(cuartoid) ON DELETE SET DEFAULT);";
    private static final String DATABASE_FOCOS =
            "CREATE TABLE Focos(\n" +
                    "  focoid     INTEGER PRIMARY KEY,\n" +
                    "  estado   INTEGER, \n" +
                    "  device INTEGER  DEFAULT 0,     \n" +
                    "  nombre   TEXT, \n" +
                    "FOREIGN KEY(device) REFERENCES Device(deviceid)ON DELETE SET DEFAULT );";

    private static final String DATABASE_PERSIANAS =
            "CREATE TABLE Persianas(\n" +
                    "  persianaid     INTEGER PRIMARY KEY,\n" +
                    "  estado   INTEGER, \n" +
                    "  device INTEGER  DEFAULT 0,     \n" +
                    "  nombre   TEXT, \n" +
                    "FOREIGN KEY(device) REFERENCES Device(deviceid)ON DELETE SET DEFAULT);";


    SQLiteDatabase db;

    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(DATABASE_CUARTO);
        db.execSQL(DATABASE_DEVICE);
        db.execSQL(DATABASE_PERSIANAS);
        db.execSQL(DATABASE_FOCOS);
    //    db.execSQL("Insert Into Cuarto VALUES(0,'Desconocido','desconocido','desconocido',0)");
    //    db.execSQL("Insert Into Device VALUES(0,'Desconocido',0)");

        db.execSQL("Insert Into Cuarto VALUES(1,'Recámara','recamara','recamara',1)");
        db.execSQL("Insert Into Device VALUES(1,'40ABBA51',1)");
        db.execSQL("Insert Into Focos VALUES(1,0,1,'Apagador1')");
        db.execSQL("Insert Into Focos VALUES(2,0,1,'Apagador2')");
        db.execSQL("Insert Into Focos VALUES(3,0,1,'Apagador3')");

        db.execSQL("Insert Into Cuarto VALUES(2,'Sala','sala','sala',2)");
        db.execSQL("Insert Into Device VALUES(2,'40AEBA30',2)");
        db.execSQL("Insert Into Device VALUES(3,'40AEBA6C',2)");
        db.execSQL("Insert Into Device VALUES(4,'40AEB83B',2)");
        db.execSQL("Insert Into Focos VALUES(4,0,2,'Apagador1')");
        db.execSQL("Insert Into Focos VALUES(5,0,2,'Apagador2')");
        db.execSQL("Insert Into Focos VALUES(6,0,3,'Apagador3')");
        db.execSQL("Insert Into Focos VALUES(7,0,3,'Apagador4')");
        db.execSQL("Insert Into Focos VALUES(8,0,3,'Apagador5')");
        db.execSQL("Insert Into Persianas VALUES(1,0,4,'Apagador6')");

        db.execSQL("Insert Into Cuarto VALUES(3,'Maqueta','patio','patio',3)");
        db.execSQL("Insert Into Device VALUES(5,'40AD58FD',3)");
        db.execSQL("Insert Into Device VALUES(6,'40AEB6C0',3)");
        db.execSQL("Insert Into Device VALUES(7,'40AEB980',3)");
        db.execSQL("Insert Into Focos VALUES(9,0,5,'Apagador1')");
        db.execSQL("Insert Into Focos VALUES(10,0,5,'Apagador2')");
        db.execSQL("Insert Into Focos VALUES(11,0,6,'Apagador3')");
        db.execSQL("Insert Into Persianas VALUES(12,0,7,'Apagador4')");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}

