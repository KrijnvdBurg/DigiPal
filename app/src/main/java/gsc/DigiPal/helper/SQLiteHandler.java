/**
 * Author: Ravi Tamada
 * URL: www.androidhive.info
 * twitter: http://twitter.com/ravitamada
 * */
package gsc.DigiPal.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.HashMap;

public class SQLiteHandler extends SQLiteOpenHelper {

	private static final String TAG = SQLiteHandler.class.getSimpleName();

	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "profiler";
	private static final String TABLE_USER = "user";

	// Login Table Columns names
	private static final String KEY_ID = "id";
	private static final String KEY_USERNAME = "username";
	private static final String KEY_FIRSTNAME = "firstname";
	private static final String KEY_LASTNAME = "lastname";


	public SQLiteHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_LOGIN_TABLE = "CREATE TABLE " + TABLE_USER + "("
				+ KEY_ID + " INTEGER PRIMARY KEY,"
				+ KEY_USERNAME + " TEXT,"
				+ KEY_FIRSTNAME + " TEXT,"
				+ KEY_LASTNAME + " TEXT"
				+ ")";
		db.execSQL(CREATE_LOGIN_TABLE);
		Log.d(TAG, "Database tables created");
	}

	// Upgrading database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);

		// Create tables again
		onCreate(db);
	}

	/**
	 * Storing user details in database
	 * */

	public void addUser(int id, String username, String firstname, String lastname) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_ID, id);
		values.put(KEY_USERNAME, username);
		values.put(KEY_FIRSTNAME, firstname);
		values.put(KEY_LASTNAME, lastname);

		// Inserting Row
		long db_id = db.insert(TABLE_USER, null, values);
		db.close(); // Closing database connection

		Log.d(TAG, "New user inserted into sqlite: " + db_id);
	}


	public HashMap<String, String> getUserDetails() {
		HashMap<String, String> user = new HashMap<String, String>();
		String selectQuery = "SELECT  * FROM " + TABLE_USER;

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		// Move to first row
		cursor.moveToFirst();
		if (cursor.getCount() > 0) {

			user.put("id", cursor.getString(0));
			user.put("username", cursor.getString(1));
			user.put("firstname", cursor.getString(2));
			user.put("lastname", cursor.getString(3));
		}
		cursor.close();
		db.close();
		// return user
		Log.d(TAG, "Fetching user from Sqlite: " + user.toString());

		return user;
	}

	/**
	 * Re crate database Delete all tables and create them again
	 * */
	public void deleteUsers() {
		SQLiteDatabase db = this.getWritableDatabase();
		// Delete All Rows
		db.delete(TABLE_USER, null, null);
		db.close();

		Log.d(TAG, "Deleted all user info from sqlite");
	}

}
