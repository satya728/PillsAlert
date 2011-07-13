package th.co.fingertip.pillsalert;

public class PillsAlertEnum {
	private static class Model{
		static final int PILL = 0;
	}
	private static class Request{
		static final int PILL_CREATE = 0;
		static final int PILL_READ   = 1;
		static final int PILL_UPDATE = 2;
		static final int PILL_DELETE = 3;
	}
	private static class Result{
		static final int PILL_CREATE = 0;
		static final int PILL_READ 	 = 1;
		static final int PILL_UPDATE = 2;
		static final int PILL_DELETE = 3;
	}
}
