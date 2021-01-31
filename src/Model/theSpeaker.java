package Model;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;

import application.Data;


public class theSpeaker {
    private volatile int port;
    private volatile ServerSocket socket;
    private volatile boolean stop = false;
    private ConcurrentHashMap<String, String> theData;

    public theSpeaker(String port) throws IOException {
        this.port = Integer.parseInt(port);
        theData = new Data().getTheData();
        socket = new ServerSocket(this.port);
    }

    public void speak() throws Exception {
        while (!stop) {
            socket.setSoTimeout(1000);
            try {
                Socket connection = socket.accept();
                PrintWriter out = new PrintWriter(connection.getOutputStream(), true);
                while (!stop) {
                    for (String key : theData.values()) {
                        out.println(theData.get(key));
                        Thread.sleep(100);
                    }
                }
                out.close();
                connection.close();
            } catch (IOException e) {

            }
        }
    }
    public void speak(String str) throws Exception {
        while (!stop) {
            socket.setSoTimeout(1000);
            try {
                Socket connection = socket.accept();
                PrintWriter out = new PrintWriter(connection.getOutputStream(), true);
                while (!stop) {
                        out.println(str);
                        Thread.sleep(100);
                }
                out.close();
                connection.close();
            } catch (IOException e) {

            }
        }
    }

    public void Stop() {
        stop = true;
        try {
            this.socket.close();
        } catch (Exception e) {
            System.out.println("try to close a listener failed ");
        }
    }

    //to test it
    public static void main(String[] args) throws InterruptedException, IOException {
        theSpeaker lis = new theSpeaker("5400");
		Thread x = new Thread(()->{
			try{
				lis.speak();
				System.out.println("speak");
				}catch (Exception e) {
					System.err.println(e.getMessage());
					}
            });
        x.start();
		Thread.sleep(1000*60*5);
		lis.Stop();
		x.stop();
		}
}