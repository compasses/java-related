package nettyinaction;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by i311352 on 2/10/2017.
 */
public class EchoServerV1 {
    public static void main(String[] args) throws IOException{
        new EchoServerV1().server(5999);
    }
    public void server(int port) throws IOException {
        final ServerSocket serverSocket = new ServerSocket(port);
        try {
            while (true) {
                final Socket clientSocket = serverSocket.accept();
                System.out.println("Get connection from " + clientSocket);
                new Thread(()->{
                    try {
                        BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                        PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);
                        while (true) {
                            writer.println(reader.readLine());
                            writer.flush();
                        }
                    } catch (IOException ex) {
                        ex.printStackTrace();
                        try {
                            clientSocket.close();
                        } catch (IOException dd) {

                        }
                    }
                    System.out.println("Serve end " + Thread.currentThread().getName());
                }).start();
            }
        } catch (IOException exx) {
            exx.printStackTrace();
        }
    }
}
