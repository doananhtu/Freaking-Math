package myapp.anhtu.com.freakingmath.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import myapp.anhtu.com.freakingmath.entity.Player;

/**
 * Created by anhtu on 2/14/2017.
 */

public class DatabaseFreakingMath extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "FreakingMath";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "player";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_SCORE = "score";
    public DatabaseFreakingMath(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = String.format("CREATE TABLE IF NOT EXISTS " +
                "%s" +
                "(%s INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                "%s VARCHAR, " +
                "%s INTEGER)",TABLE_NAME,KEY_ID,KEY_NAME,KEY_SCORE);
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = String.format("DROP TABLE IF EXISTS %s", TABLE_NAME);
        db.execSQL(sql);
        onCreate(db);
    }

    public void addScore(Player player){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME,player.getName());
        values.put(KEY_SCORE,player.getScore());
        db.insert(TABLE_NAME,null,values);
        db.close();
    }

    public ArrayList<Player> getAllScore(){
        ArrayList<Player> playerList = new ArrayList<>();
        String sql = "SELECT * FROM " + TABLE_NAME + " ORDER BY " + KEY_SCORE + " DESC";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql,null);
        cursor.moveToFirst();

        while(cursor.isAfterLast()==false){
            Player player = new Player(cursor.getString(1),cursor.getInt(2));
            playerList.add(player);
            cursor.moveToNext();
        }
        return playerList;
    }

    public ArrayList<Player> getTopScore(){
        ArrayList<Player> playerList = new ArrayList<>();
        String sql = "SELECT * FROM " + TABLE_NAME + " ORDER BY " + KEY_SCORE + " DESC LIMIT 10";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql,null);
        cursor.moveToFirst();

        while(cursor.isAfterLast()==false){
            Player player = new Player(cursor.getString(1),cursor.getInt(2));
            playerList.add(player);
            cursor.moveToNext();
        }
        return playerList;
    }

    public int getMaxScore(){
        int score = 0;
        SQLiteDatabase db = getWritableDatabase();
        String sql = "SELECT MAX("+KEY_SCORE+") FROM " +TABLE_NAME;
        Cursor cursor = db.rawQuery(sql,null);
        cursor.moveToFirst();
        if(cursor.getCount()>0)
            score = cursor.getInt(0);
        db.close();
        return score;
    }

    public void delAllScore(){
        SQLiteDatabase db = getWritableDatabase();
        String sql = "DELETE * FROM " + TABLE_NAME;
        db.delete(TABLE_NAME,null,null);
        db.close();
    }
}
