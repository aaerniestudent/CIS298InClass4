package edu.kvcc.cis298.inclass3.inclass3;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import edu.kvcc.cis298.inclass3.inclass3.database.CrimeBaseHelper;
import edu.kvcc.cis298.inclass3.inclass3.database.CrimeCursorWrapper;
import edu.kvcc.cis298.inclass3.inclass3.database.CrimeDbSchema;
import edu.kvcc.cis298.inclass3.inclass3.database.CrimeDbSchema.CrimeTable;

/**
 * Created by dbarnes on 10/28/2015.
 */
public class CrimeLab {

    //Static variable to hold the instance of CrimeLab
    //Rather than returning a new instance of CrimeLab,
    //we will return this variable that holds our instance.
    private static CrimeLab sCrimeLab;

    //A variable to of TYPE List, which is an interface, to hold
    //A list of TYPE Crime.
    //private List<Crime> mCrimes;

    //variable for context
    private Context mContext;
    //variable for database
    private SQLiteDatabase mDatabase;

    //This is the method that will be used to get an instance of
    //CrimeLab. It will check to see if the current instance in the
    //variable is null, and if it is, it will create a new one using
    //the private constuctor. If it is NOT null, it will just return
    //the instance that exists.
    public static CrimeLab get(Context context) {
        if (sCrimeLab == null) {
            sCrimeLab = new CrimeLab(context);
        }
        return sCrimeLab;
    }

    //This is the constuctor. It is private rather than public.
    //It is private because we don't want people to be able to
    //create a new instance from outside classes. If they want
    //to make an instance, we want them to use the get method
    //declared right above here.
    private CrimeLab(Context context) {

        //assign the passed in context to the class level one
        mContext = context.getApplicationContext();
        //use the context in conjunction with the CrimeBaseHelper class
        //that we wrote o get the writable database. We didn't write
        //that function, it comes from the parent class of CBH.
        mDatabase = new CrimeBaseHelper(mContext).getWritableDatabase();
        //Instantiate a new ArrayList, which is a child class that
        //Implements the Interface List. Because ArrayList is a child
        //of List, we can store it in the mCrimes variable which is of
        //type List, and not ArrayList. (Polymorphism)
        //mCrimes = new ArrayList<>();

        //for loop to populate our arraylist with some dummy data.
        /*for (int i=0; i < 100; i++) {
         *   Crime crime = new Crime();
         *   crime.setTitle("Crime #" + i);
         *   crime.setSolved(i % 2 == 0);
         *   mCrimes.add(crime);
         *
        }*/
    }

    //method to add a new crime to the database. Called when a user adds with the toolbar
    public void addCrime(Crime c) {
        //mCrimes.add(c);
        //get the content values that we would like to stick into the database by sending it the crime that needs to be added
        ContentValues values = getContentValues(c);
        //call the insert method of our class level version of CrimeBaseHelper class.
        //insert is inherited from CBH's parent class.
        mDatabase.insert(CrimeTable.NAME, null, values);
    }

    //Getter to get the crimes
    public List<Crime> getCrimes() {
        //return mCrimes;
        List<Crime> crimes = new ArrayList<>();
        CrimeCursorWrapper cursor = queryCrimes(null,null);
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                crimes.add(cursor.getCrime());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return crimes;
    }

    //Method to get a specific crime based on the
    //UUID that is passed in.
    public Crime getCrime(UUID id) {
        //This is a foreach loop to go through all of the crimes
        //at each iteration the current crime will be called 'crime'.
        /*for (Crime crime : mCrimes) {
         *   //If we find a match, return it.
         *   if (crime.getId().equals(id)) {
         *       return crime;
         *   }
         *
        }*/
        //no match, return null.
        CrimeCursorWrapper cursor = queryCrimes(
                CrimeTable.Cols.UUID + " = ?",
                new String[] { id.toString()}
        );
        try {
            if (cursor.getCount() == 0) {
                return null;
            }
            cursor.moveToFirst();
            return cursor.getCrime();
        }finally {
            cursor.close();
        }
    }

    public void updateCrime(Crime crime) {
        String uuidString = crime.getId().toString();
        ContentValues values = getContentValues(crime);
        mDatabase.update(CrimeTable.NAME, values,CrimeTable.Cols.UUID + " = ?",new String[] {uuidString});
    }
    //static method to do the work of taking in a crime and creating a content values object
    // that can be used to insert the crime into the database. the contentvalues class operates
    // as a hash table, or "key => value" array. The key refers to the column name of the
    // database and the vale refers to the value we would like to put into the database.
    private static ContentValues getContentValues(Crime crime) {
        //make a new object
        ContentValues values = new ContentValues();
        //put the UUID into a string.
        values.put(CrimeTable.Cols.UUID, crime.getId().toString());
        //put in the title
        values.put(CrimeTable.Cols.TITLE, crime.getTitle());
        //put in the date as a timestamp.
        values.put(CrimeTable.Cols.DATE, crime.getDate().getTime());
        //put the solved bool converted using a ternary operator.
        //That evaluates an expression as true("? 1 :") / false (": 0") statement.
        values.put(CrimeTable.Cols.SOLVED, crime.isSolved() ? 1 : 0);

        return values;
    }

    //method to query the table for crimes. It takes in a where
    //clause and args that can be used for the query.
    private CrimeCursorWrapper queryCrimes(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                CrimeTable.NAME,  // table name
                null,  //Columns - null selects all columns
                whereClause,  // where
                whereArgs,  // args for where
                null,  // group by
                null,  // having
                null   // order by
        );
        return new CrimeCursorWrapper(cursor);
    }
}
