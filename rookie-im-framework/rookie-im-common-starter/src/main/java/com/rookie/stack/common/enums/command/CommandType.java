package com.rookie.stack.common.enums.command;

/**
 * @author eumenides
 * @description
 * @date 2024/5/9
 */
public enum CommandType {

    USER("4"),

    FRIEND("3"),

    GROUP("2"),

    MESSAGE("1"),

    SYSTEM("9")
    ;

    private String commandType;

    public String getCommandType() {
        return commandType;
    }

    CommandType(String commandType) {
        this.commandType = commandType;
    }

    public static CommandType getCommandType(String command) {
        String commandSub = command.substring(0, 1);
        for (int i = 0; i < CommandType.values().length; i++) {
            if (CommandType.values()[i].getCommandType().equals(commandSub)) {
                return CommandType.values()[i];
            }
        }
        return null;
    }

}
