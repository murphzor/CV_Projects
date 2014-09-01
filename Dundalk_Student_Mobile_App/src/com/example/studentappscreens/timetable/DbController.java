package com.example.studentappscreens.timetable;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DbController {

	private SQLiteDatabase db;
	private MySQLiteHelper dbHelper;
	
	private String[] dbCols = 
		{ 
			MySQLiteHelper.COLUMN_TITLE,
			MySQLiteHelper.COLUMN_MONCLASSES,
			MySQLiteHelper.COLUMN_TUESCLASSES, 
			MySQLiteHelper.COLUMN_WEDCLASSES,
			MySQLiteHelper.COLUMN_THURCLASSES,
			MySQLiteHelper.COLUMN_FRICLASSES, 
		};

	public DbController(Context context) 
	{
		dbHelper = new MySQLiteHelper(context);
	}

	public void open() throws SQLException 
	{
		db = dbHelper.getWritableDatabase();
	}

	public void close() 
	{
		dbHelper.close();
	}

	public FullTimetable addEntry(String title, String monClasses, String tuesClasses, String wedClasses, String thurClasses, String friClasses) 
	{

		ContentValues values = new ContentValues();
		values.put(MySQLiteHelper.COLUMN_TITLE, title);
		values.put(MySQLiteHelper.COLUMN_MONCLASSES, monClasses);
		values.put(MySQLiteHelper.COLUMN_TUESCLASSES, tuesClasses);
		values.put(MySQLiteHelper.COLUMN_WEDCLASSES, wedClasses);
		values.put(MySQLiteHelper.COLUMN_THURCLASSES, thurClasses);
		values.put(MySQLiteHelper.COLUMN_FRICLASSES, friClasses);


		long insertId = db.insert(MySQLiteHelper.TABLE_TIMETABLE, null, values);

		Cursor cursor = db.query(MySQLiteHelper.TABLE_TIMETABLE, dbCols, MySQLiteHelper.COLUMN_ID + " = " + insertId, null,null, null, null);

		cursor.moveToFirst();
		FullTimetable newEntry = cursorToEntry(cursor);
		cursor.close();

		return newEntry;
	}



	public void deleteEntry(long id) 
	{
		//long id = entry.getId();
		Log.d("DELETE ENTRY", Long.toString(id));
		db.delete(MySQLiteHelper.TABLE_TIMETABLE,
				MySQLiteHelper.COLUMN_ID + " = " + id, null);
	}
	
//	public void deleteEntry(FullTimetable entry) 
//	{
//		long id = entry.getId();
//		String title = entry.getTitle();
//		Log.d("DELETE ENTRY", Long.toString(id));
//		db.delete(MySQLiteHelper.TABLE_TIMETABLE, MySQLiteHelper.COLUMN_TITLE + " = " + title, null);
//	}

	public ArrayList<FullTimetable> getEntries() 
	{

		ArrayList<FullTimetable> entries = new ArrayList<FullTimetable>();

		Cursor cursor = db.query(MySQLiteHelper.TABLE_TIMETABLE, dbCols, null, null, null, null, null);

		cursor.moveToFirst();

		while (!cursor.isAfterLast()) {
			FullTimetable ftt = cursorToEntry(cursor);
			entries.add(ftt);
			cursor.moveToNext();
		}
		cursor.close();

		return entries;
	}

	private FullTimetable cursorToEntry(Cursor cursor) 
	{

		FullTimetable entry = new FullTimetable();

		//id here, removed for causing crash
		entry.setTitle(cursor.getString(0));
		entry.setMonClasses(cursor.getString(1));
		entry.setTuesClasses(cursor.getString(2));
		entry.setWedClasses(cursor.getString(3));
		entry.setThurClasses(cursor.getString(4));
		entry.setFriClasses(cursor.getString(5));
		
		return entry;
	}
}
