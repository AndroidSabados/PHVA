package com.empresa.phva.db;

<<<<<<< HEAD
import static com.empresa.phva.db.Config.SQL_CREATE_USERDOCUMENTS;
import static com.empresa.phva.db.Config.SQL_DELETE_USERDOCUMENTS;
=======
import static com.empresa.phva.db.Config.*;
>>>>>>> 1233e6a9570f33162e38f0bf3a8bff02305e17dd

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
<<<<<<< HEAD
=======
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
>>>>>>> 1233e6a9570f33162e38f0bf3a8bff02305e17dd

public class DbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "phva.db";


    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        //Log.e("FFFFFFFFFF", "onCreate: "+SQL_CREATE_USERDOCUMENTS);
        db.execSQL(SQL_CREATE_USERDOCUMENTS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1){
        db.execSQL(SQL_DELETE_USERDOCUMENTS);
        onCreate(db);
    }


}
