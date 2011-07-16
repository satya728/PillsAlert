package th.co.fingertip.pillsalert.db;

import android.content.Context;

public class PillDatabaseAdapter extends DatabaseTemplate {

	public PillDatabaseAdapter(Context context) {
		super(context);
		configure(
			DatabaseConfiguration.DB_NAME, 
			DatabaseConfiguration.PILL_TABLE_NAME, 
			DatabaseConfiguration.PILL_CREATE_STATEMENT, 
			DatabaseConfiguration.PILL_SCHEMA_KEYS, 
			DatabaseConfiguration.DB_VERSION
		);
	}

}
