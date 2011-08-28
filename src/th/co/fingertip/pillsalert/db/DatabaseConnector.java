package th.co.fingertip.pillsalert.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseConnector extends SQLiteOpenHelper {
	
	String db_name;
	int db_version;
	String table_name;
	String create_statement; //must be set
	public DatabaseConnector(Context context, String db_name, String table_name, String create_statement ,int db_version) {
		super(context,db_name,null,db_version);
		this.db_name = db_name;
		this.table_name = table_name;
		this.create_statement = create_statement;
		this.db_version = db_version;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		//db.execSQL(create_statement);
		db.execSQL(DatabaseConfiguration.PILL_CREATE_STATEMENT);
		db.execSQL(DatabaseConfiguration.NOTIFICATION_CREATE_STATEMENT);
		db.execSQL(DatabaseConfiguration.PERIOD_CREATE_STATEMENT);
		//add_dummy_data(db);
	}

	private void add_dummy_data(SQLiteDatabase db) {
		db.execSQL("delete from pills where _id>0");
		db.execSQL("delete from periods where _id>0");
		db.execSQL("delete from notifications where _id>0");
		db.execSQL("insert into pills(_id,title,note,image) values(1,'first pill','f','1.PNG')");
		db.execSQL("insert into pills(_id,title,note,image) values(2,'first pill','f','dummy_pill.png')");
		db.execSQL("insert into pills(_id,title,note,image) values(3,'four','yo','4.png')");
		db.execSQL("insert into pills(_id,title,note,image) values(4,'five','yo','5.png')");
		db.execSQL("insert into pills(_id,title,note,image) values(5,'six','yo','6.png')");
		
		ContentValues cv = new ContentValues();
		cv.put("_id", 1);
		cv.put("title", "morning");
		cv.put("time", "14-45");
		db.insert("periods", null,cv);
		cv.put("_id", 1);
		cv.put("title", "afternoon");
		cv.put("time", "17-45");
		db.insert("periods", null,cv);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w("DatabaseConnector", "Upgrading database from version " + oldVersion + " to "
                + newVersion + ", which will destroy all old data");
        //db.execSQL("DROP TABLE IF EXISTS " + table_name);
		db.execSQL("DROP TABLE IF EXISTS pills");
		db.execSQL("DROP TABLE IF EXISTS periods");
		db.execSQL("DROP TABLE IF EXISTS notifications");
        onCreate(db);
	}

}