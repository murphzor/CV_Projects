package com.example.studentappscreens.timetable;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySQLiteHelper extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "timetable.db";
	public static final String TABLE_TIMETABLE = "timetable";
	private static final int DATABASE_VERSION = 2;
  
	public static final String COLUMN_ID = "id";
	public static final String COLUMN_TITLE = "title";
	public static final String COLUMN_MONCLASSES = "monClasses"; 
	public static final String COLUMN_TUESCLASSES = "tuesClasses"; 
	public static final String COLUMN_WEDCLASSES = "wedClasses";
	public static final String COLUMN_THURCLASSES = "thurClasses"; 
	public static final String COLUMN_FRICLASSES = "friClasses";

  

  private static final String DATABASE_CREATE = "create table " 
          + TABLE_TIMETABLE + "(" 
	      + COLUMN_ID       + " integer primary key autoincrement, " 
	      + COLUMN_TITLE + " text not null, "
		  + COLUMN_MONCLASSES  + " text not null, "
		  + COLUMN_TUESCLASSES + " text not null, "		  
		  + COLUMN_WEDCLASSES  + " text not null, "
		  + COLUMN_THURCLASSES + " text not null, "
		  + COLUMN_FRICLASSES  + " text not null )";

  public MySQLiteHelper(Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
  }

  @Override
  public void onCreate(SQLiteDatabase database) {
    database.execSQL(DATABASE_CREATE);
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    Log.w(MySQLiteHelper.class.getName(),"Upgrading database from version " + oldVersion + " to "
            + newVersion + ", which will destroy all old data");
    
    db.execSQL("DROP TABLE IF EXISTS " + TABLE_TIMETABLE);
    onCreate(db);
  }

} 
