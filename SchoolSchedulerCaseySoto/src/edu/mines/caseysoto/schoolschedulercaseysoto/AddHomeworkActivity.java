package edu.mines.caseysoto.schoolschedulercaseysoto;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;
import android.content.Intent;

public class AddHomeworkActivity extends Activity {
	private final static int DESC_MAX = 140;
	private View mCourseText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_homework);
		
		mCourseText = findViewById(R.id.courseNameEnd);
		//get the message from the intent
	    Intent intent = getIntent();
	    String message = intent.getStringExtra( MainActivity.COURSE_MNAME);
	    ((TextView) mCourseText).setText(message);
	}

	public void submit(View view){
		String hwName = (String) findViewById(R.id.nameInput).toString();
		String desc = (String) findViewById(R.id.descriptionInput).toString();
		String dueDate = (String) findViewById(R.id.dateInput).toString();
		
		if(hwName.length() > 0 && desc.length() > 0){
			if(desc.length() > DESC_MAX) {
				desc = desc.substring(0, DESC_MAX);
			}
		} else {
			hwName = "Untitled";
			desc = "None";
		}
		
		Toast toast = Toast.makeText(getApplicationContext(), "Name: " + hwName + "Desc: " + desc.substring(0, 10) + "Due Date: " + dueDate, Toast.LENGTH_SHORT);
		toast.show();
		
		//TODO: Database Logic here
		finish();
		
	}
}
