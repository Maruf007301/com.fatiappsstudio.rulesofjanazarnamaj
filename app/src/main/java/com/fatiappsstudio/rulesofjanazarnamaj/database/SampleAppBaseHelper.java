package com.fatiappsstudio.rulesofjanazarnamaj.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.fatiappsstudio.rulesofjanazarnamaj.R;
import com.fatiappsstudio.rulesofjanazarnamaj.database.SampleAppDbSchema.SampleAppTable;

import java.util.UUID;

import static com.fatiappsstudio.rulesofjanazarnamaj.database.SampleAppDbSchema.SampleAppTable.NAME;


/**
 * Created by Nishat on 5/27/2017.
 */

public class SampleAppBaseHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "SampleAppBase.db";
    private static final String TAG = "Error_tag";
    private Context context;
    public SampleAppBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + NAME + "(" +
                " _id integer primary key autoincrement, " +
                SampleAppTable.Cols.UUID + ", " +
                SampleAppTable.Cols.NAME + ", " +
                SampleAppTable.Cols.DETAILS + ", " +
                SampleAppTable.Cols.PHOTO +
                ")"
        );

        String[] mNames = context.getResources().getStringArray(R.array.name_bank);
        String[] mDetails = context.getResources().getStringArray(R.array.details_bank);
        String[] mPhotos = context.getResources().getStringArray(R.array.photo_bank);

        ContentValues values = new ContentValues();
        for (int i=0; i<mNames.length; i++) {
            values.put(SampleAppTable.Cols.UUID, UUID.randomUUID().toString());
            values.put(SampleAppTable.Cols.NAME, mNames[i]);
            values.put(SampleAppTable.Cols.DETAILS, mDetails[i]);
            values.put(SampleAppTable.Cols.PHOTO, mPhotos[i]);
            db.insert(NAME, null, values);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(TAG, "Upgrading database from version " + oldVersion + " to " +
                newVersion + ", which will destroy all old data");

        db.execSQL("DROP TABLE IF EXISTS " + NAME);
        onCreate(db);

    }
}
