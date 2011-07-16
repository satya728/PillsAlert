package th.co.fingertip.pillsalert;

public class PillsAlertEnum {
	public static class Model{
		public static final int PILL 					= 0;
		public static final int NOTIFICATION 			= 1;
		public static final int PERIOD					= 2;
	}
	public static class Request{
		public static final int PILL_CREATE 			= 0;
		public static final int PILL_READ   			= 1;
		public static final int PILL_UPDATE 			= 2;
		public static final int PILL_DELETE 			= 3;
		
		public static final int NOTIFICATION_CREATE	 	= 10;
		public static final int NOTIFICATION_READ		= 11;
		public static final int NOTIFICATION_UPDATE		= 12;
		public static final int NOTIFICATION_DELETE		= 13;
		
		public static final int PERIOD_CREATE			= 20;
		public static final int PERIOD_READ				= 21;
		public static final int PERIOD_UPDATE			= 22;
		public static final int PERIOD_DELETE			= 23;
	}
	public static class Result{
		public static final int PILL_CREATE = 0;
		public static final int PILL_READ 	 = 1;
		public static final int PILL_UPDATE = 2;
		public static final int PILL_DELETE = 3;
		
		public static final int NOTIFICATION_CREATE	 	= 10;
		public static final int NOTIFICATION_READ		= 11;
		public static final int NOTIFICATION_UPDATE		= 12;
		public static final int NOTIFICATION_DELETE		= 13;
		
		public static final int PERIOD_CREATE			= 20;
		public static final int PERIOD_READ				= 21;
		public static final int PERIOD_UPDATE			= 22;
		public static final int PERIOD_DELETE			= 23;
	}
}
