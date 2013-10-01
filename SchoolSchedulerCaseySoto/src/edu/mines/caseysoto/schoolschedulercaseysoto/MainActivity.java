package edu.mines.caseysoto.schoolschedulercaseysoto;

import android.net.Uri;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.ListActivity;
import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.AdapterView.AdapterContextMenuInfo;

@SuppressLint("NewApi")
public class MainActivity extends ListActivity implements LoaderManager.LoaderCallbacks<Cursor>, InputDialogFragment.Listener{

	private SimpleCursorAdapter adapter;
	private static final int DELETE_ID = Menu.FIRST + 1;
	private String courseName;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.classes_list);
		this.getListView().setDividerHeight( 2 );
	    fillData();
	    registerForContextMenu( getListView() );

	    if(savedInstanceState== null)
	    	check();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	public void onDialog(View view){
		Bundle args = new Bundle();
        args.putInt( "dialogID", 1 );
        args.putString( "prompt", getString( R.string.statement ) );

        InputDialogFragment dialog = new InputDialogFragment();
        dialog.setArguments( args );
        dialog.show( getFragmentManager(), "Dialog" );
		
	}
	public void check(){
		ContentValues values = new ContentValues();
	    values.put( CourseTable.COLUMN_NAME, "CSCI498" );
	    String[] projection = { CourseTable.COLUMN_ID, CourseTable.COLUMN_NAME}; 
	    Cursor cursor = getContentResolver().query( CourseContentProvider.CONTENT_URI, projection, null, null, null );
	    if(cursor.getCount() <= 0){
	    	Uri CourseUri = getContentResolver().insert( CourseContentProvider.CONTENT_URI, values );
	    }
	    cursor.close();
	}
	public void insertNewCourse(){
		ContentValues values = new ContentValues();
		//values.put(CourseTable.COLUMN_ID, "idd");
	    values.put( CourseTable.COLUMN_NAME, courseName );
	    String[] projection = { CourseTable.COLUMN_ID, CourseTable.COLUMN_NAME};
	    //TODO: need to figure out how not to enter in same course
	    Cursor cursor = getContentResolver().query( CourseContentProvider.CONTENT_URI, projection, null, null, null );
	    Uri CourseUri = getContentResolver().insert( CourseContentProvider.CONTENT_URI, values );
	    cursor.close();
	}
	@Override
	  protected void onListItemClick( ListView l, View v, int position, long id )
	  {
	    super.onListItemClick( l, v, position, id );
	    Intent i = new Intent( this, HomeworkActivity.class );
	    //Uri todoUri = Uri.parse( CourseContentProvider.CONTENT_URI + "/" + id );
	    //i.putExtra( CourseContentProvider.CONTENT_ITEM_TYPE, todoUri );
	    startActivity( i );
	  }
	
	@Override
	  public boolean onContextItemSelected( MenuItem item )
	  {
	    switch( item.getItemId() )
	    {
	      case DELETE_ID:
	        AdapterContextMenuInfo info = (AdapterContextMenuInfo)item.getMenuInfo();
	        Uri uri = Uri.parse( CourseContentProvider.CONTENT_URI + "/" + info.id );
	        getContentResolver().delete( uri, null, null );
	        fillData();
	        return true;
	    }
	    return super.onContextItemSelected( item );
	  }
	@Override
	public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {
		String[] projection = { CourseTable.COLUMN_ID, CourseTable.COLUMN_NAME };
	    CursorLoader cursorLoader = new CursorLoader( this, CourseContentProvider.CONTENT_URI, projection, null, null, null );
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
	    String[] from = new String[] { CourseTable.COLUMN_NAME };

	    // Fields on the UI to which we map
	    int[] to = new int[] { R.id.label };

	    // Ensure a loader is initialized and active.
	    getLoaderManager().initLoader( 0, null, this );
	    
	    // Note the last parameter to this constructor (zero), which indicates the adaptor should
	    // not try to automatically re-query the data ... the loader will take care of this.
	    this.adapter = new SimpleCursorAdapter( this, R.layout.list_row, null, from, to, 0 );
	    
	    
	    // Let this ListActivity display the contents of the cursor adapter.
	    setListAdapter( this.adapter );
	  }
	
	@Override
	  public void onInputDone( int dialogID, String input )
	  {
	    Log.d( "School_Scheduler", "\"" + input + "\" received from input dialog with id =" + dialogID );
	    
	    if(dialogID == 1){
	      this.courseName = input;
	      insertNewCourse();
	    }
	     
	  }

	  /**
	   * Callback method from InputDialogFragment when the user clicks Cancel.
	   * 
	   * @param dialogID The dialog producing the callback.
	   */
	  @Override
	  public void onInputCancel( int dialogID )
	  {
	    Log.d( "School_Scheduler", "No input received from input dialog with id =" + dialogID );
	  }
	  
	  /** The menu displayed on a long touch. */
	  @Override
	  public void onCreateContextMenu( ContextMenu menu, View v, ContextMenuInfo menuInfo )
	  {
	    super.onCreateContextMenu( menu, v, menuInfo );
	    menu.add( 0, DELETE_ID, 0, R.string.menu_delete );
	  }

	  
}
