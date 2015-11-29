package com.eurekakids.euraka1;

/**
 * Created by Kirubanand on 06/09/2015.
 */
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;
import android.widget.EditText;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.eurekakids.com.eurekakids.db.DatabaseHandler;
import com.eurekakids.db.datamodel.Student;


public class AddKid extends AppCompatActivity{

    private Toolbar toolbar;
	private int centre_id;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_new_kid);

		Bundle bundle = getIntent().getExtras();
		centre_id = bundle.getInt("CENTRE_ID");

		toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

    }

    public void movetoList(View view)
    {
        EditText KidName, KidStd;
        KidName = (EditText) findViewById(R.id.editname);
		KidStd = (EditText) findViewById(R.id.std);

        String name = KidName.getText().toString();
		int std = Integer.parseInt(KidStd.getText().toString());

        Toast.makeText(AddKid.this,"Added  "+name,Toast.LENGTH_SHORT).show();
        Intent getListIntent = new Intent(this,Listscreen.class);

		DatabaseHandler db = new DatabaseHandler(getApplicationContext());
		db.addStudent(new Student(centre_id, name, std));

		getListIntent.putExtra("CENTRE_ID", centre_id);
        startActivity(getListIntent);
    }
}
