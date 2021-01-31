package application;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentHashMap.KeySetView;
import java.util.spi.CurrencyNameProvider;

import javax.xml.transform.stream.StreamSource;

public class speaker {
    private static volatile boolean stop = false;
    private static ConcurrentHashMap<String,String> theData = new Data().getTheData();
    private static ConcurrentHashMap<String,String> theVars = new Data().getTheVars();

    public static void speak() throws Exception {
        Socket soc = new Socket("127.0.0.1",5402);
            PrintWriter p;
            try {
                soc.setSoTimeout(1000);
                p = new PrintWriter(soc.getOutputStream(), true);
                while(!stop){
                    try {
                    	Collection<String> vals = theData.values();
                    	KeySetView<String, String> keys = theData.keySet();
                        for (String key :keys) {
                        	p.println(theVars.get(key)+" "+keys.getMap().get(key));
                        	System.out.println(theVars.get(key)+" "+keys.getMap().get(key));
						}
                        Thread.sleep(500);
                        Data.update = false;
                    }catch (Exception e) {
                        System.out.println("error");
                    }
                }
                p.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        soc.close();
}


    public static void speak(String command) throws Exception {
        Socket soc = new Socket("127.0.0.1",5402);
            PrintWriter p;
            try {
                soc.setSoTimeout(1000);
                p = new PrintWriter(soc.getOutputStream(), true);
                p.println(command);
                p.close();
                soc.close();
            }catch (Exception e) {
                System.out.println("error");
            }
		}
    }