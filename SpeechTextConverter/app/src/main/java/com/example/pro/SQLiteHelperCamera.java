package com.example.pro;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
public class SQLiteHelperCamera extends SQLiteOpenHelper{


    SQLiteHelperCamera(Context context,
                 String name,
                 SQLiteDatabase.CursorFactory factory,
                 int version){
        super(context, name, factory, version);
    }

    public void queryData(String sql){
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(sql);
    }

    //insertData
    public void insertData(String name){
        SQLiteDatabase database = getWritableDatabase();
        //query to insert record in database table
        String sql = "INSERT INTO RECORD_CAMERA VALUES(NULL, ?)"; //where "RECORD" is table name in database we will create in mainActivity

        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();

        statement.bindString(1, name);



        statement.executeInsert();
    }

    //updateData
    public void updateData(String name, int id){
        SQLiteDatabase database = getWritableDatabase();
        //query to update record
        String sql = "UPDATE RECORD_CAMERA SET name=? WHERE id=?";

        SQLiteStatement statement = database.compileStatement(sql);

        statement.bindString(1, name);
        statement.bindDouble(2, (double)id);

        statement.execute();
        database.close();
    }

    //deleteData
    public void deleteData(int id){
        SQLiteDatabase database = getWritableDatabase();
        //query to delete record using id
        String sql = "DELETE FROM RECORD_CAMERA WHERE id=?";

        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();
        statement.bindDouble(1, (double)id);

        statement.execute();
        database.close();
    }

    public Cursor getData(String sql){
        SQLiteDatabase database = getReadableDatabase();
        return database.rawQuery(sql, null);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
