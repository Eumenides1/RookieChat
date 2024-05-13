package com.rookie.stack.common.enums;

/**
 * @author eumenides
 * @description
 * @date 2024/5/9
 */
public enum DeviceMultiLoginEnum {
    /**
     * 单端登录 仅允许 Windows、Web、Android 或 iOS 单端登录。
     */
    ONE(1,"DeviceMultiLoginEnum_ONE"),

    /**
     * 双端登录 允许 Windows、Mac、Android 或 iOS 单端登录，同时允许与 Web 端同时在线。
     */
    TWO(2,"DeviceMultiLoginEnum_TWO"),

    /**
     * 三端登录 允许 Android 或 iOS 单端登录(互斥)，Windows 或者 Mac 单聊登录(互斥)，同时允许 Web 端同时在线
     */
    THREE(3,"DeviceMultiLoginEnum_THREE"),

    /**
     * 多端同时在线 允许 Windows、Mac、Web、Android 或 iOS 多端或全端同时在线登录
     */
    ALL(4,"DeviceMultiLoginEnum_ALL");

    private int loginMode;
    private String loginDesc;

    /**
     * 不能用 默认的 enumType b= enumType.values()[i]; 因为本枚举是类形式封装
     * @param ordinal
     * @return
     */
    public static DeviceMultiLoginEnum getMember(int ordinal) {
        for (int i = 0; i < DeviceMultiLoginEnum.values().length; i++) {
            if (DeviceMultiLoginEnum.values()[i].getLoginMode() == ordinal) {
                return DeviceMultiLoginEnum.values()[i];
            }
        }
        return THREE;
    }

    DeviceMultiLoginEnum(int loginMode, String loginDesc){
        this.loginMode=loginMode;
        this.loginDesc=loginDesc;
    }

    public int getLoginMode() {
        return loginMode;
    }

    public void setLoginMode(int loginMode) {
        this.loginMode = loginMode;
    }

    public String getLoginDesc() {
        return loginDesc;
    }

    public void setLoginDesc(String loginDesc) {
        this.loginDesc = loginDesc;
    }
}
