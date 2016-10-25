package nioserver.NIOTutorial;

import java.nio.channels.SocketChannel;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by I311352 on 7/30/2016.
 */
class ServerDataEvent {
    public NIOServer server;
    public SocketChannel socket;
    public byte[] data;

    public ServerDataEvent(NIOServer server, SocketChannel socket, byte[] data) {
        this.server = server;
        this.socket = socket;
        this.data = data;
    }
}

public class EchoWorker implements Runnable{
    private List queue = new LinkedList();

    public void processData(NIOServer server, SocketChannel channel, byte[] data, int count) {
        byte[] dataCopy = new byte[count];
        System.arraycopy(data, 0, dataCopy, 0, count);

        synchronized (queue) {
            queue.add(new ServerDataEvent(server, channel, dataCopy));
            queue.notify();
        }
    }

    public void run() {
        ServerDataEvent serverDataEvent;

        while (true) {
            synchronized (queue) {
                while (queue.isEmpty()) {
                    try {
                        queue.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            serverDataEvent = (ServerDataEvent) queue.remove(0);
            // return to sender
            serverDataEvent.server.send(serverDataEvent.socket, serverDataEvent.data);
        }
    }
}
