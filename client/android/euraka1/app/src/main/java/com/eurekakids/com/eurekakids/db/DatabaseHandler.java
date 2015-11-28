package com.eurekakids.com.eurekakids.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.eurekakids.db.datamodel.Block;
import com.eurekakids.db.datamodel.District;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.List;

/**
 * Created by paln on 28/11/2015.
 */
public class DatabaseHandler extends SQLiteOpenHelper {

	// All Static variables
	// Database Version
	private static final int DATABASE_VERSION = 1;
	private String TAG = "SQL ERROR";
	// Database Name
	private static final String DATABASE_NAME = "offline_db";

	// Contacts table name
	private static final String TABLE_DISTRICT = "districts";
	private static final String TABLE_BLOCK = "block";
	private static final String TABLE_VILLAGE = "village";
	private static final String TABLE_CHILDREN = "children";
	private static final String TABLE_SKILL = "skill";
	private static final String TABLE_ASSESSMENT = "assessments";


	// Contacts Table Columns names
	private static final String DISTRICT_ID = "district_id";
	private static final String DISTRICT_NAME = "district_name";
    private static final String BLOCK_ID = "block_id";
    private static final String BLOCK_NAME = "block_name";

    public DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_DISTRICT + "("
				+ DISTRICT_ID + " INTEGER PRIMARY KEY," + DISTRICT_NAME + " TEXT" + ")";
		db.execSQL(CREATE_CONTACTS_TABLE);
	}

	// Upgrading database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_DISTRICT);

		// Create tables again
		onCreate(db);
	}

	public List<District> getAllDistricts() {
		List<District> districts = new ArrayList<District>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + TABLE_DISTRICT;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				District district = new District();
				district.setDistrictId(Integer.parseInt(cursor.getString(0)));
				district.setDistrictName(cursor.getString(1));

				districts.add(district);
			} while (cursor.moveToNext());
		}
		return districts;
	}

    public List<Block> getAllBlocksByDistrict(String district_name) {
        List<Block> blocks = new ArrayList<Block>();
        String selectQuery1 = "SELECT  * FROM " + TABLE_DISTRICT + " WHERE " + DISTRICT_NAME + " = " + district_name ;
        SQLiteDatabase db1 = this.getWritableDatabase();
        Cursor cursor1 = db1.rawQuery(selectQuery1, null);
        cursor1.moveToFirst();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_BLOCK + " WHERE " + DISTRICT_ID + " = " + cursor1.getInt(0) ;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Block block = new Block();
                block.setDistrictId(cursor.getInt(0));
                block.setBlockId(cursor.getInt(1));
                block.setBlockName(cursor.getString(2));

                blocks.add(block);
            } while (cursor.moveToNext());
        }
        return blocks;
    }

	public void addDistricts(JSONArray response) {
		ArrayList<District> districts = new ArrayList<>();
		try {
			for (int i = 0; i < response.length(); i++) {

				JSONObject jsonobject = response.getJSONObject(i);
				int id = jsonobject.getInt(DISTRICT_ID);
				String name = jsonobject.getString(DISTRICT_NAME);
				districts.add(new District(id, name));
			}

			addDistricts(districts);

		}catch (JSONException e){
			Log.e(TAG, e.getLocalizedMessage());
		}
		getAllDistricts();
	}

	private void addDistricts(List<District> districts) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + TABLE_DISTRICT);
        db.beginTransaction();
        try {
            for (District district : districts) {
                String sqlQuery = "INSERT INTO " + TABLE_DISTRICT + " VALUES('" + district.getDistrictId() + "',' " + district.getDistrictName() + "')";
                db.execSQL(sqlQuery);
            }
            db.setTransactionSuccessful();
        } catch (Exception e) {
            int i = 0;
            Log.e(TAG, e.getLocalizedMessage());
        } finally {
            db.endTransaction();
        }
    }

    public void addBlocks(JSONArray response) {
        ArrayList<Block> blocks = new ArrayList<>();
        try {
            for (int i = 0; i < response.length(); i++) {

                JSONObject jsonobject = response.getJSONObject(i);
                int district_id = jsonobject.getInt(DISTRICT_ID);
                int block_id = jsonobject.getInt(BLOCK_ID);
                String block_name = jsonobject.getString(BLOCK_NAME);
                blocks.add(new Block(district_id,block_id,block_name));
            }

            addBlocks(blocks);

        }catch (JSONException e){
            Log.e(TAG, e.getLocalizedMessage());
        }

    }

    private void addBlocks(List<Block> blocks) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+ TABLE_BLOCK);
        db.beginTransaction();
        try {
            for(Block block : blocks) {
                String sqlQuery = "INSERT INTO " + TABLE_BLOCK +" VALUES('"+ block.getDistrictId() + "',' "+ block.getBlockId() + "',' " + block.getBlockName() + "')";
                db.execSQL(sqlQuery);
            }
            db.setTransactionSuccessful();
        } catch (Exception e){
            int i=0;
            Log.e(TAG, e.getLocalizedMessage());
        }finally {
            db.endTransaction();
        }
	}

}
