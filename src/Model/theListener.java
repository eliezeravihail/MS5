package Model;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;

import application.Data;


public class theListener {
	private volatile int port;
	private volatile String ip;

	private volatile Socket leasten;
	private volatile boolean stop = false;
	private ConcurrentHashMap<String, String> theData ;

	public theListener(String ip, String port) throws NumberFormatException, IOException{
		this.port = Integer.parseInt(port);
		theData = new Data().getTheData();
		this.ip = ip;
		stop = false;
	}

	public void Listen() throws IOException {
		leasten = new Socket(this.ip, this.port);
		theData = new Data().getTheData();
		while(!stop){
			Scanner toin = new Scanner(leasten.getInputStream());
            String str;
            while (toin.hasNextLine() && !stop) {
                    str = toin.nextLine();
                    theData.put(str,str);
                }
            toin.close();
			}
		leasten.close();
    }

    public void Stop(){
    	stop = true;
        try {
        	leasten.close();
		} catch (IOException e) {
			System.out.println("try to close a listener failed");
		}
    }

  //to test it
    public static void main(String[] args) throws InterruptedException, IOException {
        theListener lis = new theListener("127.0.0.1","5402");
		Thread y = new Thread(()->{
			try{
				lis.Listen();
				System.out.println("listen");
				}catch (Exception e) {
					System.err.println(e.getMessage());
					}
            });
		y.start();
		ConcurrentHashMap<String, String> tempData = new Data().getTheData();
		Thread z= new Thread(()-> {while(true)
		for (String key : tempData.values()) {
            System.out.println(key);
			}
		});
		z.start();
		Thread.sleep(1000*60*5);
		lis.Stop();
		z.stop();
		}
}



