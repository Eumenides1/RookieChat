package com.rookie.im.core.codec.proto;

import lombok.Data;
import lombok.ToString;

/**
 * @author eumenides
 * @description
 * @date 2024/4/23
 */
@Data
public class Message{

    private MessageHeader messageHeader;

    private Object messagePack;

    @Override
    public String toString() {
        return "Message{" +
                "messageHeader=" + messageHeader +
                ", messagePack=" + messagePack +
                '}';
    }
}
