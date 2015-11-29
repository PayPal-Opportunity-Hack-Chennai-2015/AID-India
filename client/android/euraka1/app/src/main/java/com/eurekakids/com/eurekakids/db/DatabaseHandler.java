package com.eurekakids.com.eurekakids.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.eurekakids.db.datamodel.Assessment;
import com.eurekakids.db.datamodel.Block;
import com.eurekakids.db.datamodel.Centre;
import com.eurekakids.db.datamodel.District;
import com.eurekakids.db.datamodel.Skill;
import com.eurekakids.db.datamodel.Student;
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
	private static final int DATABASE_VERSION = 4;
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
    public static final String CENTRE_ID = "centre_id";
    private static final String CENTRE_NAME = "centre_name";
	public static final String CHILD_ID = "student_id";
	public static final String CHILD_NAME = "student_name";
	public static final String CHILD_STD = "student_std";
	public static final String SKILL_ID = "skill_id";
	public static final String SKILL_NAME = "skill_name";
	public static final String SKILL_SUBJECT = "skill_subject";
	public static final String IS_COMPLETED = "is_completed";

    public DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_DISTRICT_TABLE = "CREATE TABLE " + TABLE_DISTRICT + "("
				+ DISTRICT_ID + " INTEGER PRIMARY KEY," + DISTRICT_NAME + " TEXT" + ")";
		db.execSQL(CREATE_DISTRICT_TABLE);

		String CREATE_BLOCK_TABLE = "CREATE TABLE " + TABLE_BLOCK + "("
				+ DISTRICT_ID + " INTEGER, " + BLOCK_ID + " INTEGER PRIMARY KEY, "+ BLOCK_NAME + " TEXT, " +
				" FOREIGN KEY ("+ DISTRICT_ID + ") REFERENCES " + TABLE_DISTRICT + "(" + DISTRICT_ID + "));";

