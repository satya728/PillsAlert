package th.co.fingertip.pillsalert;

public class PillsAlertEnum {
	public static class Model{
		public static final int PILL 					= 0;
		public static final int NOTIFICATION 			= 1;
		public static final int PERIOD					= 2;
	}
	public static class Request{
		public static final int PILL_CREATE 			= 1;
		public static final int PILL_READ   			= 2;
		public static final int PILL_UPDATE 			= 3;
		public static final int PILL_DELETE 			= 4;
		
		public static final int NOTIFICATION_CREATE	 	= 11;
		public static final int NOTIFICATION_READ		= 12;
		public static final int NOTIFICATION_UPDATE		= 13;
		public static final int NOTIFICATION_DELETE		= 14;
		
		public static final int PERIOD_CREATE			= 21;
		public static final int PERIOD_READ				= 22;
		public static final int PERIOD_UPDATE			= 23;
		public static final int PERIOD_DELETE			= 24;
		
		public static final int CAMERA_REQUEST 			= 1313;
		public static final int CAMERA_PIC_REQUEST 		= 1414;
	}
	public static class Result{
		public static final int PILL_CREATE 			= 1;
		public static final int PILL_READ 	 			= 2;
		public static final int PILL_UPDATE 			= 3;
		public static final int PILL_DELETE 			= 4;
		
		public static final int NOTIFICATION_CREATE	 	= 11;
		public static final int NOTIFICATION_READ		= 12;
		public static final int NOTIFICATION_UPDATE		= 13;
		public static final int NOTIFICATION_DELETE		= 14;
		
		public static final int PERIOD_CREATE			= 21;
		public static final int PERIOD_READ				= 22;
		public static final int PERIOD_UPDATE			= 23;
		public static final int PERIOD_DELETE			= 24;
	}
	public static class FileName{
		public static final String PILL_DUMMY_FILENAME 	= "dummy_pill.png";
	}
}
