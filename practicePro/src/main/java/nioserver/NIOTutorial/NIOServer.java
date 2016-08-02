package nioserver.NIOTutorial;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.util.*;

/**
 * Created by I311352 on 7/30/2016.
 */
public class NIOServer implements Runnable{
    private InetAddress hostAddress;
    private int port;

    private ServerSocketChannel serverSocketChannel;

    private Selector selector;

    private ByteBuffer readBuffer = ByteBuffer.allocate(8192);
    private EchoWorker worker;

    //
    private List changeRequests = new LinkedList();
    private Map pendingData = new HashMap();


    private Selector initSelector() throws IOException {
        //create new selector
        Selector selector = SelectorProvider.provider().openSelector();
        this.serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        InetSocketAddress isa = new InetSocketAddress(this.hostAddress, this.port);
        serverSocketChannel.socket().bind(isa);
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        return selector;
    }

    public NIOServer(InetAddress address, int port, EchoWorker worker) throws IOException {
        this.hostAddress = address;
        this.port = port;
        this.selector = this.initSelector();
        this.worker = worker;
    }

    public void run() {
        while (true) {
            System.out.println("waiting connect");
            try {
                // Process any pending changes
                synchronized(this.changeRequests) {
                    Iterator changes = this.changeRequests.iterator();
                    while (changes.hasNext()) {
                        ChangeRequest change = (ChangeRequest) changes.next();
                        switch(change.type) {
                            case ChangeRequest.CHANGEOPS:
                                SelectionKey key = change.socket.keyFor(this.selector);
                                key.interestOps(change.ops);
                        }
                    }
                    this.changeRequests.clear();
                }

                this.selector.select();
                Iterator selectedKeys = this.selector.selectedKeys().iterator();
                while (selectedKeys.hasNext()) {
                    SelectionKey key = (SelectionKey) selectedKeys.next();
                    selectedKeys.remove();
                    if (!key.isValid()) {
                        continue;
                    }
                    if (key.isAcceptable()) {
                        this.accept(key);
                    } else if (key.isReadable()) {
                        this.read(key);
                    } else if (key.isWritable()) {
                        this.write(key);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public void send(SocketChannel channel, byte[] data) {
        synchronized (this.changeRequests) {
            //
            this.changeRequests.add(new ChangeRequest(channel, ChangeRequest.CHANGEOPS, SelectionKey.OP_WRITE));

            // queue the data we want to write
            synchronized (this.pendingData) {
                List queue = (List) this.pendingData.get(channel);
                if (queue == null) {
                    queue = new ArrayList();
                    this.pendingData.put(channel, queue);
                }
                queue.add(ByteBuffer.wrap(data));
            }
        }
        this.selector.wakeup();
    }

    private void write(SelectionKey key) throws IOException {
        SocketChannel socketChannel = (SocketChannel) key.channel();

        synchronized (this.pendingData) {
            List queue = (List) this.pendingData.get(socketChannel);

            // Write until there's not more data ...
            while (!queue.isEmpty()) {
                ByteBuffer buf = (ByteBuffer) queue.get(0);
                socketChannel.write(buf);
                if (buf.remaining() > 0) {
                    // ... or the socket's buffer fills up
                    break;
                }
                queue.remove(0);
            }

            if (queue.isEmpty()) {
                // We wrote away all data, so we're no longer interested
                // in writing on this socket. Switch back to waiting for
                // data.
                key.interestOps(SelectionKey.OP_READ);
            }
        }
    }

    private void read(SelectionKey key) throws IOException {
        SocketChannel socketChannel = (SocketChannel) key.channel();

        this.readBuffer.clear();

        int numRead;
        try {
            numRead = socketChannel.read(this.readBuffer);
        } catch (IOException e) {
            key.cancel();
            socketChannel.close();
            return;
        }
        if (numRead == -1) {
            key.channel().close();
            key.cancel();
            return;
        }
        System.out.println("got data len " + numRead + " read data: " + this.readBuffer.array().toString());
        // Hand the data off to our worker thread
        this.worker.processData(this, socketChannel, this.readBuffer.array(), numRead);
    }

    private void accept(SelectionKey key) throws IOException {
        System.out.println("got connect " + key.toString());
        ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();

        //Accept the connection and make it non-blocking
        SocketChannel socketChannel = serverSocketChannel.accept();
        Socket socket = socketChannel.socket();

        socketChannel.configureBlocking(false);
        socketChannel.register(this.selector, SelectionKey.OP_READ);

    }

    public static void main(String[] args) {
        try {
            EchoWorker worker = new EchoWorker();
            new Thread(worker).start();
            new Thread(new NIOServer(null, 30033, worker)).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
