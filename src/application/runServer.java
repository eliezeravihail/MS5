package application;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class runServer {
    private static volatile boolean stop = false;

    public static void main(String[] args) throws Exception {
        ServerSocket soc = new ServerSocket(5400);
        try {
            while(!stop){
                try{
                    soc.setSoTimeout(1000);
                    Socket lis = soc.accept();
                    Scanner s = new Scanner(lis.getInputStream());
                    String line;
                    while(!(line=s.next()).equals("end"))
                    {
                        try{
                            Socket sp  = new Socket("127.0.0.1", 5401);
                            PrintWriter p = new PrintWriter(sp.getOutputStream(), true);
                            p.println(line);
                            p.close();
                            sp.close();
                        }catch(Exception e){
                            }
                    }
                        lis.close();
                        s.close();
                        return;
                    }catch (IOException e) {
                }
            }
                soc.close();
                return;
            }catch (Exception e) {
                System.out.println(e.getMessage());
            }
        soc.close();
        return;
    }

}
