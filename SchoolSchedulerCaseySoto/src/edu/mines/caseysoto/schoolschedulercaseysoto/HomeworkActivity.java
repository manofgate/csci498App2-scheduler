package edu.mines.caseysoto.schoolschedulercaseysoto;
/*
 * http://www.androidhive.info/2013/09/android-sqlite-database-with-multiple-tables/
 */

import android.annotation.SuppressLint;
import android.app.ListActivity;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.AdapterView.AdapterContextMenuInfo;

@SuppressLint("NewApi")
public class HomeworkActivity extends ListActivity implements LoaderManager.LoaderCallbacks<Cursor>{

	private static final int DELETE_ID = Menu.FIRST + 1;
	private static final int EDIT_ID = Menu.FIRST + 2;
	private String homeworkName;
	private SimpleCursorAdapter adapter;
	public static final String HW_NAME = "NameOfHomework";
	private String courseName;
	private View mCourseText;

	/** Called when the activity is first created. */
	@Override
	public void onCreate( Bundle savedInstanceState )
	{
		super.onCreate( savedInstanceState );
		setContentView( R.layout.homework_list );
		mCourseText= findViewById(R.id.courseNameMid);
		// Get the message from the intent
		Intent intent = getIntent();
		String message = intent.getStringExtra( MainActivity.COURSE_MNAME);
		((TextView) mCourseText).setText(message);
		courseName = message;
		
		this.getListView().setDividerHeight( 2 );
		fillData();
		registerForContextMenu( getListView() );

		

		
	}

	@Override
	public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {
		String[] projection = { HomeworkTable.COLUMN_ID, HomeworkTable.COLUMN_NAME, HomeworkTable.COLUMN_DATE, HomeworkTable.COLUMN_DESCRIPTION, HomeworkTable.COLUMN_COURSE_NAME };
		Log.d("SchoolScheduler::OnlyCourse", "This Course name is "+ courseName);
		String[] selection = {courseName};
		CursorLoader cursorLoader = new CursorLoader( this, SchedulerContentProvider.CONTENT_URI_H, projection, "course=?", selection, null );
		return cursorLoader;
	}

	@Override
	public void onLoadFinished(Loader<Cursor> arg0, Cursor arg1) {
		this.adapter.swapCursor( arg1 );
	}

	@Override
	public void onLoaderReset(Loader<Cursor> arg0) {
		this.adapter.swapCursor( null );
	}

	private void fillData()
	{
		// Fields from the database (projection)
		// Must include the _id column for the adapter to work

		String[] from = new String[] { HomeworkTable.COLUMN_NAME, HomeworkTable.COLUMN_DATE, HomeworkTable.COLUMN_DESCRIPTION };

		// Fields on the UI to which we map
		int[] to = new int[] { R.id.hwName, R.id.date, R.id.descrption };

		// Ensure a loader is initialized and active.
		getLoaderManager().initLoader( 0, null, this );

		// Note the last parameter to this constructor (zero), which indicates the adaptor should
		// not try to automatically re-query the data ... the loader will take care of this.
		this.adapter = new SimpleCursorAdapter( this, R.layout.home_list_row, null, from, to, 0 ){
			@Override
		    public View getView(int position, View convertView, ViewGroup parent) {
				 View v = super.getView(position, convertView, parent);

			        if (position %2 ==1) {
			            v.setBackgroundColor(Color.argb(TRIM_MEMORY_MODERATE, 100, 100, 100));
			        } else {
			            v.setBackgroundColor(Color.argb(TRIM_MEMORY_MODERATE, 170, 170, 170)); //or whatever was original
			        }

			        return v;
			}
			
		};;
		// Let this ListActivity display the contents of the cursor adapter.
		setListAdapter( this.adapter );
	}

	public void addHomeworkToList(View view){
		Intent intent = new Intent(this, AddHomeworkActivity.class);
		intent.putExtra(MainActivity.COURSE_MNAME, courseName);
		intent.putExtra(MainActivity.HW_NAME_TEXT, "");
		intent.putExtra(MainActivity.DATE_TEXT, "");
		intent.putExtra(MainActivity.DESC_TEXT, "");
		startActivity(intent);
	}
	/** The menu displayed on a long touch. */
	@Override
	public void onCreateContextMenu( ContextMenu menu, View v, ContextMenuInfo menuInfo )
	{
		super.onCreateContextMenu( menu, v, menuInfo );
		menu.add( 0, DELETE_ID, 0, R.string.menu_delete );
		menu.add( 0, EDIT_ID, 0, R.string.menu_edit );
	}
	@Override
	public boolean onContextItemSelected( MenuItem item )
	{
		switch( item.getItemId() )
		{
		case DELETE_ID:
			AdapterContextMenuInfo info = (AdapterContextMenuInfo)item.getMenuInfo();
			Uri uri = Uri.parse( SchedulerContentProvider.CONTENT_URI_H + "/" + info.id );
			getContentResolver().delete( uri, null, null );
			fillData();
			return true;
		}
		return super.onContextItemSelected( item );
	}
	
	@Override
	protected void onListItemClick( ListView l, View v, int position, long id )
	{
		super.onListItemClick( l, v, position, id );
		Intent i = new Intent( this, AddHomeworkActivity.class );
		Uri courseUri = Uri.parse( SchedulerContentProvider.CONTENT_URI_H + "/" + id );
		String[] projection = { HomeworkTable.COLUMN_NAME, HomeworkTable.COLUMN_DATE, HomeworkTable.COLUMN_DESCRIPTION, HomeworkTable.COLUMN_COURSE_NAME };

		//gets the uris for the same id, moves it to first position.
		Cursor cursor = getContentResolver().query( courseUri, projection, null, null, null );
		String name= "", courseName = "", date = "", desc = "";
		
		cursor.moveToFirst();	    
		name = cursor.getString( cursor.getColumnIndexOrThrow( HomeworkTable.COLUMN_NAME ) );
		date = cursor.getString( cursor.getColumnIndexOrThrow( HomeworkTable.COLUMN_DATE ) );
		desc = cursor.getString( cursor.getColumnIndexOrThrow( HomeworkTable.COLUMN_DESCRIPTION ) );
		courseName = cursor.getString( cursor.getColumnIndexOrThrow( HomeworkTable.COLUMN_COURSE_NAME ) );
		cursor.close();
		
		i.putExtra(MainActivity.HW_NAME_TEXT, name);
		i.putExtra(MainActivity.DATE_TEXT, date);
		i.putExtra(MainActivity.DESC_TEXT, desc);
		i.putExtra(MainActivity.COURSE_MNAME, courseName);
		startActivity( i );
	}
}
