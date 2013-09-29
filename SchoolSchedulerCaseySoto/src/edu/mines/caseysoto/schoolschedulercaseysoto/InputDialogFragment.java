package edu.mines.caseysoto.schoolschedulercaseysoto;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.widget.EditText;


public class InputDialogFragment extends DialogFragment
{
  private EditText input;
  private int dialogID;
  private String prompt;

  protected interface Listener
  {
    void onInputDone( int dialogID, String input );
    void onInputCancel( int dialogID );
  }

  @Override
  public Dialog onCreateDialog( Bundle savedInstanceState )
  {
    // Set default values.
    this.dialogID = -1;
    this.prompt = getString( R.string.default_message );


    Bundle args = getArguments();
    if( args != null )
    {
      this.dialogID = args.getInt( "dialogID", this.dialogID );
      this.prompt = args.getString( "prompt", this.prompt );
    }

    // Create an input field.
    input = new EditText( getActivity() );
    input.setInputType( InputType.TYPE_CLASS_TEXT );

    // Create the dialog.
    return new AlertDialog.Builder( getActivity() )
      .setTitle( R.string.app_name )
      .setMessage( this.prompt )
      .setPositiveButton( R.string.dialog_done,
          new DialogInterface.OnClickListener()
          {
            public void onClick( DialogInterface dialog, int whichButton )
            {
              ((InputDialogFragment.Listener)getActivity()).onInputDone( dialogID, input.getText().toString() );
            }
          }
      )
      .setNegativeButton( R.string.dialog_cancel,
          new DialogInterface.OnClickListener()
          {
            public void onClick( DialogInterface dialog, int whichButton )
            {
              ((InputDialogFragment.Listener)getActivity()).onInputCancel( dialogID );
            }
          }
      )
      .setView( input )
      .create();
  }
}
