package com.fatiappsstudio.rulesofjanazarnamaj.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.fatiappsstudio.rulesofjanazarnamaj.Person;
import com.fatiappsstudio.rulesofjanazarnamaj.database.SampleAppDbSchema.SampleAppTable;

import java.util.UUID;

/**
 * Created by Nishat on 5/27/2017.
 */

public class SampleAppCursorWrapper extends CursorWrapper {
    public SampleAppCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Person getPerson() {
        String uuidString = getString(getColumnIndex(SampleAppTable.Cols.UUID));
        String name = getString(getColumnIndex(SampleAppTable.Cols.NAME));
        String details = getString(getColumnIndex(SampleAppTable.Cols.DETAILS));
        String photo = getString(getColumnIndex(SampleAppTable.Cols.PHOTO));

        Person person = new Person(UUID.fromString(uuidString));
        person.setName(name);
        person.setDetails(details);
        person.setPhoto(photo);
        return person;
    }
}
