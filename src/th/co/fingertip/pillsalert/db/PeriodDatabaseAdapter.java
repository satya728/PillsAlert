package th.co.fingertip.pillsalert.db;

import android.content.Context;

public class PeriodDatabaseAdapter extends DatabaseTemplate {

	public PeriodDatabaseAdapter(Context context) {
		super(context);
		configure(
			DatabaseConfiguration.DB_NAME, 
			DatabaseConfiguration.PERIOD_TABLE_NAME, 
			DatabaseConfiguration.PERIOD_CREATE_STATEMENT, 
			DatabaseConfiguration.PERIOD_SCHEMA_KEYS, 
			DatabaseConfiguration.DB_VERSION
		);
	}

}
