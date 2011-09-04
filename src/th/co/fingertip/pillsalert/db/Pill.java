package th.co.fingertip.pillsalert.db;

import th.co.fingertip.pillsalert.PillsAlertEnum;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class Pill {
	
	//instance variables
	public int id = 0;
	public String title;
	public String note;
	public String image;
	
	//static variables
	private static Cursor cursor;
	private static DatabaseConnector database_connector;
	private static SQLiteDatabase sqlite_database;
	private static boolean is_initialed = false;
	private static String db_name = "pillsalert";;
	private static String table_name = "pills";
	private static String create_statement = "create table pills (" + 
											  "_id integer primary key autoincrement, " +
											  "title text not null, "+
											  "note text not null, " +
											  "image text not null);";
	private static String[] fields = {"_id","title","note","image"};
	private static int db_version = DatabaseConfiguration.DB_VERSION;
	
	public static final String FIRST = "first";
	public static final String ALL = "all";
	public static final String ID = "_id";
	public static final String TITLE = "title";
	public static final String NOTE = "note";
	public static final String IMAGE = "image";
	
	
	//instance methods
	
	/**
	 * Create Pill instance from current row of Cursor instance
	 * @param c the Cursor that holds a row data
	 * @return new Pill instance
	 */
	public Pill(Cursor c) {
		super();
		id = c.getInt(c.getColumnIndex("_id"));
		title = c.getString(c.getColumnIndex("title"));
		note = c.getString(c.getColumnIndex("note"));
		image = c.getString(c.getColumnIndex("image"));
	}
	/**
	 * Create Pill instance from a given data set
	 * @param title title of the pill
	 * @param note note of the pill
	 * @param image image file name
	 * @return new Pill instance
	 */
	public Pill(String title, String note, String image) {
		super();
		this.title = title;
		this.note = note;
		this.image = image;
	}
	public boolean save(){
		int operation_status;
		//if existed, update
		if(id!=0){
			operation_status = sqlite_database.update(table_name, getContentValues(), "_id=?", new String[]{id+""});
			if(operation_status!=-1){
				return true;
			}
		}
		//else, create new
		else{
			operation_status = (int)sqlite_database.insert(table_name, null, getContentValues());
			if(operation_status != -1){
				id = operation_status;
				return true;
			}
		}
		return false;
	}
	
	private ContentValues getContentValues(){
		ContentValues content_values = new ContentValues();
		content_values.put("title", title);
		content_values.put("note", note);
		content_values.put("image", image);
		return content_values;
	}



	//static methods
	public static void init(Context context){
		if(!is_initialed){
			//init database connector
			database_connector = new DatabaseConnector(
				context, db_name, table_name, create_statement, db_version
			);
			//init sqlite database instance
			sqlite_database = database_connector.getWritableDatabase();
			is_initialed = true;
		}
	}
	
	public static void close(){
		database_connector.close();
	}
	
	public static void reset(){
		cursor = null;
	}
	
	public static Cursor find_cursor(int id){
		return sqlite_database.query(table_name, fields, "_id = ?", new String[]{id+""}, null, null, null);
	}
	public static Cursor find_cursor(String mode){
		if(mode.equals(Pill.ALL)){
			return sqlite_database.query(table_name, fields, null, null, null, null, null);
		}
		else if(mode.equals(Pill.FIRST)){
			return sqlite_database.query(table_name, fields, null, null, null, null, null,"1");
		}
		return null;
	}
	public static Cursor find_cursor(String condition_string, String[] condition_parameters){
		return sqlite_database.query(table_name, fields, condition_string, condition_parameters, null, null, null);
	}
	public static Cursor find_cursor(String condition_string, String[] condition_parameters, String order){
		return sqlite_database.query(table_name, fields, condition_string, condition_parameters, null, null, order);
	}
	public static Cursor find_cursor(String condition_string, String[] condition_parameters, String order, String limit){
		return sqlite_database.query(table_name, fields, condition_string, condition_parameters, null, null, order, limit);
	}
	
	public static Pill find(int id){
		cursor = find_cursor(id);
		if(cursor.getCount() == 1){
			cursor.moveToFirst();
			return new Pill(cursor);
		}
		return null;
	}
	public static Pill[] find(String mode){
		cursor = find_cursor(mode);
		return getPillArray();
	}
	public static Pill[] find(String condition_string, String[] condition_parameters){
		cursor = find_cursor(condition_string, condition_parameters);
		return getPillArray();
	}
	public static Pill[] find(String condition_string, String[] condition_parameters, String order){
		cursor = find_cursor(condition_string, condition_parameters, order);
		return getPillArray();
	}
	public static Pill[] find(String condition_string, String[] condition_parameters, String order, String limit){
		cursor = find_cursor(condition_string, condition_parameters, order, limit);
		return getPillArray();
	}
	
	public static void delete(int id){
		String[] parameters = {""+id};
		sqlite_database.delete(table_name, "_id = ?", parameters);
	}
	
	
	private static Pill[] getPillArray(){
		if(cursor.getCount() != 0 ){
			Pill[] results = new Pill[cursor.getCount()];
			
			for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()){
				results[cursor.getPosition()] = new Pill(cursor);
			}
			return results;
		}
		return (new Pill[]{});
	}
	@Override
	public boolean equals(Object o) {
		Pill compared_pill = (Pill)o;
		if(compared_pill.id == id){
			return true;
		}
		return false;
	}
	
	
}
