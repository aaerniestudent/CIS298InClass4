package edu.kvcc.cis298.inclass3.inclass3.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import edu.kvcc.cis298.inclass3.inclass3.database.CrimeDbSchema.CrimeTable;

/**
 * Created by aaernie7528 on 11/23/2015.
 */
public class CrimeBaseHelper extends SQLiteOpenHelper{

    //Setup a version number
    private static final int VERSION = 1;
    //Setup a name for the database
    private static final String DATABASE_NAME = "crimeBase.db";

    //Constructor to get this class instanciated
    public CrimeBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    //This is the method that will get called if the database needs to be created
    @Override
    public void onCreate(SQLiteDatabase db) {
        //Execute a Q&D SQLite statement to create the table to store the data.
        //It would be safer to use some other layer of abstraction for the such as ORM.
        db.execSQL("create table " + CrimeTable.NAME + "(" +
                " _id integer primary key autoincrement, " +
                CrimeTable.Cols.UUID + ", " +
                CrimeTable.Cols.TITLE + ", " +
                CrimeTable.Cols.DATE + ", " +
                CrimeTable.Cols.SOLVED + ", " +
                CrimeTable.Cols.SUSPECT + ")" );
    }

    //this is the method that will get called if the the database already exists
    // but is not on the same version specified above. this will migrate the
    // database tot he most current version for the app.
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

    }
}
