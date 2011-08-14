package th.co.fingertip.pillsalert.db;

public class DatabaseConfiguration {
	public static String DB_NAME = "pillsalert";
	public static int DB_VERSION = 1;
	
	//note table
	public static String 	PILL_TABLE_NAME 				= "pills";
	public static String[] 	PILL_SCHEMA_KEYS 				= {"_id","title","note","with_image"};
	public static String 	PILL_CREATE_STATEMENT 			= "create table pills (" + 
															  "_id integer primary key autoincrement, " +
															  "title text not null, "+
															  "note text not null, " +
															  "with_image boolean not null);"; 
	
	public static String 	NOTIFICATION_TABLE_NAME			= "notifications";
	public static String[]  NOTIFICATION_SCHEMA_KEYS		= {"_id","pill_id","period_id"};
	public static String 	NOTIFICATION_CREATE_STATEMENT 	= "create table notifications ("+
															  "_id integer primary key autoincrement, "+
															  "pill_id integer not null, "+
															  "period_id integer not null);";
	
	public static String 	PERIOD_TABLE_NAME				= "periods";
	public static String[]  PERIOD_SCHEMA_KEYS				= {"_id","title","time"};
	public static String 	PERIOD_CREATE_STATEMENT 		= "create table periods ("+
															  "_id integer primary key autoincrement, "+
															  "title text not null, "+
															  "time text not null);";
}
