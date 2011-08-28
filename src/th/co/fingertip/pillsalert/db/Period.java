package th.co.fingertip.pillsalert.db;

import th.co.fingertip.pillsalert.PillsAlertEnum;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
public class Period {

	//instance variables
	public int id = 0;
	public String title;
	public String time;
	public boolean is_existed;
	
	//static variables
	private static Cursor cursor;
	private static DatabaseConnector database_connector;
	private static SQLiteDatabase sqlite_database;
	private static boolean is_initialed = false;
	private static String db_name = "pillsalert";;
	private static String table_name = "periods";
	private static String create_statement = "create table periods ("+
											  "_id integer primary key autoincrement, "+
											  "title text not null, "+
											  "time text not null);";
	private static String[] fields = {"_id","title","time"};
	private static int db_version = DatabaseConfiguration.DB_VERSION;
	
	public static final String FIRST = "first";
	public static final String ALL = "all";
	
	
	//instance methods
	
	/**
	 * Create Pill instance from current row of Cursor instance
	 * @param c the Cursor that holds a row data
	 * @return new Pill instance
	 */
	public Period(Cursor c) {
		super();
		id = c.getInt(c.getColumnIndex("_id"));
		title = c.getString(c.getColumnIndex("title"));
	}
	/**
	 * Create Pill instance from a given data set
	 * @param title title of the pill
	 * @param note note of the pill
	 * @param image image file name
	 * @return new Pill instance
	 */
	public Period(String title, String time) {
		super();
		this.title = title;
		this.time = time;
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
		content_values.put("time", time);
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
	
	public static Period find(int id){
		
		String[] query_parameters ={""+id}; 
		cursor = sqlite_database.query(table_name, fields, "_id = ?", query_parameters, null, null, null);
		if(cursor.getCount() == 1){
			return new Period(cursor);
		}
		return null;
	}
	
	public static Period[] find(String mode){
		if(mode.equals(Pill.ALL)){
			cursor = sqlite_database.query(table_name, fields, null, null, null, null, null);
		}
		else if(mode.equals(Pill.FIRST)){
			cursor = sqlite_database.query(table_name, fields, null, null, null, null, null,"1");
		}
		
		return getPeriodArray();
	}
	
	public static Period[] find(String condition_string, String[] condition_parameters){
		cursor = sqlite_database.query(table_name, fields, condition_string, condition_parameters, null, null, null);
		return getPeriodArray();
	}
	
	public static Period[] find(String condition_string, String[] condition_parameters, String order){
		cursor = sqlite_database.query(table_name, fields, condition_string, condition_parameters, null, null, order);
		return getPeriodArray();
	}
	
	public static Period[] find(String condition_string, String[] condition_parameters, String order, String limit){
		cursor = sqlite_database.query(table_name, fields, condition_string, condition_parameters, null, null, order, limit);
		return getPeriodArray();
	}
	
	public static void delete(int id){
		String[] parameters = {""+id};
		sqlite_database.delete(table_name, "_id = ?", parameters);
	}
	
	
	private static Period[] getPeriodArray(){
		if(cursor.getCount() != 0 ){
			Period[] results = new Period[cursor.getCount()];
			
			for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()){
				results[cursor.getPosition()] = new Period(cursor);
			}
			return results;
		}
		return null;
	}
}
