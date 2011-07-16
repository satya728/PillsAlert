package th.co.fingertip.pillsalert.db;

import android.content.Context;

public class NotificationDatabaseConnector extends DatabaseTemplate {

	public NotificationDatabaseConnector(Context context) {
		super(context);
		configure(
			DatabaseConfiguration.DB_NAME, 
			DatabaseConfiguration.NOTIFICATION_TABLE_NAME, 
			DatabaseConfiguration.NOTIFICATION_CREATE_STATEMENT, 
			DatabaseConfiguration.NOTIFICATION_SCHEMA_KEYS, 
			DatabaseConfiguration.DB_VERSION
		);
	}

}
