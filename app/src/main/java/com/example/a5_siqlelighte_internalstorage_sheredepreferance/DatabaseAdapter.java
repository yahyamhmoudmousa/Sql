package com.example.a5_siqlelighte_internalstorage_sheredepreferance;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseAdapter {
    private static Helper dpHelper;


    public DatabaseAdapter(Context context){
        if(dpHelper == null ){
            dpHelper = new Helper(context);
        }
    }

    public long insertUser(User user){
        SQLiteDatabase db = dpHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();
//        cv.put(Helper.UID,user.getId());
        cv.put(Helper.PHONE_NUMBER,user.getPhoneNumber());
        cv.put(Helper.MESSAGE,user.getMessage());

        long id = db.insert(Helper.TABLE_NAME,null,cv);
        return id;
    }

    public User[] getAllUsers(){
        User[] users= null;
        SQLiteDatabase db = dpHelper.getReadableDatabase();
        int i = 0;
        Cursor c;
        String[] columns = { Helper.UID, Helper.PHONE_NUMBER,Helper.PHONE_NUMBER};
        c = db.query(Helper.TABLE_NAME,columns,null, null, null,null,null);
        users = new User[c.getCount()];
        while(c.moveToNext()){
            users[i] = new User(c.getInt(0),c.getString(1),c.getString(2));
            i++;
        }
        return users;
    }

    public void deleteAllUsers(){
        SQLiteDatabase db = dpHelper.getWritableDatabase();
        db.execSQL("delete from " + Helper.TABLE_NAME);
    }

    private static class Helper extends SQLiteOpenHelper {
        //Table Name
        public static final String DATABASE_NAME = "mydp";
        public static final int DATABASE_VERSION = 1;
        public static final String TABLE_NAME = "MYTABLE";
        public static final String UID = "_id";
        public static final String PHONE_NUMBER = "Phone";
        public static final String MESSAGE = "message";
        public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME +
                " ("+UID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+PHONE_NUMBER+" VARCHAR(20)," + MESSAGE+" VARCHAR(255));";


        public Helper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }


        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            sqLiteDatabase.execSQL(CREATE_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        }
    }
}
