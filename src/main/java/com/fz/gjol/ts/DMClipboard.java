package com.fz.gjol.ts;

import com.jacob.com.Dispatch;

public class DMClipboard extends DMPlug {

    private DMClipboard() {};

    /**
     * 获取剪贴板的内容
     * @return 以字符串表示的剪贴板内容
     */
    public static String get() {
        return Dispatch.call(com, "GetClipboard").getString();
    }

    /**
     * 设置剪贴板的内容
     * @param value 以字符串表示的剪贴板内容
     * @return
     */
    public static boolean set(String value) {
        return Dispatch.call(com, "SetClipboard", value).getInt()==1;
    }

    /**
     * 发送粘贴板内容
     * 向指定窗口发送粘贴命令. 把剪贴板的内容发送到目标窗口.
     * @param hwnd  指定的窗口句柄
     * @return
     */
    public static boolean sendPaste(int hwnd) {
        return Dispatch.call(com, "SendPaste", hwnd).getInt()==1;
    }

}
