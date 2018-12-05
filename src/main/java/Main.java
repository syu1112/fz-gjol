import com.fz.gjol.ts.DMPlug;
import com.jacob.com.Dispatch;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        System.out.println(Dispatch.call(DMPlug.com, "Reg", "allen1112b46ddfa453d1dc7615fa8c255df64676", "pToc"));
//        int hwnd = Dispatch.call(DMPlug.com, "FindWindow", "", "古剑奇谭网络版").getInt();
//        System.out.println(Dispatch.call(DMPlug.com, "BindWindowEx", hwnd,"dx2","windows","windows","dx.public.disable.window.size",101));

        for(int k=0; k<50; k++) {
            Thread.sleep(8000);
            System.out.println(Dispatch.call(DMPlug.com, "KeyPressChar", "q").getInt());
            System.out.println("脚本开始运行...");
            Thread.sleep(8000);
            System.out.println("开启循环检测...");
            for(int i=0; i<500; i++) {
                //Dispatch.call(DMPlug.com, "Capture", 0, 0, 1920, 1080, "D://TEST/dt_"+i+".bmp");
                String findPicE = Dispatch.call(DMPlug.com, "FindPicE", 0, 0, 1920, 1080, "D:/dy_0.bmp", "000000", 0.90, 0).getString();
                String[] findPicEs = findPicE.split("\\|");
                if(Integer.parseInt(findPicEs[1]) < 0) {
                    Thread.sleep(100);
                    continue;
                }
                System.out.println("已定位!"+findPicE);
                int x1 = Integer.parseInt(findPicEs[1])-100;
                int y1 = Integer.parseInt(findPicEs[2])-100;

                int x2 = Integer.parseInt(findPicEs[1])+200;
                int y2 = Integer.parseInt(findPicEs[2])+200;
                System.out.println("开始寻找鱼鳔...");
                String findPicE2 = Dispatch.call(DMPlug.com, "FindPicE", x1, y1, x2, y2, "D:/dy_yb.bmp", "000000", 0.99, 0).getString();
                String[] split = findPicE2.split("\\|");
                if(Integer.parseInt(split[1]) < 0) {
                    continue;
                }
                System.out.println(findPicE2);
                Dispatch.call(DMPlug.com, "Capture", Integer.parseInt(split[1])-50, Integer.parseInt(split[2])-50, Integer.parseInt(split[1])-10, Integer.parseInt(split[2])-10, "D:/鱼鳔位置.bmp");

                System.out.println("开始寻找鱼线...");
                String findPicE3 = Dispatch.call(DMPlug.com, "FindPicE", Integer.parseInt(split[1]), Integer.parseInt(split[2])-20, Integer.parseInt(split[1])+20, Integer.parseInt(split[2]), "D:/dy_2.bmp", "000000", 0.9, 0).getString();
                System.out.println(findPicE3);
                String[] findPicE3s = findPicE3.split("\\|");
                if(Integer.parseInt(findPicE3s[1]) < 0) {
                    continue;
                }
                Dispatch.call(DMPlug.com, "Capture", Integer.parseInt(findPicE3s[1])-20, Integer.parseInt(findPicE3s[2])-20, Integer.parseInt(findPicE3s[1])+20, Integer.parseInt(findPicE3s[2])+20, "D:/鱼线位置.bmp");
                System.out.println(Dispatch.call(DMPlug.com, "KeyPressChar", "q").getInt());
                break;
            }
        }

//        for(int i=0; i<500; i++) {;
//            //ffef61

//        }

//        System.out.println(Dispatch.call(DMPlug.com, "BindWindow", hwnd,"gdi","dx","dx",1).getInt()==1);
//        System.out.println(DMWindow.lockInput(0));
//        int intX = 0, intY = 0;
//        System.out.println(Dispatch.call(DMPlug.com, "FindColor", 0, 0, 300, 300, "4B3C37-4B3C37", 0.9, 0, intX, intY));
//        System.out.println(intX+":"+intY);
//        System.out.println(Dispatch.call(DMPlug.com, "Capture", 0, 0, 300, 300, "D:\\1.bmp"));

//        //置顶窗口
//        System.out.println(Dispatch.call(DMPlug.com, "SetWindowState", hwnd, 8).getInt()==1);
////        Thread.sleep(1000);
//        //恢复并激活窗口
//        System.out.println(Dispatch.call(DMPlug.com, "SetWindowState", hwnd, 12).getInt()==1);
////        Thread.sleep(1000);
//        //获取窗口焦点
//        System.out.println(Dispatch.call(DMPlug.com, "SetWindowState", hwnd, 15).getInt()==1);
////        Thread.sleep(100);
//
//        //开启拟真键盘
////        System.out.println(Dispatch.call(DMPlug.com, "EnableRealKeypad", 1));
////        Thread.sleep(100);
//
//        //按键回车-准备输入
//        System.out.println(Dispatch.call(DMPlug.com, "KeyPressChar", "enter").getInt()==1);
////        Thread.sleep(100);
//        //发送内容
////        System.out.println(Dispatch.call(DMPlug.com, "SendString2", hwnd, "富强、民主、文明、和谐、自由、平等、公正、法治").getInt()==1);
////        Thread.sleep(100);
//        System.out.println(Dispatch.call(DMPlug.com, "KeyDownChar", "1"));
//
//        //按键回车-发送内容
////        System.out.println(Dispatch.call(DMPlug.com, "KeyPressChar", "enter").getInt()==1);
////        Thread.sleep(100);
//
//
//        //取消置顶窗口
//        Dispatch.call(DMPlug.com, "SetWindowState", hwnd, 9);




//        System.out.println(Dispatch.call(DMPlug.com, "SetWindowState", hwnd, 7).getInt()==1);
//        System.out.println(Dispatch.call(DMPlug.com, "SetWindowState", hwnd, 8).getInt()==1);
//        System.out.println(Dispatch.call(DMPlug.com, "SetWindowState", hwnd, 15).getInt()==1);

//        System.out.println(DMWindow.lockInput(1));
//        System.out.println(DMPlug.sendString(hwnd, "中文字符"));
////        System.out.println(DMWindow.unBind());
//        System.out.println(Dispatch.call(DMPlug.com, "FindPic", 940, 48,  983,83, "C:\\Users\\syu11\\Desktop\\天使4.019\\tmp\\test.tmp", 000000, 0.9, 0, 940, 48).getInt()==1);
//        System.out.println(DMClipboard.sendPaste(hwnd));
//
    }

}