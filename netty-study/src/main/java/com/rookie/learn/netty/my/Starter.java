package com.rookie.learn.netty.my;

import com.rookie.stack.io.netty.my.server.DiscardServer;

/**
 * @author eumenides
 * @description
 * @date 2024/1/27
 */
public class Starter {
    public static void main(String[] args) throws Exception {
        new DiscardServer(9001).run();
    }
}
