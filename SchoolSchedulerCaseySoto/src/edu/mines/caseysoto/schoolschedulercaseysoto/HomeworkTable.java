package edu.mines.caseysoto.schoolschedulercaseysoto;


import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


public class HomeworkTable
{
	// Database table
	public static final String TABLE_NAME = "homework";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_NAME = "name";
	public static final String COLUMN_DATE = "date";
	public static final String COLUMN_DESCRIPTION = "desc";
	public static final String COLUMN_COURSE_NAME = "course";

	// Database creation SQL statement
	private static final String TABLE_CREATE = "create table " + TABLE_NAME + "(" + 
			COLUMN_ID + " integer primary key autoincrement, " + 
			COLUMN_NAME + " text not null" + 
			COLUMN_DATE + " text not null" +
			COLUMN_DESCRIPTION + " text not null" +
			COLUMN_COURSE_NAME + " text not null" + ");";

	public static void onCreate( SQLiteDatabase database )
	{
		database.execSQL( TABLE_CREATE );
	}

	public static void onUpgrade( SQLiteDatabase database, int oldVersion, int newVersion )
	{
		Log.w( HomeworkTable.class.getName(), "Upgrading database from version " + oldVersion + " to " + newVersion + ", which will destroy all old data." );
		database.execSQL( "DROP TABLE IF EXISTS " + TABLE_NAME );
		onCreate( database );
	}
}
