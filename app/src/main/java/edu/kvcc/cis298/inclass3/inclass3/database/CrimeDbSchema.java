package edu.kvcc.cis298.inclass3.inclass3.database;

/**
 * Created by aaernie7528 on 11/23/2015.
 */
//class holds info about the structure
public class CrimeDbSchema {

    //Inner class to represent information about the crime table
    public static final class CrimeTable {

        //The table name
        public static final String NAME = "crimes";

        //Column names
        public static final class Cols {
            public static final String UUID = "uuid";
            public static final String TITLE = "title";
            public static final String DATE = "date";
            public static final String SOLVED = "solved";
            public static final String SUSPECT = "suspect";
        }
    }
}
