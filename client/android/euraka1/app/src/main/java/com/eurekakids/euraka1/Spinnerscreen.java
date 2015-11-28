package com.eurekakids.euraka1;

/**
 * Created by Kirubanand on 05/09/2015.
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.support.v7.app.ActionBarActivity;
import android.widget.TextView;

import com.eurekakids.http.Httphandler;


public class Spinnerscreen extends AppCompatActivity {

    private Spinner Districtspinner;
    private Spinner Villagespinner;
    private Spinner Blockspinner;
    private Spinner Centerspinner;
    private TextView Centerid;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.spinner_value);
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        addItemsToDisttrictSpinner();
        addListenerDistrictSpinner();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.spinner_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        //noinspection SimplifiableIfStatement
        if (id == R.id.action_next) {
            View ac_view = findViewById(id);
            movetoList(ac_view);
            return true;
        } else if (id == R.id.action_refresh) {
			Httphandler httphandler = new Httphandler(this);
			httphandler.getAllDistricts();
            httphandler.getAllBlocks();
            httphandler.getAllVillages();
			return true;
		}

        return super.onOptionsItemSelected(item);
    }
    public void movetoList(View view)
    {
        Intent getListIntent = new Intent(this,Listscreen.class);
        startActivity(getListIntent);
    }
    public void addItemsToDisttrictSpinner(){

        Districtspinner = (Spinner) findViewById(R.id.name_spinner);
        Villagespinner  = (Spinner) findViewById(R.id.village_spinner);
        Blockspinner    =  (Spinner) findViewById(R.id.block_spinner);
        Centerspinner = (Spinner) findViewById(R.id.center_spinner);
        Centerid = (TextView) findViewById(R.id.cid);

        ArrayAdapter<CharSequence> DistrictspinnerAdapter = ArrayAdapter.createFromResource(this,R.array.dis,android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> VillagespinnerAdapter = ArrayAdapter.createFromResource(this,R.array.vil,android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> BlockspinnerAdapter = ArrayAdapter.createFromResource(this,R.array.bloc,android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> CenterspinnerAdapter = ArrayAdapter.createFromResource(this,R.array.cen,android.R.layout.simple_spinner_item);

        DistrictspinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        VillagespinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        BlockspinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        CenterspinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Districtspinner.setAdapter(DistrictspinnerAdapter);
        Villagespinner.setAdapter(VillagespinnerAdapter);
        Blockspinner.setAdapter(BlockspinnerAdapter);
        Centerspinner.setAdapter(CenterspinnerAdapter);

    }

    public void addListenerDistrictSpinner()
    {
        Districtspinner = (Spinner) findViewById(R.id.name_spinner);
        Villagespinner = (Spinner) findViewById(R.id.village_spinner);
        Blockspinner    =  (Spinner) findViewById(R.id.block_spinner);
        Centerspinner = (Spinner) findViewById(R.id.center_spinner);
        Centerid = (TextView) findViewById(R.id.cid);

        Districtspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                                      @Override
                                                      public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                                          String itemSelected = parent.getItemAtPosition(position).toString();
                                                      }

                                                      @Override
                                                      public void onNothingSelected(AdapterView<?> parent) {

                                                      }
                                                  }


        );

        Villagespinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                                      @Override
                                                      public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                                          String itemSelected = parent.getItemAtPosition(position).toString();
                                                      }

                                                      @Override
                                                      public void onNothingSelected(AdapterView<?> parent) {

                                                      }
                                                  }


        );
        Blockspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                                      @Override
                                                      public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                                          String itemSelected = parent.getItemAtPosition(position).toString();
                                                      }

                                                      @Override
                                                      public void onNothingSelected(AdapterView<?> parent) {

                                                      }
                                                  }


        );
        Centerspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                                      @Override
                                                      public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                                          String itemSelected = parent.getItemAtPosition(position).toString();
                                                             Centerid.setText(itemSelected);
                                                      }

                                                      @Override
                                                      public void onNothingSelected(AdapterView<?> parent) {

                                                      }
                                                  }


        );




    }



}
