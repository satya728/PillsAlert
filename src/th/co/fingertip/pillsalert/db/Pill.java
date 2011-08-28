package th.co.fingertip.pillsalert.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class Pill {
	
	//instance variables
	public int id = 0;
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
	private static String[] fields = {"_id","title","note","image"};
	private static int db_version = 1;
	
	public static final String FIRST = "first";
	public static final String ALL = "all";
	
	
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
	public void save(){
		//if existed, update
		if(id!=0){
			
		}
		//else, create new
		else{
			
		}
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
		String[] query_parameters ={""+id}; 
		cursor = sqlite_database.query(table_name, fields, "_id = ?", query_parameters, null, null, null);
		return getPillArray();
	}
	
	public static Pill[] find(String mode){
		if(mode.equals(Pill.ALL)){
			cursor = sqlite_database.query(table_name, fields, null, null, null, null, null);
		}
		else if(mode.equals(Pill.FIRST)){
			cursor = sqlite_database.query(table_name, fields, null, null, null, null, null,"1");
		}
		
		return getPillArray();
	}
	
	public static Pill[] find(String condition_string, String[] condition_parameters){
		cursor = sqlite_database.query(table_name, fields, condition_string, condition_parameters, null, null, null);
		return getPillArray();
	}
	
	public static Pill[] find(String condition_string, String[] condition_parameters, String order){
		cursor = sqlite_database.query(table_name, fields, condition_string, condition_parameters, null, null, order);
		return getPillArray();
	}
	
	public static Pill[] find(String condition_string, String[] condition_parameters, String order, String limit){
		cursor = sqlite_database.query(table_name, fields, condition_string, condition_parameters, null, null, order, limit);
		return getPillArray();
	}
	
	public static void delete(int id){
		String[] parameters = {""+id};
		sqlite_database.delete(table_name, "id = ?", parameters);
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
