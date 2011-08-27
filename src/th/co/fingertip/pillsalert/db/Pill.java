package th.co.fingertip.pillsalert.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class Pill {
	
	//instance variables
	public int id;
	public String title;
	public String note;
	public String image;
	public boolean is_existed;
	
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
	private static int db_version = 1;
	
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
	public void save(){
		//if existed, update
		//else, create new
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
	
	public static Pill[] find(int id){
//		cursor = sqlite_database.query(table_name, {"_id","title","note","image"}, "_id = ?", {""+id}, null, null, null);
		return getPillArray();
	}
	
	public static Pill[] find(String mode){
//		if(mode.equals("all")){
//			cursor = sqlite_database.query(table_name, ["_id","title","note","image"], null, null, null, null, null);
//		}
//		else if(mode.equals("first")){
//			cursor = sqlite_database.query(table_name, ["_id","title","note","image"], "_id = ?", [""+id], groupBy, having, orderBy);
//		}
		
		return getPillArray();
	}
	
	public static Pill[] find(String mode, String conditions){
		return getPillArray();
	}
	
	public static Pill[] find(String mode, String conditions, String order){
		return getPillArray();
	}
	
	public static Pill[] find(String mode, String conditions, String order, String limit){
		return getPillArray();
	}
	
	public static void delete(int id){
		
	}
	
	private static Pill[] getPillArray(){
		if(cursor.getCount() != 0 ){
			Pill[] results = new Pill[cursor.getCount()];
			
			for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()){
				results[cursor.getPosition()] = new Pill(cursor);
			}
			return results;
		}
		return null;
	}
}
