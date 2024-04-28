package com.rookie.learn.io.bio;

import lombok.extern.slf4j.Slf4j;

import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author eumenides
 * @description
 * @date 2024/4/10
 */
@Slf4j
public class SockerServer {

    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(9000);

        while (true) {
            System.out.println("等待链接。。");

            Socket socket = serverSocket.accept();

            System.out.println("客户端进来了");

            // handler(socket);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        handler(socket);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }


    }

    private static void handler(Socket clientSocket) throws Exception {
        byte[] bytes = new byte[1024];
        System.out.println("准备read..");
        //接收客户端的数据，阻塞方法，没有数据可读时就阻塞
        int read = clientSocket.getInputStream().read(bytes);
        System.out.println("read完毕。。");
        if (read != -1) {
            System.out.println("接收到客户端的数据：" + new String(bytes, 0, read));
        }

    }

}
