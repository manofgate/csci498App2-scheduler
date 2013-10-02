package edu.mines.caseysoto.schoolschedulercaseysoto;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import android.app.Activity;

public class AddHomeworkActivity extends Activity {
	private final static int DESC_MAX = 140;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_homework);
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
