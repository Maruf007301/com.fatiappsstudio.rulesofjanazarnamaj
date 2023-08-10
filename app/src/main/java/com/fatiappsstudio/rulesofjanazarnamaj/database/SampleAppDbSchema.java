package com.fatiappsstudio.rulesofjanazarnamaj.database;

/**
 * Created by Nishat on 5/27/2017.
 */

public class SampleAppDbSchema {
    public static final class SampleAppTable {
        public static final String NAME = "items";

        public static final class Cols {
            public static final String UUID = "uuid";
            public static final String NAME = "name";
            public static final String DETAILS = "details";
            public static final String PHOTO = "photo";
        }
    }
}