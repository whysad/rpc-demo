package com.example.rpc.consumer.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

@RestController
public class UserController {


    @GetMapping("/userNumber")
    public int getUser(){
        try {
            Socket socket = new Socket();
            socket.connect(new InetSocketAddress(InetAddress.getLocalHost(),8899), 1000);
            socket.getOutputStream().write(1);
            byte[] bytes = new byte[100];
            socket.getInputStream().read(bytes);
            if (bytes.length > 0){
                return bytesToInt(bytes);
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        };
        return 0;
    }

    private int bytesToInt(byte[] bytes){
       return  (bytes[0] & 0xff) |
                (bytes[1] & 0xff00) |
                (bytes[2] & 0xff0000) |
                (bytes[3] & 0xff000000);
    }

    public static void main(String[] args) {

    }
}
