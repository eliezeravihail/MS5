package application;

import java.util.concurrent.ConcurrentHashMap;

public class Data{

	    private static ConcurrentHashMap<String,String> theData = new ConcurrentHashMap<>();

		public static ConcurrentHashMap<String, String> getTheData() {
			return theData;
		}
}