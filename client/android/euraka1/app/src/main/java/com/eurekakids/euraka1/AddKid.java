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



public class AddKid extends AppCompatActivity{

    private Toolbar toolbar;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_new_kid);
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
    }

    public void movetoList(View view)
    {
        EditText KidName;
        KidName = (EditText) findViewById(R.id.editname);
        String Tst = KidName.getText().toString();
        Toast.makeText(AddKid.this,"Added  "+Tst,Toast.LENGTH_SHORT).show();
        Intent getListIntent = new Intent(this,Listscreen.class);
        startActivity(getListIntent);
    }
}
