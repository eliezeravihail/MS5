package application;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;

public class listener {
    private static volatile boolean stop = false;
    static Data data = new Data();

    public static void listen() throws Exception {
        ServerSocket soc = new ServerSocket(5401);
        while (!stop) {
                try {
                try {
                    soc.setSoTimeout(1000);
                    Socket lis = soc.accept();
                    Scanner s = new Scanner(lis.getInputStream());
                    String line;
                    while (!(line = s.next()).equals("end")) {
                    	if(!Data.manoal && !Data.update){
                    		format(line);
                    	}
                    	Thread.sleep(100);
                    }
                    lis.close();
                    s.close();
                    return;
                } catch (IOException e) {
                }
                soc.close();
                return;
            } catch (Exception e) {
            }
        }
        soc.close();
        return;
    }

	private static void format(String line) {
		String[] vars =  line.split(",");
		ConcurrentHashMap<String, String> d = data.getTheData();
		d.put("aileron", vars[17]);
    	d.put("rudder", vars[19]);
    	d.put("throttle", vars[21]);
    	d.put("planeX", "0");
    	d.put("elevator", vars[18]);
    	d.put("planeY", "0");
    	d.put("airspeed", vars[0]);
    	d.put("heading", vars[12]);
    	d.put("roll", vars[4]);
    	d.put("pitch", vars[5]);
    	d.put("alt", vars[1]);
    	d.put("breaks", vars[23]);

	}

}
