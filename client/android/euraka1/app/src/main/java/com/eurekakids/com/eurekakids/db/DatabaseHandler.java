package com.eurekakids.com.eurekakids.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.eurekakids.db.datamodel.Block;
import com.eurekakids.db.datamodel.Centre;
import com.eurekakids.db.datamodel.District;
import com.eurekakids.db.datamodel.Village;

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
    private static final String TABLE_CENTRE = "centre";
	private static final String TABLE_CHILDREN = "children";
	private static final String TABLE_SKILL = "skill";
	private static final String TABLE_ASSESSMENT = "assessments";


	// Contacts Table Columns names
	private static final String DISTRICT_ID = "district_id";
	private static final String DISTRICT_NAME = "district_name";
    private static final String BLOCK_ID = "block_id";
    private static final String BLOCK_NAME = "block_name";
    private static final String VILLAGE_ID = "village_id";
    private static final String VILLAGE_NAME = "village_name";
    private static final String CENTRE_ID = "centre_id";
    private static final String CENTRE_NAME = "centre_name";

    public DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_DISTRICT_TABLE = "CREATE TABLE " + TABLE_DISTRICT + "("
				+ DISTRICT_ID + " INTEGER PRIMARY KEY," + DISTRICT_NAME + " TEXT" + ")";
		db.execSQL(CREATE_DISTRICT_TABLE);

        String CREATE_VILLAGE_TABLE = "CREATE TABLE " + TABLE_VILLAGE + "("
                + BLOCK_ID + " INTEGER," + " FOREIGN KEY ("+BLOCK_ID+") REFERENCES "+ VILLAGE_ID + " INTEGER PRIMARY KEY," + VILLAGE_NAME + " TEXT" + ")";
        db.execSQL(CREATE_VILLAGE_TABLE);

        String CREATE_CENTRE_TABLE = "CREATE TABLE " + TABLE_CENTRE + "("
                + VILLAGE_ID + " INTEGER," + " FOREIGN KEY ("+VILLAGE_ID+") REFERENCES "+ CENTRE_ID + " INTEGER PRIMARY KEY," + CENTRE_NAME + " TEXT" + ")";
        db.execSQL(CREATE_CENTRE_TABLE);
	}

	// Upgrading database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_DISTRICT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BLOCK);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_VILLAGE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CENTRE);

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
        String selectQuery1 = "SELECT  * FROM " + TABLE_DISTRICT + " WHERE " + DISTRICT_NAME + " = '" + district_name + "'";
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

    public List<Village> getAllVillagesByBlock(String block_name) {
        List<Village> villages = new ArrayList<Village>();
        String selectQuery1 = "SELECT  * FROM " + TABLE_BLOCK + " WHERE " + BLOCK_NAME + " = '" + block_name + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor1 = db.rawQuery(selectQuery1, null);
        cursor1.moveToFirst();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_VILLAGE + " WHERE " + BLOCK_ID + " = " + cursor1.getInt(0) ;

        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Village village = new Village();
                village.setBlock_id(cursor.getInt(0));
                village.setVillage_id(cursor.getInt(1));
                village.setVillage_name(cursor.getString(2));

                villages.add(village);
            } while (cursor.moveToNext());
        }
        return villages;
    }

    public List<Centre> getAllCentresByVillage(String village_name) {
        List<Centre> centres = new ArrayList<Centre>();
        String selectQuery1 = "SELECT  * FROM " + TABLE_VILLAGE + " WHERE " + VILLAGE_NAME + " = '" + village_name + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor1 = db.rawQuery(selectQuery1, null);
        cursor1.moveToFirst();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_CENTRE + " WHERE " + VILLAGE_ID + " = " + cursor1.getInt(0) ;

        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Centre centre = new Centre();
                centre.setVillageId(cursor.getInt(0));
                centre.setCentreId(cursor.getInt(1));
                centre.setCentreName(cursor.getString(2));

                centres.add(centre);
            } while (cursor.moveToNext());
        }
        return centres;
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

    public void addVillages(JSONArray response) {
        ArrayList<Village> villages = new ArrayList<>();
        try {
            for (int i = 0; i < response.length(); i++) {

                JSONObject jsonobject = response.getJSONObject(i);
                int district_id = jsonobject.getInt(BLOCK_ID);
                int village_id = jsonobject.getInt(VILLAGE_ID);
                String village_name = jsonobject.getString(VILLAGE_NAME);
                villages.add(new Village(district_id,village_id,village_name));
            }

            addVillages(villages);

        }catch (JSONException e){
            Log.e(TAG, e.getLocalizedMessage());
        }

    }

    private void addVillages(List<Village> villages) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+ TABLE_VILLAGE);
        db.beginTransaction();
        try {
            for(Village village : villages) {
                String sqlQuery = "INSERT INTO " + TABLE_VILLAGE +" VALUES('"+ village.getBlock_id() + "',' "+ village.getVillage_id() + "',' " + village.getVillage_name() + "')";
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

    public void addCentres(JSONArray response) {
        ArrayList<Centre> centres = new ArrayList<>();
        try {
            for (int i = 0; i < response.length(); i++) {

                JSONObject jsonobject = response.getJSONObject(i);
                int village_id = jsonobject.getInt(VILLAGE_ID);
                int centre_id = jsonobject.getInt(CENTRE_ID);
                String centre_name = jsonobject.getString(CENTRE_NAME);
                centres.add(new Centre(village_id,centre_id,centre_name));
            }

            addCentres(centres);

        }catch (JSONException e){
            Log.e(TAG, e.getLocalizedMessage());
        }

    }

    private void addCentres(List<Centre> centres) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+ TABLE_CENTRE);
        db.beginTransaction();
        try {
            for(Centre centre : centres) {
                String sqlQuery = "INSERT INTO " + TABLE_CENTRE +" VALUES('"+ centre.getVillageId() + "',' "+ centre.getCentreId() + "',' " + centre.getCentreName() + "')";
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
