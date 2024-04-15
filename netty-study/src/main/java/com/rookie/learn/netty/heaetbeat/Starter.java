package com.rookie.learn.netty.heaetbeat;


import com.rookie.learn.netty.heaetbeat.server.DiscardServer;

/**
 * @description:
 * @author: lld
 * @version: 1.0
 */
public class Starter {
    //2

    //

    //0-2 + 1 = 3 ___3 //5

    public static void main(String[] args) throws Exception {
        new DiscardServer(9001).run();
    }
}
