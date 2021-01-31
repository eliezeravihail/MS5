package application;

import java.io.PrintWriter;
import java.net.Socket;

public class stopServer {

    public static void main(String[] args) throws Exception {
        try {
            Socket sp = new Socket("127.0.0.1", 5400);
            PrintWriter p = new PrintWriter(sp.getOutputStream(), true);
            p.println("end");
            p.close();
            sp.close();
        } catch (Exception e) {
        }
        return;
    }

}