//		+ " FOREIGN KEY ("+TASK_CAT+") REFERENCES "+CAT_TABLE+"("+CAT_ID+"));";
		db.execSQL(CREATE_BLOCK_TABLE);

        String CREATE_VILLAGE_TABLE = "CREATE TABLE " + TABLE_VILLAGE + "("
                + BLOCK_ID + " INTEGER," + VILLAGE_ID + " INTEGER PRIMARY KEY," + VILLAGE_NAME + " TEXT," +
				" FOREIGN KEY ("+BLOCK_ID+") REFERENCES "+ TABLE_BLOCK + "(" + BLOCK_ID + "));";
        db.execSQL(CREATE_VILLAGE_TABLE);

        String CREATE_CENTRE_TABLE = "CREATE TABLE " + TABLE_CENTRE + "("
                + VILLAGE_ID + " INTEGER," + CENTRE_ID + " INTEGER PRIMARY KEY," + CENTRE_NAME + " TEXT," +
				" FOREIGN KEY ("+VILLAGE_ID+") REFERENCES "+ TABLE_VILLAGE + "(" + VILLAGE_ID + "));";
        db.execSQL(CREATE_CENTRE_TABLE);

		String CREATE_CHILDREN_TABLE = "CREATE TABLE " + TABLE_CHILDREN + "("
				+ CENTRE_ID + " INTEGER," + CHILD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," + CHILD_NAME + " TEXT," + CHILD_STD + " TEXT," +
				" FOREIGN KEY ("+ CENTRE_ID +") REFERENCES "+ TABLE_CENTRE + "(" + CENTRE_ID + "));";
		db.execSQL(CREATE_CHILDREN_TABLE);

		String CREATE_SKILL_TABLE = "CREATE TABLE " + TABLE_SKILL + "("
				+ SKILL_ID + " INTEGER PRIMARY KEY," + SKILL_NAME + " TEXT," +  SKILL_SUBJECT + " TEXT" +")";
		db.execSQL(CREATE_SKILL_TABLE);

		String CREATE_ASSESSMENT_TABLE = "CREATE TABLE " + TABLE_ASSESSMENT + "("
				+ CHILD_ID + " INTEGER," + SKILL_ID + " INTEGER," + IS_COMPLETED + " INTEGER," +
				" FOREIGN KEY ("+ CHILD_ID +") REFERENCES "+ TABLE_CHILDREN + "(" + CHILD_ID + "), " +
				" FOREIGN KEY ("+ SKILL_ID +") REFERENCES "+ TABLE_SKILL + "(" + SKILL_ID + "));";
		db.execSQL(CREATE_ASSESSMENT_TABLE);
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

    public List<Student> getAllStudents() {
        List<Student> students = new ArrayList<Student>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_CHILDREN;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Student student = new Student();
                student.setCentreId(cursor.getInt(0));
                student.setStudentId(cursor.getInt(1));
                student.setStudentName(cursor.getString(2));
                student.setStudentStd(cursor.getInt(3));

                students.add(student);
            } while (cursor.moveToNext());
        }
        return students;
    }

    public List<Skill> getAllSkills() {
        List<Skill> skills = new ArrayList<Skill>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_SKILL;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Skill skill = new Skill();
                skill.setSkillId(cursor.getInt(0));
                skill.setSkillName(cursor.getString(1));
                skill.setSubjectName(cursor.getString(2));

                skills.add(skill);
            } while (cursor.moveToNext());
        }
        return skills;
    }

    public List<Assessment> getAllAssessments() {
        List<Assessment> assessments = new ArrayList<Assessment>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_ASSESSMENT;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Assessment assessment = new Assessment();
                assessment.setStudentId(cursor.getInt(0));
                assessment.setSkillId(cursor.getInt(1));
                assessment.setIsCompleted(cursor.getInt(2));

                assessments.add(assessment);
            } while (cursor.moveToNext());
        }
        return assessments;
    }

    public List<Block> getAllBlocksByDistrict(String district_name) {
        List<Block> blocks = new ArrayList<Block>();
        String selectQuery1 = "SELECT  * FROM " + TABLE_DISTRICT + " WHERE " + DISTRICT_NAME + " = '" + district_name +"';";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor1 = db.rawQuery(selectQuery1, null);

        cursor1.moveToFirst();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_BLOCK + " WHERE " + DISTRICT_ID + " = " + cursor1.getInt(0) ;

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
        String selectQuery = "SELECT  * FROM " + TABLE_VILLAGE + " WHERE " + BLOCK_ID + " = " + cursor1.getInt(1) ;

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
        String selectQuery = "SELECT  * FROM " + TABLE_CENTRE + " WHERE " + VILLAGE_ID + " = " + cursor1.getInt(cursor1.getColumnIndex(VILLAGE_ID)) ;

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

	public int getCentreIdByName(String centre_name) {
		String selectQuery = "SELECT  * FROM " + TABLE_CENTRE + " WHERE " + CENTRE_NAME + " = '" + centre_name + "'";
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor1 = db.rawQuery(selectQuery, null);
		cursor1.moveToFirst();

		return cursor1.getInt(cursor1.getColumnIndex(CENTRE_ID));
	}

	public ArrayList<Student> getAllStudentsByCentreId(int centre_id){
		String selectQuery = "SELECT  * FROM " + TABLE_CHILDREN + " WHERE " + CENTRE_ID + " = '" + centre_id +"';";
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		ArrayList<Student> students = new ArrayList<>();
		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				Student student = new Student();
				student.setStudentId(cursor.getInt(cursor.getColumnIndex(CHILD_ID)));
				student.setStudentName(cursor.getString(cursor.getColumnIndex(CHILD_NAME)));
				student.setStudentStd(cursor.getInt(cursor.getColumnIndex(CHILD_STD)));
				student.setCentreId(cursor.getInt(cursor.getColumnIndex(CENTRE_ID)));
				students.add(student);
			} while (cursor.moveToNext());
		}
		return students;
	}

    public ArrayList<Assessment> getAllSkillsByIds(int child_id, int skill_id){

        String selectQuery = "SELECT  * FROM " + TABLE_ASSESSMENT + " WHERE " + CHILD_ID + " = " + child_id + " AND " + SKILL_ID + " = " + skill_id +";";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        ArrayList<Assessment> assessments = new ArrayList<>();
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Assessment assessment = new Assessment();
                assessment.setStudentId(cursor.getInt(0));
                assessment.setSkillId(cursor.getInt(1));
                assessment.setIsCompleted(cursor.getInt(2));
                assessments.add(assessment);
            } while (cursor.moveToNext());
        }
        return assessments;
    }

	public void addStudent(Student student) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(CHILD_NAME, student.getStudentName());
		values.put(CHILD_STD, student.getStudentStd());
		values.put(CENTRE_ID, student.getCentreId());
		// Inserting Row
		db.insert(TABLE_CHILDREN, null, values);
		db.close();
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

    private void updateAssessment(List<Assessment> assessments) {
        SQLiteDatabase db = this.getWritableDatabase();

        try {
            for(Assessment assessment : assessments) {
                String sqlQuery = "UPDATE " + TABLE_ASSESSMENT + "SET" + IS_COMPLETED + " = " +  assessment.getIsCompleted() + " where " + SKILL_ID + " = '" + assessment.getSkillId() + "' AND " + CHILD_ID + " = " + assessment.getStudentId();
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
