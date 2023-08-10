package com.fatiappsstudio.rulesofjanazarnamaj;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.fatiappsstudio.rulesofjanazarnamaj.database.SampleAppBaseHelper;
import com.fatiappsstudio.rulesofjanazarnamaj.database.SampleAppCursorWrapper;
import com.fatiappsstudio.rulesofjanazarnamaj.database.SampleAppDbSchema.SampleAppTable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.fatiappsstudio.rulesofjanazarnamaj.database.SampleAppDbSchema.SampleAppTable.NAME;

/**
 * Created by Nishat on 5/27/2017.
 */

public class PersonLab {
    private static PersonLab sPersonLab;
    private List<Person> mPersons;
    private Context mContext;
    private SQLiteDatabase mDatabase;

    public static PersonLab get(Context context) {
        if (sPersonLab == null) {
            sPersonLab = new PersonLab(context);
        }
        return sPersonLab;
    }

    private PersonLab(Context context) {
        mContext = context.getApplicationContext();
        mDatabase = new SampleAppBaseHelper(mContext).getWritableDatabase();
    }

    public void addPerson(Person p) {
        ContentValues values = getContentValues(p);
        mDatabase.insert(NAME, null, values);
    }

    public List<Person> getPersons() {
        SampleAppCursorWrapper cursor = queryPersons(null, null, null);

        List<Person> persons = new ArrayList<>();

        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                persons.add(cursor.getPerson());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return persons;
    }

    public Person getPerson(UUID id) {
        SampleAppCursorWrapper cursor = queryPersons(
                SampleAppTable.Cols.UUID + " = ?",
                new String[]{id.toString()},
                null
        );
        try {
            if (cursor.getCount() == 0) {
                return null;
            }
            cursor.moveToFirst();
            return cursor.getPerson();
        } finally {
            cursor.close();
        }
    }

    public void updatePerson(Person person) {
        String uuidString = person.getID().toString();
        ContentValues values = getContentValues(person);
        mDatabase.update(NAME, values,
                SampleAppTable.Cols.UUID + " = ?",
                new String[]{uuidString});
    }

    public void deletePerson(UUID personID) {
        String uuidString = personID.toString();
        mDatabase.delete(NAME, SampleAppTable.Cols.UUID + " = ?", new String[]{uuidString});
    }

    private static ContentValues getContentValues(Person person) {
        ContentValues values = new ContentValues();
        values.put(SampleAppTable.Cols.UUID, person.getID().toString());
        values.put(SampleAppTable.Cols.NAME, person.getName());
        values.put(SampleAppTable.Cols.DETAILS, person.getDetails());
        values.put(SampleAppTable.Cols.PHOTO, person.getPhoto());
        return values;
    }

    private SampleAppCursorWrapper queryPersons(String whereClause, String[] whereArgs, String orderBy) {
        Cursor cursor = mDatabase.query(
                NAME,
                null, // Columns - null selects all columns
                whereClause,
                whereArgs,
                null, // groupBy
                null, // having
                orderBy // orderBy
        );
        return new SampleAppCursorWrapper(cursor);
    }
}
