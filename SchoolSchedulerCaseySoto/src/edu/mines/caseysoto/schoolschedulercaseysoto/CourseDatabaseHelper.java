package edu.mines.caseysoto.schoolschedulercaseysoto;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * http://www.vogella.com/articles/AndroidSQLite/article.html
 * http://www.vogella.com/articles/AndroidSQLite/article.html#todo
 * 
 * http://www.vogella.com/articles/AndroidSQLite/article.html#todo_database
 */
public class CourseDatabaseHelper extends SQLiteOpenHelper
{
  private static final String DATABASE_NAME = "coursetable.db";
  private static final int DATABASE_VERSION = 1;

  public CourseDatabaseHelper( Context context )
  {
    super( context, DATABASE_NAME, null, DATABASE_VERSION );
  }

  /** Method is called during creation of the database */
  @Override
  public void onCreate( SQLiteDatabase database )
  {
    CourseTable.onCreate( database );
  }

  /** Method is called during an upgrade of the database, e.g. if you increase the database version. */
  @Override
  public void onUpgrade( SQLiteDatabase database, int oldVersion, int newVersion )
  {
    CourseTable.onUpgrade( database, oldVersion, newVersion );
  }
}
