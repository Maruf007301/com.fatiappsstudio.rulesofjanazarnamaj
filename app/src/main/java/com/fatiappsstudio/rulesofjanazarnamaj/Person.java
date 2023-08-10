package com.fatiappsstudio.rulesofjanazarnamaj;

import java.util.UUID;

/**
 * Created by Nishat on 5/27/2017.
 */

public class Person {
    private UUID mID;
    private String mName;
    private String mDetails;
    private String mPhoto;

    public Person() {
        this(UUID.randomUUID());
    }

    public Person(UUID id) {
        mID = id;
    }

    public UUID getID() {
        return mID;
    }

    public void setID(UUID ID) {
        mID = ID;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getDetails() {
        return mDetails;
    }

    public void setDetails(String details) {
        mDetails = details;
    }

    public String getPhoto() {
        return mPhoto;
    }

    public void setPhoto(String photo) {
        mPhoto = photo;
    }
}
