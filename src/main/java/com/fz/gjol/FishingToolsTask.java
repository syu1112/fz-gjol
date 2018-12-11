package com.fz.gjol;

import com.fz.gjol.ts.DMPlug;
import com.jacob.com.Dispatch;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class FishingToolsTask extends TimerTask {
    private static String basePath = "D:/FishingTools/imgs/";
    private Timer timer = null;
    private static FishingToolsTask fishingToolsTask;
    public static boolean flag = true;
    public static int count = 0;
    public static int hwnd;
    public static int s;
    public static int f;

    private FishingToolsTask() {
    }

    //单例模式，保持这个对象
    public static FishingToolsTask getInstance(){
        if (flag) {
            fishingToolsTask = new FishingToolsTask();
            flag = false;
        }
        return fishingToolsTask;
    }

    public void start(int hwnd, int s, int f) {
        this.hwnd = hwnd;
        this.f = f;
        this.s = s;
        if (timer == null) {
            timer = new Timer();
        } else {
            //从此计时器的任务队列中移除所有已取消的任务。
            timer.purge();
        }
        timer.scheduleAtFixedRate(this, new Date(), 100);
        System.out.println("start...waittime "+s +"s");
    }

    public synchronized void run() {
        Dispatch.call(DMPlug.com, "SetWindowState", hwnd, 1).getInt();
        Dispatch.call(DMPlug.com, "SetWindowState", hwnd, 8).getInt();
        Dispatch.call(DMPlug.com, "SetWindowState", hwnd, 12).getInt();
        Dispatch.call(DMPlug.com, "SetWindowState", hwnd, 15).getInt();
        count++;
        try {
            Dispatch.call(DMPlug.com, "SetWindowText", hwnd, "[FishingTools][第"+count+"次] 垂钓中...").getInt();
            System.out.println("脚本开始运行...");
            if(Dispatch.call(DMPlug.com, "KeyPressChar", "q").getInt()!=1) {
                System.err.println("KeyPressChar Error");
                return;
            };
            this.wait(f*1000);
            Dispatch.call(DMPlug.com, "SetWindowText", hwnd, "[FishingTools][第"+count+"次] 识别中...").getInt();
            System.out.println("开启循环检测...");
            for(int i=0; i<150; i++) {
                //Dispatch.call(DMPlug.com, "Capture", 0, 0, 1920, 1080, "D://TEST/dt_"+i+".bmp");
                String findPicE = Dispatch.call(DMPlug.com, "FindPicE", 0, 0, 1920, 1080, basePath+ "dy_0.bmp", "000000", 0.90, 0).getString();
                String[] findPicEs = findPicE.split("\\|");
                if(Integer.parseInt(findPicEs[1]) < 0) {
                    this.wait(100);
                    continue;
                }
                System.out.println("已定位!"+findPicE);
                int x1 = Integer.parseInt(findPicEs[1])-100;
                int y1 = Integer.parseInt(findPicEs[2])-100;

                int x2 = Integer.parseInt(findPicEs[1])+200;
                int y2 = Integer.parseInt(findPicEs[2])+200;
                System.out.println("开始寻找鱼钩...");
                String findPicE2 = Dispatch.call(DMPlug.com, "FindPicE", x1, y1, x2, y2, basePath+ "dy_1.bmp", "000000", 0.99, 0).getString();
                String[] split = findPicE2.split("\\|");
                if(Integer.parseInt(split[1]) < 0) {
                    continue;
                }
                System.out.println(findPicE2);
                //                Dispatch.call(DMPlug.com, "Capture", Integer.parseInt(split[1])-50, Integer.parseInt(split[2])-50, Integer.parseInt(split[1])-10, Integer.parseInt(split[2])-10, "D:/鱼钩位置.bmp");

                System.out.println("开始寻找鱼线...");
                String findPicE3 = Dispatch.call(DMPlug.com, "FindPicE", Integer.parseInt(split[1]), Integer.parseInt(split[2])-15, Integer.parseInt(split[1])+20, Integer.parseInt(split[2])-5, basePath+ "dy_2.bmp", "000000", 0.9, 0).getString();
                System.out.println(findPicE3);
                String[] findPicE3s = findPicE3.split("\\|");
                if(Integer.parseInt(findPicE3s[1]) < 0) {
                    continue;
                }
                //                Dispatch.call(DMPlug.com, "Capture", Integer.parseInt(findPicE3s[1])-20, Integer.parseInt(findPicE3s[2])-20, Integer.parseInt(findPicE3s[1])+20, Integer.parseInt(findPicE3s[2])+20, "D:/鱼线位置.bmp");
                Dispatch.call(DMPlug.com, "KeyPressChar", "q").getInt();
                System.out.println("钓鱼成功!");
                Dispatch.call(DMPlug.com, "SetWindowText", hwnd, "[FishingTools][第"+count+"次] 收杆中...").getInt();
                break;
            }
            this.wait(s*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void destroyed(){
        Dispatch.call(DMPlug.com, "SetWindowState", hwnd, 9).getInt();
        System.out.println("destroyed...");
        timer.cancel();
        flag = true;
    }
}
