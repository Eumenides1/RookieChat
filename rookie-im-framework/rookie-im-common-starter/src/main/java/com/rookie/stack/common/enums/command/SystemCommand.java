package com.rookie.stack.common.enums.command;

public enum SystemCommand implements Command {

    //心跳 9999
    PING(0x270f),
    /**
     * 登录 9000
     */
    LOGIN(0x2328),

    //登录ack  9001
    LOGINACK(0x2329),

    //登出  9003
    LOGOUT(0x232b),

    //下线通知 用于多端互斥  9002
    MUTUALLOGIN(0x232a),

    ;

    private int command;

    SystemCommand(int command){
        this.command=command;
    }


    @Override
    public int getCommand() {
        return command;
    }
}
