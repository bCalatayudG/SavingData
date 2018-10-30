package com.example.billcalatayud.savingdata;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class PersonDatabase extends SQLiteOpenHelper {
    public PersonDatabase(@Nullable Context context) {
        super(context, PersonContract.NAME, null, PersonContract.VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(PersonContract.CREATE_TABLE);
    }

    @Override
    //Update the data base.
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Drop table and update to new version of database scheme
        //migration
    }

    public long savedPerson(Person person) {

        //get instance of the data base
        SQLiteDatabase database = getWritableDatabase();

        //Create contenct value to save the data as row
        ContentValues contentValues = new ContentValues();
        contentValues.put(PersonContract.FeedEntry.COL_NAME, person.getName());
        contentValues.put(PersonContract.FeedEntry.COL_AGE, person.getAge());
        contentValues.put(PersonContract.FeedEntry.COL_GENDER, person.getGender());

        long rowId = database.insert(PersonContract.FeedEntry.TABLE_NAME, null, contentValues);
        return rowId;
    }

    public List<Person> getPeople() {
        SQLiteDatabase database = getWritableDatabase();

        //database.execSQL(PersonContract.GET_ALL);
        List<Person> personList = new ArrayList<>();

        Cursor cursor = database.rawQuery(PersonContract.GET_ALL, null);

        if (cursor.moveToFirst()) {
            do {
                Person person = new Person(cursor.getString(cursor.getColumnIndex(PersonContract.FeedEntry.COL_NAME)),
                        cursor.getString(cursor.getColumnIndex(PersonContract.FeedEntry.COL_AGE)),
                        cursor.getString(cursor.getColumnIndex(PersonContract.FeedEntry.COL_GENDER)));

                personList.add(person);
            } while (cursor.moveToNext());
        }

        return personList;
    }
}


