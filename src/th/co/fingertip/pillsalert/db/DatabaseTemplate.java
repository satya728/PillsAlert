package th.co.fingertip.pillsalert.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public abstract class DatabaseTemplate {
	public DatabaseConnector db_connector;
	public String db_name;
	public int db_version;
	public String table_name;
	public String create_statement;
	public String[] schema;
	public Context context;
	public SQLiteDatabase sqlite_db_instance;
	
	public DatabaseTemplate(Context context) {
		this.context = context; 
	}
	
	public void configure(String db_name, String table_name, String create_statement, String[] schema, int db_version){
		this.db_name 			= db_name;
		this.db_version 		= db_version;
		this.table_name 		= table_name;
		this.schema 			= schema;
		this.create_statement 	= create_statement;
	}
	
	/**
     * connect to the database
     * 
     * @return new DatabaseTemplate instance if connection is not yet established
     */
	public DatabaseTemplate connect(){
		db_connector = new DatabaseConnector(context, db_name, table_name, create_statement, db_version);
		sqlite_db_instance =  db_connector.getWritableDatabase();
		return this;
	}
	
	
	/**
     * close database connection
     * 
     */
	public void close(){
		db_connector.close();
	}
	
	/**
     * insert new row to table
     * 
     * @param params Parameter instance that of the data to be inserted
     * @return new created row id
     */
	public long insertRow(Parameters params){
		ContentValues content_values = params.getContentValues();
		return sqlite_db_instance.insert(table_name, null, content_values);
	}
	
	/**
     * delete row in table
     * 
     * @param params Parameter instance that of the data to be deleted
     * @return true if the selected row is deleted
     */
	public boolean deleteRow(Parameters params){
		return sqlite_db_instance.delete(
				table_name, 
				schema[0]+"="+
				(String)params.get(schema[0]), 
				null
			) > 0;
	}
	public boolean deleteRow(Long row_id){
		return sqlite_db_instance.delete(
				table_name, 
				schema[0]+"="+row_id, 
				null
			) > 0;
	}

	/**
     * get all row according to specified condition
     * 
     * @param where_statement String stating the selection condition
     * @return new Cursor of the rows that match the condition
     */
	public Cursor selectRow(Long row_id){
		Cursor c = null;
		if(row_id==null){
			c = sqlite_db_instance.query(
				table_name, 
				schema,  
				null, null, null, null, null
			);
		}else{
			c = sqlite_db_instance.query(
				table_name, 
				schema,  
				schema[0] + "=" +row_id,
				null, null, null, null
			);
			if(c!=null){
				c.moveToFirst();
			}
		}
		return c;
	}
	
	/**
     * update existing row in table
     * 
     * @param params Parameter instance that of the data to be updated
     * @param where_statement String stating the selection condition
     * @return boolean expressing the update status
     */
	public boolean updateRow(Parameters params,Long row_id){
		ContentValues content_values = params.getContentValues();
		
		return sqlite_db_instance.update(
			table_name, 
			content_values,
			schema[0] + "=" +row_id, 
			null
		) > 0;
	}
	
}
