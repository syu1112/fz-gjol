package com.fz.gjol.ts;

import com.jacob.com.Dispatch;

public class DMKeyMouse extends DMPlug {

    private DMKeyMouse() {
    }

    public static boolean pressChar(String charStr) {
        return Dispatch.call(DMPlug.com, "KeyPressChar", charStr).getInt()==1;
    }

    public static boolean press(int vkCode) {
        return Dispatch.call(DMPlug.com, "KeyPress", vkCode).getInt()==1;
    }


    public static boolean keyDown(int vkCode) {
        return Dispatch.call(DMPlug.com, "KeyDown", vkCode).getInt()==1;
    }
}