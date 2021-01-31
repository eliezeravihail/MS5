package application;

import java.util.concurrent.ConcurrentHashMap;

public class Data{

	    private static ConcurrentHashMap<String,String> theData = new ConcurrentHashMap<>();
	    private static ConcurrentHashMap<String,String> theVars = new ConcurrentHashMap<>();
	    public static volatile boolean update =  false;
		public static volatile boolean manoal = false;

	    public Data(){
	    	try{
	    	theData.put("aileron", "0");
	    	theData.put("rudder", "0");
	    	theData.put("throttle", "0");
//	    	theData.put("planeX", "0");
	    	theData.put("elevator", "0");
//	    	theData.put("planeY", "0");
//	    	theData.put("airspeed", "0");
//	    	theData.put("heading", "0");
//	    	theData.put("roll", "0");
//	    	theData.put("pitch", "0");
//	    	theData.put("alt", "0");
//	    	theData.put("breaks", "0");

	    	theVars.put("rudder", "set /controls/flight/rudder");
	    	theVars.put("aileron", "set /controls/flight/aileron");
	    	theVars.put("throttle", "set /controls/engines/current-engine/throttle");
	    	theVars.put("elevator", "set /controls/flight/elevator");
//	       	theVars.put("airspeed", "set /instrumentation/airspeed-indicator/indicated-speed-kt");
//	    	theVars.put("heading", "set /instrumentation/heading-indicator/offset-deg");
//	    	theVars.put("roll", "set /instrumentation/attitude-indicator/indicated-roll-deg");
//	    	theVars.put("pitch", "set /instrumentation/attitude-indicator/internal-pitch-deg");
//	    	theVars.put("alt", "set /instrumentation/altimeter/indicated-altitude-ft");
//	    	theVars.put("breaks", "set /controls/flight/speedbrake");
	    	}catch (Exception e){
				// TODO: handle exception
			}
	    }
		public ConcurrentHashMap<String, String> getTheData() {
			return theData;
		}


		public ConcurrentHashMap<String, String> getTheVars() {
			return theVars;
		}
}