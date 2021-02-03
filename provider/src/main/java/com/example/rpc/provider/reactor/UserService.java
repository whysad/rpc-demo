package com.example.rpc.provider.reactor;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;
import java.util.Queue;

/**
 * 初始版本
 *
 * userService监听个端口
 */
public class UserService implements Runnable{

    private final int PORT = 8899;

    @Override
    public void run() {
        // 单个监听
        try {
            ServerSocket serverSocket = new ServerSocket();
            serverSocket.bind(new InetSocketAddress(PORT));
            int resultNumber = 1;
            for (;;){
                Socket accept = serverSocket.accept();
                byte[] readBuffer = new byte[100];
                InputStream inputStream = accept.getInputStream();
                inputStream.read(readBuffer);
                if (readBuffer.length > 0){
                    accept.getOutputStream().write(intToBytes(resultNumber++));
                }
                accept.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static byte[] intToBytes(int i){
        // 四个字节
        byte[] intBytes = new byte[4];
        for (int l = 0; l < intBytes.length; l++){
            intBytes[l] = (byte) (i >> (8 * l));
        }
        return intBytes;
    }

    public static void main(String[] args) {
        new Thread(new UserService()).start();
//        System.out.println(Arrays.toString(intToBytes(100000)));
    }
}
