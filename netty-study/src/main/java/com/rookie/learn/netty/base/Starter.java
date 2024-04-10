package com.rookie.stack.io.netty.base;

import com.rookie.stack.io.netty.base.server.DiscardServer;

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
