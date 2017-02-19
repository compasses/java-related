package nettyinaction;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by i311352 on 2/10/2017.
 */
public class EchoServerV2NIO {

    public static void main(String[] args) throws  IOException {
        new EchoServerV2NIO().server(5999);
    }
    public void server(int port) throws IOException {
        System.out.println("Listening for connections on port " + port);

        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        ServerSocket serverSocket = serverSocketChannel.socket();

        InetSocketAddress address = new InetSocketAddress(port);
        serverSocket.bind(address);

        serverSocketChannel.configureBlocking(false);
        Selector selector = Selector.open();

        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        while (true) {
            try {
                selector.select();
            } catch (IOException ex) {
                ex.printStackTrace();
                break;
            }

            Set readyKeys = selector.selectedKeys();
            Iterator iterator = readyKeys.iterator();
            while (iterator.hasNext()) {
                SelectionKey key = (SelectionKey) iterator.next();
                iterator.remove();
                try {
                    if (key.isAcceptable()) {
                        ServerSocketChannel server = (ServerSocketChannel)key.channel();
                        SocketChannel client = server.accept();
                        System.out.println("Connection from " + client);
                        client.configureBlocking(false);
                        client.register(selector, SelectionKey.OP_READ|SelectionKey.OP_WRITE, ByteBuffer.allocate(100));
                    }

                    if (key.isReadable()) {
                        SocketChannel client = (SocketChannel) key.channel();
                        ByteBuffer output = (ByteBuffer) key.attachment();
                        client.read(output);
                        System.out.println("Channel read " + client);
                    }

                    if (key.isWritable()) {
                        SocketChannel client = (SocketChannel) key.channel();
                        ByteBuffer output = (ByteBuffer) key.attachment();
                        output.flip();client.write(output);
                        output.compact();
                        System.out.println("Channel write " + client);
                    }

                } catch (IOException exx) {
                    key.cancel();
                    try {
                        key.channel().close();
                    } catch (IOException ee) {

                    }
                    exx.printStackTrace();
                }
            }

        }

    }
}
