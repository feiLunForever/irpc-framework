package org.idea.irpc.framework.core.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author linhao
 * @date created in 10:39 上午 2020/10/8
 */
public class NioSocketClient extends Thread {
    private SocketChannel socketChannel;
    private Selector selector = null;
    private int clientId;


    public static void main(String args[]) throws IOException{
        NioSocketClient client = new NioSocketClient();
        client.initClient();
        System.out.println("starting client");
        client.start();
    }
 
    public NioSocketClient() {
    }
 
    public NioSocketClient(int clientId) {
        this.clientId = clientId;
    }
 
    public void initClient() throws IOException {
        InetSocketAddress inetSocketAddress = new InetSocketAddress(8888);
        selector = Selector.open();
        socketChannel = SocketChannel.open();
        //设置为非阻塞模式
        socketChannel.configureBlocking(false);
        socketChannel.connect(inetSocketAddress);
        synchronized (selector) {
            //注册到channel上，告诉channel感兴趣的事件是 连接事件
            socketChannel.register(selector, SelectionKey.OP_CONNECT);
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                int key = selector.select();
                if (key > 0) {
                    Set<SelectionKey> keySet = selector.selectedKeys();
                    Iterator<SelectionKey> iter = keySet.iterator();
                    while (iter.hasNext()) {
                        SelectionKey selectionKey = null;
                        synchronized (iter) {
                            selectionKey = iter.next();
                            iter.remove();
                        }
                        //下边是针对不同的事件类型进行处理
                        //连接类型
                        if (selectionKey.isConnectable()) {
                            finishConnect(selectionKey);
                        }

                        //写事件类型
                        if (selectionKey.isWritable()) {
//                            sendWithPak(selectionKey);
                            send(selectionKey);
                        }

                        //读事件类型
                        if (selectionKey.isReadable()) {
                            read(selectionKey);
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
 
    public void finishConnect(SelectionKey key) {
        System.out.println("client finish connect!");
        SocketChannel socketChannel = (SocketChannel) key.channel();
        try {
            socketChannel.finishConnect();
            //连接获取之后，继续订阅感兴趣的事件类型，读和写
            synchronized (selector) {
                socketChannel.register(selector, SelectionKey.OP_WRITE);
                key.interestOps(SelectionKey.OP_WRITE);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void read(SelectionKey key) throws IOException {
        SocketChannel channel = (SocketChannel) key.channel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        int len = channel.read(byteBuffer);
        if (len > 0) {
            byteBuffer.flip();
            byte[] byteArray = new byte[byteBuffer.limit()];
            byteBuffer.get(byteArray);
            System.out.println("client[" + clientId + "]" + "receive from server:"+new String(byteArray,0,len));
            byteBuffer.clear();
        }
        key.interestOps(SelectionKey.OP_READ);
    }


    public void send(SelectionKey key) {
        SocketChannel channel = (SocketChannel) key.channel();
 
        for (int i = 0; i < 10; i++) {
            String ss = i + "Server ,how are you?";
            ByteBuffer byteBuffer = ByteBuffer.wrap(ss.getBytes());
 
            System.out.println("[client] send:{" + i + "}-- " + ss);
            while (byteBuffer.hasRemaining()) {
                try {
 
                    channel.write(byteBuffer);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        try {
            synchronized (selector) {
                channel.register(selector, SelectionKey.OP_READ);
            }
        } catch (ClosedChannelException e) {
            e.printStackTrace();
        }
    }
 
}