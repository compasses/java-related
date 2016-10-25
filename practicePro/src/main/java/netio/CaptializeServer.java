package netio;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by i311352 on 7/23/16.
 */

public class CaptializeServer {
    public static void main(String[] args) throws Exception{
        int clientNumber = 0;
        ServerSocket listener = new ServerSocket(9898);

    }
}

class Captilizer extends Thread {
    private Socket socket;
    private int clientNumber;
    public Captilizer(Socket socket, int clientNumber) {
        this.socket = socket;
        this.clientNumber = clientNumber;
    }
    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            System.out.println();
        }
    }

}
