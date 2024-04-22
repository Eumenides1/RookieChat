package com.rookie.im.tcp;

import com.rookie.im.tcp.server.ImServer;
import org.springframework.beans.factory.annotation.Value;

/**
 * @author eumenides
 * @description
 * @date 2024/4/22
 */
public class Starter {

    public static void main(String[] args) {
        new ImServer();
    }
}
